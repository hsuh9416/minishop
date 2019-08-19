<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
	<!--CSS Local LINK:START-->        
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">	 
	<!--CSS Local LINK:END-->
	
<div class="container">       

 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h2 class="first">[1:1문의답변]</h2>		
 		</div>
	</div>	

	<div class="card card-body">
		<form id="informSendForm">
			<input type="hidden" name="seq" value="${messageDTO.seq}"/>
			<input type="hidden" name="content"/>
			<div class="form-row justify-content-center">
				<div class="col-2">
					<label for="receiver" style="text-decoration:underline;text-underline-position:under;padding-top:5px;"><strong>문의자명</strong></label>
				</div>	
				<div class="col-3" align="center">
					<input type="text" class="form-control-plaintext" readonly name="receiver" value="${messageDTO.sender}"/>
				</div>
					<div class="col-3">
						<label for="senddate" style="text-decoration:underline;text-underline-position:under;padding-top:5px;"><strong>문의일자</strong></label>
					</div>	
					<div class="col-4" align="center" id="senddate">
						<input type="text" class="form-control-plaintext" readonly value="<fmt:formatDate value="${messageDTO.senddate}" pattern="yyyy년 MM월 dd일"/>"/>
					</div>				
			</div>	
			<div class="form-row justify-content-center">
				<div class="col-5">
					<label for="receiver" style="text-decoration:underline;text-underline-position:under;padding-top:5px;"><strong>문의자 회신메일주소</strong></label>
				</div>	
				<div class="col-7" align="center">
					<input type="text" class="form-control-plaintext" readonly name="receiveAddr" value="${messageDTO.sendAddr}"/>
				</div>			
			</div>					
			<div class="form-row justify-content-center">
				<div class="col-5">
					<label for="subject" style="text-decoration:underline;text-underline-position:under;padding-top:5px;"><strong>문의 분류</strong></label>
				</div>				
				<div class="col-7">
					<input type="text" class="form-control-plaintext" readonly name="subject" value="${messageDTO.subject}"/>	
				</div>			
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-10" align="center">
					<label for="primaryText" style="text-decoration:underline;text-underline-position:under"><strong>메일 알림 내용</strong></label>		
				</div>			
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-10" >
					<textarea class="form-control-plaintext" readonly id="primaryText">${messageDTO.content}</textarea>
				</div>			
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-10" align="center">
					<label for="editor_admin" style="text-decoration:underline;text-underline-position:under"><strong>문의에 대한 답변</strong></label>		
				</div>			
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-10" >
					<textarea class="form-control" id="editor_admin"></textarea>					
				</div>			
			</div>	
							
			<div class="form-row justify-content-center">
				<div class="form-group col" align="right"> 
					<input type="button" class="btn btn-outline-secondary" id="resetBtn" value="다시작성"/>				
					<input type="button" class="btn btn-outline-info" id="putQAAnswerMailBtn" value="메일발송"/>					
					<button type="button" id="closeBtn" class="btn btn-outline-dark">닫기</button>
				</div>						
			</div>
		</form>				
	</div>	

</div>
	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/ckeditor4Admin.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/personalQAFormAdmin.js"></script>
	<!--JavaScript Local LINK:END-->