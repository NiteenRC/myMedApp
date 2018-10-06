angular.module('myCart.orderSummary_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'orderSummaryController', orderSummaryController);

function orderSummaryController($scope, sharedService, $rootScope) {
	'use strict'

	var ORDER_URL = "/order/";
	var ORDER_BY_USER = "/order/byUser/";

	function getDate() {
		var today = new Date();
		var mm = today.getMonth() + 1;
		var dd = today.getDate();
		var yyyy = today.getFullYear();

		return dd + "/" + mm + "/" + yyyy
	}

	$scope.isOrderTracked = false;

	$scope.orderInfo = {};
	$scope.errorMessage = '';

	$scope.orderInfo = sharedService.get('orderInfo');

	$scope.isOrderPlaced = sharedService.get('isOrderPlaced');

	$scope.isProductOrdered = false;

	$scope.getDate = getDate();
	$scope.trackOrderNo = trackOrderNo;
	$scope.orderHistory = orderHistory;

	if (sharedService.isDefinedOrNotNull($scope.orderInfo)) {
		$scope.isProductOrdered = true;
	}

	function trackOrderNo() {
		sharedService.getAllMethod(ORDER_URL + $scope.orderNo).then(
				function(response) {
					$scope.orderTrack = response.data;
					$scope.isOrderTracked = true;
				}, function(error) {
					$scope.errorMessage = error.data.errorMessage;
				});
	}

	$scope.isOrderHistory = false;

	function orderHistory() {
		if (!sharedService.isDefinedOrNotNull($rootScope.userID)) {
			return alert('Please login to check your order history!!');
		}
		sharedService.getAllMethod(ORDER_BY_USER + parseInt($rootScope.userID))
				.then(function(response) {
					$scope.orderTrack = response.data;
					$scope.isOrderHistory = true;
					$scope.isProductOrdered = false;
				}, function(error) {
					$scope.errorMessage = error.data.errorMessage;
				});
	}
}
