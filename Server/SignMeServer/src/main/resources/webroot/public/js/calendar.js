function eventInput(callback){
	var eventInput=[];
	$.ajax({
		url:'/task',
		type:'GET',
		data:"",
		success: function(result){
			result = JSON.parse(result);
			// console.log(result.length);
			// console.log(typeof(result[1].startDate));
			for(var i=0;i<result.length;i++){
				$("#wholist").append(
					`
					<div>
						<div style="background-color:${result[i].color}"></div>
						<span>${result[i].title}</span>
					</div>
					`
				)
				eventInput.push({
					title : result[i].title,
					start : result[i].startDate.substring(0,10),
					end : result[i].endDate.substring(0,10),
					backgroundColor : result[i].color
				});
				if(i==result.length-1){
					callback(eventInput);
				}
			}
			// console.log(eventInput);
		},
		statusCode: {
			400: function() {
				alert("잘못된 요청");
			}
		},
		failure : function(e){
			console.log("error");
			console.log(e);
		},
		finally : function(er){
			console.log("?????");
			console.log(er);
		}
	});
}
function createCalendar(callback){
	$('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		// defaultDate: '2017-08-28',
		defaultDate: new Date(),
		navLinks: true, // can click day/week names to navigate views
		selectable: true,
		selectHelper: true,
		select: function(start, end) {
			var title = prompt('Event Title:');
			var eventData;
			if (title) {
				eventData = {
					title: title,
					start: start,
					end: end
				};
				$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
			}
			$('#calendar').fullCalendar('unselect');
		},
		editable: true,
		eventLimit: false,
		selectable: false,
		selectHelper: false,
		events : ""
	});
	callback();
}

$(document).ready(function() {

		// console.log(eventInput);
		// var eventIn=eventInput();
		// console.log(eventInput());
		// var testInput=addTestDate();
		// var eventsInput=eventInput();
		createCalendar(function(){
			eventInput(function(eventsInput){
				$("#calendar").fullCalendar( 'addEventSource', eventsInput);
			})
		});

		// $('#calendar').fullCalendar('addEventSource',testInput);

	});
