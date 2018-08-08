<?php
 # in class example 1 for learning how to create
 #  a simple webpage generated (output) by php
 #  August 2015
 # cosc 2328
 require "./classfun.php";   # gets functions we will use
 ## call a method to output the document's page heading
 printDocHeading("./start.css", "Sample Page Title");
 # now begin output of our html content
print 
  "<!--  body of document starts here  -->".
  "<body>\n".
  "<div class='heading'>\n".
  "  <h2>  Carlos Homepage</h2>\n".
  "</div>\n".
  "<div class='content'>\n".
  "<!--  float left starts here -->\n".
  " <div class='floatleft'>\n".
  " <h3> My major </h3>\n".
  "    Computer Science \n".
  " <h4> My hobbies </h4>\n".
  " <ul> \n".
  " <li> Trumpet </li>\n".
  " <li> Soccer </li>\n".
  " <li> Concerts </li>\n".
  " <li> Traveling </li>\n".
  " </ul>\n".
  " <h5> Some helpful links: </h5>\n".
  "<a href='http://validator.w3.org'>\n".
  "  html validator </a> \n".
  " <br /><br />\n".
  " <a href='http://w3schools.com/html/default.asp'>".
  " html reference </a> \n".
  "  <br /><br />\n".
  "  <a href='http://w3schools.com/css/default.asp'>".
  " css reference </a> \n".
  "  <br /><br />\n".
  "<a href = 'http://websitesetup.org/html5-cheat-sheet/'>".
   "Cheats for HTML 5 </a>\n".
  "  <br /><br />\n".
  "<a href='http://www.codecademy.com/dashboard'> CodeAcademy </a>".
  "  <br /><br />\n".
  
  " </div>\n".
  "<!--  end of float left  -->\n".
  "<!--  start new float left  --> \n".
  " <div class='floatleft'>\n".
  "  <div> \n".
  "  <br />\n".
  "     Me Saving the World ....\n".
  "  <br /> \n".
  " <img src='img001.jpg' alt='Me saving the world' />\n".
  "  </div>\n".
  " </div>\n".
  "<!--  end of float left  --> \n".
  "<div class='clear'></div> \n".
  "<p class='footer'>\n".
  " St. Edward&apos;s University .:. ".
  "cosc 2328 - web programming .:. fall 2015\n".
  "</p>\n".
  "</div>\n".
  "<!--  end of content  -->\n".
  "</body>\n".
  "</html>\n";


?>
