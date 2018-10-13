angular
    .module('myCart.cart_module', ['myCart.shared_module.sharedService'])
    .controller('cartController', cartController);

function cartController(
    $scope,
    $rootScope,
    $uibModal,
    sharedService,
    $location
) {
    'use strict';

    var cartUrl = '/cart/';
    var CART_LIST_SAVE = '/carts';
    var CART_LIST_DELETE = '/cartsDelete';
    var productsUrl = '/products';

    getCarts();
    getAlCartCount();
    fetchAllProducts();

    $scope.removeCart = removeCart;
    $scope.addToCart = addToCart;
    $scope.addCartList = addCartList;
    $scope.removeCartList = removeCartList;
    $scope.order = {};
    $scope.addToCart = addToCart;

    $scope.cartData = {
        productName: null,
        price: null,
        image: null,
        qty: null,
    };

    $scope.selectedProduct = function(productID) {
        var originalProductName = null;
        var originalPrice = 0;
        var originalProductID = 0;
        for (var i = 0; i < $scope.products.length; i++) {
            if ($scope.products[i].productID == productID) {
                originalProductID = $scope.products[i].productID
                originalPrice = $scope.products[i].price;
                originalProductName = $scope.products[i].productName;
            }
        }
        for (var i = 0; i < $scope.rows.length; i++) {
            if (!$scope.products[i]) {
                alert('Medicine is already Selected!!');
                return;
            }

            if ($scope.products[i].productID == productID) {
                $scope.rows[i].productID = originalProductID
                $scope.rows[i].price = originalPrice;
                $scope.rows[i].productName = originalProductName;
            }
        }
    };

    $scope.rows = [];

    $scope.addRow = function() {
        $scope.rows.push({
            productID: 0,
            productName: null,
            qty: 1,
            price: 0
        });
    };

    function fetchAllProducts() {
        sharedService.getAllMethod(productsUrl).then(
            function(response) {
                $scope.products = response.data;
            },
            function(error) {
                $scope.errorMessage = 'Some thing went wrong' + error;
                $scope.successMessage = '';
            }
        );
    }

    $scope.orderVo = {
        userID: parseInt($rootScope.userID),
        purchaseOrder: $scope.order,
        purchaseOrderDetail: $scope.carts,
    };

    function addToCart(cart) {
        sharedService.postMethod(cartUrl, cart).then(
            function(response) {
                $scope.carts = response.data;
                $rootScope.totalCartsByUser = response.data;
                alert(cart.productName + '  added to cart successfully!!');
            },
            function(error) {
                $scope.errorMessage = 'Error while creating' + error;
                $scope.successMessage = '';
            }
        );
    }

    function addCartList() {
        sharedService.postMethod(CART_LIST_SAVE, $scope.rows).then(
            function(response) {
                $location.path('/checkout');
                $scope.carts = response.data;
            },
            function(error) {
                $scope.errorMessage = 'Error while creating' + error;
                $scope.successMessage = '';
            }
        );
    }

    function getCarts() {
        sharedService.getAllMethod(cartUrl + parseInt($rootScope.userID)).then(
            function(response) {
                $scope.carts = response.data;
                $scope.orderVo.purchaseOrderDetail = response.data;
            },
            function(error) {
                $scope.errorMessage = 'Error while creating' + error;
                $scope.successMessage = '';
            }
        );
    }

    function removeCartList(id) {
    		sharedService.postMethod(CART_LIST_DELETE, $scope.rows).then(
                        function(response) {
                            $location.path('/checkout');
                            $scope.carts = response.data;
                        },
                        function(error) {
                            $scope.errorMessage = 'Error while creating' + error;
                            $scope.successMessage = '';
                        }
                    );
    	}

    function removeCart(index) {
        angular.forEach($scope.rows, function (row) {
              $scope.rows.splice(index, 1);
              //if no columns left in the row push a blank array
              if (row.length === 0) {
                $scope.rows.data = [];
              }
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
            }
        );
    }

    $scope.getTotal = function(val) {
        var total = 0;
        angular.forEach($scope.rows, function(oldVal) {
            total += oldVal[val];
        });
        return total;
    };

    $scope.totalAmountToPaid = function(){
        var totalAmount = 0;
        angular.forEach($scope.rows, function(val) {
            totalAmount += val.price*val.qty;
        });
        return totalAmount;
    }
}
