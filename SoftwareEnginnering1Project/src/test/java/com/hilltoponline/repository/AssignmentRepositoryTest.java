package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.AssignmentGroup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssignmentRepositoryTest {
	@Autowired
	AssignmentRepository asgRepository;
	
	@Test
	public void getAllAssignments() {
		
		//get all the asgGroups from the db
		List<Assignment> asgsFromDb = asgRepository.getAllAssignments();
		
		//create a list of asgGroups that I know exist
		List<Assignment> staticAsgs = Arrays.asList(
			new Assignment(1, 1, "Exam 1", 0.50f),
			new Assignment(2, 1, "Exam 2", 0.50f),
			new Assignment(3, 2, "Homework 1", 0.10f),
			new Assignment(4, 2, "Homework 2", 0.15f));
				
			assertThat(asgsFromDb, is(equalTo(staticAsgs)));			
	}
	
	@Test
	public void getAssignmentsByGroupId() {
		
		//get all the asgs from the db with groupId = 1
		List<Assignment> asgsFromDb = asgRepository.getAssignmentsByGroupId(1);
		
		//create a list of asgs that I know exist
		List<Assignment> staticAsgs = Arrays.asList(
				new Assignment(1, 1, "Exam 1", (float) 0.50),
				new Assignment(2, 1, "Exam 2", (float) 0.50));
				
		assertThat(asgsFromDb, is(equalTo(staticAsgs)));			
	}
	
	@Test
	public void getAssignmentByAsgId() {
		Assignment asg = asgRepository.getAssignmentByAsgId(1);
		Assignment staticAsg = new Assignment(1, 1, "Exam 1", (float) 0.50);
		assertThat(asg, is(equalTo(staticAsg)));
	}
	
	
	
	@Test
	public void removeAssignment(){
		Assignment asg = new Assignment(2, 2,"Assignment 1",(float) 0.10);
		assert(asgRepository.removeAssignment(2));
	}
	
	@Test
	public void getAllAssignmentGroups() {
		
		//get all the asgGroups from the db
		List<AssignmentGroup> asgGroupsFromDb = asgRepository.getAllAssignmentGroups();
		
		//create a list of asgGroups that I know exist
		List<AssignmentGroup> staticAsgGroups = Arrays.asList(
			new AssignmentGroup(1, 5, "Exams", 0.60f),
			new AssignmentGroup(2, 5, "Homework", 0.40f));
			
			assertThat(asgGroupsFromDb, is(equalTo(staticAsgGroups)));			
	}
	
	@Test
	public void getAssignmentsGroupsBySectionId() {
		
		List<AssignmentGroup> asgGroupsFromDb = asgRepository.getAssignmentGroupsBySectionId(5);
		List<AssignmentGroup> staticAsgGroups = Arrays.asList(
				new AssignmentGroup(1, 5, "Exams", (float) 0.60),
				new AssignmentGroup(2, 5, "Homework", (float) 0.40));
				
			assertThat(asgGroupsFromDb, is(equalTo(staticAsgGroups)));			
	}
	
	@Test
	public void getAssignmentGroupByGroupId() {
		AssignmentGroup asgGroup = asgRepository.getAssignmentGroupsByGroupId(1);
		AssignmentGroup staticAsgGroup = new AssignmentGroup(1, 5, "Exams", (float) 0.60);
		assertThat(asgGroup, is(equalTo(staticAsgGroup)));
	}
	
	@Test
	public void addAssignmentGroup(){
		AssignmentGroup newAsgGroup = new AssignmentGroup(0,4,"InClass",(float) 0.30);
		asgRepository.addAssignmentGroup(newAsgGroup);
		int asgGrpId = asgRepository.getAssignmentGroupId(newAsgGroup);
		newAsgGroup.setGroupId(asgGrpId);
		AssignmentGroup asgGroup = asgRepository.getAssignmentGroupsByGroupId(asgGrpId);
		assertThat(asgGroup, is(equalTo(newAsgGroup)));
	}
	
	@Test
	public void editAssignmentGroup(){
		AssignmentGroup newAsgGrp = new AssignmentGroup(0, 4,"Homework",(float) 0.30);
		boolean added = asgRepository.addAssignmentGroup(newAsgGrp);
		int groupId = asgRepository.getAssignmentGroupId(newAsgGrp);
		System.out.println(groupId);
		AssignmentGroup staticAsgGrp = new AssignmentGroup(groupId, 4,"Homework",(float) 0.30);
		newAsgGrp.setGroupId(groupId);
		if (added)
		{
			asgRepository.editAssignmentGroup(newAsgGrp, staticAsgGrp);
			AssignmentGroup asgGrp = asgRepository.getAssignmentGroupsByGroupId(groupId);
			asgRepository.removeAssignmentGroup(asgGrp.getGroupId());
			assertThat(asgGrp,is(equalTo(staticAsgGrp)));
		}
		else
		{
			fail();
		}
		/*AssignmentGroup staticAsgGroup = new AssignmentGroup(6,4,"Homework",(float) 0.20);
		asgGroupRepository.addAssignmentGroup(6,4,"InClass",(float) 0.30);
		asgGroupRepository.editAssignmentGroup(6, "Homework", (float) 0.20);
		AssignmentGroup asgGroup = asgGroupRepository.getAssignmentGroupsByGroupId(6);
		asgGroupRepository.removeAssignmentGroup(6);
		assertThat(asgGroup,is(equalTo(staticAsgGroup)));*/
	}
	
	@Test
	public void removeAssignmentGroup(){
		AssignmentGroup asgGrp = new AssignmentGroup(2, 2,"Homework",(float) 0.30);
		assert(asgRepository.removeAssignmentGroup(asgGrp.getGroupId()));
	}


}



