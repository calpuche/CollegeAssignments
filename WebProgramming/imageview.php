<?
//expects 2 GET arguements - view and idea
require_once "mydb.php";
$view = htmlentities($_GET['view'],ENT_QUOTES);
$id = htmlentities($_GET['id'],ENT_QUOTES);
$db = adoConnect();
$db->debug = false;
if($view=="thumb"){
	$query = "SELECT ThumbImage FROM Images WHERE ImageId = '$id'";
}
else{
	$query = "SELECT FullImage FROM Images WHERE ImageId = '$id'";
}
$result = $db ->Execute($query);
$row=$result->FetchRow();
if($view=="thumb"){
	$bytes = $row['ThumbImage'];
}
else{
	$bytes = $row['FullImage'];
}
header("Content-type:image/jpg");
print $bytes;

?>