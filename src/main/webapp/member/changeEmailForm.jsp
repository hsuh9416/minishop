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
 			<h2 class="first">[이메일 재설정]</h2>		
 		</div>
	</div>

	<form id="changeEmailForm">
		<div class="card card-body">	
			<div class="form-row">	
			    <div class="form-group col-md-3">
			      <input type="text" class="form-control" id="changeEmail1" name="changeEmail1" placeholder="이메일 " data-toggle="tooltip" data-placement="bottom" title="이메일을 입력하세요."/>
			    </div>
				<span>@</span>  
			    <div class="form-group col-md-3">
			      	<input type="text" class="form-control" name="changeEmail2" id="changeEmail2" data-toggle="tooltip" data-placement="bottom" title="이메일 인증을 하시기 바랍니다.">
			    </div>
			    <div class="form-group col-md-3">
			      <select id="inputchangeEmail" name="changeEmail2" class="form-control">
					<option value="">직접 입력</option>   
					<option value="gmail.com">gmail.com</option>
					<option value="hanmail.net">hanmail.net</option>
					<option value="naver.com">naver.com</option>
			      </select>
			    </div>
				<div class="form-group col-md-2">
					<button class="btn btn-outline-info" id="goConfirm" type="button">인증</button>
				</div>		    	
			</div>	

	
			<div id="confirmDiv" class="form-row">
				<div class="form-group col-md-3"> 
					<input type="text" class="form-control-plaintext" value="인증번호 입력 : "/>
				</div>		
				<div class="form-group col-md-3"> 
					<input type="text" class="form-control" id="confirmNum" name="confirmNum" placeholder="인증번호"/>
				</div>
				<div class="form-group col-md-5"> 
					<button class="btn btn-outline-info" id="getConfirm" name="getConfirm" type="button">인증받기</button> 
					<button class="btn btn-outline-success" id="doConfirm" name="doConfirm" type="button">인증확인</button> 				      
				</div>	    
			</div>	
			
			<div class="row">
				<div class="col" align="right">          	
					<button type="button" id="resetBtn" class="btn btn-outline-secondary">다시 작성</button>	
					<button type="button" id="resetEmailBtn" class="btn btn-outline-info" disabled>이메일 재설정</button>			
				</div>
			</div>			
		</div>	
	</form>	
</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/changeEmailForm.js"></script>
	<!--JavaScript Local LINK:END-->