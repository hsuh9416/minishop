<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
	<!--Custom styles-->
	<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">

	

	<div class="col-lg-8">
		<input type="hidden" id="pg" value="${pg}">	
		 <div class="row" id="titleDiv">
		 	<div class="col" align="center" style="padding-bottom: 20px;">
				<h3>상품 수정</h3>
			</div>	
		</div>
	<form name="modifyForm" id="modifyForm" method="post" enctype="multipart/form-data" action="/minishop/admin/product/productModify.do">
	   <input type="hidden" name="pg" id="pg" value="${pg}">
	   <input type="hidden" id="product_name_no" name="product_name_no" value="${productDTO.product_name_no}">  
	   <input type="hidden" name="product_name_image" id="product_name_image" value="${productDTO.product_name_image}">			   		      
	   <div class="form-row">
		    <div class="form-group col-5">
		      <label for="productName"><strong>상품명</strong></label>
		      <input type="text" class="form-control" name="productName" id="productName" value="${productDTO.productName}">
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
		      <input type="number" class="form-control" name="product_name_price" id="product_name_price" value="${productDTO.product_name_price}">
		    </div>
			<div class="form-group col-5">
		      <label for="product_name_image"><strong>섬네일 이미지 업로드</strong></label>
		      <input type="file" class="form-control-file" name="product_image" id="product_name_image">
		    </div>	 	    
	   </div>	   
	   <div class="form-row">
		    <div class="form-group col-8">
		      <label for="product_name_title"><strong>상품 페이지 제목</strong></label>
		      <input type="text" class="form-control" name="product_name_title" id="product_name_title" value="${productDTO.product_name_title}">
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
		    	<label for="product_name_detail"><strong>상품 페이지 내용</strong></label>
		      <textarea id="product_name_detail">${productDTO.product_name_detail}</textarea>
		    </div>
		    <input type="hidden" name="product_name_detail"/>
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
			      <input type="text" class="form-control-plaintext" name="productID" id="productID" value="${productDTO.productID}" readonly>
			    </div>	
			    <div class="form-group col-4">
			     <label for="unitcost"><strong>판매단가</strong></label>
				  <input type="number" class="form-control" name="unitcost" id="unitcost" value="${productDTO.unitcost}" placeholder="실제로 판매할 단위금액 입력">	    
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
<script type="text/javascript" src="/minishop/resources/ckeditor4/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">
var content_detail='';//product_name_detail 저장할 경로
$('#modifyReset').click(function(){
	window.location.reload();
});
$(function(){
	$('input[type=radio][name=product_onstore][value=${productDTO.product_onstore}]').prop('checked',true);
	$('#product_category_no').val('${productDTO.product_category_no}');
	$('input[type=radio][name=promotioncode][value=${productDTO.promotioncode}]').prop('checked',true);
	if('${productDTO.product_onstore==YES}'){
		$('#instore').show();
	}else{
		$('#instore').hide();
	}
	
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
    CKEDITOR.instances.product_name_detail.on('instanceReady', function() { 
    	content_detail = CKEDITOR.instances.product_name_detail.getData();
    });
     
 	$('input[type=radio][name=product_onstore]').change(function(){
 		//alert($('input[type=radio][name=onstore]:checked').val());
 		if($('input[type=radio][name=product_onstore]:checked').val()=='YES'){
 			$('#instore').show();
 			$('#unitcost').val('${productDTO.unitcost}');
 		}
 		else{
 			$('#instore').hide();
 			$('#unitcost').val('0');}
 	});
 	

});


$('#productModifyBtn').click(function(event){

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
			 if($('#unitcost').val()=='') {
				alert('판매가격이 입력되지 않았습니다.');
				$('#unitcost').focus();	
			}
			else{
				$('#modifyForm').submit();
			}
		}else{
			$('#modifyForm').submit();
		}

		
		}
	});

	
</script>
