var gradingTable = null

function updateGrade(userId, grade, gradeIndex){
	gradingTable.grades[userId][gradeIndex].grade = grade
	
	var studentGrade = 0.0
	$.each(gradingTable.grades[userId], function(i, grade){
		studentGrade += parseFloat(grade.grade) 
					* parseFloat(gradingTable.assignments[i].asgWeight)
					* getGroupWeight(gradingTable.assignments[i].groupId)
	});
	studentGrade = studentGrade.toFixed(2);
	$(`.grading tbody #${userId} td:last-child h5`).text(`${studentGrade}`)
}

function updateGrades(){
	$.ajax({
	    type: "post", url: "/updateGrades", contentType: "application/json",
	    data: JSON.stringify(gradingTable),
	    success: function(data) {
	    	console.log(data);
	    	showTooltip("success","Grades updated.")
	    },
	    error: function(error) {
	    	console.log(error);
	    	showTooltip("danger","Error updating grades.")
	    }
	});
}

function getGroupWeight(groupId) {
	var weight = 0
	$.each(gradingTable.assignmentGroups, function(i, assignmentGroup){
		if(assignmentGroup.groupId == groupId){
			weight = parseFloat(assignmentGroup.groupWeight)
		}
	});
	return weight
}

function showTooltip(type, message){
	$('#tooltip').html(`<div class="row">
							<div class="alert alert-${type} alert-dismissible" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<span>${message}</span>
							</div>
						</div>`);
}


$(document).ready(function() {
	$("#changeGrades").click(function(){
		updateGrades()
	});
	let params = new URLSearchParams(window.location.search)
	$.ajax('/getGrades/'+params.get("sectionId"), {
	      success: function(data) {
	    	gradingTable = data 
	    	$('.grading thead tr').append(`<th></th>`)
	    	$.each(gradingTable.assignments, function(i, assignment){
	    		var tooltip = `Assignment Weight: ${parseFloat(gradingTable.assignments[i].asgWeight)}<br />Group Weight: ${getGroupWeight(gradingTable.assignments[i].groupId)}`
	      	 	$('.grading thead tr').append(`<th><h5><a data-toggle="tooltip" title="${tooltip}">${assignment.asgName}</a></h5></th>`)
	    	});
	    	$('.grading thead tr').append(`<th><h5>Total Grade</h5></th>`)
	    	$.each(gradingTable.grades, function(userKey, user){
	    		$('.grading tbody').append(`<tr id=${userKey}>`)
	    		$(`.grading tbody #${userKey}`).append(`<td><h5>${gradingTable.students[userKey].lastName}, ${gradingTable.students[userKey].firstName}</h5></td>`)
	    		var studentGrade = 0.0
	    		$.each(user, function(i, grade){
	    			studentGrade += parseFloat(grade.grade) 
	    						* parseFloat(gradingTable.assignments[i].asgWeight)
								* getGroupWeight(gradingTable.assignments[i].groupId)
		      	 	$(`.grading tbody #${userKey}`).append(`
		      	 	<td>
		      	 		<div class="input-group">
		      	 			<input class="form-control" onchange='updateGrade(${userKey}, this.value, ${i})' type='text' value='${grade.grade}'/>
		      	 		</div>
		      	 	</td>`)
		    	});
	    		studentGrade = studentGrade.toFixed(2);
	    		$(`.grading tbody #${userKey}`).append(`<td><h5>${studentGrade}</h5></td>`)
	    		$('.grading tbody').append(`</tr>`)
	    	});
	    	$('[data-toggle="tooltip"]').tooltip({
	    	    'placement': 'top',
	    	    'html': true
	    	});
	         
	      },
	      error: function(e) {
	    	  showTooltip("danger","Error Fetching Grading Table.")
	      }
	   });
});
