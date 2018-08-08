<?php
require_once "./mydb.php";
function createNewAccount(){
    $db = dbConnect();
	$emailEntered = htmlentities($_POST['email'],ENT_QUOTES);
	$passEntered = htmlentities($_POST['password'],ENT_QUOTES);
	$addressEntered = htmlentities($_POST['address'],ENT_QUOTES);
	$cityEntered = htmlentities($_POST['city'],ENT_QUOTES);
	$stateEntered = htmlentities($_POST['state'],ENT_QUOTES);
	$firstNameEntered = htmlentities($_POST['firstName'],ENT_QUOTES);
	$lastNameEntered = htmlentities($_POST['lastName'],ENT_QUOTES);
	$zipEntered = htmlentities($_POST['zip'],ENT_QUOTES);
	
	$query ="INSERT into ACCOUNT".$emailEntered.",".$passEntered.",".$address.",".$city.",".$state.",".$firstName.",".$lastName.",".$zip;
    $result = $db ->Execute($query);
	print $result;
	if ($count == 1){
		 $_SESSION["valid"] = true;
		$_POST = "";
	}
	else{
		print "<h2>Invalid UserName or Password, Please try again!";
	}


}


?>