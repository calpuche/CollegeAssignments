<?php
include ('./session.php');
require "../startupPage.php";
session_start();
//require "../myitemsdb.php";
## call a method to output the document's page heading
printDocHeading("../bootstrap.css", "Items Admin Page");
# now begin output of our html content
print "<link rel='stylesheet' href='navigataur.css'>";
print "<body>";
print "<div class='header'>";
print "<input type=\"checkbox\" id=\"toggle\" />\n";
print "<div>\n";
print "<label for=\"toggle\" class=\"toggle\" data-open=\"Main Menu\" data-close=\"Close Menu\" onclick></label>\n";
print "<ul class=\"menu\">\n";
print "<li><a href=\"#\">Add Item</a></li>\n";
print "<li><a href=\"#\">Delete Item</a></li>\n";
print "<li><a href=\"#\"></a></li>\n";
print "<li><a href=\"#\"></a></li>\n";
print "</ul>\n";
print "</div>";
print "</div>";

print "<div class='container'>";
print "<div class='row main'>";
print "<div class='panel-heading'>";
print "<div class='panel-title text-center'>";
print "<h1>Adminstrative Item Tools</h1>";
print "<hr/>";
print "</div>";
print "<h4>Add Item</h4>";
print "<!-- URL -->\n";
print "<form name='AddNewItem' class='form-horizontal' method='post'>";
print "<div class='form-group'>";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='url'>URL</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='url' name='url' placeholder='Insert URL Here' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";
print "<!-- Title -->\n";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='title'>Title</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='title' name='title' placeholder='Insert Title Here' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";
print "<!-- Type -->\n";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='type'>Type</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='type' name='type' placeholder='Insert Type Here' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";
print "<!-- Description -->\n";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='description'>Description</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='description' name='description' placeholder='Insert Description Here' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";
print "<!-- Release Date-->\n";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='releaseDate'>Release Date</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='releaseDate' name='releaseDate' placeholder='MM/DD/YYYY HH:MM' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";
print "<br>";
print "<button class='btn btn-primary' >Submit New Item</button>\n";
print "</div>\n";
print "</div>";
print "</form>";
print "</div>";
print "</div>";
showAllItems();
print "</body>";


if ($_SERVER["REQUEST_METHOD"] == "POST" && !$_POST['remove'] )
{
    $db = dbConnect();
    $url = htmlentities($_POST['url'], ENT_QUOTES);
    $title = htmlentities($_POST['title'], ENT_QUOTES);
    $type = htmlentities($_POST['type'], ENT_QUOTES);
    $type = strtoupper($type);
    $description = htmlentities($_POST['description'], ENT_QUOTES);
    $releaseDateString = htmlentities($_POST['releaseDate'], ENT_QUOTES);
    $releaseDate = date("Y-m-d H:i:s", strtotime($releaseDateString));
    $query = "INSERT INTO item VALUES(null,'" . $url . "','".$title."','" . $type . "','" . $description . "','0','" . $releaseDate . "')";
    $result = $db->Execute($query);

    if ($result == true)
    {
        print "<p style='color:green;'>The item has been successfully added!</p>";
        print "<meta http-equiv='refresh' content='1'>";
    }
    else
    {
        print "<p style='color:red;'>Error Occured, Try Again!</p>";
    }
}
elseif ($_POST['remove']) {
                        removeItem();
                }



function showAllItems(){
    $db = dbConnect();
	$query = "SELECT * FROM item";
	$result = $db->Execute($query);
	if(!$result){
		print "<h3>No Items Found</h3>\n";
		return;
	}
print "<div class='container'>";
print"<h2>All Items</h2>";
print "<table class='table'>";
print "<thead>\n";
print "<tr>\n";
print "<th>ItemID</th>\n";
print "<th>URL</th>\n";
print "<th>Title</th>\n";
print "<th>TYPE</th>\n";
print "<th>Description</th>\n";
print "<th>Released</th>\n";
print "<th>Release Date</th>\n";
print "<th></th>\n";
print "<th></th>\n";
print "</tr>\n";
print "</thead>";
print "<tbody>";
	while($row= $result->FetchRow()){
		$id=$row['itemID'];
		$url = $row['url'];
		$type = $row['type'];
		$title = $row['title'];
		$description = $row['description'];
		$released = $row['released'];
		$releaseDate = $row['releaseDate'];
        if($released){
            $released="Yes";
        }
        else{
            $released="No";

        }
print "<tr>\n";
print "<td>".$id."</td>\n";
print "<td>".$url."</td>\n";
print "<td>".$title."</td>\n";
print "<td>".$type."</td>\n";
print "<td>".$description."</td>\n";
print "<td>".$released."</td>\n";
print "<td>".$releaseDate."</td>\n";
print "<form method='post' action=$self >\n";
print "<input type='hidden' name='itemID' value='$id'/> \n";
print "<td><input type='submit' name='remove' value='Delete'/></td>\n";
print "</form>\n";
print "</tr>";
	}
print"</tbody>";
print "</table>";
print "</div'>";

}
function removeItem () {
        $self=$_SERVER['PHP_SELF'];
        $itemID=($_POST['itemID']);
        $db = dbConnect();
        $query="DELETE FROM item where itemID = '$itemID'";
        $result=$db->Execute($query);
        print "<p style='color:green;'>The item you selected has been successfully removed</p>";
        print "<meta http-equiv='refresh' content='1'>";

}



?>
