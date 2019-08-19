<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
	<!--CSS Local LINK:START--> 
<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>상품 수정</h3>
		</div>	
	</div>
		
		
	<form id="modifyForm" method="post" enctype="multipart/form-data" action="/minishop/admin/product/productModify.do">
	
		<input type="hidden" name="pg" id="pg" value="${pg}"/>
		<input type="hidden" name="product_name_no" value="${productDTO.product_name_no}"/>  
		<input type="hidden" name="product_name_image" value="${productDTO.product_name_image}"/>	
	   	<input type="hidden" id="product_onstore" value="${productDTO.product_onstore}"/>	
	   	<input type="hidden" id="product_category_no" value="${productDTO.product_category_no}"/>
	   	<input type="hidden" id="promotioncode" value="${productDTO.promotioncode}"/>	   	
		<input type="hidden" id="unitcost" value="${productDTO.unitcost}"/>   	   	   		      
	   
	   <div class="form-row">
		    <div class="form-group col-5">
		      <label for="productName"><strong>상품명</strong></label>
		      <input type="text" class="form-control" name="productName" value="${productDTO.productName}">
		    </div>
		    <div class="form-group col-3">
		     <label for="product_category_no"><strong>상품분류</strong></label>
		      <select name="product_category_no" class="form-control">
				<option value="1">Women</option>  
				<option value="2">Men</option> 
				<option value="3">Accessories</option> 								 
		      </select>		    
		    </div>    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-4">
		      <label for="productName"><strong>상품예정가격</strong></label>
		      <input type="number" class="form-control" name="product_name_price" value="${productDTO.product_name_price}">
		    </div>
			<div class="form-group col-5">
		      <label for="product_name_image"><strong>섬네일 이미지 업로드</strong></label>
		      <input type="file" class="form-control-file" name="product_image_new">
		    </div>	 	    
	   </div>	   
	   <div class="form-row">
		    <div class="form-group col-8">
		      <label for="product_name_title"><strong>상품 페이지 제목</strong></label>
		      <input type="text" class="form-control" name="product_name_title" value="${productDTO.product_name_title}">
		    </div>	
			<div class="form-group col-3">
		      <label for="inofstore"><strong>입점 여부</strong></label>
		      <div class="custom-control custom-radio">
			  	<input type="radio"  value="YES" id="inofstore" name="product_onstore" class="custom-control-input">
			  	<label class="custom-control-label" for="inofstore">입점</label>
			  </div>
			  <div class="custom-control custom-radio">
			    <input type="radio"  value="NO" id="outofstore" name="product_onstore" class="custom-control-input">
			    <label class="custom-control-label" for="outofstore">입점 안함</label>
			  </div>
		    </div>	        
	   </div>	
	   <div class="form-row">
		    <div class="form-group col-10">
		    	<label for="editor_admin"><strong>상품 페이지 내용</strong></label>
		      <textarea id="editor_admin">${productDTO.product_name_detail}</textarea>
		    </div>
		    <input type="hidden" name="product_name_detail" value="${productDTO.product_name_detail}"/>
	   </div>	   
		<div id="instore">
		   <div class="form-row">
		   	<div class="form-group col-4">
		   		<label for="product_name_instockdate"><strong>입고(예정)일 </strong></label>
		   		 
		   		  <input type="date" class="form-control"  name="date" value="<fmt:formatDate value='${productDTO.product_name_instockdate}' pattern='yyyy-MM-dd'/>">
		   	</div>   	
		   </div>
		   <div class="form-row">
			    <div class="form-group col-4">
			      <label for="productID"><strong>등록코드</strong></label>
			      <input type="text" class="form-control-plaintext" name="productID" value="${productDTO.productID}" readonly>
			    </div>	
			    <div class="form-group col-4">
			     <label for="unitcost"><strong>판매단가</strong></label>
				  <input type="number" class="form-control" name="unitcost" value="${productDTO.unitcost}" placeholder="실제로 판매할 단위금액 입력">	    
			    </div>	
				<div class="form-group col-3">
			      <label for="promotioncode"><strong>추가 할인 가능 여부</strong></label>
			      <div class="custom-control custom-radio">
				  	<input type="radio"  value="1" id="dicountable" name="promotioncode" class="custom-control-input">
				  	<label class="custom-control-label" for="dicountable">할인 가능</label>
				  </div>
				  <div class="custom-control custom-radio">
				    <input type="radio"  value="0" id="nondiscountable" name="promotioncode" class="custom-control-input">
				    <label class="custom-control-label" for="nondiscountable">할인 불가</label>
				  </div>
			    </div>	        			    		        
		   </div>	
		</div>   	   	
    
	     	   
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">      
					<input type="button" class="btn btn-outline-dark" value="상품 수정" id="productModifyBtn">
					<input type="button" id="modifyReset" class="btn btn-outline-dark" value="다시작성">
					<input type="button" id="productReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
				</div>
			</div>	
		</div>	
		</form>			
 </div>
	


<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/ckeditor4Admin.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/admin.product.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/productModifyForm.js"></script>
