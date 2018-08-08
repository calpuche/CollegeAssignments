package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Grade;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeRepositoryTest {
	@Autowired
	GradeRepository gradeRepository;
	
	@Test
	public void getAllGrades() {
		//get all the grades from the db
		List<Grade> gradesFromDb = gradeRepository.getAllGrades();
		//create a list of grades that I know exist
		List<Grade> staticRoles = Arrays.asList(
			new Grade(1,(float) 98.7,1,2),
			new Grade(2,(float) 22.7,2,2),
			new Grade(3,(float) 76.3,3,2),
			new Grade(4,(float) 100,4,2));
				
			assertThat(gradesFromDb, is(equalTo(staticRoles)));			
	}

	
}
