angular.module('myCart.product_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'productController', productController);

function productController($scope, $uibModal, sharedService,$location) {
	'use strict';

	var productUrl = "/product/";
	var productByNameUrl = "/product/byName/";
	var PRODUCT_BY_CATEGORY_ID = "/product/category/"

	$scope.products =	sharedService.get('products');
	$scope.getCategoryID=sharedService.get('categoryID');

	$scope.searchByProductName = searchByProductName;
	$scope.selectProduct = selectProduct;
	$scope.getProductsBySorting = getProductsBySorting;
	$scope.calculateRating = calculateRating;
	
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

	function selectProduct(productID) {
		sharedService.getMethod(productUrl + productID).then(
				function(response) {
					$location.path('/productSelected');
					sharedService.store('productItem', response.data);
				}, function(error) {
					$scope.errorMessage = 'Error while getting: ' + error;
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

	function calculateRating(rating) {
		var newVal = 0;
		angular.forEach(rating, function(oldVal) {
			if (sharedService.isDefinedOrNotNull(oldVal.star)) {
				newVal = newVal + parseInt(oldVal.star);
			}
		});
		if(newVal===0 || rating.lenth==0){
			return 0;
		}	
		else{
		return Math.round(((newVal / rating.length)*100)/100)
		}
	}
}
