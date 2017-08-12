myApp.controller( 'homeCtrl',function($scope, $state,$uibModal, homeService){
	
		$scope.inputNote=function(){
			console.log("shownote");
		
			$scope.shownote=true;
		}
	
		$scope.shownotes=true;
		$scope.takenote=true;
		$scope.showtrash=false;
		$scope.showarchive=false;
		
		
		//.....................show grid view.................//
		
		
		$scope.gridView=function(){
			
			$scope.listBtn=true;
			$scope.gridBtn=false;
			
			$scope.col2="col-lg-2";
			$scope.showdiv="col-lg-4 col-md-6 col-sm-12 col-xs-12 item";
			
			localStorage.setItem("view","grid");
		}
		
		
		//.....................show list view.................//
		
		
		$scope.listView=function(){
			
			$scope.listBtn=false;
			$scope.gridBtn=true;
			
			$scope.col2="";
			$scope.showdiv="col-lg-8 item";
			localStorage.setItem("view","list");
		}
		
		
		if(localStorage.view == "list")
		{
			$scope.listView();
		}
		else
		{
			$scope.gridView();
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
		
		if($scope.title!="" && $scope.description!="")
		{
			
		noteData.title=$scope.title;
		noteData.description=$scope.description;
		noteData.color=$scope.putcolor;
		
		$scope.title="";
		$scope.description="";
		$scope.putcolor="";
		
		
		var httpnote=homeService.addNote(noteData);
		
		httpnote.then(function(response1)
		{
			console.log(response1);
				if(response1.data.status == 1)
				{
						$scope.notesList=response1.data.list.reverse();
						console.log("note added");
						$state.reload();
						
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
										$state.reload();
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
						/*$state.go('login');*/
						return;
				}
			
			
			});
			}
			/*else
			{
				console.log("note is null");
				return;
		
			}*/
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
					$state.reload();
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
									$state.reload();
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
					this.notereminder=data.reminder;
					this.archivenote=data.isArchive;
					this.trashnote=data.isTrash;
				
					
		//..........................delete reminder.................//
					
					
					this.deleteReminder=function(){
						
						var noteData={};
						
						noteData.tid=this.noteId;
						noteData.title=this.updateTitle;
						noteData.description=this.updateDescription;
						noteData.user=this.user;
						noteData.creation_date=this.createdDate;
						noteData.color=this.notecolor;
						noteData.isArchive=this.archivenote;
						noteData.isTrash=this.trashnote;
						
						noteData.reminder=null;
						
						$scope.cancelReminder(noteData);
						
						this.notereminder=null;
					}
					
					
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
						updateData.reminder=this.notereminder;
						updateData.isArchive=this.archivenote;
						updateData.isTrash=this.trashnote;
						//updateData.edited_date=this.editedDate;
						
						$scope.update(updateData);
						
					}
				
			
				},
				controllerAs:"$ctrl"
			})
		}
	
	
	
	//.................................change color...........................................// 
	
	
	$scope.changeColor=function(notedata,color)
	{
		
		notedata.color=color;
		
		$scope.update(notedata);
		
		
	}
	
	
	
	
	
	//...............................set reminder....................................//
	
	
	$scope.setReminder=function(notedata,day){
		console.log("in setReminder");
		
		
		var today=new Date();
		
		var httpobj;
		
		if(day == "Today")
		{
			today.setHours(20,00,00);
			
			notedata.reminder=today;
			
			console.log(today);
			
			httpobj=homeService.updateNote(notedata);
			
		}
		else if(day == "Tomorrow")
		{
			var tomorrow=new Date();
			tomorrow.setDate(today.getDate() + 1);
			tomorrow.setHours(08,00,00);
			console.log(tomorrow);
			
			notedata.reminder=tomorrow;
			
			httpobj=homeService.updateNote(notedata);
			
		}
		/*else if(day == "Next-week")*/
		else
		{
			var nextweek=new Date();
			nextweek.setDate(today.getDate() + 7);
			nextweek.setHours(08,00,00);
			console.log(nextweek);
			
			notedata.reminder=nextweek;
			
			httpobj=homeService.updateNote(notedata);
			
		}
		
		
		
		
		httpobj.then(function(response1)
		{
			console.log(response1);
			
				if(response1.data.status == 1)
				{
						$scope.notesList=response1.data.list.reverse();
						console.log("reminder set");
						$state.reload();
	
				}
				else if(response1.data.status == -4)
				{
						console.log("access token expired");
				
						homeService.getNewAccessToken().then(function(response2){
							
							if(response2.data.status == 4)
							{
									localStorage.setItem("accessToken", response2.data.token.accessToken);
									localStorage.setItem("refreshToken", response2.data.token.refreshToken);
						
									homeService.updateNote(notedata).then(function(resp){
										console.log(resp.data);
										$scope.notesList=resp.data.list.reverse();
										$state.reload();
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
	
	
	//...............................cancel reminder...........................//

	
	$scope.cancelReminder=function(notedata){
		
		console.log("in cancelReminder");
		
		notedata.reminder=null;
		
		$scope.update(notedata);
		
		
	}
	
	
	
	
	//...............................do archive.............................//
	
	
	$scope.doArchive=function(notedata){
		
		console.log("in doArchive");
		
		notedata.isArchive="true";
		notedata.isPinned="false";
		
		$scope.update(notedata);
		
	}
	
	
	
//...............................do un-archive.............................//
	
	
	$scope.doUnArchive=function(notedata){
		console.log("in doArchive");
		
		notedata.isArchive="false";
		
		$scope.update(notedata);
		
	}
	
	
	
	
	//.................................. do trash.............................//
	
	
	
	$scope.doTrash=function(notedata){
		
		console.log("in doTrash");
		
		notedata.isTrash="true";
		notedata.reminder=null;
		
		$scope.update(notedata);
		
	}
	
	
	
//.................................. restore note from trash.............................//
	
	
	
	$scope.restoreNote=function(notedata){
		
		console.log("in doTrash");
		
		notedata.isTrash="false";
		
		$scope.update(notedata);
		
		
	}
	
	
	
	//..............................pinned..............................//
	
	
	
	$scope.doPin=function(notedata){
		
		console.log("in doPinned");
		
		notedata.isPinned="true";
		
		$scope.update(notedata);
	
	}
	
	
	
	
	//..............................un-pinned..............................//
	
	
	
	$scope.unPin=function(notedata){
		
		console.log("in unPinned");
		
		notedata.isPinned="false";
		
		$scope.update(notedata);
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
	
	
//..................................utilty update method..............................//	
	
	$scope.update=function(updateData)
	{
		console.log("in update");
		
		homeService.updateNote(updateData)
		.then(function(response1)
		{
			console.log(response1);
			
				if(response1.data.status == 1)
				{
						$scope.notesList=response1.data.list.reverse();
						console.log("note updated");
						$state.reload();
	
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
										$state.reload();
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


