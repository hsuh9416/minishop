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
		    <li class="breadcrumb-item active"  aria-current="page">상품 재고 관리</li>	    
		  </ol>
		</nav>	
	</div>
</div>

<div class="container-fluid">
		<input type="hidden" id="pg" value="${pg}">
		<div class="table-responsive">
			<table id="inventoryTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">상품코드</th>
					<th scope="col">등록코드</th>
					<th scope="col">상품단가</th>
					<th scope="col">재고수량</th>					
					<th scope="col">상품명</th>					
					<th scope="col">입고(예정)일</th>					
			  </tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<th scope="row"></th>
			   			<td colspan="6"></td>			   		
			   	</tr>
			   </tbody> 	  
			</table>
		</div>
</div>
<div class="container-fluid">
	<div class="form-row">
	<div class="justify-content-right">
		<a id="reloadIcon"><i class="fas fa-retweet">새로고침</i></a>
	</div>
	</div>
</div>	
<br><br>
	<div class="container-fluid">
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center" id="productPagingDiv"></ul>
		</nav>
	</div>

<div class="container-fluid">
	<form id="inventorySearch" name="inventorySearch">
		<div class="form-row justify-content-center">
		   <span>
			<input type="hidden" name="pg" id="pg" value="1">
			</span>
			<span style="margin-left:20px;">
			<select name="searchOption" id="searchOption" class="form-control">
				<option value="productid">등록코드</option>
				<option value="productname">상품명</option>
		        <option value="product_name">상품코드</option>
		    </select>
		    </span>
		    <span style="margin-left:20px;">
		    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}" size="20">
		    </span>
		   <span style="margin-left:20px;">
		    <input type="button" id="inventorySearchBtn" class="btn btn-outline-dark" value="검색">
		   </span>
	   
		</div>
  	</form>
</div>  	
	<!-- model personalQAform frame -->
	<jsp:include page="/admin/product/inventoryModify.jsp"/>		
	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mapplroject/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/mallproject/admin/product/inventoryList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			$('#inventoryTable tr:gt(0)').empty();
			
			$.each(data.inventoryList, function(index, items){
				var instockdate = '[미정]';
				var stock ='[재고 없음]';
				if(items.product_name_instockdate!=null){
					instockdate = items.product_name_instockdate;}
				if(items.stock>0){
					stock = items.stock+'';}			
				$('<tr/>').append($('<th/>',{
					scope : 'row',
					align : 'center',
					text : items.product_name_no
				})).append($('<td/>',{
					}).append($('<a/>',{
						href : 'javascript:void(0)',
						id : 'subjectA',
						text : items.productID,
						class : items.product_name_no+''
				}))).append($('<td/>',{
					align : 'center',
					text : items.unitcost
				})).append($('<td/>',{
					align : 'center',
					text : stock				
				})).append($('<td/>',{
					align : 'center',
					text : items.productName					
				})).append($('<td/>',{
					align : 'center',
					html : instockdate				
				})).appendTo($('#inventoryTable tbody'));			
			});//each
			

			$('#productPagingDiv').html(data.productPaging.pagingHTML);
			
			$('#inventoryTable').on('click','#subjectA',function(){
					var productID = $(this).text();
					window.location='/mallproject/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val();
			});//제목 클릭시!
		}//success
	});//ajax
});//onready

function productPaging(pg){
	location.href='/mallproject/admin/product/inventoryManage.do?pg='+pg;
}

function productSearchPaging(pg){

	$('input[name=pg]').val(pg);
	$('#inventorySearchBtn').trigger('click','trigger');
}

$('#inventorySearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else
		$.ajax({
			type : 'post',
			url : '/mallproject/admin/product/inventorySearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption option:selected').val(),
				   'keyword':$('#keyword').val()},
			dataType : 'json',
			success : function(data){
				$('#inventoryTable tr:gt(0)').empty();
				
				$.each(data.inventorySearchList, function(index, items){
					var instockdate = '[미정]';
					var stock ='[재고 없음]';
					if(items.product_name_instockdate!=null){
						instockdate = items.product_name_instockdate;}
					if(items.stock>0){
						stock = items.stock+'';}			
					$('<tr/>').append($('<th/>',{
						scope : 'row',
						align : 'center',
						text : items.product_name_no
					})).append($('<td/>',{
						}).append($('<a/>',{
							href : 'javascript:void(0)',
							id : 'subjectA',
							text : items.productID,
							class : items.product_name_no+''
					}))).append($('<td/>',{
						align : 'center',
						text : items.unitcost
					})).append($('<td/>',{
						align : 'center',
						text : stock				
					})).append($('<td/>',{
						align : 'center',
						text : items.productName					
					})).append($('<td/>',{
						align : 'center',
						html : instockdate				
					})).appendTo($('#inventoryTable tbody'));
				});//each
				
				$('#productPagingDiv').html(data.productPaging.pagingHTML);
				
				$('#inventoryTable').on('click','#subjectA',function(){
						var productID = $(this).text();
						window.location='/mallproject/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val();
				});//제목 클릭시!
			}//success
		});//에이작스	
});
$('#reloadIcon').click(function(){
	window.location.reload();
});
</script>
