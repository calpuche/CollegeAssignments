package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Course;
import com.hilltoponline.model.Department;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRepositoryTest {

	private long time = 0;

	@Autowired
	CourseRepository testCourseRepo;

	@Autowired
	DepartmentRepository testDeptRepo;

	@Autowired
	SectionRepository testSectionRepo;

	@Autowired
	TermRepository termRepo;

	List<Course> staticCourses = Arrays.asList(
			new Course(1, 4, "Programming Concepts 1", 1323, "An introduction to object-oriented programming using Java Programming Language. Three hours lecture with a one hour lab.", 1),
			new Course(2, 4, "Programming Concepts 2", 2325, "A continuation of Programming Concepts 1. This Course focuses on developing advanced strategies in computer programming. Three hours lecture with a one hour lab. Must have passed COSC1323 with C or better.", 1),
			new Course(3, 3, "Computer Organization/Architecture", 2528, "This course focuses on the hardware components that make up a computer and how to use those components in programming. To illustrate this, students will learn and create programs using Assembly Language. Three hour lecture.", 1),
			new Course(4, 3, "Algorithms and Data Structures", 3327, "This course focuses on the use of efficient programming algorithms and the various data structures available to the programmer. Three hour lecture. Must have passed COSC2325 with a C or better", 1),
			new Course(5, 4, "Databases", 3337, "This course focuses on relational databases using mySQL. Three hour lecture. Must have passes COSC2325 with a C or better", 1),
			new Course(6, 4, "Software Engineering", 3339, "This course focuses on the process of creating a software from start to finish. Students will work in teams on an assigned project for the duration of the semester. Three hour lecture and one hour group meeting. Must have passed COSC3327 and COSC3337 with a C or higher", 1),
			new Course(7, 4, "Senior", 4157, "This course is a semester long project in which each student must design and develop an approved software project. Three hours lecture with one hour research. Must have passed COSC3339 with a C or better.", 1),
			new Course(8, 3, "Introduction to Biology", 1215, "This course is a general introduction to Biology and focuses on the fundamental concepts of Biology as a Natural Science. Three hour lecture.", 2),
			new Course(9, 3, "Plant Biology", 2345, "This course focus on the biology of plants. Three hour lecture. Must have passed BIOL1215 with C or better.", 2),
			new Course(10, 3, "Anatomy and Physiology", 2450, "This course focuses on human anatomy and looks at the various body structures in-depth. Three hour lecture. Must have passed BIOL1215 with C or better.", 2),
			new Course(11, 3, "Vertebrate Biology", 3257, "This course is an in-depth exploration of the biology of all vertebrates. Three hour lecture. Must have passed BIOL2450 with C or better.", 2),
			new Course(12, 4, "Cell Biology", 4437, "This course explores the biology of cells and focuses on how fundamental this understanding is, particularly in medicine. Three hour lecture with one hour lab. Must have passed BIOL3257 with C or better.", 2),
			new Course(13, 3, "dChem1", 1120, "c1", 3),
			new Course(14, 3, "astr1", 2244, "a1", 4),
			new Course(15, 3, "Hist1", 2380, "h1", 5),
			new Course(16, 3, "Composition", 1456, "e1", 6),
			new Course(17, 3, "French", 1864, "f1", 7),
			new Course(18, 3, "Painting 1", 3356, "art1", 8),
			new Course(19, 3, "Algebra", 2365, "m1", 9),
			new Course(20, 3, "Edu1", 1100, "edu1", 10));
/*
			@Test
			public void aTestCourseReposiotry()
			{
				//Testing getAllCourses
				List<Course> coursesFromRepo = testCourseRepo.getAllCourses();
				assertThat(coursesFromRepo, is(equalTo(staticCourses)));

				//Testing add, remove, edit and getCourseByCourseId methods
				Course newCourse = new Course(21, 3, "Stellar Astronomy", 2357, "ASTR Description", 4);
				testCourseRepo.addCourse(newCourse);
				coursesFromRepo = testCourseRepo.getAllCourses();
				assert(coursesFromRepo.contains(newCourse));
				testCourseRepo.removeCourse(newCourse);
				coursesFromRepo = testCourseRepo.getAllCourses();
				testCourseRepo.sortCourseList(staticCourses);
				assertThat(coursesFromRepo, is(equalTo(staticCourses)));
				assertThat(testCourseRepo.getCourseByCourseId(4), is(equalTo(staticCourses.get(3))));
				Course editCourse = new Course(13, 3, "Introduction to Chemistry", 1120, "This course is an introduction to Chemistry. Three hour lecture.", 3);
				testCourseRepo.editCourse(testCourseRepo.getCourseByCourseId(13), editCourse);
				coursesFromRepo = testCourseRepo.getAllCourses();
				assertThat(testCourseRepo.getCourseByCourseId(13), is(equalTo(editCourse)));
				testCourseRepo.editCourse(testCourseRepo.getCourseByCourseId(13), staticCourses.get(12));
				coursesFromRepo = testCourseRepo.getAllCourses();
				assertThat(testCourseRepo.getCourseByCourseId(13), is(equalTo(new Course(13, 3, "dChem1", 1120, "c1", 3))));
				assertThat(coursesFromRepo, is(equalTo(staticCourses)));


				assertThat(testCourseRepo.getCourseByName("dChem1"), is(equalTo(new Course(13, 3, "dChem1", 1120, "c1", 3))));

				List<Course> coursesWithNumCredits = testCourseRepo.getAllCoursesWithNumberCredits(3);
				assert(coursesWithNumCredits.size() == 14);

				List<Section> sections = Arrays.asList(
						new Section(1, 1, 2, 1, "TR", "11:00a"),
						new Section(2, 1, 1, 1, "TR", "12:00p"),
						new Section(3, 1, 3, 1, "TR", "1:00p"),
						new Section(4, 3, 2, 1, "TR", "11:00a"),
						new Section(5, 3, 1, 2, "MWF", "11:00a"));

				List<Course> courses = testCourseRepo.getAllCourses();

				Course test1 = testCourseRepo.getCourseByCourseId(sections.get(1).getCourseId());
				Course test2 = testCourseRepo.getCourseByCourseId(sections.get(4).getCourseId());
				assertThat(test1, is(equalTo(courses.get(0))));
				assertThat(test2, is(equalTo(courses.get(2))));

			}
*/
			@Test
			public void bTestGetSectionsForCourseByTerm()
			{
				Course course = new Course(4, 1, "Senior Research", 4157, "Research Description", 1);
				Term term = new Term(2,"Summer",2014,false,false);
				List<Section> sections = testCourseRepo.getAllSectionsForCourseByTerm(course, term);
				assert(sections.size() == 1);
			}

			@Test
			public void cTestAllCoursesByDepartment()
			{
				List<Department> depts= testDeptRepo.getAllDepartments();
				Department dept = testDeptRepo.getDepartmentByAbbreviation("COSC");
				assert(dept.getDeptId()==1);
				List<Course> courses = testCourseRepo.getAllCoursesByDepartment(dept);
				assert(courses.size() == 7);

			}

			@Test
			public void dTestSortCourses()
			{
				Department dept = testDeptRepo.getDepartmentByDeptId(1);
				List<Course> allCourses = testCourseRepo.getAllCoursesByDepartment(dept);
				for(int i = 0; i<allCourses.size()-1; i++)
					assert(allCourses.get(i).getDeptId()<allCourses.get(i+1).getDeptId() 
							|| (allCourses.get(i).getDeptId()==allCourses.get(i+1).getDeptId()
							&&allCourses.get(i).getCourseNum()<allCourses.get(i+1).getCourseNum()));
			}

		



}
