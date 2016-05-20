user.controller('userController',function($scope,$http){
	
	//var urlBase=window.location.pathname;
	var urlBase=window.location.origin;
	$scope.toggle=true;
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	//get one user details
	$http.get(urlBase+'user'+$scope.user.userId)
		.success(function(data){
			 $scope.users = data;
		});
	
	
	//user filter for likes  
	$http.get(urlBase+'/user/'+$scope.user.userId+'/blogpost/like')
	.success(function(data){
		 $scope.users = data;
	});

	//user filter for dislikes 
	$http.get(urlBase+'/user/'+$scope.user.userId+'/blogpost/dislike')
	.success(function(data){
		 $scope.users = data;
	});
	
	
	//fetch the like from users on a userId 
	$http.get(urlBase+'/user/'+$scope.user.userId+'/blogpost/isActive')
	.success(function(data){
		 $scope.users = data;
	});
	
	
	//create a new user 
	$scope.addUser = function addUser() {
			$http.post(urlBase + 'user/'+$scope.user)
		 	.success(function(data) {
		 		$scope.users=user;
		    });
		};
		
	//user filter for getting records based on month 
	$http.get(urlBase+'/user/'+$scope.user.userId+'/blogpost/month')
	.success(function(data){
		 $scope.users = data;
	});
		
	
	//user filter for getting records based on year 
	$http.get(urlBase+'/user/'+$scope.user.userId+'/blogpost/year')
	.success(function(data){
		 $scope.users = data;
	});
		
});
	