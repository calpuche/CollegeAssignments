package com.hilltoponline.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.AssignmentGroup;
import com.hilltoponline.model.Course;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Repository
public class AssignmentRepository {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Assignment> getAllAssignments() {
		String sql = "SELECT * FROM Assignments";
    	return this.jdbcTemplate.query(sql, new Assignment.AssignmentMapper());
	}
	
	public List<Assignment> getAssignmentsByGroupId(Integer groupId) {
		String sql = "SELECT * FROM Assignments where groupId = " + groupId;
    	return this.jdbcTemplate.query(sql, new Assignment.AssignmentMapper());
	}
	
	public Assignment getAssignmentByAsgId(Integer asgId) {
		String sql = "SELECT * FROM Assignments where asgId = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{asgId}, new Assignment.AssignmentMapper());
	}
	
	public boolean editAssignment(Assignment asg) {
		String sql = "UPDATE Assignments SET asgName = ?, asgWeight = ? WHERE asgId = ?";
		return this.jdbcTemplate.update(sql, asg.getAsgName(), asg.getAsgWeight(), asg.getAsgId()) > 0;
	}
	
	public boolean addAssignment(Assignment asg) {
		String sql = "INSERT INTO Assignments (groupId, asgName, asgWeight) VALUES (?, ?, ?)";
		return this.jdbcTemplate.update(sql, new Object[]{asg.getGroupId(), asg.getAsgName(), asg.getAsgWeight()}) > 0;
	}
	
	public boolean removeAssignment(Integer asgId)
	{
		String sql = "DELETE FROM Assignments where asgId = ?";
		return this.jdbcTemplate.update(sql, new Object[]{asgId}) > 0;
	}
	
	public List<AssignmentGroup> getAllAssignmentGroups() {
		String sql = "SELECT * FROM AssignmentGroups";
    	return this.jdbcTemplate.query(sql, new AssignmentGroup.AssignmentGroupMapper());
	}
	
	public List<AssignmentGroup> getAssignmentGroupsBySectionId(Integer sectionId) {
		String sql = "SELECT * FROM AssignmentGroups where sectionId = ?";
    	return this.jdbcTemplate.query(sql, new Object[]{sectionId}, new AssignmentGroup.AssignmentGroupMapper());
	}
	
	public AssignmentGroup getAssignmentGroupsByGroupId(Integer groupId) {
		String sql = "SELECT * FROM AssignmentGroups where groupId = ?";
    	return this.jdbcTemplate.queryForObject(sql, new Object[]{groupId},  new AssignmentGroup.AssignmentGroupMapper());
	}
	
	public int getAssignmentGroupId(AssignmentGroup asgGrp)
	{
		String sql = "SELECT * FROM AssignmentGroups where sectionId = ? and groupName = ? and groupWeight = ?";
		AssignmentGroup asgGrpInfo = this.jdbcTemplate.queryForObject(sql, new Object[]{asgGrp.getSectionId(), asgGrp.getGroupName(), asgGrp.getGroupWeight()}, new AssignmentGroup.AssignmentGroupMapper());
		return asgGrpInfo.getGroupId();
	}
	
	public boolean editAssignmentGroup(AssignmentGroup asgGrp, AssignmentGroup updatedAsgGrp) {
		String sql = "UPDATE AssignmentGroups SET groupName = ?, groupWeight= ? where groupId = ?";
		return this.jdbcTemplate.update(sql, new Object[]{asgGrp.getGroupName(), asgGrp.getGroupWeight(), asgGrp.getGroupId()}) > 0;
	}
	
	public boolean addAssignmentGroup(AssignmentGroup asgGrp) {
		String sql = "INSERT INTO AssignmentGroups (sectionId, groupName, groupWeight) VALUES (?, ?, ?)";
		return this.jdbcTemplate.update(sql, new Object[]{asgGrp.getSectionId(), asgGrp.getGroupName(), asgGrp.getGroupWeight()}) > 0;
	}
	
	public boolean removeAssignmentGroup(Integer asgGrpId)
	{
		String sql = "DELETE FROM AssignmentGroups where groupId = ?";
		return this.jdbcTemplate.update(sql, new Object[]{asgGrpId}) > 0;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SectionAndcourseRecord {
		private Course course;
		private Section section;
	}

}
