<?php
require "./startupPage.php"; // gets functions we will use
require "./mydb.php"; // gets functions we will use
session_start();
// call a method to output the document's page heading
printDocHeading("./bootstrap.css", "Login");
// now begin output of our html content
print "<body style='background-color:LIGHTSTEELBLUE;'>\n";
print "<div class='center-div'>";
print "<div class='container'>\n";
print "<div class='row'>\n";
print "<form class='form-group' action='' method='POST'>\n";
print "<fieldset>\n";
print "<div id='legend'>\n";
print "<h1><center>Cop or Drop</center></h1>";
print "<legend><center>Login</center></legend>\n";
print "</div>\n";
print "<div class='control-group'>\n";
print "<!-- Username -->\n";
print "<label class='control-label'  for='username'>Email</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='username' name='username' placeholder='Email' class='form-control'>\n";
print "</div>\n";
print "</div>\n";
print "<div class='control-group'>\n";
print "<!-- Password-->\n";
print "<label class='control-label' for='password'>Password</label>\n";
print "<div class='controls'>\n";
print "<input type='password' id='password' name='password' placeholder='Password' class='form-control'>\n";
print "</div>\n";
print "</div>\n";
print "<br>";
print "<div class='row'>\n";
print "<!-- Button -->\n";
print "<div class='col-sm-4'>\n";
print "<button class='btn btn-primary '>Login</button>\n";
print "</div>\n";
print "<div class='col-sm-4'>\n";
print "<a href='http://calpuche.create.stedwards.edu/CopOrDrop/register/index.php'><button type='button' class='btn btn-primary float-left'>Sign Up</button></a>\n";
print "</div>\n";
print "</div>\n";
print "</div>\n";
if ($_SERVER["REQUEST_METHOD"] == "POST")
{
    $db = dbConnect();
    $emailEntered = htmlentities($_POST['username'], ENT_QUOTES);
    $passEntered = htmlentities($_POST['password'], ENT_QUOTES);
    $query = "SELECT email, password FROM account WHERE email = '" . $emailEntered . "' AND password = '" . $passEntered . "'";
    $result = $db->Execute($query);
    $count = $result->RowCount();

    if ($count == true)
    {
        $_SESSION['login_user'] = $emailEntered;
        //enter the welcome page down here in post
        header("location: home.php");
    }
    else
    {
        print "<p style='color:red;'>Invalid Email or Password, Please try again!</p>";
    }
}
print "</div>\n";
print "</div>\n";
print "</fieldset>\n";
print "</form>\n";
print "</div>\n";
print "</div>\n";
print "</body>\n";
print "</html>\n";
if ($_SESSION['login_user'])
{
    header("location:./home.php");

}

?>
