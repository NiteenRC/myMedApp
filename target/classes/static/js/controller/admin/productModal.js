angular.module('myCart.product_module.productModal',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'productModalCtrl', modalCtrl);

function modalCtrl($scope, $http, $modalInstance, sharedService) {
	'use strict';

	var productUrl = "/product";
	$scope.saveProduct = saveProduct;
	$scope.cancel = cancel;
	$scope.clear = clear;
	$scope.categories = sharedService.get('categories');

	$scope.productData = {
		productID : null,
		name : null,
		productDesc : null,
		price : null,
		image : null,
	};

	function saveProduct() {

		if (!sharedService.isDefinedOrNotNull($scope.productData.name)) {
			return alert('Please add Product name')
		}

		if (!sharedService.isDefinedOrNotNull($scope.productData.price)) {
			return alert('Please add price')
		} else if (isNaN($scope.productData.price)) {
			return alert('Price should be number')
		}

		if (!sharedService.isDefinedOrNotNull($scope.item)) {
			return alert('Please select category')
		}

		if (!sharedService.isDefinedOrNotNull($scope.productData.image)) {
			return alert('Please add image')
		}

		var fd = new FormData();
		fd.append('file', $scope.productData.image);
		fd.append('productData', JSON.stringify($scope.productData));

		sharedService.uploadFile(productUrl + "/" + $scope.item.categoryID, fd)
				.then(function(response) {
					$modalInstance.close(response);
				}, function(error) {
					alert(error.data.errorMessage);
				});
	}

	function cancel() {
		$modalInstance.dismiss('cancel');
	}

	function clear() {
		$scope.productData = {};
	}
}