blogspot.controller('blogController',function($scope,$http){
	
	var urlBase=window.location.origin;
		
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	$http.get(urlBase+'/home')
	.success(function(data){
		 $scope.blog = data;
	});
	
});