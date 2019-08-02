<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">
	
	<div class="col-lg-8">
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>후기 게시판</h3>		
	 		</div>
		</div>
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
		<div class="row justify-content-center" style="padding-bottom:20px;">
				<nav aria-label="Page navigation example">
				  <ul class="pagination justify-content-center" id="boardPagingDiv"></ul>
				</nav>
		</div>		
		
<div class="row justify-content-center">
	<form id="reviewSearch" name="reviewSearch">
		<div class="form-row justify-content-center">
		   <span>
			<input type="hidden" name="pg" value="1">
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
		</div>
  	</form>
</div>  		
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
					src : '/minishop/storage/showProduct.do?product_name_image='+items.productid,
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
				for(i=0; i<items.review_lev; i++){
					$('.'+items.review_seq).before('&emsp;');
				}
				if(items.review_pseq!=0){
					$('.'+items.review_seq).before($('<i/>',{
						class : 'fab fa-replyd'
					}));
				}				
			});//each
			
			$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
			
			$('#reviewTable').on('click','#subjectA',function(){
					var review_seq = $(this).parent().prev().text();
					window.location='/minishop/board/review/reviewView.do?review_seq='+review_seq+'&pg='+$('#pg').val();
			});//제목 클릭시!
		}//success
	});//ajax
});//onready

</script>
