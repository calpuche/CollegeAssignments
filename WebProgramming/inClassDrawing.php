<?php
// script to draw a few simple shapes on a canvas
// demonstrates use of php graphics library


// create a blank image
$image = imagecreatetruecolor(1000, 800);

//create text on image



// choose a color for the ellipse
$gray = imagecolorallocate($image, 96,105,105);
$orange = imagecolorallocate($image, 209, 145, 60);
$purple = imagecolorallocate($image, 134, 54, 181);
$blueGreen = imagecolorallocate($image, 102, 153, 153);
$red = imagecolorallocate($image, 165, 50, 66);
// put a gray background on the image

imagefill($image, 0, 0, $gray);


   $trianglePoints = array(900,450,700,250,700,650,900,450);
   imageellipse($image, 100, 80, 200, 160, $orange);
   imagerectangle($image, 300, 100, 400, 200, $purple);
   imagepolygon($image, $trianglePoints, 4, $blueGreen);
   imagestring($image,5,500,500,"My First Drawing",$red);
 // save the image out to temp directory
 imagepng   ( $image, "pictures/myImage.png" );
 // output the image
 header("Content-type: image/png");
 imagepng($image);

?>