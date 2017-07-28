myApp.controller( 'homeCtrl',function($scope, $state, homeService){
	
	$scope.inputNote=function(){
		console.log("shownote");
		
		$scope.shownote=true;
	}
	
	$scope.submitNote=function(){
		console.log("inside note");
		$scope.shownote=false;
		
		var noteData={};
		
		noteData.title=$scope.title;
		noteData.description=$scope.description;
		
		$scope.title="";
		$scope.description="";
		
		var httpnote=homeService.addNote(noteData);
		
		httpnote.then(function(response1)
		{
			console.log(response1);
			if(response1.data.status == 4)
			{
				console.log("note added");
				$scope.showNotes();
			}
			else
			{
				console.log("access token expired");
				
				homeService.getNewAccessToken().then(function(response2){
					if(response2.data.status == 1)
					{
						localStorage.setItem("accessToken", response2.data.token.accessToken);
						localStorage.setItem("refreshToken", response2.data.token.refreshToken);
						
						homeService.addNote(noteData);
					}
					else
					{
						console.log(response2.data);
						$state.go('login');
					}
				})
			}
			
			
		});
	}
	
	$scope.showNotes=function(){
	homeService.getNotes().then(function(resp){
		console.log(resp);
		$scope.records=resp.data;
	})
	}
	$scope.showNotes();
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
						headers:{"refsagar

dgsgsg
gsgsdg

sgsdgsdg
hdfhdfhdf
hdfhdfh
test

adfdgfdf

hjhgjhgkhk

vnghj
hjhjh
jjjjkj



dfafa

adfafaff
ffffffreshToken":localStorage.getItem("refreshToken")}
					})
		}
	
	this.getNotes=function(){
		console.log("in getNotes service");
		return $http({
						url:"getNotes",
						method:"get",
						headres:{"refreshToken":localStorage.getItem("refreshToken")}
				})
	}
})


