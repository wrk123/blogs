<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="userApp">
	<head>
		<title>Blogs </title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="../static/css/style.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
		<script type="text/javascript" src="../static/js/angular.js"></script>
		<script type="text/javascript" src="../static/js/angular-animate.js"></script>
		<script type="text/javascript" src="../static/js/app.js"></script>
		<script type="text/javascript" src="../static/js/controller/userController.js"></script>
		<script type="text/javascript" src="../static/js/controller/blogspotController.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	</head>
	<body ng-controller="usersController">
	<!-- Header -->
		 <header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">			
			<div class="container">
				<div class="navbar-header">		
					<a href="/jsp/index.jsp" class="navbar-brand">Bloggers Point</a>
				</div>				 
			</div>
		</header>
		
		<br/><br/>	
		<div class="container">
		<!-------->
		<div id="content">			
		    <ul id="tabs" class="nav nav-pills" data-tabs="tabs">
		        <li><a href="#profile" data-toggle="tab">Profile</a></li>
		        <li><a href="#ViewBlogs" data-toggle="tab">View Blogs</a></li>
		        <li><a href="#CreateBlogs" data-toggle="tab">Create Blog</a></li>
		        <form class="navbar-form navbar-right">
					<div class="form-group">
						<h4> Welcome  User name  {{user.name }} ! &nbsp; &nbsp;<i class="fa fa-user" aria-hidden="true"></i></h4>      
					</div></form>
		    </ul>
		    <div id="my-tab-content" class="tab-content">        
		        <div class="tab-pane" id="profile">
		            <h2>Profile Details</h2>
				    <table class="table table-bordered table-striped container">
						<tr>
							<td>Name: </td><td>{{ user.name }}</td>
						</tr>
						<tr>
							<td>Email:</td><td>{{ user.email }}</td>
						</tr>
						<tr>
							<td>Contact:</td><td>{{user.contact}}</td>
						</tr>
						<tr>
							<td>Credits: </td><td>{{ user.credit }}</td>
						</tr>						
					</table><br/>
					<button class="btn btn-primary"> Edit </button>
		        </div>
		        <div class="tab-pane" id="ViewBlogs">
		            <h2> You recent Blogs</h2>
		             <table class="table table-bordered table-hover">
					<thead>
					  <tr>
						<td><a href="#"> Title     </a></td>
						<td><a href="#"> Content   </a></td>
						<td><a href="#"> Created on </a></td>
						<td><a href="#"> Published on  </a></td>
						<td><a href="#"> Draft      </a></td>
						<td><a href="#"> Likes     </a></td>
						<td><a href="#"> Dislikes      </a></td>
						<td><a href="#"> Is Active  </a></td>
					  </tr>
					</thead>
					<tbody>
					  <tr ng-repeat="blog in blogs" ng-include="getTemplate(blog)">
					   <script type="text/ng-template" id="display">
					  	 <td>{{ blog.blogTitle }}</td>			  
					     <td>{{ blog.blogContent }}</td>
					     <td>{{ blog.creationTime | date:'medium' }}</td>
					     <td>{{ blog.publishTime | date:'medium' }}</td>
					     <td>{{ blog.draft }}</td>
					     <td>{{ blog.blogLikes }}</td>
					     <td>{{ blog.blogDislikes }}</td>
					     <td>{{ blog.isActive }}</td>
						  <td>
				    		&nbsp;<button class="btn btn-info" ng-click="editUser(blog)"><i class="fa fa-pencil"></i></button>&nbsp;&nbsp;
				    		<button class="btn" ng-Click='updateUser(blog.id)'><i class="fa fa-archive"></i></button> 
				    	  </td>
					  </script>
   					  <script type="text/ng-template" id="edit">
						<td>{{ blog.blogTitle }}</td>
						<td><input type="text" ng-model=blog.blogContent class="form-control input-sm"/></td>
						<td>{{ blog.creationTime | date:'medium' }}</td>
					    <td>{{ blog.publishTime | date:'medium' }}</td>
						<td><input type="text" ng-model=blog.draft class="form-control input-sm"/></td>
						<td>{{ blog.blogLikes }}</td>
					    <td>{{ blog.blogDislikes }}</td>
						<td>
				   	 		<button type="button" class="btn btn-info btn-sm" ng-click="updateBlog(blog)">Save</button>
     						<button type="button" class="btn btn-danger btn-sm" ng-click="reset()">Back</button> 
				   		</td>						
					  </script>
					  </tr>
					</tbody>
				  </table>	            
		        </div>
		         <div class="tab-pane" id="CreateBlogs">
		            <h2> Please start below </h2>
		        	  <div class="container">
						<input type="email" class="form-control"  ng-model="blogs.blogTitle" placeholder="Blog Title" row="2"/><br/>
						<textarea class="form-control"  ng-model="blogs.blogContent" rows="12" cols="6"></textarea><br/><br/>
						<button ng-click="addBlog()"  class="btn btn-primary"> Create </button>
					 </div>
		        </div>		       
		    </div>
		</div>
	</div> <!-- container -->
		
	
		<script type="text/javascript">
		    jQuery(document).ready(function ($) {
		        $('#tabs').tab();
		    });
		</script>    
	</body>
</html>