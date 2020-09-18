angular.module('myCart').config(function($routeProvider) {
	$routeProvider.when('/home', {
		templateUrl : 'pages/user/home.html',
		controller : 'homeController'
	}).when('/user', {
		templateUrl : 'pages/user/user.html',
		controller : 'userController'
	}).when('/productList', {
		templateUrl : 'pages/user/productList.html',
		controller : 'productController'
	}).when('/cart', {
		templateUrl : 'pages/user/cart.html',
		controller : 'cartController'
	}).when('/addStock', {
      		templateUrl : 'pages/admin/add_stock.html',
      		controller : 'cartController'
    }).when('/productManage', {
		templateUrl : 'pages/admin/manage_product.html',
		controller : 'productManageController'
	}).when('/categoryManage', {
		templateUrl : 'pages/admin/manage_category.html',
		controller : 'categoryManageController'
	}).when('/generateReport', {
		templateUrl : 'pages/user/report.html',
		controller : 'cartController'
	}).when('/bill', {
		templateUrl : 'pages/user/bill.html',
		controller : 'billController'
	}).when('/billSuccess', {
		templateUrl : 'pages/user/billSuccess.html',
		controller : 'billController'
	}).otherwise({
		redirectTo : '/productList'
	});
});