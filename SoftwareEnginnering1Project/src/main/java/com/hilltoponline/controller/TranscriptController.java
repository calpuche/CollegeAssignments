package com.hilltoponline.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.model.User;
import com.hilltoponline.model.FinalGrade;
import com.hilltoponline.repository.CourseRepository;
import com.hilltoponline.repository.SectionRepository;
import com.hilltoponline.repository.FinalGradeRepository;
import com.hilltoponline.repository.FinalGradeRepository.FinalGradeRecord;
import com.hilltoponline.repository.SectionRepository.CurrentSectionRecord;
import com.hilltoponline.repository.SectionRepository.SectionHistoryRecord;
import com.hilltoponline.repository.TermRepository;
import com.hilltoponline.repository.UserRepository;
import com.hilltoponline.security.CustomUserDetail;

@Controller
@RequestMapping("/grading")
public class TranscriptController {

	private final static Logger LOG = LoggerFactory.getLogger(TranscriptController.class);
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	TermRepository termRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FinalGradeRepository finalRepository;
	
	@PreAuthorize("hasRole('STUDENT')")
	@RequestMapping(value = "/viewTranscript", method = RequestMethod.GET)
	public ModelAndView viewTranscript(Model model, @AuthenticationPrincipal CustomUserDetail user) {
		  
		  List<Section> sectionHistory = sectionRepository.getSectionHistory(user);
		  List<FinalGradeRecord> gradeRecords = new ArrayList<FinalGradeRecord>();
		  List<Term> terms = termRepository.getAllTerms();
		  
		  if (sectionHistory.size() == 0)
			  System.out.println("NONE");
		  
		  for(Section section : sectionHistory) {
			  
			  if(finalRepository.getFinalGrade(user, section) != null)
			  {
			 
			  FinalGradeRecord grade = new FinalGradeRepository.FinalGradeRecord();
			  
			  grade.setSection(section);
			  grade.setCourseName(courseRepository.getCourseByCourseId(section.getCourseId()).getCourseName());
			  grade.setLetterGrade(finalRepository.getFinalGrade(user, section).getFinalGradeLetter());
			  grade.setGpaPoints(finalRepository.getFinalGrade(user, section).getFinalGradeGpa());
			  gradeRecords.add(grade);
			  }
			  
		  }
		  
		  Map<Term, List<FinalGradeRecord>> termToFinalGradeRecordMap = new HashMap<Term, List<FinalGradeRecord>>();
		  
		  for (Term term : terms) {
			  
			  List<FinalGradeRecord> transcript = gradeRecords.stream()
					  .filter(section -> ( term.getTermId() == section.getSection().getTermId() ))
					  .collect(Collectors.toList());
			  
			  if(transcript.size() > 0) {
				  termToFinalGradeRecordMap.put(term, transcript);
			  }
			  
		  }
		  
		  LOG.info(termToFinalGradeRecordMap.toString());
		  
		  model.addAttribute("termToFinalGradeRecordMap", termToFinalGradeRecordMap);
		  
		  float gpa = calculateGpa(user);
		  model.addAttribute("gpa", gpa);
		  
		  return new ModelAndView("viewTranscript");
		  
	  }
 
///////////////************Added for gpa
 public float calculateGpa(User user)
	{
		List<FinalGrade> finalGrades = finalRepository.getAllFinalGradesForUser(user);
		float points;
		int credits;
		int totalCredits = 0;
		float gpa = 0;
		if(finalGrades.size() != 0)
		{
			for(int i = 0; i < finalGrades.size(); i++)
			{
				points = finalGrades.get(i).getFinalGradeGpa();
				credits = courseRepository.getCourseFromSectionId(finalGrades.get(i).getSectionId()).getCourseCredits();
				totalCredits += credits;
				gpa += (points * credits);
			}
		}
		gpa = gpa/totalCredits;
		float newGpa = (float) (Math.round(gpa*100.0)/100.0);
		return newGpa;
	}
	
}