<?php
//phoneExample
require "classfun.php";
printDocHeading("layout.css","InClass Assignment 2");
printAll();
showPhoneList();
if(empty ($_POST))
{
  showAddForm();
}
else
{
  checkPhoneEntry();
}

//functions
print "<a href='/~calpuche/cosc2328/inClass2.php'>Back to Start</a>";
printDocFooter();


function showPhoneList()
{
	print "<h1>The Phone List Website</h1>"."\n";
	print "<h4>Current Phone List</h4>"."\n";
	$filename = "../../../xj1364/phone.txt";
	$contents = file_get_contents($filename);
	
	//now break into lines
	$lines = explode("\n",$contents);
	foreach($lines as $line)
	{
		$parts = explode("||", $line);
		print $parts[0]."==>".$parts[1].'<br>'."\n";
	}
}	
	//end of showPhoneList
function checkPhoneEntry()
	{
	//check for empty name,phone
	$errorMsg = "";
	$name = htmlentities($_POST['theName'],ENT_QUOTES);
	if ($name == "")
	{
		$errorMsg .="No Name Submitted! <br/>\n";
	}
	$phone = htmlentities($_POST['thePhone'],ENT_QUOTES);
	if ($name == "")
	{
		$errorMsg .="No Phone Number Submitted! <br/>\n";
	}
	if($errorMsg){
		print $errorMsg;
	}
	else {
		//add %phone,$name to file
		addPhoneEntry($name,$phone);
	}
	}
function addPhoneEntry($name, $phone)
	{
		$filename = "../../../xj1364/phone.txt";
		$fh = fopen($filename, 'a') or die("can't open file");
		$stringData = "\n".$name."||".$phone;
		fwrite($fh, $stringData);
		fclose($fh);
	}
function showAddForm($aPhone="",$aName="")
	{
	  $self = $_SERVER['PHP_SELF'];
	  print "<div> <form method='POST' action='$self' >\n";
	  print "<fieldset>\n";
	  print "<legend> Phone Entry</legend>\n";
	  print "<h3> Enter a contact name: <input type='text' name='theName' ".
			" value='$aName' /></h3>\n";
	  print "<h3> Enter a phone number: <input type='text' name='thePhone' ".
			" value='$aPhone' /></h3>\n";
	  print "<input type='submit' name='submitInfo' ".
			" value='submit for checking' />\n";
	  print "</fieldset>\n</form>\n</div>\n";
	}

	
?>