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
				<!-- 사용자 -->
			<c:if test="${location=='home'}">
		         <h2>Welcome<br><Strong>Kissin'</Strong> <font style="color:red;">BUGS</font></h2>
			     <div class="list-group list-group-lg">
			     	  <a href="/minishop/main/introduce.do" class="list-group-item">About Kissin' Bugs</a>
			          <a href="/minishop/product/categories.do?product_category_name=ALL" class="list-group-item">상품 카테고리</a>
			          <a href="/minishop/product/eventProductList.do?condition=newArrival" class="list-group-item">특별전</a>
			          <a href="/minishop/main/userContact.do" class="list-group-item">찾아오시는 길</a>
			     </div>			
			</c:if>					
			<c:if test="${location=='beforeLogin'}">
		        <h2>Welcome <br>Our <font style="color:red;">Members</font></h2>
			     <div class="list-group list-group-lg">
			          <a href="/minishop/member/writeForm.do" class="list-group-item">회원가입</a>			     
			          <a href="/minishop/member/loginForm.do" class="list-group-item">로그인</a>	
			          <a href="/minishop/member/findForm.do" class="list-group-item">ID/PWD찾기</a>	
			     </div>			
			</c:if>				
			<c:if test="${location=='board'}">
		        <h2>고객 게시판</h2>			
			     <div class="list-group list-group-lg">
			          <a href="/minishop/board/qa/qaList.do" class="list-group-item">문의 게시판</a>
			          <a href="/minishop/board/review/reviewList.do" class="list-group-item">후기 게시판</a>				     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>					
			<c:if test="${location=='member'}">
		        <h2>MY ACCOUNT</h2>			
			     <div class="list-group list-group-lg">
			          <a href="/minishop/member/memberView.do" class="list-group-item">My Info</a>
			          <a href="/minishop/member/memberOrderlist.do" class="list-group-item">내 주문 현황</a>
			          <a href="/minishop/member/memberCouponlist.do" class="list-group-item">내 쿠폰 현황</a>			     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>
			<c:if test="${location=='orderUser'}">
		        <h2>주문/거래 현황</h2>			
			     <div class="list-group list-group-lg">
			     	<c:if test="${memberDTO!=null&&memberDTO!=''}">
			     		<a href="/minishop/member/memberOrderlist.do" class="list-group-item">주문 목록</a>
			     	</c:if>
			          <a href="/minishop/trading/userCart.do" class="list-group-item">장바구니</a>			     
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>			
			<c:if test="${location=='category'}">
		        <h4>OUR PRODUCTS</h4>			
			     <div class="list-group list-group-lg">
			          <a href="/minishop/product/categories.do?product_category_name=ALL" class="list-group-item">ALL</a>	
			          <a href="/minishop/product/eventProductList.do?condition=newArrival" class="list-group-item">SPECIAL</a>
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>				
			</c:if>	
			<!-- 관리자 -->
			<c:if test="${location=='homeAdmin'}">
		        <h2>빠른 조회</h2>
			     <div class="list-group list-group-lg">
			          <a href="/minishop/admin/shop/salesInfo.do" class="list-group-item">매출현황</a>
			          <a href="/minishop/admin/order/orderManage.do" class="list-group-item">고객주문 조회</a>			          
			          <a href="/minishop/admin/user/personalQAManager.do" class="list-group-item">1:1문의 조회</a>
			          <a href="/minishop/admin/board/qaManage.do" class="list-group-item">일반문의 조회</a>
			     </div>			
			</c:if>	
			<c:if test="${location=='shopAdmin'}">
		        <h2>점포 관리</h2>
			     <div class="list-group list-group-lg">
			     	  <a href="/minishop/admin/shop/adminManage.do" class="list-group-item">관리자정보</a>
			          <a href="/minishop/admin/shop/salesInfo.do" class="list-group-item">매출현황</a>
			          <a href="/minishop/admin/shop/eventManage.do" class="list-group-item">이벤트관리</a>	
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>			
			</c:if>				
			<c:if test="${location=='QAAdmin'}">
		        <h2>답변대기중인<br>문의글</h2>			
			     <div class="list-group list-group-lg">		     
			          <a href="/minishop/admin/board/qaManage.do" class="list-group-item">답변대기중인<br>고객문의글목록</a>
			          <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>			        
			</c:if>			
			<c:if test="${location=='productAdmin'}">
		        <h2>상품관리</h2>			
			     <div class="list-group list-group-lg">
			     	<a href="/minishop/admin/product/inventoryManage.do" class="list-group-item">입점재고관리</a>	     
			        <a href="/minishop/admin/product/productManage.do" class="list-group-item">등록상품관리</a>
			        <a href="/minishop/admin/product/productUpload.do" class="list-group-item">상품등록</a>				          				     
			        <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>	
	
			<c:if test="${location=='userAdmin'}">
		        <h2>고객관리</h2>			
			     <div class="list-group list-group-lg">
			     	<a href="/minishop/admin/user/userManage.do" class="list-group-item">고객정보관리</a>	     
			        <a href="/minishop/admin/user/personalQAManager.do" class="list-group-item">1:1문의관리</a>			          				     
			        <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>	
																		
			<c:if test="${location=='orderAdmin'}">
		        <h2>고객주문관리</h2>			
			     <div class="list-group list-group-lg">	 
			     	<a href="/minishop/admin/order/orderManage.do" class="list-group-item">주문관리</a>	    		          				          				     
			        <a href="javascript:history.back()" class="list-group-item">돌아가기</a>
			     </div>					
			</c:if>				
		</div>		
	</div>		
      <!-- /.col-lg-3 -->		
	</c:if>		