<?
require "classfun.php";
printDocHeading("bootstrap.css","Thumb Creator");
print
	"<body>\n".
	"<div class='container'> \n".
	"<div class='jumbotron'>\n".
	"  <h1>Thumb Image Creator</h1>\n".
	"</div>\n".
	"</div>\n".
	"<!--  end of content  -->\n".
	"</body>\n";
	print "<div class='container'>";
if (empty($_POST)) {
  showForm();
}

elseif ($_POST['submitFile']) {
  processFile();
}
function showForm() {
	$self = $_SERVER['PHP_SELF'];
	print "<form method='post' enctype='multipart/form-data' action='$self'>\n";
	print "<input type='file' name='myfile' value='uploadfile' /> \n";
	print "<input type='hidden' name='MAX_FILE_SIZE' value='2000' /> \n";
	print "<h2> Thumb Image Size(Must be between 50 and 150): </h2>\n";
	print "<input type= 'text' name = 'width' value = '' placeholder='Thumb Size' required />\n ";
	print "<input class='btn btn-default navbar-btn' type='submit' name='submitFile' value='Submit File' />\n";
	print "</form>\n";
  move_uploaded_file($_FILES ['myfile'] ['tmp_name'], "temp/image.jpg" );
}

function createThumb( $imageFile, $thumbFile, $thumbWidth )
{

  $img = imagecreatefromjpeg($imageFile);

  $width = imagesx( $img );
  $height = imagesy( $img );
  $new_width = $thumbWidth;
  $new_height = floor( $height * ( $new_width / $width ) );
  $tmp_img = imagecreatetruecolor( $new_width, $new_height );
  imagecopyresized( $tmp_img, $img, 0, 0, 0, 0,$new_width, $new_height, $width, $height );
  imagejpeg( $tmp_img, $thumbFile );
  print "<h2> Thumbnail Image </h2> \n ";
        print "<img src='pictures/thumb.jpg' />";
}
function processFile() {
  $thumbWidth  = htmlentities($_POST['width'],ENT_QUOTES);
  if ($thumbWidth<50 || $thumbW>150) {
    print "Please enter a thumbwidth between 50 and 150. ";
    showForm();
    return;
  }
	$file="temp/image.jpg";
	$check=getimagesize($_FILES["myfile"]["tmp_name"]);
    if($check !== false) {
      $imageFileType=pathinfo($file,PATHINFO_EXTENSION);
      $didUpload=1;
    }
    if ($imageFileType != "jpg" && $imageFileType != "jpeg") {
      $didUpload = 0;
      print "The file you attempted to upload is not in jpg/jpeg format. Please try again! <br/>";
    }
    //Checks if file is above 2MB. If so, complete form again.
    if ($_FILES['myfile']['size'] > 2000000) {
      print "File is over 2MB. Please choose a file that is less than 2 MB. </br> ";
      $didUpload=0;
    }
    if ($didUpload == 0) {
    //echo "Sorry, your file was not uploaded.";

    showForm();
    return;
// if everything is ok, try to upload file
  }
  else {
    if (move_uploaded_file($_FILES["myfile"]["tmp_name"], $file)) {
        print "<h2> Original Image </h2>\n";
        //Limits width to 700 pixels
        print "<img src='temp/image.jpg' width='700' /></br> \n";
        //Upon completion, thumbnail will be created
        $thumbFileOrg = "temp/thumb.jpg";
        createThumb("temp/image.jpg", $thumbFileOrg, $thumbWidth);
        print "</br> </br>";
        startOverLink();
      } else {
        print "<h2>Error Occured. Please try again!</h2>";
     }
    }
  }
?>
