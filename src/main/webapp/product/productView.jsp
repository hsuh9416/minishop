<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    

<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
<div class="col-lg-8">	
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>${productDTO.product_category_name}</h3>		
	 		</div>
		</div>
		<input type="hidden" id="SEQ" value="${SEQ}"/>
		<input type="hidden" id="productID" value="${productDTO.productID}"/>
		<input type="hidden" name="product_name_no" id="product_name_no" value="${productDTO.product_name_no}"/>
		<form id="buyNowForm" method="post" action="/minishop/trading/orderForm.do">
			<input type="hidden" name="product_name_no" value=""/>
			<input type="hidden" name="cart_qty" value="1"/>
		</form>
		<div class="table table-borderless">
			<table id="userProductTable" class="table justify-content-center">
			  <thead class="thead">
			    <tr align="center">
					<th scope="col" colspan="2" >${productDTO.product_name_title}</th>
			  	</tr>
			   </thead>
			   <tbody>
			   	<tr>
			   		<td rowspan="5">
			   			<img src="/minishop/storage/showProduct.do?product_name_image=${productDTO.product_name_image}">
			   		</td>
			   		<td><h2>${productDTO.productName}</h2></td>
			   	</tr>
			   	<tr>
			   		<td>
			   			<c:if test="${productDTO.product_salesMount>=300}">
			   				<span>#인기 상품</span>&emsp;
						</c:if>
			   			<c:if test="${productDTO.product_name_price>productDTO.unitcost}">
			   				<span>#세일 상품</span>&emsp;
						</c:if>	
			   			<c:if test="${productDTO.product_hit>=300}">
			   				<span>#주목 상품</span>&emsp;
						</c:if>	
			   			<c:if test="${productDTO.product_like>=300}">
			   				<span>#관심 상품</span>&emsp;
						</c:if>																						
			   		</td>			   	
			   	</tr>		
			   	<tr>
				   	<td>
				   	<c:if test="${productDTO.product_name_price<=productDTO.unitcost}">
						<div class="product_price">NowOn Price&emsp;<fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>원</div>
					</c:if>
					<c:if test="${productDTO.product_name_price>productDTO.unitcost}">
						<div class="original_price">정상가&emsp;<fmt:formatNumber type="number" value="${productDTO.product_name_price}" pattern="#,###"/>원</div>
						<div class="product_price">판매가&emsp;<fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>원</div>					
					</c:if>	
			   		</td>		   	
			   	</tr>	
				<tr>
			   		<td align="center">
			   				<h4 style="padding-right:180px;">수량</h4>
			   			  	<div class="btn-group mr-2" role="group" aria-label="Second group">
							    <button type="button" id="cart_minus" class="btn btn-light"><i class="fa fa-minus" aria-hidden="true"></i></button>
							    <input type="number" class="form-control-plaintext" id="cart_qty" value="1">
							    <button type="button" id="cart_plus" class="btn btn-light"><i class="fa fa-plus" aria-hidden="true"></i></button>
  							</div>
  							<span class="total_price">합계&emsp;<font id="totalCost"><fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/></font>원</span>
  							
					</td>
				</tr>
				<tr>
					<td>
						<div align="center">
							<button type="button" id="putCartBtn" class="btn btn-outline-danger btn-lg">add to cart</button>
							<c:if test="${SEQ==0}">
								<button class="btn btn-outline-light" id="product_like"><i class="fas fa-heart">좋아요</i></button>
							</c:if>
							<c:if test="${SEQ!=0}">
								<button class="btn btn-light" id="product_like"><i class="fas fa-heart">좋아요</i></button>
							</c:if>							
							
						</div>
			   		</td>
			   	</tr>
			   	<tr align="center">
				   	<td colspan="2">
				   		<h2>상세정보</h2>
				   	</td>
			   	</tr>
			   	<tr align="center">
				   	<td colspan="2">
				   		${productDTO.product_name_detail}
				   	</td>
			   	</tr>
			   </tbody>  			
			</table>
		</div>
		<div class="row justify-content-center">
			<div class="col" align="center">
				<button type="button" id="writeQABtn" class="btn btn-outline-info btn-lg">이 상품의 문의 쓰기</button>
				<button type="button" id="writeReviewBtn" class="btn btn-outline-info btn-lg">이 상품의 리뷰 쓰기</button>
			</div>
		</div>
	 	<!-- shop policies -->
	<jsp:include page="/template/shopPolicy.jsp"/>
