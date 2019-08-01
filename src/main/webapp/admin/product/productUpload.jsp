<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--Custom styles-->
	<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">

	

	<div class="col-lg-8">
		<input type="hidden" id="pg" value="${pg}">	
		 <div class="row" id="titleDiv">
		 	<div class="col" align="center" style="padding-bottom: 20px;">
				<h3>상품등록</h3>
			</div>	
		</div>
	<form name="uploadForm" id="uploadForm" method="post" enctype="multipart/form-data" action="/minishop/admin/product/doUpload.do">
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
		      <label for="productName"><strong>상품예정가격</strong></label>
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
		      <label for="onstore"><strong>입점 여부</strong></label>
		      <div class="custom-control custom-radio">
			  	<input type="radio"  value="YES" id="inofstore" name="product_onstore" class="custom-control-input">
			  	<label class="custom-control-label" for="inofstore">즉시 입점</label>
			  </div>
			  <div class="custom-control custom-radio">
			    <input type="radio"  value="NO" id="outofstore" name="product_onstore" class="custom-control-input" checked>
			    <label class="custom-control-label" for="outofstore">입점 안함</label>
			  </div>
		    </div>	        
	   </div>	
	   <div class="form-row">
		    <div class="form-group col-10">
		    	<label for="product_name_title"><strong>상품 페이지 내용</strong></label>
		      <textarea id="product_name_detail"></textarea>
		    </div>
		    <input type="hidden" name="product_name_detail"/>
	   </div>	   
		<div id="instore">
		   <div class="form-row">
		   	<div class="form-group col-4">
		   		<label for="product_name_instockdate"><strong>입고예정일 </strong></label>
		   		  <input type="date" class="form-control"  name="date">
		   	</div>   	
		   </div>
		   <div class="form-row">
			    <div class="form-group col-4">
			      <label for="productID"><strong>등록코드</strong></label>
			      <input type="text" class="form-control" name="productID" id="productID" placeholder="ex>RDBSW0000(알파벳으로만 입력)">
			    </div>	
			    <div class="form-group col-4">
			     <label for="product_name_price"><strong>판매단가</strong></label>
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
 		<div id="missing"></div>	
 </div>
	


<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript"src="/minishop/resources/ckeditor4/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">
//serializeArray가 수행된 form을 JSON 형태로 변형시켜준다.
var content_detail='';//product_name_detail 저장할 경로
$(function(){
	$('#instore').hide();
    CKEDITOR.replace( 'product_name_detail', {//해당 이름으로 된 textarea에 에디터를 적용
        width:'100%',
        height:'300px',
        filebrowserImageUploadUrl: '/minishop/admin/product/productImgUpload.do', //여기 경로로 파일을 전달하여 업로드 시킨다.
        
    });
           
    CKEDITOR.on('dialogDefinition', function( ev ){
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition;
      
        switch (dialogName) {
            case 'image': //Image Properties dialog
                //dialogDefinition.removeContents('info');
                dialogDefinition.removeContents('Link');
                dialogDefinition.removeContents('advanced');
                break;
        }
    });
    CKEDITOR.instances.product_name_detail.on('change', function() { 
    	content_detail = CKEDITOR.instances.product_name_detail.getData();
    });
    
 	$('input[type=radio][name=product_onstore]').change(function(){
 		//alert($('input[type=radio][name=onstore]:checked').val());
 		if($('input[type=radio][name=product_onstore]:checked').val()=='YES'){
 			$('#instore').show();
 			$('#unitcost').val('0');
 		}
 		else{
 			$('#instore').hide();
 			$('#unitcost').val('');}
 	});
});


$('#productUploadBtn').click(function(event){
		/*JSON 파일 방식: 향후 다시 시도->현시점에는 이중 submit 형태로 시도하도록 함
		//file과 product_name_detail 변수는 별도 선정해주어야 함:push로 추가
		var product_name_detail = CKEDITOR.instances.product_name_detail.getData();
		var form = $('#uploadForm').serializeArray();
		var file = $('#product_name_image').val();
		form.push({"name":"product_name_image","value":file});
		form.push({"name":"product_name_detail","value":product_name_detail});
		
		var formData = toJson(form);
		*/
		//ckeditor 내용 최종 저장
		$('input[name=product_name_detail]').val(content_detail);
		//유효성 검사
		if($('#productName').val()=='') {
			alert('이름이 입력되지 않았습니다.');
			$('#productName').focus();
		}
		else if($('#product_name_price').val()=='')	{
			alert('가격이 입력되지 않았습니다.');
			$('#product_name_price').focus();			
		}
		else if($('#product_name_image').val()=='')	{
			alert('사진이 업로드되지 않았습니다.');
			$('#product_name_image').focus();	
		}
		else if($('#product_name_title').val()=='')	{
			alert('상품 소개 제목이 입력되지 않았습니다.');
			$('#product_name_title').focus();	
		}
		else if(content_detail=='')	{
			alert('상품 소개 내용이 입력되지 않았습니다.');	
			$('#product_name_detail').focus();	
		}
		else{
		if($('input[name=product_onstore]').val()=='YES'){
			if($('#productID').val()=='') {
				alert('등록코드가 입력되지 않았습니다.');
				$('#productID').focus();	
			}
			else if($('#unitcost').val()=='') {
				alert('판매가격이 입력되지 않았습니다.');
				$('#unitcost').focus();	
			}
			else{
				$('#uploadForm').submit();
			}
		}else{
			$('#uploadForm').submit();
		}

		
		}
	});

	
</script>
