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
<div class="qaForm-container">
	<div class="container">
	 	<!-- 실행 메뉴 -->
		 <nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item active" aria-current="page">고객 문의 답변</li>       	    
		  </ol>
		</nav>
	</div>
<div class="container-fluid">
  <form name="qaViewForm">
	<input type="hidden" id="pg" name="pg" value="${pg}"/>
	<input type="hidden" id="admin_pseq" name="admin_pseq" value="${qaBoardDTO.qa_seq }"/>
	<input type="hidden" id="qa_pseq" name="qa_pseq" value="${qaBoardDTO.qa_seq}"/>
	<input type="hidden" id="user_id" name="user_id" value="${qaBoardDTO.user_id}"/>	
		<div class="table-responsive">
			<table id="viewTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">글번호</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">상품번호</th>					
					<th scope="col">작성일</th>			
			  </tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<th scope="row" class="align-middle">${qaBoardDTO.qa_seq}</th>
			   		<td class="align-middle">${qaBoardDTO.qa_subject}</td>			   		
			   		<td class="align-middle">${qaBoardDTO.name}</td>
			   		<td class="align-middle">
			   		<c:if test="${qaBoardDTO.productid==null}">[문의 상품 없음]</c:if>
			   		<c:if test="${qaBoardDTO.productid!=null}">[${qaBoardDTO.productid}]</c:if>
			   		
			   		</td>		
			   		<td class="align-middle"><fmt:formatDate value="${qaBoardDTO.qa_logtime}" pattern="yyyy년 MM월 dd일"/></td>		   				   		   		
			   	</tr>
			   	<tr class="thead-dark">
			   		<th scope="row" colspan="5" class="align-middle">문의하신 내용</th>		   				   		   		
			   	</tr>	
			   	<tr>
			   		<td scope="row" colspan="5" class="align-middle">${qaBoardDTO.qa_content}</td>	   				   		   		
			   	</tr>				   					   			   	
			   	<tr class="thead-dark">
			   		<th scope="row" colspan="5" class="align-middle">
			   		<i id="adminAns" class="fas fa-check-circle">관리자 답변</i>
			   		</th>		   				   		   		
			   	</tr>

			   	<tr>
			   		<td id="answer" colspan="5" class="align-middle">
						  <textarea id="admin_content" class="md-textarea form-control" rows="3"></textarea>		   		
			   		</td>	   				   		   		
			   	</tr>				   		   				   	
			</tbody>
		</table>
	  </div>
	</form>	
</div>
		<div class="form-group" id="btnDiv">
			<div class="row">
				<div class="col" align="right">   
				<input type="button" class="btn btn-outline-dark" id="adminWriteBtn" value="답변 작성">			  	
				<input type="button"  id="resetAns" class="btn btn-outline-dark" value="답변 재작성">			
				<input type="button" value="돌아가기" class="btn btn-outline-dark"
					onclick="location.href='/mallproject/admin/board/qaManage.do?pg=${pg}'">										    									
				</div>
		    </div>	
		</div>	
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mallproject/js/board.qa.js"></script>
<script type="text/javascript">
$('#review_content').summernote({
    placeholder: 'Hello stand alone ui',
    tabsize: 2,
    height: 100
  });

$('#adminWriteBtn').click(function(){
	$.ajax({
		type : 'post',
		url : '/mallproject/admin/board/qaManageWrite.do',
		data : {'admin_pseq':$('#admin_pseq').val(),
				'user_id':$('#user_id').val(),	
				'admin_content' :$('#admin_content').val()},
		success :function(){
			alert('답변 작성을 완료하였습니다. 목록으로 돌아갑니다.');
			window.location='/mallproject/admin/board/qaManage.do?pg='+$('#pg').val();
		}
	});
});
$('#resetAns').click(function(){
	window.location.reload();
});
</script>