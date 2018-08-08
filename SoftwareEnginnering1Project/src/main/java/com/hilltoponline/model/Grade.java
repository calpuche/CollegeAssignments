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
public class Grade {
	private Integer gradeId;
	private Float grade;
	private Integer asgId;
	private Integer userId;
	
	public static final class gradeMapper implements RowMapper<Grade>{
		public Grade mapRow(ResultSet rs, int rowNum) throws SQLException{
			Grade grade = new Grade(rs.getInt("gradeId"),
					rs.getFloat("grade"),
					rs.getInt("asgId"),
					rs.getInt("userId"));
			return grade;
		}
	}
}
