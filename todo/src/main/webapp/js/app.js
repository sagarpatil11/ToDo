var myApp=angular.module("todo",['ui.router'])
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
				 });

		 $urlRouterProvider.otherwise('/login');

});