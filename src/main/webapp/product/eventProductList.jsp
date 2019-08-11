<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1">

	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">	
	
	<input type="hidden" id="condition" value="${condition}">  
 	<div class="form-row justify-content-center">
		<h4 id="eventType">${condition}</h4>	
	</div>	    	
	   	
	<div class="form-row justify-content-center" id="categories">
		<div class="form-group col-md-4" >
				<ul class="list-group list-group-horizontal">				  
				  <li class="list-group-item">
				  	<a class="list-group-item dropdown-toggle " href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						${condition}
			     	</a>
				     <div class="dropdown-menu" aria-labelledby="navbarDropdown">
					       <a class="dropdown-item" href="/minishop/product/eventProductList.do?condition=newArrival">newArrival</a>
					       <a class="dropdown-item" href="/minishop/product/eventProductList.do?condition=onSale">onSale</a>
						   <a class="dropdown-item" href="/minishop/product/eventProductList.do?condition=mustHave">mustHave</a>     
				     </div>
				  </li> 				  				  	  
				</ul>		
		</div>
		<div class="form-group col-md-8" >
			<ul class="list-group list-group-horizontal">
				<li class="list-group-item">
					<div class="custom-control custom-radio">
						<input type="radio" name="searchOption"  id="option1" value="PRODUCT.PRODUCTNAME" class="custom-control-input" checked>
						<label class="custom-control-label" for="option1">이름</label>
					</div>
				</li>
				<li class="list-group-item">	
					<div class="custom-control custom-radio">
						<input type="radio" name="searchOption"  id="option2" value="PRODUCT_NAME.PRODUCT_NAME_TITLE" class="custom-control-input">
						<label class="custom-control-label" for="option2">제목</label>
					</div>							
				</li>												  				  
				<li class="list-group-item">
					<form class="form-inline">
						<input class="form-control form-control-sm mr-3 w-75" id="searchWord" type="text" placeholder="Search"
							aria-label="Search" value="${searchWord}">
						<a id="searchBtn" href="#"><i class="fas fa-search" aria-hidden="true"></i></a>
					</form>
				</li>		  
			</ul>		
		</div>
	</div>

	<div id="mainList" class="row"></div>

  	 	<!-- shop policies -->
	<jsp:include page="/template/shopPolicy.jsp"/>
        		
</div>  
	
 	<!--JavaScript Local LINK:START-->	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/product/eventProductList.js"></script>
	<!--JavaScript Local LINK:END-->
