package com.hilltoponline.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;
import com.hilltoponline.model.Registration;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.model.User;
import com.hilltoponline.repository.CourseRepository;
import com.hilltoponline.repository.DepartmentRepository;
import com.hilltoponline.repository.SectionRepository;
import com.hilltoponline.repository.SectionRepository.CurrentSectionRecord;
import com.hilltoponline.repository.SectionRepository.SectionHistoryRecord;
import com.hilltoponline.repository.TermRepository;
import com.hilltoponline.repository.UserRepository;
import com.hilltoponline.security.CustomUserDetail;

@Controller
@RequestMapping("/section")
public class SectionController {

	private final static Logger LOG = LoggerFactory.getLogger(SectionController.class);
	
	@Autowired
	SectionRepository sectionRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	TermRepository termRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DepartmentRepository deptRepo;
	
  @RequestMapping(value = "/viewAllSectionsByTerm", method = RequestMethod.GET)
  public ModelAndView getAllSectionsPage(Model model) {
	   
	  List<Course> allCourses = new ArrayList<Course>();
	  Map<Course, Section> courseToSectionMap = new HashMap<Course, Section>();
	  
	  Term term = termRepo.getActiveTerm();
	  List<Section> allSectionsInTerm = sectionRepo.getAllSectionsByTerm(term);
	  
	  int i = 0;
	  for (Section section : allSectionsInTerm) {
		  allCourses.add(courseRepo.getCourseByCourseId(section.getCourseId()));
		  
		  courseToSectionMap.put(allCourses.get(i), allSectionsInTerm.get(i));
		  
		  i++;
	  }
	  
	  model.addAttribute("term", term);
	  model.addAttribute("courseToSectionMap", courseToSectionMap);
	  
	  return new ModelAndView("viewAllSectionsByTerm");
  }
  
  @PreAuthorize("hasRole('STUDENT')")
  @RequestMapping(value = "/enrollments", method = RequestMethod.GET)
  public ModelAndView getSchedulePage(Model model, @AuthenticationPrincipal CustomUserDetail user,
		  							  @RequestParam(value="dropped", required=false) Boolean dropped) {
	  
	  Term term = termRepo.getActiveTerm();
	  List<Section> currentSections = sectionRepo.getSchedule(term, user);
	  
	  List<CurrentSectionRecord> currentSectionRecords = new ArrayList<>();
	  
	  for(Section section : currentSections) {
		  CurrentSectionRecord sectionRecord = new SectionRepository.CurrentSectionRecord();
		  
		  sectionRecord.setSection(section);
		  sectionRecord.setCourse(courseRepo
				  .getCourseByCourseId(section
				  .getCourseId()));

		  sectionRecord.setDept(deptRepo.getDepartmentByDeptId
				  (courseRepo.getCourseByCourseId(section.getCourseId()).getDeptId()));
		  
		  sectionRecord.setUser(userRepo.getUserById(section.getUserId()));

		  
		  currentSectionRecords.add(sectionRecord);
	  }
	  
	  model.addAttribute("term", term);
	  model.addAttribute("currentSectionRecords", currentSectionRecords);
	  model.addAttribute("dropped", dropped);
	  return new ModelAndView("currentSchedule");
  }
  
  @PreAuthorize("hasRole('STUDENT')")
  @RequestMapping(value = "/enrollments", method = RequestMethod.POST)
  public ModelAndView processDropSectionByStudent(Model model,
		  										  @RequestParam("sectionId") Integer sectionId,
		  										  @RequestParam(value="dropped", required=false) Boolean dropped,
		  										  @AuthenticationPrincipal CustomUserDetail user) {
	  Integer userId = user.getUserId();
	  Registration reg = new Registration(null, userId, sectionId);
	  
	  dropped = sectionRepo.removeRegistration(reg) ? true : false;
	  System.out.println("HELLO");
	  model.addAttribute("dropped", dropped);
	  return new ModelAndView("redirect:/section/enrollments?dropped=" + dropped);
  }
  
