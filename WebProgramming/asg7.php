<?
//Carlos Alpuche
//Asg #7 Web Development
//UserName: Admin
//Password: Password

require_once "mydb.php";
require_once "classfun.php";
session_start();

printDocHeading("bootstrap.css","Photo Gallery");
print
	"<body>\n".
	"<div class='container'> \n".
	"<div class='jumbotron'>\n".
	"  <h1>Photo Gallery</h1>\n".
	"</div>\n".
	"</div>\n".
	"<!--  end of content  -->\n".
	"</body>\n";

if(!$_SESSION['valid']== true){
	showLogin();
}
if($_POST['submit']){
	checkCredentials();
}
if($_SESSION['valid']==1){

	if($_POST['view']){
		showCategoryView();
		uploadImage();
		logOut();
	}
	elseif($_POST['imageUpload']){
		processUpload();
		uploadImage();
		logOut();
	}
	else{
		showCategoryForm();
		showAllImages();
		uploadImage();
		logOut();
	}
	if($_POST['logout']){
	$_SESSION["valid"] = false;
	}
}


printDocFooter();
function showCategoryView(){
	
	//Posted 'view' -> one of my category values
	$value = htmlentities($_POST['ImageCategory'], ENT_QUOTES);
	$db = adoConnect(); //gives me connection to database
	//$db -> debug=true; //turn off when turning on
	$query = "SELECT * FROM Images WHERE ImageCategory = '$value'";
	$result = $db ->Execute($query);
	print "<h3>$value</h3>";
	while($row=$result->fetchRow()){
		print "<div>\n";
		$id=$row['ImageId'];
		$imageTag="<img src='imageview.php?view=thumb&id= $id' alt = 'an image'/>\n";
		$href = "<a href = 'imageview.php?view=full&id=$id'>$imageTag</a>\n";
		print $href."<br/>";
		print "</div>\n";
	}
}
function processUpload(){
	//check size
	//checks for errors
	//check type
	//if all is ok
	//move uploaded file to temp folder (777 prevlidge)
	//create thumb image in temp
	//read contents of full.jpg and ad slashes
	
	
	
	$db = adoConnect();
	$db->debug = false;
	$cat=$_POST['ImageCategory'];
	$fullpath = "./temptemp/image.jpg";
	$thumbpath = "./temptemp/thumb.jpg";
	$width = 100;
	move_uploaded_file($_FILES ['myfile'] ['tmp_name'], "./temptemp/image.jpg");
	if ($_FILES['myfile'] ['tmp_name'] != "image/jpeg"){
		print "<h1> Image was not in jpeg format, please try again!</h1>";
		return;
	}
	else{
		createThumb($fullpath,$thumbpath,$width);
	$fullimage = file_get_contents($fullpath);
	$thumbimage = file_get_contents($thumbpath);
	$fullimage = $db->qstr($fullimage);
	$thumbimage = $db->qstr($thumbimage);
	//set up insert query
	$query = "Insert into Images (ImageCategory,ThumbImage,FullImage) values('$cat',$thumbimage,$fullimage)";
	$result = $db->Execute($query);
	}
}
function showAllImages(){
	$db =adoConnect();
	$query = "SELECT * FROM Images";
	$res = $db->Execute($query);
	if(!$res){
		print "<h3>No Images Found</h3>\n";
		return;
	}
	$query = "SELECT ImageId, ImageCategory FROM Images";
	$result = $db->Execute($query);
	while($row= $result->FetchRow()){
		$id=$row['ImageId'];
		$cat = $row['ImageCategory'];
		$imageTag="<img src='imageview.php?view=thumb&id= $id' alt = 'an image'/>\n";
		$href = "<a href = 'imageview.php?view=full&id=$id'>$imageTag</a>\n";
		print $cat;
	print $href."<br/>";
	}
}
function showLogin(){
	print "<body>\n";

	print "<div class='container'>\n";

	print "<h2 class='form-signin-heading'>Please sign in</h2>\n";

	print "<form class='form-signin' name = 'login' method = 'post'>\n";
	print "<input type='text' name='username' class='form-control' placeholder='UserName' required autofocus><br>\n";
	print "<input type='password' name='password' class='form-control' placeholder='Password' required><br>\n";
	print "<input class='btn btn-lg btn-primary btn-block' type='submit' name='submit' value='login'>\n";
	print "</div>\n";
	print "</body>\n";
}
function checkCredentials(){
	$user= 'Admin';
	$pass= 'Password';
	$userEntered = htmlentities($_POST['username'],ENT_QUOTES);
	$passEntered = htmlentities($_POST['password'],ENT_QUOTES);
	if ($user == $userEntered && $pass==$passEntered){
		 $_SESSION["valid"] = true;
		$_POST = "";
	}
	else{
		print "<h2>Invalid UserName or Password, Please try again!";
	}


}
function logOut(){
	$self = $_SERVER['PHP_SELF'];
	print "<form method='post' action = '$self'>\n";
	print "<input type = 'submit' name = 'logout' value = 'LogOut'/>\n";


}
function uploadImage(){
	$self = $_SERVER['PHP_SELF'];
	print "<form method='post' enctype='multipart/form-data' action = '$self'>\n";
	print "<input type = 'file' name = 'myfile' value = 'uploadFile'/>\n";
	$result = enumDropdown("Images","ImageCategory",true);
	print "<input type = 'hidden' name = 'MAX_FILE_SIZE' value = '2000'/>\n";
	print "<input type = 'submit' name = 'imageUpload' value= 'submitNow'/>\n";
	print "</form>\n";
	move_uploaded_file($_FILES ['myfile'] ['tmp_name'], "./temptemp/image.jpg");

	}
function createThumb($imageFile,$thumbFile,$thumbWidth )
{
  $img = imagecreatefromjpeg("$imageFile");
  $width = imagesx( $img );
  $height = imagesy( $img );
  $new_width = $thumbWidth;
  $new_height = floor( $height * ( $thumbWidth / $width ) );
  $tmp_img = imagecreatetruecolor( $new_width, $new_height );
  imagecopyresized( $tmp_img, $img, 0, 0, 0, 0,$new_width, $new_height, $width, $height );
  imagejpeg( $tmp_img, $thumbFile );
}
function enumDropdown($table_name, $column_name, $echo = false)
{
$db = adoConnect();

$selectDropdown = "<select name='$column_name'>\n";
$query="SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS " .
" WHERE TABLE_NAME = '$table_name' AND COLUMN_NAME = '$column_name'";

$result = $db ->Execute($query);
$row = $result -> FetchRow();
$enumList = explode(",", str_replace("'", "", substr($row['COLUMN_TYPE'], 5, (strlen($row['COLUMN_TYPE'])-6))));

foreach($enumList as $value)
$selectDropdown .= "<option value='$value'>$value</option>\n";

$selectDropdown .= "</select>\n";

// if true is sent in, it will print the dropdown before returning it.

if ($echo)
echo $selectDropdown;

return $selectDropdown;
}
function showCategoryForm(){
	$self = $_SERVER['PHP_SELF'];
	print "<form method='post' enctype='multipart/form-data' action = '$self'>\n";
	$result = enumDropdown("Images","ImageCategory",true);
	print "<input type = 'submit' name = 'view' value= 'submitNow'/>\n";
	print "</form>\n";
}


?>
