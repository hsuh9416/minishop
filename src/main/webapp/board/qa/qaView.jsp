<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userboard.css">

	<div class="col-lg-8">
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>고객 문의글</h3>		
	 		</div>
		</div>
 <form name="qaViewForm">
	<input type="hidden" id="pg" name="pg" value="${pg}"/>
	<input type="hidden" id="qa_seq" name="qa_seq" value="${qaBoardDTO.qa_seq}"/>
	<input type="hidden" id="qa_pseq" name="qa_pseq" value="${qaBoardDTO.qa_seq}"/>
	<input type="hidden" id="qa_pwd" value="${qaBoardDTO.qa_pwd}"/>
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
		<div class="form-group" id="btnDiv">
			<div class="row">
				<div class="col" align="right">   
			  <c:if test="${qaBoardDTO.user_id==memberDTO.id }">
			  	<c:if test="${qaBoardDTO.qa_reply=='0'}">
				<input type="button" class="btn btn-outline-dark" value="문의 수정하기"  id="qaModifyFormBtn">			  	
			  	</c:if>
			  	<c:if test="${qaBoardDTO.qa_reply=='1'}">
				<input type="button" class="btn btn-outline-dark" value="답변시 수정불가"  disabled>			  	
			  	</c:if>
				<input type="button" class="btn btn-outline-dark" value="문의 삭제하기" id="qaDeleteBtn">			
			  </c:if>
				<input type="button" value="목록" class="btn btn-outline-dark"
					onclick="location.href='/minishop/board/qa/qaList.do?pg=${pg}'">										    									
				</div>
		    </div>	
		</div>	
		<div id="pwdConfirm" class="form-group row justify-content-center">
			<input type="hidden" id="purpose" value=""> 
			<div class="col-4">
				<input type="text" id="alertText" readonly class="form-control-plaintext" value="글의 비밀번호를 입력하세요"/>
			</div>
			<div class="col-3">
				<input type="password" class="form-control" placeholder="비밀번호 입력" id="rePwd"/>			
			</div>
			<div class="col-2">
				<input type="button" class="btn btn-outline-dark" value="확인" id="checkQaPwd"/>			
			</div>						
		</div>				
	</div>


<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript"  src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/board.qa.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#pwdConfirm').hide();
	if('${qaBoardDTO.qa_reply}'=='1'){
		$('#adminAns').removeClass('far fa-question-circle');
		$('#adminAns').addClass('fas fa-check-circle');		
		$.ajax({
			type : 'post',
			url : '/minishop/board/qa/getQaAns.do',
			data : {'seq' : '${qaBoardDTO.qa_seq}'},
			dataType : 'json',
			success : function(data){
				data.adminboardDTO.admin_content.replace(/(?:\r\n|\r|\n)/g, '<br>');
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