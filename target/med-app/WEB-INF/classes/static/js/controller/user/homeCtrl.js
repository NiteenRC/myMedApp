angular.module('myCart.home_module',
		[ 'myCart.shared_module.sharedService' ]).controller(
		'homeController', homeController);

function homeController($scope, $rootScope, $route, sharedService) {
	'use strict';

	var url_home = sharedService.urlHome();
	$scope.slides = [];

	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree1.jpg'
	});

	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree2.jpg'
	});
	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree3.jpg'
	});
	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree4.jpg'
	});
	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree5.jpg'
	});
	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree6.jpg'
	});
	$scope.slides.push({
		url : '#home',
		image : '..'+url_home+'/images/sarees/saree7.jpg'
	});

	$scope.setActive = function(idx) {
		$scope.slides[idx].active = true;
	};
}