#!/home/calpuche/virtualenv/python3/3.4/bin

def printDocHeading(stylesheet, title):
	print("Content-type:text:text/html \n")
	print("<DOCTYPE html\n")
    print("<html lang='eng'>\n")
    print("<head>\n")
    print("<meta charset='utf-8'/>\n")
    print("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n")
    print("<meta name='author' content='Carlos Alpuche'>\n")
    print("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>|n")
    print("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>\n")
    print("<link rel='STYLESHEET' href=" + stylesheet + "  type='text/css' />\n")
    print("<title>\n" + title + "\n</title>\n")
    print("</head>\n")



# outputs a document heading tag and 
# stylesheet link, and a title tag
#  $stylesheet - name of stylesheet relative to this page
#  $title - title of page 
#  $jsfile1 - javascript file to be used in page
#  $jsfile2 - optional javascript file for page
def printDocHeadingJS($stylesheet, $title, $jsfile2=""):
jsfile1 = "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js";
    print('<!DOCTYPE html>'. "\n" .
    print('<html lang="en">' . "\n" .
    print('<head> ' ."\n" .
    print('<meta charset="utf-8" />'. "\n".
    print('<meta name="viewport" content="width=device-width, initial-scale=1.0">' ."\n".
    print('<meta name="author" content="Carlos Alpuche">'. "\n".
    print('<link rel="shortcut icon" href="../favicon.png">' . "\n". 
    print('<link rel="STYLESHEET" href="' . $stylesheet . '"  type="text/css" />'."\n" .
    print('<script type="text/javascript" src="'.$jsfile1.'"> </script>' . "\n";
    if($jsfile2): 
       print('<script type="text/javascript" src="'.$jsfile2.'"> </script>' . "\n";
       print('<title>' . "\n" .$title . "\n" .'</title> ' ."\n" .
       print('</head> ' . "\n" ;
# end of printDocHeadingJS

// prints a page footer in small font with copyright
function printDocFooter()
{
  print "<div> \n" ;
  print "<div> "."\n" ;
  print "&copy; St. Edward's University &nbsp;&nbsp;&nbsp;&nbsp;\n " .
        "Page Last Updated:\n " .
        " <script type='text/javascript'>\n " .
        " document.write(document.lastModified); \n" .
        " </script> &nbsp;&nbsp;&nbsp; \n" .
        " <a href='http://validator.w3.org/check?uri=referer'> \n" .
        "  <strong>HTML</strong></a>\n " .
        " &nbsp;&nbsp;&nbsp;&nbsp; \n" .
        " <a href='http://jigsaw.w3.org/css-validator/check?uri=referer'> \n" .
        "  <strong>CSS</strong></a>\n ";
  print "</div>\n ";
  print "</div>\n";  // end of maincontent
  print "</body>\n</html>\n";
}//end printDocFooter 

//function that outputs a link that links back to this script with no data sent
// returns back to the beginning as a link.
function startOverLink()
{
  $self = $_SERVER['PHP_SELF'];
  print "<a href='$self'>Back to Start</a>\n";
}
// function that outputs the start of a form with method as POST and default encryption type
// action of the form is always self.
function startForm()
{
  $self = $_SERVER['PHP_SELF'];
  print "\n<form method = 'post' action = '$self' >\n";
}
// prints POST array and SESSION array
function printAll()
{
  print "POST array: \n";
  print_r($_POST);
  print "<br /> SESSION array: \n";
  print_r($_SESSION);
  print "<br />\n";
}// end of printAll

?>