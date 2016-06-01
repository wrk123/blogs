<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="blogspotApp">
	<head>
		<title>Blog in detail</title>
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
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  		<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-route.js"></script>
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
					<a href="/" class="navbar-brand">Bloggers Point</a>
				</div>
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">								    
					<ul class="nav navbar-nav">
						<li><a href="/blogHome">Home</a></li>
						<li><a href="#">Profile</a></li>
						<li><a href="#">View blogs</a></li>
					</ul>
				</nav>
			</div>
		</header>
		<c:if test="${not empty blogs}">
			<c:set var="id" scope="session" value="${blogs.blogId}"/>
			<c:set var="userId" value="${blogs.userId}"/>
		</c:if>
		
		<div class="container"  ng-init="init(<c:out value="${id}"/>)">
			<%	
			  Cookie[] cookies = request.getCookies();
				String name="";
				String cookieId="";
				String email="";				
			
			if(cookies.length>1)
			 {  cookieId=cookies[1].getValue();
			 	name=cookies[2].getValue();
			 	email=cookies[3].getValue();
			 	email=email.replace("%40","@");
			 	if(!name.isEmpty())
				{ 
				  %>
			<!-- add Logout -->
			<form class="navbar-form navbar-right">
				<div class="form-group">
				 	<h4> Welcome <%=name%> ! &nbsp; &nbsp;<a  ng-click="logout('<%=email%>')"><i class="fa fa-sign-out fa-lg" aria-hidden="true" ></i></a></h4>
				</div>
			</form>
			<%}	}%>
			<div class="row">
				<div class="col-md-8">
					<article>
						<h2>{{ blogs.blogTitle}}</h2></a>
				        <div class="row">
				          	<div class="col-sm-6 col-md-6">author -
				          	<em>	{{ blogs.user.name }} </em>
				          	</div>
				          	<div class="col-sm-6 col-md-6">
				          		<span class="glyphicon glyphicon-pencil"></span>
				          		&nbsp;&nbsp;  	{{ blogs.review.length }} Comments	          		
				          		&nbsp;&nbsp;<span class="glyphicon glyphicon-time"></span>&nbsp;&nbsp;{{ blogs.creationTime | date:'medium'}}			          		
				          	</div>
				          </div>
				          <hr>
	                      <br />		        
						  	 <p>{{ blogs.blogContent }}</p> 
				          <hr>
				         <%  
			         if(cookies.length>1 && !cookieId.equals(pageContext.getAttribute("userId").toString()))
		 				{ 	if(!name.isEmpty())
							{%> 
						 <div class="col-md-6 form-group text-ledt">
						 	
						  	<a ng-click="likeBlog(<%=cookieId%>,<c:out value="${id}"/>)"><i class="fa fa-thumbs-o-up fa-lg" aria-hidden="true">&nbsp;&nbsp;&nbsp;Like &nbsp;&nbsp; {{blogs.blogLikes}}</i></a> &nbsp;&nbsp; &nbsp;&nbsp;
				          	<a ng-click="disLikeBlog(<%=cookieId%>,<c:out value="${id}"/>)"><i class="fa fa-thumbs-o-down fa-lg" aria-hidden="true">&nbsp;&nbsp;&nbsp;Dislike &nbsp;&nbsp; {{blogs.blogDislikes}}</i></a>
				         </div>
				          <%  }}else{ %>
				          <div>
				          	<i class="fa fa-thumbs-up fa-lg" aria-hidden="true">&nbsp;&nbsp;Likes       {{ blogs.blogLikes }}</i> &nbsp;&nbsp; &nbsp;&nbsp;
				          	<i class="fa fa-thumbs-down fa-lg" aria-hidden="true">&nbsp;&nbsp;Dislikes  {{ blogs.blogDislikes }}</i>
				          </div>
				          <%   } %>
				          <br/>
					</article>	
					<ul class="pager">
						<li class="previous"><a href="/blogHome">&larr; Back to posts</a></li>
					</ul>	
				<!-- Comment form -->
				<%
				   if(cookies.length>1 &&  !cookieId.equals(pageContext.getAttribute("userId").toString())){
					  
					if(!name.isEmpty()) {%>
					<div class="well">
						<h4>Leave a comment</h4>
						<form role="form" class="clearfix"> 
						  <div class="col-md-6 form-group">
						    <label class="sr-only" for="name">Name</label>
						    <%=name%>
						  </div>						   
						  <div class="col-md-12 form-group">
						    <label class="sr-only" for="email">Comment</label>
						    <textarea class="form-control" ng-model="review.comments" placeholder="Comment"></textarea>
						  </div>
						  <div class="col-md-12 form-group text-right">
						  	<button type="submit" ng-click="commentOnBlog(<%=cookieId%>,<c:out value="${id}"/>)" class="btn btn-primary">Submit</button>
						  </div>
						</form>					
					</div> 
					<%} }%>
					<hr />
				<!-- Displays the latest comments on the blog  -->
					<h3><em> Recent Comments -</em></h3>
					<ul id="comments" class="comments">
						<li class="comment" ng-repeat="review in blogs.review">
							<div class="clearfix">
								<h5 class="pull-left"> ID: {{ review.userId }} </h5>
								<p class="pull-right"><em>Commented on : &nbsp;{{ review.creationTime | date:'medium' }}</em></p>
							</div>
							<p>
								<em>{{ review.comments }}</em>
							</p>
						</li>
					</ul>
				</div>
				
				<!-- Right  Side panel -->
				<div class="col-md-4">
					<!-- Latest Posts -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3>Latest Posts</h4>
						</div>
						<ul class="list-group" ng-repeat="blogs in blog | limitTo:6 ">
							<li class="list-group-item"><a href="/article/{{ blogs.blogId }}">{{blogs.blogTitle}}</li>
						</ul>
					</div>			
				</div>
			 <!-- Right Side panel -->
			</div>
		</div>
	</body>
</html>