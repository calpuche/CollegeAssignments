<?php
include ('./session.php');
require "./startupPage.php";
session_start();
//require "../myitemsdb.php";
## call a method to output the document's page heading
printDocHeading("../bootstrap.css", "HomePage");
# now begin output of our html content

echo '<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">';
echo '<div class="container">';
echo '<a class="navbar-brand"><b><font color="white">Cop or Drop</font></b></a>';
echo '<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">';
echo '<span class="navbar-toggler-icon"></span>';
echo '</button>';
echo '<div class="collapse navbar-collapse" id="navbarResponsive">';
echo '<ul class="navbar-nav ml-auto">';
echo '<li class="nav-item active">';
//make this home button go home
echo '<a class="nav-link" href="#">Home';
echo '<span class="sr-only">(current)</span>';
echo '</a>';
echo '</li>';
echo '<li class="nav-item">';
// add logout script here

print "<form class='nav-link' action='Logout.php' 'method='post'>";
print "<input name='logout' value='Log Out' type='submit' />";
print "</form>";
echo '</li>';
echo '</ul>';
echo '</div>';
echo '</div>';
	print "<p>&nbsp;</p>";

echo '</nav>';
print "<body style='background-color:LIGHTSTEELBLUE;'>";
print "<p>&nbsp;</p>";
print "<div class='container' style='background-color:white;'>\n";
print "<p>&nbsp;</p>";


showAllImages();

function showAllImages(){
    $db = dbConnect();
	$query = "SELECT itemID,title,description FROM item";
	$result = $db->Execute($query);
	if(!$result){
		print "<h3>No Items Found</h3>\n";
		print "<h3>Contact Admin!</h3>\n";
		return;
	}
	print "<div class = 'container' style='background-color:white;'>";
	print "<div class = 'row'>";

	$counter = 0;
	while($row= $result->FetchRow()){
	    if($counter%3==0){
	        print "</div>\n";
	       	print "<div class = 'row'>";
	    }
	    print "<div class='card' style='width: 20rem;'>";
	    $itemID=$row['itemID'];
		$title=$row['title'];
		$description = $row['description'];
		$imageTag="<img class='card-img-top' width='30%'  src='images/".$title.".jpg'/>\n";
		print $imageTag;
		print "<div class='card-body'>";
		print "<h5 class='card-title'>$description</h5>\n";
		print "<form action='./preorder/index.php' method='post' action=$self >\n";
        print "<input  type='hidden' name='itemID' value='$itemID'/> \n";
        print "<input type='submit' class='btn btn-primary' name='PreOrder' value='Pre-Order' id='$description'/>\n";
        print "</form>\n";
        print "<br/>";
        print "</div>\n";
    	print "</div>\n";
    	
    	$counter = $counter + 1;
	}
	print "</div>\n";
	print "<p>&nbsp;</p>";
	print "</div>\n";
	print "</div>\n";
}

if ($_SERVER["REQUEST_METHOD"] == "POST")
{
    //header("location: ./preorder/index.php");    
    
}
print "</body>";
?>
