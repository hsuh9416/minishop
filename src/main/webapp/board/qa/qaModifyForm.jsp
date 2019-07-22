<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<link rel="stylesheet" type="text/css" href="/mallproject/resources/custom/css/userboard.css">
		<!-- 문의글 수정 폼 -->	
<input type="hidden" name="qa_seq" id="qa_seq" value="${qaBoardDTO.qa_seq}">
<input type="hidden" name="pg" id="pg" value="${pg}">
<input type="hidden" name="user_id" id="user_id" value="${qaBoardDTO.user_id}">		  
 <div class="qaForm-container">
 	<div class="container">
 	<!-- 실행 메뉴 -->
	 <nav aria-label="breadcrumb">
	  <ol class="breadcrumb">
	    <li class="breadcrumb-item active" aria-current="page">문의 게시글 수정</li>       	    
	  </ol>
	</nav>
	   <div class="form-row">
		    <div class="form-group col-md-6">
		      <label for="qa_subject"><strong>제목</strong></label>
		      <input type="text" class="form-control" name="qa_subject" id="qa_subject" value="${qaBoardDTO.qa_subject}">
		    </div>
		    <div class="form-group col-md-3">
		     <label for="productid"><strong>상품명</strong></label>
		      <select id="productid" name="productid" class="form-control">
				<option value="">[문의대상(선택)]</option>   
		      </select>		    
		    </div>
		    <div class="form-group col-md-2" id="imgDiv"></div>	    
		    <div class="form-group col-md-1">
		       <button  id="isSecret" class="btn btn-outline-secondary"><i class="fas fa-lock"></i></button>
		    </div>	    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-12">
		      <label for="qa_content"><strong>글 내용</strong></label>
		     <textarea class="form-control" id="qa_content" name="qa_content" placeholder="문의하실 글의 내용을 입력해주세요.">${qaBoardDTO.qa_content}</textarea>
		    </div>	    
	   </div>
	   <div class="form-row">
		    <div class="form-group col-md-2">
		      <label for="qa_pwd"><strong>글 비밀번호</strong></label>
		     <input type="password" class="form-control" name="qa_pwd" id="qa_pwd" placeholder="비밀번호 입력">
		     <input type="hidden" name="qa_check" id="qa_check" value="${qaBoardDTO.qa_pwd}">
		    </div>	 		       
	   </div>	   
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">      
					<input type="button" class="btn btn-outline-dark" value="수정하기" id="qaModifyBtn">
					<input type="button" id="qaReset" class="btn btn-outline-dark" value="다시작성">
					<input type="button" id="qaReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
				</div>
			</div>	
		</div>					
 	</div> 
 </div>
<input type="hidden" id="qa_state"  name="qa_state" value="0"/>
<div id="missingMod"></div>	

<script type="text/javascript"  src="/mallproject/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript"  src="/mallproject/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/custom/js/board.qa.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'get',
		url : '/mallproject/product/getProductList.do',
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
			var getImgOn='<img style="height:80px;weight:60px;" src="/mallproject/storage/'+$("#productid option:selected").val()+'.jpg">';
			$('#imgDiv').html(getImgOn);			
		}//success
	});//ajax
	$('#productid').change(function(){
		$('#imgDiv').empty();
		if($("#productid option:selected").val()!=''){
			var getImgOn='<img style="height:80px;weight:60px;" src="/mallproject/storage/'+$("#productid option:selected").val()+'.jpg">';
			$('#imgDiv').html(getImgOn);
		}
	});//선택지가 바뀔 때 마다	

	
});
</script>