  @PreAuthorize("hasRole('INSTRUCTOR')")
  @RequestMapping(value = "/viewSectionHistory", method = RequestMethod.GET)
  public ModelAndView viewSectionHistoryInstructor(Model model, @AuthenticationPrincipal CustomUserDetail user) {
	  
	  List<Section> sectionHistory = sectionRepo.getSectionHistory(user);
	  List<SectionHistoryRecord> sectionHistoryRecords = new ArrayList<SectionHistoryRecord>();
	  List<Term> terms = termRepo.getAllTerms();
	  System.out.println(terms);
	  for(Section section : sectionHistory) {
		  SectionHistoryRecord sec = new SectionRepository.SectionHistoryRecord();
		  
		  sec.setCourseName(courseRepo.getCourseByCourseId(section.getCourseId()).getCourseName());
		  sec.setSection(section);
		  sec.setListUsers(sectionRepo.getStudentsInSection(section));
		  
		  sectionHistoryRecords.add(sec);
	  }
	  
	  Map<Term, List<SectionHistoryRecord>> termToSectionRecordListMap = new LinkedHashMap<>();
	  
	  for (Term term : terms) {
		  List<SectionHistoryRecord> sectionHistories = sectionHistoryRecords.stream()
				  .filter(section -> ( term.getTermId() == section.getSection().getTermId() ))
				  .collect(Collectors.toList());
		  
		  termToSectionRecordListMap.put(term, sectionHistories);
	  }
	  
	  LOG.debug(termToSectionRecordListMap.toString());
	  
	  model.addAttribute("termToSectionRecordListMap", termToSectionRecordListMap);
	  
	  return new ModelAndView("sectionHistory");
  }
  
  @PreAuthorize("hasRole('INSTRUCTOR')")
  @RequestMapping(value = "/sectionDetails", method = RequestMethod.GET)
  public ModelAndView getStudentsInSection(Model model, @AuthenticationPrincipal CustomUserDetail user, 
		  								   @RequestParam("sectionId") Integer sectionId, 
		  								   @RequestParam(value = "dropped", required = false) boolean isDropped) {
	  // get section details from the sectionId
	  Section section = sectionRepo.getSectionById(sectionId);
	  List<User> studentsInSection = sectionRepo.getStudentsInSection(section);
	  
	  String courseName = courseRepo.getCourseByCourseId(section.getCourseId()).getCourseName();
	  
	  SectionHistoryRecord shr = new SectionRepository.SectionHistoryRecord(courseName, section, studentsInSection);
	  
	  Term term = termRepo.getTermById(section.getTermId());
	  model.addAttribute("term", term);
	  model.addAttribute("shr", shr);
	  model.addAttribute("dropped", isDropped);
	  
	  return new ModelAndView("studentsInSection", "reg", new Registration());
  }
  
  @PreAuthorize("hasRole('INSTRUCTOR')")
  @RequestMapping(value = "/sectionDetails", method = RequestMethod.POST)
  public ModelAndView processDropStudent(@Valid @ModelAttribute("reg") Registration reg,
		  								 BindingResult result,  ModelMap model) {
	  sectionRepo.removeRegistration(reg);  
	  return new ModelAndView("redirect:/section/sectionDetails?sectionId="+reg.getSectionId()+"&dropped=true");
  }
  
  @PreAuthorize("hasRole('STUDENT')")
  @RequestMapping(value="/register", method = RequestMethod.GET) 
  public ModelAndView getAllSectionsForRegistration(Model model, 
		  											@RequestParam(value="term", required=false) Integer termId, 
		  											@RequestParam(value="dept", required=false) Integer deptId, 
		  											@RequestParam(value="registered", required = false) Boolean registered) {

	  if(termId != null && deptId != null) {
		  
		  Map<Course, List<CurrentSectionRecord>> courseToSectionsMap = new HashMap<>();
		  List<Course> coursesInDept = courseRepo.getAllCoursesByDepartment(deptRepo.getDepartmentByDeptId(deptId));
		  
		  for(Course course : coursesInDept) {
			  List<CurrentSectionRecord> listSecRec = new ArrayList<>();
			  List<Section> sectionsInCourse = sectionRepo.getSectionsByCourseDeptTerm(course.getCourseId(), deptId, termId);
			  
			  for(Section section : sectionsInCourse) {
				  CurrentSectionRecord secRec = new CurrentSectionRecord();
				  secRec.setCourse(course);
				  secRec.setSection(section);
				  secRec.setUser(userRepo.getUserById(section.getUserId()));
				  listSecRec.add(secRec);
			  }
			  
			  courseToSectionsMap.put(course, listSecRec);
		  }
		  
		  String deptAbbr = deptRepo.getAbbreviationByDepartmentId(deptId);
		  
		  
		  // ---------- DROP DOWN MENU START ---------- //
		  List<Department> allDepts = deptRepo.getSortedDepartmentsList();
		  List<Term> termsOpenForReg = termRepo.termActiveForRegistration();
		  model.addAttribute("allDepts", allDepts);
		  model.addAttribute("termsOpenForReg", termsOpenForReg);
		  // ---------- DROP DOWN MENU END ---------- //
		  
		  model.addAttribute("courseToSectionsMap", courseToSectionsMap);
		  model.addAttribute("deptAbbr", deptAbbr);
		  model.addAttribute("registered", registered);
		  model.addAttribute("termId", termId);
		  model.addAttribute("deptId", deptId);
		  return new ModelAndView("register");
	  }
	  
	  // ---------- DROP DOWN MENU START ---------- //
	  List<Department> allDepts = deptRepo.getSortedDepartmentsList();
	  List<Term> termsOpenForReg = termRepo.termActiveForRegistration();
	  
	  model.addAttribute("allDepts", allDepts);
	  model.addAttribute("termsOpenForReg", termsOpenForReg);
	  // ---------- DROP DOWN MENU END ---------- //
	  registered = null;
	  model.addAttribute("registered", registered);
	  
	  return new ModelAndView("register", "reg", new Registration());
  }
  
