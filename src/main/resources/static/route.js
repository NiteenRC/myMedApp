angular.module('myCart').config(function($routeProvider) {
	$routeProvider.when('/home', {
		templateUrl : 'pages/user/home.html',
		controller : 'homeController'
	}).when('/user', {
		templateUrl : 'pages/user/user.html',
		controller : 'userController'
	}).when('/forgetPassword', {
		templateUrl : 'pages/user/forgetPassword.html',
		controller : 'forgetPasswordController'
	}).when('/category', {
		templateUrl : 'pages/user/category.html',
		controller : 'categoryController'
	}).when('/productList', {
		templateUrl : 'pages/user/productList.html',
		controller : 'productController'
	}).when('/productSelected', {
		templateUrl : 'pages/user/productSelected.html',
		controller : 'productSelectController'
	}).when('/cart', {
		templateUrl : 'pages/user/cart.html',
		controller : 'cartController'
	}).when('/orderSummary', {
		templateUrl : 'pages/user/orderSummary.html',
		controller : 'orderSummaryController'
	}).when('/trackOrder', {
		templateUrl : 'pages/user/trackOrder.html',
		controller : 'orderSummaryController'
	}).when('/checkout', {
		templateUrl : 'pages/user/checkout.html',
		controller : 'checkoutController'
	}).when('/productManage', {
		templateUrl : 'pages/admin/manage_product.html',
		controller : 'productManageController'
	}).when('/categoryManage', {
		templateUrl : 'pages/admin/manage_category.html',
		controller : 'categoryManageController'
	}).when('/orderManage', {
		templateUrl : 'pages/admin/manage_order.html',
		controller : 'orderManageController'
	}).otherwise({
		redirectTo : '/home'
	});
});
