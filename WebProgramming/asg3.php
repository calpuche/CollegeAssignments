<?php
// Example script that uses a form for
// inputting user's name and gpa
// reports Users year of school
// checks that all fields are filled in.
//  expects data when form is submitted as GET
//  theName - name of person
//  theCredit - person's Credits (checks that its a valid amount of hours as well)

require "classfun.php";
printDocHeading("layout.css", "ASG #3");
print "<body>\n<div class='content'>\n";
print "<h2>Credit Checker</h2>\n";
if(empty ($_GET))
{
  showForm();
}
else
{
  checkFormData();
}
print "</div>\n";  // end of opening div
printDocFooter();

// function that displays form to user, uses default arguments
//  1st arg for name, 2nd arg for gpa
function showForm($aName="", $aCredit="")
{
  $self = $_SERVER['PHP_SELF'];
  print "<div> <form method='get' action='$self' >\n";
  print "<h3> Enter your name: <input type='text' name='theName' ".
        " value='$aName' /></h3>\n";
  print "<h3> Enter the amount of Credit Hours Achieved: <input type='text' name='theCredit' ".
        " value='$aCredit' /></h3>\n";
  print " <input type='submit' name='submitCreditInfo' ".
        " value='Submit' /> \n";
  print "</form>\n</div>\n";
}

// function that expects to find data in GET
// expects theName (text) and gpa (text but will test as numeric and 0.0-4.0 range
// either process data (its valid and present)
// or outputs error message and reprints input form
function checkFormData()
{
  $name = htmlentities($_GET['theName'], ENT_QUOTES);
  $credit = htmlentities($_GET['theCredit'], ENT_QUOTES);
  $errorMessage = "";
  if($name == "")
  {
    $errorMessage .= "<h3> You must enter your name: </h3> \n";
  }
  if($credit == "" || !is_numeric($credit) || $credit < 0.0)
  {
    $credit="";
    $errorMessage .= "<h3> Your amount of hours, must be greater than 0. </h3> \n";
  }
  if($errorMessage)
  {
    print $errorMessage;
    showForm($name, $credit);
  }
  else 
  {
    if($credit < 29)
      $result="<br /> Congratulations! You are a Freshman. Welcome to College! \n";
    else if($credit >=30 AND $credit <= 59)
      $result="<br /> Congratulations! You are a Sophomore. Welcome Back to College! \n";
  else if($credit >=60 AND $credit <= 89)
      $result="<br /> Congratulations! You are a Junior. Welcome Back to College! \n";
  else if($credit >= 90)
      $result="<br /> Congratulations! You are a Senior. Welcome Back to College, you're almost done! \n";
    print " <h2> Hello $name <br /> $result </h2> \n";
  }//end else
  startOverLink();
}// end checkFormData function

?>