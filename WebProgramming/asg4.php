<?php
//assignment 4. It is a blog
//September 2015
//cosc2328

require "classfun.php";
printDocHeading("bootstrap.css", "ASG #4");
printAll();
	
print 
	"<body>\n".
	"<div class='container'> \n".
	"<div class='jumbotron'>\n".
	"  <h1>The Greatest Blog on Earth</h1>\n".
	"</div>\n".
	"</div>\n".
	"<!--  end of content  -->\n".
	"</body>\n";
	//PageLogic
if(empty($_POST)){
showBlog();
showAddClear();
}
elseif($_POST['addEntry'])
	{
		showForm();
	}
elseif($_POST['submitEntry'])
	{
		processAddEntry();
	}
elseif($_POST['clearEntry'])
	{
		askConfirm();
	}
elseif($_POST['submitClearConfirm'])
	{
		clearBlog();
		showAddClear();
		showBlog();
	}
elseif($_POST['cancel'])
{
	startOverLink();
}
	printDocFooter();
	//Functions
function showBlog(){
	$filename = "../../xj13b/blog.txt";
	$contents= file_get_contents($filename);
	print $contents;
	}
function showAddClear(){
	$self = $_SERVER['PHP_SELF'];
	print "<div><form method = 'post' action = '$self'>\n";
	print "<input type = 'submit' name = 'addEntry' value='Add a Blog' /n>";
	print "<input type = 'submit' name = 'clearEntry' value='Clear Blog' /n>";
	print "</form>\n</div>\n";
	}
function showForm($aName="", $aHome="")
{
	$self = $_SERVER['PHP_SELF'];
	print "<div><form method = 'post' action = $self>\n";
	print "<h5> Enter Entry:</h5>\n";
	print "<textarea name = 'entry' rows = '10' cols = '100'></textarea>\n";
	print "<input type = 'submit' name = 'submitEntry' value='Submit Blog' /n>";
	print "</form>\n</div>\n";
}
function processAddEntry(){
		$blog = htmlentities($_POST['entry'], ENT_QUOTES);
		$blog = str_replace("|","---",$blog);
		if($blog ==""){
			return;
		}
		else{
			$filename = "../../xj13b/blog.txt";
			$contents = file_get_contents($filename);
			$fh = fopen($filename,"w");
			$date = date("D m/d/Y H:i:s a");
			$out = $date.":".$blog."<br/><br?>\n".$contents;
			fwrite ($fh,$out, strlen($out));
			fclose($fh);
		}
		startOverLink();
	}
function askConfirm(){
	print "<h1> Are you sure you want to clear the blog?</h1>";
	$self = $_SERVER['PHP_SELF'];
	print "<div><form method = 'post' action = '$self'>\n";
	print "<input type = 'submit' name = 'submitClearConfirm' value='Accept' /n>";
	print "<input type = 'submit' name = 'cancel' value='Cancel' /n>";
	print "</form>\n</div>\n";
	}
function clearBlog(){
	$filename = "../../xj13b/blog.txt";
    $fh = fopen($filename,'w');
	$out = "";
	fwrite($fh,$out);
    fclose($fh);
	}
?>