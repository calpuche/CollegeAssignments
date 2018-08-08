<?php
require "./startupPage.php";
printDocHeading("../bootstrap.css", "Run Python");
print "<body>";
print "<div class='container-fluid'>";
print "test";

exec('/home/calpuche/virtualenv/python35/3.5/bin/python test.py', $output, $return);
print_r($output);
print_r($return);

shell_exec(escapeshellcmd('/home/calpuche/virtualenv/python35/3.5/bin/python test.py'));


print "</div>\n";
print "</body>\n";



?>