<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!--CSS Local LINK:START-->
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>고객정보관리</h3>		
		</div>
	</div>
	
	<div class="form-row align-items-center">
		<div class="col-1">#</div>
		<div class="col-2" align="center">회원명</div>
		<div class="col-2" align="center">아이디</div>					
		<div class="col-1" align="center">등급</div>
		<div class="col-2" align="center">가입일자</div>
		<div class="col-2" align="center">상태</div>	
		<div class="col-2" align="center"></div>																	
	</div>
	<hr width="100%" color="darkgray" noshade/>
		<!-- MEMBERLIST:START -->
		<form id="memberListForm"></form>
		<!-- MEMBERLIST:END -->		
	<hr width="100%" color="darkgray" noshade/>	
	<div class="form-row align-items-center">
		<div class="form-group col" align="right"> 
			<input type="button" class="btn btn-outline-primary" value="전체 혜택 지급" id="giveBenefitAll">	
			<input type="button" class="btn btn-outline-info" value="전체 공지 발송" id="giveNoticeAll">	
		</div>
	</div>
</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/userManage.js"></script>	
	<!--JavaScript Local LINK:END-->