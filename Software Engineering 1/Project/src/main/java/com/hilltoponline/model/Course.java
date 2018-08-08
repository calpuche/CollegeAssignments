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
public class Course 
{
		
		private Integer courseId;
		private Integer courseCredits;
		private String courseName;
		private Integer courseNum;
		private String courseDescription;
		private Integer deptId;
		
	
		public static final class CourseMapper implements RowMapper<Course>{
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course Course = new Course(rs.getInt("courseId"), 
						rs.getInt("courseCredits"), 
						rs.getString("courseName"),
						rs.getInt("courseNum"),
						rs.getString("courseDescription"),
						rs.getInt("deptId"));
				return Course;
			}
		}
		
	

}
