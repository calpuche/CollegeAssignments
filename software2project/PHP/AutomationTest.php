<?php
// AutomationTest
// Provides an example of how to open a connection to a database and retrieve
// test information from the database.
// Returns test information in an array, which can easily be converted to JSON

require "./DBConnection.php";

Class AutomationTest {

	private $test = array(
		"testName" => '',
		"scriptName" => '');


	// getNextTest()
	// Fetches the test information from the database.  This is very limited in functionality,
	// in that it does not mark  the test as "in progress"
	//
	// After calling getNextTest(), the user should call getTest() to obtain all the test information
	// or call getTestName() for the test description, or call getScriptName for just the test script
	// file name
	public function getNextTest($sut) {

        $conn = new DBConnection();
        $db = $conn->dbConnect();

        if ($db == NULL) {
            $err = array("ERROR" => "Cannot connect to database");
            return $err;
        }

        $sql = "select testName, scriptName from tests";
        $rs = $db->Execute($sql);

        if ($rs == false) {
            $err = array("ERROR" => "No results from database");
            return $err;
        }
        
        while (!$rs->EOF) {
            $this->test["testName"] = $rs->fields["testName"];
            $this->test["scriptName"] = $rs->fields["scriptName"];
            $rs->MoveNext();
        }
	}
    
    public function putResults($resultFromTest,$startTime,$endTime,$errorLog,$emailed,$testID,$sut){
        $conn = new DBConnection();
        $db = $conn->dbConnect();
        if ($db == NULL) {
            $err = array("ERROR" => "Cannot connect to database");
            return $err;
        }
        
        if(   !((preg_match('/SUCCESS/',$resultFromTest)) or (preg_match('/FAILURE/',$resultFromTest))) ){
            $err = array("ERROR" => "Invalid result");
            return $err;
        }
        else if(is_null($startTime)){
            $err = array("ERROR" => "Start time cannot be null");
            return $err;
        }
        else if(is_null($endTime)){
            $err = array("ERROR" => "End time cannot be null");
            return $err;
        }
        else if(is_null($emailed)){
            $err = array("ERROR" => "Email Boolean cannot be null");
            return $err;
        }
         else if(is_null($errorLog)){
            $err = array("ERROR" => "Error log cannot be null");
            return $err;
        }
        $sql = "select testID from tests";
		$result=mysql_query($sql);
   		$testList = array();
    	while($row=mysql_fetch_array($result))
    		{
      		$testList[]=$row['testID'];
     		}
        if(!in_array($testID, $testList)){
            $err = array("ERROR" => "ERROR, Test ID not in database");
            return $err;
        }
        else if(is_null($testID)){
            $err = array("ERROR" => "Test ID cannot be null");
            return $err;
        }
        
         $sql = "select sutID from systemsUnderTest";
		$result=mysql_query($sql);
   		$sutList = array();
    	while($row=mysql_fetch_array($result))
    		{
      		$sutList[]=$row['sutID'];
     		}
        if(!in_array($sut, $sutList)){
            $err = array("ERROR" => "ERROR, SUT ID not in database");
            return $err;
        }
        else if(is_null($sut)){
            $err = array("ERROR" => "SUT ID cannot be null");
            return $err;
        }

       $sql = "insert into testResults VALUES (null,'".$resultFromTest."','". $startTime. "','". $endTime. "','".$errorLog."','".$emailed."','".$testID. "','".$sut."')";
       //print $sql;
       $rs = $db->Execute($sql);
        
        if ($rs == false) {
            $err = array("ERROR" => "Could not add results to database");
            return $err;
        }
        
    }

	// Return the entire array
	//
	public function getTest($sut){
	    $sutList = array(
		"sutID" => '');
	    
	    $conn = new DBConnection();
        $db = $conn->dbConnect();
        
        if ($db == NULL) {
            $err = array("ERROR" => "Error, Cannot connect to database");
            return $err;
        }
        
        $sql = "select sutID from systemsUnderTest";
		$result=mysql_query($sql);
   		$sutList = array();
    	while($row=mysql_fetch_array($result))
    		{
      		$sutList[]=$row['sutID'];
     		}
		
        //print_r ($sutList);
        if(!in_array($sut, $sutList)){
            //print $sut;
            //print strval($sut);
            $err = array("ERROR" => "ERROR, SUT ID not in database");
            return $err;
        }
        
	    $testID=  rand(1,7);
	    $testSQL="select testName, scriptName from tests where testID=".$testID;
	    $nextTestResult = $db->Execute($testSQL);
	    $rowTest = $nextTestResult->FetchRow();
	    
	    $this->test["testName"] = $rowTest[0];
	    $this->test["scriptName"] = $rowTest[1];
		
	    //print $test; 
	   
	    //$scriptName= $this->test["scriptName"];
        //$dt=date("Y-m-d H:i:s");
        
        $countQuery="SELECT COUNT(startTime) FROM testResults where startTime >= CURDATE() - INTERVAL 1 DAY AND testID=".$testID." AND sutID=".$sut;
        
        $rs1 = $db->Execute($countQuery);
        $row1 = $rs1->FetchRow();
        $count = $row1[0];
        //print $count;
        
        $dailyFreq="select dailyFrequenceCount from tests where testID= ".$testID;
        $rs2 = $db->Execute($dailyFreq);
        $row2=$rs2->FetchRow();
        $dailyFreq1=$row2[0];
        
        if ($rs2 == false) {
            $err2 = array("ERROR" => "No results from database");
            return $err2;
        }
        
        //print " ".$dailyFreq1;
        if((int)$count< (int)$dailyFreq1){
            //print $this;
            //print "   i'm here    ";
		    return $this->test;
		    //print $this->test;
        }
		else{
		    $err = array("ERROR" => "Error, Over max daily frequency");
		    //print "here";
		    return $err;
	    }
	    
	}

	// Return the test name which is the description
	//
	public function getTestName() {

		return $this->test["testName"];
	}

	// Return the script name to execute
	//
	public function getScriptName() {
    	return $this->test["scriptName"];
	}
}
?>