var userUrl ="/councelApp/counsel/client/findUser.json?userId=";
var signupUrl = "/councelApp/WebContent/";
var updateUrl= "/councelApp/counsel/client/updateUser.json";
var uploadImageUrl= "/councelApp/counsel/client/upload.json";
var profileUrl = "/councelApp/WebContent/dashboard/#profile";
var appointmentsURL="/councelApp/counsel/calendar/getAppointments.json";
var createAppointmentsURL="/councelApp/counsel/calendar/createAppointments.json";
var updateAppointmentsURL="/councelApp/counsel/calendar/updateAppointments.json";
var deleteAppointmentsURL="/councelApp/counsel/calendar/deleteAppointments.json";
var getLawyersURL = "/councelApp/counsel/lawyers/getLawyers.json";
var getClientAppoinmentsURL = "/councelApp/counsel/lawyers/getClientAppointments.json";
var bookAppoinmentsURL = "/councelApp/counsel/lawyers/bookAppointments.json";

var counselApp = angular.module('counselApp',
		['ngRoute',
		'userController',
		'userProfileController',
		'userCalendarController',
		'userLawyerController',
		'userPublicProfileController',
		'ngMaterial',
		'ngFileUpload',
		'ngImgCrop',
		'angular-loading-bar',
		'ui.calendar',
		'ui.bootstrap',
		'ui.bootstrap.datetimepicker',
		'ngAnimate'
		]);

 counselApp.config(['$routeProvider', function($routeProvider){
	$routeProvider.
	when('/dashboard',{
		templateUrl : '../partials/dashboard.html',
		controller: 'DashboardController'
	}).
	when('/profile',{
		templateUrl : '../partials/profile.html',
		controller: 'ProfileController'
	}).
	when('/edit',{
		templateUrl : '../partials/profile-edit.html',
		controller: 'EditProfileController'
	}).
	when('/calendar',{
		templateUrl : '../partials/calendar.html',
		controller: 'CalendarController'
	}).
	when('/lawyers',{
		templateUrl : '../partials/lawyers.html',
		controller: 'LawyerController'
	}).
	when('/user',{
		templateUrl : '../partials/publicprofile.html',
		controller: 'PublicProfileController'
	}).
	otherwise({
		redirectTo: '/dashboard'
	})
}]); 

