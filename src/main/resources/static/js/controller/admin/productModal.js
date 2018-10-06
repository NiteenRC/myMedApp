angular
    .module('myCart.product_module.productModal', [
        'myCart.shared_module.sharedService',
    ])
    .controller('productModalCtrl', modalCtrl);

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
        if (!sharedService.isDefinedOrNotNull($scope.productData.productName)) {
            return alert('Please add Product name');
        }

        if (!sharedService.isDefinedOrNotNull($scope.productData.price)) {
            return alert('Please add price');
        } else if (isNaN($scope.productData.price)) {
            return alert('Price should be number');
        }

        if (!sharedService.isDefinedOrNotNull($scope.item)) {
            return alert('Please select category');
        }

        sharedService
            .postMethod(
                productUrl + '/' + $scope.item.categoryID,
                $scope.productData
            )
            .then(
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
