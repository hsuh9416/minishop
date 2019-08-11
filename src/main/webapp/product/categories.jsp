<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">	

	<input type="hidden" id="product_category_name" value="${product_category_name}">
	<input type="hidden" id="order" value="${order}">   
	<div class="form-row justify-content-center">
		<h4 align="center">${product_category_name}</h4>	
	</div>	
	<div class="form-row justify-content-center" id="categories">
		<div class="form-group col-md-4" >
			<ul class="list-group list-group-horizontal">		
				<li class="list-group-item">
					<a class="list-group-item dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						${product_category_name}
					</a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=ALL&order=new">ALL</a>
					<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=MEN&order=new">MEN</a>
					<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=WOMEN&order=new">WOMEN</a>
					<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=ACCESORIES&order=new">ACCESORIES</a>					       					       
				</div>
				</li> 				  
				<li class="list-group-item">
					<a class="list-group-item dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<c:if test="${order=='new'}">신상품순</c:if>
						<c:if test="${order=='name'}">상품명순</c:if>
						<c:if test="${order=='highPrice'}">최고가순</c:if>
						<c:if test="${order=='lowPrice'}">최저가순</c:if>
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=new">신상품순</a>
						<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=name">상품명순</a>
						<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=highPrice">최고가순</a>
						<a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=lowPrice">최저가순</a>		       
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
<script type="text/javascript" src="/minishop/resources/custom/js/product/categories.js"></script>
	<!--JavaScript Local LINK:END-->
