<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		
	<!--CSS Local LINK:START--> 
<link rel="stylesheet" href="/minishop/resources/custom/css/userboard.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
				<h3>답변대기중인 문의글</h3>
			</div>	
		</div>
		
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
			
		<div class="form-group" id="btnDiv">
			<div class="row">
				<div class="col" align="right">   
				<input type="button" class="btn btn-outline-dark" id="adminWriteBtn" value="답변 작성">			  	
				<input type="button"  id="resetAns" class="btn btn-outline-dark" value="답변 재작성">			
				<input type="button" value="돌아가기" class="btn btn-outline-dark"
					onclick="location.href='/minishop/admin/board/qaManage.do?pg=${pg}'">										    									
				</div>
		    </div>	
		</div>			
	</div>
	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/board.qa.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminBoard/qaManageView.js"></script>
	<!--JavaScript Local LINK:END-->
