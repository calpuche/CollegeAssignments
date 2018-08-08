<?php
require "../startupPage.php"; // gets functions we will use
require "./mydb.php";
// call a method to output the document's page heading
printDocHeading("../bootstrap.css", "Cop or Drop");
// now begin output of our html content
print "<body style='background-color:LIGHTSTEELBLUE;'>";
print "<div class='container'>";
print "<h1><center>Cop or Drop</center></h1>";
print "<hr />";
print "</div>";
print "</div>";
print "<div class=container>";
print "<div class='row'>";
print "<div class='col'>";
print "<form class='form-horizontal' method='post'>";
print "<div class='form-group'>";
print "<label>First Name</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='text' class='form-control' name='firstName' id='firstName'  placeholder='Enter your first name' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<form class='form-horizontal' method='post'>";
print "<div class='form-group'>";
print "<label>Last Name</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='text' class='form-control' name='lastName' id='lastName'  placeholder='Enter your last name' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<div class='form-group'>";
print "<label>Email</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='email' class='form-control' name='email' id='email'  placeholder='Enter your Email' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<div class='form-group'>";
print "<label>Password</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='password' class='form-control' name='password' id='password'  placeholder='Enter your Password' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<div class='form-group'>";
print "<label>Confirm Password</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='password' class='form-control' name='confirm' id='confirm'  placeholder='Confirm your Password' required/>";
print "</div>";
print "</div>";
print "</div>";
print "</div>";

print "<div class='col'>";
print "<div class='form-group'>";
print "<label>Address</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='text' class='form-control' name='address' id='address'  placeholder='Enter your Address' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<div class='form-group'>";
print "<label>City</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='text' class='form-control' name='city' id='city'  placeholder='Enter your City' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<div class='form-group'>";
print "<label>State</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
echo '<select class="custom-select">';
echo '<option value="AL">Alabama</option>';
echo '<option value="AK">Alaska</option>';
echo '<option value="AZ">Arizona</option>';
echo '<option value="AR">Arkansas</option>';
echo '<option value="CA">California</option>';
echo '<option value="CO">Colorado</option>';
echo '<option value="CT">Connecticut</option>';
echo '<option value="DE">Delaware</option>';
echo '<option value="DC">District Of Columbia</option>';
echo '<option value="FL">Florida</option>';
echo '<option value="GA">Georgia</option>';
echo '<option value="HI">Hawaii</option>';
echo '<option value="ID">Idaho</option>';
echo '<option value="IL">Illinois</option>';
echo '<option value="IN">Indiana</option>';
echo '<option value="IA">Iowa</option>';
echo '<option value="KS">Kansas</option>';
echo '<option value="KY">Kentucky</option>';
echo '<option value="LA">Louisiana</option>';
echo '<option value="ME">Maine</option>';
echo '<option value="MD">Maryland</option>';
echo '<option value="MA">Massachusetts</option>';
echo '<option value="MI">Michigan</option>';
echo '<option value="MN">Minnesota</option>';
echo '<option value="MS">Mississippi</option>';
echo '<option value="MO">Missouri</option>';
echo '<option value="MT">Montana</option>';
echo '<option value="NE">Nebraska</option>';
echo '<option value="NV">Nevada</option>';
echo '<option value="NH">New Hampshire</option>';
echo '<option value="NJ">New Jersey</option>';
echo '<option value="NM">New Mexico</option>';
echo '<option value="NY">New York</option>';
echo '<option value="NC">North Carolina</option>';
echo '<option value="ND">North Dakota</option>';
echo '<option value="OH">Ohio</option>';
echo '<option value="OK">Oklahoma</option>';
echo '<option value="OR">Oregon</option>';
echo '<option value="PA">Pennsylvania</option>';
echo '<option value="RI">Rhode Island</option>';
echo '<option value="SC">South Carolina</option>';
echo '<option value="SD">South Dakota</option>';
echo '<option value="TN">Tennessee</option>';
echo '<option value="TX">Texas</option>';
echo '<option value="UT">Utah</option>';
echo '<option value="VT">Vermont</option>';
echo '<option value="VA">Virginia</option>';
echo '<option value="WA">Washington</option>';
echo '<option value="WV">West Virginia</option>';
echo '<option value="WI">Wisconsin</option>';
echo '<option value="WY">Wyoming</option>';
echo '</select>';
print "</div>";
print "</div>";
print "</div>";
print "<div class='form-group'>";
print "<label>Zip</label>";
print "<div class='cols-sm-10'>";
print "<div class='input-group'>";
print "<input type='number' class='form-control' name='zip' id='zip'  placeholder='Enter your Zip' required/>";
print "</div>";
print "</div>";
print "</div>";
print "<div class='control-group'>\n";

print "</div>\n";
print "</div>\n";
print "</div>\n";
print "<button class='btn btn-primary btn-lg btn-block login-button'>Register</button>\n";
print "</form>";
print "</div>";

print "<div class='login-register'>\n";
print "<a href='../'><center>Login</center></a>";

if (!empty($_POST))
{
    $db = dbConnect();
    $firstName = htmlentities($_POST['firstName'], ENT_QUOTES);
    $lastName = htmlentities($_POST['lastName'], ENT_QUOTES);
    $email = htmlentities($_POST['email'], ENT_QUOTES);
    $password = htmlentities($_POST['password'], ENT_QUOTES);
    $confirmPassword = htmlentities($_POST['confirm'], ENT_QUOTES);
    $address = htmlentities($_POST['address'], ENT_QUOTES);
    $city = htmlentities($_POST['city'], ENT_QUOTES);
    $state = htmlentities($_POST['state'], ENT_QUOTES);
    $zip = htmlentities($_POST['zip'], ENT_QUOTES);

    if ($password != $confirmPassword)
    {
        print "<p style='color:red;'>Passwords don't match, Please try again!</p>";
    }
    else
    {
        $query = "INSERT INTO account VALUES(null,'" . $email . "','" . $password . "','" . $firstName . "','" . $lastName . "','" . $address . "'," . $zip . ",'" . $city . "','" . $state . "')";
        $result = $db->Execute($query);
        if ($result == false)
        {
            print "<p>Error please contact admin</p>";
        }
        else
        {
            header("location: ../index.php");
        }
    }
}
print "</div>";
print "</div>";
print "</div>";
print "</body>";
?>