  @PreAuthorize("hasRole('STUDENT')")
  @RequestMapping(value="/register", method = RequestMethod.POST)
  public ModelAndView registerStudent(@Valid @ModelAttribute("reg") Registration reg, 
		  							  BindingResult br, ModelMap model, 
		  							  @AuthenticationPrincipal CustomUserDetail user,
		  							  @RequestParam(value = "dept", required = false) Integer deptId, 
		  							  @RequestParam(value = "term", required = false) Integer termId) {
	  reg.setUserId(user.getUserId());

	  return 	sectionRepo.addRegistration(reg) ? 
			   	new ModelAndView("redirect:/section/register?dept=" + deptId + "&term=" + termId + "&registered=true") : 
			   	new ModelAndView("redirect:/section/register?dept=" + deptId + "&term=" + termId + "&registered=false");
  }
  
  @PreAuthorize("hasRole('REGISTRAR')")
  @RequestMapping(value="/manageSections", method = RequestMethod.GET)
  public ModelAndView manageSections(Model model, 
		  							@RequestParam(value="term", required=false) Integer termId, 
		  							@RequestParam(value="dept", required=false) Integer deptId,
		  							@RequestParam(name = "error", required=false, defaultValue="false") boolean error,
		  				            @RequestParam(name = "created", required=false, defaultValue="false") boolean created,
		  				            @RequestParam(name = "edited", required=false, defaultValue="false") boolean edited,
		  				            @RequestParam(name = "deleted", required=false, defaultValue="false") boolean deleted) {
	  model.addAttribute("clickedAdd", false);
	  if(termId != null && deptId != null) {
		  Map<Course, List<CurrentSectionRecord>> courseToSectionsMap = new HashMap<>();
		  List<Course> coursesInDept = courseRepo.getAllCoursesByDepartment(deptRepo.getDepartmentByDeptId(deptId));
		  
		  for(Course course : coursesInDept) {
			  List<CurrentSectionRecord> listSecRec = new ArrayList<>();
			  List<Section> sectionsInCourse = sectionRepo.getSectionsByCourseDeptTerm(course.getCourseId(), deptId, termId);
			  
			  for(Section section : sectionsInCourse) {
				  CurrentSectionRecord secRec = new CurrentSectionRecord();
				  secRec.setCourse(course);
				  secRec.setSection(section);
				  secRec.setUser(userRepo.getUserById(section.getUserId()));
				  listSecRec.add(secRec);
			  }
			  
			  courseToSectionsMap.put(course, listSecRec);
		  }
		  
		  String deptAbbr = deptRepo.getAbbreviationByDepartmentId(deptId);
		  
		  
		  // ---------- DROP DOWN MENU START ---------- //
		  List<Department> allDepts = deptRepo.getAllDepartments();
		  List<Term> termsOpenForReg = new ArrayList<>();
		  for(Term term : termRepo.termActiveForRegistration()){
			  termsOpenForReg.add(term);
		  }

		  model.addAttribute("allDepts", allDepts);
		  model.addAttribute("termsOpenForReg", termsOpenForReg);
		  // ---------- DROP DOWN MENU END ---------- //
		  
		  model.addAttribute("courseToSectionsMap", courseToSectionsMap);
		  model.addAttribute("deptAbbr", deptAbbr);
		  model.addAttribute("termId", termId);
		  model.addAttribute("deptId", deptId);
		  model.addAttribute("deleted", deleted);
		  List<Department> allDepartments = deptRepo.getSortedDepartmentsList();			
		  model.addAttribute("depts", allDepartments);
		  model.addAttribute("clickedOnViewSections", true);
		  return new ModelAndView("manageSections");
	  }
	  
	  // ---------- DROP DOWN MENU START ---------- //
	  List<Department> allDepts = deptRepo.getAllDepartments();
	  //List<Term> termsOpenForReg= termRepo.getTermsOpenForRegistration();
	  List<Term> termsOpenForReg = new ArrayList<>();
	  for(Term term : termRepo.termActiveForRegistration()){
		  termsOpenForReg.add(term);
	  }

	  model.addAttribute("allDepts", allDepts);
	  model.addAttribute("termsOpenForReg", termsOpenForReg);
	  // ---------- DROP DOWN MENU END ---------- //
	  List<Department> allDepartments = deptRepo.getSortedDepartmentsList();
		
	  model.addAttribute("depts", allDepartments);
	  model.addAttribute("added", false);
	  return new ModelAndView("manageSections");
  }
  
