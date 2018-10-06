angular.module('myCart.categoryManage_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'categoryManageController', categoryManageController);

function categoryManageController($scope, $uibModal, sharedService) {
	'use strict';

	var categoryUrl = "/category";

	getCategory();
	$scope.saveCategory = saveCategory;
	$scope.removeCategory = removeCategory;

	$scope.categoryData = {
		productID : null,
		name : null,
		price : null,
		image : null
	};

	function saveCategory() {

		if (!sharedService.isDefinedOrNotNull($scope.categoryData.categoryName)) {
			return alert('Please enter category name')
		}

		if (!sharedService.isDefinedOrNotNull($scope.categoryData.image)) {
			return alert('Please add image')
		}

		var fd = new FormData();
		fd.append('file', $scope.categoryData.image);
		fd.append('categoryData', JSON.stringify($scope.categoryData));

		sharedService.uploadFile(categoryUrl, fd).then(function(response) {
			getCategory();
			reset();
		}, function(error) {
			alert(error.data.errorMessage);
		});
	}

	function getCategory() {
		sharedService.getMethod(categoryUrl).then(function(response) {
			$scope.categories = response.data;
		}, function(error) {
			$scope.errorMessage = 'Some thing went wrong';
			$scope.successMessage = '';
		});
	}

	function removeCategory(id) {
		sharedService.deleteMethod(categoryUrl + "/" + id).then(
				function(response) {
					getCategory();
					$scope.successMessage = 'User created successfully';
					$scope.errorMessage = '';
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong';
					$scope.successMessage = '';
				});
	}

	function reset() {
		$scope.successMessage = '';
		$scope.errorMessage = '';
	}
}
