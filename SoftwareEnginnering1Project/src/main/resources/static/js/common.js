$(document).ready(function() {
    $('#password, #passwordCheck').keyup(CheckPasswords);

});
function CheckPasswords() {
	if ($("#password").val() != $("#passwordCheck").val()) {
		$("label[for='password'], label[for='passwordCheck']").show();
    }
	else {
		$("label[for='password'], label[for='passwordCheck']").hide();
	}
}