angular.module('myCart.category_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'categoryController', categoryController);

function categoryController($scope, $uibModal, sharedService, $location) {
	'use strict';

	var productUrl = "/product";
	var categoryUrl = "/category/";
	var CATEGORY_BY_NAME = "/category/byName/";
	var PRODUCT_FOR_ALL_CATEGORIES = "/products/";

	getCategory();
	$scope.productsByCategory = getProducts;
	$scope.searchByCategoryName = searchByCategoryName;

	function getCategory() {
		$scope.dataLoading = true;
		sharedService.getMethod(PRODUCT_FOR_ALL_CATEGORIES).then(
				function(response) {
					$scope.categories = response.data;
					$scope.dataLoading = false;
				}, function(error) {
					$scope.errorMessage = 'Error while creating' + error;
					$scope.successMessage = '';
					$scope.dataLoading = false;
				});
	}

	function searchByCategoryName() {
		if (!sharedService.isDefinedOrNotNull($scope.categoryName)) {
			return getCategory();
		}
		sharedService.getAllMethod(CATEGORY_BY_NAME + $scope.categoryName)
				.then(function(response) {
					$scope.categories = response.data;
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong' + error;
					$scope.successMessage = '';
				});
	}

	function getProducts(categoryID) {
		sharedService.getAllMethod(
				productUrl + categoryUrl + categoryID + "/MR").then(
				function(response) {
					$location.path('/productList');
					sharedService.store('products', response.data);
					sharedService.store('categoryID', categoryID);
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong' + error;
					$scope.successMessage = '';
				});
	}

	function reset() {
		$scope.successMessage = '';
		$scope.errorMessage = '';
	}
}
