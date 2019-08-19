<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!--CSS Local LINK:START-->
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>1:1문의관리</h3>		
		</div>
	</div>
	
	<div class="form-row align-items-center">
		<div class="col-2">문의번호</div>
		<div class="col-2" align="center">문의주제</div>
		<div class="col-3" align="center">문의자명</div>
		<div class="col-3" align="center">회신메일주소</div>					
		<div class="col-2" align="center">문의일자</div>															
	</div>
	<hr width="100%" color="darkgray" noshade/>
		<!-- MEMBERLIST:START -->
		<form id="personalQAListForm"></form>
		<!-- MEMBERLIST:END -->		
	<hr width="100%" color="darkgray" noshade/>	

</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/personalQAManager.js"></script>	
	<!--JavaScript Local LINK:END-->