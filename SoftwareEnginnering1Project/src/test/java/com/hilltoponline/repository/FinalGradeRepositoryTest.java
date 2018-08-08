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

import com.hilltoponline.model.FinalGrade;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinalGradeRepositoryTest {
	@Autowired
	FinalGradeRepository finalGradeRepository;
	
	@Test
	public void submitFinalGrade(){
		//TODO need final grade object for this to take in
		FinalGrade finalGrade = new FinalGrade(5,4,1,(float) 3.0,"B");
		
		assert(finalGradeRepository.submitFinalGrade(finalGrade));
 
	}
	
	@Test
	public void getAllFinalGrades() {
		
		//get all the finalGrades from the db
		List<FinalGrade> finalGradesFromDb = finalGradeRepository.getAllFinalGrades();
		
		//create a list of finalGrades that I know exist
		List<FinalGrade> staticFinalGrades = Arrays.asList(
			new FinalGrade(1, 2, 5, 4.00f, "A"),
			new FinalGrade(2, 2, 6, 3.00f, "B"),
			new FinalGrade(3, 2, 7, 2.00f, "C"),
			new FinalGrade(4, 4, 1, 3.0f, "B"));
				
			assertThat(finalGradesFromDb, is(equalTo(staticFinalGrades)));			
	}
	
}

