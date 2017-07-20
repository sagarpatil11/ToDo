myApp.controller("regCtrl", function($scope,$state,regService){
	
	$scope.signUp=function(){
		console.log("signup method");
		
		var user={};
		
		user.fullname=$scope.fullname;
		user.email=$scope.email;
		user.mobile=$scope.mobile;
		user.password=$scope.password;
		
		console.log(user);
		
		var httpobj=regService.signup(user);
	
		
		httpobj.then(function(response){
			console.log(response);
			
			if(response.data.status == 1){
				$state.go("login");
			}
			else{
				$state.go("register");
			}
		})
		
	}
});



myApp.service("regService", function($http){
	
	console.log("in regservice");
	
	this.signup=function(user){
		return $http({
				url:"signup",
				method:"post",
				data:user
		});
			
	}
});