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

 	<!--JavaScript Local LINK:START-->	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/trading/userCart.js"></script>
	<!--JavaScript Local LINK:END-->