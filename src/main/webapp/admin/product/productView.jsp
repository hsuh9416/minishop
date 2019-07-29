<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    

	<!--Custom styles-->
	<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	<div class="col-lg-8">
		 <div class="row" id="titleDiv">
		 	<div class="col" align="center" style="padding-bottom: 20px;">
				<h3>[상품정보] ${productDTO.productName}</h3>
			</div>	
		</div>
    <input type="hidden" id="productID" value="${productDTO.productID}">
    <input type="hidden" id="product_name_no" value="${productDTO.product_name_no}">   
	<input type="hidden" id="pg" value="${pg}">	
	
	<div class="accordion" id="productInfo">
	
	  <!-- 상품 기본 정보:START -->	
	  <div class="card">
	    <div class="card-header" id="headingOne" align="center">
	      <h2 class="mb-0">
	        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#basicInfo" aria-expanded="true" aria-controls="basicInfo">
	         	 상품 기본 정보
	        </button>
	      </h2>
	    </div>
	
	    <div id="basicInfo" class="collapse show" data-parent="#productInfo">
	      <div class="card-body">
	        <div class="form-row justify-content-center">
				<div class="form-group col-3">
					<i id="iconSubject" class="far fa-file-alt">상품코드 #${productDTO.product_name_no}</i>
				</div>	
				<div class="form-group col-4">
					<i id="iconSubject" class="fas fa-dolly-flatbed">등록코드 #${productDTO.productID}</i>
				</div>	
				<div class="form-group col-5">
					<i id="iconSubject" class="fas fa-tag">이름 ${productDTO.productName}</i>
				</div>
			</div>
	        <div class="form-row justify-content-center">	
				<div class="form-group col-3">
					<i id="iconSubject" class="fas fa-list">분류 ${productDTO.product_category_name}</i>
				</div>		
				<div class="form-group col-4">
					<i id="iconSubject" class="fas fa-won-sign">
					 	정상가 <fmt:formatNumber type="number" value="${productDTO.product_name_price}" pattern="#,###"/>(원)/(개)
					</i>
				</div>						        				
				<div class="form-group col-5">
				 <c:if test="${productDTO.product_onstore=='YES'}">
					<i id="iconActive" class="fas fa-dolly-flatbed">상태 <strong>[입점]</strong></i>
				 </c:if>
				 <c:if test="${productDTO.product_onstore=='NO'}">
					<i id="iconInactive" class="fas fa-dolly-flatbed">상태 <strong>[미입점]</strong></i>
				 </c:if>			
				</div>
	
	      	</div>
	      </div>
	    </div>
	  </div>
	  <!-- 상품 기본 정보:END -->
	  <!-- 상품 옵션 정보:START -->
	  <div class="card">
	    <div class="card-header" id="headingTwo"  align="center">
	      <h2 class="mb-0">
	        <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
	          	상품 옵션 정보
	        </button>
	      </h2>
	    </div>
	    
	    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#productInfo">
	      <div class="card-body">
	       <div class="form-row justify-content-center">
				<div class="form-group col-3">
					<i id="iconSubject"class="fas fa-won-sign">판매가
					<strong id="pridceDiff"><fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>(원)/(개)</strong>
					</i>
				</div>	
				<div class="form-group col-4">
					<i id="iconSubject" class="fas fa-won-sign">원가대비
					<strong><fmt:formatNumber type="number" value="${productDTO.product_name_price-productDTO.unitcost}" pattern="#,###"/>(원)/(개)</strong>
					</i>
				</div>					
				<div class="form-group col-5">
					<i id="iconGeneral" class="fas fa-shopping-cart">누적판매량 <strong>${productDTO.product_salesMount}</strong>(개)</i>
				</div>	
			</div>
			<div class="form-row justify-content-center">	
				<div class="form-group col-3">
					<i id="iconMinimum" class="fas fa-eye">조회수<strong>${productDTO.product_hit}</strong></i>
				</div>	
				<div class="form-group col-4">
					<i id="iconMinimum" class="fas fa-heart">관심도<strong>${productDTO.product_like}</strong></i>
				</div>
				<div class="form-group col-5">
				 <c:if test="${productDTO.promotioncode=='1'}">
					<i id="iconActive" class="fas fa-percent"><strong>[할인가능]</strong></i>
				 </c:if>
				 <c:if test="${productDTO.promotioncode=='0'}">
					<i id="iconInactive" class="fas fa-percent"><strong>[할인제외]</strong></i>
				 </c:if>	
				</div>										
			</div>
	      </div>
	    </div>
	  </div>
	  <!-- 상품 옵션 정보:END -->
	  	  
	  <div class="card">
	    <div class="card-header" id="headingThree"  align="center">
	      <h2 class="mb-0">
	        <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
	        	 상품 상세 정보
	        </button>
	      </h2>
	    </div>
	    <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#productInfo">
	      <div class="card-body">
	        <div class="table">
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
					 		<img style="width:300px; height:300px;"src="/minishop/storage/${productDTO.product_name_image}">
					 		</td>
					 	</tr>			 	
					 	<tr align="center" class="table-dark"><td><strong>등록된 내용</strong></td></tr>	
					 	<tr align="center"><td id="content"><strong>${productDTO.product_name_detail}</strong></td></tr>			 			 	
					 </tbody>
				</table> 	
			</div>
	     </div>
	    </div>
	  </div>
	</div>	
		
</div>	
<!-- col-lg-8 -->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
if(${productDTO.product_name_price > productDTO.unitcost}) $('#pridceDiff').attr('color','red');
else if(${productDTO.product_name_price < productDTO.unitcost}) $('#pridceDiff').attr('color','blue');

});
</script>