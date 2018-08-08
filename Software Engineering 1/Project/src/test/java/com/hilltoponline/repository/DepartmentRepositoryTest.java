package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentRepositoryTest {

	private long time = 0;

	@Autowired
	DepartmentRepository testDepartmentRepo;
	//CourseRepository testCourseRepo;
	//DepartmentRepository staticRepository;


	@Test
	public void aGetAllDepartments() 
	{
		List<Department> DepartmentsFromRepo = testDepartmentRepo.getAllDepartments();
		List<Department> staticDepartments = Arrays.asList(
				new Department(1, "Computer Science", "COSC"),
				new Department(2, "Biology", "BIOL"),
				new Department(3, "Chemistry", "CHEM"),
				new Department(4, "Astronomy", "ASTR"),
				new Department(5, "History", "HIST"),
				new Department(6, "English", "ENGL"),
				new Department(7, "Foreign Languages", "LANG"),
				new Department(8, "Art", "ARTS"),
				new Department(9, "Mathematics", "MATH"),
				new Department(10, "Education", "EDUC"),
				new Department(11, "Theater", "THAR"));

		staticDepartments = testDepartmentRepo.sortDeparmentsList(staticDepartments);
		assertThat(DepartmentsFromRepo, is(equalTo(staticDepartments)));
	}

	@Test
	public void bGetDepartmentByAbbreviation() {
		Department dept = testDepartmentRepo.getDepartmentByAbbreviation("COSC");
		Department dept2 = testDepartmentRepo.getDepartmentByAbbreviation("ENGL");
		assertThat(dept, is(equalTo(new Department(1, "Computer Science", "COSC"))));
		assertThat(dept2, is(equalTo(new Department(6, "English", "ENGL"))));
	}

	@Test
	public void cGetAbbreviationByDepartment()
	{
		List<Department> departmentsFromRepo = testDepartmentRepo.getAllDepartments();
		String[] abbreviations = new String[departmentsFromRepo.size()];
		for(int k = 0; k<abbreviations.length; k++)
			abbreviations[k] = departmentsFromRepo.get(k).getDeptAbbreviation();
		for(int i=0; i<abbreviations.length; i++)
		{
			assertThat(departmentsFromRepo.get(i).getDeptAbbreviation(), is(equalTo(abbreviations[i])));
		}
	}

	@Test
	public void dGetDepartmentByCourseId()
	{
		Course theCourse = new Course(1, 3, "Software 1", 3339, "Software Engineering Description", 1);
		Department dept = testDepartmentRepo.getDepartmentByDeptId(theCourse.getDeptId());

		assertThat(dept, is(equalTo(new Department(1, "Computer Science", "COSC"))));

	}
	
	@Test
	public void eAddDepartmentRemoveDepartmentTest() throws SQLException
	{

		Department deptToAdd = new Department(12, "Psychology", "PSYC");
		assert(testDepartmentRepo.addDepartment(deptToAdd));
		List<Department> depts = testDepartmentRepo.getAllDepartments();
		Department deptToRemove = new Department(12, "Psychology", "PSYC");
		assert(testDepartmentRepo.removeDepartment(deptToRemove));
	}

	@Test
	public void gEditDepartment()
	{
		Department deptToEdit = testDepartmentRepo.getDepartmentByDeptId(2);
		Department editedDept =  new Department(2, "Bio", "BIOL");
		assert(testDepartmentRepo.editDepartment(deptToEdit, editedDept));
		testDepartmentRepo.editDepartment(testDepartmentRepo.getDepartmentByDeptId(2), new Department(2, "Biology", "BIOL"));
		assertThat(testDepartmentRepo.getDepartmentByDeptId(2).getDeptName(), is(equalTo("Biology")));
	}

	@Test
	public void hTestSort()
	{
		List<Department> dept = testDepartmentRepo.getAllDepartments();
		for(int j=0; j<dept.size()-1; j++)
			assert(dept.get(j).getDeptName().compareTo(dept.get(j+1).getDeptName())<0);
	}
	
	@Test
	public void iTestGetDeptByAbb()
	{
		String abb = "COSC";
		Department dept = testDepartmentRepo.getDepartmentByAbbreviation(abb);
		assert(dept.getDeptId() == 1);
	}
	
	@Test
	public void eTestCoursesInDepartmentMap()
	{
		List<Department> departments= testDepartmentRepo.getAllDepartments();
		Map<Department, List<Course>> map = new LinkedHashMap<Department, List<Course>>();
		map = testDepartmentRepo.getCoursesToDepartmentMap();
		for(int i=0; i<departments.size(); i++)
		{
			List<Course> coursesForDept = map.get(departments.get(i));
			for(int j = 0; j<coursesForDept.size(); j++)
				assertThat(coursesForDept, is(equalTo(testDepartmentRepo.getCoursesInDepartmentList(departments.get(i)))));
		}



	}
	
}
