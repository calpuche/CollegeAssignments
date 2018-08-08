<?php
// Example script that uses a form for
// inputting user's name and gpa
// reports back dean's list eligibility (gpa >=3.5)
// checks that all fields are filled in.
//  expects data when form is submitted as GET
//  theName - name of person
//  gpa - person's gpa (checks that its a valid gpa as well)

require "classfun.php";
printDocHeading("layout.css", "Php example using get");
print "<body>\n<div class='content'>\n";
print "<h2>Welcome!</h2>\n";
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
function showForm($aName="", $aHome="")
{
  $self = $_SERVER['PHP_SELF'];
  print "<div> <form method='get' action='$self' >\n";
  print "<h3> Enter your name: <input type='text' name='theName' ".
        " value='$aName' /></h3>\n";
  print "<h3> Enter your HomeTown: <input type='text' name='theHome' ".
        " value='$aHome' /></h3>\n";
  print "<h3> <input type='submit' name='submitInfo' ".
        " value='submit for checking' /> </h3>\n";
  print "</form>\n</div>\n";
}

// function that expects to find data in GET
// expects theName (text) and gpa (text but will test as numeric and 0.0-4.0 range
// either process data (its valid and present)
// or outputs error message and reprints input form
function checkFormData()
{
  $name = htmlentities($_GET['theName'], ENT_QUOTES);
  $homeTown = htmlentities($_GET['theHome'], ENT_QUOTES);
  $errorMessage = "";
  if($name == "")
  {
    $errorMessage .= "<h3> You must enter your name: </h3>\n";
  }
   if($homeTown == "")
  {
    $errorMessage .= "<h3> You must enter your HomeTown: </h3>\n";
  }
  if($errorMessage)
  {
    print $errorMessage;
    showForm($name, $homeTown);
  }
  else 
  {
       $result="<br /> Welcome to my Website!! \n";
    print " <h2> Hello $name <br /> $result $homeTown is a great city!</h2> \n";
  }//end else
  startOverLink();
}// end checkFormData function

?>