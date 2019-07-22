<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="/mallproject/resources/custom/css/userboard.css">

<div class="qaForm-container">
	<div class="container">
	 	<!-- 실행 메뉴 -->
		 <nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item active" aria-current="page">고객 문의 게시글</li>       	    
		  </ol>
		</nav>
	</div>
<div class="container-fluid">
  <form name="qaViewForm">
	<input type="hidden" id="pg" name="pg" value="${pg}"/>
	<input type="hidden" id="qa_seq" name="qa_seq" value="${qaBoardDTO.qa_seq }"/>
	<input type="hidden" id="qa_pseq" name="qa_pseq" value="${qaBoardDTO.qa_seq}"/>
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
			   		<td class="align-middle" >${qaBoardDTO.qa_subject}</td>			   		
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
			   		<i id="adminAns">관리자 답변</i>
			   		</th>		   				   		   		
			   	</tr>

			   	<tr>
			   		<td id="answer" colspan="5" class="align-middle">
			   		<c:if test="${qaBoardDTO.qa_reply=='0'}">
			   			아직 답변이 존재하지 않습니다.
			   		</c:if>
				   	<c:if test="${qaBoardDTO.qa_reply=='1'}">
				   	    [관리자의 답변이 들어오는 자리입니다.]
				   	</c:if>				   		
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
			  <c:if test="${qaBoardDTO.user_id==memberDTO.id }">
			  	<c:if test="${qaBoardDTO.qa_reply=='0'}">
				<input type="button" class="btn btn-outline-dark" value="문의 수정하기"  id="qaModifyBtn">			  	
			  	</c:if>
			  	<c:if test="${qaBoardDTO.qa_reply=='1'}">
				<input type="button" class="btn btn-outline-dark" value="답변시 수정불가"  disabled>			  	
			  	</c:if>
				<input type="button" class="btn btn-outline-dark" value="문의 삭제하기" id="qaDeleteBtn">			
			  </c:if>
				<input type="button" value="목록" class="btn btn-outline-dark"
					onclick="location.href='/mallproject/board/qa/qaList.do?pg=${pg}'">										    									
				</div>
		    </div>	
		</div>	
</div>

<script type="text/javascript"  src="/mallproject/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript"  src="/mallproject/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/custom/js/board.qa.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if('${qaBoardDTO.qa_reply}'=='1'){
		$('#adminAns').removeClass('far fa-question-circle');
		$('#adminAns').addClass('fas fa-check-circle');		
		$.ajax({
			type : 'post',
			url : '/mallproject/board/qa/getQaAns.do',
			data : {'seq' : '${qaBoardDTO.qa_seq}'},
			dataType : 'json',
			success : function(data){
				data.adminboardDTO.admin_content.replace(/(?:\r\n|\r|\n)/g, '<br />');
				$('#answer').html(data.adminboardDTO.admin_content);
			}
		});		
	}//if
	else{
		$('#adminAns').addClass('far fa-question-circle');
		$('#adminAns').removeClass('fas fa-check-circle');				
	}//else
});
</script>