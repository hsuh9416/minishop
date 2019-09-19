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
			<h3>고객 문의글</h3>
		</div>
	</div>
	
 	<form name="qaViewForm">
 	
		<input type="hidden" id="pg" value="${pg}"/>
		<input type="hidden" id="qa_seq" value="${qaBoardDTO.qa_seq}"/>
		<input type="hidden" id="qa_pseq" value="${qaBoardDTO.qa_seq}"/>
		<input type="hidden" id="qa_pwd" value="${qaBoardDTO.qa_pwd}"/>		
		<input type="hidden" id="qa_reply" value="${qaBoardDTO.qa_reply}"/>
		
		<div class="form-row align-items-center">
			<div class="col-2 subTitle"><font>글번호</font></div>		
			<div class="col-5 subContent">${qaBoardDTO.qa_seq}</div>	
			<div class="col-2 subTitle"><font>상품번호</font></div>	
			<div class="col-3 subContent">
				<c:if test="${qaBoardDTO.productid==null}">[문의 상품 없음]</c:if>
				<c:if test="${qaBoardDTO.productid!=null}">[${qaBoardDTO.productid}]</c:if>						
			</div>										
		</div>
		
		<div class="form-row align-items-center">
			<div class="col-2 subTitle"><font>작성자</font></div>		
			<div class="col-5 subContent">${qaBoardDTO.name}</div>	
			<div class="col-2 subTitle"><font>작성일</font></div>	
			<div class="col-3 subContent"><fmt:formatDate value="${qaBoardDTO.qa_logtime}" pattern="yyyy년 MM월 dd일"/></div>								
		</div>		
		
		<div class="form-row align-items-center">
			<div class="col-2 subTitle"><font>제목</font></div>	
			<div class="col-10 subContent">${qaBoardDTO.qa_subject}</div>			
		</div>			
			
		<div class="form-row align-items-center">
			<div class="col-2 subTitle  contentArea"><font>문의하신 내용</font></div>	
			<div class="col-10 subContent  contentArea">${qaBoardDTO.qa_content}</div>			
		</div>	
			
		<div class="form-row align-items-center">
			<div class="col-2 subTitle  contentArea"><i id="adminAns"></i><font>관리자 답변</font></div>	
			<div class="col-10 subContent  contentArea" id="answer">
				<c:if test="${qaBoardDTO.qa_reply=='0'}">
			   		아직 답변이 존재하지 않습니다.
			   	</c:if>
				<c:if test="${qaBoardDTO.qa_reply=='1'}">
				   	[관리자의 답변이 들어오는 자리입니다.]
				</c:if>	
			</div>						
		</div>			
					
	</form>	
	
	<div class="form-row"  id="btnDiv">
		<div class="col-6"></div>
		<c:if test="${qaBoardDTO.user_id==memberDTO.id || qaBoardDTO.user_id==guestDTO.guest_id}">	
			<div class="col-2  qaDiv">		
				<c:if test="${qaBoardDTO.qa_reply=='0'}">				
					<input type="button" class="btn btn-outline-dark" value="수정하기"  id="qaModifyFormBtn">	
				</c:if>					
				<c:if test="${qaBoardDTO.qa_reply=='1'}">
					<input type="button" class="btn btn-outline-dark" value="수정불가"  disabled>					
				</c:if>																
			</div>
			<div class="col-2  qaDiv">
				<input type="button" class="btn btn-outline-dark" value="삭제하기" id="qaDeleteBtn">					
			</div>
		</c:if>								
		<div class="col-2  qaDiv">
			<input type="button" value="목록으로" class="btn btn-outline-dark" onclick="location.href='/minishop/board/qa/qaList.do?pg=${pg}'">			
		</div>					
	</div>		
		
	<div id="pwdConfirm" class="form-row justify-content-center">
		<div class="col-1"></div>		
		<div class="col-4 qaDiv">
			<input type="text" id="alertText" readonly class="form-control-plaintext" value="글의 비밀번호를 입력하세요"/>
		</div>
		<div class="col-3 qaDiv">
			<input type="password" class="form-control" placeholder="비밀번호 입력" id="rePwd"/>			
		</div>
		<div class="col-2 qaDiv">
			<input type="button" class="btn btn-outline-dark" value="확인" id="checkQaPwd"/>			
		</div>		
		<input type="hidden" id="purpose" value=""> 						
	</div>				
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/board.qa.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/qaView.js"></script>
	<!--JavaScript Local LINK:END-->