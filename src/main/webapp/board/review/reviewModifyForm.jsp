<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	<!--CSS Local LINK:END-->
		  
<div class="col-lg-8">

	<div class="form-row" id="titleDiv">
	 	<div class="col">
	 		<h3>후기 수정</h3>		
	 	</div>
	</div>
	
	<input type="hidden" id="pg" value="${pg}"/>
	<input type="hidden" id="review_seq" value="${reviewboardDTO.review_seq}"/>	
	<input type="hidden" name="productid"  value="${reviewboardDTO.productid}">	
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-4">
			<label for="review_subject"><strong>제목</strong></label>
			<input type="text" class="form-control" id="review_subject" value="${reviewboardDTO.review_subject}">
		</div>
		<div class="form-group col-md-3">
			<label for="productid"><strong>상품명</strong></label>
				<select id="productid" class="form-control">
					<option value="">[리뷰상품]</option>   
				</select>		    
		</div>
		<div class="form-group col-3" id="imgDiv"></div>	   
	</div>
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-10">
			<label for="review_content"><strong>글 내용</strong></label>
			<textarea id="review_content">${reviewboardDTO.review_content}</textarea>
		</div>	    
	</div>
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-8"></div>
		<div class="form-group col-3">
		     <label for="review_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" id="review_pwd" placeholder="비밀번호 입력">
		     <input type="hidden" id="qa_check" value="${reviewboardDTO.review_pwd}">		
		</div>	 
	</div>			    			       
	   
	<div class="form-row justify-content-center">
		<div class="col" align="center">       
			<input type="button" id="reviewModBtn" class="btn btn-outline-dark" value="리뷰 수정" >
			<input type="button" id="reviewReset" class="btn btn-outline-dark" value="다시작성">
			<input type="button" id="reviewReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
		</div>
	</div>	

	<div class="form-row justify-content-center">
		<div class="form-group col-5" id="missingMod"></div>		
	</div> 
							
 </div> 

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/ckeditor4.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/review/board.review.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/review/reviewModifyForm.js"></script>
	<!--JavaScript Local LINK:END-->
