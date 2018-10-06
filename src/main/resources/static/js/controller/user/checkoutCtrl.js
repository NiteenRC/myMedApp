angular.module('myCart.checkout_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'checkoutController', checkoutController);

function checkoutController($scope, $rootScope, $uibModal, sharedService,
		$location) {
	'use strict';

	var cartUrl = "/cart/";
	var ORDER_LIST_SAVE = "/orders";

	getCarts();
	$scope.order = {};
	$scope.placeOrder = placeOrder;

	$scope.cartData = {
		productName : null,
		price : null,
		image : null,
		qty : null
	};

	$scope.orderVo = {
		userID : parseInt($rootScope.userID),
		purchaseOrder : $scope.order,
		purchaseOrderDetail : $scope.carts
	};

	function placeOrder() {
		sharedService.postMethod(ORDER_LIST_SAVE, $scope.orderVo).then(
				function(response) {
					sharedService.store('orderInfo', response.data);
					sharedService.store('isOrderPlaced', true);
					$location.path("/orderSummary");
				}, function(error) {
					$scope.errorMessage = 'Error while creating' + error;
					$scope.successMessage = '';
				});
	}

	function getCarts() {
		sharedService.getAllMethod(cartUrl + parseInt($rootScope.userID)).then(
				function(response) {
					$scope.carts = response.data;
					$scope.orderVo.purchaseOrderDetail = response.data;
				}, function(error) {
					$scope.errorMessage = 'Error while creating' + error;
					$scope.successMessage = '';
				});
	}

	$scope.getTotal = function(val) {
		var total = 0;
		angular.forEach($scope.carts, function(oldVal) {
			total += oldVal[val];
		});
		return total;
	};
}
