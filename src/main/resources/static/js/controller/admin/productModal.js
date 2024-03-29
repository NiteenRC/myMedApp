angular.module('myCart.product_module.productModal', ['myCart.shared_module.sharedService']).controller('productModalCtrl', modalCtrl);

function modalCtrl($scope, $http, $modalInstance, sharedService) {
  'use strict';

  var url_home = sharedService.urlHome();
  
  var productUrl = url_home+'/product';
  $scope.saveProduct = saveProduct;
  $scope.cancel = cancel;
  $scope.clear = clear;
  $scope.categories = sharedService.get('categories');

  $scope.productData = {
    productID: null,
    productName: null,
    productDesc: null,
    price: null,
  };

  function saveProduct() {
    if (sharedService.numberValidation($scope.productData.price)) {
      $scope.productData.price = 1;
      return;
    }

    if(!sharedService.isDefinedOrNotNull($scope.item)){
    	alert('Please select category');
    	return;
    }
    sharedService.postMethod(productUrl + '/' + $scope.item.categoryID, $scope.productData).then(
      function(response) {
        $modalInstance.close(response);
      },
      function(error) {
        alert(error.data.errorMessage);
      }
    );
  }

  function cancel() {
    $modalInstance.dismiss('cancel');
  }

  function clear() {
    $scope.productData = {};
  }
}