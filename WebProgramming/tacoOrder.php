<?
//receives get data
//looks up phone number in customer database for taco to go
//if found
	//return from with customer info and last placed order
	//2 buttons submit and clear order
//if not found
	//show empty form
//1) determine wha service providing
require_once "mydb.php";
require_once "classfun.php";
	$task = htmlentities($_GET['task'],ENT_QUOTES);
	if($task == 1){
		findCustomer();
	}
	else{
		$name = htmlentities($_GET['aName'],ENT_QUOTES);
		
		submitOrder();
	}
	function findCustomer(){
		$phone = htmlentities($_GET['phoneNumber'],ENT_QUOTES);
		//print $phone;
		$db = adoConnectTacos();
		$query = "SELECT * FROM Customer WHERE CustomerPhone = '$phone'";
		$result = $db ->Execute($query);
		//print $result;
		if(!$result){
			showCustomerForm($phone);
		}
		else{
			$row=$result->FetchRow();
			//print $row;
			$name = $row[1];
			//$address = $row['CustomerAddress'];
			$lastOrder = $row[2];
			//print "I'm in else and name is : $name";
			showCustomerForm($phone,$name,$lastOrder);
			
		}
	}
	function submitOrder(){
		//print "I'm Here";
		$db = adoConnectTacos();
		$phone = htmlentities($_GET['aPhone'],ENT_QUOTES);
		$query ="SELECT * FROM Customer WHERE customerPhone = '$phone'";
		$result = $db ->Execute($query);
		//print $result;
		$count = $result->RowCount();
		$name = htmlentities($_GET['aName'],ENT_QUOTES);
		//print $name;
		$beef = htmlentities($_GET['numBeef'],ENT_QUOTES);
		$chicken = htmlentities($_GET['numChicken'],ENT_QUOTES);
		$breakfast = htmlentities($_GET['numBreakfast'],ENT_QUOTES);
		$drink = htmlentities($_GET['aDrink'],ENT_QUOTES);
		$drink = $drink -1;
		//print $count;
		if($count == 0){
			$query = "INSERT INTO TacoOrder (OrderID,NumBreakfest,NumBeef,NumChicken,DrinkID) VALUES (NULL,'$breakfast','$beef','$chicken','$drink')";
			$result = $db ->Execute($query);
			//print $result;
			$lastId = $db->Insert_ID(); 
		//	print $lastId;
			$query = "INSERT INTO Customer (CustomerPhone,CustomerName,LastOrder) VALUES ('$phone','$name','$lastId')";
			$result = $db ->Execute($query);
			//print $result;
			//do an insert query into the customer database
			//insert order stuff
			
		}
		else{
			$query = "INSERT INTO TacoOrder (OrderID,NumBreakfest,NumBeef,NumChicken,DrinkID) VALUES (NULL,'$breakfast','$beef','$chicken','$drink')";
			$result = $db ->Execute($query);
			$lastId = $db->Insert_ID(); 
			$query = "UPDATE Customer set CustomerName='$name',LastOrder ='$lastId' WHERE CustomerPhone = '$phone'";
			$result = $db ->Execute($query);
			//update query into customer database
			//update name address and last order
		}
		$query = "SELECT * FROM Drink WHERE DrinkID=$drink";
		$result = $db ->Execute($query);
		$row=$result->FetchRow();
		$count = $result->RowCount();
		$showDrink = $row[1];
		print "<h1>Your Order is: </h1>\n";
		print "<ul class= 'list-group'>";
		print "<li class='list-group-item'>Name: $name</li>\n";
		print "<li class='list-group-item'>Beef Tacos: $beef</li>\n";
		print "<li class='list-group-item'>Chicken Tacos: $chicken</li>\n";
		print "<li class='list-group-item'>Breakfast Tacos: $breakfast</li>\n";
		print "<li class='list-group-item'>Drink: $showDrink</li>\n";
		print "</ul>";
	}
	function showCustomerForm($phone,$name='',$lastOrder= ''){
		print "name: <input type='text' id='name' value='$name'/>\n<br/>\n";
		print "</div>\n";
		print "<h3>Phone: \n";
		print "<span id='phoneSpan'>$phone</span>\n";
		print "</h3>\n";
	//	print "phone: $phone<br/>\n";
		print "<hr/>\n";
		showTacoOrder($lastOrder);	
	}
	function showTacoOrder($lastOrder){
		$db=adoConnectTacos();
		$query="SELECT * FROM TacoOrder WHERE OrderID='$lastOrder'";
		$drinkID=0;
		$numBreakfast=0;
		$numChicken=0;
		$numBeef=0;
		if($lastOrder){
			$result=$db->Execute($query);
			$row=$result->FetchRow();
			$drinkID=$row['DrinkID'];
			$numBreakfast=$row['NumBreakfest'];
			$numBeef=$row['NumBeef'];
			$numChicken=$row['NumChicken'];
		}
		
		showTaco("beef",$numBeef);
		showTaco("chicken",$numChicken);
		showTaco("breakfast",$numBreakfast);
		showDrink($drinkID);
		showButtons();
	}
	function showTaco($desc,$num){
		print "$desc:";
		print "<select name= '$desc' id ='$desc'>\n";
		for($i=0;$i<=10;$i++){
			if ($i ==$num){
				$sel="Selected";
			}
			else{
				$sel ="";
			}
		print "<option value='$i' $sel >$i</option>\n";
			
		}
		print "</select></br>\n";
	}
		function showDrink($desc){
		$db=adoConnectTacos();
		$db->false;
		$query = "SELECT * FROM Drink";
		$result = $db->Execute($query);
		$numDrinks=$result->RowCount();
		print "<select name= 'drink' id ='drink'>\n";
		//$count = $result->RowCount();
		//print "This is the row count: $count";
		
		if($result){
			for($i=0;$i<$numDrinks;$i++){
				$row=$result->FetchRow($i);
				$drinkID = $row[0];
				$drinkDesc = $row[1];
				if($i==$desc){
					$sel = "Selected";
				}
				else{
					$sel = "";
				}
				print "<option value='$drinkID' $sel >$drinkDesc</option>\n";
			}
			//$row=$result->FetchRow();
			//print $row;			
		}
		else{
			print "Failed to Query";
		}
		
		print $row;
			
		print "<select id ='$desc'>\n";
		
		print "</select>\n";
	}
	function showButtons(){
		print "</br>\n";
		print "<button id = 'submitOrder' type = 'submit' class='btn btn-default'>Submit</button>\n";
		print "<button id = 'clearOrder' type = 'submit' class='btn btn-default'>Clear</button>\n";
	}

?>