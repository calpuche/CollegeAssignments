<?php
require './session.php';
require "../startupPage.php"; // gets functions we will use
// call a method to output the document's page heading
printDocHeading("../bootstrap.css", "Confirmation");
print "<body>";
if ($_POST['ConfirmOrder'] == "Confirm Order")
{
    $db = dbConnect();
    $shoeSize = htmlentities($_POST['shoeSize'], ENT_QUOTES);
    $shirtSize = htmlentities($_POST['shirtSize'], ENT_QUOTES);
    $firstName = htmlentities($_POST['fName'], ENT_QUOTES);
    $lastName = htmlentities($_POST['lName'], ENT_QUOTES);
    $email = htmlentities($_POST['email'], ENT_QUOTES);
    $address = htmlentities($_POST['address'], ENT_QUOTES);
    $city = htmlentities($_POST['city'], ENT_QUOTES);
    $state = htmlentities($_POST['state'], ENT_QUOTES);
    $zip = htmlentities($_POST['zip'], ENT_QUOTES);
    $itemID = htmlentities($_POST['itemID'], ENT_QUOTES);
    
    $CreditCardName = htmlentities($_POST['CreditCardName'], ENT_QUOTES);
    $CreditCardNumber = htmlentities($_POST['CreditCardNumber'], ENT_QUOTES);
    $Month = htmlentities($_POST['Month'], ENT_QUOTES);
    $Year = htmlentities($_POST['Year'], ENT_QUOTES);
    $SecurityCode = htmlentities($_POST['SecurityCode'], ENT_QUOTES);
    
    //insert credit card info query
    $creditCardQuery = "INSERT INTO creditCard VALUES(null,'$CreditCardName','$CreditCardNumber',$Month,$Year,$SecurityCode)";

    //makes sure that the credit card query isn't empty
     if(empty($creditCardQuery)){
        print "<p>Error purchasing object please try again.</p>";
        return;
    } 
    
    //runs credit card query
    $result = $db->Execute($creditCardQuery);
    //if credit card query fails, rest of php isn't run and this error pops up
    if ($result == false)
        {
           print "<div class = 'container'>";
            print "<h1>Error</h1>";
            print "<p>Please Try Again. If order was placed, please contact the Website Administrator.</p>";
            print "</div>";
        }
    
    //gets the most recent credit card id to be used lated for inserting the purchase query
    $creditCardIDQuery = "SELECT MAX(creditCardID) FROM creditCard;";
    $creditCardID = $db->Execute($creditCardIDQuery);
    $creditCardID = trim(str_replace("MAX(creditCardID)", "", $creditCardID));

    
    if(empty($shoeSize)){
    $query = "INSERT INTO purchase VALUES(null,'','$shirtSize',$itemID,'$firstName','$lastName','$address','$city','$state',$zip)";
    }
    elseif(empty($shirtSize)){
        $query = "INSERT INTO purchase VALUES(null,'$shoeSize','',$itemID,'$firstName','$lastName','$address','$city','$state','$zip')";
    }
    if(empty($query)){
        print "<p>Error purchasing object please try again.</p>";
        return;
    }
    $result = $db->Execute($query);

        if ($result == false)
        {
           print "<div class = 'container'>";
            print "<h1>Error</h1>";
            print "<p>Please Try Again. If order was placed, please contact the Website Administrator.</p>";
            print "</div>";
        }
    $db = dbConnect();
    $query = "SELECT MAX(purchaseID) FROM purchase;";
    $orderID = $db->Execute($query);
}

if (empty($_POST)){
    print "<p>&nbsp;</p>";
    print "<div class = 'container'>";
    print "<h1>Error</h1>";
    print "<p>Please Try Again</p>";
    print "</div>";
}
else{
    $db = dbConnect();
    $query = "SELECT description FROM item WHERE itemID = '$itemID'";
    $itemName = $db->Execute($query);
    print "<p>&nbsp;</p>";
    print "<div class = 'container'>";
    //showAll();
    $orderNumber = trim(str_replace("MAX(purchaseID)", "", $orderID));
    print "<h1>Thank you for your order</h1>";
    print "<h3>Order number is: $orderNumber</h3>\n";
    print "<p>&nbsp;</p>";
    print "<p>First Name: ".$firstName."</p>\n";
    print "<p>Last Name: ".$lastName."</p>\n";
    print "<p>Address: ".$address."</p>\n";
    print "<p>City: ".$city."</p>\n";
    print "<p>State: ".$state."</p>\n";
    print "<a href='http://calpuche.create.stedwards.edu/CopOrDrop/'>Go Back Home</a>";
    print "</div>";
    
    $msg = "Thank you for ordering from Cop or Drop!\n
                    Order Details \n
                    Order number is: $orderNumber \n
                    First Name: $firstName \n
                    Last Name: $lastName \n
                    Address: $address \n
                    City: $city \n
                    State: ".$state."
                    ";
            
            // use wordwrap() if lines are longer than 70 characters
            $msg = wordwrap($msg,70);
            
            // send email
            mail($email,"Cop or Drop Order",$msg);
    
    
}



