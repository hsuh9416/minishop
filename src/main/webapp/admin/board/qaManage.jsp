<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!--Bootsrap 4-->
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	
    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="/mallproject/resources/fontawesome-free-5.9.0-web/css/all.css">

	<!--Custom styles-->
	<link rel="stylesheet" href="/mallproject/css/userboard.css">

<div class="boardList-container">
	<div class="container-fluid">
 	<!-- 실행 메뉴 -->
		 <nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item"><a href="/mallproject/board/review/reviewList.do">후기 게시판</a></li>
		    <li class="breadcrumb-item active"  aria-current="page">문의 게시판</li>	    
		  </ol>
		</nav>	
	</div>
</div>
<div class="container-fluid">
		<input type="hidden" id="pg" value="${pg}">
		<div class="table-responsive">
			<table id="qaTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">#</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">상품번호</th>					
					<th scope="col">작성일</th>
					<th scope="col">구분</th>						
					<th scope="col">답변</th>					
			  </tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<th scope="row"></th>
			   		<td colspan="5"></td>			   		
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
	<form id="qaSearch" name="qaSearch">
		<div class="form-row justify-content-center">
		   <span>
			<input type="hidden" name="pg" id="pg" value="1">
			</span>
			<span style="margin-left:20px;">
			<select name="searchOption" id="searchOption" class="form-control">
				<option value="name">작성자</option>
				<option value="user_id">아이디</option>
		        <option value="qa_subject">제목</option>
		    </select>
		    </span>
		    <span style="margin-left:20px;">
		    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}" size="20">
		    </span>
		   <span style="margin-left:20px;">
		    <input type="button" id="qaSearchBtn" class="btn btn-outline-dark" value="검색">
		   </span>
		</div>
  	</form>
</div>  	


<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mallproject/js/board.qa.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$.ajax({
		type : 'get',
		url : '/mallproject/admin/board/getQaList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			
			$('#qaTable tr:gt(0)').empty();					
			$.each(data.qalist, function(index, items){
				var state = '<i class="fas fa-lock"></i>';
				var subject = '[비밀글로 작성된 문의글입니다]';
				var isreplied ='<i class="far fa-square"></i>';
				var productid ='[비공개]';
				if (items.qa_state=='0'){//공개글
					subject = items.qa_subject;
					state = '<a><i class="fas fa-lock-open"></i></a>';
					if(items.productid!=null){
						productid ='['+items.productid+']';}	
					else if(items.productid==null){productid = '[문의 상품 없음]';}
				}
				if(items.qa_reply=='1'){
					isreplied ='<a><i class="far fa-check-square"></i></a>';
				}
			
				$('<tr/>').append($('<th/>',{
					scope : 'row',
					align : 'center',
					text : items.qa_seq
				})).append($('<td/>',{
					}).append($('<a/>',{
						href : 'javascript:void(0)',
						id : 'subjectA',
						text : subject,
						class : items.qa_seq+''
				}))).append($('<td/>',{
					align : 'center',
					text : items.name,
				})).append($('<td/>',{
					align : 'center',
					text : productid				
				})).append($('<td/>',{
					align : 'center',
					text : items.qa_logtime					
				})).append($('<td/>',{
					align : 'center',
					html : state			
				})).append($('<td/>',{
					align : 'center',
					html : isreplied					
				})).appendTo($('#qaTable tbody'));
			});//each
			
			$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
			
			$('#qaTable').on('click','#subjectA',function(){
				var qa_seq = $(this).parent().prev().text();
				window.location='/mallproject/admin/board/qaManageView.do?qa_seq='+qa_seq+'&pg='+$('#pg').val();
			});//제목 클릭시!
		}//success
	});//ajax
});//onready

function boardPaging(pg){
	location.href='/mallproject/admin/board/qaManage.do?pg='+pg;
}
</script>
