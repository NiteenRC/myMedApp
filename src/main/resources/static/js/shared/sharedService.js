angular.module('myCart.shared_module.sharedService', []).factory('sharedService', sharedService);

function sharedService($http) {
  'use strict';

  var mem = {};

  var postMethod = function(url, data) {
    return $http.post(url, data);
  };

  var uploadFile = function(url, data) {
    return $http.post(url, data, {
      transformRequest: angular.identity,
      headers: {
        'Content-Type': undefined,
      },
    });
  };

  var deleteMethod = function(url) {
    return $http.delete(url);
  };

  var getAllMethod = function(url) {
    return $http.get(url);
  };

  var getMethod = function(url, id) {
    return $http.get(url, id);
  };

  var isDefinedOrNotNull = function(val) {
    return val !== null && val !== undefined && val !== '';
  };

  var filedValidation = function(name) {
    if (!name) {
      alert('Mandatory fields should not be blank')
      return true;
    }

    if (!isNaN(name)) {
      alert('You are enter only Numbers!!')
      return true;
    }

    if ((name.length < 2) || (name.length > 25)) {
      alert("Your Character must be 2 to 15 Character");
      return true;
    }

    var splChars = "*|,\":<>[]{}`\';()@&$#%";
    for (var i = 0; i < name.length; i++) {
      if (splChars.indexOf(name.charAt(i)) != -1) {
        alert("Special Characters are not allowed!");
        return true;
      }
    }
  };

  var numberValidation = function(number) {
    if (!number) {
      alert('Mandatory fields should not be blank')
      return true;
    }

    if (isNaN(number)) {
      alert('Price should be number!!')
      return true;
    }

    if (!(number > 1 && number < 10000000)) {
      alert("Price should be in between 1 to 10000000");
      return true;
    }

    var splChars = "*|,\":<>[]{}`\';()@&$#%";
    for (var i = 0; i < name.length; i++) {
      if (splChars.indexOf(name.charAt(i)) != -1) {
        alert("Special Characters are not allowed!");
        return true;
      }
    }
  };

  return {
    getAllMethod: getAllMethod,
    postMethod: postMethod,
    deleteMethod: deleteMethod,
    getMethod: getMethod,
    isDefinedOrNotNull: isDefinedOrNotNull,
    uploadFile: uploadFile,
    store: function(key, value) {
      mem[key] = value;
    },
    get: function(key) {
      return mem[key];
    },
    filedValidation: filedValidation,
    numberValidation: numberValidation
  };
}