</div>

   
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
var unitcost = ${productDTO.unitcost};
var stock = ${productDTO.stock};
var getCookie = function(name) {
	  var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	  return value? value[2] : null;
};
function formatNumber(x){
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

$(document).ready(function(){
	$('#cart_qty').change(function cartChange(){
		var cart_qty = parseInt($('#cart_qty').val(),10);
		if(cart_qty<1) {
			alert('더이상 줄일수 없습니다.');
			$('#cart_qty').val(1);
		}
		else if(cart_qty>stock){
			alert('재고 이상으로 담을 수 없습니다. [현재재고 :'+stock+'(개)]');
			$('#cart_qty').val('${productDTO.stock}');	
		}
		var totalcost = formatNumber(unitcost*$('#cart_qty').val());
		$('#totalCost').text(totalcost);
	});
});
$('#cart_minus').click(function(e){
	e.preventDefault();	
	var stat = $('#cart_qty').val();
	var num = parseInt(stat,10);
	num=num-1;	
	if(num<1) {
		alert('더이상 줄일수 없습니다.');
		$('#cart_qty').val(1);
	}else $('#cart_qty').val(num);
	var totalcost = formatNumber(unitcost*$('#cart_qty').val());
	$('#totalCost').text(totalcost);
});//cart-
$('#cart_plus').click(function(e){
	e.preventDefault();	
	var stat = $('#cart_qty').val();
	var num = parseInt(stat,10);
	num=num+1;
	if(num>stock){ 
		alert('재고 이상으로 담을 수 없습니다. [현재 남은 재고 수량 :'+stock+'(개)]');
		$('#cart_qty').val('${productDTO.stock}');	
	}else $('#cart_qty').val(num);
	var totalcost = formatNumber(unitcost*$('#cart_qty').val());
	$('#totalCost').text(totalcost);
});//cart+
$('#putCartBtn').click(function(){
	var cart_qty = parseInt($('#cart_qty').val(),10);
	if(cart_qty>stock){
		alert('재고 이상으로 담을 수 없습니다. [현재재고 :'+stock+'(개)]');
	}
	else if(cart_qty==0){
		alert('최소 1개 이상 주문하셔야 합니다.');
	}
	else{
		var buyNow = confirm('바로 구입하시겠습니까?');
		if(buyNow){
			var product_name_no = new Array();
			product_name_no[0] = $('#product_name_no').val();
			$('input[name=product_name_no]').val(product_name_no);
			$('input[name=cart_qty]').val($('#cart_qty').val());	
			$('#buyNowForm').submit();
			}//if
		else{
			$.ajax({
				type: 'post',
				url : '/minishop/trading/addCart.do',
				data: {'product_name_no':$('#product_name_no').val(),
						'cart_qty':$('#cart_qty').val()},
				success: function(){
					var seeCart = confirm('현재 품목을 장바구니에 담았습니다. 장바구니를 확인하러 가시겠습니까?');
					if(seeCart){
						window.location='/minishop/trading/userCart.do';
					}//if
					else{
						alert('쇼핑목록으로 돌아갑니다.');
						window.location='/minishop/product/categories.do?product_category_name=ALL';}//else
				}//success

			});//ajax
		}//else

		
	}
});
$('#writeQABtn').click(function(){
	window.location='/minishop/board/qa/qaWriteForm.do?productID=${productDTO.productID}';
});
$('#writeReviewBtn').click(function(){
	window.location='/minishop/board/review/reviewWriteForm.do?productID=${productDTO.productID}';	
});
$('#product_like').click(function(){
	if(${memberDTO==null && orderDTO==null}) {
		alert('좋아요를 하시려면 로그인이 필요합니다.');
		var login = confirm('로그인 화면으로 이동합니까?');
		if(login){
			window.location='/minishop/member/loginForm.do';
		}
	}else{
	$.ajax({
		type: 'get',
		url : '/minishop/product/likeOnAndOff.do',
		data: {'product_name_no':$('#product_name_no').val()},
		success: function(){
			$('#SEQ').val('${SEQ}');
			window.location.reload();
		}//success

	});//ajax
	}//else
	
});//click method
</script>