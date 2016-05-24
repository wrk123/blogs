blogspot.controller('blogController',function($scope,$http){
	
	var urlBase=window.location.origin;
		
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	$http.get(urlBase+'/home')
	.success(function(data){
		 $scope.blog = data;
	});

	
	$scope.addBlog = function addBlog() {
		$http.post(urlBase + '/blogpost/',$scope.blog)
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(data, status);
		
	};	
		
	$scope.commentOnBlog = function commentOnBlog() {
		$http.post(urlBase + '/user/'+$scope.user.id+'/blogpost/'+$scope.blog.blogId+'/comment/'+review)
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(data, status);
	};

	$scope.likeBlog = function likeBlog() {
		$http.delete(urlBase + '/user/'+$scope.user.id+'/blogpost/'+$scope.blog.blogId+'/like')
	 	     .success(function(data) {
	 	    	 $scope.blog = data; 
	 	     })
	 	     .error(data, status);
	};
	
	
	$scope.disLikeBlog = function disLikeBlog() {
		$http.delete(urlBase + '/user/'+$scope.user.id+'/blogpost/'+$scope.blog.blogId+'/dislike')
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	})
	 	.error(data, status);
	};
	
	$scope.isActiveBlog = function isActiveBlog() {
		$http.delete(urlBase + '/user/'+$scope.user.id+'/blogpost/'+$scope.blog.blogId+'/isActive')
	 	.success(function(data) {
	 		$scope.blog = data; 
	 	});
	};
	
});