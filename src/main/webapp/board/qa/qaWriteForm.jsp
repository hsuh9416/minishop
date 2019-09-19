<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	<!--CSS Local LINK:END-->	  

<div class="col-lg-8">

	<div class="form-row" id="titleDiv">
	 	<div class="col">
	 		<h3>문의글 작성</h3>		
	 	</div>
	</div>
	<c:if test="${memberDTO!=null}">
		<input type="hidden" id="user_id" value="${memberDTO.id}">
		<input type="hidden" id="name" value="${memberDTO.name}">
	</c:if>
	<c:if test="${memberDTO==null&&guestDTO!=null}">
		<input type="hidden" id="user_id" value="${guestDTO.guest_id}">
		<input type="hidden" id="name" value="${guestDTO.guest_name}">
	</c:if>	
	
	<input type="hidden" name="productid" value="${productID}"/>
				
	<div class="form-row justify-content-center">
		<div class="form-group col-md-4">
			<label for="qa_subject"><strong>제목</strong></label>
			<input type="text" class="form-control" id="qa_subject" placeholder="문의하실 글의 제목을 입력하세요.">
		</div>
		<div class="form-group col-md-3">
			<label for="productid"><strong>상품명</strong></label>
			<select id="productid" class="form-control">
				<option value="">[문의대상(선택)]</option>   
			</select>		    
		</div>
		<div class="form-group col-3" id="imgDiv"></div>	    
	</div>
	   
	<div class="form-row justify-content-center">
		<div class="form-group col-md-10">
			<label for="qa_content"><strong>글 내용</strong></label>
			<textarea class="form-control autosize" id="qa_content" placeholder="문의하실 글의 내용을 입력해주세요."></textarea>
		</div>	    
	</div>
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-3">
			<label for="qa_pwd"><strong>글 비밀번호</strong></label>
			<input type="password" class="form-control" id="qa_pwd" placeholder="비밀번호 입력">
		</div>	 
		<div class="form-group col-md-3">
			<label for="qa_repwd"><strong>재확인</strong></label>
			<input type="password" class="form-control" id="qa_repwd" placeholder="비밀번호 재확인">
		</div>			    
		<div class="form-group col-2" id="makeSecret">
			<button class="btn btn-outline-secondary"><i class="fas fa-lock-open"></i></button>
		</div>		    		       
	</div>	   
	
	<div class="form-row justify-content-center">
		<div class="form-group col" align="center">      
			<input type="button" class="btn btn-outline-dark" value="문의글 올리기" id="qaWriteBtn">
			<input type="button" id="qaReset" class="btn btn-outline-dark" value="다시작성">
			<input type="button" id="qaReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
		</div>
	</div>	
	
	<div class="form-row justify-content-center">
		<div class="form-group col-5" id="missing"></div>		
	</div>
					
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/board.qa.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/qaWriteForm.js"></script>
	<!--JavaScript Local LINK:END-->
