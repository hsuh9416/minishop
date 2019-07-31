<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">

		<!-- 리뷰 수정 폼 -->	  
<div class="col-lg-8">
	<div class="row" id="titleDiv">
	 	<div class="col" align="center" style="padding-bottom: 20px;">
	 		<h3>고객 후기</h3>		
	 	</div>
	</div>
	<form name="reviewViewForm">	
			<input type="hidden" name="pg" id="pg" value="${pg}"/>
			<input type="hidden" name="review_seq" id="review_seq" value="${reviewboardDTO.review_seq}"/>
			<input type="hidden" name="review_pseq" id="review_pseq" value="${reviewboardDTO.review_seq}"/>
			<input type="hidden" name="productid" id="productid" value="${reviewboardDTO.productid}"/>
			<input type="hidden" name="review_pwd" id="review_pwd" value="${reviewboardDTO.review_pwd}"/>
			<table id="viewTable" class="table justify-content-center">
			  <thead class="thead-dark">
					<tr>
						<th scope="col">글번호</th>
						<th scope="col">상품번호</th>
						<th scope="col">작성자</th>
						<th scope="col">조회수</th>					
						<th scope="col">작성일</th>		
					</tr>
			  </thead>
			   <tbody>
				    <tr>
						<td>${reviewboardDTO.review_seq}</td>
						<td>${reviewboardDTO.productid}</td>
						<td>${reviewboardDTO.name}</td>
						<td>${reviewboardDTO.review_hit}</td>					
						<td><fmt:formatDate value="${reviewboardDTO.review_logtime}" pattern="yyyy년 MM월 dd일"/></td>			    	
			   		</tr>
			   		<tr class="thead-dark">
						<th class="" colspan="5" class="align-middle">${reviewboardDTO.review_subject}</th>					   		
			   		</tr>
			   		<tr>
			   			<td colspan="5" class="align-middle">
			   				<div id="review_content">${reviewboardDTO.review_content}</div>
			   			</td>
			   		</tr>		   
			   </tbody>
			</table>  			
	 </form>
	<div class="form-group row" id="btnDiv">
		<div class="col" align="right">   
			  <c:if test="${reviewboardDTO.user_id==memberDTO.id}">
				<input type="button" class="btn btn-outline-dark" value="수정하기"  id="reviewModifyFormBtn">
				<input type="button" class="btn btn-outline-dark" value="삭제하기" id="reviewDeleteBtn">				  	
			  </c:if>
			  <c:if test="${memberDTO==null||adminDTO==null}">		  
				<input type="button" class="btn btn-outline-dark" value="답글달기" id="reviewReplyFormBtn">	
			  </c:if>		  
				<input type="button" value="목록" class="btn btn-outline-dark"
					onclick="location.href='/minishop/board/review/reviewList.do?pg=${pg}'">										    									
				</div>
	</div>	
		<div id="pwdConfirm" class="form-group row justify-content-center">
			<input type="hidden" id="purpose" value=""> 
			<div class="col-4">
				<input type="text" id="alertText" readonly class="form-control-plaintext" value="글의 비밀번호를 입력하세요"/>
			</div>
			<div class="col-3">
				<input type="password" class="form-control" placeholder="비밀번호 입력" id="rePwd"/>			
			</div>
			<div class="col-2">
				<input type="button" class="btn btn-outline-dark" value="확인" id="checkReviewPwd"/>			
			</div>						
		</div>		     
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/board.review.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#pwdConfirm').hide();
});

</script>