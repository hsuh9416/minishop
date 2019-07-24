<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	
		<!-- 리뷰 글쓰기 폼 -->	  
 <div class="reviewForm-container">
 	<div class="container">
 	<!-- 실행 메뉴 -->
	 <nav aria-label="breadcrumb">
	  <ol class="breadcrumb">
	    <li class="breadcrumb-item active" aria-current="page">고객 리뷰 게시글 작성</li>       	    
	  </ol>
	</nav>
	<input type="hidden" id="user_id" value="${memberDTO.id}">	
	<input type="hidden" id="name" value="${memberDTO.name}">	
	   <div class="form-row">
		    <div class="form-group col-md-6">
		      <label for="review_subject"><strong>제목</strong></label>
		      <input type="text" class="form-control" name="review_subject" id="review_subject" placeholder="제목을 입력해주세요.">
		    </div>
		    <div class="form-group col-md-2">
		     <label for="productid"><strong>상품명</strong></label>
		      <select id="productid" name="productid" class="form-control">
				<option value="">[리뷰상품]</option>   
		      </select>		    
		    </div>
		    <div class="form-group col-md-3" id="imgDiv"></div>	    
		    <div class="form-group col-md-1"></div>
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-12">
		      <label for="review_content"><strong>글 내용</strong></label>
		      <textarea id="review_content" name="review_content"></textarea>
		    </div>	    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-2">
		      <label for="review_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" name="review_pwd" id="review_pwd" placeholder="비밀번호 입력">
		    </div>	 
		    <div class="form-group col-md-2">
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
 	</div> 
 </div>
<input type="hidden" id="review_state"  name="review_state" value="0"/>
<div id="missing"></div>		




<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript"  src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"	src="/minishop/resources/ckeditor_4.12.1_full/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/board.review.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	//에디터 설정
	var content = CKEDITOR.replace('review_content',{
		customConfig : '/minishop/resources/ckeditor_4.12.1_full/ckeditor/config.js'
	});
	//	

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
		}//success
	});//ajax
});//ready

</script>
