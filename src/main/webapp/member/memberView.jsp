<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
	<!--CSS Local LINK:END-->	
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
	 		<h3>회원 정보</h3>		
		</div>
	</div>   	
	<div class="form-row justify-content-center">	
		<div class="form-group col viewTitle">
			<strong>${memberDTO.name} 님의 방문을 환영합니다.</strong>
		</div>
	</div>	
	<div class="form-row justify-content-center">	
		<div class="form-group col-4">
			<c:if test="${memberDTO.state==1}">
				<img style="width:100%;height:100%;" alt="general_user" src="/minishop/resources/image/icons/general_icon.jpg"/>
			</c:if>	
			<c:if test="${memberDTO.state==2}">
				<img style="width:100%;height:100%;" alt="viip_user" src="/minishop/resources/image/icons/vip_icon.jpg"/>
			</c:if>	
			<c:if test="${memberDTO.state==0}">
				<img style="width:100%;height:100%;" alt="admin" src="/minishop/resources/image/gif/duck1.gif"/>
			</c:if>									
		</div>
		<div class="form-group col-8">
			<div class="form-row justify-content-center">
				<div class="form-group col">
					<c:if test="${memberDTO.state==0}">
						현재 [관리자 모드]입니다. (접속 관리자명: ${memberDTO.name})
					</c:if>	
					<c:if test="${memberDTO.state==1}">
						<strong>${memberDTO.name}</strong> 님은 <strong>일반 회원(Clover)</strong> 이십니다.
					</c:if>		
					<c:if test="${memberDTO.state==2}">
						<strong>${memberDTO.name}</strong> 님은 <strong>특별 회원(Star)</strong> 이십니다
					</c:if>																
				</div>
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[내 장바구니 ]</div>
				<div class="form-group col-8">
			   		<c:if test="${cartList==null||cartList.size()==0}">		
			   			<a href="/minishop/trading/userCart.do">장바구니에 담긴 상품이 없습니다</a>	  
			   		</c:if>
			   		<c:if test="${cartList!=null&&cartList.size()>0}">
				   		<a href="/minishop/trading/userCart.do">${cartList.size()}건</a>	   		
			   		</c:if>				
				</div>				
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[진행 중인 주문 건수]</div>
				<div class="form-group col-8"><a id="orderlistPg" href="/minishop/member/memberOrderlist.do"></a></div>
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[사용 가능한 쿠폰]</div>
				<div class="form-group col-8"><a id="goCoupon" href="/minishop/member/memberCouponlist.do"></a></div>
			</div>		
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[사용 가능한 포인트]</div>
				<div class="form-group col-8"><fmt:formatNumber value="${memberDTO.point}" pattern="#,###"/> 점</div>	
			</div>		
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[가입하신 날짜 ]</div>
				<div class="form-group col-8"><fmt:formatDate value="${memberDTO.registerdate}" pattern="yyyy년  MM월  dd일"/></div>	
			</div>															
		</div>		
	</div>	
	<div class="form-row justify-content-center">	
		<div class="col-6"></div>	
		<div class="col-2 memberBtnDiv">
			<input type="button" id="memberModify" class="btn btn-outline-dark" value="정보수정"/>		
		</div>
		<c:if test="${memberDTO.state==1 || memberDTO.state==2}">
			<div class="col-2 memberBtnDiv">
				<input type="button" id="memberDelete" data-toggle="modal" class="btn btn-outline-dark" value="탈퇴신청"/>		
			</div>	
			<div class="col-2 memberBtnDiv">
				<input type="button" id="personalQA" data-toggle="modal"  class="btn btn-outline-dark" value="1:1문의"/>			
			</div>		
		</c:if>
		


	</div>			
</div>
		
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/member.view.js"></script>	
	<!--JavaScript Local LINK:END-->