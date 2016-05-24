var user=angular.module('userApp', ['ngAnimate','ngRoute'])
		.config(function ($routeProvider, $locationProvider) {
		    // configure the routing rules here
		    $routeProvider.when('/jsp/main/:id', {
		        controller: 'usersController'
		    });
		   
		    // enable HTML5mode to disable hashbang urls
		    $locationProvider.html5Mode(true);
		});




var blogspot=angular.module('blogspotApp',['ngRoute']);
/*
angular.module('ngApp', ['ngRoute'])
.config(function ($routeProvider, $locationProvider) {
    // configure the routing rules here
    $routeProvider.when('/jsp/main.jsp?:id', {
        controller: 'PagesCtrl'
    });

    // enable HTML5mode to disable hashbang urls
    $locationProvider.html5Mode(true);
})
.controller('PagesCtrl', function ($routeParams) {
    console.log($routeParams.id, $routeParams.type);
});*/