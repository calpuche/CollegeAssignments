package com.hilltoponline.repository;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hilltoponline.model.Role;
import com.hilltoponline.model.User;

@Repository
public class UserRepository {
	
	private JdbcTemplate jdbcTemplate;
	private final static Logger LOG = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public List<User> getAllUsers() {
    	String sql = "SELECT * FROM Users";
    	return this.jdbcTemplate.query(sql, new User.UserMapper());
    }
    
    public User getUserByUsername(String username) {
    	String sql = "select * from Users where username=?";	    	
    	return this.jdbcTemplate.queryForObject(sql, new Object[]{username}, new User.UserMapper());
    }
    
    public User getUserById(Integer userId) {
    	String sql = "select * from Users where userId=?";	    	
    	return this.jdbcTemplate.queryForObject(sql, new Object[]{userId}, new User.UserMapper());
    }
    
    public User isUserClaimable(User user) {
    	String sql = "SELECT * FROM Users "
    			+ "WHERE 1=1 AND "
    			+ "firstName = ? AND "
    			+ "lastName = ? AND "
    			+ "phone = ? AND "
    			+ "dob = ? AND "
    			+ "password IS NULL";
    	List<User> users = this.jdbcTemplate.query(sql, new Object[]{
					    			user.getFirstName(),
					    			user.getLastName(),
					    			user.getPhone(),
					    			user.getDob()
					    	}, new User.UserMapper());
    	return users.size() == 0 ? null : users.get(0); 
    }  
    
    public boolean addNewUser(User user) {
    	if (empty(user.getUsername(), user.getFirstName(), user.getLastName(),
				user.getAddress(), user.getPhone(), user.getRoleId())) {
    		return false;
    	}
    	
    	String sql = "INSERT INTO Users "
    			+ "(username, firstName, lastName, address, phone, dob, roleId) "
    			+ "VALUES (?,?,?,?,?,?,?)";
    	try {
    		Integer rowsAffected = this.jdbcTemplate.update(sql, new Object[]{
    				user.getUsername(), user.getFirstName(), user.getLastName(),
    				user.getAddress(), user.getPhone(), user.getDob(), user.getRoleId()
    				});
    		return rowsAffected > 0;
    		
    	} catch (Exception e) {
    		LOG.warn(e.toString());
    	}
		
		return false;
    }  
    
    public boolean updateUser(User user) {
    	if (empty(user.getUsername(), user.getFirstName(), user.getLastName(),
				user.getAddress(), user.getPhone(), user.getRoleId())) {
    		LOG.warn("EMPTY USER: {}", user);
    		return false;
    	}
    	
    	String sql = "UPDATE Users "
    			+ "SET username = ?, "
    			+ "firstName = ?, "
    			+ "lastName = ?, "
    			+ "address = ?, "
    			+ "phone = ?, "
    			+ "dob = ?, "
    			+ "roleId = ? "
    			+ "WHERE userId = ?";
    	try {
    		Integer rowsAffected = this.jdbcTemplate.update(sql, new Object[]{
    				user.getUsername(), user.getFirstName(), user.getLastName(),
    				user.getAddress(), user.getPhone(), user.getDob(), 
    				user.getRoleId(), user.getUserId()});
    		return rowsAffected > 0;
    		
    	} catch (Exception e) {
    		LOG.warn(e.toString());
    	}
		
		return false;
    }  

	public boolean updateUserPassword(User user, String password) {
    		String encodedPassword = passwordEncoder.encode(password);
    		String sql = "UPDATE Users SET password = ? where userId = ?";
    		return this.jdbcTemplate.update(sql, new Object[]{encodedPassword, user.getUserId()}) > 0;
    }
    
	public List<Role> getAllRoles() {
		String sql = "SELECT * FROM Roles";
		return this.jdbcTemplate.query(sql, new Role.RoleMapper());
	}
	
	public Role getRoleById(Integer roleId) {
		String sql = "select roleId, role from Roles where roleId = ?";	    	
		Role role = this.jdbcTemplate.queryForObject(sql, new Object[]{roleId}, new Role.RoleMapper());
		return role;
    }	
	
	public Boolean removeUserById(Integer userId) {
		String sql = "DELETE FROM Users WHERE userId = ?";	    	
		return this.jdbcTemplate.update(sql, new Object[]{userId}) > 0;
    }	
	
    private boolean empty(String username, String firstName, String lastName, 
    		String address, String phone, Integer roleId) {
		return username.length() == 0 || firstName.length() == 0 || lastName.length() == 0 
				|| address.length() == 0 || phone.length() == 0 || roleId == null;
	}
    
    //return a user with only name and userId
  	public User getMinimalProps(User user) {
  		return new User(user.getUserId(), null, null, user.getFirstName(),
  				user.getLastName(), null, null, null, null);
  	}

}
