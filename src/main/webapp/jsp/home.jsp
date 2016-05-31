<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="blogspotApp">
	<head>
		<title>Blogs Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="../static/css/style.css" rel="stylesheet" type="text/css"/>
		<!-- <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/sandstone/bootstrap.min.css"> -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> 
		<link href="../static/css/blog.css" rel="stylesheet" />  
		<script type="text/javascript" src="../static/js/angular.js"></script>
		<script type="text/javascript" src="../static/js/angular-animate.js"></script>
		<script type="text/javascript" src="../static/js/app.js"></script>
		<script type="text/javascript" src="../static/js/controller/blogspotController.js"></script>
		<script type="text/javascript" src="../static/js/controller/userController.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  		<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-route.js"></script>
	</head>
	<%
	 //  Cookie cookie = null;
	   Cookie[] cookies = null;
	   // Get an array of Cookies associated with this domain
	   cookies = request.getCookies();
	   String name="",id="",email="";
	   
	%>
	
	<body ng-controller="blogController">
		<!-- Header -->
		<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">			
			<div class="container">
				<div class="navbar-header">
					<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="/" class="navbar-brand">Bloggers Point</a>
				</div>
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search blog by name" ng-model="searchBlog" />     
						</div>
					</form>				    
					<ul class="nav navbar-nav">
						<li class="active"><a href="/">Home</a></li>
						<li><a href="#">Profile</a></li>
						<li><a href="#">View blogs</a></li>
					</ul>
				</nav>
			</div>
		</header>		
		<!--  -->
		<div class="container">
		<%	if(cookies.length>1){
			    id=cookies[1].getValue();
			 	name=cookies[2].getValue();
				email=cookies[3].getValue();
				if(!cookies[1].getValue().isEmpty())
				{%>
			<!-- add Logout -->
			<form class="navbar-form navbar-right">
				<div class="form-group">
					<h4> Welcome <%=name%> ! &nbsp; &nbsp;<a  ng-click='logout()'><i class="fa fa-sign-out fa-lg" aria-hidden="true" ></i></a></h4>      
				</div>
			</form>
			<%}}%>
			<br/><br/>
			<div class="row">			
				<div class="col-md-8">
					<h1>Latest Posts</h1>
					<article ng-repeat="blogs in blog | filter:searchBlog">
						<a href="/article/{{ blogs.blogId }}"></a><h3>{{ blogs.blogTitle}}</h3></a>
				        <div class="row">
				          	<div class="col-sm-6 col-md-6">author -
				          	<em>	{{ blogs.user.name }} </em>
				          	</div>
				          	<div class="col-sm-6 col-md-6">
				          		<a href="/article/{{ blogs.blogId }}"><span class="glyphicon glyphicon-pencil"></span></a>
				          		&nbsp;&nbsp;  	{{ blogs.review.length }} Comments	          		
				          		&nbsp;&nbsp;<span class="glyphicon glyphicon-time"></span>&nbsp;&nbsp;{{ blogs.creationTime | date:'medium'}}			          		
				          	</div>
				          </div>
				          <hr>
				          <br/>
				          <p>{{ blogs.blogContent}}</p>
				          <hr>
					</article>
				</div>
				<div class="col-md-4">
					<!-- Latest Posts -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Latest Posts</h4>
						</div>
						<ul class="list-group" ng-repeat="blogs in blog | limitTo:5 ">
							<li class="list-group-item"><a href="/article/{{ blogs.blogId }}">{{blogs.blogTitle}}</li>
						</ul>
					</div>			
				</div>				
			</div>
		</div>
		<script style="text/javascript">
			var id=<%=id%>;
			var email=<%=email%>;
		</script>
	</body>
</html>