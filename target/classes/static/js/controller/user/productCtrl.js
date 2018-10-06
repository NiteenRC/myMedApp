angular.module('myCart.product_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'productController', productController);

function productController($scope, $uibModal, sharedService,$location) {
	'use strict';

	var productsUrl = "/products";
	var productByNameUrl = "/product/byName/";
	var PRODUCT_BY_CATEGORY_ID = "/product/category/"

	$scope.products = fetchAllProducts();
	$scope.getCategoryID=sharedService.get('categoryID');

	$scope.searchByProductName = searchByProductName();

	function searchByProductName() {
		 if (!sharedService.isDefinedOrNotNull($scope.productName)) { 
			 return fetchProductsByCategoryID(); 
		 }
		 fetchProductsByCategoryIDAndProductName();
	}
	
	function fetchProductsByCategoryID(){
		sharedService.getAllMethod(PRODUCT_BY_CATEGORY_ID +$scope.getCategoryID).then(
				function(response) {
					$scope.products = response.data;
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong' + error;
					$scope.successMessage = '';
				});
	}
	
	function fetchProductsByCategoryIDAndProductName(){
    $scope.getCategoryID = !$scope.getCategoryID  ? 0 : $scope.getCategoryID;
		sharedService.getAllMethod(productByNameUrl +$scope.getCategoryID+"/"+ $scope.productName).then(
				function(response) {
					$scope.products = response.data;
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong' + error;
					$scope.successMessage = '';
				});
	}

	function fetchAllProducts(){
    		sharedService.getAllMethod(productsUrl).then(
    				function(response) {
    					$scope.products = response.data;
    				}, function(error) {
    					$scope.errorMessage = 'Some thing went wrong' + error;
    					$scope.successMessage = '';
    				});
    	}
}
