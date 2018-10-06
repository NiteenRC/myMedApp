angular.module('myCart.cart_module', [ 'myCart.shared_module.sharedService' ])
		.controller('cartController', cartController);

function cartController($scope, $rootScope, $uibModal, sharedService, $location) {
	'use strict';

	var cartUrl = "/cart/";
	var CART_LIST_SAVE = "/carts";

	getCarts();
	getAlCartCount();

	$scope.removeCart = removeCart;
	$scope.addToCart = addToCart
	$scope.addCartList = addCartList;
	$scope.order = {};
	$scope.addToCart = addToCart;

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

	function addToCart(cart) {
		sharedService.postMethod(cartUrl, cart).then(function(response) {
			$scope.carts = response.data;
			$rootScope.totalCartsByUser = response.data;
			alert(cart.productName + '  added to cart successfully!!');
		}, function(error) {
			$scope.errorMessage = 'Error while creating' + error;
			$scope.successMessage = '';
		});
	}

	function addCartList() {
		sharedService.postMethod(CART_LIST_SAVE, $scope.carts).then(
				function(response) {
					$location.path("/checkout");
					$scope.carts = response.data;
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

	function removeCart(id) {
		sharedService.deleteMethod(cartUrl + id).then(function(response) {
			getCarts();
			getAlCartCount();
			$scope.successMessage = 'User created successfully';
			$scope.errorMessage = '';
		}, function(error) {
			$scope.errorMessage = 'Some thing went wrong' + error;
			$scope.successMessage = '';
		});
	}

	    function getAlCartCount() {
            sharedService.getAllMethod(cartUrl + parseInt($rootScope.userID)).then(
                function(response) {
                    $rootScope.totalCartsByUser = response.data.length;
                },
                function(error) {
                    $scope.errorMessage = 'Error while Counting' + error;
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
