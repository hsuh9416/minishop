<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	<!--CSS Local LINK:END-->
  
<div class="col-lg-8">

	<div class="form-row" id="titleDiv">
	 	<div class="col">
	 		<h3>문의글 수정</h3>		
	 	</div>
	</div>
	
	<input type="hidden" name="qa_seq" id="qa_seq" value="${qaBoardDTO.qa_seq}">
	<input type="hidden" name="pg" id="pg" value="${pg}">
	<input type="hidden" name="user_id" id="user_id" value="${qaBoardDTO.user_id}">	
	<input type="hidden" name="productid"  value="${qaBoardDTO.productid}">	
	<input type="hidden" id="qa_state" value="${qaBoardDTO.qa_state}"/>
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-4">
			<label for="qa_subject"><strong>제목</strong></label>
			<input type="text" class="form-control" id="qa_subject" value="${qaBoardDTO.qa_subject}">
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
			<textarea class="form-control autosize" id="qa_content" placeholder="문의하실 글의 내용을 입력해주세요.">${qaBoardDTO.qa_content}</textarea>
		</div>	    
	</div>

	<div class="form-row justify-content-center">
		<div class="form-group col-md-6"></div>	 
		<div class="form-group col-3">
		     <label for="qa_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" id="qa_pwd" placeholder="비밀번호 입력">
		     <input type="hidden" name="qa_check" id="qa_check" value="${qaBoardDTO.qa_pwd}">		
		</div>			    
		<div class="form-group col-2" id="changeSecret">
			<c:if test="${qaBoardDTO.qa_state==0}">
				<button  class="btn btn-outline-secondary"><i class="fas fa-lock-open"></i></button>
			</c:if>
			<c:if test="${qaBoardDTO.qa_state==1}">
				<button  class="btn btn-outline-secondary"><i class="fas fa-lock"></i></button>
			</c:if>		    	
		</div>			    		       
	</div>		
  	

	<div class="form-row justify-content-center">
		<div class="col" align="center">      
			<input type="button" class="btn btn-outline-dark" value="수정하기" id="qaModifyBtn">
			<input type="button" id="qaReset" class="btn btn-outline-dark" value="다시작성">
			<input type="button" id="qaReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
		</div>
	</div>	
							
	<div class="form-row justify-content-center">
		<div class="form-group col-5" id="missingMod"></div>		
	</div> 

 </div>


	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/board.qa.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/qaModifyForm.js"></script>
	<!--JavaScript Local LINK:END-->








