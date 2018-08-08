package com.hilltoponline.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;
import com.hilltoponline.model.Registration;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Repository
public class SectionRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Section> getAllSections() {
		// DONE
		String sql = "SELECT * FROM Sections";
		
    	return this.jdbcTemplate.query(sql, new Section.SectionMapper());
    	
	}
	
	public List<Section> getAllSectionsByTerm(Term term) {
		// DONE
		// THESE ARE THE SECTIONS IN THE CURRENT SEMESTER
		Integer termId = term.getTermId();
		
		String sql = "SELECT * FROM Sections WHERE Sections.termId = ?";
		
		return this.jdbcTemplate.query(sql, new Section.SectionMapper(), termId);
		
	}
	
	// authorized user: registrar
	// pre: section is a Section with courseId, termId, userId, sectionDay and sectionTime.
	// post: if(rv == 0) did not insert into DB. 
	//		 if(rv != 0) insert successful.
	public boolean addSection(Section section) {
		// DONE
		Integer courseId = section.getCourseId();
		Integer termId = section.getTermId();
		Integer userId = section.getUserId();
		String sectionDay = section.getSectionDay();
		String sectionTime = section.getSectionTime();
		
		String sql = "INSERT INTO Sections (courseId, termId, userId, sectionDay, sectionTime) VALUES (?, ?, ?, ?, ?)";
		Integer rowsAffected = this.jdbcTemplate.update(sql, courseId, termId, userId, sectionDay, sectionTime);
		
		return rowsAffected > 0;

	}
	
	// authorized user: registrar
	public boolean removeSectionById(Integer id) {
		// DONE 
		Integer sectionIdToRemove = id;
		
		String sql = "DELETE FROM Sections WHERE sectionId = ?";
		Integer rowsAffected = this.jdbcTemplate.update(sql, sectionIdToRemove);
		
		return rowsAffected > 0;
		
	}
	
	// authorized user: registrar
	// pre: section is the edited version of the section to edit.
	//      sectionId cannot change
	public boolean editSection(Section section) {
		// DONE
		Integer sectionId = section.getSectionId();
		Integer courseId = section.getCourseId();
		Integer termId = section.getTermId();
		Integer userId = section.getUserId();
		String sectionDay = section.getSectionDay();
		String sectionTime = section.getSectionTime();
		
		String sql = "UPDATE Sections SET courseId = ?, termId = ?, userId = ?, sectionDay = ?, sectionTime = ? WHERE sectionId = ?";
		Integer rowsAffected = this.jdbcTemplate.update(sql, courseId, termId, userId, sectionDay, sectionTime, sectionId);
		
		return rowsAffected > 0;
		
	}
	
	// authorized user: instructor, student
	public List<Section> getSchedule(Term term, User user) {
		// TODO: fix the SQL it's wrong!
		Integer termId = term.getTermId();
		Integer userId = user.getUserId();
		String sql = "";
		
		// if user is a student
		if (user.getRoleId() == 3) {
			sql = "SELECT * FROM Sections WHERE Sections.termId = ? AND Sections.sectionId IN (SELECT Registrations.sectionId FROM Registrations WHERE Registrations.userId = ?)";
		}
		else {
			// user is instructor
			sql = "SELECT * FROM Sections WHERE Sections.termId = ? AND Sections.userId = ?";
		}
		
		return this.jdbcTemplate.query(sql, new Section.SectionMapper(), termId, userId);
	}
	
	// authorized user: instructor, student
	public List<Section> getSectionHistory(User user) {
		// DONE
		Integer userId = user.getUserId();
		String sql = "";
		
		if(user.getRoleId() == 3) {
			sql =  "SELECT * FROM Sections WHERE Sections.sectionId IN (SELECT Registrations.sectionId FROM Registrations WHERE Registrations.userId = ?)";
		}
		else {
			sql = "SELECT * FROM Sections WHERE Sections.userId = ?";
		}
		
		return this.jdbcTemplate.query(sql, new Section.SectionMapper(), userId);
	}
	
	// authorized user: instructor 
	public List<User> viewStudentsInSection(Section section) {
		// DONE BUT PRINTS OUT 3 TIMES THE SAME USER/STUDENT...
		Integer sectionId = section.getSectionId();
		
		String sql = "SELECT * FROM Users, Registrations, Sections WHERE Sections.sectionId = ? AND Users.userId=Registrations.userId";
	
		return this.jdbcTemplate.query(sql, new User.UserMapper(), sectionId);
	}
	
	public List<User> getStudentsInSection(Section section) {
		
		Integer sectionId = section.getSectionId();
		
		String sql = "SELECT * FROM Users WHERE Users.userId IN (SELECT Registrations.userId FROM Registrations WHERE Registrations.sectionId = ?)";
		
		List<User> usersInSection = this.jdbcTemplate.query(sql, new User.UserMapper(), sectionId);
				
		return usersInSection;
	}
	
	public List<Registration> getAllRegistrations() {
		String sql = "SELECT * FROM Registrations";
		return this.jdbcTemplate.query(sql, new Registration.RegistrationMapper());
	}
	
	public boolean addRegistration(Registration reg) {
		// DONE
		Integer sectionId = reg.getSectionId();
		Integer studentId = reg.getUserId();
		String sql = "INSERT INTO Registrations (sectionId, userId) VALUES (?, ?)";
		
		try{
			int rows = this.jdbcTemplate.update(sql, sectionId, studentId);
			return rows > 0;
		} catch(DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeRegistration(Registration reg) {
		// DONE
		Integer studentId = reg.getUserId();
		Integer sectionId = reg.getSectionId();
		String sql = "DELETE FROM Registrations WHERE Registrations.userId = ? AND Registrations.sectionId = ?";
		
		Integer rows = this.jdbcTemplate.update(sql, studentId, sectionId);
		
		return rows > 0;
	}
	public List<Section> getSectionsByUserAndCourse(Integer userId, Integer courseId, Integer termId) {
		String sql = "SELECT * FROM Sections where Sections.courseId = ? AND Sections.userId = ? AND Sections.termId = ?";
		
		return this.jdbcTemplate.query(sql,  new Section.SectionMapper(), courseId, userId, termId);
	}
	
	public Section getSectionById(Integer sectionId) {		
		String sql = "SELECT * FROM Sections WHERE sectionId = ?;";
		return this.jdbcTemplate.queryForObject(sql, new Section.SectionMapper(), sectionId);
	}
	
	public Section getSectionBySectionId(Integer sectionId){
		String sql = "SELECT * FROM Sections where Sections.sectionId = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{sectionId}, new Section.SectionMapper());
	}

	
	public List<Section> getSectionsInDeptAndTerm(Integer deptId, Integer termId) {
		String sql = "select * from sections s where s.courseId in (select courses.courseId from courses where deptId=?) and s.termId=?";
		
		return this.jdbcTemplate.query(sql, new Section.SectionMapper(), deptId, termId);
	}
	
	public List<Section> getSectionsByCourseDeptTerm(Integer courseId, Integer deptId, Integer termId) {
		String sql = "SELECT * FROM Sections WHERE courseId = ? AND termId = ? AND courseId IN (select courseId from Courses where deptId=?)";
		
		return this.jdbcTemplate.query(sql, new Section.SectionMapper(), courseId, termId, deptId);
	}
	
	public List<User> getAllInstructors() {
		String sql = "SELECT * FROM Users WHERE Users.roleId = 2";
		
		return this.jdbcTemplate.query(sql, new User.UserMapper());
	}
	
	public List<User> getInstructorsInDepartment(Integer deptId) {
		String sql = "SELECT * FROM Users WHERE Users.userId IN (SELECT Sections.userId FROM Sections, Courses WHERE Sections.courseId = Courses.courseId  AND Courses.deptId = ?)";
		
		return this.jdbcTemplate.query(sql, new User.UserMapper(), deptId);
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SectionHistoryRecord {
		
		private String courseName;
		private Section section;
		private List<User> listUsers;
		
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CurrentSectionRecord {
		
		private Course course;
		private Section section;
		private Department dept;
		private User user;
		
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DepartmentAndTerm {
		
		private Department department;
		private Term term;
		
	}

}
