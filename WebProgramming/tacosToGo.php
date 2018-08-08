<?
//opening page
	//shows form for user to enter phone number with submit form
require_once "mydb.php";
require_once "classfun.php";
printDocHeadingJS("bootstrap.css","Tacos-to-Go","asgTacos.js");
//print "<script src=></script>";
print
	"<body>\n".
	"<div class='container'> \n".
	"<div class='jumbotron'>\n".
	"  <h1>Tacos-to-Go</h1>\n".
	"</div>\n".
	"</div>\n".
	"</body>\n";
print "<body>\n";
print"<div id='mainDiv' class = 'container'>\n";
print"<div id='phoneDiv'>\n";
print "<h3>Enter Phone Number: </h3>\n";
print "<input id ='textPhone' type='text'>\n";
print "<button id = 'submitPhone' type = 'submit' class='btn btn-default'>Submit</button>\n";
print "</div>\n";
print"<div id='orderDiv'>\n";
print "</div>\n";
print"<div id='finishDiv'>\n";
print "</div>\n";
print "</div>\n";
print "<div class = 'container'>\n";
printDocFooter();
print "</div>\n";
?>