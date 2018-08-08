<?
require_once "../../adodb5/adodb.inc.php";
function dbConnect(){
	$host = "localhost";
	$database = "calpuche_copOrDropProd";
	$user = "calpuche_bob";
	$password = "cosc2328";
	$db = ADONewConnection("mysqli");
	$db -> Connect($host,$user,$password,$database);
	return $db;
}
?>