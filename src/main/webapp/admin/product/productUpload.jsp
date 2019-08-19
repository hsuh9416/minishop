<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<!--CSS Local LINK:START--> 
<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>상품등록</h3>
		</div>
	</div>  
		
	<form id="uploadForm" method="post" enctype="multipart/form-data" action="/minishop/admin/product/doUpload.do">
	
		<div class="form-row">
			<div class="form-group col-5">
				<label for="productName"><strong>상품명</strong></label>
		      	<input type="text" class="form-control" name="productName" id="productName" placeholder="상품명 입력">
		    </div>
		    <div class="form-group col-3">
				<label for="product_category_no"><strong>상품분류</strong></label>
				<select id="product_category_no" name="product_category_no" id="product_category_no" class="form-control">
					<option value="1">Women</option>  
					<option value="2">Men</option> 
					<option value="3">Accessories</option> 								 
				</select>		    
			</div>    
	   </div>
	   
		<div class="form-row">
			<div class="form-group col-4">
				<label for="product_name_price"><strong>상품예정가격</strong></label>
				<input type="number" class="form-control" name="product_name_price" id="product_name_price" placeholder="ex>2000(단가,단위 생략)">
		    </div>
			<div class="form-group col-5">
				<label for="product_name_image"><strong>섬네일 이미지 업로드</strong></label>
				<input type="file" class="form-control-file" name="product_image" id="product_name_image">
		    </div>	 	    
		</div>
	   	   
		<div class="form-row">
			<div class="form-group col-8">
				<label for="product_name_title"><strong>상품 페이지 제목</strong></label>
				<input type="text" class="form-control" name="product_name_title" id="product_name_title" placeholder="제목 입력">
		    </div>	
			<div class="form-group col-3">
				<label for="inofstore"><strong>입점 여부</strong></label>
				<div class="custom-control custom-radio">
			  		<input type="radio"  value="YES" id="inofstore" name="product_onstore" class="custom-control-input">
			  		<label class="custom-control-label" for="inofstore">입점</label>
			  	</div>
				<div class="custom-control custom-radio">
			    	<input type="radio"  value="NO" id="outofstore" name="product_onstore" class="custom-control-input" checked>
			    	<label class="custom-control-label" for="outofstore">입점 안함</label>
				</div>
		    </div>	        
	   </div>	
	   
		<div class="form-row">
			<div class="form-group col-10">
				<label for="editor_admin"><strong>상품 페이지 내용</strong></label>
				<textarea id="editor_admin"></textarea>
		    </div>
		    <input type="hidden" name="product_name_detail"/>
		</div>	
		   
		<div id="instore">
			<div class="form-row">
				<div class="form-group col-4">
					<label for="product_name_instockdate"><strong>입고(예정)일 </strong></label>
					<input type="date" class="form-control"  name="date">
		   		</div>   	
		   	</div>
		   		
		  	<div class="form-row">
			    <div class="form-group col-4">
					<label for="productID"><strong>등록코드</strong></label>
					<input type="text" class="form-control" name="productID" id="productID" placeholder="ex>RDBSW0000(알파벳으로만 입력)">				    </div>	
			
			    <div class="form-group col-4">
					<label for="unitcost"><strong>판매단가</strong></label>
					<input type="number" class="form-control" name="unitcost" id="unitcost" value="0" placeholder="실제로 판매할 단위금액 입력">	    
			   	</div>	
			   
				<div class="form-group col-3">
			     	<label for="promotioncode"><strong>추가 할인 가능 여부</strong></label>
			      	<div class="custom-control custom-radio">
				  		<input type="radio"  value="1" id="dicountable" name="promotioncode" class="custom-control-input" checked>
				  		<label class="custom-control-label" for="dicountable">할인 가능</label>
				  	</div>
				  	<div class="custom-control custom-radio">
				    	<input type="radio"  value="0" id="nondiscountable" name="promotioncode" class="custom-control-input">
				    	<label class="custom-control-label" for="nondiscountable">할인 불가</label>
				  	</div>
			    </div>	        			    		        
		   	</div>	
		</div>   	   	
        	   
		<div class="form-row">
			<div class="form-group col" align="center">      
				<input type="button" class="btn btn-outline-dark" value="상품 등록" id="productUploadBtn">
				<input type="button" id="uploadReset" class="btn btn-outline-dark" value="다시작성">
				<input type="button" id="productReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
			</div>
		</div>	

	</form>				

</div>
	

	<!--JavaScript Local LINK:START-->	 
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/ckeditor4Admin.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/admin.product.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/productUpload.js"></script>
	<!--JavaScript Local LINK:END-->