angular.module('myCart.directive_module.directiveService',
		[ 'myCart.shared_module.sharedService' ]).directive('fileModel',
		fileModel);

function fileModel(sharedService, $parse) {
	'use strict';

	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}