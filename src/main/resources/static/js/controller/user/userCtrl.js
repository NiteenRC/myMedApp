angular.module('myCart.user_module', [ 'myCart.shared_module.sharedService' ])
		.controller('userController', userController);

function userController($scope, $http, $location, $window, sharedService,
		$rootScope, $interval) {
	'use strict'

	var url_home = sharedService.urlHome();
	$rootScope.userLoggedin = false;
	$rootScope.userName = '';
	$rootScope.userType = '';
	$rootScope.userDetails = null;

	var checkUserLoginUrl = "user/byEmailAndPassword/";

	$scope.userLogin = userLogin;

	$interval(function() {
		$scope.currentDateTime = new Date();
	}, 100);

	function userLogin(user) {
		$scope.dataLoading = true;
		sharedService.postMethod(checkUserLoginUrl, user).then(
				function(userDetails) {
					$location.path(url_home+"/productList");
					if (userDetails.data.userName != null) {
						$rootScope.userLoggedin = true;
						$scope.isRegistrationRequired = false;
						$rootScope.userName = userDetails.data.userName;
						$rootScope.userLoggedin = true;
						$window.sessionStorage.setItem("userID",
								userDetails.data.userId);
						$window.sessionStorage.setItem("userName",
								userDetails.data.userName)
						$window.sessionStorage.setItem("userType",
								userDetails.data.userType)
						$scope.getUserNamerfromCookie();
						$scope.submitted = true;
					}
				}, function(error) {
					$scope.setError(error.data.errorMessage);
				});
	}

	$scope.setError = function(message) {
		$scope.error = true;
		$scope.errorMessage = message;
	};

	$scope.invalidateUser = function() {
		$window.sessionStorage.setItem("userID", '');
		$window.sessionStorage.setItem("userName", '');
		$window.sessionStorage.setItem("userType", '');
		$rootScope.userLoggedin = false;
		$location.path("/user");
	}

	$scope.getUserNamerfromCookie = function() {
		$rootScope.userID = $window.sessionStorage.getItem("userID");
		$rootScope.userName = $window.sessionStorage.getItem("userName");
		$rootScope.userType = $window.sessionStorage.getItem("userType");
	}

	$scope.getUserNamerfromCookie();
}
