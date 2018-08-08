#drops
DROP TABLE IF EXISTS Grades;

DROP TABLE IF EXISTS Assignments;

DROP TABLE IF EXISTS AssignmentGroups;

DROP TABLE IF EXISTS FinalGrades;

DROP TABLE IF EXISTS Registrations;

DROP TABLE IF EXISTS Sections;

DROP TABLE IF EXISTS Courses;

DROP TABLE IF EXISTS Users;

DROP TABLE IF EXISTS Terms;

DROP TABLE IF EXISTS Departments;

DROP TABLE IF EXISTS Roles;

#creates
CREATE TABLE Roles (
  roleId INTEGER AUTO_INCREMENT,
  role VARCHAR(20) NOT NULL,
  PRIMARY KEY (roleId)
) Engine=InnoDB;

INSERT INTO Roles (roleId, role) VALUES
  (1, 'ROLE_REGISTRAR'),
  (2, 'ROLE_INSTRUCTOR'),
  (3, 'ROLE_STUDENT');

CREATE TABLE Departments (
  deptId INTEGER AUTO_INCREMENT,
  deptName VARCHAR(30),
  deptAbbreviation VARCHAR(10),
  PRIMARY KEY(deptId)
) Engine=InnoDB;

CREATE TABLE Terms (
  termId INTEGER AUTO_INCREMENT,
  termSemester VARCHAR(6),
  termYear NUMERIC(4,0),
  termActive BOOLEAN,
  termOpenForRegistration BOOLEAN,
  PRIMARY KEY(termId)
) Engine=InnoDB;

CREATE TABLE Users (
  userId INTEGER AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL,
  password VARCHAR(64),
  firstName VARCHAR(64) NOT NULL,
  lastName VARCHAR(64) NOT NULL,
  address VARCHAR(64) NOT NULL,
  phone VARCHAR(11) NOT NULL,
  dob DATE NOT NULL,
  roleId INTEGER NOT NULL,
  PRIMARY KEY (userId),
  FOREIGN KEY (roleId)
    REFERENCES Roles(roleId)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  UNIQUE(username)
) Engine=InnoDB;

CREATE TABLE Courses (
  courseId INTEGER AUTO_INCREMENT,
  courseCredits NUMERIC(1),
  courseName VARCHAR(50),
  courseNum NUMERIC(4),
  courseDescription VARCHAR(500),
  deptId INTEGER,
  PRIMARY KEY(courseId),
  FOREIGN KEY(deptId) REFERENCES Departments(deptId)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
) Engine=InnoDB;

CREATE TABLE Sections (
  sectionId INTEGER AUTO_INCREMENT,
  courseId INTEGER,
  termId INTEGER,
  userId INTEGER,
  sectionDay VARCHAR(15),
  sectionTime VARCHAR(15),
  PRIMARY KEY (sectionId),
  FOREIGN KEY (courseId)
    REFERENCES Courses (courseId)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (termId)
    REFERENCES Terms (termId)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (userId)
    REFERENCES Users (userId)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) Engine=InnoDB;

CREATE TABLE Registrations (
  registrationId INTEGER AUTO_INCREMENT,
  userId INTEGER,
  sectionId INTEGER,
  PRIMARY KEY (registrationId),
  FOREIGN KEY (userId)
    REFERENCES Users (userId)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (sectionId)
    REFERENCES Sections (sectionId)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  UNIQUE (userId, sectionId)
) Engine=InnoDB;

CREATE TABLE FinalGrades (
	finalGradeId INTEGER AUTO_INCREMENT,
	userId INTEGER,
	sectionId INTEGER,
	finalGradeGpa DECIMAL(3,2),
	finalGradeLetter VARCHAR(2),
	PRIMARY KEY (finalGradeId),
	FOREIGN KEY (userId)
		REFERENCES Users (userId)
		ON UPDATE CASCADE
		ON DELETE RESTRICT,
	FOREIGN KEY (sectionId)
		REFERENCES Sections (sectionId)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
) Engine=InnoDB;

CREATE TABLE AssignmentGroups (
	groupId INTEGER AUTO_INCREMENT,
	sectionId INTEGER,
	groupName VARCHAR(20),
	groupWeight DECIMAL(3,2),
	PRIMARY KEY (groupId),
	FOREIGN KEY (sectionId)
		REFERENCES Sections (sectionId)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
) Engine=InnoDB;

CREATE TABLE Assignments (
	asgId INTEGER AUTO_INCREMENT,
	groupId INTEGER,
	asgName VARCHAR(20),
	asgWeight DECIMAL(3,2),
	PRIMARY KEY (asgId),
	FOREIGN KEY (groupId)
		REFERENCES AssignmentGroups (groupId)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
) Engine=InnoDB;

CREATE TABLE Grades (
  gradeId INTEGER AUTO_INCREMENT,
  grade NUMERIC(5,2),
  asgId INTEGER,
  userId INTEGER,
  PRIMARY KEY(gradeId),
  FOREIGN KEY(asgId) REFERENCES Assignments(asgId)
   ON UPDATE CASCADE
   ON DELETE RESTRICT,
  FOREIGN KEY(userId) REFERENCES Users(userId)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
) Engine=InnoDB;
