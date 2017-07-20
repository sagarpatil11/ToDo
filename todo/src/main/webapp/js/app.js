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
				   controller:"regCtrl"
				 })
				 
			.state("home",{
					url:"/home",
					templateUrl:"templates/home.html"
			})

		 $urlRouterProvider.otherwise('/login');

});