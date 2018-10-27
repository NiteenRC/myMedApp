angular.module('myCart.product_module.productModal', ['myCart.shared_module.sharedService']).controller('productModalCtrl', modalCtrl);

function modalCtrl($scope, $http, $modalInstance, sharedService) {
  'use strict';

  var productUrl = '/product';
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
    if (sharedService.filedValidation($scope.productData.productName) || sharedService.filedValidation($scope.productData.productDesc) || sharedService.filedValidation($scope.item)) {
      return;
    }

    if (sharedService.numberValidation($scope.productData.price)) {
      $scope.productData.price = 1;
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