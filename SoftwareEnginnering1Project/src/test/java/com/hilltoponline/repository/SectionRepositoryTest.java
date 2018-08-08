package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Registration;
import com.hilltoponline.model.Section;
import com.hilltoponline.model.Term;
import com.hilltoponline.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SectionRepositoryTest {

	@Autowired
	SectionRepository sectionRepository;

	@Test
	public void testA_getAllSections() {

		//get all the sections from the db
		List<Section> sectionsFromDb = sectionRepository.getAllSections();

		//create a list of sections that I know exist
		List<Section> staticSections = Arrays.asList(
				new Section(1, 1, 1, 3, "MWF", "10:00a"),
				new Section(2, 2, 1, 3, "TR", "10:00a"),
				new Section(3, 3, 2, 3, "TR", "12:30a"),
				new Section(4, 4, 2, 3, "MWF", "9:00a"),
				new Section(5, 3, 4, 3, "TR", "12:00p"),
				new Section(6, 1, 4, 3, "MWF", "10:00a"),
				new Section(7, 2, 4, 3, "TR", "2:00p"));

		assertThat(sectionsFromDb, is(equalTo(staticSections)));
	}

	@Test
	public void testB_addSection() {

		Section section = new Section(null, 3, 1, 3, "MWF", "2:00p");

		assertTrue(sectionRepository.addSection(section));
	}

	@Test
	public void testC_getAllSectionsAfterAdd() {

		//get all the sections from the db
		List<Section> sectionsFromDb = sectionRepository.getAllSections();

		//create a list of sections that I know exist
		List<Section> staticSections = Arrays.asList(
				new Section(1, 1, 1, 3, "MWF", "10:00a"),
				new Section(2, 2, 1, 3, "TR", "10:00a"),
				new Section(3, 3, 2, 3, "TR", "12:30a"),
				new Section(4, 4, 2, 3, "MWF", "9:00a"),
				new Section(5, 3, 4, 3, "TR", "12:00p"),
				new Section(6, 1, 4, 3, "MWF", "10:00a"),
				new Section(7, 2, 4, 3, "TR", "2:00p"),
				new Section(8, 3, 1, 3, "MWF", "2:00p"));
		assertThat(sectionsFromDb, is(equalTo(staticSections)));
	}

	@Test
	public void testD_removeSectionById() {

		Integer sectionIdToRemove = 8;

		assertTrue(sectionRepository.removeSectionById(sectionIdToRemove));

	}

	@Test
	public void testE_testIfRemoveWorked() {

		//get all the sections from the db
		List<Section> sectionsFromDb = sectionRepository.getAllSections();

		//create a list of sections that I know exist
		List<Section> staticSections = Arrays.asList(
				new Section(1, 1, 1, 3, "MWF", "10:00a"),
				new Section(2, 2, 1, 3, "TR", "10:00a"),
				new Section(3, 3, 2, 3, "TR", "12:30a"),
				new Section(4, 4, 2, 3, "MWF", "9:00a"),
				new Section(5, 3, 4, 3, "TR", "12:00p"),
				new Section(6, 1, 4, 3, "MWF", "10:00a"),
				new Section(7, 2, 4, 3, "TR", "2:00p"));

		assertThat(sectionsFromDb, is(equalTo(staticSections)));
	}

	@Test
	public void testF_getAllSectionsByTerm() {

		Term term = new Term(4, "spring", 2017, true,false);

		Section sectionInTerm = new Section(5, 3, 4, 3, "TR", "12:00p");

		List<Section> sectionsInTerm = sectionRepository.getAllSectionsByTerm(term);

		assertThat(sectionInTerm, is(equalTo(sectionsInTerm.get(0))));
	}

	@Test
	public void testG_viewStudentsInSection() {

		Section section = new Section(3, 3, 2, 3, "TR", "12:30a");

		List<User> studentsInSection = sectionRepository.viewStudentsInSection(section);

		// assert studentsInSection has studentId = 2
		java.sql.Date date = java.sql.Date.valueOf("2017-01-01");
		User studentInSection = new User(2, "student", "$2a$10$j/gvAeSdIlaP5guxElDFzepHfBkT/7CU01aiSExoPq49Ghdp2hTdq", "example", "student", "123-example street", "12345678910", date, 3);
		assertThat(studentsInSection.get(0), is(equalTo(studentInSection)));

	}

	@Test
	public void testH_editSection() {

		Section section = new Section(3, 3, 2, 3, "MWF", "10:11a");

		assertTrue(sectionRepository.editSection(section));

	}

	@Test
	public void testI_dropSection() {

		User user = new User(2, "student", "student", "example", "student", "123-example street", "12345678910", java.sql.Date.valueOf("2017-01-01"), 3);
		Section section = new Section(2, 2, 1, 3, "TR", "10:00a");

		assertTrue(sectionRepository.removeRegistration(new Registration(null, user.getUserId(), section.getSectionId())));
	}

	@Test
	public void testJ_dropStudent() {

		User user = new User(2, "student", "student", "example", "student", "123-example street", "12345678910", java.sql.Date.valueOf("2017-01-01"), 3);
		Section section = new Section(1, 1, 1, 3, "MWF", "10:00a");

		assertTrue(sectionRepository.removeRegistration(new Registration(null, user.getUserId(), section.getSectionId())));
	}

	@Test
	public void testK_getSchedule() {

		Term term = new Term(2, "summer", 2014, false,false);
		User user = new User(2, "student", "student", "example", "student", "123-example street", "12345678910", java.sql.Date.valueOf("2017-01-01"), 3);
		List<Section> schedule = sectionRepository.getSchedule(term, user);

		assertThat(schedule.get(0), is(equalTo(new Section(3, 3, 2, 3, "MWF", "10:11a"))));
	}

	@Test
	public void testL_viewHistory() {

		User user = new User(2, "student", "student", "example", "student", "123-example street", "12345678910", java.sql.Date.valueOf("2017-01-01"), 3);

		List<Section> history = sectionRepository.getSectionHistory(user);

		Section section = new Section(3, 3, 2, 3, "MWF", "10:11a");

		assertThat(history.get(0), is(equalTo(section)));

	}
	
	//registration tests
	@Test
	public void testM_register() {

		Section section = new Section(8, 1, 4, 3, "MWF", "10:00a");
		Section section2 = new Section(9, 2, 4, 3, "TR", "2:00p");
		Section section3 = new Section(10, 3, 4, 3, "MW", "6:00p");

		sectionRepository.addSection(section);
		sectionRepository.addSection(section2);
		sectionRepository.addSection(section3);
		
		assertTrue(sectionRepository.addRegistration(new Registration(null, 2, section.getSectionId())));
		assertTrue(sectionRepository.addRegistration(new Registration(null, 2, section2.getSectionId())));
		assertTrue(sectionRepository.addRegistration(new Registration(null, 2, section3.getSectionId())));

	}

	@Test
	public void testN_getNumberOfStudents() {
		Section section = new Section(6, 1, 4, 3, "MWF", "10:00a");

		assertThat(1, is(equalTo(sectionRepository.getStudentsInSection(section).size())));
	}
	
	@Test
	public void testO_getAllRegistrations() {
		//get all the registrations from the db
		List<Registration> registrationsFromDb = sectionRepository.getAllRegistrations();
		//create a list of registrations that I know exist
		List<Registration> staticRegistrations = Arrays.asList(
				new Registration(3, 2, 3),
				new Registration(4, 2, 6),
				new Registration(5, 2, 7),
				new Registration(6, 2, 5),
				new Registration(7, 2, 8),
				new Registration(8, 2, 9),
				new Registration(9, 2, 10));
		assertThat(registrationsFromDb, is(equalTo(staticRegistrations)));
	}
	
//	@Test 
//	public void testP_testAddRegistration() {
//		Registration registration = new Registration(null, 2, 4);
//		assertTrue(sectionRepository.addRegistration(registration));
//	}
//	
//	@Test
//	public void testQ_removeRegistration() {
//		Registration registration = new Registration(7, 2, 4);
//		
//		assertTrue(sectionRepository.removeRegistration(registration));
//	}
}
