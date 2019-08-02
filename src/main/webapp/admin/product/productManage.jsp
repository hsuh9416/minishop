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
				<h3>등록상품현황</h3>
			</div>	
		</div>
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
		<div class="row">
			<div class="col-12" align="center">
				<nav aria-label="Page navigation example">
			  		<ul class="pagination justify-content-center" id="productPagingDiv"></ul>
				</nav>			
			</div>
		</div>

		<form id="inventorySearch" name="inventorySearch">	
			<input type="hidden" name="pg" value="1">					
			<div class="form-row justify-content-center" style="padding-top: 20px;padding-left:20px;">
				<div class="form-group col-2">
					<select name="searchOption" id="searchOption" class="form-control">		
				        <option value="productid">등록코드</option>					
				        <option value="product_name_no">상품코드</option>
						<option value="productname">상품명</option>
				    </select>	
						</div>
				<div class="form-group col-4">
				    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}" size="20">		
				</div>
				<div class="form-group col-4">
					<input type="button" id="productSearchBtn" class="btn btn-outline-dark" value="검색">		    	
				</div>
			</div>				
		</form>	
	</div>	 	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/admin/product/productList.do',
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
					src: '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
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
					window.location='/minishop/admin/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
			});//제목 클릭시!
			

			$('#productTable').on('click','#checkA',function(){
				var onShop = $(this).next().next().next().next().next().next().text();
				if(onShop=='[등재]') alert('현재 매장에 등록되어 있는 상품은 삭제하실 수 없습니다. 등록 해제를 하신 후 재시도해주세요.');
				else {
					var result =confirm('해당 상품을 정말로 삭제하시겠습니까? 삭제시에는 재고관리 목록에서도 제거됩니다.');
					if(result){
						window.location='/minishop/admin/product/productDelete.do?product_name_no='+$(this).text();					
					}

				}

			});//선택 삭제
			
		}//success
	});//ajax
});//onready

function productPaging(pg){
	location.href='/minishop/admin/product/productManage.do?pg='+pg;
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
			url : '/minishop/admin/product/productSearch.do',
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
						src:  '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
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
						window.location='/minishop/admin/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
				});//제목 클릭시!
				
				$('#productTable').on('click','#checkA',function(){
					var onShop = $(this).next().next().next().next().next().next().text();
					if(onShop=='[등재]') alert('현재 매장에 등록되어 있는 상품은 삭제하실 수 없습니다. 등록 해제를 하신 후 재시도해주세요.');
					else {
						var result =confirm('해당 상품을 정말로 삭제하시겠습니까? 삭제시에는 재고관리 목록에서도 제거됩니다.');
						if(result){
							window.location='/minishop/admin/product/productDelete.do?product_name_no='+$(this).text();					
						}

					}

				});//선택 삭제
				
			}//success
		});//에이작스	
});

</script>
