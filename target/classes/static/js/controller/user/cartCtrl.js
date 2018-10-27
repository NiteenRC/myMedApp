angular.module('myCart.cart_module', ['myCart.shared_module.sharedService']).controller('cartController', cartController);

function cartController($scope, $rootScope, $uibModal, sharedService, $location) {
  'use strict';

  var CART_LIST_SAVE = '/cartsAdd';
  var CART_LIST_DELETE = '/cartsDelete';
  var productsUrl = '/products';

  $scope.rows = [];
  fetchAllProducts();
  addRow();
  $scope.addRow = addRow;

  $scope.removeCart = removeCart;
  $scope.addCartList = addCartList;
  $scope.removeCartList = removeCartList;
  $scope.isSingleCartAvail = true;

  $scope.cartData = {
    productName: null,
    price: null,
    image: null,
    qty: null,
  };

  $scope.qtyValidation = function(qty, index) {
    if (sharedService.numberValidation(qty)) {
      $scope.rows[index].qty = 1
      return;
    }
  }

  $scope.selectedProduct = function(productID, index) {
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

    var duplicateCount = 0;
    for (var i = 0; i < $scope.rows.length; i++) {
      if ($scope.rows[i].productID == productID) {
        duplicateCount++;
      }

      if ((duplicateCount >= 1 && index == 0) || duplicateCount >= 2) {
        alert('Medicine is already Selected!!');
        removeCart(index);
        return;
      }

      $scope.rows[index].productID = originalProductID
      $scope.rows[index].price = originalPrice;
      $scope.rows[index].productName = originalProductName;
    }
  };

  function addRow() {
    $scope.rows.push({
      productID: 0,
      productName: null,
      qty: 1,
      price: 0
    });

    if ($scope.rows && $scope.rows.length == 1) {
      $scope.isSingleCartAvail = true;
    } else {
      $scope.isSingleCartAvail = false;
    }

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

  function addCartList() {
    if ($scope.rows.length === 1 && $scope.rows[0].productName === null) {
      alert('Please select atleast one product');
      return;
    }

    sharedService.postMethod(CART_LIST_SAVE, $scope.rows).then(
      function(response) {
        $scope.carts = response.data;
        $scope.rows = [];
      },
      function(error) {
        $scope.errorMessage = 'Error while creating' + error;
        $scope.successMessage = '';
      }
    );
  }

  function removeCartList(id) {
    if ($scope.rows.length === 1 && $scope.rows[0].productName === null) {
      alert('Please select atleast one product');
      return;
    }

    sharedService.postMethod(CART_LIST_DELETE, $scope.rows).then(
      function(response) {
        $scope.carts = response.data;
        $scope.rows = [];
      },
      function(error) {
        alert(error.data.errorMessage);
        $scope.errorMessage = 'Error while creating' + error;
        $scope.successMessage = '';
      }
    );
  }

  function removeCart(index) {
    if ($scope.rows && $scope.rows.length == 1) {
      $scope.isSingleCartAvail = true;
    } else {
      $scope.rows.splice(index, 1);
      $scope.isSingleCartAvail = false;
    }
  }

  $scope.getTotal = function(val) {
    var total = 0;
    angular.forEach($scope.rows, function(oldVal) {
      total += oldVal[val];
    });
    return total;
  };

  $scope.totalAmountToPaid = function() {
    var totalAmount = 0;
    angular.forEach($scope.rows, function(val) {
      totalAmount += val.price * val.qty;
    });
    return totalAmount;
  }
}