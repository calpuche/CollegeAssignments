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
public class Registration {
	
	private Integer registrationId;
	private Integer userId;
	private Integer sectionId;
	
	public static final class RegistrationMapper implements RowMapper<Registration>{
		public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
			Registration registration = new Registration(rs.getInt("registrationId"),
					rs.getInt("userId"),
					rs.getInt("sectionId"));
			return registration;
		}
	}
}
