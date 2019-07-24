<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	
<div class="boardList-container">
	<div class="container-fluid">
 	<!-- 실행 메뉴 -->
		 <nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item active"  aria-current="page">후기 게시판</li>			  
		    <li class="breadcrumb-item"><a href="/minishop/board/qa/qaList.do">문의 게시판</a></li>
		  </ol>
		</nav>	
	</div>
</div>
<div class="container-fluid">
		<input type="hidden" id="pg" value="${pg}">
		<div class="table-responsive">
			<table id="reviewTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">#</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">상품번호</th>	
					<th scope="col">상품이미지</th>						
					<th scope="col">조회수</th>										
					<th scope="col">작성일</th>			
			  </tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<th scope="row"></th>
				   		<td colspan="6"></td>		   		
			   	</tr>
			   </tbody> 	  
			</table>
		</div>
</div>

<div class="container-fluid">
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center" id="boardPagingDiv"></ul>
		</nav>
</div>
<br><br>
<div class="container-fluid">
	<form id="reviewSearch" name="reviewSearch">
		<div class="form-row justify-content-center">
		   <span>
			<input type="hidden" name="pg" id="pg" value="1">
			</span>
			<span style="margin-left:20px;">
			<select name="searchOption" id="searchOption" class="form-control">
				<option value="name">작성자</option>
				<option value="user_id">아이디</option>
		        <option value="review_subject">제목</option>
		    </select>
		    </span>
		    <span style="margin-left:20px;">
		    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword }" size="20">
		    </span>
		   <span style="margin-left:20px;">
		    <input type="button" id="reviewSearchBtn" class="btn btn-outline-dark" value="검색">
		   </span>
		   <span style="margin-left:20px;">
		    <input type="button" id="goReviewWrite" class="btn btn-outline-info" value="글쓰기">
		   </span>		   
		</div>
  	</form>
</div>  	 	
<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript"  src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/board.review.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/board/review/getReviewList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			
			$('#reviewTable tr:gt(0)').empty();					
			$.each(data.reviewList, function(index, items){			
				$('<tr/>').append($('<th/>',{
					scope : 'row',
					align : 'center',
					text : items.review_seq
				})).append($('<td/>',{
					}).append($('<a/>',{
						href : 'javascript:void(0)',
						id : 'subjectA',
						text : items.review_subject,
						class : items.review_seq+''
				}))).append($('<td/>',{
					align : 'center',
					text : items.name,
				})).append($('<td/>',{
					align : 'center',
					text : '['+items.productid+']'				
				})).append($('<td/>',{
					align : 'center'			
				}).append($('<img/>',{
					src : '/minishop/storage/'+items.productid+'.jpg',
					width :'100',
					height : '100'
				}))).append($('<td/>',{
					align : 'center',
					text : items.review_hit					
				})).append($('<td/>',{
					align : 'center',
					text : items.review_logtime			
				})).appendTo($('#reviewTable tbody'));
				//답글
				if(items.review_pseq!=0){
					$('.'+items.review_pseq).before($('<img/>',{
						src : '../image/reply.gif'
					}));
				}				
			});//each
			
			$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
			
			$('#reviewTable').on('click','#subjectA',function(){
					var review_seq = $(this).parent().prev().text();
					alert(review_seq);
			});//제목 클릭시!
		}//success
	});//ajax
});//onready

</script>
