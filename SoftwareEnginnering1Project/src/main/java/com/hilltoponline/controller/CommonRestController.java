package com.hilltoponline.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.AssignmentGroup;
import com.hilltoponline.model.Grade;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.model.User;
import com.hilltoponline.repository.AssignmentRepository;
import com.hilltoponline.repository.CourseRepository;
import com.hilltoponline.repository.DepartmentRepository;
import com.hilltoponline.repository.GradeRepository;
import com.hilltoponline.repository.GradeRepository.GradingTable;
import com.hilltoponline.repository.SectionRepository;
import com.hilltoponline.repository.SectionRepository.CurrentSectionRecord;
import com.hilltoponline.repository.TermRepository;
import com.hilltoponline.repository.UserRepository;
import com.hilltoponline.security.CustomUserDetail;

@RestController
@RequestMapping("/")
public class CommonRestController {
	
	@Autowired
	SectionRepository sectionRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	DepartmentRepository deptRepo;
	
	@Autowired
	TermRepository termRepo;
	
	@Autowired
	GradeRepository gradeRepo;
	
	@Autowired
	AssignmentRepository assignmentRepo;
	
	private final static Logger LOG = LoggerFactory.getLogger(CommonRestController.class);

	
	@PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public List<CurrentSectionRecord> getSchedulePage(@AuthenticationPrincipal CustomUserDetail user) {
		  
		  Term term = termRepo.getActiveTerm();
		  
		  List<Section> currentSections = sectionRepo.getSchedule(term, user);
		  List<CurrentSectionRecord> currentSectionRecords = new ArrayList<>();
		  
		  for(Section section : currentSections) {
			  CurrentSectionRecord sectionRecord = new SectionRepository.CurrentSectionRecord();
			  
			  sectionRecord.setSection(section);
			  sectionRecord.setCourse(courseRepo
					  .getCourseByCourseId(section
					  .getCourseId()));
			  sectionRecord.setDept(deptRepo.getDepartmentByDeptId(sectionRecord.getCourse().getDeptId()));
			  
			  currentSectionRecords.add(sectionRecord);
		  }
		  
		  return currentSectionRecords;
	  }

	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping("/getGrades/{sectionId}")
	public GradingTable getGrades(@AuthenticationPrincipal CustomUserDetail user, @PathVariable Integer sectionId) {
		
		Term term = termRepo.getActiveTerm();
		List<Section> currentSections = sectionRepo.getSchedule(term, user);
		Section gradingSection = sectionRepo.getSectionById(sectionId);
		
		//if instructor teaches the section, get the grading table
		if (currentSections.contains(gradingSection)) {
			//get the list of assignments, x axis.
			List<AssignmentGroup> assignmentGroups = assignmentRepo.getAssignmentGroupsBySectionId(gradingSection.getSectionId());
			List<Assignment> assignments =
					assignmentGroups.stream()
					.map(group -> assignmentRepo.getAssignmentsByGroupId(group.getGroupId()))
					.flatMap(List::stream)
					.collect(Collectors.toList());
			//get the students in the section
			List<User> listOfStudents = sectionRepo.getStudentsInSection(gradingSection);
			//get the list of users, y axis;
			Map<Integer, User> students = new HashMap<>(listOfStudents.size());
			
			//populate the rows of users
			Map<Integer, List<Grade>> studentToGradeMap = new HashMap<>(listOfStudents.size());
			for (User student : listOfStudents) {
				//add student to list of students
				students.put(student.getUserId(), userRepo.getMinimalProps(student));
				//put student grades in map
				studentToGradeMap.put(student.getUserId(), 
						assignments.stream().map(asg -> gradeRepo.getGrade(student, asg))
						.collect(Collectors.toList()));
			}
			//return new grading table;
			GradingTable gradingTable = new GradingTable(gradingSection, assignments, assignmentGroups, studentToGradeMap, students);
			
			return gradingTable;

		}
		else {
			return null;
		}
		
	}	
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping("/updateGrades")
	public ResponseEntity<?> updateGrades(@AuthenticationPrincipal CustomUserDetail user, 
			@RequestBody GradingTable gradingTable, BindingResult result) {
	
		for(Integer studentId : gradingTable.getGrades().keySet()){
			boolean updatedStudent = gradeRepo.updateGrades(gradingTable.getGrades().get(studentId));
			if (!updatedStudent){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			};
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}	
	
	
}
