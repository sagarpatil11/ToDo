myApp.controller('trashCtrl',['$scope','$controller',function($scope,$controller){
		
		$controller('homeCtrl',{$scope : $scope}),
		console.log("in trash ctrl");
		
		$scope.takenote=false;
		$scope.shownotes=false;
		$scope.showtrash=true;
		$scope.showarchive=false;
		
}])