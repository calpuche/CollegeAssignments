package com.hilltoponline.model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private Integer userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Date dob;
	private Integer roleId;
	
	public static final class UserMapper implements RowMapper<User>{
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User(rs.getInt("userId"), 
					rs.getString("username"), 
					rs.getString("password"),
					rs.getString("firstName"),
					rs.getString("lastName"),
					rs.getString("address"),
					rs.getString("phone"),
					rs.getDate("dob"),
					rs.getInt("roleId"));
			return user;
		}
	}

}
