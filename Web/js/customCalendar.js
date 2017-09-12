
	$(document).ready(function() {
		
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
			editable: false,
			eventLimit: false,
			selectable: false,
			selectHelper: false,
			
			 // allow "more" link when too many events
			// eventClick:function(event){
			// 	if(event){
			// 		alert(
			// 			event.title+'\n'+event.start+"~"+event.end
			// 			);
			// 		return false;
			// 	}
			// },
			eventSources : [
                {
                    events : [
                        {
                            title : "All Day Event",
                            start : "2017-09-03"
                        },
                        {
                            title : "Long Event",
                            start : "2017-09-07",
                            end : "2017-09-10",
							who : "java"
                        }
                    ]
                    , color : "#FF0000"
                    , textColor : "#FFFF00"
                }
            ]
		});
		
	});
