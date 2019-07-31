<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">

		<!-- 리뷰 글쓰기 폼 -->	  
<div class="col-lg-8">
	<div class="row" id="titleDiv">
	 	<div class="col" align="center" style="padding-bottom: 20px;">
	 		<h3>후기 작성</h3>		
	 	</div>
	</div>
	<input type="hidden" id="user_id" value="${memberDTO.id}">	
	<input type="hidden" id="name" value="${memberDTO.name}">	
	<input type="hidden" id="review_state"  name="review_state" value="0"/>	
	   <div class="form-row justify-content-center">
		    <div class="form-group col-4">
		      <label for="review_subject"><strong>제목</strong></label>
		      <input type="text" class="form-control" name="review_subject" id="review_subject" placeholder="제목을 입력해주세요.">
		    </div>
		    <div class="form-group col-3">
		     <label for="productid"><strong>상품명</strong></label>
		      <select id="productid" name="productid" class="form-control">
				<option value="">[리뷰상품]</option>   
		      </select>		    
		    </div>
		    <div class="form-group col-3" id="imgDiv"></div>	    
	   </div>
	   <div class="form-row justify-content-center">
		    <div class="form-group col-10">
		      <label for="review_content"><strong>글 내용</strong></label>
		      <textarea id="review_content" name="review_content" rows="10" cols="100"></textarea>
		    </div>	    
	   </div>
	   <div class="form-row justify-content-center">
		    <div class="form-group col-3">
		      <label for="review_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" name="review_pwd" id="review_pwd" placeholder="비밀번호 입력">
		    </div>	 
		    <div class="form-group col-3">
		      <label for="review_repwd"><strong>재확인</strong></label>
		     <input type="password" class="form-control" name="review_repwd" id="review_repwd" placeholder="비밀번호 재확인">
		    </div>			    		       
	   </div>	   
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">      
					<input type="button" class="btn btn-outline-dark" value="리뷰 올리기" id="reviewWriteBtn">
					<input type="button" id="reviewReset" class="btn btn-outline-dark" value="다시작성">
					<input type="button" id="reviewReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
				</div>
			</div>	
		</div>					
		<div id="missing"></div>
 </div>

		
<script type="text/javascript" src="/minishop/resources/ckeditor4/ckeditor.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/board.review.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/ckeditor4.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	
	$.ajax({
		type : 'get',
		url : '/minishop/product/getAllproduct.do',
		dataType : 'json',
		success : function(data){
			$('#productid option:gt(0)').empty();
			$.each(data.productList, function(index, items){
			$('<option/>',{
				align : 'center',
				value : items.productID,
				text : items.productID
			}).appendTo($('#productid'));
			});//eachs	
		$('#productid').val('${qaBoardDTO.productid}');	
		}//success
	});//ajax
});//ready



</script>
