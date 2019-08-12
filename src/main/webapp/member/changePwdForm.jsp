<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">
 	<!--CSS Local LINK:END-->

 <div class="container">       
 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h2 class="first">[비밀번호 재설정]</h2>
 		</div>
	</div>
	<form id="changePwdForm">
		<div class="card card-body">	
			<div class="form-row justify-content-center">	
				<div class="form-group col-7">
					<label for="pwd"><strong>기존 비밀번호</strong></label>
					<input type="password" class="form-control" name="pwd" id="pwd" data-toggle="tooltip" data-placement="right" title="기존 비밀번호를 입력하세요." placeholder="기존 비밀번호"/>
				</div>
			</div>
			<div class="form-row justify-content-center">	
				<div class="form-group col-7">
					<label for="newPwd"><strong>새 비밀번호</strong></label>
					<input type="password" class="form-control" name="newPwd" id="newPwd" data-toggle="tooltip" data-placement="right" title="새 비밀번호를 입력하세요." placeholder="새 비밀번호"/>
				</div>
			</div>
			<div class="form-row justify-content-center">	
				<div class="form-group col-7">
					<label for="rePwd"><strong>비밀번호 재입력</strong></label>
					<input type="password" class="form-control" name="rePwd" id="rePwd" data-toggle="tooltip" data-placement="right" title="비밀번호가 일치하지 않습니다." placeholder="비밀번호 재확인"/>
				</div>
			</div>	

			<div class="row">
				<div class="col" align="right">          	
					<button type="reset" class="btn btn-outline-secondary">다시 작성</button>	
					<button type="button" id="resetPwdBtn" class="btn btn-outline-info">비밀번호 재설정</button>			
				</div>
			</div>			
		</div>					
	</form>	
</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/changePwdForm.js"></script>
	<!--JavaScript Local LINK:END-->