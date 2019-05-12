angular.module('myCart', [ 'ngRoute',
	    'ngCookies',
		'ui.bootstrap',
		'myCart.user_module',
		'myCart.home_module',
		'myCart.product_module',
		'myCart.cart_module',
		'myCart.categoryManage_module',
		'myCart.productManage_module',
		'myCart.product_module.productModal',
		'myCart.shared_module.sharedService',
		'myCart.directive_module.startMarkDirectiveService'
]);

