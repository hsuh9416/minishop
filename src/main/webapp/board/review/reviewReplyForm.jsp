<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	
		<!-- 리뷰 글쓰기 폼 -->	  
<div class="col-lg-8">
	<div class="row" id="titleDiv">
	 	<div class="col" align="center" style="padding-bottom: 20px;">
	 		<h3>리뷰 답글</h3>		
	 	</div>
	</div>
	<form id="replyForm">	
		<input type="hidden" name="review_pseq" id="review_pseq" value="${review_pseq}"/>
	   <div class="form-row">
		    <div class="form-group col-md-4">
		      <label for="review_subject"><strong>제목</strong></label>
		      <input type="text" class="form-control" id="review_subject" placeholder="제목을 입력해주세요.">
		    </div>
		    <div class="form-group col-md-3">
		     <label for="productid"><strong>상품명</strong></label>
		      <select id="productid" class="form-control" disabled>
				<option value="">[리뷰상품]</option>   
		      </select>		    
		    </div>
		    <div class="form-group col-md-3" id="imgDiv"></div>	    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-10">
		      <label for="review_content"><strong>글 내용</strong></label>
		      <textarea id="review_content"></textarea>
		    </div>	    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-3">
		      <label for="review_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" id="review_pwd" placeholder="비밀번호 입력">
		    </div>	 
		    <div class="form-group col-md-3">
		      <label for="review_repwd"><strong>재확인</strong></label>
		     <input type="password" class="form-control" id="review_repwd" placeholder="비밀번호 재확인">
		    </div>			       
	   </div>	   
	   <input type="hidden" id="review_state"  name="review_state" value="0"/>
	   </form>
		<div id="missing"></div>
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">      
					<input type="button" class="btn btn-outline-dark" value="답글 올리기" id="replyWriteBtn">
					<input type="button" id="reviewReset" class="btn btn-outline-dark" value="다시작성">
					<input type="button" id="reviewReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
				</div>
			</div>	
		</div>					

 </div>
		




<script type="text/javascript" src="/minishop/resources/ckeditor4/ckeditor.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
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
			$('#productid').val('${productid}');	
			if($("#productid option:selected").val()!=''){
				var getImgOn='<img style="height:80px;weight:60px;" src="/minishop/storage/'+$("#productid option:selected").val()+'.jpg">';
				$('#imgDiv').html(getImgOn);
			}
		}//success
	});//ajax
});//ready

</script>
