<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!docType html>
<html>
	<head>
	<!--CSS Global LINK:START-->
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/template/css/main_styles.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/template/css/responsive.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/innerMain.css">	
	<!--CSS Global LINK:END-->
		<title>Kissin' Bugs</title>
	</head>
	<body>
	
	<!--Body Header:START-->
		<jsp:include page="/template/header.jsp"/>	
	<!--Body Header:END-->			 	
	<!--Body Content:START-->
			<div class="container" style="padding-top: 170px;">
				<div class="row">		
					<!--col-lg-3:START-->
						<jsp:include page="/template/left.jsp"/>	
					<!--col-lg-3:END-->
					<!--col-lg-8:START -->												     
						<jsp:include page="${display}"/>
					<!--col-lg-8:END-->					
			     	<div class="col-lg-1">
						<a href="#" class="top"><i class="fas fa-chevron-circle-up">TOP</i></a>	 	     			     		
		     		</div>			 			
			    </div>
			</div>
	<!--Body Content:END-->
 	<!--Body Footer:START-->
		<jsp:include page="/template/footer.jsp"/>
 	<!--Body Footer:END-->	
	</body>
	<!--JavaScript Global LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/template/js/custom.js"></script> 
<script type="text/javascript" src="/minishop/resources/custom/js/main/innerMain.js"></script>
	<!--JavaScript Global LINK:END-->
</html>
