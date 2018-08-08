package com.hilltoponline.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hilltoponline.model.Role;
import com.hilltoponline.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	
	private long time = 0;
	
	@Autowired
	UserRepository testUserRepo;
	
	@Autowired
	private void setupDate() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YYYY");
		try {
			time = formatter.parse("01/01/2017").getTime();
		} catch (ParseException e) {
			assertThat(true, is(equalTo(false)));
		}
	}
	
	@Test
	public void getAllUsers() {
		//get the Users that currently exist in the repo
		List<User> usersFromRepo = testUserRepo.getAllUsers();
		//these are the users I think are in the repo
		List<User> staticUsers = Arrays.asList(
				new User(2, "student", "$2a$10$j/gvAeSdIlaP5guxElDFzepHfBkT/7CU01aiSExoPq49Ghdp2hTdq", "example", "student", "123-example street", "12345678910", new Date(time), 3),
				new User(3, "instructor", "$2a$10$X8kaahH/jniByWRr.6vi/ehU4ljzaIo8vYf5/fqcDcEJcVA30edvy", "example", "instructor", "123-example street", "12345678910", new Date(time), 2),
				new User(4, "registrar", "$2a$10$xHlqR4x2RzuaVfIEQs4BG.bZdSf1NkqGepyA6mkrUYX.uVSeUrwBW", "example", "resgistrar", "123-example street", "12345678910", new Date(time), 1),
				new User(5, "claimable",						 null								, "claimable", "user", "123 example street", "12345678910", new Date(time), 1));
		assertThat(usersFromRepo, is(equalTo(staticUsers)));
	}
	
	@Test
	public void getUserByUsername() {
		User studentUser = testUserRepo.getUserByUsername("student");
		User statisStudentUser = new User(2, "student", "$2a$10$j/gvAeSdIlaP5guxElDFzepHfBkT/7CU01aiSExoPq49Ghdp2hTdq", "example", "student", "123-example street", "12345678910", new Date(time), 3);
		assertThat(studentUser, is(equalTo(statisStudentUser)));
	}
	
	@Test
	public void getUserById() {
		User studentUser = testUserRepo.getUserById(2);
		User statisStudentUser = new User(2, "student", "$2a$10$j/gvAeSdIlaP5guxElDFzepHfBkT/7CU01aiSExoPq49Ghdp2hTdq", "example", "student", "123-example street", "12345678910", new Date(time), 3);
		assertThat(studentUser, is(equalTo(statisStudentUser)));
	}
	
	@Test
	public void getAllRoles() {
		//get all the roles from the db
		List<Role> rolesFromDb = testUserRepo.getAllRoles();
		//create a list of roles that I know exist
		List<Role> staticRoles = Arrays.asList(
				new Role(1, "ROLE_REGISTRAR"),
				new Role(2, "ROLE_INSTRUCTOR"),
				new Role(3, "ROLE_STUDENT"));
		assertThat(rolesFromDb, is(equalTo(staticRoles)));
	}
	
	@Test
	public void getRoleById() {
		Role studentRole = testUserRepo.getRoleById(3);
		Role staticStudentRole = new Role(3, "ROLE_STUDENT");
		assertThat(studentRole, is(equalTo(staticStudentRole)));
	}
	
}
