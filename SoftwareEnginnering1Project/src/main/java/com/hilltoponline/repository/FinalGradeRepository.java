package com.hilltoponline.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hilltoponline.repository.CourseRepository;

import com.hilltoponline.model.FinalGrade;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Repository
public class FinalGradeRepository {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<FinalGrade> getAllFinalGrades() {
		String sql = "SELECT * FROM FinalGrades";
    	return this.jdbcTemplate.query(sql, new FinalGrade.FinalGradeMapper());
	}
	
	///////////////************Added for gpa
	public List<FinalGrade> getAllFinalGradesForUser(User user)
	{
		int userId = user.getUserId();
		String sql = "SELECT * FROM FinalGrades where userId=?";
    	return this.jdbcTemplate.query(sql, new Object[]{userId}, new FinalGrade.FinalGradeMapper());
	}
	
	public FinalGrade getFinalGrade(User user, Section section){
		int userId = user.getUserId();
		int sectionId = section.getSectionId();
		
		String sql = "SELECT * FROM FinalGrades WHERE userId=? AND sectionId=?";
		
		List<FinalGrade> finalGrades =  this.jdbcTemplate.query(sql, new Object[]{userId,sectionId}, new FinalGrade.FinalGradeMapper());	
		return finalGrades.size() == 0 ? null : finalGrades.get(0);
	}
	
	public String viewFinalGrade(FinalGrade finalGrade){
		int userId = finalGrade.getUserId();
		int sectionId = finalGrade.getSectionId();
		
		String sql = "SELECT * FROM FinalGrades WHERE userId=? AND sectionId = ?";
		
		return this.jdbcTemplate.queryForObject(sql, new Object[]{userId,sectionId}, new FinalGrade.FinalGradeMapper()).getFinalGradeLetter();	
	}
	public boolean submitFinalGrade(FinalGrade finalGrade){
		int userId = finalGrade.getUserId();
		int sectionId = finalGrade.getSectionId();
		float finalGpa = finalGrade.getFinalGradeGpa();
		String finalGpaLetter = finalGrade.getFinalGradeLetter();
		String sql = "INSERT INTO FinalGrades(userId,sectionId,finalGradeGpa,finalGradeLetter) VALUES (?,?,?,?)";
		Integer rows = this.jdbcTemplate.update(sql,userId,sectionId,finalGpa,finalGpaLetter);
		return rows > 0;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FinalGradeRecord {
		
		private String letterGrade;
		private Section section;
		private String courseName;
		private float gpaPoints; ///////////////************Added for gpa
		
	}
}