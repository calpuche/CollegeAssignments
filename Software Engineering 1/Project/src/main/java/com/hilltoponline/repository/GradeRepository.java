package com.hilltoponline.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.AssignmentGroup;
import com.hilltoponline.model.Grade;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hilltoponline.model.User;

@Repository
public class GradeRepository {
	private JdbcTemplate jdbcTemplate;
	private final static Logger LOG = LoggerFactory.getLogger(GradeRepository.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Grade> getAllGrades() {
		String sql = "SELECT * FROM Grades";
    	return this.jdbcTemplate.query(sql, new Grade.gradeMapper());
	}
	
	public Grade getGrade(User user, Assignment assignment) {
		Integer userId = user.getUserId();
		Integer asgId = assignment.getAsgId();
		String sql = "SELECT * FROM Grades where userId = ? AND asgId = ?";
    	List<Grade> studentGrade = this.jdbcTemplate.query(sql, new Object[]{userId, asgId}, new Grade.gradeMapper());
    	return studentGrade.size() > 0 ? studentGrade.get(0) : new Grade(null, 0f, asgId, userId);
	}
	
	
	public boolean updateGrades(List<Grade> newGrades){
		try {
			for (Grade newGrade : newGrades){
				Integer rowsAffected = 0;
				if (newGrade.getGradeId() == null){
					String sql = "INSERT INTO Grades (grade, asgId, userId) VALUES (?,?,?)";
					rowsAffected = this.jdbcTemplate.update(sql, new Object[]{newGrade.getGrade(), newGrade.getAsgId(), newGrade.getUserId()});
				} else {
					String sql = "UPDATE Grades SET grade = ? where gradeId = ?";
					rowsAffected = this.jdbcTemplate.update(sql, new Object[]{newGrade.getGrade(), newGrade.getGradeId()});
				}
				if (rowsAffected == 0) {
					return false;
				}
				
			} 
		} catch(Exception e) {
			LOG.debug(e.toString());
			return false;
		}
		return true;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GradingTable {
		
		private Section section;
		private List<Assignment> assignments;
		private List<AssignmentGroup> assignmentGroups;
		private Map<Integer, List<Grade>> grades;
		private Map<Integer, User> students;
	}
	
}
