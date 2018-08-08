DROP TABLE testResults;
DROP TABLE testRunsSut;
DROP TABLE systemsUnderTest;
DROP TABLE tests;

CREATE TABLE tests(
  testID INTEGER AUTO_INCREMENT NOT NULL,
  testName VARCHAR(50) NOT NULL,
  scriptName VARCHAR(50) NOT NULL,
  dailyFrequenceCount INTEGER,
  PRIMARY KEY(testID)
)Engine=InnoDB;

CREATE TABLE systemsUnderTest(
sutID INTEGER AUTO_INCREMENT NOT NULL,
systemName VARCHAR(50),
operatingSystem VARCHAR(50),
hardwareDescription VARCHAR(250),
systemDescription VARCHAR(250),
location VARCHAR(50),
PRIMARY KEY(sutID)
)Engine=InnoDB;

CREATE TABLE testResults(
  resultID INTEGER AUTO_INCREMENT NOT NULL,
  result VARCHAR(15),
  startTime TIMESTAMP,
  endTime TIMESTAMP,
  errorLog VARCHAR(500),
  emailed BOOLEAN,
  testID INTEGER,
  sutID INTEGER,
  PRIMARY KEY(resultID),
  FOREIGN KEY(testID) REFERENCES tests(testID),
  FOREIGN KEY(sutID) REFERENCES systemsUnderTest(sutID)
)Engine=InnoDB;