  @PreAuthorize("hasRole('REGISTRAR')")
  @RequestMapping(value="/manageSections", method = RequestMethod.POST)
  public ModelAndView deleteSectionPost(@RequestParam(name="dept", required = false) Integer deptId,
		  								@RequestParam(name="term", required = false) Integer termId,
		  								@RequestParam(name="sectionId", required = false) Integer sectionId,
		  								@RequestParam(name="delete", required = false) String delete,
		  								@RequestParam(name="edit", required = false) String edited,
		  								Model model) {
	  if(delete != null) {
		  sectionRepo.removeSectionById(sectionId);
	  }
	  model.addAttribute("clickedAdd", false);
	  return new ModelAndView("redirect:/section/manageSections?dept=" + deptId + "&term=" + termId + "&deleted=true");
  }
  
  
  	@PreAuthorize("hasRole('REGISTRAR')")
  	@RequestMapping(value="/manageSections/addSection", method = RequestMethod.GET)
  	public ModelAndView addSectionGet(Model model,
  									  @RequestParam(value = "chosenDept", required = false) Integer chosenDept,
  									  @RequestParam(value = "chosenCourse", required = false) Integer chosenCourse,
  									  @RequestParam(value = "instructorId", required = false) Integer instructorId,
  									  @RequestParam(value = "sectionDay", required = false) String sectionDay,
  									  @RequestParam(value = "sectionTime", required = false) String sectionTime,
  									  @RequestParam(value = "termId", required = false) Integer termId) {
  		
  		if (chosenDept == null) model.addAttribute("chosenDept", chosenDept);
  		model.addAttribute("added", false);
  		model.addAttribute("clickedAdd", true);
  		List<Department> allDepartments = deptRepo.getSortedDepartmentsList();
  		
  		model.addAttribute("depts", allDepartments);
  		
  		if(chosenDept != null) {
  			model.addAttribute("chosenDept", chosenDept);
  			Department dept = deptRepo.getDepartmentByDeptId(chosenDept);
  			List<Course> coursesInDept = courseRepo.getAllCoursesByDepartment(dept);
  			model.addAttribute("courses", coursesInDept);
  			
  			if(chosenCourse != null) {
  				List<Term> allTerms = termRepo.getAllTerms();
  	  			model.addAttribute("chosenCourse", chosenCourse);
  	  			//List<User> instructorsInDept = sectionRepo.getInstructorsInDepartment(chosenDept);
  	  		List<User> instructorsInDept = sectionRepo.getAllInstructors();
  	  			model.addAttribute("instructors", instructorsInDept);
  	  			model.addAttribute("terms", allTerms);
  			}
  		}
  		
  		if(instructorId != null && chosenCourse != null && termId != null) {
  			
  			Section newSection = new Section();
  			newSection.setCourseId(chosenCourse);
  			newSection.setSectionDay(sectionDay);
  			newSection.setSectionTime(sectionTime);
  			newSection.setUserId(instructorId);
  			newSection.setTermId(termId);
  			if(sectionDay == "" || sectionTime == "") {
  				model.addAttribute("error", true);
  			}
  			else {
  				sectionRepo.addSection(newSection);
  	  			model.addAttribute("added", true);
  			}
  		}
  		return new ModelAndView("manageSections");
  	}
  	
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getUserSectionHomePage(Model model, @AuthenticationPrincipal CustomUserDetail user) {
  	  	return user.getRoleId() == 3
  	  			? new ModelAndView("redirect:/section/enrollments") 
  	  			: new ModelAndView("redirect:/section/viewSectionHistory");
    }
  
}