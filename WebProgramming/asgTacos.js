//when submit phonenumber button action from tacotogo.php
	//1)check phone number if 10 digits and strip out any non digit numbers
	//if there are only 10 digits
		//make a request to a php script tacoOrder.php
	//handles clear button
	//and a finish order button
	$(document).ready(function(){
		$("#submitPhone").click(
		function(){
			var phone=$("#textPhone").val();
			//document.write(phone);
			var newPhone=checkPhone(phone);
			if(phone.length == 10)
			{
				showOrderForm(newPhone);
				/*$.get("./tacoOrder.php",
				{phoneNum:phone,task:1, 
				function(response){
					$("#phoneDiv").html(response);
					$("#submitClear").click(function(){
						clearOrder();
					});
					$("#submitFinish").click(function(){
						finishOrder();
					});
				}
				}); */
			}
			else{
				$('#finishDiv').html("<h3>Invalid Phone Number! Please Try Again! <h3>");
			}
		}
		)
	});
        

	function submitFinalOrder()
	{
		var name = $("#name").val();
		var phoneNumber = $("#phoneSpan").html();
		var beef = $("#beef").val();
		var chicken = $("#chicken").val();
		var breakfast = $("#breakfast").val();
		var drink = $("#drink").val();
		$.get('./tacoOrder.php',
		{task:2,aName:name,aPhone:phoneNumber,numBeef:beef,numChicken:chicken,numBreakfast:breakfast,aDrink:drink},
		function(response){
		
		$("#phoneDiv").html("<h2>Thank You!</h2");
		$("#orderDiv").html(response);
		
		});
		}
	
	
	
	function showButtons()
{
 // print "<input type='button' id='clearButton' value='Clear Order' />\n";
 // print "<input type='button' id='finishOrderButton' value='Place Order Now' />\n";
 // print "<br />\n";
}
function checkPhone(thePhone)
{
    var result="";
    var good=false;
    // must 
    var newPhone="";
    for(i=0; i<thePhone.length; i++)
    {   var ch=thePhone.charAt(i);
	if(ch>='0' && ch <='9')
	{  
            newPhone += ch;
        }
    }
    return newPhone;
}
	function showOrderForm(phone){
		$('#phoneDiv').html("");
		$.get('./tacoOrder.php',
			{task: 1, phoneNumber:phone},
			function(response){
				$('#orderDiv').html(response);
				$("#clearOrder").click(function(){
					clearOrder();
				}
				);
				$('#submitOrder').click(function(){
					$('#phoneDiv').html("");
					submitFinalOrder();
				} 
				);
			});
}
	function clearOrder(){
	$("#beef").val(0);
    $("#chicken").val(0);
    $("#breakfast").val(0);
    $("#drink").val(0);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
