<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">

<div class="col-lg-8">
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>장바구니</h3>		
	 		</div>
		</div>
		<div class="table">
		<form id="cartListForm" method="post" action="/minishop/trading/removeCart.do">		
			<table id="cartTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col"><input type="checkbox" class="form-control" id="checkAll"></th>			    
					<th scope="col">상품이미지</th>				
					<th scope="col">상품명</th>
					<th scope="col">판매단가</th>						
					<th scope="col">수량</th>										
					<th scope="col">합계</th>					
			  </tr>
			   </thead>  
			   <tbody>
			   		<tr>
			   		<th scope="row"></th>
			   		<td colspan="6"></td>
			   		</tr>	
			   </tbody> 	  
			</table>
			</form>
		</div>
<div style="float : left;">
	<input type="button" class="btn btn-outline-secondary" value="선택삭제" id="choiceDelete">
</div>
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/trading/getCartList.do',
		dataType : 'json',
		success : function(data){
			
			var size = Object.keys(data.cartList).length;
			//alert(size);
			$('#cartTable tr:gt(0)').empty();
			if(size>0){
			$.each(data.cartList, function(index, items){
				$('<tr/>').append($('<td/>',{
					align: 'center',
					html : items.product_name_no,
				}).prepend($('<input/>',{
					type: 'checkbox',
					value: items.product_name_no,
					name : 'check',
					class: 'check'
			}))).append($('<td/>',{
					align : 'center'
				}).append($('<img/>',{
					src:  '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
					width: '100',
					height: '100',
					id : 'imageA'
				}))).append($('<td/>',{
					align : 'center',
					html : items.productName				
				})).append($('<td/>',{
					align : 'center',
					html : items.unitcost+'(원)'					
				})).append($('<td/>',{
					align : 'center',
					html : items.cart_qty+'(개)'
				})).append($('<td/>',{
					align : 'center',
					html : (items.unitcost*items.cart_qty)+'(원)'				
				})).appendTo($('#cartTable tbody'));
				
			});//each
			$('#cartTable').on('click','#imageA',function(){
				if('${memberDTO.id}'=='') alert("먼저 로그인하세요");
				else{
					var product_name_no = $(this).parent().prev().text();
					window.location='/minishop/imageboard/productView.do?product_name_no='+product_name_no;
				}//else
			});//제목 클릭시!
			}//if
			else{
				$('<tr/>').append($('<td/>',{
					colspan : '6',
					align : 'center',
					text : '장바구니에 담긴 상품이 없습니다.',
				})).appendTo($('#cartTable tbody'));		
			}
			
		}//success
	});

	
});//onready
//전체 선택, 해제
$('#checkAll').click(function(){
	//alert($('.check').length);
	
	if($('#checkAll').prop('checked')){
		$('.check').prop('checked', true);
	}else{
		$('.check').prop('checked', false);
	}
});

//선택 삭제
$('#choiceDelete').click(function(){
	if($('.check:checked').length==0) alert("항목을 선택해주세요");
	else{
		$('#cartListForm').submit();
	}//else
});
</script>
