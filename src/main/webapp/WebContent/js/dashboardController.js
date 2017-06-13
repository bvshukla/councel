
var userController = angular.module('userController',[]);

userController.factory('dataService', function($window,$http) {

			var userId = $window.localStorage.getItem('userId');
			if(userId <=0){
				$window.location=signupUrl;
			}
			var url = userUrl+userId;
			return $http.get(url)
			.success(function(data, status, headers, config){
				
				productList = data;
				//dataService.addProduct(data);
				//console.log($scope.user);
				return productList;
			})
			.error(function(data, status, headers, config){
				$window.location=signupUrl;
				return null;
			});	
			

	});

userController.controller('DashboardController',function ($scope,$http,$window,dataService){
	
	dataService.success(function(data){
		$scope.user = data;
		$scope.placeholder='https://placehold.it/250x250';
		if(data.dob){
		$scope.dob= new Date(data.dob);
		}else{
			$scope.dob = new Date();
		}
	});
	
	$scope.logout= function(){
		
		$window.localStorage.setItem('userId', undefined);
		
		
			$window.location=signupUrl;
		
	}
	
});