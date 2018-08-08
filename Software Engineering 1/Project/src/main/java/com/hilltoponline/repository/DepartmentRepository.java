package com.hilltoponline.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;


@Repository
public class DepartmentRepository {

	private JdbcTemplate jdbcTemplate;
	private final static Logger LOG = LoggerFactory.getLogger(DepartmentRepository.class);

	@Autowired
	CourseRepository courseRepo;

	@Autowired
	public void setDataSource(DataSource dataSource) 
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//generic list of all departments
	public List<Department> getAllDepartments() {
		String sql = "SELECT * FROM Departments";
		return this.sortDeparmentsList(this.jdbcTemplate.query(sql, new Department.DepartmentMapper()));
	}

	//get single department by any one value
	public Department getDepartmentByDeptId(int deptId)
	{
		String sql = "SELECT * FROM Departments WHERE deptId = ?";	
		return this.jdbcTemplate.queryForObject(sql, new Object[]{deptId}, new Department.DepartmentMapper());
	}

	public Department getDepartmentByName(String deptName)
	{
		String sql = "SELECT * FROM Departments WHERE deptName = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{deptName}, new Department.DepartmentMapper());
	}

	public Department getDepartmentByAbbreviation(String abbreviation)
	{
		String sql = "SELECT * FROM Departments WHERE deptAbbreviation = ?" ;
		return this.jdbcTemplate.queryForObject(sql, new Object[]{abbreviation}, new Department.DepartmentMapper());
	}


	//Getting the Abbreviation by either other value
	public String getAbbreviationByDepartmentId(Integer deptId)
	{
		String sql = "SELECT * FROM Departments WHERE deptId = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{deptId}, new Department.DepartmentMapper()).getDeptAbbreviation();
	}

	public String getAbbreviationByDepartmentName(String deptName)
	{
		//String sql = "SELECT deptAbbreviation FROM Departments WHERE deptName = ?";
		//return this.jdbcTemplate.queryForObject(sql, new Object[]{deptName}, new Department.DepartmentMapper()).getDeptAbbreviation();
		String sql = "SELECT * FROM Departments WHERE deptName = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{deptName}, new Department.DepartmentMapper()).getDeptAbbreviation();
	}


	public boolean addDepartment(Department dept)
	{
		String sql = "INSERT INTO Departments(deptId, deptName, deptAbbreviation) VALUES (?,?,?)";
		int rows = this.jdbcTemplate.update(sql, dept.getDeptId(), this.formatDepartmentName(dept.getDeptName()), dept.getDeptAbbreviation().trim().toUpperCase());
		return rows > 0;
	}

	public boolean removeDepartment(Department dept)
	{
		if(courseRepo.getAllCoursesByDepartment(dept).isEmpty())
		{
			String sql = "DELETE FROM Departments WHERE deptId = ?";
			int rows = this.jdbcTemplate.update(sql, dept.getDeptId());
			return rows > 0;
		}
		else
			return false;
	}

	public boolean editDepartment(Department dept, Department editedDept)
	{
		String deptName = this.formatDepartmentName(editedDept.getDeptName().trim());
		String deptAbbreviation = editedDept.getDeptAbbreviation().trim().toUpperCase();
		String sql = "UPDATE Departments SET deptName = ?, deptAbbreviation = ? WHERE deptId = ?";
		int rows = jdbcTemplate.update(sql, deptName, deptAbbreviation, dept.getDeptId());
		return rows > 0;
	}

	//Finding which department a course is in
	public Department getDepartmentByCourse(Course course)
	{
		int courseId = course.getCourseId();
		String sql = "SELECT deptName FROM Departments, Courses WHERE courseId = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{courseId}, new Department.DepartmentMapper());
	}

	public Map<Department, List<Course>> getCoursesToDepartmentMap()
	{
		Map<Department, List<Course>> coursesInDepartmentMap = new LinkedHashMap<Department, List<Course>>();
		List<Department> departments = getAllDepartments();
		for(int i = 0; i<departments.size(); i++)
		{
			List<Course> coursesInDepartment = new ArrayList<Course>();
			coursesInDepartment = courseRepo.getAllCoursesByDepartment(departments.get(i));
			coursesInDepartmentMap.put(departments.get(i), coursesInDepartment);
		}
		return coursesInDepartmentMap;
	}

	public List<Course> getCoursesInDepartmentList(Department dept)
	{
		return courseRepo.getAllCoursesByDepartment(dept);
	}

		
	public Integer addCourseToDepartment(Course course, Integer deptId)
	{
		if(this.containsCourse(course, deptId ))
			return 1;
		else if(this.courseFormatCorrect(course))
			return 2;
		else
		{
			courseRepo.addCourse(course);
			return 3;
		}
	}
	
	

	//Sorting
	public List<Department> sortDeparmentsList(List<Department> depts)
	{
		Department[] departments = this.sortDepartments(depts.toArray(new Department[depts.size()]));
		return Arrays.asList(departments);

	}

	public boolean contains(Department theDept)
	{
		boolean rv = false;
		List<Department> depts = this.getAllDepartments();

		for(int i = 0; i<depts.size(); i++)
		{
			if(theDept.getDeptName().equals(depts.get(i).getDeptName())
					&& theDept.getDeptAbbreviation().equals(depts.get(i).getDeptAbbreviation()))
			{
				rv = true;
			}
			else
			{
				rv = false;
				continue;
			}
		}
		return rv;
	}

	private Department[] sortDepartments(Department[] list) 
	{
		if (list.length <= 1) {
			return list;
		}
		Department[] first = new Department[list.length / 2];
		Department[] second = new Department[list.length - first.length];
		System.arraycopy(list, 0, first, 0, first.length);
		System.arraycopy(list, first.length, second, 0, second.length);
		sortDepartments(first);
		sortDepartments(second);
		merge(first, second, list);
		return list;
	}

	private static void merge(Department[] first, Department[] second, Department[] result) 
	{
		int iFirst = 0;
		int iSecond = 0;
		int iMerged = 0;
		while (iFirst < first.length && iSecond < second.length) 
		{
			if (first[iFirst].getDeptName().compareTo(second[iSecond].getDeptName()) < 0) 
			{
				result[iMerged] = first[iFirst];
				iFirst++;
			} 
			else
			{
				result[iMerged] = second[iSecond];
				iSecond++;
			}
			iMerged++;
		}
		System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
		System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
	}


	private boolean containsCourse(Course course, Integer deptId)
	{
		if(courseRepo.getAllCoursesByDepartment(this.getDepartmentByDeptId(deptId)).contains(course))
			return false;
		else
			return true;
	}

	private boolean courseFormatCorrect(Course course)
	{
		if(course.getCourseNum().toString().length()!=4 
				|| course.getCourseCredits().toString().length()!=1 
				|| course.getCourseName().equals("") 
				|| course.getCourseDescription().equals(""))
			return false;
		else
			return true;
	}

	private String formatDepartmentName(String deptName)
	{
		deptName.substring(0, 0).toUpperCase();
		for(int i=1; i<deptName.length(); i++)
		{
			if(deptName.substring(i, i)==" ")
				deptName.substring(i+1, i+1).toUpperCase();
		}
		return deptName;
	}

	public List<Department> getSortedDepartmentsList() {
		return getAllDepartments();
	}

}
