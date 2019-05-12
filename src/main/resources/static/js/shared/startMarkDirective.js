angular.module('myCart.directive_module.startMarkDirectiveService', [ 'myCart.shared_module.sharedService' ]).directive('starMark', starMark);

function starMark($http) {
  'use strict';

  return {
    restrict: 'A',
    link: function(scope, elem, attr) {
      elem.find('label').append('<span>*</span>');
      elem.find('input').attr('required', 'required');
    }
  };
}