user.controller('usersController',function($scope,$http){
	
	
	var urlBase=window.location.origin;
	$scope.selected = {};
	$scope.toggle=true;
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.commonDetailsError=false;
	$scope.commonDetailsErrorMsg="";
	
	//get one user details
	$scope.getOneUser = function (userId) {
		$http.get(urlBase+'/user/'+userId)
		.success(function(data){
			$scope.commonDetailsError=false;
			$scope.user = data;
		})
		.error(function(data, status) {
			$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg=" Error occured while fetching one user details !!!";
		});
	};
	
	
	//user filter for likes  
	$scope.getLike = function (userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/like')
		.success(function(data){
			$scope.commonDetailsError=false;
			$scope.blogs = data;
		})
		.error(function(data, status) {
			$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg=" Error occured while fetching results based on like !!!";
		});;
	};

	//user filter for dislikes 
	$scope.getDislike = function (userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/dislike')
		.success(function(data){
		$scope.commonDetailsError=false;
		 $scope.blogs = data;
		})
		.error(function(data, status) {
			$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg=" Error occured while fetching results based on dislike !!!";
		});
	};
	
	
	//create a new user 
	$scope.addUser = function () {		
		$http.post(urlBase + '/user/',$scope.user)
			.success(function(data) {
		 		$scope.user={};
		 		$scope.toggle='!toggle';
		 		$scope.commonDetailsError=false;
		    })
		    .error(function(data, status) {
		    	$scope.commonDetailsError=true;
		    	if(status == 409){ $scope.commonDetailsErrorMsg=" User with email already exists !!! Please enter a different EMAIL ID to continue.";}
		    	else  $scope.commonDetailsErrorMsg=" Error occured while creating user !!!";
		    });
		};
	
	//edit a user and update it
	$scope.updateUser = function () {	
		$http.post(urlBase + '/user/',$scope.user)
			.success( function (data) {
		 		$scope.user=data;
		 		$scope.selected={};
		 		$scope.commonDetailsError=false;
		    })
		    .error(function (data, status) {
		    	$scope.commonDetailsError=true;		    	
		    	$scope.commonDetailsErrorMsg=" Error occured while updating user !!!";	
			});
		};	
		
		
	//user filter for getting records based on month 
	$scope.getByMonth=function (userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/month')
			.then( function successCallback(data){
				$scope.users = data;
				$scope.commonDetailsError=false;
			}, function errorCallback(data, status) {
				$scope.commonDetailsError=true;
				$scope.commonDetailsErrorMsg=" Error occured while fetching results based on month !!!";
			});
		};
		
	
	//user filter for getting records based on year 
	$scope.getByYear=function (userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/year')
		.success( function (data){
			$scope.users = data;
			$scope.commonDetailsError=false;
		})
		.error(function (data, status) {
			$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg=" Error occured while fetching results based on year !!!";
		});
	};
	
	//user login function
	$scope.login = function () {
		$scope.loginError=false;
		$scope.msg="";
		$http.post(urlBase + '/auth/login/',$scope.user)
	 	.success(function (data) {
	 		$scope.user = data;	 		
	 		$scope.loginError=false;
	 		//set the cookie value 
	 		var now = new Date();
	 		var time = now.getTime();
	 		time += 3600 * 1000;
	 		now.setTime(time);
	 		
	 		document.cookie="id="+$scope.user.id+";expires="+now.toGMTString()+";path=/";
	 		document.cookie="name="+$scope.user.name+";expires="+now.toGMTString()+";path=/";
	 	    document.cookie="email="+encodeURIComponent($scope.user.email)+";expires="+now.toGMTString()+";path=/";
	 	   
	 	    window.location="/profile/"+$scope.user.id;
	 	})
	 	.error(function (data, status){
			$scope.loginError=true;
			if(status == 404){	$scope.msg =" User not found !!!"; }
			
	 		if(status == 401){ 	$scope.msg =" Invalid email or password. "; }
	 		
		});
	};
	
	//user logout function
	$scope.logout = function () {
		$http.post(urlBase + '/auth/logout/',$scope.user)
	 	.success( function (data) {
	 		//$scope.blog = {}; 
	 		//delete a cookie on logout 	 		
	 		$scope.commonDetailsError=false;
	 		document.cookie="id=;expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	 		document.cookie = "name=; expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	        document.cookie = "email=;expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	 		window.location="/";
	 		
	 	})
	 	.error(function (data, status) {
	 		$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg=" Error occured while logout !!!";
		});
	};
		
	//blog related operations 
	
	//fetch a single users alll blogs 
	$scope.getOneUsersAllBlogs =function (userId) {
		$http.get(urlBase + '/user/'+userId+'/blogpost')
	 	.success(function (data) {
	 		$scope.commonDetailsError=false;
	 		$scope.blogs = data; 	 		
	 	}).
	 	error(function (data, status) {
	 		$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg=" Error occured while getting user details !!!";
		});
	};	
	
	//create a blog and switch to view all logs of the user 
	$scope.createBlog = function () {
	
		$scope.blog.userId=$scope.user.id;
		$scope.blog.blogLikes=0;
		$scope.blog.blogDislikes=0;
		
		$http.post(urlBase +'/blogpost',$scope.blog)
	 	.then(  function successCallback(data,status) {
	 		$scope.blog = {}; 
	 		$scope.commonDetailsError=false;
	 		alert("Goto View blogs section for viewing the latest  blog. "+status);
	 	}
	 	, function errorCallback(data, status) {	 		
	 		$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg="Error occured while creating a blog !!!";	 		
		});
	};	
	
	//click to enable or disable a blog
	$scope.isActiveBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/isActive')
	 	.success( function (data) {
	 		$scope.commonDetailsError=false;
	 		for (var i=0; i < $scope.blogs.length; i++) {
	 	        if ($scope.blogs[i].blogId === data.blogId) {
	 	        	$scope.blogs[i].publishTime=data.publishTime;
	 	        	$scope.blogs[i].draft=data.draft;
	 	        	$scope.blogs[i].isActive=data.isActive;
	 	        }
	 	    } 	 		
	 	})
	 	.error(function (data, status) {
	 		$scope.commonDetailsError=true;
			$scope.commonDetailsErrorMsg="Error occured while updating blog !!!";
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
	
	 $scope.init = function(id){
			$scope.getOneUser(id);
			$scope.getOneUsersAllBlogs(id);
			
		}
});


