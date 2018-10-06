angular.module('myCart.product_select_module', ['myCart.shared_module.sharedService']).controller(
    'productSelectController', productSelectController);

function productSelectController($scope, $rootScope, sharedService, $location) {
    'use strict';

    var productUrl = "/product/";
    var cartUrl = "/cart/";
    var RATING_URL = "/rating/";

    getAlCartCount();

    $scope.productItem = sharedService.get('productItem');
    $scope.addToCart = addToCart;
    $scope.addRating = addRating;
    $scope.calculateRating = calculateRating;

    $scope.ratings = [{
        star: 1,
        max: 5
    }];

    $scope.rating = {
        firstName: null,
        emailId: null,
        review: null,
        star: null,
    };

    $scope.selectRating = function(star) {
        $scope.rating.star = star;
    }

    function calculateRating(rating) {
        var newVal = 0;
        angular.forEach(rating, function(oldVal) {
            if (sharedService.isDefinedOrNotNull(oldVal.star)) {
                newVal = newVal + parseInt(oldVal.star);
            }
        });
        if (newVal === 0 || rating.lenth == 0) {
            return 0;
        } else {
            return Math.round(((newVal / rating.length) * 100) / 100)
        }
    }

    function selectProduct(productID) {
        sharedService.getMethod(productUrl + productID).then(
            function(response) {
                $scope.productItem = response.data;
                $location.path('/productSelected');
                sharedService.store('productItem', response.data);
            },
            function(error) {
                $scope.errorMessage = 'Error while getting: ' + error;
                $scope.successMessage = '';
            });
    }

    function addRating() {
        if (!sharedService.isDefinedOrNotNull($scope.productItem.productID)) {
            return alert('Please select Product again!!');
        }

        var userID1 = parseInt($rootScope.userID)

        sharedService.postMethod(RATING_URL + $scope.productItem.productID + "/" + userID1,
            $scope.rating).then(function(response) {
            selectProduct($scope.productItem.productID);
            alert('Rating added successfully!!');
        }, function(error) {
            alert(error.data.errorMessage);
        });
    }

    function addToCart(cart) {
        $scope.productItem.userID = parseInt($rootScope.userID);
        sharedService.postMethod(cartUrl, $scope.productItem).then(function(response) {
            $scope.carts = response.data;
            getAlCartCount();
            alert(cart.productName + '  added to cart successfully!!');
        }, function(error) {
            $scope.errorMessage = 'Error while creating' + error;
            $scope.successMessage = '';
        });
    }

    function getAlCartCount() {
        sharedService.getAllMethod(cartUrl + parseInt($rootScope.userID)).then(
            function(response) {
                $rootScope.totalCartsByUser = response.data.length;
            },
            function(error) {
                $scope.errorMessage = 'Error while Counting' + error;
                $scope.successMessage = '';
            });
    }
}