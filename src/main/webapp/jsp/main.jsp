<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  		<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-route.js"></script>
	</head>
	<body ng-controller="usersController">
	<!-- Header -->
		 <header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">			
			<div class="container">
				<div class="navbar-header">		
					<a href="/" class="navbar-brand">Bloggers Point</a>
				</div>				 
			</div>
		</header>
		<br/><br/>	
		
		<c:if test="${not empty user}">
			<c:set var="id" scope="session" value="${user.id}"/>
		</c:if>
		
		<div class="container">
		<!-------->
		<div id="content">			
		    <ul id="tabs" class="nav nav-pills" data-tabs="tabs">
		        <li><a href="#profile" data-toggle="tab" ng-click="getOneUser(<c:out value="${id}"/>)">Profile</a></li>
		        <li><a href="#ViewBlogs" data-toggle="tab" ng-click="getOneUsersAllBlogs(<c:out value="${id}"/>)">View Blogs</a></li>
		        <li><a href="#CreateBlogs" data-toggle="tab" ng-click="">Create Blog</a></li>
		        <form class="navbar-form navbar-right">
					<div class="form-group">
						<h4> Welcome  {{ user.name }} ! &nbsp; &nbsp;<a  ng-click='logout()'><i class="fa fa-sign-out fa-lg" aria-hidden="true" ></i></a></h4>      
					</div></form>
		    </ul>
		    <div id="my-tab-content" class="tab-content">        
		        <div class="tab-pane" id="profile">
		            <h2>Profile Details</h2><br><br>
				    <table class="table table-bordered table-striped container" ng-include="getTemplate(user)">
						<script type="text/ng-template" id="display">
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
						<button type="button" class="btn btn-primary" ng-click="editObj(user)" > Edit </button>						
					 </script>
					 
					 <script type="text/ng-template" id="edit">
							<tr>
								<td>Name: </td><td><input type="text" ng-model=user.name class="form-control input-sm"/></td>
							</tr>
							<tr>
								<td>Email:</td><td><input type="text" ng-model=user.email class="form-control input-sm"/></td>
							</tr>
							<tr>
								<td>Contact:</td><td><input type="text" ng-model=user.contact class="form-control input-sm"/></td>
							</tr>
							<tr>
								<td>Credits: </td><td>{{ user.credit }}</td>
							</tr>
							<button type="button" class="btn btn-info" ng-click="updateUser()"> Save </button>
							<button type="button" class="btn btn-danger" ng-click="reset()"> Back </button> 		
					</script>						
					</table>
					
					<br/>
					
		        </div>
		        <div class="tab-pane" id="ViewBlogs">
		        	
		            <h2> You recent Blogs</h2>
		            <p style=" text-align: center; color: red;"> Please note blog saved as draft will be automatically published when made <b>Active.</b></p>
		            <h3>Click on the icons for respective sort options </h3>
		            <h4> 
		             	Month &nbsp;&nbsp;<a ng-click="getbyMonth(user.id)"><i class="fa fa-sort" aria-hidden="true"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		            	Year  &nbsp;&nbsp;<a ng-click="getbyYear(user.id)"><i class="fa fa-sort" aria-hidden="true"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		            	Likes &nbsp;&nbsp;<a ng-click="getLike(user.id)"><i class="fa fa-sort" aria-hidden="true"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		            	Dislikes &nbsp;&nbsp;<a ng-click="getDislike(user.id)"><i class="fa fa-sort" aria-hidden="true"></i></a><br/>
		            <br/>
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
						<td><a href="#"> Enable / Disable </a></td>
					  </tr>
					</thead>
					<tbody>
					  <tr ng-repeat="blog in blogs" >
					  	 <td>{{ blog.blogTitle }}</td>			  
					     <td>{{ blog.blogContent }}</td>
					     <td>{{ blog.creationTime | date:'medium' }}</td>
					     <td>{{ blog.publishTime | date:'medium' }}</td>
					     <td>{{ blog.draft }}</td>
					     <td>{{ blog.blogLikes }}</td>
					     <td>{{ blog.blogDislikes }}</td>
					     <td>{{ blog.isActive }}</td>
						 <td>
				    		&nbsp;&nbsp;<button class="btn" ng-Click='isActiveBlog(user.id,blog.blogId)'><i class="fa fa-archive"></i></button> 
				    	  </td>
					  </tr>
					</tbody>
				  </table>	            
		        </div>
		        <!-- Creation of blogs  -->
		         <div class="tab-pane" id="CreateBlogs" ng-controller="usersController">
		            <h2> Please start below </h2><br/>
		            <form ng-submit="createBlog()" name="form" class="pure-form login-form">
				    	<div class="form-group">
							<input type="text" class="form-control"  ng-model="blog.blogTitle" placeholder="Blog Title" row="2"/><br/>
							<textarea class="form-control"  ng-model="blog.blogContent" rows="6" cols="6"></textarea><br/><br/>
						</div>
						<div class="checkbox">
							  <label><input type="checkbox" ng-model="blog.draft" >Draft</label>
						</div>
						<button type="submit"  class="btn btn-primary"> Create </button>
					</form>
				</div><!-- Creation of blogs  -->		       		   
				</div> <!-- container -->
			</div>
		</div>
	<!-- tab operations  -->
		<script type="text/javascript">
			jQuery(document).ready(function ($) {
		        $('#tabs').tab(); 
		});
		</script>
	</body>
</html>