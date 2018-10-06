angular.module('myCart.product_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'productController', productController);

function productController($scope, $uibModal, sharedService,$location) {
	'use strict';

	var productUrl = "/product/";
	var productsUrl = "/products/";
	var productByNameUrl = "/product/byName/";
	var PRODUCT_BY_CATEGORY_ID = "/product/category/"

	$scope.products = fetchAllProducts();
	$scope.getCategoryID=sharedService.get('categoryID');

	$scope.searchByProductName = searchByProductName;
	$scope.getProductsBySorting = getProductsBySorting;

	$scope.sortDirection="MR";

	$scope.productSort = [ {
		sortType : "MP",
		sortDirection : "Feature:Most Popular"
	}, {
		sortType : "MR",
		sortDirection : "Feature:Most Recent"
	}, {
		sortType : "HL",
		sortDirection : "Price:High to Low"
	}, {
		sortType : "LH",
		sortDirection : "Price:Low to High"
	} ];

	function searchByProductName() {
		 if (!sharedService.isDefinedOrNotNull($scope.productName)) { 
			 return fetchProductsByCategoryID(); 
		 }
		 fetchProductsByCategoryIDAndProductName();
	}
	
	function fetchProductsByCategoryID(){
		sharedService.getAllMethod(PRODUCT_BY_CATEGORY_ID +$scope.getCategoryID+"/"+$scope.sortDirection).then(
				function(response) {
					$scope.products = response.data;
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong' + error;
					$scope.successMessage = '';
				});
	}
	
	function fetchProductsByCategoryIDAndProductName(){
		sharedService.getAllMethod(productByNameUrl +$scope.getCategoryID+"/"+ $scope.productName+"/"+$scope.sortDirection).then(
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

	function getProductsBySorting(sortDirection) {
		$scope.sortDirection=sortDirection;
		
		if (!sharedService.isDefinedOrNotNull($scope.productName)) { 
			 return fetchProductsByCategoryID(); 
		 }
		 fetchProductsByCategoryIDAndProductName();
	}
}
