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
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<article>
						<a href="/jsp/articleDetails.jsp?{{ blogs.blogId }}"></a><h3>{{ blogs.blogTitle}}</h3></a>
				        <div class="row">
				          	<div class="col-sm-6 col-md-6">author -
				          	<em>	{{ blogs.user.name }} </em>
				          	</div>
				          	<div class="col-sm-6 col-md-6">
				          		<a href="/jsp/articleDetails.jsp?{{ blogs.blogId }}"><span class="glyphicon glyphicon-pencil"></span></a>
				          		&nbsp;&nbsp;  	{{ blogs.review.length }} Comments	          		
				          		&nbsp;&nbsp;<span class="glyphicon glyphicon-time"></span>&nbsp;&nbsp;{{ blogs.creationTime | date:'medium'}}			          		
				          	</div>
				          </div>
				          <hr>
	                      <br />		        
						  	 <p>{{ blogs.blogContent}}</p> 
				          <hr>
					</article>	
					<ul class="pager">
						<li class="previous"><a href="/jsp/home.jsp">&larr; Back to posts</a></li>
					</ul>	
				<!-- Comment form -->
					<div class="well">
						<h4>Leave a comment</h4>
						<form role="form" class="clearfix">
						  <div class="col-md-6 form-group">
						    <label class="sr-only" for="name">Name</label>
						    <input type="text" class="form-control" id="name" placeholder="Name">
						  </div>
						  <div class="col-md-6 form-group">
						    <label class="sr-only" for="email">Email</label>
						    <input type="email" class="form-control" id="email" placeholder="Email">
						  </div>
						  <div class="col-md-12 form-group">
						    <label class="sr-only" for="email">Comment</label>
						    <textarea class="form-control" id="comment" placeholder="Comment"></textarea>
						  </div>
						  <div class="col-md-12 form-group text-right">
						  	<button type="submit" class="btn btn-primary">Submit</button>
						  </div>
						</form>					
					</div>

					<hr />
				<!-- Displays the latest comments on the blog  -->
					<ul id="comments" class="comments">
						<li class="comment">
							<div class="clearfix">
								<h4 class="pull-left">John</h4>
								<p class="pull-right">9:41 PM on August 24, 2013</p>
							</div>
							<p>
								<em>I don't believe in astrology but still your writing style is really great!</em>
							</p>
						</li>

						<li class="comment clearfix">
							<div class="clearfix">
								<h4 class="pull-left">John</h4>
								<p class="pull-right">9:41 PM on August 24, 2013</p>
							</div>
							<p>
								<em>I don't believe in astrology but still your writing style is really great!</em>
							</p>
						</li>
					</ul>
				</div>
				<div class="col-md-4">
					<!-- Latest Posts -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>Latest Posts</h4>
						</div>
						<ul class="list-group" ng-repeat="blogs in blog | limitTo:5 ">
							<li class="list-group-item"><a href="#">{{blogs.blogTitle}}</li>
						</ul>
					</div>			
				</div>
			</div>
		</div>	
	</body>
</html>