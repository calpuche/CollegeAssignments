package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Term;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TermRepositoryTest {
	@Autowired
	TermRepository termRepository;
	
	@Test
	public void getAllTerms(){
		//get all the terms from the db
		List<Term> termsFromDb = termRepository.getAllTerms();
		//create a list of terms that I know exist
		List<Term> staticRoles = Arrays.asList(
				new Term(5,"Summer",2017,false,true),
				new Term(4,"Spring",2017,true,true),
				new Term(3,"Fall",2014,false,false),
				new Term(2,"Summer",2014,false,false),
				new Term(1,"Spring",2014,false,false)
				);
		
		
		System.out.println(termsFromDb);
		assertThat(termsFromDb, is(equalTo(staticRoles)));
	}
	
	@Test
	public void addTerm(){
		Term term = new Term(5,"Summer",2017,false,true);
		assert(termRepository.addTerm(term));
	}
	
	@Test
	public void editTerm(){
		//create new term
		Term term = new Term(6,"Fall",2012,false,false);
		boolean isTrue = termRepository.addTerm(term);
		//check that it updates
		if(isTrue){
			//update info
		Term updatedTermInfo = new Term(6,"Summer",2013,false,false);		
		System.out.println(updatedTermInfo.getTermSemester()+ " " + updatedTermInfo.getTermId()+ " "+updatedTermInfo.getTermYear());
		assert(termRepository.editTerm(updatedTermInfo));
		System.out.println(term.getTermSemester()+ " " + term.getTermId()+ " "+term.getTermYear());
		}
		else{
			fail();
		}
	}
	
	@Test
	public void removeTerm(){
		Term term = new Term(2,"Summer",2014,false,false);
		assert(termRepository.removeTerm(term));
		
	}
	
	@Test
	public void getActiveTerm(){
		//create term instance to match what is in the database
		Term term = new Term(4,"Spring",2017,true,true);
		Term currentTerm = termRepository.getActiveTerm();
		assertThat(currentTerm, is(equalTo(term)));
	}
	@Test
	public void getTermById(){
		Term term = new Term(2,"Summer",2014,false,false);
		Term searchedTerm = termRepository.getTermById(2);
		assertThat(term, is(equalTo(searchedTerm)));
	}
	
	@Test
	public void termActiveForRegistration(){
		Term term = new Term(4,"Spring",2017,true,true);
		List<Term> searchedTerm = termRepository.termActiveForRegistration();
		assertThat(term, is(equalTo(searchedTerm.get(0))));
		
	}
	
}
