
package com.hilltoponline.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;
import com.hilltoponline.repository.CourseRepository;
import com.hilltoponline.repository.DepartmentRepository;

@Controller
@RequestMapping("/courseCatalog")
public class CourseAndDepartmentController {

	private final static Logger LOG = LoggerFactory.getLogger(CourseAndDepartmentController.class);
	
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@RequestMapping(value = "/viewCourseCatalog", method = RequestMethod.GET)
	public ModelAndView courseCatalogPage(Model model) {

		List<Department> sortedDepartments = departmentRepository.getAllDepartments();
		Map<Department, List<Course>> coursesInDepartmentMap = new LinkedHashMap<Department, List<Course>>();
		//for(Department department : sortedDepartments)
		Department department;
		for(int i=0; i<sortedDepartments.size(); i++)
		{
			department = sortedDepartments.get(i);
			if(courseRepository.getAllCoursesByDepartment(department).size()==0)
				continue;
			else
				coursesInDepartmentMap.put(department, courseRepository.getAllCoursesByDepartment(department));
		}

		model.addAttribute("coursesInDepartmentMap", coursesInDepartmentMap);
		LOG.debug(coursesInDepartmentMap.toString());
		return new ModelAndView("viewCourseCatalog");
	}


	//CourseCatalog Management
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/manageCourseCatalog", method=RequestMethod.GET)
	public ModelAndView getAllCoursesAndDepartmentsGet(ModelMap model,
			@RequestParam(name = "error", required=false, defaultValue="false") boolean error,
			@RequestParam(name = "created", required=false, defaultValue="false") boolean created,
			@RequestParam(name = "edited", required=false, defaultValue="false") boolean edited,
			@RequestParam(name = "removed", required=false, defaultValue="false") boolean removed,
			@RequestParam(name = "removeError", required=false, defaultValue="false") boolean removeError,
			@RequestParam(name = "removedCourse", required=false, defaultValue="false") boolean removedCourse,
			@RequestParam(name = "editedCourse", required=false, defaultValue="false") boolean editedCourse,
			@RequestParam(name = "createdCourse", required=false, defaultValue="false") boolean createdCourse,
			@RequestParam(name = "errorCourse", required=false, defaultValue="false") boolean errorCourse) {

		Map<Department, List<Course>> coursesInDepartmentMap = departmentRepository.getCoursesToDepartmentMap();
		model.addAttribute("coursesInDepartmentMap", coursesInDepartmentMap);
		model.addAttribute("allDepartments", departmentRepository.getAllDepartments());
		model.addAttribute("error", error);
		model.addAttribute("created", created);
		model.addAttribute("edited", edited);
		model.addAttribute("removed", removed);	
		model.addAttribute("removeError", removeError);
		model.addAttribute("removedCourse", removedCourse);
		model.addAttribute("editedCourse", editedCourse);
		model.addAttribute("createdCourse", createdCourse);
		model.addAttribute("errorCourse", errorCourse);
		model.addAttribute("newDepartment", new Department());
		model.addAttribute("newCourse",new Course());
		return new ModelAndView("manageCourseCatalog");
	}




