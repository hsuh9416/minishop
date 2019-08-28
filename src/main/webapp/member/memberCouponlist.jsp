<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--CSS Local LINK:START-->    
<link rel="stylesheet" href="/minishop/resources/custom/css/orderForm.css">
	<!--CSS Local LINK:END-->	
    
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
	 		<h3>회원 쿠폰 목록</h3>		
		</div>
	</div>   	
	<hr class="sub-hr" noshade/>	
	<div class="form-row justify-content-center">
		<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">#</div>
		<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">이름</div>
		<div class="col-1" align="center" style="text-decoration:underline;text-underline-position:under">분류</div>
		<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">할인액(률)</div>
		<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">유효기간</div>	
		<div class="col-1" align="center" style="text-decoration:underline;text-underline-position:under">상태</div>																																	
	</div>
	<form id="couponForm"></form>	
	<hr class="sub-hr" noshade/>
	
	<div id="detailInfo"></div>		
	
	<div class="form-row justify-content-center">
		<div class="form-group col" align="right">      								
			<button type="button" id="returnBtn" class="btn btn-outline-secondary">돌아가기</button>					
		</div>
	</div>	
</div>	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/memberCouponlist.js"></script>	
	<!--JavaScript Local LINK:END-->