var userPublicProfileController = angular.module('userPublicProfileController',[]);

userPublicProfileController.factory('publicUserService', function() {
	  var user = undefined;

	  return {
	    setUser: setUser,
	    getUser: getUser
	  };

	  function setUser(item) {
	    user = item;
	  }

	  function getUser() {
	    return user;
	  }
	});

userProfileController.controller('PublicProfileController',function ($scope,$http,$window,$route,$location,publicUserService){
	$scope.puser =  publicUserService.getUser();
	if(typeof $scope.puser == "undefined"){
		$location.path('/dashboard');
	}
	
	
});