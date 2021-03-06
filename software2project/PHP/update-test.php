<?php

require_once "./dbConnection.php";

echo "<html lang='en'>\n";
echo "<head>\n";
echo '<meta charset="utf-8">';
echo '<meta http-equiv="X-UA-Compatible" content="IE=edge">';
echo '<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">';
echo '<meta name="description" content="">';
echo '<meta name="author" content="">';
echo '<title>Update Test</title>';
echo '<!-- Bootstrap core CSS-->';
echo '<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">';
echo '<!-- Custom fonts for this template-->';
echo '<link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">';
echo '<!-- Page level plugin CSS-->';
echo '<link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">';
echo '<!-- Custom styles for this template-->';
echo '<link href="css/sb-admin.css" rel="stylesheet">';
echo '';
echo '</head>';
echo '';
echo '<body class="fixed-nav sticky-footer bg-dark" id="page-top">';
echo '<!-- Navigation-->';
echo '<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">';
echo '';
echo '<div class="collapse navbar-collapse" id="navbarResponsive">';
echo '<ul class="navbar-nav navbar-sidenav" id="exampleAccordion">';
echo '<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">';
echo '';
echo '';
echo '<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Results">';
echo '<a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseResultsPages" data-parent="#exampleAccordion">';
echo '<i class="fa fa-fw fa-table"></i>';
echo '<span class="nav-link-text">Test Results</span>';
echo '</a>';
echo '<ul class="sidenav-second-level collapse" id="collapseResultsPages">';
echo '<li>';
echo '<a href="index.php">Test Results</a>';
echo '</li>';
echo '<li>';
echo '<a href="tests.php">Tests</a>';
echo '</li>';
echo '<li>';
echo '<a href="sut.php">Systems Under Test</a>';
echo '</li>';
echo '</ul>';
echo '</li>';
echo '';
echo '<li class="nav-item" data-toggle="tooltip" data-placement="right" title="SUT">';
echo '<a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseSUT" data-parent="#exampleAccordion">';
echo '<i class="fa fa-fw fa-file"></i>';
echo '<span class="nav-link-text">SUT</span>';
echo '</a>';
echo '<ul class="sidenav-second-level collapse" id="collapseSUT">';
echo '<li>';
echo '<a href="add-SUT.php">Add SUT</a>';
echo '</li>';
echo '<li>';
echo '<a href="update-SUT-form.php">Update SUT</a>';
echo '</li>';
echo '<li>';
echo '<a href="delete-SUT.php">Delete SUT</a>';
echo '</li>';
echo '</ul>';
echo '</li>';
echo '';
echo '<li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tests">';
echo '<a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseTestPages" data-parent="#exampleAccordion">';
echo '<i class="fa fa-fw fa-file"></i>';
echo '<span class="nav-link-text">Tests</span>';
echo '</a>';
echo '<ul class="sidenav-second-level collapse" id="collapseTestPages">';
echo '<li>';
echo '<a href="add-Test.php">Add Test</a>';
echo '</li>';
echo '<li>';
echo '<a href="update-test-form.php">Update Test</a>';
echo '</li>';
echo '<li>';
echo '<a href="delete-Test.php">Delete Test</a>';
echo '</li>';
echo '</ul>';
echo '</li>';
echo '';
echo '</ul>';
echo '<ul class="navbar-nav sidenav-toggler">';
echo '<li class="nav-item">';
echo '<a class="nav-link text-center" id="sidenavToggler">';
echo '<i class="fa fa-fw fa-angle-left"></i>';
echo '</a>';
echo '</li>';
echo '</ul>';
echo '';
echo '</div>';
echo '</nav>';
echo '';
echo '<div class="content-wrapper">';
echo '<div class="container-fluid">';
echo '<!-- Breadcrumbs-->';
echo '<ol class="breadcrumb">';
echo '<li class="breadcrumb-item active">Struix</li>';
echo '</ol>';
echo '</div>';
echo '';
echo '';

$testID=$_POST["testID"];
$db = adoConnect();
if(!$db){
       echo "<p>error cannot connect to db</p>" ;
}
$sql = "SELECT * from tests WHERE testID=".$testID;
$rs = $db ->Execute($sql);
// Make sure we have results
if (!$rs) {
    print_r($rs);
    print "<br>SQL select failed \n";
}

$testName =  $rs->fields['testName'];
$scriptName =  $rs->fields['scriptName'];
$dailyFrequenceCount =  $rs->fields['dailyFrequenceCount'];

