<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<!--Bootsrap 4-->
	<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	
    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="/minishop/resources/fontawesome-free-5.9.0-web/css/all.css">

	<!--Custom styles-->
	<link rel="stylesheet" href="/minishop/css/userboard.css">
 <div class="reviewForm-container">
 	<div class="container">
 	<!-- 실행 메뉴 -->
	 <nav aria-label="breadcrumb">
	  <ol class="breadcrumb">
	    <li class="breadcrumb-item active" aria-current="page">고객 리뷰 게시글 작성</li>       	    
	  </ol>
	</nav>
    </div>
		<div class="container-fluid">
		 <form name="reviewViewForm">	
			<input type="hidden" name="pg" value="${reviewBoardDTO.pg}"/>
			<input type="hidden" name="seq" value="${reviewBoardDTO.review_seq }"/>
			<input type="hidden" name="pseq" value="${reviewBoardDTO.review_seq}"/>
			<table id="viewTable" class="table justify-content-center">
			  <thead class="thead-dark">
					<tr>
						<th scope="col">글번호</th>
						<th scope="col">상품번호</th>
						<th scope="col">작성자</th>
						<th scope="col">조회수</th>					
						<th scope="col">작성일</th>		
					</tr>
			  </thead>
			   <tbody>
				    <tr>
						<td>${reviewBoardDTO.review_seq}</td>
						<td>${reviewBoardDTO.productid}</td>
						<td>${reviewBoardDTO.name}</td>
						<td>${reviewBoardDTO.review_hit}</td>					
						<td><fmt:formatDate value="${reviewBoardDTO.review_logtime}" pattern="yyyy년 MM월 dd일"/></td>			    	
			   		</tr>
			   		<tr class="thead-dark">
						<th class="" colspan="5" class="align-middle">${reviewBoardDTO.review_subject}</th>					   		
			   		</tr>
			   		<tr>
			   			<td colspan="5" class="align-middle">
			   				<textarea></textarea>
			   			</td>
			   		</tr>		   
			   </tbody>
			</table>  			
	   	</form>
	   </div>
		<div class="form-group" id="btnDiv">
			<div class="row">
				<div class="col" align="right">   
			  <c:if test="${reviewBoardDTO.user_id==memberDTO.id }">

				<input type="button" class="btn btn-outline-dark" value="수정하기"  id="reviewModifyBtn">
				<input type="button" class="btn btn-outline-dark" value="삭제하기" id="reviewDeleteBtn">				  	
			  </c:if>		  
				<input type="button" class="btn btn-outline-dark" value="답글달기" id="reviewReplyBtn">			  
				<input type="button" value="목록" class="btn btn-outline-dark"
					onclick="location.href='/minishop/board/review/reviewList.do?pg=${pg}'">										    									
				</div>
		    </div>	
		</div>		     
   </div>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
<script type="text/javascript" src="/minishop/js/board.review.js"></script>
<script type="text/javascript"></script>