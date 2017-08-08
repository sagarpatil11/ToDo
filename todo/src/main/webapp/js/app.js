var myApp=angular.module("todo",['ui.router','ngSanitize','ui.bootstrap'])
.config(function($stateProvider, $urlRouterProvider){
	$stateProvider
			.state("login",{
					url:"/login",
					templateUrl:"templates/login.html",
					controller:"loginCtrl"
				})
			
			.state("register",{
				   url:"/register",
				   templateUrl:"templates/userregistration.html",
				   controller:"regCtrl"
				 })
				 
			.state("home",{
					url:"/home",
					templateUrl:"templates/home.html",
					controller:"homeCtrl"	
			})

		 $urlRouterProvider.otherwise('/login');

});




myApp.directive('contenteditable1', [function() {
    return {
        require: '?ngModel',
        scope: {
        },
        link: function(scope, element, attrs, ctrl) {
            // view -> model (when div gets blur update the view value of the model)
            element.bind('blur', function() {
                scope.$apply(function() {
                    ctrl.$setViewValue(element.html());
                });
            });

            // model -> view
            ctrl.$render = function() {
                element.html(ctrl.$viewValue);
            };

            // load init value from DOM
            ctrl.$render();

            // remove the attached events to element when destroying the scope
            scope.$on('$destroy', function() {
                element.unbind('blur');
                element.unbind('paste');
                element.unbind('focus');
            });
        }
    };

}]);

