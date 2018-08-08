INSERT INTO Users (userId, username, password, firstName, lastName, address, phone, dob, roleId) VALUES
  	(2, 'student', '$2a$10$j/gvAeSdIlaP5guxElDFzepHfBkT/7CU01aiSExoPq49Ghdp2hTdq', 'example', 'student', '123-example street', '12345678910', '2017-01-01', 3),
  	(3, 'instructor', '$2a$10$X8kaahH/jniByWRr.6vi/ehU4ljzaIo8vYf5/fqcDcEJcVA30edvy', 'example', 'instructor', '123-example street', '12345678910', '2017-01-01', 2),
  	(4, 'registrar', '$2a$10$xHlqR4x2RzuaVfIEQs4BG.bZdSf1NkqGepyA6mkrUYX.uVSeUrwBW', 'example', 'resgistrar', '123-example street', '12345678910', '2017-01-01', 1),
  	(5, 'claimable', null, 'claimable', 'user', '123 example street', '12345678910', '2017-01-01', 1);

INSERT INTO Departments (deptId, deptName, deptAbbreviation) VALUES
	(1, 'Computer Science', 'COSC'),
	(2, 'Biology', 'BIOL'),
	(3, 'Chemistry', 'CHEM'),
	(4, 'Astronomy', 'ASTR'),
	(5, 'History', 'HIST'),
	(6, 'English', 'ENGL'),
	(7, 'Foreign Languages', 'LANG'),
	(8, 'Art', 'ARTS'),
	(9, 'Mathematics', 'MATH'),
	(10, 'Education', 'EDUC'),
	(11, 'Theater', 'THAR');

INSERT INTO Terms VALUES
  (1,'Spring',2014,false,false),
  (2,'Summer',2014,false,false),
  (3,'Fall',2014,false,false),
  (4,'Spring',2017,true,true);

INSERT INTO Courses (courseId, courseCredits, courseName, courseNum, courseDescription, deptId) VALUES
	(1, 4, 'Programming Concepts 1', 1323, 'An introduction to object-oriented programming using Java Programming Language. Three hours lecture with a one hour lab.', 1),
	(2, 4, 'Programming Concepts 2', 2325, 'A continuation of Programming Concepts 1. This Course focuses on developing advanced strategies in computer programming. Three hours lecture with a one hour lab. Must have passed COSC1323 with C or better.', 1),
	(3, 3, 'Computer Organization/Architecture', 2528, 'This course focuses on the hardware components that make up a computer and how to use those components in programming. To illustrate this, students will learn and create programs using Assembly Language. Three hour lecture.', 1),
	(4, 3, 'Algorithms and Data Structures', 3327, 'This course focuses on the use of efficient programming algorithms and the various data structures available to the programmer. Three hour lecture. Must have passed COSC2325 with a C or better', 1),
	(5, 4, 'Databases', 3337, 'This course focuses on relational databases using mySQL. Three hour lecture. Must have passes COSC2325 with a C or better', 1),
	(6, 4, 'Software Engineering', 3339, 'This course focuses on the process of creating a software from start to finish. Students will work in teams on an assigned project for the duration of the semester. Three hour lecture and one hour group meeting. Must have passed COSC3327 and COSC3337 with a C or higher', 1),
	(7, 4, 'Senior', 4157, 'This course is a semester long project in which each student must design and develop an approved software project. Three hours lecture with one hour research. Must have passed COSC3339 with a C or better.', 1),
	(8, 3, 'Introduction to Biology', 1215, 'This course is a general introduction to Biology and focuses on the fundamental concepts of Biology as a Natural Science. Three hour lecture.', 2),
	(9, 3, 'Plant Biology', 2345, 'This course focus on the biology of plants. Three hour lecture. Must have passed BIOL1215 with C or better.', 2),
	(10, 3, 'Anatomy and Physiology', 2450, 'This course focuses on human anatomy and looks at the various body structures in-depth. Three hour lecture. Must have passed BIOL1215 with C or better.', 2),
	(11, 3, 'Vertebrate Biology', 3257, 'This course is an in-depth exploration of the biology of all vertebrates. Three hour lecture. Must have passed BIOL2450 with C or better.', 2),
	(12, 4, 'Cell Biology', 4437, 'This course explores the biology of cells and focuses on how fundamental this understanding is, particularly in medicine. Three hour lecture with one hour lab. Must have passed BIOL3257 with C or better.', 2),
	(13, 3, 'dChem1', 1120, 'c1', 3),
	(14, 3, 'astr1', 2244, 'a1', 4),
	(15, 3, 'Hist1', 2380, 'h1', 5),
	(16, 3, 'Composition', 1456, 'e1', 6),
	(17, 3, 'French', 1864, 'f1', 7),
	(18, 3, 'Painting 1', 3356, 'art1', 8),
	(19, 3, 'Algebra', 2365, 'm1', 9),
	(20, 3, 'Edu1', 1100, 'edu1', 10);

INSERT INTO Sections (sectionId, courseId, termId, userId, sectionDay, sectionTime) VALUES
  	(1, 1, 1, 3, 'MWF', '10:00a'),
	(2, 2, 1, 3, 'TR', '10:00a'),
	(3, 3, 2, 3, 'TR', '12:30a'),
	(4, 4, 2, 3, 'MWF', '9:00a'),
	(5, 3, 4, 3, 'TR', '12:00p'),
	(6, 1, 4, 3, 'MWF', '10:00a'),
	(7, 2, 4, 3, 'TR', '2:00p');

INSERT INTO Registrations (registrationId, userId, sectionId) VALUES
	(1, 2, 1),
	(2, 2, 2),
	(3, 2, 3),
	(4, 2, 6),
	(5, 2, 7),
	(6, 2, 5);

INSERT INTO FinalGrades (finalGradeId, userId, sectionId, finalGradeGpa, finalGradeLetter) VALUES
	(1, 2, 5, 4.00, 'A'),
	(2, 2, 6, 3.00, 'B'),
	(3, 2, 7, 2.00, 'C');

INSERT INTO AssignmentGroups (groupId, sectionId, groupName, groupWeight) VALUES
	(1, 5, 'Exams', 0.60),
	(2, 5, 'Homework', 0.40);

INSERT INTO Assignments (groupId, asgName, asgWeight) VALUES
	(1, 'Exam 1', 0.50),
	(1, 'Exam 2', 0.50),
	(2, 'Homework 1', 0.10),
	(2, 'Homework 2', 0.15);
	
INSERT INTO Grades (grade, asgId, userId) VALUES
  	(98.7, 1, 2),
 	(22.7, 2, 2),
  	(76.3, 3, 2),
  	(100, 4, 2);

