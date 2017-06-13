var userCalendarController = angular.module('userCalendarController', []);

userCalendarController
		.controller(
				'CalendarController',
				function($scope, $http, $window, $compile, $timeout,
						uiCalendarConfig, $route) {
					var date = new Date();
					var d = date.getDate();
					var m = date.getMonth();
					var h = date.getHours();
					var y = date.getFullYear();
					var userId = $window.localStorage.getItem('userId');
					var calD = {
						lawyerId : parseInt(userId),
						startTime : new Date().toISOString()
					};

					$scope.events = [];
					// $scope.events.push({title: 'Before Rest Call',start: new
					// Date(y, m, 1)});

					$http.post(appointmentsURL, calD).success(
							function(data, status, headers, config) {
								var arr = [];
								$.each(data, function(index) {
									var d = data[index];
									$scope.events.push({
										id : d.id,
										title : d.title,
										start : new Date(d.start),
										end : new Date(d.end),
										purpose : d.purpose,
										venue : d.venue,
										availableToClients : d.availableToClients
									});
								});
								// callback(arr);
								// console.log($scope.eventSources);
								// $scope.apply();
							}).error(function(data, status, headers, config) {
						alert("error");
					});

					/* event source that calls a function on every view switch */
					$scope.eventsF = function(start, end, timezone, callback) {
						callback($scope.events);
					};

					$scope.calEventsExt = {
						color : '#f00',
						textColor : 'yellow',
						events : $scope.events
					};
					/* alert on eventClick */
					$scope.alertOnEventClick = function(date, jsEvent, view) {
						$scope.targetEvent = date;
						$scope.alertMessage = (date.title + ' was clicked ');
						$('#myModalLabel').html(
								"Appointment Details for " + date.title);
						$('#myModalPurpose').html(date.purpose);
						$('#myModalVenue').html(date.venue);
						$('#myModalStart').html(date.start.toLocaleString());
						$('#myModalEnd').html(date.end.toLocaleString());
						$('#myModal').modal('show');
						console.log(date);
					};
					/* alert on Drop */
					$scope.alertOnDrop = function(event, delta, revertFunc,
							jsEvent, ui, view) {
						$scope.targetEvent = event;
						$("#confirmMessage").html(
								"Are you sure you want to reschedule?");
						$("#messageType").html("update");
						$("#confirmDialog").modal('show');
					};
					/* alert on Resize */
					$scope.alertOnResize = function(event, delta, revertFunc,
							jsEvent, ui, view) {

						$scope.alertMessage = ('Event Resized to make dayDelta ' + delta);
						$scope.targetEvent = event;
						$("#confirmMessage").html(
								"Are you sure you want to reschedule?");
						$("#messageType").html("update");
						$("#confirmDialog").modal('show');
						// updateAppointment();

						// console.log(event);
					};
					/* add and removes an event source of choice */
					$scope.addRemoveEventSource = function(sources, source) {
						var canAdd = 0;
						angular.forEach(sources, function(value, key) {
							if (sources[key] === source) {
								sources.splice(key, 1);
								canAdd = 1;
							}
						});
						if (canAdd === 0) {
							sources.push(source);
						}
					};
					/* add custom event */
					$scope.addEvent = function() {
						$scope.events.push({
							title : 'Open Sesame',
							start : new Date(y, m, 28),
							end : new Date(y, m, 29),
							className : [ 'openSesame' ]
						});
					};
					/* remove event */
					$scope.remove = function(index) {
						$scope.events.splice(index, 1);
					};
					/* Change View */
					$scope.changeView = function(view, calendar) {
						uiCalendarConfig.calendars[calendar].fullCalendar(
								'changeView', view);
					};
					/* Change View */
					$scope.renderCalender = function(calendar) {
						$timeout(function() {
							if (uiCalendarConfig.calendars[calendar]) {
								uiCalendarConfig.calendars[calendar]
										.fullCalendar('render');
							}
						});
					};
					/* Render Tooltip */
					$scope.eventRender = function(event, element, view) {
						element.attr({
							'tooltip' : event.title,
							'tooltip-append-to-body' : true
						});
						$compile(element)($scope);
					};
					/* config object */
					$scope.uiConfig = {
						calendar : {
							height : 720,
							editable : true,
							header : {
								left : 'title',
								center : '',
								right : 'today prev,next'
							},
							stick : true,
							ignoreTimezone : false,
							timezone : 'local',
							eventClick : $scope.alertOnEventClick,
							eventDrop : $scope.alertOnDrop,
							eventResize : $scope.alertOnResize,
							eventRender : $scope.eventRender
						}
					};

					/* event sources array */
					$scope.eventSources = [ $scope.events, $scope.eventsF ];
					// $scope.eventSources2 = [$scope.calEventsExt,
					// $scope.eventsF, $scope.events];

					$scope.endDateBeforeRender = endDateBeforeRender
					$scope.endDateOnSetTime = endDateOnSetTime
					$scope.startDateBeforeRender = startDateBeforeRender
					$scope.startDateOnSetTime = startDateOnSetTime

					function updateAppointment() {
						var event = $scope.targetEvent;
						var eve = {
							id : event.id,
							venue : 'office',
							purpose : event.purpose,
							'startTime' : event.start,
							'endTime' : event.end,
							'lawyerId' : calD.lawyerId
						};
						// console.log(eve);
						$http.post(updateAppointmentsURL, eve).success(
								function(data, status, headers, config) {
									// $('#addEventModal').modal('hide').on(
									$timeout(function() {
										console.log(data);
										// $scope.events.push(data);
										// $scope.$apply();
										$route.reload();
									}, 500);
								}).error(
								function(data, status, headers, config) {
									alert("error");
								});
					}

					function deleteAppointment() {
						var event = $scope.targetEvent;
						var eve = {
							id : parseInt(event.id),
						};
						// console.log(eve);
						$http.post(deleteAppointmentsURL, eve).success(
								function(data, status, headers, config) {
									// $('#addEventModal').modal('hide').on(
									$timeout(function() {
										console.log(data);
										// $scope.events.push(data);
										// $scope.$apply();
										$route.reload();
									}, 500);
								}).error(
								function(data, status, headers, config) {
									alert("error");
								});
					}
					
					function startDateOnSetTime() {
						$scope.$broadcast('start-date-changed');
					}

					function endDateOnSetTime() {
						$scope.$broadcast('end-date-changed');
					}

					function startDateBeforeRender($dates) {
						const todaySinceMidnight = new Date();
						todaySinceMidnight.setUTCHours(0, 0, 0, 0);
						//var activeDate = moment(todaySinceMidnight);
						$dates.filter(
										function(date) {
											return date.utcDateValue < todaySinceMidnight.getTime()
										}).forEach(function(date) {
											
									date.selectable = false;
								});

						if ($scope.dateRangeEnd) {
							var activeDate = moment($scope.dateRangeEnd);

							$dates.filter(function(date) {
												return date.localDateValue() >= activeDate
														.valueOf()
											}).forEach(function(date) {
										date.selectable = false;
									});
						}
						
					   if($scope.events.length>0){
						   $scope.events.forEach(function(event){
							   if($scope.updateEvent && event.id == $scope.updateEvent.id){
								   return true;
							   }
							   var startDateStr = moment(event.start);
							   var endDateStr = moment(event.end);

								$dates.filter(function(date) {
													if (date.localDateValue() >= startDateStr.valueOf()){
														if($dates.length == 12){
															return true && date.localDateValue()<=endDateStr.valueOf();
														}else{
															return false;
														}
													}
													
												}).forEach(function(date) {
											date.selectable = false;
										});
						   })
					   }
					}

					function endDateBeforeRender($view, $dates) {
						
						if ($scope.dateRangeStart) {
							var activeDate = moment($scope.dateRangeStart)
									.subtract(1, $view).add(1, 'minute');

							$dates.filter(
											function(date) {
												return date.localDateValue() <= activeDate
														.valueOf()
											}).forEach(function(date) {
										date.selectable = false;
									})
						}
						
						   if($scope.events.length>0){
							   $scope.events.forEach(function(event){									
								   if($scope.updateEvent && event.id == $scope.updateEvent.id){
									   return true;
								   }
								   var startDateStr = moment(event.start);
								   var endDateStr = moment(event.end);

									$dates.filter(function(date) {										
														if (date.localDateValue() >= startDateStr.valueOf()){
															if($dates.length == 12){
																return true && date.localDateValue()<=endDateStr.valueOf();
															}else{
																return false;
															}
														}
														
													}).forEach(function(date) {
												date.selectable = false;
											});
									
									$dates.filter(function(date) {
										
										if ($scope.dateRangeStart) {
											var activeDate = moment($scope.dateRangeStart);															
											if (activeDate.valueOf()<=startDateStr.valueOf() && date.localDateValue() >= startDateStr.valueOf()){
												return true;
											}
											if (activeDate.valueOf()<=endDateStr.valueOf() && date.localDateValue() >= endDateStr.valueOf()){
												return true;
											}
											if (activeDate.valueOf()>=startDateStr.valueOf() && date.localDateValue() <= endDateStr.valueOf() && $view == 'minute'){
												return true;
											}
										}else{
											return false;
										}
										
										return false;
										
										
									}).forEach(function(date) {
								date.selectable = false;
							});
							   })
						   }
						   

					}

					$scope.dialogConfirm = function() {
						$('#confirmDialog').modal('hide').on(
								
								$timeout(function() {
									if($("#messageType").html() == "delete"){
										deleteAppointment();
									}else{
										updateAppointment();
									}
									
								}, 500));
					}

					$scope.dialogCancel = function() {
						$('#confirmDialog').modal('hide').on(
								$timeout(function() {
									$route.reload();
								}, 500));
					}

					$scope.updateMyAppointment = function(targetEvent) {
						$scope.updateEvent = targetEvent;
						$('#myModal').modal('hide');
						$scope.dateRangeStart = targetEvent.start;
						$scope.dateRangeEnd = targetEvent.end;
						$scope.purpose = targetEvent.purpose;
						$scope.appId = targetEvent.id;
						$scope.availableToClients = targetEvent.availableToClients;
						$("#messageType").html("update");
						$('#addEventLabel').html(
								"Update Appointment Details for "
										+ targetEvent.title);
						$('#addEventModal').modal('show');

					}
					$scope.deleteMyAppointment = function(event) {
						$scope.targetEvent = event;
						$('#myModal').modal('hide');
						$("#confirmMessage").html(
								"Are you sure you want to delete this event?");
						$("#messageType").html("delete");
						$("#confirmDialog").modal('show');
					}
					$scope.saveAppointment = function() {
						var ids = $scope.appId;

						if (typeof ids != 'undefined') {
							$scope.targetEvent = {
								id : ids,
								venue : 'office',
								purpose : $scope.purpose,
								'startTime' : $scope.dateRangeStart,
								'endTime' : $scope.dateRangeEnd,
								'lawyerId' : calD.lawyerId,
								'availableToClients' : $scope.availableToClients
							};
							$('#addEventModal').modal('hide');
							$http.post(updateAppointmentsURL,
									$scope.targetEvent).success(
									function(data, status, headers, config) {
										// $('#addEventModal').modal('hide').on(
										$timeout(function() {
											console.log(data);
											// $scope.events.push(data);
											// $scope.$apply();
											$route.reload();
										}, 500);
									}).error(
									function(data, status, headers, config) {
										alert("error");
									});

						} else {
							var eve = {
								venue : 'office',
								purpose : $scope.purpose,
								'startTime' : $scope.dateRangeStart,
								'endTime' : $scope.dateRangeEnd,
								'lawyerId' : calD.lawyerId,
								'availableToClients' : $scope.availableToClients
							};
							$http.post(createAppointmentsURL, eve).success(
									function(data, status, headers, config) {
										$('#addEventModal').modal('hide').on(
												$timeout(function() {
													console.log(data);
													$scope.events.push(data);
													// $scope.$apply();
													$route.reload();
												}, 500));
									}).error(
									function(data, status, headers, config) {
										alert("error");
									});
						}
					};

				});