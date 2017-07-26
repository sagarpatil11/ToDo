myApp.controller( 'homeCtrl',function($scope, $state, homeService){
	
	$scope.addNote=function(){
		console.log("shownote");
		
		$scope.shownote=true;
	}
	
	$scope.submitNote=function(){
		
		$scope.shownote=false;
		
		var noteData={};
		console.log($scope.title);
		noteData.title=$scope.title;
		noteData.description=$scope.description;
		
		console.log(noteData);
		
		var httpnote=homeService.addNote(noteData);
		
		httpnote.then=function(response)
		{
			if(response.data.status == 1)
			{
				console.log(response.data);
			}
			else
			{
				console.log(response.data);
			}
		}
	}
})




myApp.service("homeService", function($http){
	
	console.log("in homeservice")
	this.addNote=function(noteData){
		return $http({
			url:"addNote",
			method:"post",
			data:noteData
		})
	}
})