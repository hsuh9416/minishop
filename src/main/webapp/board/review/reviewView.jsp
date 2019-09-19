<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	<!--CSS Local LINK:END-->

<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
	 		<h3>고객 후기</h3>		
		</div>
	</div>
	
	<form name="reviewViewForm">	
	
		<input type="hidden" id="pg" value="${pg}"/>
		<input type="hidden" id="review_seq" value="${reviewboardDTO.review_seq}"/>
		<input type="hidden" id="review_pseq" value="${reviewboardDTO.review_seq}"/>
		<input type="hidden" id="productid" value="${reviewboardDTO.productid}"/>
		<input type="hidden" id="review_pwd" value="${reviewboardDTO.review_pwd}"/>

		<div class="form-row align-items-center">
			<div class="col-2 subTitle"><font>글번호</font></div>		
			<div class="col-5 subContent">${reviewboardDTO.review_seq}</div>	
			<div class="col-2 subTitle"><font>상품번호</font></div>	
			<div class="col-3 subContent">[${reviewboardDTO.productid}]</div>													
		</div>

		<div class="form-row align-items-center">
			<div class="col-2 subTitle"><font>작성자</font></div>		
			<div class="col-5 subContent">${reviewboardDTO.name}</div>	
			<div class="col-2 subTitle"><font>작성일</font></div>	
			<div class="col-3 subContent"><fmt:formatDate value="${reviewboardDTO.review_logtime}" pattern="yyyy년 MM월 dd일"/></div>								
		</div>
		
		<div class="form-row align-items-center">
			<div class="col-2 subTitle"><font>제목</font></div>		
			<div class="col-5 subContent">${reviewboardDTO.review_subject}</div>
			<div class="col-2 subTitle"><font>조회수</font></div>		
			<div class="col-3 subContent">${reviewboardDTO.review_hit}</div>												
		</div>			

		<div class="form-row align-items-center">
			<div class="col reviewContent">${reviewboardDTO.review_content}</div>								
		</div>	
								
	</form>
	 
	<div class="form-group row" id="btnDiv">
		<div class="col-4"></div>	 
		<c:if test="${reviewboardDTO.user_id==memberDTO.id || reviewboardDTO.user_id==guestDTO.guest_id}">
			<div class="col-2  reviewDiv">				  
				<input type="button" class="btn btn-outline-dark" value="수정하기"  id="reviewModifyFormBtn">
			</div>	
			<div class="col-2  reviewDiv">
				<input type="button" class="btn btn-outline-dark" value="삭제하기" id="reviewDeleteBtn">						
			</div>						  	
		</c:if>
		<div class="col-2  reviewDiv">				    
			<input type="button" class="btn btn-outline-dark" value="답글달기" id="reviewReplyFormBtn">	
		</div>	  
		<div class="col-2  reviewDiv">				
			<input type="button" value="목록으로" class="btn btn-outline-dark"
					onclick="location.href='/minishop/board/review/reviewList.do?pg=${pg}'">										    									
		</div>
	</div>	
	
	<div id="pwdConfirm" class="form-row justify-content-center">
		<div class="col-1"></div>		
		<div class="col-4 reviewDiv">
			<input type="text" id="alertText" readonly class="form-control-plaintext" value="글의 비밀번호를 입력하세요"/>
		</div>
		<div class="col-3 reviewDiv">
			<input type="password" class="form-control" placeholder="비밀번호 입력" id="rePwd"/>			
		</div>
		<div class="col-2 reviewDiv">
			<input type="button" class="btn btn-outline-dark" value="확인" id="checkReviewPwd"/>			
		</div>		
		<input type="hidden" id="purpose" value=""> 						
	</div>				
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/review/board.review.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/review/reviewView.js"></script>
	<!--JavaScript Local LINK:END-->
