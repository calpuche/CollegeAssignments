package com.hilltoponline.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	
	private Integer roleId;
	private String role;
	
	public static final class RoleMapper implements RowMapper<Role> {
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role(rs.getInt("roleId"),
					rs.getString("role"));
			return role;
		}
	}
	
	public String getFormattedRole() {
		return (""+role.charAt(5)).toUpperCase() + role.substring(6).toLowerCase();
	}

}
