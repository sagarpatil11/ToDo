myApp.controller( 'loginCtrl',function($scope, $state, loginService)
{
	$scope.signin = function () 
	{
		var user={};
		
		user.email=$scope.email;
		user.password=$scope.password;
		
		var httpObj = loginService.login(user);
		
		httpObj.then(function(response)
		{
			if(response.data.status == 1)
			{
				localStorage.setItem("accessToken", response.data.token.accessToken);
				
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
})



myApp.service("loginService", function($http){
	
	console.log("in loginservice")
	this.login=function(user){
		return $http({
			url:"login",
			method:"post",
			data:user
		})
	}
})