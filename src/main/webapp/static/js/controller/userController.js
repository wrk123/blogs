user.controller('usersController',function($scope,$http){
	
	
	var urlBase=window.location.origin;
	$scope.selected = {};
	$scope.toggle=true;
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	
	
	//get one user details
	$scope.getOneUser = function (userId) {
		$http.get(urlBase+'/user/'+userId)
		.success(function(data){
			 $scope.user = data;
		})
		.error(function(data, status) {
			console.error(' line 18 error', status, data);
		});
	};
	
	
	//user filter for likes  
	$scope.getLike = function (userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/like')
		.success(function(data){
			$scope.blogs = data;
		})
		.error(function(data, status) {
			console.error(' line 30 error', status, data);
		});;
	};

	//user filter for dislikes 
	$scope.getDislike = function (userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/dislike')
		.success(function(data){
		 $scope.blogs = data;
		})
		.error(function(data, status) {
			console.error(' line 41 error', status, data);
		});;
	};
	
	
	//create a new user 
	$scope.addUser = function () {
		console.log($scope.user);
		$http.post(urlBase + '/user/',$scope.user)
			.success(function(data) {
		 		$scope.user={};
		 		$scope.toggle='!toggle';	
		    })
		    .error(function(data, status) {
		    	console.error(' userController line 55 error', status, data);
		    });
		};
	
	//edit a user and update it
	$scope.updateUser = function () {	
		$http.post(urlBase + '/user/',$scope.user)
			.success(function(data) {
		 		$scope.user=data;
		 		$scope.selected={};
		    })
		    .error(function(data, status) {
				console.error(' line 67 error', status, data);
			});
		};	
		
		
	//user filter for getting records based on month 
	$scope.getByMonth=function (userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/month')
			.success(function(data){
				$scope.users = data;
			})
			.error(function(data, status) {
				console.error(' userController line 79 error', status, data);
			});
		};
		
	
	//user filter for getting records based on year 
	$scope.getByYear=function (userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/year')
		.success(function(data){
			$scope.users = data;
		})
		.error(function(data, status) {
			console.error(' line 91 error', status, data);
		});
	};
	
	//user login function
	$scope.login = function () {
		$http.post(urlBase + '/auth/login/',$scope.user)
	 	.success(function(data) {
	 		$scope.user = data;	 		
	 		window.location="/profile/"+$scope.user.id;
	 	})
	 	.error(function(data, status) {
			console.error(' userController line 103 error', status,'data is ', data);
		});
	};
	
	//user logout function
	$scope.logout = function () {
		$http.post(urlBase + '/auth/logout/',$scope.user)
	 	.success(function(data) {
	 		$scope.blog = {}; 
	 		window.location="/";
	 	})
	 	.error(function(data, status) {
			console.error(' line 115 error', status, data);
		});
	};
		
	//blog related operations 
	
	//fetch a single users alll blogs 
	$scope.getOneUsersAllBlogs =function (userId) {
		$http.get(urlBase + '/user/'+userId+'/blogpost')
	 	.success(function(data) {
	 		$scope.blogs = data; 
	 	})
	 	.error(function(data, status) {
			console.error('  line 128 error', status, data);
		});
	};	
	
	//create a blog and switch to view all logs of the user 
	$scope.createBlog = function () {
		
		$scope.blog.userId=$scope.user.id;
		$scope.blog.blogLikes=0;
		$scope.blog.blogDislikes=0;
		
		$http.post(urlBase + '/blogpost',$scope.blog)
	 	.success(function(data) {
	 		$scope.blog = {}; 
	 		alert("Check the View blogs section for updation of the blog. ");
	 	})
	 	.error(function(data, status) {
			console.error('line 145 error', status, data);
		});;	 			
	};	
		
	$scope.commentOnBlog = function(id,blogId) {
		$http.post(urlBase + '/user/'+id+'/blogpost/'+blogId+'/comment/'+review)
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(function(data, status) {
			console.error(' userController line 155 error', status, data);
		});
	};

	$scope.likeBlog = function() {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/like')
	 	     .success(function(data) {
	 	    	 $scope.blog = data; 
	 	     })
	 	    .error(function(data, status) {
				console.error(' userController line 165 error', status, data);
			});
	};
	
	
	$scope.disLikeBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/dislike')
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(function(data, status) {
			console.error(' userController line 176 error', status, data);
		});
	};
	
	$scope.isActiveBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/isActive')
	 	.success(function(data) {
	 		for (var i=0; i < $scope.blogs.length; i++) {
	 	        if ($scope.blogs[i].blogId === data.blogId) {
	 	        	$scope.blogs[i].publishTime=data.publishTime;
	 	        	$scope.blogs[i].isActive=data.isActive;
	 	        }
	 	    } 	 		
	 	})
	 	.error(function(data, status) {
			console.error(' line 191 error', status, data);
		});
	};
	
	
	//code for inline editing using angular js 
	$scope.getTemplate = function (user) {
	 if (user.id === $scope.selected.id){		  
		   return 'edit';
		 }
	   else  
		   return 'display';  	
	 };
	
	$scope.editObj = function (obj) {
			 $scope.selected = angular.copy(obj);
	 };
	 
	 $scope.reset = function () {
		 $scope.selected = {};
	 };
});
