<?php
require './session.php';
require "../startupPage.php"; // gets functions we will use
// call a method to output the document's page heading
printDocHeading("../bootstrap.css", "Credit Card Info");
printAll();
print "<body style='background-color:CORNFLOWERBLUE;'>";
print "<p>&nbsp;</p>";
print "<div class='container' style='background-color:white;'>";
print "<h1 style='text-align:center'>The Shoe Store</h1>";
print "<div class='border'>";
list ($id) = printProduct();
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

if ($_POST['CreditCardConfirm'] == "Next")
{
    $shoeSize = htmlentities($_POST['ShoeSize'], ENT_QUOTES);
    $shirtSize = htmlentities($_POST['ShirtSize'], ENT_QUOTES);
    $firstName = htmlentities($_POST['firstName'], ENT_QUOTES);
    $lastName = htmlentities($_POST['lastName'], ENT_QUOTES);
    $email = htmlentities($_POST['email'], ENT_QUOTES);
    $address = htmlentities($_POST['address'], ENT_QUOTES);
    $city = htmlentities($_POST['city'], ENT_QUOTES);
    $state = htmlentities($_POST['state'], ENT_QUOTES);
    $zip = htmlentities($_POST['zip'], ENT_QUOTES);
    $itemID = htmlentities($_POST['itemID'], ENT_QUOTES);

}

if (empty($_POST)){
    print "<p>&nbsp;</p>";
    print "<div class = 'container'>";
    print "<h1>Error</h1>";
    print "<p>Please Try Again</p>";
    print "</div>";
}
else{
print "<div class='container'>";
print "<h1>Credit/Debit Card</h1>";

print "<form method='post' action='confirmation.php'>\n";

print "<div class='form-row'>\n";
print "<div class='form-group col-md-6'>\n";
print "<label for='inputCreditCardName'>Name on Card</label>\n";
print "<input type='text' name='CreditCardName' class='form-control' id='CreditCardName' placeholder='Name*' value='$creditCardName' required>\n";
print "</div>\n";
print "</div>\n";

print "<div class='form-row'>\n";
print "<div class='form-group col-md-6'>\n";
print "<label for='inputCreditCardNumber'>Credit Card Number</label>\n";
print "<input type='text' name='CreditCardNumber' class='form-control' id='CreditCardNumber' placeholder='Credit Card Number*' value='$creditCardNumber' required>\n";
print "</div>\n";
print "</div>\n";

print "<div class='form-row'>\n";
print "<div class='form-group col-md-3'>\n";
print "<label for='inputMonth'>Month</label>\n";
print "<select id='inputMonth' name='Month' class='form-control'>\n";
print "<option value='1'>01</option>\n";
print "<option value='2'>02</option>\n";
print "<option value='3'>03</option>\n";
print "<option value='4'>04</option>\n";
print "<option value='5'>05</option>\n";
print "<option value='6'>06</option>\n";
print "<option value='7'>07</option>\n";
print "<option value='8'>08</option>\n";
print "<option value='9'>09</option>\n";
print "<option value='10'>10</option>\n";
print "<option value='11'>11</option>\n";
print "<option value='12'>12</option>\n";
print "</select>";
print "</div>\n";

print "<div class='form-group col-md-3'>\n";
print "<label for='inputYear'>Year</label>\n";
print "<input type='text' name='Year' class='form-control' id='Year' placeholder='Year*' value='$cvcYear' required>\n";
print "</div>\n";
print "</div>\n";
   
print "<div class='form-row'>\n";
print "<div class='form-group col-md-2'>\n";
print "<label for='inputSecurityCode'>Security Code</label>\n";
print "<input type='text' name='SecurityCode' class='form-control' id='SecurityCode' placeholder='Security Code*' value='$SecurityCode' required>\n";
print "</div>\n";
print "</div>\n";
   
print "<input  type='hidden' name='shoeSize' value='$shoeSize'/> \n";
print "<input  type='hidden' name='shirtSize' value='$shirtSize'/> \n";
print "<input  type='hidden' name='fName' value='$firstName'/> \n";
print "<input  type='hidden' name='lName' value='$lastName'/> \n";
print "<input  type='hidden' name='email' value='$email'/> \n";
print "<input  type='hidden' name='address' value='$address'/> \n";
print "<input  type='hidden' name='city' value='$city'/> \n";
print "<input  type='hidden' name='state' value='$state'/> \n";
print "<input  type='hidden' name='zip' value='$zip'/> \n";
print "<input  type='hidden' name='title' value='$title'/> \n";
print "<input  type='hidden' name='itemID' value='$itemID'/> \n";

print "<input type='submit' name='ConfirmOrder' value='Confirm Order' id='ConfirmOrder'/>\n";
print "</div>\n";
print "<p>&nbsp;</p>";
print "</div>\n";
print "<p>&nbsp;</p>";
print "</body>";

}
print "</html>";
?>