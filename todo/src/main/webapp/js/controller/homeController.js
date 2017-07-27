myApp.controller( 'homeCtrl',function($scope, $state, homeService){
	
	$scope.inputNote=function(){
		console.log("shownote");
		
		$scope.shownote=true;
	}
	
	$scope.submitNote=function(){
		
		$scope.shownote=false;
		
		var noteData={};
		
		noteData.title=$scope.title;
		noteData.description=$scope.description;
		
		$scope.title="";
		$scope.description="";
		
		var httpnote=homeService.addNote(noteData);
		
		httpnote.then=function(response1)
		{
			console.log(response1.data);
			if(response1.data.status == 4)
			{
				console.log("note added");
			}
			if(response1.data.status == -4)
			{
				console.log("access token expired");
				
				homeService.getNewAccessToken().then(function(response2){
					if(response2.data.status == 1)
					{
						localStorage.setItem("accessToken", response2.data.token.accessToken);
						localStorage.setItem("refreshToken", response2.data.token.refreshToken);
					}
					else
					{
						$state.go('login');
					}
				})
			}
			
		}
	}
})




myApp.service("homeService", function($http){
	
	console.log("in homeservice");
	this.addNote=function(noteData){
		return $http({
			url:"addNote",
			method:"post",
			data:noteData,
			headers:{"accessToken":localStorage.getItem("accessToken")}
		})
	}
	
	this.getNewAccessToken=function(){
		console.log("in homeservice2");
		return $http({
						url:"newAccessToken",
						method:"post",
						headers:{"refreshToken":localStorage.getItem("refreshToken")}
					})
		}
})


