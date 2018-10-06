angular.module('myCart.shared_module.sharedService', []).factory(
		'sharedService', sharedService);

function sharedService($http) {
	'use strict';

	var postMethod = function(url, data) {
		return $http.post(url, data)
	};
	
	var uploadFile = function(url, data) {
		return $http.post(url, data,{
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		})
	};
	
	var deleteMethod=function(url){
		return $http.delete(url)
	};
	
	var getAllMethod=function(url){
		return $http.get(url)
	};
	
	var getMethod=function(url, id){
		return $http.get(url, id)
	};
	
	var isDefinedOrNotNull=function(val) {
		return val !== null && val !== undefined && val !== '';
	};
	
	var mem = {};

	return {
		getAllMethod:getAllMethod,
		postMethod : postMethod,
		deleteMethod:deleteMethod,
		getMethod:getMethod,
		isDefinedOrNotNull:isDefinedOrNotNull,
		uploadFile:uploadFile,
		store: function (key, value) {
	            mem[key] = value;
	        },
	    get: function (key) {
	            return mem[key];
	        }
	};
}