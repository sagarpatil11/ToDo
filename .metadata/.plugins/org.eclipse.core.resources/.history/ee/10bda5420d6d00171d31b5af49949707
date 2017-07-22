myApp.controller( 'loginCtrl',function($scope, $state, $http)
{
	$scope.isSignIn = function () 
	{
		var httpObj = loginService.isLogin();
		httpObj.then(function(data)
		{
			$scope.user = data;
			console.log("inside the controller");
			if(data.status==200)
			{
				
			}
			else
			{
				$state.go('login');
			}

		})
	}
})