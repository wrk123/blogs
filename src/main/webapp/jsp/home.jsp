<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="blogspotApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>AngularJS Blogs</title>
		<link href="../static/css/style.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/sandstone/bootstrap.min.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> 
		<link href="../static/css/blog.css" rel="stylesheet" />  
		<script type="text/javascript" src="../static/js/angular.js"></script>
		<script type="text/javascript" src="../static/js/angular-animate.js"></script>
		<script type="text/javascript" src="../static/js/app.js"></script>
		<script type="text/javascript" src="../static/js/controller/blogspotcontroller.js"></script>
	</head>
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
					<a href="index.html" class="navbar-brand">Bloggers Point</a>
				</div>
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<form class="navbar-form navbar-right" role="search">
				      <div class="form-group">
				        <input type="text" class="form-control" placeholder="Search">
				      </div>
				      <button type="submit" class="btn btn-default">Submit</button>
				    </form>
					<ul class="nav navbar-nav">
						<li class="active"><a href="index.html">Home</a></li>
						<li><a href="contact.html">Contact</a></li>
						<li><a href="about.html">About</a></li>
					</ul>
				</nav>
			</div>
		</header>
		
		
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<h1>Latest Posts</h1>
					<article ng-repeat="blogs in blog">
						<h3>{{ blogs.blogTitle}}</h3>
				        <div class="row">
				          	<div class="col-sm-6 col-md-6">
				          	</div>
				          	<div class="col-sm-6 col-md-6">
				          		<span class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;  	{{ blogs.review.length }} Comments	          		
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
						<ul class="list-group">
							<li class="list-group-item"><a href="singlepost.html">1. Aries Sun Sign March 21 - April 19</a></li>
							<li class="list-group-item"><a href="singlepost.html">2. Taurus Sun Sign April 20 - May 20</a></li>
							<li class="list-group-item"><a href="singlepost.html">3. Gemini Sun Sign May 21 - June 21</a></li>
						</ul>
					</div>
	
					<!-- Categories -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Categories</h4>
						</div>
						<ul class="list-group">
							<li class="list-group-item"><a href="#">Signs</a></li>
							<li class="list-group-item"><a href="#">Elements</a></li>
							<li class="list-group-item"><a href="#">Planets</a></li>
						</ul>
					</div>
	
					<!-- Tags -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Tags</h4>
						</div>
						<div class="panel-body">
							<ul class="list-inline">
								<li><a href="#">Aries</a></li>
								<li><a href="#">Fire</a></li>
								<li><a href="#">Mars</a></li>
								<li><a href="#">Taurus</a></li>
								<li><a href="#">Earth</a></li>
							</ul>
						</div>
					</div>
	
					<!-- Recent Comments -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Recent Comments</h4>
						</div>
						<ul class="list-group">
							<li class="list-group-item"><a href="#">I don't believe in astrology but still your writing style is really great! - <em>john</em></a></li>
							<li class="list-group-item"><a href="#">Wow.. what you said is really true! I'm an aries though - <em>Anto</em></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>