myApp.controller( 'homeCtrl',function($scope, $state,$uibModal, homeService){
	
		$scope.inputNote=function(){
			console.log("shownote");
		
			$scope.shownote=true;
		}
	
		
		
		
		//.....................show grid view.................//
		
		
		$scope.gridView=function(){
			
			$scope.listBtn=true;
			$scope.gridBtn=false;
			
			$scope.col2="";
			$scope.showdiv="col-lg-4 col-md-6 col-sm-12 col-xs-12";
		}
		
		
		//.....................show list view.................//
		
		
		$scope.listView=function(){
			
			$scope.listBtn=false;
			$scope.gridBtn=true;
			
			$scope.col2="col-lg-2";
			$scope.showdiv="col-lg-8";
		}
		
		
		
		//...................set color while creating note..........//
		
		
		/*var givecolor;
		
		$scope.putColor=function(colors){
			
			givecolor=colors;
			
			$scope.notecolor={'background-color':givecolor}
		}*/
		
		
		//......................Create new note .................//
		
		$scope.submitNote=function(){
		
			console.log("inside note");
		
			$scope.shownote=false;
		
		var noteData={};
		
		noteData.title=$scope.title;
		noteData.description=$scope.description;
		noteData.color=$scope.putcolor;
		
		$scope.title="";
		$scope.description="";
		$scope.putcolor='';
		
		
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
						console.log(response1.data);
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
	
	
	//......................popup note.........................//
	
	
	
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
					this.editedDate=data.edited_date;
					this.notecolor=data.color;
				
		//.........................update note......................//			
					
					this.updateNote=function(tid){
						
						console.log(tid);
						console.log(this.updateTitle);
						console.log(this.updateDescription);
						console.log("color ",this.notecolor);
						
						$uibModalInstance.dismiss('Done');
						
						var updateData={};
						
						updateData.tid=tid;
						updateData.title=this.updateTitle;
						updateData.description=this.updateDescription;
						updateData.user=this.user;
						updateData.creation_date=this.createdDate;
						updateData.color=this.notecolor;
						//updateData.edited_date=this.editedDate;
						
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
	
	
	
	//.................................change color...........................................// 
	
	
	$scope.changeColor=function(noteData,color)
	{
		
		noteData.color=color;
		
		homeService.updateNote(noteData)
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
	
	
	//...............................logout....................................//
	
	
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
	
	
	
	//.................................show notes initialy................................//
	
	
	$scope.showNotes=function(){
		homeService.getNotes().then(function(response1){
			console.log(response1);
			console.log("list display after login");
			
			if(response1.data.status == 1)
			{
				$scope.notesList=response1.data.list.reverse();
				
				$scope.gridView();
				
				
				/*$scope.listBtn=true;
				$scope.gridBtn=false;
				
				$scope.gridview=true;
				$scope.listview=false;*/
					
			}
			else if(response1.data.status == -4)
			{
					console.log("access token expired");
			
					homeService.getNewAccessToken().then(function(response2){
						
						if(response2.data.status == 4)
						{
								localStorage.setItem("accessToken", response2.data.token.accessToken);
								localStorage.setItem("refreshToken", response2.data.token.refreshToken);
					
								homeService.getNotes().then(function(resp){
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


