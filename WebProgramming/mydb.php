<?
require_once "../adodb5/adodb.inc.php";
function adoConnect(){
	$host = "localhost";
	$database = "calpuche_asg7";
	$user = "calpuche_bob";
	$password = "cosc2328";
	$db = ADONewConnection("mysqli");
	$db -> Connect($host,$user,$password,$database);
	return $db;
}
function adoConnectTacos(){
	$host = "localhost";
	$database = "calpuche_Tacos";
	$user = "calpuche_bob";
	$password = "cosc2328";
	$db = ADONewConnection("mysqli");
	$db -> Connect($host,$user,$password,$database);
	return $db;
}
?>