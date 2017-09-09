myApp.controller('socialLoginController',function($scope, $state, $location, socialLoginService)
{
	console.log("in socialLoginController");
	
	var urlString= $location.search().token;
	
	console.log("tokenstr:: "+urlString);
	
	var urlObj={};
	
	urlObj.urlString=urlString;
	
	var httpobj=socialLoginService.getTokenByUrl(urlObj);
	
	httpobj.then(function(response)
	{
			console.log(response.data);
		
			if(response.data.status == 5)
			{
				localStorage.setItem("accessToken", response.data.token.accessToken);
				localStorage.setItem("refreshToken", response.data.token.refreshToken);
				
				$state.go('home');
			}
	})		
})


myApp.service("socialLoginService", function($http){
	
	console.log("in loginservice");
	
	this.getTokenByUrl=function(urlObj){
		return $http({
			url:"getTokenByUrl",
			method:"post",
			data:urlObj
		})
	}
	
})