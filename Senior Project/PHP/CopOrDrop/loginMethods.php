<?php
require_once "./mydb.php";
function checkCredentials()
{
    $db = dbConnect();
    $emailEntered = htmlentities($_POST['email'], ENT_QUOTES);
    $passEntered = htmlentities($_POST['password'], ENT_QUOTES);
    $query = "SELECT email, password FROM account WHERE email = " . $emailEntered . " AND password = " . passEntered;
    $result = $db->Execute($query);
    print $result;

    if ($count == 1)
    {
        $_SESSION['login_user'] = $emailEntered;
        //enter the welcome page down here in post
        header("location: home.php");
    }
    else
    {
        print "<h2>Invalid UserName or Password, Please try again!";
    }
}
?>
