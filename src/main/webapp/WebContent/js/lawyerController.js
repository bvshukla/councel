var userLawyerController = angular.module('userLawyerController',[]);

userController.controller('LawyerController',function ($scope,$http,$window,$route,$location,publicUserService){
	
	var inputStr = null;
	
	$http.post(getLawyersURL, inputStr).success(
			function(data, status, headers, config) {
				var arr = [];
				$scope.lawyers = data;

			}).error(function(data, status, headers, config) {
		alert("error");
	});
		
	$scope.book = function(lawyer){
		var payLoad = {lawyerId:lawyer.userId};
		$http.post(getClientAppoinmentsURL, payLoad).success(
				function(data, status, headers, config) {
					$scope.appointments = data;
					$scope.bookingLawyerId = lawyer.userId;
					$scope.$broadcast('end-date-changed');
					$("#addEventModal").modal('show');
					console.log($scope.appointments);
				}).error(function(data, status, headers, config) {
			alert("error");
		});
	}
	
	$scope.viewUser = function(lawyer){
		publicUserService.setUser(lawyer);
		$location.path('/user');
	}
	
	$scope.saveAppointment = function(){
		
		var clientIdVal = $window.localStorage.getItem('userId');
		var payLoad = {lawyerId:parseInt($scope.bookingLawyerId),clientId:parseInt(clientIdVal),purpose:$scope.purpose,slot:$scope.dateRangeStart};
		$("#addEventModal").modal('hide');
		$http.post(bookAppoinmentsURL, payLoad).success(
				function(data, status, headers, config) {
					$timeout(function() {
						console.log(data);
						// $scope.events.push(data);
						// $scope.$apply();
						$route.reload();
					}, 500);
				}).error(function(data, status, headers, config) {
			alert("error");
		});
	}
	$scope.endDateOnSetTime = endDateOnSetTime
	$scope.startDateBeforeRender = startDateBeforeRender
	$scope.startDateOnSetTime = startDateOnSetTime

	function startDateOnSetTime () {
	  $scope.$broadcast('start-date-changed');
	}

	function endDateOnSetTime () {
	  $scope.$broadcast('end-date-changed');
	}

	function startDateBeforeRender ($dates,$view) {
		
		const todaySinceMidnight = new Date();
		todaySinceMidnight.setUTCHours(0, 0, 0, 0);
		//var activeDate = moment(todaySinceMidnight);
		$dates.filter(
						function(date) {
							return true;
						}).forEach(function(date) {
							
					date.selectable = false;
				});
		
	  if ($scope.appointments && $scope.appointments.length>0) {
		   $scope.appointments.forEach(function(event){
			   if(event.availableToClients == true){
			   var startDateStr = moment(event.start).subtract(1, $view).add(1, 'minute');;
			   var endDateStr = moment(event.end).subtract(1, 'minute');

				$dates.filter(function(date) {
									
									if (date.localDateValue() > startDateStr.valueOf() && date.localDateValue()<endDateStr.valueOf()){
										date.selectable = true;
									}else{
										date.selectable = false;
									}							
						});
			   }
		   })
		   $scope.appointments.forEach(function(event){
			   if(event.availableToClients != true){
			   var startDateStr = moment(event.start);
			   var endDateStr = moment(event.end).subtract(1, 'minute');

				$dates.filter(function(date) {
									
									if (date.localDateValue() >= startDateStr.valueOf() && date.localDateValue()<=endDateStr.valueOf()){
										date.selectable = false;
									}							
						});
			   }
		   })
	  }
	}	
});