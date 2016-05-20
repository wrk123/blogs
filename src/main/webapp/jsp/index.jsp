<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>AngularJS Blogs</title>
		<link href="css/style.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/sandstone/bootstrap.min.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
		
		
	</head>
	<body>
	<div class="container">
	<h2 class="page-title">Welcome to the blogs.</h2>
		<h3> Please select the below options to continue</h3>
		<div class="container">
		  <div class="row">
		  	<div class="col">
		  		<button type="button" class="btn btn-primary">Login</button>&nbsp;&nbsp;&nbsp;&nbsp;
	 	  		<%-- <a href="${request.pageContextPath}/jsp/home.jsp"><button type="button" class="btn btn-info">View all blogs</button></a> --%> 
	 	  		<a href="/jsp/home.jsp"><button type="button" class="btn btn-info">View all blogs</button></a>
		  		
		  	</div>
		  </div>
		  
		</div>
	</div>
</body>
</html>