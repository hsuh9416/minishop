<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!--CSS Local LINK:START-->
	
	<!--CSS Local LINK:END-->
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col" align="center" style="padding-bottom: 20px;">
			<h3>1:1 문의 목록</h3>		
		</div>
	</div>
	<div class="form-row align-items-center">
		<div class="col-2">#</div>
		<div class="col-2" align="center">회원명</div>
		<div class="col-2" align="center">회원아이디</div>					
		<div class="col-2" align="center">회원등급</div>
		<div class="col-2" align="center">가입일자</div>
		<div class="col-2" align="center">회원상태</div>															
	</div>
	<hr width="100%" color="darkgray" noshade/>
		<!-- MEMBERLIST:START -->
		<form id="memberListForm"></form>
		<!-- MEMBERLIST:END -->		
	<hr width="100%" color="darkgray" noshade/>	
	<div class="form-row align-items-center">
		<div class="col-2"></div>
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
<script type="text/javascript" src="/minishop/resources/custom/js/admin/personalQAManager.js"></script>	
	<!--JavaScript Local LINK:END-->