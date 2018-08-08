<?php
require './session.php';
require "../startupPage.php"; // gets functions we will use
// call a method to output the document's page heading
printDocHeading("../bootstrap.css", "Pre-Order");
// now begin output of our html content
print "<body style='background-color:LIGHTSTEELBLUE;'>";
print "<p>&nbsp;</p>";
print "<div class='container' style='background-color:white;'>";
print "<h1 style='text-align:center'>Cop or Drop</h1>";
print "<div class='border'>";
list ($id,$type) = printProduct();

function printProduct(){
    $id = $_POST["itemID"];
    $db = dbConnect();
    $query = "SELECT title,description,type FROM item WHERE itemID='$id'";
	$result = $db->Execute($query);
if(!$result){
		print "<h3>No Items Found</h3>\n";
		print "<h3>Contact Admin!</h3>\n";
		return;
	}
	$row= $result->FetchRow();

	$title=$row['title'];
	$description = $row['description'];
	$type = $row['type'];
	print "<div class='container'>";
	$imageTag="<img class='img-thumbnail' width='40%' height='40%' src='../images/".$title.".jpg'/>\n";
	print $imageTag;
	print "<h3>$description</h3>\n";
    print "</div>";
    return array ($id, $type);
}


$userName = $_SESSION['login_user'];
$query = "SELECT * FROM account WHERE email='$userName'";
$result = $db->Execute($query);
if(!$result){
		print "<h3>Account information not found please type in your information</h3>\n";
	}
else{
	    $row= $result->FetchRow();
	    $firstName=$row['firstName'];
	    $lastName=$row['lastName'];
	    $address=$row['address'];
	    $zip=$row['zip'];
	    $city=$row['city'];
	    $state=$row['state'];
	}
print "<div class='container'>";
print "<form method='post' action='creditCard.php'>\n";
if($type=="SHOE"){
print "<div class='form-row'>\n";
print "<div class='form-group col-md-1'>\n";
print "<label for='inputSize'>Shoe Size</label>\n";
print "<select id='inputSize' name='ShoeSize' class='form-control'>\n";
print "<option value='6.0'>6.0</option>\n";
print "<option value='6.5'>6.5</option>\n";
print "<option value='7.0'>7.0</option>\n";
print "<option value='7.5'>7.5</option>\n";
print "<option value='8.0'>8.0</option>\n";
print "<option value='8.5'>8.5</option>\n";
print "<option value='9.0'>9.0</option>\n";
print "<option value='9.5'>9.5</option>\n";
print "<option value='10.0'>10.0</option>\n";
print "<option value='10.5'>10.5</option>\n";
print "<option value='11.0'>11.0</option>\n";
print "<option value='12.5'>11.5</option>\n";
print "<option value='12.0'>12.0</option>\n";
print "</select>";
print "</div>\n";
print "</div>\n";
}
elseif($type=="SHIRT"){
print "<div class='form-row'>\n";
print "<div class='form-group col-md-6'>\n";
print "<label for='shirtSize'>Shirt Size</label>\n";
print "<select id='shirtSize' name='shirtSize' class='form-control'>\n";
print "<option selected>Choose</option>\n";
print "<option>S</option>\n";
print "<option>M</option>\n";
print "<option>L</option>\n";
print "<option>XL</option>\n";

print "</select>";
print "</div>\n";
print "</div>\n";
    
}
print "<div class='form-row'>\n";
print "<div class='form-group col-md-6'>\n";
print "<label for='firstName'>First Name</label>\n";
print "<input type='text' name='firstName' class='form-control' id='firstName' placeholder='First Name' value='$firstName'>\n";
print "</div>\n";
print "<div class='form-group col-md-5'>\n";
print "<label for='lastName'>Last Name</label>\n";
print "<input type='text' name='lastName' class='form-control' id='lastName' placeholder='Last Name' value='$lastName'>\n";
print "</div>\n";
print "</div>\n";
print "<div class='form-row'>\n";
print "<div class='form-group col-md-6'>\n";
print "<label for='inputEmail'>Email</label>\n";
print "<input type='email' name='email' class='form-control' id='inputEmail' placeholder='Email' value='$userName'>\n";
print "</div>\n";
print "</div>\n";
print "<div class='form-row'>\n";
print "<div class='form-group col-md-11'>\n";
print "<label for='inputAddress'>Address</label>\n";
print "<input type='text' name='address' class='form-control' id='inputAddress' placeholder='1234 Main St' value='$address'>\n";
print "</div>\n";
print "</div>\n";
print "<div class='form-row'>\n";
print "<div class='form-group col-md-5'>\n";
print "<label for='inputCity'>City</label>\n";
print "<input type='text' name='city' class='form-control' id='inputCity' value='$city'>\n";
print "</div>\n";
print "<div class='form-group col-md-4'>\n";
print "<label for='inputState'>State</label>\n";
print "<input type='text' name='state' class='form-control' id='inputState' value='$state'>\n";
print "</div>\n";
print "<div class='form-group col-md-2'>\n";
print "<label for='inputZip'>Zip</label>\n";
print "<input type'text' name='zip' class='form-control' id='inputZip' value='$zip'>\n";
print "</div>\n";
print "</div>\n";
print "<input type='hidden' name='itemID' value='$id'/> \n";
print "<input class='btn btn-primary' type='submit' name='CreditCardConfirm' value='Next' id='confirmOrder'/>\n";
print "</form>";
print "</div>\n";
print "<p>&nbsp;</p>";

print "</div>\n";
print "<p>&nbsp;</p>";

print "</div>\n";

print "</div>\n";
print "</body>";
print "</html>";

?>
