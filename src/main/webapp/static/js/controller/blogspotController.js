blogspot.controller('blogController',function($scope,$http){
	
	var urlBase=window.location.origin;
		
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	$http.get(urlBase+'/home')
	.success(function(data){
		 $scope.blog = data;
	})
	.error(function(data, status) {
		console.error('Repos error', status, data);
	});
	
	
	$scope.fetchOneBlog = function (id) {
		$http.get(urlBase+'/blog/'+id)
		.success(function(data){
			$scope.blogs = data;
		})
		.error(function(data, status) {
			console.error('Repos error', status, data);
		});
	};
	
	$scope.init = function(id){
		$scope.fetchOneBlog(id);
	}
	
});