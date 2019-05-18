angular.module('myCart.bill_module', [ 'myCart.shared_module.sharedService' ])
		.controller('billController', billController);

function billController($scope, $rootScope, $uibModal, sharedService, $location) {
	'use strict';

	var url_home = sharedService.urlHome();
	var productsUrl = url_home + '/products';
	var productByNameUrl = url_home + '/product/byName/';
	var PRODUCT_BY_CATEGORY_ID = url_home + '/advancedSearch/';
	var categoryUrl = url_home + '/category';
	var PRODUCTS_GET_TEMP = url_home + '/productsGetTemp';
	var PRODUCTS_REMOVE = url_home + '/productsDelete';

	// $scope.paidAmount = 0;
	// $scope.balanceAmount = balanceToPaid();
	$scope.balanceToPaid = balanceToPaid;
	$scope.productsTemp = fetchAllTempProducts();
	$scope.removeCartList = removeCartList;

	function balanceToPaid() {
		if ($scope.paidAmount == "") {
			$scope.balanceAmount = $scope.totalAmountToPaid();
		} else {
			$scope.balanceAmount = $scope.totalAmountToPaid()
					- parseInt($scope.paidAmount);
		}
	}

	function fetchAllTempProducts() {
		sharedService.getAllMethod(PRODUCTS_GET_TEMP).then(function(response) {
			$scope.billData = response.data;
		}, function(error) {
			$scope.errorMessage = 'Some thing went wrong' + error;
			$scope.successMessage = '';
		});
	}

	$scope.getTotal = function(val) {
		var total = 0;
		angular.forEach($scope.billData, function(oldVal) {
			total += oldVal[val];
		});
		return total;
	};

	$scope.totalAmountToPaid = function() {
		var totalAmount = 0;
		angular.forEach($scope.billData, function(val) {
			totalAmount += val.price * val.qty;
		});
		return totalAmount;
	}

	function removeCartList(id) {
		if (!sharedService.isDefinedOrNotNull($scope.paidAmount)) {
			alert('Amount Paid should not be blank');
			return;
		}
		if(isNaN($scope.paidAmount)){
			alert('Amount Paid should be number');
			return;
		}
		
		if ($scope.billData.length >= 1
				&& !sharedService
						.isDefinedOrNotNull($scope.billData[0].productName)) {
			alert('Please select atleast one product to Sell');
			return;
		}

		sharedService.postMethod(PRODUCTS_REMOVE, $scope.billData).then(
				function(response) {
					$location.path("/billSuccess");
				}, function(error) {
					alert(error.data.errorMessage);
					$scope.errorMessage = 'Error while creating' + error;
					$scope.successMessage = '';
				});
	}
}