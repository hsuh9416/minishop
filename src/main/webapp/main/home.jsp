<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Kissin' Bugs</title>

 <link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/template/css/main_styles.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/template/css/responsive.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/innerMain.css">

</head>

<body>
		 <!-- Header -->
			<jsp:include page="../template/header.jsp"/>
			
		 <!-- Body -->
		  <!-- Page Content -->
		  <div class="container" style="padding-top: 170px;">
		
		    <div class="row">	
		    	
				<jsp:include page="${menu}"/>			
		      
	           
				<jsp:include page="${display}"/>
	     	<div class="col-lg-1">
					<a href="#" class="top"><i class="fas fa-chevron-circle-up">TOP</i></a>	 	     			     		
	     	</div>			
		      			
		    </div>
		    <!-- /.row -->

		  </div>
		  <!-- /.container -->
 	 	<!-- Footer -->
			<jsp:include page="../template/footer.jsp"/>
</body>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
  <script type="text/javascript" src="/minishop/resources/template/js/custom.js"></script> 
  <script type="text/javascript" src="/minishop/resources/custom/js/innerMain.js"></script>

</html>
