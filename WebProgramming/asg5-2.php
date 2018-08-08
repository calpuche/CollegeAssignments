<?php
require "classfun.php";
printDocHeading("layout.css","Madlibs Assignment");

print "<h1>Madlibs</h1>\n";


if(empty($_POST)){
	showForm($storyName);
}
elseif($_POST['submitStory']){
	showStory();
}
elseif($_POST['submitWords']){
	createStory();
}
printDocFooter();
function showForm($storyName){
	$storyName=array("Love Letter"=>1,"The Office"=>2,"Fairy Tale"=>3);
	$self = $_SERVER['PHP_SELF'];
	print "<form method = 'post' action='$self'>\n";
	print "\<select name = 'storyChoice'>\n";
	foreach($storyName as $key=>$value){
		print "<option value = '$value'>$key</option>\n";
	}
	print "</select>\n";
	print "<input type = 'submit' name='submitStory' value= 'Submit Story'/>\n";
	print "</form>\n";
		
	}
function showStory(){
	$choice = htmlentities($_POST['storyChoice'],ENT_QUOTES);
	if($choice<1 || $choice>3){
		print "<h1> ERROR. Please Try Again</h1>";
	}
	else{
		$self = $_SERVER['PHP_SELF'];
		$storyfile="stories/story".$choice.".txt";
		$contents=file_get_contents($storyfile);
		print"<h2>The Original File:</h2>";
		print "<p>".$contents."</p>\n";
		$subfile="stories/substory".$choice.".txt";
		$subcontents=file_get_contents($subfile);
		print"<h2>The Story File:</h2>";
		print"<p>".$subcontents."</p>\n";
		$pattern="/\[(.+?)\]/";
		preg_match_all($pattern,$subcontents,$matches);
		print"<h2>Here are the Whole Matches:</h2>";
		print "<ul>\n";
		for($i = 0;$i<count($matches[0]);$i++){
			print "<li>".$matches[0][$i]."</li>\n";
		}
		print "</ul>\n";
		print"<h2>Here are the 1st Subgroup Matches:</h2>";
		print "<ul>\n";
		for($i = 0;$i<count($matches[1]);$i++){
			$clue = str_replace("_"," ",$matches[1][$i]);
			print "<li>".$clue."</li>\n";
		}
		print "</ul>\n";
		print "<form method = 'post' action='$self'>\n";
		for($i = 0;$i<count($matches[1]);$i++){
			$clue = str_replace("_"," ",$matches[1][$i]);
			print "$clue:<input type = 'text' name ='".$matches[1][$i]."' value = ''/><br/>\n";
		}
		print "<input type = 'hidden' name = 'storyChoice' value ='$choice'/>\n";
		print "<input type = 'submit' name='submitWords' value= 'Submit Words'/>\n";
		print"</form>\n";
	}
}
function createStory(){
	$choice = htmlentities($_POST['storyChoice'],ENT_QUOTES);
	$subfile="stories/substory".$choice.".txt";
	$subcontents=file_get_contents($subfile);
	$pattern="/\[(.+?)\]/";
	preg_match_all($pattern,$subcontents,$matches);
	foreach($_POST as $key => $value ) {
		
		for($i = 0;$i<count($matches[0]);$i++){
			$subcontents = str_replace($matches[0][$i],$value,$subcontents);
			
		}
		
}
print "<p>".$subcontents."</p>\n";
}
?>