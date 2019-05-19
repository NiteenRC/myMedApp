angular.module('myCart.cart_module', [ 'myCart.shared_module.sharedService' ])
		.controller('cartController', cartController);

function cartController($scope, $rootScope, $uibModal, sharedService, $location) {
	'use strict';

	var url_home = sharedService.urlHome();
	var CART_LIST_SAVE = url_home + '/cartsAdd';
	var CART_LIST_DELETE = url_home + '/cartsDelete';
	var productsUrl = url_home + '/products';
	var CARTS_REPORT = url_home + '/cartsReport';
	var PRODUCTS_ADD = url_home + '/productsAdd';
	var PRODUCTS_REMOVE_TEMP = url_home + '/productsTempDelete';

	$scope.rows = [];
	$scope.billData = [];
	fetchAllProducts();
	addRow();
	$scope.addRow = addRow;
	$scope.generateReport = generateReport;

	$scope.removeCart = removeCart;
	$scope.addCartList = addCartList;
	$scope.isSingleCartAvail = true;
	$scope.generateBill = generateBill;

	$scope.cartData = {
		productName : null,
		price : null,
		image : null,
		qty : null,
	};

	$scope.qtyValidation = function(qty, index) {
		// 
	}

	$scope.selectedProduct = function(productID, index) {
		var originalProductName = null;
		var originalPrice = 0;
		var originalProductID = 0;
		for (var i = 0; i < $scope.products.length; i++) {
			if ($scope.products[i].productID == productID) {
				originalProductID = $scope.products[i].productID
				originalPrice = $scope.products[i].price;
				originalProductName = $scope.products[i].productName;
			}
		}

		var duplicateCount = 0;
		for (var i = 0; i < $scope.rows.length; i++) {
			if ($scope.rows[i].productID == productID) {
				duplicateCount++;
			}

			if ((duplicateCount >= 1 && index == 0) || duplicateCount >= 2) {
				alert('Medicine is already Selected!!');
				removeCart(index);
				return;
			}

			$scope.rows[index].productID = originalProductID
			$scope.rows[index].price = originalPrice;
			$scope.rows[index].productName = originalProductName;
		}
	};

	function addRow() {
		$scope.rows.push({
			productID : 0,
			productName : null,
			qty : 1,
			price : 0
		});

		if ($scope.rows && $scope.rows.length == 1) {
			$scope.isSingleCartAvail = true;
		} else {
			$scope.isSingleCartAvail = false;
		}
	}

	function fetchAllProducts() {
		sharedService.getAllMethod(productsUrl).then(function(response) {
			$scope.products = response.data;
		}, function(error) {
			$scope.errorMessage = 'Some thing went wrong' + error;
			$scope.successMessage = '';
		});
	}

	function addCartList() {
		if ($scope.rows.length >= 1
				&& !sharedService
						.isDefinedOrNotNull($scope.rows[0].productName)) {
			alert('Please select atleast one product to Buy');
			return;
		}

		sharedService.postMethod(PRODUCTS_ADD, $scope.rows).then(
				function(response) {
					$scope.carts = response.data;
					$scope.rows = [];
				}, function(error) {
					$scope.errorMessage = 'Error while creating' + error;
					$scope.successMessage = '';
				});
	}

	function generateBill(id) {
		if ($scope.rows.length >= 1
				&& !sharedService
						.isDefinedOrNotNull($scope.rows[0].productName)) {
			alert('Please select atleast one product to Sell');
			return;
		}

		sharedService.postMethod(PRODUCTS_REMOVE_TEMP, $scope.rows).then(
				function(response) {
					if (response.data.body.errorMessage != undefined
							&& response.data.body.errorMessage
									.includes("Stock avaible for")) {
						alert(response.data.body.errorMessage);
						return;
					}
					$scope.billData = response.data;
					$location.path("/bill");
				}, function(error) {
					alert(error.data.errorMessage);
					$scope.errorMessage = 'Error while creating' + error;
					$scope.successMessage = '';
				});
	}

	function removeCart(index) {
		if ($scope.rows && $scope.rows.length == 1) {
			$scope.isSingleCartAvail = true;
		} else {
			$scope.rows.splice(index, 1);
			$scope.isSingleCartAvail = false;
		}
	}

	$scope.getTotal = function(val) {
		var total = 0;
		angular.forEach($scope.rows, function(oldVal) {
			total += oldVal[val];
		});
		return total;
	};

	$scope.totalAmountToPaid = function() {
		var totalAmount = 0;
		angular.forEach($scope.rows, function(val) {
			totalAmount += val.price * val.qty;
		});
		return totalAmount;
	}

	$scope.opts = {
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	}
	$scope.data = {
		valor : "10/09/2013"
	}

	function generateReport(fromDate, toDate) {
		if (fromDate == undefined || toDate == undefined) {
			alert('From date and To dates must be selected');
			return;
		}

		sharedService
				.getAllMethod(CARTS_REPORT + "/" + fromDate + "/" + toDate)
				.then(function(response) {
					$scope.carts = response;
					$scope.rows = [];
					alert(response.data.errorMessage);
				}, function(error) {
					// alert(error.data); need to handle in next version
				});
	}
}