<?php

// putResults.php
// Puts results into database
//

require_once "./AutomationTest.php";
require_once "./DBConnection.php";

// Process all key/value pairs that come with the $GET
// and take the appropriate action to select mobile phones
//
if (isset($_GET["action"])) {
    
    $action = $_GET["action"];
    $result = $_GET["result"];
    $errorLog = $_GET["error"];
    $startTime = $_GET["startTime"];
    $endTime = $_GET["endTime"];
    $scriptName = $_GET["scriptName"];
    $sut = $_GET["sutID"];
    
    //$myJson = "Unknown Error";        // Setup json for the return info

        $conn = new DBConnection();
        $db = $conn->dbConnect();
        //print "I'm getting this far";
        
        if ($db == NULL) {
            $err = array("ERROR" => "Cannot connect to database");
            return $err;
        }
        
        $sql = 'select testID from tests where scriptName="'.$scriptName.'"';
        //print $sql;
        //print $scriptName;
        $rs = $db->Execute($sql);
        $row=$rs->FetchRow();
        $testID=$row[0];
        //print $testID;
        
        if ($rs == false) {
            $err = array("ERROR" => "No results from database");
            return $err;
        }
        
        if ($result=="FAILURE"){
            // the message
            $msg = "There was a failure error in the Struix System!\n
                    SUT ID: ".$sut."\n
                    Test Script Name: ".$scriptName."\n
                    Error Message: ".$errorLog."\n
                    ";
            
            // use wordwrap() if lines are longer than 70 characters
            $msg = wordwrap($msg,70);
            
            // send email
            mail("mxlblood@gmail.com","Struix Error",$msg);
            
            $test = new AutomationTest();     // Setup a test object
            $putResult = $test->putResults($result,$startTime,$endTime,$errorLog,1,$testID,$sut);
            if(array_key_exists("ERROR",$putResult)){
                print $putResult['ERROR'];
            }
        }
        else{
        $test = new AutomationTest();     // Setup a test object
        $putResult = $test->putResults($result,$startTime,$endTime,$errorLog,0,$testID,$sut);
        if(array_key_exists("ERROR",$putResult)){
            print $putResult['ERROR'];
        }
    }
}

?>