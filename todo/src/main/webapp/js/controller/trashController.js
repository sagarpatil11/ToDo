myApp.controller('trashCtrl',['$scope','$controller',function($scope,$controller){
		
		$controller('homeCtrl',{$scope : $scope}),
		console.log("in trash ctrl");
		
		$scope.takenote=false;
		$scope.showpinned=false;
		$scope.shownotes=false;
		$scope.showtrash=true;
		$scope.showarchive=false;
		
		$scope.headercolor={"background-color":"rgb(99, 99, 99)", "border":"none"};
		$scope.headername="Trash";
		$scope.headernamestyle={"color":"white","font-size":"22px", "font-weight":"400"}
}])