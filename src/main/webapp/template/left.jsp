<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!-- 왼쪽 메뉴바 -->
    <!-- 설정 안함 -->
    <c:if test="${location==''}">
	</c:if>	
    <!-- 설정함 -->
    <c:if test="${location!=''}">
	
	<div class="col-lg-3" style="padding-top: 20px;">    
		<div class="position-fixed" id="menuDiv">
	
			<c:if test="${location=='home'}">
		        <h2>바로가기</h2>
			     <div class="list-group list-group-horizontal-lg">
			          <a href="#" class="list-group-item">신상품</a>
			          <a href="#" class="list-group-item">인기상품</a>
			          <a href="#" class="list-group-item">특가상품</a>
			     </div>			
			</c:if>
			<c:if test="${location=='writeForm'}">
		        <h2>회원가입</h2>		
			</c:if>
			<c:if test="${location=='loginForm'}">
		        <h2>로그인</h2>	
			</c:if>		
			<c:if test="${location=='findForm'}">
		        <h2>아이디/비밀번호<br>찾기</h2>
			     <div class="list-group list-group-horizontal-lg">
			          <a href="/minishop/member/writeForm.do" class="list-group-item">회원가입</a>			     
			          <a href="/minishop/member/loginForm.do" class="list-group-item">로그인</a>			     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>			
			</c:if>						
		</div>		
	</div>
      <!-- /.col-lg-3 -->		
	</c:if>	      