package com.hilltoponline.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.AssignmentGroup;
import com.hilltoponline.model.FinalGrade;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.model.User;
import com.hilltoponline.repository.AssignmentRepository;
import com.hilltoponline.repository.CourseRepository;
import com.hilltoponline.repository.FinalGradeRepository;
import com.hilltoponline.repository.GradeRepository;
import com.hilltoponline.repository.SectionRepository;
import com.hilltoponline.repository.SectionRepository.CurrentSectionRecord;
import com.hilltoponline.repository.TermRepository;
import com.hilltoponline.security.CustomUserDetail;

@Controller
@RequestMapping("/grade")
public class GradeController {
	private final static Logger LOG = LoggerFactory.getLogger(GradeController.class);
	@Autowired
	GradeRepository gradeRepo;
	
	@Autowired
	FinalGradeRepository finalGradeRepo;
	
	@Autowired
	SectionRepository sectionRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired	
	TermRepository termRepo;
	
	@Autowired
	AssignmentRepository assignmentRepo;
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/viewGradingTable", method=RequestMethod.GET)
	public ModelAndView viewGradingTableList(ModelMap model, @AuthenticationPrincipal CustomUserDetail user,
			@RequestParam(name = "sectionId", required=false) Integer sectionId) {
		
		if(sectionId == null){
			Term term = termRepo.getActiveTerm();
			  
			  List<Section> currentSections = sectionRepo.getSchedule(term, user);
			  List<CurrentSectionRecord> currentSectionRecords = new ArrayList<>();
			  
			  for(Section section : currentSections) {
				  CurrentSectionRecord sectionRecord = new SectionRepository.CurrentSectionRecord();
				  
				  sectionRecord.setSection(section);
				  sectionRecord.setCourse(courseRepo
						  .getCourseByCourseId(section
						  .getCourseId()));
				  
				  currentSectionRecords.add(sectionRecord);
			  }
			model.addAttribute("sectionList", currentSectionRecords);
			return new ModelAndView("assignmentGradingTableDropdown");
		} else {
			return new ModelAndView("assignmentGradingTable");
		}
		
	}
	
