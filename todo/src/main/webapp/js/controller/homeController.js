myApp.controller( 'homeCtrl',function($scope, $state,$uibModal, homeService,fileReader){
	
		$scope.inputNote=function(){
			console.log("shownote");
		
			$scope.shownote=true;
		}
	
		$scope.shownotes=true;
		$scope.showpinned=true;
		$scope.takenote=true;
		$scope.showtrash=false;
		$scope.showarchive=false;
		
		$scope.headercolor={"background-color":"#f9b902", "border":"none"};
		$scope.headername="Fundoo Notes";
		
		//.....................show grid view.................//
		
		
		$scope.gridView=function(){
			
			$scope.listBtn=true;
			$scope.gridBtn=false;
			
			$scope.pinned1={
					'margin-top': '30px',
					'color':'rgba(0,0,0,.54)',
					'font-weight': 'bold',
					'font-family': "'Roboto',arial,sans-serif",
					'padding-left': '15px'
			}
			
			$scope.others={
					'margin-top': '45px',
					'color':'rgba(0,0,0,.54)',
					'font-weight': 'bold',
					'font-family': "'Roboto',arial,sans-serif",
					'padding-left': '15px'
			}
			
			$scope.col2="col-lg-2";
			$scope.showdiv="col-lg-4 col-md-6 col-sm-12 col-xs-12 item";
			
			localStorage.setItem("view","grid");
		}
		
		
		//.....................show list view.................//
		
		
		$scope.listView=function(){
			
			$scope.listBtn=false;
			$scope.gridBtn=true;
			
			$scope.pinned1={
					'margin-top': '30px',
					'color':'rgba(0,0,0,.54)',
					'font-weight': 'bold',
					'font-family': "'Roboto',arial,sans-serif",
					'padding-left': '142px'
			}
			
			$scope.others={
					'margin-top': '45px',
					'color':'rgba(0,0,0,.54)',
					'font-weight': 'bold',
					'font-family': "'Roboto',arial,sans-serif",
					'padding-left': '142px'
			}
			
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
		
		if(($scope.title!="" && $scope.description =="") || ($scope.title =="" && $scope.description !="") || ($scope.title !="" && $scope.description !=""))
		{
			
		noteData.title=$scope.title;
		noteData.description=$scope.description;
		noteData.color=$scope.putcolor;
		noteData.image=$scope.imageSrc;
		
		console.log("img: "+noteData.image);
		
		$scope.title="";
		$scope.description="";
		$scope.putcolor="";
		$scope.imageSrc=null;
		
		
		var httpnote=homeService.addNote(noteData);
		
		httpnote.then(function(response1)
		{
			console.log(response1);
				if(response1.data.status == 1)
				{
					//	$scope.notesList=response1.data.list.reverse();
					$scope.showNotes();	
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
										//$scope.notesList=resp.data.list.reverse();
										$scope.showNotes();
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
					//$scope.notesList=response1.data.list.reverse();
					$scope.showNotes();
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
									
									//$scope.notesList=resp.data.list.reverse();
									$scope.showNotes();
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
		
		
		})
		
	}
	
	
	//......................popup note.........................//
	
	
	
	$scope.openPopup=function(data){
			console.log("in popup ");
		
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
					this.pinnednote=data.isPinned;
					this.imagenote=data.image;
					this.scraper=data.webscraper;
					
					
		//..........................add image.................//
					
					this.addImg=function(){
						console.log("in addImg");
						
						document.getElementById("imgid").click();
					}
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
						noteData.isPinned=this.pinnednote;
						
						noteData.reminder=null;
						
						$scope.cancelReminder(noteData);
						
						this.notereminder=null;
					}
		
		//...........................Remove Scraper...................//
					
					this.removeScraper=function(scraper){
						
						console.log("in removeScraper "+scraper.id);
						
						var httpresp=homeService.deleteScraper(scraper);
						
						httpresp.then(function(response1)
								{
									console.log(response1);
										if(response1.data.status == 1)
										{
												//$scope.notesList=response1.data.list.reverse();
												$scope.showNotes();
												console.log("delete scraper success");
												
												
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
																console.log("delete scraper success");
																//$scope.notesList=resp.data.list.reverse();
																$scope.showNotes();
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
						updateData.isPinned=this.pinnednote;
						updateData.image=this.imagenote;
						//updateData.edited_date=this.editedDate;
						
						$scope.update(updateData);
						
					}
				
			
				},
				controllerAs:"$ctrl"
			})
		}
	
	
	
	//.............................Collaberator popup...........................//
	
	$scope.collaboratorPopup=function(data){
		console.log("in collaboratorpopup");
		
		var modal=$uibModal.open({
			templateUrl:"templates/collaborator.html",
			controller:function($uibModalInstance){
				console.log("popup open");
				var $colCtrl=this;
				
				this.owner=data.user.email;
				
			
			//...................add Collaborator......................//	
				
				this.addCollaborator=function(){
					console.log("in addCollaborator");
					console.log(this.emailtoshare);
					
					$uibModalInstance.dismiss('Save');
					
					if(this.emailtoshare != "")
					{
						var colObj={};
					
						colObj.tid=data.tid;
						colObj.emailToShare=this.emailtoshare;
					
						var httpobj= homeService.addCollaborator(colObj);
					
						httpobj.then(function(response1)
						{
							console.log(response1);
						
							if(response1.data.status == 1)
							{
								//$scope.showNotes();
								//$scope.notesList=response1.data.list.reverse();
								console.log("Note Collaborated successfully");
								
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
												//$scope.notesList=resp.data.list.reverse();
												//$scope.showNotes();
												})
										}
										else
										{
												console.log(response2.data);
												$state.go('login');
										}
									})
							}
							else if(response1.data.status == -2)
							{
								console.log(response1.data);
								return;
							}
						
							else
							{
								console.log(response1.data);
								return;
							}
						
						});
					}
				}
			},
			controllerAs:"$colCtrl"
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
						$scope.showNotes();
						//$scope.notesList=response1.data.list.reverse();
						console.log("reminder set");
						
	
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
										//$scope.notesList=resp.data.list.reverse();
										$scope.showNotes();
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
		notedata.isPinned="false"
		
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
	
	
	//..............................pinned from archive..............................//
	
	
	
	$scope.doPinFromArchive=function(notedata){
		
		console.log("in doPinnedFromArchive");
		
		notedata.isPinned="true";
		notedata.isArchive="false";
		
		$scope.update(notedata);
	
	}
	

	
	//..............................share on facebook......................//
	
	$scope.shareOnFb=function(data){
		console.log("in shareOnFb");
		
		FB.init({
	           appId: '1136987416445864',
	           status: true,
	           xfbml: true,
	           version     : 'v2.7', 
	       }); 
	    
	       FB.ui({
	               method: 'share_open_graph',
	               action_type: 'og.shares',
	               action_properties: JSON.stringify({
	                   object: {
	                       'og:title': data.title,
	                       'og:description': data.description,
	                   }
	               })
	           },
	           // callback
	           function(response) {
	               if (response && !response.error_message) {
	             //      alert('successfully posted. ');
	               } else {
	              //     alert('Something went error.');
	               }
	           }
	           );

	}
	
	
	//............................add image while creating new note.......................//
	
	$scope.addImage=function(){
		console.log("in addImage");
		
		document.getElementById("imgId").click();
	}

	
	
	//...........................add image in created note...........................//
	
	$scope.uploadImage=function(data){
		console.log("in uploadImage");
		
		document.getElementById("imageId").click();
		
		
		data.image=$scope.uploadImg;
		console.log("img "+$scope.uploadImg)
		$scope.update(data);
	}

	
	//..............................make a copy.................//
	
	
	$scope.makeCopy=function(notedata){
			console.log("in makeCopy");
		
			notedata.reminder=null;
			notedata.isArchive='false';
			notedata.isPinned='false';
			
			var httpnote=homeService.addNote(notedata);
			
			httpnote.then(function(response1)
			{
				console.log(response1);
					if(response1.data.status == 1)
					{
							$scope.notesList=response1.data.list.reverse();
							console.log("note of copy success");
							
							
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
							/*$state.go('login');*/
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
				
				$scope.username=response1.data.user.fullname;
				$scope.email=response1.data.user.email;
				
				$scope.showNames($scope.notesList);
				
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
									$scope.showNames($scope.notesList);
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
	
	$scope.showNames=function(notelist)
	{
		var count=0;
			for(var i=0;i < notelist.length-1;i++)
			{
					if(notelist[i].isPinned == 'true')
					{
						count=count+1;
					}
			}
			
			if(count > 0)
			{
				$scope.showname=true;
			}
			else
			{
				$scope.showname=false;
			}
	}
	
	
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
						//$scope.notesList=response1.data.list.reverse();
						$scope.showNotes();
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
										//$scope.notesList=resp.data.list.reverse();
										$scope.showNotes();
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
			console.log("in updateNote Service");
		
			return $http({
						url:"updateNote",
						method:"post",
						data:updateData,
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})		
		}
		
		
		this.deleteScraper=function(scraper){
			console.log("in deleteScraper service ");
			
			return $http({
						url:"deleteScraper",
						method:"post",
						data:scraper,
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})
		}
		
		
		this.addCollaborator=function(colObj){
			
			return $http({
						url:"collaborator",
						method:"post",
						data:colObj,
						headers:{"accessToken":localStorage.getItem("accessToken")}
					})
		}
	
	
})


