<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
	<!--Bootsrap 4-->
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	
    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="/mallproject/resources/fontawesome-free-5.9.0-web/css/all.css">

	<!--Custom styles-->
	<link rel="stylesheet" href="/mallproject/css/userproduct.css">
	
<div class="productForm-container">
 <div class="container-fluid">
	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="/mallproject/admin/product/productManage.do">상품 등록 관리</a></li>		
			<li class="breadcrumb-item active"  aria-current="page">개별 상품 정보</li>
			<li class="breadcrumb-item"><a href="/mallproject/admin/product/productWrite.do">개별 상품 등록</a></li>			
		</ol>
	</nav>
 </div>
</div>
<div class="container-fluid">
    <input type="hidden" id="productID" value="${productDTO.productID}">
    <input type="hidden" id="product_name_no" value="${productDTO.product_name_no}">   
	<input type="hidden" id="pg" value="${pg}">	
	<form class="form-row">
		<div class="form-group col-md-2">
			<i id="iconTitle"class="far fa-file-alt"><strong>상품코드 #${productDTO.product_name_no}</strong></i>
		</div>	
		<div class="form-group col-md-2">
			<i id="iconTitle" class="fas fa-dolly-flatbed"><strong>등록코드 #${productDTO.productID}</strong></i>
		</div>	
		<div class="form-group col-md-3">
			<i id="iconSubject" class="fas fa-tag"><strong>이름 ${productDTO.productName}</strong></i>
		</div>		
		<div class="form-group col-md-2">
		 <c:if test="${productDTO.product_onstore=='YES'}">
			<i id="iconActive" class="fas fa-dolly-flatbed">상태 <strong>[입점]</strong></i>
		 </c:if>
		 <c:if test="${productDTO.product_onstore=='NO'}">
			<i id="iconInactive" class="fas fa-dolly-flatbed">상태 <strong>[미입점]</strong></i>
		 </c:if>			
		</div>
		<div class="form-group col-md-3">
			<i id="iconSubject" class="fas fa-list">분류 
			<strong>${productDTO.product_category_name}</strong></i>
		</div>										
	</form>
	<form class="form-row">
		<div class="form-group col-md-3">
			<i id="iconSubject"class="fas fa-won-sign">단위당 정상가격
			<strong><fmt:formatNumber type="number" value="${productDTO.product_name_price}" pattern="#,###"/>(원)</strong>
			</i>
		</div>	
		<div class="form-group col-md-3">
			<i id="iconSubject" class="fas fa-won-sign">단위당 판매가격
			<strong><fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>(원)</strong>
			</i>
		</div>	
		<div class="form-group col-md-2">
			<i id="iconGeneral" class="fas fa-shopping-cart">누적판매량 <strong>${productDTO.product_salesMount}</strong>(개)</i>
		</div>		
		<div class="form-group col-md-1">
			<i id="iconMinimum" class="fas fa-eye">조회수<strong>${productDTO.product_hit}</strong></i>
		</div>	
		<div class="form-group col-md-1">
			<i id="iconMinimum" class="fas fa-heart">관심도<strong>${productDTO.product_like}</strong></i>
		</div>
		<div class="form-group col-md-2">
		 <c:if test="${productDTO.promotioncode=='1'}">
			<i id="iconActive" class="fas fa-percent"><strong>[할인가능]</strong></i>
		 </c:if>
		 <c:if test="${productDTO.promotioncode=='0'}">
			<i id="iconInactive" class="fas fa-percent"><strong>[할인제외]</strong></i>
		 </c:if>	
		</div>													
	</form>	
	<div class="table-responsive">
		<table id="productViewTable" class="table justify-content-center">
			<thead class="thead-dark">
			    <tr align="center">
					<th scope="col">상품 등록 정보</th>		
			   </tr>
			 </thead>
			 <tbody>
			 	<tr align="center" class="table-dark"><td><strong>등록된 제목</strong></td></tr>
			 	<tr align="center"><td><strong>${productDTO.product_name_title}</strong></td></tr>
			 	<tr align="center" class="table-dark"><td><strong>등록된 이미지</strong></td></tr>
			 	<tr align="center">
			 		<td>
			 		<img style="width:300px; height:300px;"src="/mallproject/storage/${productDTO.product_name_image}">
			 		</td>
			 	</tr>			 	
			 	<tr align="center" class="table-dark"><td><strong>등록된 내용</strong></td></tr>	
			 	<tr align="center"><td id="content"><strong>${productDTO.product_name_detail}</strong></td></tr>			 			 	
			 </tbody>
		</table> 	
	</div>
</div>	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mapplroject/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){

});
</script>