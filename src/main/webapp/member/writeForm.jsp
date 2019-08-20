<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
	<!--CSS Local LINK:END-->	
	    
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>회원 가입</h3>
		</div>
	</div>  
	
	<form id="writeForm">	
		<div class="form-row">	
			<div class="form-group col-4">
				<label for="name"><strong>이름</strong></label>
				<input type="text" class="form-control-plaintext inline-form" name="name" id="name" data-toggle="tooltip" data-placement="right" title="회원님의 이름을 입력하세요." placeholder="이름"/>
			</div>
			<div class="form-group col-4">
				<label for="id"><strong>아이디</strong></label>
				<input type="text" class="form-control-plaintext inline-form" name="id" id="id" data-toggle="tooltip" data-placement="right" title="아이디를 입력하세요" placeholder="아이디" >
			</div>					  		  	
		</div>	
		<div class="form-row">
			<div class="form-group col-4">
				<label for="pwd"><strong>비밀번호</strong></label>
				<input type="password" class="form-control-plaintext inline-form" name="pwd" id="pwd" data-toggle="tooltip" data-placement="bottom" title="비밀번호를 입력하세요." placeholder="비밀번호"/>
			</div>
			<div class="form-group col-4">
				<label for="repwd"><strong>재입력</strong></label>
				<input type="password" class="form-control-plaintext inline-form" name="repwd" id="repwd" placeholder="비밀번호 재입력 " data-toggle="tooltip" data-placement="bottom" title="비밀번호와 일치해야 합니다."/>
			</div>    
		 </div>				   							
		<label for="email"><strong>이메일 주소</strong></label>	
		<div class="form-row" id="email">
			<div class="form-group col-3">
				<input type="text" class="form-control-plaintext inline-form" id="email1" name="email1" placeholder="이메일 " data-toggle="tooltip" data-placement="bottom" title="이메일을 입력하세요."/>
			</div>
			<span>@</span>  
			<div class="form-group col-3">
				<input type="text" class="form-control-plaintext inline-form" name="email2" id="email2" data-toggle="tooltip" data-placement="bottom" title="이메일 인증을 하시기 바랍니다."/>
			</div>
			<div class="form-group col-3">
				<select id="inputEmail" class="form-control-plaintext inline-form" style="padding-top: 10px;">
						<option value="">직접입력</option>   
						<option value="gmail.com">gmail.com</option>
						<option value="hanmail.net">hanmail.net</option>
						<option value="naver.com">naver.com</option>
				</select>
			</div>
			<div class="form-group col-md-1">
				<a id="goConfirm"><i class="fas fa-question">인증</i></a> 
			</div>
		</div>
		<div id="confirmDiv" class="form-row">
			<div class="form-group col-4"> 
				<input type="text" class="form-control-plaintext inline-form" id="confirmNum" name="confirmNum" placeholder="인증번호 입력"/>
			</div>
			<div class="form-group col-md-4"> 
				<button class="btn btn-outline-info" id="getConfirm" name="getConfirm" type="button">인증받기</button> 
				<button class="btn btn-outline-success" id="doConfirm" name="doConfirm" type="button">인증확인</button> 				      
				</div>	    
			</div>
		<label for="tel"><strong>연락처</strong></label>	
		<div class="form-row" id="tel">
			<div class="form-group col-3">
				<select name="tel1" id="tel1" class="form-control-plaintext inline-form" style="padding-top: 10px;">
					<option value="010">010</option>
					<option value="011">011</option>
					<option value="011">016</option>						
					<option value="019">019</option>
				</select>
			</div>	
			<span>-</span>  
			<div class="form-group col-3">
				<input type="text" class="form-control-plaintext inline-form" id="tel2" name="tel2" value="${memberDTO.tel2}">
			</div>
			<span>-</span>      
			<div class="form-group col-3">
				<input type="text" class="form-control-plaintext inline-form" name="tel3" id="tel3" value="${memberDTO.tel3}">
			</div>
		</div>		
		<div class="form-row">
			<div class="form-group col-8">
				<input type="checkbox" id="policyCheck" value="1">Kissin'Bus의 가입 약관에 동의 합니다.
			</div>
			<div class="form-group col-4">
				<input type="button" class="btn btn-danger" id="policyPop" value="약관 확인"/>
			</div>
		</div>						    
		<div class="form-row">
			<div class="form-group col" align="center">          	
				<button type="reset" id="resetWrite" class="btn btn-outline-secondary">다시 작성</button>								
				<button type="button" id="writeBtn" class="btn btn-outline-success">회원 가입</button>		
			</div>
		</div>	
		<input type="hidden" id="checkId" value="">
		<input type="hidden" id="checkEmail" value="">						
		<div id="writeResult"></div>											
	</form>
		<div class="form-row" style="padding-top: 20px;">
			<div class="form-group col" align="center">
				<a class="button btn-outline-info" href="/minishop/member/loginForm.do" style="padding-right: 20px;"><i class="fas fa-sign-in-alt">로그인하기</i></a>				     		     
				<a class="button btn-outline-info" href="javascript:history.back()" ><i class="fas fa-undo-alt">돌아가기</i></a>
			</div>				
		</div> 					
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/member.join.js"></script>
	<!--JavaScript Local LINK:END-->

