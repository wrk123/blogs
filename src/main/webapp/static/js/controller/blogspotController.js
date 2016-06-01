blogspot.controller('blogController',function($scope,$http){
	
	var urlBase=window.location.origin;	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	$scope.blogDetailsError=false;
	$scope.blogDetailsErrorMsg="";
	
	
	
	//get all the blogs from the database 
	$http.get(urlBase+'/home')
	.success(function(data){
		 $scope.blog = data;
		 $scope.blogDetailsError=false;
	})
	.error(function(data, status) {
		$scope.blogDetailsError=true;
		$scope.blogDetailsErrorMsg=" Error occured while fetching blogs !!! ";
	});
	
	
	//fetches details of a single blog 
	$scope.fetchOneBlog = function (id) {
		$http.get(urlBase+'/blog/'+id)
		.success(function(data){
			$scope.blogDetailsError=false;
			$scope.blogs = data;
		})
		.error(function(data, status) {
			$scope.blogDetailsError=false;
			$scope.blogDetailsErrorMsg=" Error occured while fetching one blog !!! ";
		});
	};
	

	//get one user details
	$scope.getOneUser = function (userId) {
		$http.get(urlBase+'/user/'+userId)
		.success(function(data){
			$scope.blogDetailsError=false;
			 $scope.user = data;
		})
		.error(function(data, status) {
			$scope.blogDetailsError=true;
			$scope.blogDetailsErrorMsg=" Error occured while fecthing one user details  !!! ";
		});
	};
	
	
	//user logout function
	$scope.logout = function(email) {
				
		$scope.user={"email":email};
		$http.post(urlBase + '/auth/logout/',$scope.user)
	 	.success(function(data) {
	 		//$scope.blog = {}; 
	 		//delete a cookie on logout 	 		
	 		document.cookie="id=;expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	 		document.cookie = "name=; expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	        document.cookie = "email=;expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	        $scope.blogDetailsError=false;
	 		window.location="/";
	 		
	 	})
	 	.error(function(data, status) {
	 		$scope.blogDetailsError=true;
	 		$scope.blogDetailsErrorMsg=" Error occured while logout !!!";
		});
	};
	
	
	//click to comment on a blog 
	$scope.commentOnBlog = function(id,blogId) {		
		$http.post(urlBase + '/user/'+id+'/blogpost/'+blogId+'/comment/',$scope.review)
	 	.success(function(data) {	 		
	 		$scope.blogs = data; 
	 		$scope.review={};
	 		$scope.blogDetailsError=false;
	 	})
	 	.error(function(data, status) {
	 		$scope.blogDetailsError=true;
	 		if(status==412)
	 			{  $scope.blogDetailsErrorMsg=" You don't have sufficient credit to proceed !!!";  }
 	    	else
 	    		$scope.blogDetailsErrorMsg=" Error occured  !!!";
		});
	};

	//click to like a blog
	$scope.likeBlog = function(id,blogId) {		
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/like')
	 	     .success(function(data) {
	 	    	 $scope.blogs = data;
	 	    	$scope.blogDetailsError=false;
	 	     })
	 	    .error(function(data, status) {
	 	    	$scope.blogDetailsError=true;
	 	    	
	 	    	if(status==412)
	 	    	{  $scope.blogDetailsErrorMsg=" You don't have sufficient credit to proceed !!!";  }
	 	    	else $scope.blogDetailsErrorMsg=" Error occured !!!";
			});
	};
	
	//click to  dislike a blog
	$scope.disLikeBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/dislike')
	 	.success(function(data) {
	 		$scope.blogs = data; 
	 		$scope.blogDetailsError=false;
	 	})
	 	.error(function(data, status) {
	 		$scope.blogDetailsError=true;
	 		if(status==412)
	 			{  $scope.blogDetailsErrorMsg=" You don't have sufficient credit to proceed !!!";  }
 	    	else
	 		$scope.blogDetailsErrorMsg=" Error occured !!!";
		});
	};
	
	//for fetching blog when asked for single page article
	$scope.init = function(id){
		$scope.fetchOneBlog(id);	
	}
	
});