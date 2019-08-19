<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--CSS Local LINK:START-->        
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">	 
	<!--CSS Local LINK:END-->
	
<div class="container">       

 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h2 class="first">[회원 공지 메일]</h2>		
 		</div>
	</div>	

	<div class="card card-body">
		<form id="informSendForm">
			<input type="hidden" id="target" value="${target}"/>
			<input type="hidden" name="id"/>
			<input type="hidden" name="content"/>
			<div class="form-row">	
				<label for="selectTarget">[대상]</label>
				<div class="form-group col-4">
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" id="global" name="selectTarget" class="custom-control-input" value="all" checked>
						<label class="custom-control-label" for="global">전체</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="personal" name="selectTarget" class="custom-control-input" value="person">
					<label class="custom-control-label" for="personal">개별</label>								
					</div>
				</div>	
				<div class="form-group col-4">
					<input type="text" class="form-control" id="inform_target" placeholder="회원 아이디 입력" readonly/>
				</div>
				<div class="form-group col-2" id="findInform">		
					<input type="button" id="searchTarget" class="btn btn-outline-info" value="검색" disabled/>
				</div>				
			</div>
			<div class="form-row justify-content-center">
					<div class="form-group col-10" align="center">
						<font id="targetInfo"></font>	
					</div>		
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-10">
					<label for="subject"><strong>메일 알림 제목</strong></label>
					<input type="text" class="form-control" name="subject"/>	
				</div>			
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-10" >
					<label for="editor_admin" ><strong>메일 알림 내용</strong></label>
					<textarea class="form-control" id="editor_admin"></textarea>					
				</div>			
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-8" id="checkInform"></div>		
			</div>		
			<div class="form-row justify-content-center">
				<div class="form-group col" align="right"> 
					<input type="button" class="btn btn-outline-secondary" id="resetBtn" value="다시작성"/>				
					<input type="button" class="btn btn-outline-info" id="putInformMail" value="메일발송"/>					
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
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/infoWriteForm.js"></script>
	<!--JavaScript Local LINK:END-->