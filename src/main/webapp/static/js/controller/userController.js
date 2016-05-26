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
		});
	};
	
	
	//user filter for likes  
	$scope.getLike = function (userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/like')
		.success(function(data){
		 $scope.blogs = data;
		});
	};

	//user filter for dislikes 
	$scope.getDislike = function (userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/dislike')
		.success(function(data){
		 $scope.blogs = data;
		});
	};
	
	
	//create a new user 
	$scope.addUser = function () {
		console.log($scope.user);
		$http.post(urlBase + '/user/',$scope.user)
			.success(function(data) {
		 		$scope.user={};
		 		$scope.toggle='!toggle';	
		    });
		};
	
	//edit a user and update it
	$scope.updateUser = function () {	
		$http.post(urlBase + '/user/',$scope.user)
			.success(function(data) {
		 		$scope.user=data;
		 		$scope.selected={};
		    });
		};	
		
		
	//user filter for getting records based on month 
	$scope.getByMonth=function (userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/month')
			.success(function(data){
				$scope.users = data;
			});
		};
		
	
	//user filter for getting records based on year 
	$scope.getByYear=function (userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/year')
		.success(function(data){
			$scope.users = data;
		});
	};
	
	//user login function
	$scope.login = function () {
		$http.post(urlBase + '/auth/login/',$scope.user)
	 	.success(function(data) {
	 		$scope.user = data;	 		
	 		window.location="/profile/"+$scope.user.id;
	 	});
	};
	
	//user logout function
	$scope.logout = function () {
		$http.post(urlBase + '/auth/logout/',$scope.user)
	 	.success(function(data) {
	 		$scope.blog = {}; 
	 		window.location="/";
	 	});
	};
		
	//blog related operations 
	
	//fetch a single users alll blogs 
	$scope.getOneUsersAllBlogs =function (userId) {
		$http.get(urlBase + '/user/'+userId+'/blogpost')
	 	.success(function(data) {
	 		$scope.blogs = data; 
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
	 		
	 		//set the blog scope to empty and switch to view blogs page  
	 		window.location=urlBase+window.location.pathname+"#/ViewBlogs";
	 	});	 			
	};	
		
	$scope.commentOnBlog = function(id,blogId) {
		$http.post(urlBase + '/user/'+id+'/blogpost/'+blogId+'/comment/'+review)
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(data, status);
	};

	$scope.likeBlog = function() {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/like')
	 	     .success(function(data) {
	 	    	 $scope.blog = data; 
	 	     })
	 	     .error(data, status);
	};
	
	
	$scope.disLikeBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/dislike')
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(data, status);
	};
	
	$scope.isActiveBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/isActive')
	 	.success(function(data) {
	 		for (var i=0; i < $scope.blogs.length; i++) {
	 	        if ($scope.blogs[i].blogId === data.blogId) {
	 	        	$scope.blogs[i].isActive=data.isActive;
	 	        }
	 	    } 	 		
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
