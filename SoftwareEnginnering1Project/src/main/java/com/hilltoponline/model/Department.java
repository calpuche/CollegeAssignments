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
public class Department 
{

	private Integer deptId;
	private String deptName;
	private String deptAbbreviation;
	

	public static final class DepartmentMapper implements RowMapper<Department>{
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			Department dept = new Department(rs.getInt("deptId"), 
					rs.getString("deptName"), 
					rs.getString("deptAbbreviation"));
			return dept;
		}
	}

}
