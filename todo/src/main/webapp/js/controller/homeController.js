myApp.controller( 'homeCtrl',function($scope, $state,$uibModal, homeService){
	
		$scope.inputNote=function(){
			console.log("shownote");
		
			$scope.shownote=true;
		}
	
		
		
		
		//.....................show list view.................//
		
		
		$scope.gridView=function(){
			
			$scope.listBtn=true;
			$scope.gridBtn=false;
			
			$scope.gridview=true;
			$scope.listview=false;
		}
		
		
		//.....................show grid view.................//
		
		
		$scope.listView=function(){
			
			$scope.listBtn=false;
			$scope.gridBtn=true;
			
			$scope.gridview=false;
			$scope.listview=true;
		}
		
		
		
		//......................Add new note .................//
		
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
				if(response1.data.status == 1)
				{
						$scope.notesList=response1.data.list.reverse();
						console.log("note added");
						
				}
				else if(response1.data.status == -4)
				{
						console.log("access token expired");
				
						homeService.getNewAccessToken().then(function(response2){
							
							if(response2.data.status == 4)
							{
									localStorage.setItem("accessToken", response2.data.token.accessToken);
									localStorage.setItem("refreshToken", response2.data.token.refreshToken);
						
									homeService.addNote(noteData).then(function(resp){
										console.log(resp.data);
										$scope.notesList=resp.data.list.reverse();
									})
							}
							else
							{
									console.log(response2.data);
									$state.go('login');
							}
						})
				}
				else
				{
						console.log(response2.data);
						$state.go('login');
				}
			
			
			});
		}
	
	
	//..........Delete note.............//
	
	
	$scope.deleteNote=function(tid){
		
		console.log(tid);
		//var id={"tid":id};
		homeService.deleteNote(tid).then(function(response1){
			
			console.log(response1);
			
			if(response1.data.status == 1)
			{
					$scope.notesList=response1.data.list.reverse();
					
					console.log("note deleted");
			}
			else if(response1.data.status == -4)
			{
					console.log("access token expired");
			
					homeService.getNewAccessToken().then(function(response2){
						
						if(response2.data.status == 4)
						{
								localStorage.setItem("accessToken", response2.data.token.accessToken);
								localStorage.setItem("refreshToken", response2.data.token.refreshToken);
					
								homeService.addNote(noteData).then(function(resp){
									console.log(resp.data);
									
									$scope.notesList=resp.data.list.reverse();
								})
						}
						else
						{
								console.log(response2.data);
								
								$state.go('login');
						}
					})
			}
			else
			{
				console.log(response1.data);
				$state.go('login');
			}
		
		
		})
		
	}
	
	
	//......................Update note.........................//
	
	
	
	$scope.openPopup=function(data){
			console.log("in ctrl ",data.title," ",data.description," ");
		
			var modal=$uibModal.open({
				templateUrl:"templates/updatePopup.html",
				controller:function($uibModalInstance){
					var $ctrl=this;
				
					this.updateTitle=data.title;
					this.updateDescription=data.description;
					this.noteId=data.tid;
					this.createdDate=data.creation_date;
					this.user=data.user;
				
					this.updateNote=function(tid){
						
						console.log(tid);
						console.log(this.updateTitle);
						console.log(this.updateDescription);
						
						$uibModalInstance.dismiss('Done');
						
						var updateData={};
						
						updateData.tid=tid;
						updateData.title=this.updateTitle;
						updateData.description=this.updateDescription;
						updateData.user=this.user;
						updateData.creation_date=this.createdDate;
						
						
						homeService.updateNote(updateData)
						.then(function(response1)
						{
							console.log(response1);
								if(response1.data.status == 1)
								{
										$scope.notesList=response1.data.list.reverse();
										console.log("note updated");
					
								}
								else if(response1.data.status == -4)
								{
										console.log("access token expired");
								
										homeService.getNewAccessToken().then(function(response2){
											
											if(response2.data.status == 4)
											{
													localStorage.setItem("accessToken", response2.data.token.accessToken);
													localStorage.setItem("refreshToken", response2.data.token.refreshToken);
										
													homeService.updateNote(updateData).then(function(resp){
														console.log(resp.data);
														$scope.notesList=resp.data.list.reverse();
													})
											}
											else
											{
													console.log(response2.data);
													$state.go('login');
											}
										})
								}
								else 
								{
									console.log(response1.data);
									return;
								}
								
							
							});
						
					}
				
			
				},
				controllerAs:"$ctrl"
			})
		}
	
	
	
	//..........logout.............//
	
	
	$scope.logout=function(){
		console.log("in logout");
		
		homeService.logout().then(function(response){
			
			if(response.data.status == 1)
			{
				console.log("logout success");
				$state.go("login");
			}	
		})
	}
	
	
	
	//...........show notes initialy...........//
	
	
	$scope.showNotes=function(){
		homeService.getNotes().then(function(resp){
			console.log(resp);
			console.log("list display after login");
			
			
			$scope.notesList=resp.data.reverse();
			$scope.listBtn=true;
			$scope.gridBtn=false;
			
			$scope.gridview=true;
			$scope.listview=false;
		})
	}
	$scope.showNotes();
})






//////////.......................Home Service.....................///////////////////




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
	
	
	
		this.getNotes=function(){
			console.log("in getNotes service");
			return $http({
						url:"getNotes",
						method:"get",
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})
		}
	
	
	
		this.deleteNote=function(tid){
			console.log("in deletenote service."+tid);
				return $http({
						url:"deleteNote/"+tid,
						method:"post",
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})
		}
	
	
	
		this.logout=function(){
			console.log("in service logout");
			return $http({
						url:"logout",
						method:"get",
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})		
		}
	
	
		this.updateNote=function(updateData){
			console.log(updateData);
		
			return $http({
						url:"updateNote",
						method:"post",
						data:updateData,
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})		
		}
	
	
})


