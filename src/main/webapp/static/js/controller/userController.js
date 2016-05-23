user.controller('usersController',function($scope,$http){
	
	
	var urlBase=window.location.origin;
	
	$scope.toggle=true;
	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	
	$scope.user=null;
	
	
	//get one user details
	$scope.getOneUser = function getOneUser(userId) {
		$http.get(urlBase+'user'+userId)
		.success(function(data){
			 $scope.users = data;
		})
	};
	
	
	//user filter for likes  
	$scope.getLike = function getLike(userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/like')
		.success(function(data){
		 $scope.users = data;
		})
	};

	//user filter for dislikes 
	$scope.getDislike = function getDislike(userId) {
		$http.get(urlBase+'/user/'+userId+'/blogpost/dislike')
		.success(function(data){
		 $scope.users = data;
		})
	};
	
	
	//fetch the like from users on a userId 
	$scope.getIsActive=function getIsActive(userId){	
		$http.get(urlBase+'/user/'+userId+'/blogpost/isActive')
		.success(function(data){
			 $scope.users = data;
		})
	};
	
	
	//create a new user 
	$scope.addUser = function addUser() {
		$http.post(urlBase + '/user/',$scope.user)
			.success(function(data) {
		 		$scope.users=user;
		 		$scope.user={};
		 		$scope.toggle='!toggle';	
		    })
		};
	
		
	//user filter for getting records based on month 
	$scope.getByMonth=function getByMonth(userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/month')
		.success(function(data){
		 $scope.users = data;
			})
		};
		
	
	//user filter for getting records based on year 
	$scope.getByYear=function getByYear(userId){
		$http.get(urlBase+'/user/'+userId+'/blogpost/year')
		.success(function(data){
		 $scope.users = data;
	})
	};
		
});
