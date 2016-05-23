<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="userApp">
	<head>
		<title>User login </title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<link href="../static/css/style.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"/>
		<script type="text/javascript" src="../static/js/angular.js"></script>
		<script type="text/javascript" src="../static/js/angular-animate.js"></script>
		<script type="text/javascript" src="../static/js/app.js"></script>
		<script type="text/javascript" src="../static/js/controller/userController.js"></script>
		<script type="text/javascript" src="../static/js/controller/blogspotController.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	</head>
	<body ng-controller="usersController">
	<div class="container">
		<div class="col-md-4"></div>
			<div class="col-md-4">
	  		<h3>Please enter email and password to continue </h3>		
	  		<div class="modal-body">
              <form class="form-horizontal" role="form">
                <div class="form-group">
                  <label  class="col-sm-2 control-label" for="inputEmail3">Email</label>
                  <div class="col-sm-10">
                      <input type="email" class="form-control"  ng-model="user.email" placeholder="Email"/>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label" for="inputPassword3" >Password</label>
                  <div class="col-sm-10">
                      <input type="password" class="form-control"  ng-model="user.password" placeholder="Password"/>
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-10">
                    <button ng-click="login()"  class="btn btn-default">Sign in</button>
                  </div>
                </div>
              </form>
             </div>
          </div>
          <div class="col-md-4"></div>
         </div>
	</body>
</html>