myApp.controller('forgetPasswordCtrl',function($scope, $state,forgetPasswordService)
{
	
		$scope.forgetPassword=function()
		{
				console.log("in forgetPassword");
				console.log("email::",$scope.emailForPwdChange);
				var emailobj={};
				emailobj.userEmail=$scope.emailForPwdChange;
				
				var httpobj=forgetPasswordService.forgetPassword(emailobj);
				
				httpobj.then(function(response){
						
						if(response.data.status == 7)
						{
							console.log("forget pwd resp::"+response.data);
							$state.go("forgetPwdLanding");
						}
						else
						{
							console.log("forget pwd resp::"+response.data);
							$state.go("login");
						}	
				})
		}
		
		
		$scope.resetPassword=function()
		{
				console.log("in resetPassword");
				
				var passwordobj={};
				passwordobj.newPassword=$scope.newPassword;
				
				var httpobj=forgetPasswordService.resetPassword(passwordobj);
				
				httpobj.then(function(response){
						
						if(response.data.status == 8)
						{
							console.log("forget pwd resp::"+response.data);
							$state.go("login");
						}
						else
						{
							console.log("forget pwd resp::"+response.data);
							$state.go("login");
						}	
				})
		}
	
})


myApp.service("forgetPasswordService", function($http){
		
		this.forgetPassword=function(emailobj){
			console.log("in service forgetPwd");
			return $http({
						url:"forgetPassword",
						method:"post",
						data: emailobj	
			})
		}
		
		
		this.resetPassword=function(passwordobj){
			console.log("in service resetpwd");
			return $http({
						url:"resetPassword",
						method:"post",
						data: passwordobj
			})
		}
})