	//add, edit, remove departments
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/addDepartment", method=RequestMethod.POST)
	public ModelAndView addDepartmentPost(@Valid @ModelAttribute("department") Department newDepartment, 
			BindingResult result,  ModelMap model) 
	{
		//return an error page if the input data was invalid
		if (!result.hasErrors() && departmentRepository.addDepartment(newDepartment)) {
			LOG.debug("Adding new department: {}", newDepartment);
			return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?created=true");
		}
		else 
		{
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new Department: {}", errors.length() > 2 ? errors : "SQL Exception");
			model.addAttribute("error",true);
			model.addAttribute("coursesInDepartmentMap", departmentRepository.getCoursesToDepartmentMap());
			return new ModelAndView("manageCourseCatalog", "newDepartment", newDepartment);
		}	
	}

	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editDepartment/{deptId}", method=RequestMethod.GET)
	public ModelAndView editDepartmentGet(@PathVariable Integer deptId, ModelMap model,
			@RequestParam(name = "error", required=false, defaultValue="false") boolean error) 
	{
		model.addAttribute("error",error);
		model.addAttribute("departmentToEdit", departmentRepository.getDepartmentByDeptId(deptId));
		return new ModelAndView("editDepartment");
	}

	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editDepartment", method=RequestMethod.POST)
	public ModelAndView editDepartmentPost(@Valid @ModelAttribute("department") Department departmentToEdit, Department editedDepartment, 
			BindingResult result,  ModelMap model) 
	{
		//return an error page if the input data was invalid
		if (!result.hasErrors() && departmentRepository.editDepartment(departmentToEdit, editedDepartment)) {
			LOG.debug("Updating Department: {}", departmentToEdit);
			return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?edited=true");
		}
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new Department: {}", errors.length() > 2 ? errors : "SQL Exception");
			return new ModelAndView(String.format("redirect:/courseCatalog/editDepartment/%d/?errorDept=true", departmentToEdit.getDeptId()));
		}	
	}


	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/removeDepartment/{deptId}", method=RequestMethod.GET)
	public ModelAndView removeDepartmentGet(@Valid @ModelAttribute("department") Department departmentToDelete, 
			BindingResult result,  ModelMap model) 
	{
		//return an error page if the input data was invalid
		if (!result.hasErrors() && departmentRepository.removeDepartment(departmentToDelete)) {
			LOG.debug("Removing department: {}", departmentToDelete);
			return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?removed=true");
		}
		else 
		{
			String errors = result.getAllErrors().toString();
			LOG.warn("Error removing Department: {}", errors.length() > 2 ? errors : "Cannot delete a Department with connected Courses");
			model.addAttribute("removeError",true);
			model.addAttribute("coursesInDepartmentMap", departmentRepository.getCoursesToDepartmentMap());
			return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?removeError=true");
		}	

	}


	//Add, Edit, Remove Course

	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/addCourse", method=RequestMethod.POST)
	public ModelAndView addCoursePost(@Valid @ModelAttribute("course") Course newCourse, 
			BindingResult result,  ModelMap model) 
	{
		//return an error page if the input data was invalid
		if (!result.hasErrors() && courseRepository.addCourse(newCourse)) {
			LOG.debug("Adding new course: {}", newCourse);
			return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?createdCourse=true");
		}
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new course: {}", errors.length() > 2 ? errors : "SQL Exception");
			model.addAttribute("errorCourseAdd",true);
			model.addAttribute("allDepartments", departmentRepository.getAllDepartments());
			model.addAttribute("coursesInDepartmentMap", departmentRepository.getCoursesToDepartmentMap());
			model.addAttribute("newCourse", newCourse);
			model.addAttribute("newDepartment", new Department());
			return new ModelAndView("manageCourseCatalog");
		}	
	}

	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editCourse/{courseId}", method=RequestMethod.GET)
	public ModelAndView editCourseGet(@PathVariable Integer courseId, ModelMap model,
			@RequestParam(name = "errorCourse", required=false, defaultValue="false") boolean errorCourseEdit) 
	{
		model.addAttribute("errorCourseEdit",errorCourseEdit);
		model.addAttribute("allDepartments", departmentRepository.getAllDepartments());
		model.addAttribute("courseToEdit", courseRepository.getCourseByCourseId(courseId));
		return new ModelAndView("editCourse");
	}

	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editCourse", method=RequestMethod.POST)
	public ModelAndView editCoursePost(@Valid @ModelAttribute("courseToEdit") Course courseToEdit, BindingResult result,  ModelMap model) 
	{
		//return an error page if the input data was invalid
		if (!result.hasErrors() && courseRepository.editCourse(courseToEdit)) {
			LOG.debug("Updating course: {}", courseToEdit);
			return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?editedCourse=true");
		}
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new course: {}", errors.length() > 2 ? errors : "SQL Exception");
			return new ModelAndView(String.format("redirect:/courseCatalog/editCourse/%d/?errorCourse=true", courseToEdit.getCourseId()));
		}	
	}

	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/removeCourse/{courseId}", method=RequestMethod.GET)
	public ModelAndView deleteCoursePost (@PathVariable Integer courseId, ModelMap model) 
	{
		LOG.debug("REMOVING Course: {}",courseRepository.getCourseByCourseId(courseId));
		courseRepository.removeCourse(courseRepository.getCourseByCourseId(courseId));
		return new ModelAndView("redirect:/courseCatalog/manageCourseCatalog?removedCourse=true");
	}



}



