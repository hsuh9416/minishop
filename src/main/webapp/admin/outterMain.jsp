<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>관리자 화면</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="description" content="jeweler - Material Admin Template">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Google Fonts
		============================================ -->
    <link href="https://fonts.googleapis.com/css?family=Play:400,700" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/mallproject/resources/fontawesome5/css/all.css">
		<link rel="stylesheet" type="text/css" href="/mallproject/resources/OwlCarousel2/css/owl.carousel.css">
		<link rel="stylesheet" type="text/css" href="/mallproject/resources/OwlCarousel2/css/owl.theme.default.css">
		<link rel="stylesheet" type="text/css" href="/mallproject/resources/OwlCarousel2/css/animate.css">
    <!-- normalize CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/normalize.css">
    <!-- meanmenu icon CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/meanmenu.min.css">
    <!-- main CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/main.css">
    <!-- morrisjs CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/morrisjs/morris.css">
    <!-- mCustomScrollbar CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/scrollbar/jquery.mCustomScrollbar.min.css">
    <!-- metisMenu CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/metisMenu/metisMenu.min.css">
    <link rel="stylesheet" href="/mallproject/resources/css/metisMenu/metisMenu-vertical.css">

    <!-- style CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/css/outterMain.css">
    <!-- responsive CSS
		============================================ -->
    <link rel="stylesheet" href="/mallproject/resources/css/responsive.css">

	</head>

	<body>

		 <!-- left -->
			<jsp:include page="/adminTemplate/adminLeft.jsp"/>
		 

		 <div class="all-content-wrapper"> 
		 
		 <!-- top -->		 
		 	<jsp:include page="/adminTemplate/adminTop.jsp"/>
		 
		 <!-- body -->
			<jsp:include page="${adminDisplay}"/>	 
	
		 <!-- Footer -->
	 		<jsp:include page="/adminTemplate/adminBottom.jsp"/>
	
		</div>	
	</body>
	    <!-- modernizr JS
		============================================ -->
	
	<script src="/mallproject/resources/js/vendor/modernizr-2.8.3.min.js"></script>
    <!-- jquery
		============================================ -->
    <script src="/mallproject/resources/js/vendor/jquery-1.11.3.min.js"></script>
    <!-- bootstrap JS
		============================================ -->
    <script src="/mallproject/resources/js/bootstrap.min.js"></script>
    <!-- wow JS
		============================================ -->
    <script src="/mallproject/resources/js/wow.min.js"></script>
    <!-- price-slider JS
		============================================ -->
    <script src="/mallproject/resources/js/jquery-price-slider.js"></script>
    <!-- meanmenu JS
		============================================ -->
    <script src="/mallproject/resources/js/jquery.meanmenu.js"></script>
    <!-- owl.carousel JS
		============================================ -->
    <script src="/mallproject/resources/js/owl.carousel.min.js"></script>
    <!-- sticky JS
		============================================ -->
    <script src="/mallproject/resources/js/jquery.sticky.js"></script>
    <!-- scrollUp JS
		============================================ -->
    <script src="/mallproject/resources/js/jquery.scrollUp.min.js"></script>
    <!-- mCustomScrollbar JS
		============================================ -->
    <script src="/mallproject/resources/js/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="/mallproject/resources/js/scrollbar/mCustomScrollbar-active.js"></script>
    <!-- metisMenu JS
		============================================ -->
    <script src="/mallproject/resources/js/metisMenu/metisMenu.min.js"></script>
    <script src="/mallproject/resources/js/metisMenu/metisMenu-active.js"></script>
    <!-- morrisjs JS
		============================================ -->
    <script src="/mallproject/resources/js/morrisjs/raphael-min.js"></script>
    <script src="/mallproject/resources/js/morrisjs/morris.js"></script>
    <script src="/mallproject/resources/js/morrisjs/morris-active.js"></script>
    <!-- morrisjs JS
		============================================ -->
    <script src="/mallproject/resources/js/sparkline/jquery.sparkline.min.js"></script>
    <script src="/mallproject/resources/js/sparkline/jquery.charts-sparkline.js"></script>

    <!-- plugins JS
		============================================ -->
    <script src="/mallproject/resources/js/plugins.js"></script>
    <!-- main JS
		============================================ -->
    <script src="/mallproject/resources/js/main.js"></script>
</html>

	







