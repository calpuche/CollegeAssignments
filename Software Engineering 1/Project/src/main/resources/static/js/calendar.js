$(document).ready(function() {
	$.ajax(window.location.href+'schedule', {
	      success: function(data) {
	    	  let days = {"M": 1,"T": 2,"W": 3,"R": 4,"F": 5}
	    	  let hours = {"8": 0,"9": 2,"10": 4,"11": 6,"12": 8,"1": 10,"2": 12,"3": 14,"4": 16,"5": 18}
	          $.each(data, function(i, scheduleRecord){
	        	 let abbr = scheduleRecord.dept.deptAbbreviation
	        	 let num = scheduleRecord.course.courseNum
	        	 let sectionDays = scheduleRecord.section.sectionDay.split('')
	        	 let sectionHour = scheduleRecord.section.sectionTime.match(/\d+:\d+/)[0].split(':')
	        	 $.each(sectionDays, function(i, day){
	        	 	$(`.calendar tbody tr:eq(${hours[sectionHour[0]]}) td:eq(${days[day]})`).html("<a style='color: white' href='/section/home'>" + abbr + '' + num + "</a>")
	        	 	$(`.calendar tbody tr:eq(${hours[sectionHour[0]]}) td:eq(${days[day]})`).addClass('class')
	        	 	$(`.calendar tbody tr:eq(${hours[sectionHour[0]]}) td:eq(${days[day]})`).attr("rowspan","2")
	        	 	$(`.calendar tbody tr:eq(${hours[sectionHour[0]]+1}) td:eq(0)`).remove()
	        	 });
	          });
	    	  
	         
	      },
	      error: function() {
	         $('#error').html(`<div class="row">
	        		 						<div class="alert alert-danger alert-dismissible" role="alert">
	     	    	  							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	     	    	  								<span aria-hidden="true">&times;</span>
	     	    	  							</button>
	     	    	  							<span>Error Fetching Calendar.</span>
	     	    							</div>
	     	    		    			</div>`);
	      }
	   });
});
