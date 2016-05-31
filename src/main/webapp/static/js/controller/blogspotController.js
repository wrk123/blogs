blogspot.controller('blogController',function($scope,$http){
	
	var urlBase=window.location.origin;
		
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	//get all the blogs from the database 
	$http.get(urlBase+'/home')
	.success(function(data){
		 $scope.blog = data;
	})
	.error(function(data, status) {
		console.error('line 12 b :', status, data);
	});
	
	
	//fetches details of a single blog 
	$scope.fetchOneBlog = function (id) {
		$http.get(urlBase+'/blog/'+id)
		.success(function(data){
			$scope.blogs = data;
		})
		.error(function(data, status) {
			console.error('line 21 b :', status, data);
		});
	};
	

	//get one user details
	$scope.getOneUser = function (userId) {
		$http.get(urlBase+'/user/'+userId)
		.success(function(data){
			 $scope.user = data;
		})
		.error(function(data, status) {
			console.error(' line 36 error', status, data);
		});
	};
	
	
	//user logout function
	$scope.logout = function (email) {
		
		console.log(email);
		$scope.user="";
		$scope.user.email=email;
		console.log($scope.user.email);
		
		$http.post(urlBase + '/auth/logout/',$scope.user)
	 	.success(function(data) {
	 		//$scope.blog = {}; 
	 		//delete a cookie on logout 	 		
	 		document.cookie="id=;expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	 		document.cookie = "name=; expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	        document.cookie = "email=;expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
	 		
	 		window.location="/";
	 		
	 	})
	 	.error(function(data, status) {
			console.error(' line 115 error', status, data);
		});
	};
	
	
	//click to comment on a blog 
	$scope.commentOnBlog = function(id,blogId) {		
		$http.post(urlBase + '/user/'+id+'/blogpost/'+blogId+'/comment/',$scope.review)
	 	.success(function(data) {	 		
	 		$scope.blogs = data; 
	 		$scope.review={};
	 	})
	 	.error(function(data, status) {
			console.error(' line 155 error', status, data);
		});
	};

	//click to like a blog
	$scope.likeBlog = function(id,blogId) {		
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/like')
	 	     .success(function(data) {
	 	    	 $scope.blogs = data; 	 	    	 
	 	     })
	 	    .error(function(data, status) {
				console.error(' line 165 error', status, data);
			});
	};
	
	//click to  dislike a blog
	$scope.disLikeBlog = function(id,blogId) {
		$http.delete(urlBase + '/user/'+id+'/blogpost/'+blogId+'/dislike')
	 	.success(function(data) {
	 		$scope.blogs = data; 
	 	})
	 	.error(function(data, status) {
			console.error(' line 176 error', status, data);
		});
	};
	
	
	
	//for fetching blog when asked for single page article
	$scope.init = function(id){
		$scope.fetchOneBlog(id);	
	}
	
});