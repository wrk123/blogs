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
	
	//for fetching blog when asked for single page article
	$scope.init = function(id){
		$scope.fetchOneBlog(id);
	}
	
});