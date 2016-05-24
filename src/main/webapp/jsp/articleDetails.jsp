<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Blog articles </title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="../static/css/style.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> 
		<link href="../static/css/blog.css" rel="stylesheet" />  
		<script type="text/javascript" src="../static/js/angular.js"></script>
		<script type="text/javascript" src="../static/js/angular-animate.js"></script>
		<script type="text/javascript" src="../static/js/app.js"></script>
		<script type="text/javascript" src="../static/js/controller/blogspotController.js"></script>
		<script type="text/javascript" src="../static/js/controller/userController.js"></script>
	</head>
	<body>
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
						<li><a href="/jsp/index.jsp">Home</a></li>
						<li><a href="#">Profile</a></li>
						<li><a href="#">View blogs</a></li>
					</ul>
				</nav>
			</div>
		</header>
		
		
	</body>
</html>