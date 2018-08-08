<?php
require "classfun.php";
printDocHeading("layout.css","Regex Assignment");
print "<body>\n<div>";
print "<h2> Regex Assignment</h2>\n";
showContent();

printDocFooter();



function showContent(){
	$filename = "ssn/ssn.txt";
	$contents = file_get_contents($filename);
	$contents = nl2br($contents);
	print "<h2>Original File</h2>";
	print "<p>".$contents."</p>\n";
	$pattern = "/(\d{3})[- .]?(\d{2})[- .]?(\d{4})/";
	preg_match_all($pattern,$contents,$matches);
	for($i = 0;$i<count($matches[0]);$i++){
		$part1 = $matches[1][$i];
		$part2 = $matches[2][$i];
		$part3 = $matches[3][$i];
		$ssn=$part1.'-'.$part2.'-'.$part3;
		$contents = str_replace($matches[0][$i],$ssn,$contents);
	}
	print "<h2>Fixed File</h2>";
	print($contents);


	
}


?>