	@PreAuthorize("hasRole('STUDENT')")
	@RequestMapping(value="/myGrades",method=RequestMethod.GET)
	public ModelAndView getGradesByStudent(Model model,/* how to get a user */ 
			@AuthenticationPrincipal CustomUserDetail user,
			@RequestParam(name = "course", required=false) Integer courseId){
			List<Section> currentSections = sectionRepo.getSchedule(termRepo.getActiveTerm(), user);
		Map<String,Integer> courseWithKey =  new HashMap<String,Integer>();
		for(Section section : currentSections) {
			courseWithKey.put(courseRepo.getCourseFromSectionId(section.getSectionId()).getCourseName(), section.getSectionId());
		}
		model.addAttribute("courseWithKey",courseWithKey);
		if(courseId!=null){
			model.addAttribute("courseId", courseId);
			//System.out.println("User Id: " + user.getUserId() + " Chosen Class Id: " + courseId + " Term ID: " + termRepo.getActiveTerm().getTermId());
			
			List<AssignmentGroup> assignmentGroupsList = assignmentRepo.getAssignmentGroupsBySectionId(courseId);
			//System.out.println("assignment groups" + assignmentGroupsList.toString());
			Map<AssignmentGroup,List<Assignment>> assignmentGroupsWithAssignments =  new HashMap<AssignmentGroup,List<Assignment>>();
			for(AssignmentGroup assignmentGroup:assignmentGroupsList){
				assignmentGroupsWithAssignments.put(assignmentGroup, assignmentRepo.getAssignmentsByGroupId(assignmentGroup.getGroupId()));
			}
			Map<Assignment,Float> assignmentWithGrade =  new HashMap<Assignment,Float>();
			for (AssignmentGroup assignmentGroup : assignmentGroupsWithAssignments.keySet()) {
			    // gets the value
			    List<Assignment> value = assignmentGroupsWithAssignments.get(assignmentGroup);
			    // checks for null value
			    if (value != null) {
			        // iterates over String elements of value
			        for (Assignment assignment : value) {
			            // checks for null 
			            if (assignment != null) {
			            	if(gradeRepo.getGrade(user, assignment)!=null){
			                assignmentWithGrade.put(assignment,gradeRepo.getGrade(user, assignment).getGrade());
			            	}
			            	else{
				                assignmentWithGrade.put(assignment,(float) -1.0);
			            	}
			            }
			        }
			    }
			}
			//System.out.println(assignmentGroupsWithAssignments.toString());
			//System.out.println(assignmentWithGrade.toString());
			model.addAttribute("assignmentGroupsWithAssignments",assignmentGroupsWithAssignments);
			model.addAttribute("assignmentWithGrade",assignmentWithGrade);
		} 
	
		return new ModelAndView("myGrades");
	}	
	
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/finalGrades",method=RequestMethod.GET)
	public ModelAndView finalGrades(Model model,
			@AuthenticationPrincipal CustomUserDetail user,
			@RequestParam(name = "sectionId", required=false) Integer sectionId,
			@RequestParam(name="submittedFinalGrade",required=true,defaultValue="false")boolean submittedFinalGrade,
			@RequestParam(name="error",required=true,defaultValue="false")boolean error){
		model.addAttribute("submittedFinalGrade", submittedFinalGrade);
		model.addAttribute("error", error);
		List<Section> currentSections = sectionRepo.getSchedule(termRepo.getActiveTerm(), user);
		Map<String,Integer> courseWithSection =  new HashMap<String,Integer>();
		for(Section section : currentSections) {
			courseWithSection.put(courseRepo.getCourseFromSectionId(section.getSectionId()).getCourseName(), section.getSectionId());
		}
		
		model.addAttribute("courseWithSection",courseWithSection);
		if(sectionId!=null){
			//System.out.println("Got Course Name" + sectionId);
			List<User> studentsList = new ArrayList<>();
			studentsList = sectionRepo.getStudentsInSection(sectionRepo.getSectionBySectionId(sectionId));
			model.addAttribute("studentsList", studentsList);
			//System.out.println("students list: " + studentsList);
		}
		
		return new ModelAndView("viewFinalGrades");
	}
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/submitFinalGrades",method = RequestMethod.POST)
	public ModelAndView submitFinalGrades(Model model, FinalGrade finalGrade
			){
		System.out.println(finalGrade.toString());
		String studentGrade = finalGrade.getFinalGradeLetter() + " ";
		if(studentGrade.charAt(0)=='A' || studentGrade.charAt(0)=='a'){
			if(studentGrade.charAt(1)=='-'){
				finalGrade.setFinalGradeGpa((float) 3.67);
			}
			else{
				finalGrade.setFinalGradeGpa((float) 4.0);
			}
		}
		else if(studentGrade.charAt(0)=='B' || studentGrade.charAt(0)=='b'){
			if(studentGrade.charAt(1)=='+'){
				finalGrade.setFinalGradeGpa((float) 3.33);
			}
			else if(studentGrade.charAt(1)=='-'){
				finalGrade.setFinalGradeGpa((float) 2.67);
			}
			else{
				finalGrade.setFinalGradeGpa((float) 3.0);
			}
		}
		else if(studentGrade.charAt(0)=='C' || studentGrade.charAt(0)=='c'){
			if(studentGrade.charAt(1)=='+'){
				finalGrade.setFinalGradeGpa((float) 2.33);
			}
			else{
				finalGrade.setFinalGradeGpa((float) 2.0);
			}
		}
		else if(studentGrade.charAt(0)=='D' || studentGrade.charAt(0)=='d'){
			finalGrade.setFinalGradeGpa((float) 1.0);
		}
		else if(studentGrade.charAt(0)=='D' || studentGrade.charAt(0)=='f'){
			finalGrade.setFinalGradeGpa((float) 0.0);
		}
		else{
			return new ModelAndView("redirect:/grade/finalGrades/?error=true");
		}
		System.out.println(finalGrade.toString());
		finalGradeRepo.submitFinalGrade(finalGrade);
		return new ModelAndView("redirect:/grade/finalGrades/?submittedFinalGrade=true");
	}
	
}	