echo '<div class="card mb-3">';
echo '<div class="card-header">';


print "<h4>Update Test</h4>";
print "<!-- Test Name -->\n";
print "<form name='UpdateTest' class='form-horizontal' method='post'>";
print "<div class='form-group'>";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='testName'>Test Name</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='testName' name='testName' value='$testName' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";

print "<!-- Script Name -->\n";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='scriptName'>Script Name</label>\n";
print "<div class='controls'>\n";
print "<input type='text' id='scriptName' name='scriptName' value='$scriptName' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";

print "<!-- Daily Frequency Count -->\n";
print "<div class='control-group'>\n";
print "<label class='control-label'  for='dailyFrequenceCount'>Maximum Daily Frequency</label>\n";
print "<div class='controls'>\n";
print "<input type='number' id='dailyFrequenceCount' name='dailyFrequenceCount' value='$dailyFrequenceCount' class='form-control' required='true'>\n";
print "</div>\n";
print "</div>\n";

print "<br>";
print "<input type='hidden' id='testID' name='testID' value='$testID'>\n";
print "<button class='btn btn-primary' >Update Test</button>\n";
print "</div>\n";
print "</div>";
print "</form>";


echo '<div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>';
echo '</div>';
echo '</div>';
echo '<!-- /.container-fluid-->';
echo '<!-- /.content-wrapper-->';
echo '<footer class="sticky-footer">';
echo '<div class="container">';
echo '<div class="text-center">';
echo '<small>Copyright © Team Six 2017</small>';
echo '</div>';
echo '</div>';
echo '</footer>';
echo '<!-- Scroll to Top Button-->';
echo '<a class="scroll-to-top rounded" href="#page-top">';
echo '<i class="fa fa-angle-up"></i>';
echo '</a>';
echo '<!-- Logout Modal-->';
echo '<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">';
echo '<div class="modal-dialog" role="document">';
echo '<div class="modal-content">';
echo '<div class="modal-header">';
echo '<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>';
echo '<button class="close" type="button" data-dismiss="modal" aria-label="Close">';
echo '<span aria-hidden="true">×</span>';
echo '</button>';
echo '</div>';
echo '<div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>';
echo '<div class="modal-footer">';
echo '<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>';
echo '<a class="btn btn-primary" href="login.html">Logout</a>';
echo '</div>';
echo '</div>';
echo '</div>';
echo '</div>';
echo '<!-- Bootstrap core JavaScript-->';
echo '<script src="vendor/jquery/jquery.min.js"></script>';
echo '<script src="vendor/popper/popper.min.js"></script>';
echo '<script src="vendor/bootstrap/js/bootstrap.min.js"></script>';
echo '<!-- Core plugin JavaScript-->';
echo '<script src="vendor/jquery-easing/jquery.easing.min.js"></script>';
echo '<!-- Page level plugin JavaScript-->';
echo '<script src="vendor/chart.js/Chart.min.js"></script>';
echo '<script src="vendor/datatables/jquery.dataTables.js"></script>';
echo '<script src="vendor/datatables/dataTables.bootstrap4.js"></script>';
echo '<!-- Custom scripts for all pages-->';
echo '<script src="js/sb-admin.min.js"></script>';
echo '<!-- Custom scripts for this page-->';
echo '<script src="js/sb-admin-datatables.min.js"></script>';
echo '<script src="js/sb-admin-charts.min.js"></script>';
echo '</div>';
echo '</body>';
echo '';
echo '</html>';
echo '';
echo '';

if($_SERVER["REQUEST_METHOD"]=="POST" && $_POST['testName']){
    $db = adoConnect();
    $testID=$_POST['testID'];
    $testName=htmlentities($_POST['testName'], ENT_QUOTES);
    $scriptName=htmlentities($_POST['scriptName'], ENT_QUOTES);
    $dailyFrequnceCount=htmlentities($_POST['dailyFrequenceCount'], ENT_QUOTES);
    
    $query = "UPDATE tests set testName='".$testName."', scriptName='".$scriptName."', dailyFrequenceCount='".$dailyFrequenceCount."' where testID=".$testID;
    
    $result = $db->Execute($query);

    if ($result == true)
    {
        print "<p style='color:green;'>The item has been successfully added!</p>";
        print "<meta http-equiv='refresh' content='1'>";
        header("location:./update-test-form.php");
    }
}
?>