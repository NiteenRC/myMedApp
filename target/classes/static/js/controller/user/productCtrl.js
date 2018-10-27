angular.module('myCart.product_module', ['myCart.shared_module.sharedService']).controller('productController', productController);

function productController($scope, $rootScope, $uibModal, sharedService, $location) {
  'use strict';

  var productsUrl = '/products';
  var productByNameUrl = '/product/byName/';
  var PRODUCT_BY_CATEGORY_ID = '/advancedSearch/';
  var categoryUrl = '/category';

  $scope.products = fetchAllProducts();
  $scope.getCategoryID = sharedService.get('categoryID');

  $scope.searchByProductName = searchByProductName;
  $scope.advancedSearch = advancedSearch;
  $scope.searchByCategory = searchByCategory;
  $scope.getCategory = getCategory();
  $rootScope.productName = null;
  $rootScope.categoryID = 0;

  function advancedSearch() {
    var productName =
      $rootScope.productName == '' ? null : $rootScope.productName;
    var categoryID = !$rootScope.categoryID ? 0 : $rootScope.categoryID;
    sharedService
      .getAllMethod(
        PRODUCT_BY_CATEGORY_ID + categoryID + '/' + productName
      )
      .then(
        function(response) {
          $scope.products = response.data;
        },
        function(error) {
          $scope.errorMessage = 'Some thing went wrong' + error;
          $scope.successMessage = '';
        }
      );
  }

  function getCategory() {
    sharedService.getMethod(categoryUrl).then(
      function(response) {
        $scope.categories = response.data;
      },
      function(error) {
        $scope.errorMessage = 'Some thing went wrong';
        $scope.successMessage = '';
      }
    );
  }

  function searchByProductName() {
    $rootScope.productName = $scope.productName;
    advancedSearch();
  }

  function searchByCategory(categoryID) {
    $rootScope.categoryID = categoryID;
    advancedSearch();
  }

  function fetchProductsByCategoryIDAndProductName() {
    $scope.getCategoryID = !$scope.getCategoryID ? 0 : $scope.getCategoryID;
    sharedService
      .getAllMethod(
        productByNameUrl +
        $scope.getCategoryID +
        '/' +
        $scope.productName
      )
      .then(
        function(response) {
          $scope.products = response.data;
        },
        function(error) {
          $scope.errorMessage = 'Some thing went wrong' + error;
          $scope.successMessage = '';
        }
      );
  }

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
}