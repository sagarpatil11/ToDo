myApp.controller( 'loginCtrl',function($scope, $state, loginService)
{
	$scope.signin = function () 
	{
		console.log("in signin",loginService);
		
		var user={};
		
		user.email=$scope.email;
		user.password=$scope.password;
		
		var httpObj=loginService.login(user);
		
		httpObj.then(function(response)
		{
			if(response.data.status == 1)
			{
				localStorage.setItem("accessToken", response.data.token.accessToken);
				localStorage.setItem("refreshToken", response.data.token.refreshToken);
				
				console.log(response.data);
				
				$state.go('home');
			}
			else
			{
				console.log("login unsuccessfull");
				console.log(response.data);
				
				$state.go('login');
			}
			/*else if(response.data.status == -2)
			{
				console.log("login unsuccessfull");
				console.log(response.data.status);
				$state.go('login');
			}
			*/

		})
		
	}
	
	//............................Login with facebook.......................//
	
	$scope.signWithFb=function()
	{
		loginService.loginWithFb().then(function(response){
			console.log(response);
		})
	}
	
})



myApp.service("loginService", function($http){
	
	console.log("in loginservice");
	
	this.login=function(user){
		console.log("in service login");
		return $http({
			url:"login",
			method:"post",
			data:user
		})
	}
	
	
	this.loginWithFb=function(){
		console.log("in loginWithFb");
			return $http({
				url:"loginwithfb"
			})
	}
	
})