package com.hilltoponline.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Assignment;
import com.hilltoponline.model.AssignmentGroup;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.repository.AssignmentRepository;
import com.hilltoponline.repository.AssignmentRepository.SectionAndcourseRecord;
import com.hilltoponline.repository.SectionRepository.CurrentSectionRecord;
import com.hilltoponline.repository.CourseRepository;
import com.hilltoponline.repository.SectionRepository;
import com.hilltoponline.repository.TermRepository;
import com.hilltoponline.repository.UserRepository;
import com.hilltoponline.security.CustomUserDetail;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

	private final static Logger LOG = LoggerFactory.getLogger(AssignmentController.class);
	
	@Autowired
	AssignmentRepository asgRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	TermRepository termRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value = "/viewAssignments", method = RequestMethod.GET)
	public ModelAndView getAllAssignmentsPage(Model model,  @AuthenticationPrincipal CustomUserDetail user,
			@RequestParam(name = "sectionId", required=false) Integer sectionId,
			
			@RequestParam(name = "errorAddGroup", required=false, defaultValue="false") boolean errorAddGroup,
            @RequestParam(name = "successAddGroup", required=false, defaultValue="false") boolean successAddGroup,
            @RequestParam(name = "errorAddAsg", required=false, defaultValue="false") boolean errorAddAsg,
            @RequestParam(name = "successAddAsg", required=false, defaultValue="false") boolean successAddAsg,
            
            @RequestParam(name = "errorDelGroup", required=false, defaultValue="false") boolean errorDelGroup,
            @RequestParam(name = "successDelGroup", required=false, defaultValue="false") boolean successDelGroup,
            @RequestParam(name = "errorDelAsg", required=false, defaultValue="false") boolean errorDelAsg,
            @RequestParam(name = "successDelAsg", required=false, defaultValue="false") boolean successDelAsg) {
			

		if(sectionId != null) {
			Section section = sectionRepository.getSectionById(sectionId);
			  Map<AssignmentGroup, List<Assignment>> groupsToAssignmentsMap = new HashMap<>();
			  for(AssignmentGroup asgg : asgRepository.getAssignmentGroupsBySectionId(section.getSectionId())){
				  groupsToAssignmentsMap.put(asgg, asgRepository.getAssignmentsByGroupId(asgg.getGroupId()));
			  }
			  
			  model.addAttribute("term", termRepository.getActiveTerm());
			  model.addAttribute("section", new SectionAndcourseRecord(courseRepo.getCourseByCourseId(section.getCourseId()), section));
			  model.addAttribute("groupsToAssignmentsMap", groupsToAssignmentsMap);
			  model.addAttribute("newAssignmentGroup", new AssignmentGroup());
			  model.addAttribute("newAssignment", new Assignment());
			  model.addAttribute("errorAddGroup", errorAddGroup);
			  model.addAttribute("successAddGroup", successAddGroup);
			  model.addAttribute("errorAddAsg", errorAddAsg);
			  model.addAttribute("successAddAsg", successAddAsg);
			  model.addAttribute("errorDelGroup", errorDelGroup);
			  model.addAttribute("successDelGroup", successDelGroup);
			  model.addAttribute("errorDelAsg", errorDelAsg);
			  model.addAttribute("successDelAsg", successDelAsg);
			  
			  return new ModelAndView("viewAssignments");	
		} else {
			
			List<Section> currentSections = sectionRepository.getSchedule(termRepository.getActiveTerm(), user);
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
			return new ModelAndView("viewAssignmentsDropdown");
		}
	    
	}
	 

	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/addGroup",method = RequestMethod.POST)
	public ModelAndView addNewGroup(Model model,@Valid @ModelAttribute AssignmentGroup newGroup, BindingResult result,
			@RequestParam(name = "sectionId", required=true) Integer sectionId){
		
		//return an error page if the input data was invalid
		if (!result.hasErrors() && asgRepository.addAssignmentGroup(newGroup)) {
			LOG.debug("Adding new group: {}", newGroup);
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&successAddGroup=true");
	    }
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new group {}.\n {}", newGroup, errors.length() > 2 ? errors : "SQL Exception");
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&errorAddGroup=true");
		}	
	}
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/delGroup",method = RequestMethod.GET)
	public ModelAndView deleteGroup(Model model,
			@RequestParam(name = "assignmentGroupId", required=true) Integer assignmentGroupId,
			@RequestParam(name = "sectionId", required=true) Integer sectionId){
		
		//return an error page if the input data was invalid
		if (asgRepository.removeAssignmentGroup(assignmentGroupId)) {
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&successDelGroup=true");
	    }
		else {
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&errorDelGroup=true");
		}	
	}
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/addAsg",method = RequestMethod.POST)
	public ModelAndView addNewAsg(Model model,@Valid @ModelAttribute Assignment newAsg, BindingResult result,
			@RequestParam(name = "sectionId", required=true) Integer sectionId){
		
		//return an error page if the input data was invalid
		if (!result.hasErrors() && asgRepository.addAssignment(newAsg)) {
			LOG.debug("Adding new asg: {}", newAsg);
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&successAddAsg=true");
	    }
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new asg: {}.\n{}", newAsg, errors.length() > 2 ? errors : "SQL Exception");
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&errorAddAsg=true");
		}	
	}
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@RequestMapping(value="/delAsg",method = RequestMethod.GET)
	public ModelAndView deleteAssignment(Model model,
			@RequestParam(name = "assignmentId", required=true) Integer assignmentId,
			@RequestParam(name = "sectionId", required=true) Integer sectionId){
		
		//return an error page if the input data was invalid
		if (asgRepository.removeAssignment(assignmentId)) {
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&successDelAsg=true");
	    }
		else {
			return new ModelAndView("redirect:/assignment/viewAssignments/?sectionId="+sectionId+"&errorDelAsg=true");
		}	
	}
}