<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--Bootsrap 4-->
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	
    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="/mallproject/resources/fontawesome-free-5.9.0-web/css/all.css">	
	
	<!-- ckeditor4 CDN -->
		
	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="/mallproject/css/userboard.css">  
		<!-- 리뷰 글쓰기 폼 -->	  
 <div class="reviewForm-container">
 	<div class="container">
 	<!-- 실행 메뉴 -->
	 <nav aria-label="breadcrumb">
	  <ol class="breadcrumb">
	    <li class="breadcrumb-item active" aria-current="page">상품 등록</li>       	    
	  </ol>
	</nav>
	<form name="productUploadForm" id="productUploadForm" method="post" enctype="multipart/form-data">
	   <div class="form-row">
		    <div class="form-group col-md-3">
		      <label for="productName"><strong>상품명</strong></label>
		      <input type="text" class="form-control" name="productName" id="productName" placeholder="상품명 입력">
		    </div>
		    <div class="form-group col-md-3">
		     <label for="product_category_no"><strong>상품분류</strong></label>
		      <select id="product_category_no" name="product_category_no" class="form-control">
				<option value="1">Women</option>  
				<option value="2">Men</option> 
				<option value="3">Accessories</option> 								 
		      </select>		    
		    </div>    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-3">
		      <label for="productName"><strong>상품예정가격</strong></label>
		      <input type="number" class="form-control" name="product_name_price" id="product_name_price" placeholder="ex>2000(단가,단위 생략)">
		    </div>
			<div class="form-group col-md-8">
		      <label for="product_name_image"><strong>섬네일 이미지 업로드</strong></label>
		      <input type="file" class="form-control-file" name="product_name_image" id="product_name_image">
		    </div>	 	    
	   </div>	   
	   <div class="form-row">
		    <div class="form-group col-md-7">
		      <label for="product_name_title"><strong>상품 페이지 제목</strong></label>
		      <input type="text" class="form-control" name="product_name_title" id="product_name_title" placeholder="제목 입력">
		    </div>	
		    <div class="form-group col-md-4">
		        <input class="form-check-input" type="checkbox" name="product_onstore" id="product_onstore" value="1">
  				<span style="margin-left:20px;">바로 입점</span>
		    </div>		        
	   </div>	
	   <div class="form-row">
		    <div class="form-group col-md-12">
		      <textarea id="product_name_detail"></textarea>
		    </div>
	   </div>	   	   	
	   <div class="form-row">
		    <div class="form-group col-md-4">
		      <label for="productID"><strong>등록코드</strong></label>
		      <input type="text" readonly class="form-control" name="productID" id="productID" placeholder="ex>RDBSW0000(알파벳으로만 입력)">
		    </div>	
		    <div class="form-group col-md-4">
		     <label for="product_name_price"><strong>판매단가</strong></label>
			  <input type="number" readonly class="form-control" name="unitcost" id="unitcost" placeholder="실제로 판매할 단위금액 입력">	    
		    </div>	
		    <div class="form-group col-md-3">
		     <label for="promotioncode"><strong>할인정책</strong></label>	
				<div class="form-check form-check-inline">
			        <input class="form-check-input" type="checkbox" name="promotioncode" id="promotioncode" value="1" disabled>
	  				<span style="margin-left:20px;">할인 허용</span>
				</div>			      
		    </div>			        
	   </div>	      	   
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">      
					<input type="button" class="btn btn-outline-dark" value="상품 등록" id="productUploadBtn">
					<input type="button" id="uploadReset" class="btn btn-outline-dark" value="다시작성">
					<input type="button" id="uploadReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
				</div>
			</div>	
		</div>	
		</form>				
 	</div> 
 </div>
<div id="missing"></div>		


<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"src="/mallproject/resources/ckeditor_4.12.1_full/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/mallproject/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	//에디터 설정
	var content = CKEDITOR.replace('product_name_detail',{
		customConfig : '/mallproject/resources/ckeditor_4.12.1_full/ckeditor/config.js'
	});
	//	
});//ready
	$('#productUploadBtn').click(function(event){
		event.preventDefault();
		
		var form = $('#productUploadForm')[0];
		
		var data = new FormData(form);
		
		data.append('product_name_detail',CKEDITOR.instances.product_name_detail.getData());//별도로 담기
		
		//alert(form);html form element
		$.ajax({
			type :'post',
			enctype : 'multipart/form-data',
			url : '/mallproject/admin/product/doUpload.do',
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(){
				console.log('파일이 성공적으로 전송되었습니다.');
			}
		});
	});
	$('#product_onstore').change(function(){
		if($('#product_onstore').prop('checked',true)){
			$('#productID').prop('readonly',false);
			$('#unitcost').prop('readonly',false);
			$('.form-check-input').prop('disabled',false);			
		}
		else{
			$('#productID').prop('readonly',true);
			$('#unitcost').prop('readonly',true);
			$('#promotioncode').prop('disabled',true);				
		}		
	});
</script>
