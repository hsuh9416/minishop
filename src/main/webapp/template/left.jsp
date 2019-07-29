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
			<c:if test="${location=='adminHome'}">
		        <h2>빠른 조회</h2>
			     <div class="list-group list-group-lg">
			          <a href="/minishop/admin/shop/salesInfo.do" class="list-group-item">매출현황</a>
			          <a href="/minishop/admin/order/orderList.do" class="list-group-item">고객주문 조회</a>			          
			          <a href="/minishop/admin/user/personalQAManager.do" class="list-group-item">1:1문의 조회</a>
			          <a href="/minishop/admin/board/qaManage.do" class="list-group-item">일반문의 조회</a>
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
			<c:if test="${location=='qaList'}">
		        <h2>문의 게시판</h2>			
			     <div class="list-group list-group-horizontal-lg">
			          <a href="/minishop/board/qa/qaWriteForm.do" class="list-group-item">문의글 작성</a>			     
			          <a href="/minishop/board/qa/loginForm.do" class="list-group-item">로그인</a>			     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>	
			<c:if test="${location=='adminQAList'}">
		        <h2>답변대기중인<br>문의글 현황</h2>			
			</c:if>
			<c:if test="${location=='adminQA'}">
		        <h2>답변대기중인<br>문의글</h2>			
			</c:if>			
			<c:if test="${location=='reivewList'}">
		        <h2>후기 게시판</h2>			
			     <div class="list-group list-group-horizontal-lg">
			          <a href="/minishop/board/reivew/reviewWriteForm.do" class="list-group-item">후기 작성</a>			     
			          <a href="/minishop/board/reivew/loginForm.do" class="list-group-item">로그인</a>			     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>		
			<c:if test="${location=='memberView'}">
		        <h2>회원 정보</h2>			
			     <div class="list-group list-group-lg">
			          <a href="/minishop/member/memberOrderlist.do" class="list-group-item">주문 현황</a>			     
			          <a href="/minishop/board/reivew/reviewWriteForm.do" class="list-group-item">리뷰 작성</a>			     
			          <a href="/minishop/board/qa/qaWriteForm.do" class="list-group-item">문의 작성</a>
			     </div>					
			</c:if>	
				<c:if test="${location=='memberModify'}">
		        <h2>회원 정보<br>수정</h2>			
			     <div class="list-group list-group-lg">	     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>			

			<c:if test="${location=='inventory'}">
		        <h2>입점상품관리</h2>			
			     <div class="list-group list-group-lg">	     
			          <a href="/minishop/admin/product/productManage.do" class="list-group-item">등록상품관리</a>				          				     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>	

			<c:if test="${location=='adminProduct'}">
		        <h2>등록상품관리</h2>			
			     <div class="list-group list-group-lg">	     
			          <a href="/minishop/admin/product/inventoryManage.do" class="list-group-item">입점상품관리</a>
			          <a href="/minishop/admin/product/productUpload.do" class="list-group-item">상품등록</a>		          				          				     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>				
			
		</div>		
	</div>		
      <!-- /.col-lg-3 -->		
	</c:if>		