<?php
session_start();
require_once ('./mydb.php');
$user_check = $_SESSION['login_user'];

if ($user_check == null){
    header("location: ");

}
else{
    $db = dbConnect();
    $query = "SELECT email FROM account WHERE email ='" . $user_check ."'" ;
    $result=$db->Execute($query); 
    $row = $result->FetchRow();
    $login_session = $row['username'];
}

if (!isset($_SESSION['login_user']))
{
    header("location: ../");

}
?>
