user.controller('usersController',function($scope,$http,$routeParams){
	
	
	var urlBase=window.location.origin;
	
	$scope.toggle=true;
	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.user=null;
	
	console.log($routeParams+":::"+$routeParams.value);
	console.log(JSON.stringify($routeParams));
	
	
	
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
	
	$scope.login = function login() {
		$http.post(urlBase + '/auth/login/',$scope.user)
	 	.success(function(data) {
	 		$scope.users = data;	 		
	 		window.location="/jsp/main.jsp?"+$scope.users.id;
	 	});
	};
	
	$scope.logout = function logout() {
		$http.post(urlBase + '/auth/logout/',$scope.user)
	 	.success(function(data) {
	 		$scope.blog = data; 
	 		window.location="/jsp/index.jsp";
	 	});
	};
		
	//blog related operations 
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
	
	
	//code for inline editing using angular js 
	//editing inline
	$scope.getTemplate = function (obj) {
	 if (obj.id === $scope.selected.id){
		 	return 'edit';
		 }
		  else return 'display';	};
		
	$scope.editUser = function (obj) {
			 $scope.selected = angular.copy(obj);
	 };
	 
	 $scope.reset = function () {
		 $scope.selected = {};
	 };
});
