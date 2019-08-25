<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/orderForm.css">
	<!--CSS Local LINK:END-->	
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
		<h3>고객주문관리</h3>
		</div>
	</div>   	
	<input type="hidden" id="pg" value="${pg}">	
	<hr class="sub-hr" noshade/>	
	
	<div class="form-row align-items-center">
		<div class="col-1" align="center">#</div>
		<div class="col-2" align="center">주문일자</div>				
		<div class="col-2" align="center">주문ID</div>
		<div class="col-2" align="center">주문자명</div>	
		<div class="col-3" align="center">주문내용</div>
		<div class="col-2" align="center">거래상태</div>													
	</div>
	<hr  class="sub-secondary-hr" noshade/>
	
	<form id="orderListForm"></form>
	
	<hr  class="sub-secondary-hr" noshade/>
	
	<hr class="sub-hr" noshade/>

	<div class="form-row justify-content-center subContent">
		<div class="col">
			<nav aria-label="Page navigation">
			  	<ul class="pagination justify-content-center" id="orderPaingDiv"></ul>
			</nav>
		</div>								
	</div>	
	
	<form id="orderSearch" name="orderSearch">
		<div class="form-row justify-content-center">
			<div class="form-group col-2" align="center"></div>
			<div class="form-group col-2" align="center">
				<input type="hidden" name="pg" value="1">
				<select name="searchOption" id="searchOption" class="form-control">
			        <option value="order_no">주문번호</option>	
			        <option value="order_date">주문일자</option>			
			        <option value="order_id">주문ID</option>
					<option value="order_name">주문자명</option>
					<option value="order_state">거래상태</option>
			    </select>			
			</div>
			<div class="form-group col-3" align="center">
				<input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}">
			</div>
			<div class="form-group col-3" align="center">	
				<input type="button" id="orderSearchBtn" class="btn btn-outline-dark" value="주문검색">
				<input type="button" id="returnBtn" class="btn btn-outline-secondary" value="돌아가기">
			</div>	
			<div class="form-group col-2" align="center"></div>	   
		</div>
	</form>
	
</div>


	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminOrder/orderManage.js"></script>	
	<!--JavaScript Local LINK:END-->