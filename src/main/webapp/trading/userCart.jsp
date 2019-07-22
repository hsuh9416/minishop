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
		    <li class="breadcrumb-item active"  aria-current="page">장바구니</li>			    	    
		  </ol>
		</nav>	
	</div>
</div>
<div class="container-fluid">
		<input type="hidden" id="pg" value="${pg}">
		<div class="table-responsive">
		<form id="cartListForm" method="post" action="/mallproject/trading/removeCart.do">		
			<table id="cartTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col"><input type="checkbox" class="form-check-input" id="checkAll">#</th>			    
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
			   			<td colspan="7"></td>			   		
			   	</tr>
			   </tbody> 	  
			</table>
			</form>
		</div>
</div>
<br><br>
<div style="float : left;">
	<input type="button" value="선택삭제" id="choiceDelete">
</div>


<div><input type="hidden" class="margin_down"></div>	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mapplroject/js/admin.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/mallproject/admin/trading/cartList.do',
		dataType : 'json',
		success : function(data){
			$('#cartTable tr:gt(0)').empty();
			
			$.each(data.productList, function(index, items){
				if(data.productList.size()<1||data.productList==null)
				
				else{
					var subTotal = items.productDTO.unitcost*items.cart_qty;
				}
				$('<tr/>').append($('<td/>',{
					align: 'center',
					text : items.productDTO.productID
					}).prepend($('<input/>',{
						type: 'checkbox',
						value: items.productDTO.productID,
						name : 'check',
						class: 'check'
				}))).append($('<td/>',{
					align: 'center'
				}).append($('<img/>',{
					src: '/mallproject/storage/'+items.productDTO.product_name_image,
					width: '100',
					height: '100',
					id : 'imageA'
				}))).append($('<td/>',{
					align : 'center',
					html : items.productDTO.productName			
				})).append($('<td/>',{
					align : 'center',
					html : items.productDTO.unitcost				
				})).append($('<td/>',{
					align : 'center',
					html : subTotal			
				})).appendTo($('#cartTable tbody'));
			});//each
				
			$('#cartTable').on('click','#imageA',function(){
					var product_name_no = $(this).parent().prev().text();
					window.location='/mallproject/admin/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
			});//제목 클릭시!
			

			$('#checkAll').click(function(){
				
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
			
		}//success
	});//ajax
});//onready

</script>
