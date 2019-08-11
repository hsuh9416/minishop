<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>장바구니</h3>		
		</div>
	</div>
				<div class="form-row align-items-center">
					<div class="col-1">
				        <input class="form-check-input" type="checkbox" id="checkAll" style="width:10px;height:10px;">
				        <label class="form-check-label" for="checkAll">
				          	#
				        </label>					
					</div>
					<div class="col-2" align="center">상품이미지</div>
					<div class="col-2" align="center">상품명</div>					
					<div class="col-2" align="center">판매단가</div>
					<div class="col-3" align="center">수량</div>
					<div class="col-2" align="center">합계</div>															
				</div>
				<hr width="100%" color="darkgray" noshade/>
			<form id="cartListForm" method="post" action="/minishop/trading/removeCart.do"></form>
			<form id="cartOrderForm" method="post" action="minishop/trading/orderForm.do">
				<input type="hidden" name="product_name_no" value=""/>
				<input type="hidden" name="cart_qty" value="0"/>
			</form>
			<hr width="100%" color="darkgray" noshade/>
	<div class="form-row align-items-center">
		<div class="col" align="right" id="totalDiv"></div>
	</div>		
	<div class="form-row align-items-center">
		<div class="col-2">
			<input type="button" class="btn btn-outline-secondary" value="삭제하기" id="choiceDelete">	
		</div>
		<div class="col-6"></div>
		<div class="col-2">
			<input type="button" class="btn btn-outline-primary" value="계속 쇼핑하기" id="goCategory">	
		</div>
		<div class="col-2">
			<input type="button" class="btn btn-outline-info" value="주문하기" id="choiceOrder">	
		</div>
	</div>
	
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.product.js"></script>
<script type="text/javascript">
var cartTotal = 0;
var totalCost = '';
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');
}
$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/trading/getCartList.do',
		dataType : 'json',
		success : function(data){
			
			var size = Object.keys(data.cartList).length;

			if(size>0){
			$.each(data.cartList, function(index, items){			
				var unitcost_format=formatNumber(items.unitcost);
				var totalCost=items.unitcost*items.cart_qty;
				cartTotal=cartTotal+totalCost;//합계 정산
				var totalCost_format=formatNumber(totalCost);
				$('<div/>',{
					class: 'form-row align-items-center'			
				}).append($('<div/>',{//col-1
					class : 'col-1',
					align : 'center'
				}).append($('<input/>',{
					class : 'form-check-input check',
					type : 'checkbox',
					name : 'check',
					value : items.product_name_no
				})).append($('<label/>',{
					class: 'form-check-label',
					html : items.product_name_no
				}))).append($('<div/>',{//col-2
					class : 'col-2',
					align : 'center'
				}).append($('<a/>',{
					href : '/minishop/product/productView.do?product_name_no='+items.product_name_no
				}).append($('<img/>',{
					src : '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
					style : 'width:80px;height:80px;'
				})))).append($('<div/>',{//col-3
					class : 'col-2',
					align : 'center',
					html : items.productName
				})).append($('<input/>',{
					type : 'hidden',
					value : items.unitcost
				})).append($('<div/>',{//col-4
					class : 'col-2',
					align : 'center',
					html : unitcost_format+'원'
				})).append($('<div/>',{//col-5
					class : 'col-3',
					align : 'right',
					style : 'display:inline-block;padding:0;float:right;'
				}).append($('<input/>',{
					type : 'hidden',
					value : items.stock
				})).append($('<input/>',{
					type : 'hidden',
					value : items.cart_qty
				})).append($('<input/>',{
					type : 'number',
					value : items.cart_qty,
					id : 'changeQty',
					style : 'width:40px;height:30px;padding:0;border:none;'
				})).append('<span>개</span>').append($('<input/>',{
					type : 'button',
					class : 'btn btn-sm btn-info',
					value : '반영',
					id : 'modifyBtn'
				}))).append($('<div/>',{//col-6
					class : 'col-2',
					align : 'center',
					html : totalCost_format+'원'
				})).appendTo($('#cartListForm'));
			});
			$('#cartListForm').on('change','#changeQty',function(){
				var originalQty = $(this).prev().val();//기준 가격
				var newQty = $(this).val();//변동한 가격
				var stock = $(this).prev().prev().val();
				var leftStock = stock-newQty;
				//alert(leftStock);
				//유효성 검사
				if(newQty<1){
					alert('최소 1개 이상으로 수량을 선택해주세요');
					$(this).val(1);
					return;
				}else if(newQty>99){
					alert('100개 이상은 주문하실 수 없습니다');
					$(this).val(originalQty);
					return;
				}else if(leftStock<0){
					alert('재고 이상은 주문하실 수 없습니다[현재 재고: '+stock+'(개)]');
					$(this).val(originalQty);
					return;					
				}
				var unicost = $(this).parent().prev().prev().val();//단가 가져오기
				var diff = (newQty-originalQty)*unicost;
				var newTotalcost = newQty*unicost;
				var newTotalcost_format = formatNumber(newTotalcost);
				
				$(this).parent().next().text(newTotalcost_format+'원');//반영
				//합계 반영
				cartTotal=cartTotal+diff;
				totalCost = formatNumber(cartTotal);
				$('#totalDiv').text('총 '+totalCost+' 원');
				//기준 가격 조정
				$(this).prev().val(newQty); 
				
			});
			$('#cartListForm').on('click','#modifyBtn',function(){
				var cart_qty = $(this).prev().prev().val();//변동한 가격
				var unicost = $(this).parent().prev().prev().val();//단가 가져오기
				var product_name_no = $(this).parent().prev().prev().prev().prev().prev().children().prop('value');
				var doModify = confirm('해당 재고의 수량을 확정적으로 반영하시겠습니까?');
				if(doModify){
					window.location='/minishop/trading/modifyCart.do?product_name_no='+product_name_no+'&changeNum='+cart_qty;
				}
			});
		}//if
			else{
				$('#cartListForm').append('<div class="form-row align-items-center"><div class="col" align="center"><strong style="color:red;font-size:20px;">장바구니에 담긴 상품이 없습니다.</strong></div></div>');		
			}
			//합계 정산
			totalCost = formatNumber(cartTotal);
			$('#totalDiv').text('총 '+totalCost+' 원');
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
$('#goCategory').click(function(){
	window.location='/minishop/product/categories.do'
});
//선택 주문
$('#choiceOrder').click(function(){
	if($('.check:checked').length==0) alert("항목을 선택해주세요");
	else{
		var product_name_no = new Array();
		$(".check:checked").each(function(index, items) { 
			product_name_no[index] = $(this).val(); 
		});

		$('input[name=product_name_no]').val(product_name_no);	
		$('#cartOrderForm').submit();
	}//else
});

</script>
