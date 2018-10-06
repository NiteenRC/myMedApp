angular.module('myCart.orderManage_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'orderManageController', orderManageController);

function orderManageController($scope, sharedService) {
	'use strict';

	var orderUrl = "/order/";

	$scope.findByOrderNoAndOrderDate = findByOrderNoAndOrderDate;
	$scope.updateOrderStatus = updateOrderStatus;
	getOrders();

	function updateOrderStatus(order) {
		sharedService.postMethod(orderUrl, order).then(function(response) {
			filterOrders();
		}, function(error) {
			alert(error.data.errorMessage);
		});
	}

	function getOrders() {
		sharedService.getAllMethod(orderUrl).then(function(response) {
			$scope.orders = response.data;
		}, function(error) {
			$scope.errorMessage = error.data.errorMessage;
		});
	}

	function findByOrderNoAndOrderDate(orderNo, orderDate) {
		sharedService.getAllMethod(orderUrl + orderNo + orderDate).then(
				function(response) {
					$scope.orders = response.data;
				}, function(error) {
					$scope.errorMessage = error.data.errorMessage;
				});
	}

	$scope.filterOrders = filterOrders;

	function filterOrders() {
		if (!sharedService.isDefinedOrNotNull($scope.fromDate)
				|| !sharedService.isDefinedOrNotNull($scope.toDate)) {
			return
		}

		sharedService.getAllMethod(
				orderUrl + $scope.fromDate + "/" + $scope.toDate).then(
				function(response) {
					$scope.orders = response.data;
				}, function(error) {
					$scope.errorMessage = error.data.errorMessage;
				});
	}

	// Date picker
	$scope.clear1 = function() {
		$scope.fromDate = null;
	};

	$scope.open1 = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened1 = true;
	};

	$scope.clear2 = function() {
		$scope.toDate = null;
	};

	$scope.open2 = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened2 = true;
	};

	$scope.dateOptions = {
		formatYear : 'yy',
		startingDay : 1
	};

	$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate' ];
	$scope.format = $scope.formats[2];
}
