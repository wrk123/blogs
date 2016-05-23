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
	<div class="container" ng-show="toggle" >
	<h2 class="page-title">Welcome to the blogs.</h2>
		<h3> Please select the below options to continue</h3><br/>
		<div class="container">
		  <div class="row">
		  	<div class="col">
		  		<!-- <a href="/jsp/login.jsp"><button class="btn btn-primary">Login</button>&</a>nbsp;&nbsp;&nbsp;&nbsp; -->
		  		<button class="btn btn-primary" data-toggle="modal" data-target="#myModal">Login</button>&nbsp;&nbsp;&nbsp;&nbsp;
	 	  		<a href="/jsp/home.jsp"><button type="button" class="btn btn-info">View all blogs</button></a>&nbsp;&nbsp;&nbsp;&nbsp;
		  		<button ng-click='toggle=!toggle' class="btn btn-panel"><i class="panel-title-icon fa fa-plus"></i> Add New User </button>
		  	</div>
		  </div>
		</div>
	</div><br/><br/><br/><br/><br/>
	
	<!-- For creating a new user -->
	 <div class="container"  ng-hide="toggle">
		 <div id="add-task-panel" class="fadein fadeout addpanel panel">
			<h3>Please enter the user details below </h3>
			<table class="table table-bordered table-striped container">
				<tr>
					<td>Name: *</td>
					<td><input type="text" ng-model="user.name"/></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="email" ng-model="user.email" required /></td>
				</tr>
				<tr>
					<td>Mobile:</td>
					<td><input type="text" ng-model="user.contact"/>&nbsp;&nbsp;Count=&nbsp;{{user.contact.length}}&nbsp;</td>
				</tr>
				<tr>
					<td>Password: *</td>
					<td><input type="password" ng-model="user.password" required /></td>
				</tr>					
			</table><br/><br/><br/><br/>		
			<a href="/jsp/index.jsp"><button class="btn btn-info"> Back </button></a>&nbsp;&nbsp;
			<button ng-click="addUser()" class="btn btn-primary">Create New User</button>						
		</div>
	</div> 
	
	<!-- For authneticating user -->
	 <div class="modal fade" id="myModal" role="dialog">
     	<div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">Please enter the details </h4>
	        </div>
	        <div class="modal-body">
	        	<label class="col-sm-2 control-label" >Email</label>
	        	<input type="email" class="form-control"  ng-model="user.email" placeholder="Email"/><br/>
	        	<label class="col-sm-2 control-label" >Password</label>
	        	<input type="password" class="form-control"  ng-model="user.password" placeholder="Password"/>
	        </div>
	        <div class="modal-footer">
	          <button ng-click="login()"  class="btn btn-primary">Sign in</button>
	          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	        </div>
	      </div>	     
    	</div>
  	</div>
  	
</body>
</html>