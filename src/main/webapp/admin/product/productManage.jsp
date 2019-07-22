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
 	<!-- 실행 메뉴 -->
		 <nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item active"  aria-current="page">상품 등록 관리</li>
			<li class="breadcrumb-item"><a href="/mallproject/admin/product/productUpload.do">개별 상품 등록</a></li>				    	    
		  </ol>
		</nav>	
	</div>
</div>
<div class="container-fluid">
		<input type="hidden" id="pg" value="${pg}">
		<div class="table-responsive">
			<table id="productTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">#</th>			    
					<th scope="col">상품이미지</th>	
					<th scope="col">등록코드</th>
					<th scope="col">상품분류</th>					
					<th scope="col">상품명</th>	
					<th scope="col">할인가능여부</th>										
					<th scope="col">상품등록여부</th>					
			  </tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<th scope="row"></th>
			   		<td colspan="7"></td>			   		
			   	</tr>
			   </tbody> 	  
			</table>
		</div>
</div>

<div class="container-fluid">
		<nav aria-label="Page navigation">
		  <ul class="pagination justify-content-center" id="productPagingDiv"></ul>
		</nav>
</div>
<br><br>

 
<div class="container-fluid">
	<form id="productSearch" name="productSearch">
		<div class="form-row justify-content-center">
		   <span>
			<input type="hidden" name="pg" id="pg" value="1">
			</span>
			<span style="margin-left:20px;">
			<select name="searchOption" id="searchOption" class="form-control">
		        <option value="productid">등록코드</option>			
		        <option value="product_name_no">상품코드</option>
				<option value="productname">상품명</option>
		    </select>
		    </span>
		    <span style="margin-left:20px;">
		    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}" size="20">
		    </span>
		   <span style="margin-left:20px;">
		    <input type="button" id="productSearchBtn" class="btn btn-outline-dark" value="검색">
		   </span>
		   <span style="margin-left:20px;">
		    <input type="button" id="goUpload" class="btn btn-outline-dark" value="상품 등록">
		   </span>	   
		</div>
  	</form>
</div>  

<div><input type="hidden" class="margin_down"></div>	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mapplroject/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/mallproject/admin/product/productList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			$('#productTable tr:gt(0)').empty();
			
			$.each(data.productList, function(index, items){
				var discountable = '[할인불가상품]';
				var isOnMall = '[미등재]';
				if(items.promotioncode==1){
					discountable = '[할인가능상품]';}
				if(items.product_onstore=='YES'){
					isOnMall = '[등재]';}	
				$('<tr/>').append($('<a/>',{
					align: 'center',
					id : 'checkA',
					}).prepend($('<i/>',{
						text : items.product_name_no,
						id : 'deleteA',
						class: 'fas fa-trash-alt'
				}))).append($('<td/>',{
					align: 'center'
				}).append($('<img/>',{
					src: '/mallproject/storage/'+items.product_name_image,
					width: '100',
					height: '100',
					id : 'imageA'
				}))).append($('<td/>',{
					align : 'center',
					html : items.productID				
				})).append($('<td/>',{
					align : 'center',
					html : items.product_category_name				
				})).append($('<td/>',{
					align : 'center',
					html : items.productName				
				})).append($('<td/>',{
					align : 'center',
					html : discountable				
				})).append($('<td/>',{
					align : 'center',
					html : isOnMall,
					id : 'onMall'					
				})).appendTo($('#productTable tbody'));
			});//each
			
			$('#productPagingDiv').html(data.productPaging.pagingHTML);
			
			$('#productTable').on('click','#imageA',function(){
					var product_name_no = $(this).parent().prev().text();
					window.location='/mallproject/admin/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
			});//제목 클릭시!
			

			$('#productTable').on('click','#checkA',function(){
				var onShop = $(this).next().next().next().next().next().next().text();
				if(onShop=='[등재]') alert('현재 매장에 등록되어 있는 상품은 삭제하실 수 없습니다. 등록 해제를 하신 후 재시도해주세요.');
				else {
					var result =confirm('해당 상품을 정말로 삭제하시겠습니까? 삭제시에는 재고관리 목록에서도 제거됩니다.');
					if(result){
						window.location='/mallproject/admin/product/productDelete.do?product_name_no='+$(this).text();					
					}

				}

			});//선택 삭제
			
		}//success
	});//ajax
});//onready

function productPaging(pg){
	location.href='/mallproject/admin/product/productManage.do?pg='+pg;
}

function productSearchPaging(pg){

	$('input[name=pg]').val(pg);
	$('#productSearchBtn').trigger('click','trigger');
}

$('#productSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else
		$.ajax({
			type : 'post',
			url : '/mallproject/admin/product/productSearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption option:selected').val(),
				   'keyword':$('#keyword').val()},
			dataType : 'json',
			success : function(data){
				$('#productTable tr:gt(0)').empty();
				
				$.each(data.productSearchList, function(index, items){
					var discountable = '[할인불가상품]';
					var isOnMall = '[미등재]';
					if(items.promotioncode==1){
						discountable = '[할인가능상품]';}
					if(items.product_onstore=='YES'){
						isOnMall = '[등재]';}	
					$('<tr/>').append($('<a/>',{
						align: 'center',
						id : 'checkA',
						}).prepend($('<i/>',{
							text : items.product_name_no,
							id : 'deleteA',
							class: 'fas fa-trash-alt'
					}))).append($('<td/>',{
						align: 'center'
					}).append($('<img/>',{
						src: '/mallproject/storage/'+items.product_name_image,
						width: '100',
						height: '100',
						id : 'imageA'
					}))).append($('<td/>',{
						align : 'center',
						html : items.productID				
					})).append($('<td/>',{
						align : 'center',
						html : items.product_category_name				
					})).append($('<td/>',{
						align : 'center',
						html : items.productName				
					})).append($('<td/>',{
						align : 'center',
						html : discountable				
					})).append($('<td/>',{
						align : 'center',
						html : isOnMall,
						id : 'onMall'					
					})).appendTo($('#productTable tbody'));
				});//each
				
				$('#productPagingDiv').html(data.productPaging.pagingHTML);
				
				$('#productTable').on('click','#imageA',function(){
						var product_name_no = $(this).parent().prev().text();
						window.location='/mallproject/admin/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
				});//제목 클릭시!
				
				$('#productTable').on('click','#checkA',function(){
					var onShop = $(this).next().next().next().next().next().next().text();
					if(onShop=='[등재]') alert('현재 매장에 등록되어 있는 상품은 삭제하실 수 없습니다. 등록 해제를 하신 후 재시도해주세요.');
					else {
						var result =confirm('해당 상품을 정말로 삭제하시겠습니까? 삭제시에는 재고관리 목록에서도 제거됩니다.');
						if(result){
							window.location='/mallproject/admin/product/productDelete.do?product_name_no='+$(this).text();					
						}

					}

				});//선택 삭제
				
			}//success
		});//에이작스	
});

$('#goUpload').click(function(){
	window.load='/mallproject/admin/product/productUpload.do';
});
</script>
