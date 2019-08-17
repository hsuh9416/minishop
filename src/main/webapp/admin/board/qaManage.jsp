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
				<h3>답변대기중인 문의글 현황</h3>
			</div>	
		</div>
		
		<input type="hidden" id="pg" value="${pg}"/>	
		<div class="table">
			<table id="qaTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">#</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">상품번호</th>					
					<th scope="col">작성일</th>
					<th scope="col">구분</th>					
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

	<div class="container-fluid">
			<nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center" id="boardPagingDiv"></ul>
			</nav>
	</div>
 			
	</div>	


	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/qa/board.qa.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminBoard/qaManage.js"></script>
	<!--JavaScript Local LINK:END-->