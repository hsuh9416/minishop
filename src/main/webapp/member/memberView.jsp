<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">

	<!-- 회원 정보 화면 -->
	<div class="col-lg-8">
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>로그인</h3>		
	 		</div>
		</div>	 	
		<div class="table-resposive">
			<table id="boardTable" class="table justify-content-center">
			  <thead class="thead-light">
			    <tr>
					<th scope="col" colspan="4">${memberDTO.name} 님 방문을 환영합니다.</th>
			  	</tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<c:if test="${memberDTO.state==1}">
			   		<td rowspan="7" id="figureSpace" >
  						<i id="figure" class="fas fa-user">USER</i>
					</td>
			   		<td colspan="3"><strong>${memberDTO.name}</strong> 님은 <strong>일반 회원</strong> 이십니다.</td>					
					</c:if>
					<c:if test="${memberDTO.state==2}">
			   		<td rowspan="7" id="figureSpace" >
  						<i id="figure" class="fal fa-chess-king-alt">VIP</i>
					</td>					
			   		<td colspan="3"><strong>${memberDTO.name}</strong> 님은 <strong>특별 회원</strong> 이십니다.</td>					
					</c:if>
			   	</tr>
			   	<tr>
			   		<td>내 장바구니 : </td>
			   		<td colspan="2"><a id="goCartMember" href="#cartModal"></a></td>
			   	</tr>			   	
			   	<tr>
			   		<td>주문 중인 건수 :</td>
			   		<td colspan="2"><a id="orderlistPg" href="#"></a></td>
			   	</tr>
			   	<tr>
			   		<td>사용 가능한 쿠폰 : </td>
			   		<td colspan="2"><a id="goCoupon" href="#couponModal"></a></td>
			   	</tr>
			   	<tr>
			   		<td>사용 가능한 포인트 : </td>
			   		<td colspan="2">${memberDTO.point} 점</td>
			   	</tr>	
			   	<tr>
			   		<td>가입하신 날짜 : </td>
			   		<td colspan="2">
			   			<fmt:formatDate value="${memberDTO.registerdate}" pattern="yyyy년  MM월  dd일"/>
			   		</td>
			   	</tr>	
			   	<tr>
			   		<td colspan="3" align="right">		   		
			   			<input type="button" id="memberModify" class="btn btn-outline-dark" value="회원 정보 수정"/>
			   			<input type="button" id="memberDelete" data-toggle="modal" class="btn btn-outline-dark" value="회원 탈퇴"/>
						<input type="button" id="personalQA" data-toggle="modal"  class="btn btn-outline-dark" value="1:1 문의"/>	   			
			   		</td>
			   	</tr>				   			   				   	
			   </tbody> 	  
			</table>
		</div>
	</div>

	<!-- model personalQAform frame -->
	<jsp:include page="/member/memberDelete.do"/>		
	<jsp:include page="/member/personalQAForm.do"/>	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member.view.js"></script>	