
function showPic(id){
	var result = "<img id='myDivImage' src='imageview.php?id="+id+"&view=full' alt='image' />";
	//alert("result="+result);
	//document.getElementById("myDiv").style.maxHeight=300px;
	$("#myDiv").html(result);
$('#myDivImage').css("max-width","100%");
	
}