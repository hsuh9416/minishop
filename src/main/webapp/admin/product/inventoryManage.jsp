<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<!--Custom styles-->
	<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	
	<div class="col-lg-8">
		 <div class="row" id="titleDiv">
		 	<div class="col" align="center" style="padding-bottom: 20px;">
				<h3>입점상품현황</h3>
			</div>	
		</div>
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
		<div class="row">
			<div class="col-12" align="left">
				<a id="reloadIcon"><i class="fas fa-retweet">새로고침</i></a>
			</div>
		</div>	
		<div class="row">
			<div class="col-12" align="center">
				<nav aria-label="Page navigation example">
			  		<ul class="pagination justify-content-center" id="productPagingDiv"></ul>
				</nav>			
			</div>
		</div>
			
		<form id="inventorySearch" name="inventorySearch">	
			<input type="hidden" name="pg" id="pg" value="1">					
			<div class="form-row justify-content-center" style="padding-top: 20px;padding-left:20px;">
				<div class="form-group col-2">
					<select name="searchOption" id="searchOption" class="form-control">
						<option value="productid">등록코드</option>
						<option value="productname">상품명</option>
				        <option value="product_name">상품코드</option>
				    </select>			
				</div>
				<div class="form-group col-4">
				    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}" size="20">		
				</div>
				<div class="form-group col-2">
				    <input type="button" id="inventorySearchBtn" class="btn btn-outline-dark" value="검색">		
				</div>
			</div>				
		</form>		
			
	</div>
<div><input type="hidden" class="margin_down"></div>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/admin/product/inventoryList.do',
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
					var loginPop = window.open('/minishop/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val(),'재고수정','width=450,height=350,resizable=no');
					//window.location='/minishop/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val();
			});//제목 클릭시!
		}//success
	});//ajax
});//onready

function productPaging(pg){
	location.href='/minishop/admin/product/inventoryManage.do?pg='+pg;
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
			url : '/minishop/admin/product/inventorySearch.do',
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
						var loginPop = window.open('/minishop/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val(),'재고수정','width=450,height=350,resizable=no');
						//window.location='/minishop/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val();
				});//제목 클릭시!
			}//success
		});//에이작스	
});
$('#reloadIcon').click(function(){
	window.location.reload();
});
</script>
