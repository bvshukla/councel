var userProfileController = angular.module('userProfileController',[]);

userProfileController.factory('dataService', function($window,$http) {

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

userProfileController.factory('sharedList', function() {
	  var user = undefined;

	  return {
	    setUser: setUser,
	    getUser: getUser
	  };

	  function addItem(item) {
	    list.push(item);
	  }

	  function getList() {
	    return list;
	  }
	});

userProfileController.controller('ProfileController',function ($scope,$http,$window,dataService){
	if(typeof $scope.user == "undefined"){
	dataService.success(function(data){
		$scope.user = data;
		$scope.placeholder='https://placehold.it/250x250';
		if(data.dob){
		$scope.dob= new Date(data.dob);
		}else{
			$scope.dob = new Date();
		}
	});
	}
	$scope.logout= function(){
		
		$window.localStorage.setItem('userId', undefined);
		
		
			$window.location=signupUrl;
		
	}
	
});

userProfileController.controller('EditProfileController',function ($scope,$http,$window,dataService,Upload, $timeout){
		dataService.success(
				function(data){
					$scope.placeholder='https://placehold.it/150x150';
					$scope.user=data;
					if($scope.user.dob){
						$scope.dob=new Date($scope.user.dob);
						}else{
							$scope.dob = new Date();
						}
				}
		);
		
		
		
		$scope.update= function(){
			$scope.user.dob =  $scope.dob.toDateString();
			
			$http.post(updateUrl, $scope.user)
			.success(function(data, status, headers, config){
				
				//$window.localStorage.setItem('userId',data.userId);
				//console.log(obj);
				$window.location=profileUrl;
				
			})
			.error(function(data, status, headers, config){
				alert("error");
			});
			
			console.log($scope.user);
		}
		
	    $scope.upload = function (dataUrl, name) {
	        Upload.upload({
	            url: uploadImageUrl,
	            data: {
	                file: Upload.dataUrltoBlob(dataUrl, name),
	                userId:$scope.user.userId,
	                fileType:'ProfilePic'
	            },
	        }).then(function (response) {
	            $timeout(function () {
	                $scope.result = response.data;
	                $scope.$apply();
	                $('#myModal').modal('hide');
	                $window.location.reload();
	            },0);
	        }, function (response) {
	            if (response.status > 0) $scope.errorMsg = response.status 
	                + ': ' + response.data;
	        }, function (evt) {
	            $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
	        });
	    }
});