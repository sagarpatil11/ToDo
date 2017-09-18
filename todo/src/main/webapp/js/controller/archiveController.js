myApp.controller('archiveCtrl',['$scope','$controller',function($scope,$controller){
		
		$controller('homeCtrl',{$scope : $scope}),
		console.log("in archive ctrl");
		
		$scope.takenote=false;
		$scope.showpinned=false;
		$scope.shownotes=false;
		$scope.showtrash=false;
		$scope.showarchive=true;
		
		$scope.headercolor={"background-color":"rgb(96, 125, 139)", "border":"none"};
		$scope.headername="Archive";
		$scope.headernamestyle={"color":"white","font-size":"22px", "font-weight":"400"};
}])