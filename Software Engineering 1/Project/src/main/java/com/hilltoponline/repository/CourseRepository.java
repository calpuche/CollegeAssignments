package com.hilltoponline.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;

@Repository
public class CourseRepository {

	private JdbcTemplate jdbcTemplate;
	private final static Logger LOG = LoggerFactory.getLogger(CourseRepository.class);
	
	@Autowired
	TermRepository termRepo;
	
	@Autowired
	SectionRepository sectionRepo;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Course> getAllCourses() {
		String sql = "SELECT * FROM Courses";
		return this.sortCourseList(this.jdbcTemplate.query(sql, new Course.CourseMapper()));
	}

	public Course getCourseByCourseId(int courseID)
	{
		String sql = "SELECT * FROM Courses WHERE courseId = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{courseID}, new Course.CourseMapper());
	}
	public Course getCourseByName(String courseName)
	{
		String sql = "SELECT * FROM Courses WHERE courseName = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{courseName}, new Course.CourseMapper());
	}

	public List<Course> getAllCoursesByDepartment(Department dept)
	{
		String sql = "SELECT * FROM Courses WHERE deptId = ?";
		return this.sortCourseList(this.jdbcTemplate.query(sql, new Course.CourseMapper(), dept.getDeptId()));
	}

	public List<Section> getAllSectionsForCourseByTerm(Course course, Term term)
	{
		String sql = "SELECT * FROM Sections WHERE courseId = ? AND termId = ?";
		return jdbcTemplate.query(sql, new Section.SectionMapper(), course.getCourseId(), term.getTermId());
	}

	public List<Section> getSectionsByCourseForActiveTerm(Course course)
	{
		termRepo.getAllTerms();
		Term term = termRepo.getActiveTerm();
		return getAllSectionsForCourseByTerm(course, term);
	}

	public List<Course> getAllCoursesWithNumberCredits(int credits)
	{
		String sql = "SELECT * FROM Courses WHERE courseCredits = " +credits ;
		return this.sortCourseList(this.jdbcTemplate.query(sql, new Course.CourseMapper()));
	}

	public Course getCourseFromSectionId(Integer sectionId)
	{
		//String sql = "SELECT * FROM Courses, Sections WHERE (Sections.sectionId = sectionId) and (Courses.courseId = Sections.courseId)";
		String sql = "SELECT * FROM Courses WHERE courseId in (select courseId from Sections where Sections.sectionId = ?)";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{sectionId}, new Course.CourseMapper());
	}

	public List<Course> getAllCoursesBySectionIDs(List<Integer> sectionID)
	{
		Integer[] sectionIds = (Integer[]) sectionID.toArray();
		List<Course> courses = new ArrayList<Course>();
		for(int i=0; i<sectionIds.length; i++)
		{
			Object match = new Object();
			match = getCourseFromSectionId(sectionIds[0]);
			courses.add((Course) match);
		}
		return courses;
	}

	public boolean addCourse(Course course)
	{
		String sql = "INSERT INTO Courses(courseId, courseCredits, courseName, courseNum, courseDescription, deptId) VAlUES (?,?,?,?,?,?)";
		int rows = this.jdbcTemplate.update(sql, course.getCourseId(), course.getCourseCredits(), course.getCourseName(), course.getCourseNum(), course.getCourseDescription(), course.getDeptId());
		return rows > 0;
	}

	public boolean removeCourse(Course course)
	{
		//int courseId = course.getCourseId();
		String sql = "DELETE FROM Courses WHERE courseId = ?";
		int rows = this.jdbcTemplate.update(sql, course.getCourseId());
		return rows > 0;
	}

	public boolean editCourse(Course course)
	{
		String sql = "UPDATE Courses SET courseCredits = ?, courseName = ?, courseNum = ?, courseDescription = ?, deptId = ? WHERE courseId = ?";
		int rows = this.jdbcTemplate.update(sql,  course.getCourseCredits(), course.getCourseName(), course.getCourseNum(), course.getCourseDescription(), course.getDeptId(), course.getCourseId());
		return rows > 0;

	}

	//Sorting
	public List<Course> sortCourseList(List<Course> courses) 
	{
		Course[] courseArray = courses.toArray(new Course[courses.size()]);
		sortCourses(courseArray);
		return Arrays.asList(courseArray);
	}

	private static Course[] sortCourses(Course[] list) 
	{
		if (list.length <= 1) {
			return list;
		}
		Course[] first = new Course[list.length / 2];
		Course[] second = new Course[list.length - first.length];
		System.arraycopy(list, 0, first, 0, first.length);
		System.arraycopy(list, first.length, second, 0, second.length);
		sortCourses(first);
		sortCourses(second);
		merge(first, second, list);
		return list;
	}

	private static void merge(Course[] first, Course[] second, Course[] result) 
	{
		int iFirst = 0;
		int iSecond = 0;
		int iMerged = 0;
		while (iFirst < first.length && iSecond < second.length) 
		{
			if (first[iFirst].getDeptId()==second[iSecond].getDeptId()) 
			{
				if(first[iFirst].getCourseNum() < second[iSecond].getCourseNum())
				{
					result[iMerged] = first[iFirst];
					iFirst++;
				}
				else
				{
					result[iMerged] = second[iSecond];
					iSecond++;
				}
			} 
			else if (first[iFirst].getDeptId()<second[iSecond].getDeptId())
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

	private boolean contains(Course theCourse)
	{
		boolean rv = false;
		List<Course> courses = this.getAllCourses();

		for(int i = 0; i<courses.size(); i++)
		{
			if(theCourse.getCourseId()==courses.get(i).getCourseId()
					&& theCourse.getCourseName().equals(courses.get(i).getCourseName())
					&& theCourse.getCourseNum() == courses.get(i).getCourseNum())
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

	public List<Course> sortCourseListForDepartment(Department department) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSortedCourseList() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean editCourse(Course courseToEdit, Course editedCourse) {
		// TODO Auto-generated method stub
		return false;
	}

}
