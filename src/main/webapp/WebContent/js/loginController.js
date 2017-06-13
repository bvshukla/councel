var signupUrl = "../counsel/client/createUser.json";
var loginUrl = "../counsel/client/login.json";
var dashboardUrl = "./dashboard/";
var accessController = angular.module('accessController', []);
accessController
		.controller(
				'LoginController',
				function($scope, $http, $window) {
					$scope.signUp = function() {
						if ($scope.user.isLawyer) {
							$scope.user.userType = 'Lawyer';
						} else {
							$scope.user.userType = 'Client';
						}
						$http.post(signupUrl, $scope.user).success(
								function(data, status, headers, config) {

									$window.localStorage.setItem('userId',
											data.userId);
									// console.log(obj);
									$window.location = dashboardUrl;

								}).error(
								function(data, status, headers, config) {
									alert("error");
								});
						console.log($scope.user);

					}

					$scope.login = function() {
						$http
								.post(loginUrl, $scope.user)
								.success(
										function(data, status, headers, config) {
											if (data.userId) {
												$window.localStorage.setItem(
														'userId', data.userId);
												// console.log(obj);
												$window.location = dashboardUrl;
											} else {
												switch (data.errorCode) {
												case 'USER_PASSWORD_INCORRECT':
													$scope.errorMessage = data.errorMsg;
													break;
												case 'USER_NOT_REGISTERED':
													$scope.errorMessage = data.errorMsg;
													break;
												default:
													$scope.errorMessage = false;
												}
											}

										})
								.error(function(data, status, headers, config) {
									$scope.errorMessage = "Unknown error occurred, Please try after sometime";
								});
						console.log($scope.user);

					}
				});