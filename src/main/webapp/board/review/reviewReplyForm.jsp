<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">

	<div class="form-row" id="titleDiv">
	 	<div class="col">
	 		<h3>후기 답글 작성</h3>		
	 	</div>
	</div>

	<input type="hidden" id="review_pseq" value="${review_pseq}"/>	
	<input type="hidden" name="productid" value="${productid}">	
		
	<div class="form-row justify-content-center">
		<div class="form-group col-md-4">
			<label for="review_subject"><strong>제목</strong></label>
		      <input type="text" class="form-control" name="review_subject" id="review_subject" placeholder="제목을 입력해주세요.">
		</div>
		
		<div class="form-group col-md-3">
			<label for="productid"><strong>상품명</strong></label>
			<select id="productid" class="form-control" disabled>
				<option value="">[리뷰상품]</option>   
			</select>		    
		</div>
		<div class="form-group col-3" id="imgDiv"></div>	   
	</div>
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-10">
			<label for="review_content"><strong>글 내용</strong></label>
		    <textarea id="review_content"></textarea>
		</div>	    
	</div>
	
	<div class="form-row justify-content-center">
		<div class="form-group col-md-3">
			<label for="review_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" id="review_pwd" placeholder="비밀번호 입력">
		</div>	 
		<div class="form-group col-md-3">
			<label for="review_repwd"><strong>재확인</strong></label>
		     <input type="password" class="form-control" id="review_repwd" placeholder="비밀번호 재확인">
		</div>				    		       
	</div>
	   
	<div class="form-row justify-content-center">
		<div class="form-group col" align="center">    
			<input type="button" class="btn btn-outline-dark" value="답글 올리기" id="replyWriteBtn">
			<input type="button" id="reviewReset" class="btn btn-outline-dark" value="다시작성">
			<input type="button" id="reviewReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
		</div>
	</div>						

	<div class="form-row justify-content-center">
		<div class="form-group col-5" id="missing"></div>		
	</div>

</div>
		
 	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/ckeditor4.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/review/board.review.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/review/reviewReplyForm.js"></script>
	<!--JavaScript Local LINK:END-->