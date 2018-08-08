<?
require "classfun.php";
printDocHeading("bootstrap.css","Photo Gallery");
print
	"<body>\n".
	"<div class='container'> \n".
	"<div class='jumbotron'>\n".
	"  <h1>Pattern Matching</h1>\n".
	"</div>\n".
	"</div>\n".
	"<!--  end of content  -->\n".
	"</body>\n";
	print "<body>";
	print "<div class='container'> \n";
	print "<div class='center-block'>";
	print "<h3>Pattern 1 Matching</h3>\n";
	$pattern="/[a-f]\d[t-z]/";
	print "Pattern is :".$pattern."<br>";
	print "<br>\n";
	$subject="33mkd4m";
	
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	$subject="44bc9x";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	$subject="k2ze7w53";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	print "<h3>Pattern 2 Matching</h3>\n";
	$pattern="/[QxY]{2}.[fg]\[\]/";
	print "Pattern is :".$pattern."<br>";
	print "<br>\n";
	$subject="44xYzf[QYwwf]";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	$subject="AKePxQg[]";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";

	
	$subject="abYYf[]rq2]";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	print "<h3>Pattern 3 Matching</h3>\n";
	$pattern = "/^[F-X]{2}\d{3}$/";
	print "Pattern is :".$pattern."<br>";
	print "<br>\n";
	$subject ="AMPT247KKP-276";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	$subject="GR654";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "<br>\n";
	
	$subject="LLMN93383";
	print "subject is :".$subject."<br>";
	preg_match($pattern,$subject,$matches);
	if ($matches[0]){
	print "The match is :".$matches[0]."<br>";
	}
	else{
		print "No matches found!<br>";
	}
	print "</div>\n";
	print "</div>\n";
	print "</body>";
	
	
	printDocFooter();
?>