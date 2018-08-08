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
public class FinalGrade {
	
	private Integer finalGradeId;
	private Integer userId;
	private Integer sectionId;
	private Float finalGradeGpa;
	private String finalGradeLetter;
		
	public static final class FinalGradeMapper implements RowMapper<FinalGrade>{
		public FinalGrade mapRow(ResultSet rs, int rowNum) throws SQLException {
			FinalGrade finalGrade = new FinalGrade(rs.getInt("finalGradeId"), 
					rs.getInt("userId"), 
					rs.getInt("sectionId"),
					rs.getFloat("finalGradeGpa"), 
					rs.getString("finalGradeLetter"));
			return finalGrade;
		}
	}
}
