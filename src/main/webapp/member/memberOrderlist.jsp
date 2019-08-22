<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  


	<!--CSS Local LINK:START-->    
<link rel="stylesheet" href="/minishop/resources/custom/css/orderForm.css">
	<!--CSS Local LINK:END-->	
    
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
	 		<h3>회원 주문 목록</h3>		
		</div>
	</div>   	
	<hr class="sub-hr" noshade/>	
	
	<div class="form-row align-items-center">
		<div class="col-1" align="center">#</div>
		<div class="col-2" align="center">주문일자</div>				
		<div class="col-4" align="center">구매내역</div>
		<div class="col-2" align="center">상태</div>
		<div class="col-3" align="center">확인/신청</div>													
	</div>
	<hr  class="sub-secondary-hr" noshade/>
	
	<form id="orderListForm"></form>
	
	<hr  class="sub-secondary-hr" noshade/>
	
	<hr class="sub-hr" noshade/>
	
	<div class="form-row align-items-center">
		<div class="form-group col" align="right">
			<input type="button" class="btn btn-outline-info" value="1:1문의" id="personalQABtn">	
			<input type="button" class="btn btn-outline-secondary" value="돌아가기" id="returnBtn">	
		</div>			
	</div>	
	
</div>	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/memberOrderlist.js"></script>	
	<!--JavaScript Local LINK:END-->