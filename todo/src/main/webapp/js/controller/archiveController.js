myApp.controller('archiveCtrl',['$scope','$controller',function($scope,$controller){
		
		$controller('homeCtrl',{$scope : $scope}),
		console.log("in trash ctrl");
		
		$scope.takenote=false;
		$scope.showpinned=false;
		$scope.shownotes=false;
		$scope.showtrash=false;
		$scope.showarchive=true;
		
}])