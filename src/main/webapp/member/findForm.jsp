<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
	<!--CSS Local LINK:END-->	
	    
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
 			<h3>아이디/비밀번호 찾기</h3>		
		</div>
	</div>  
	<div class="form-row justify-content-center">	
		<div class="form-group col">	
			<div class="accordion" id="accordionExample">
			<!-- 아이디 찾기:START-->
			  <div class="card">
			    <div class="card-header" id="headingOne">
			      <h2 class="mb-0">
			        <button class="btn btn-link btn-block" type="button" data-toggle="collapse" data-target="#idFind" aria-expanded="true" aria-controls="idFind">
			          	아이디 찾기
			        </button>
		      	  </h2>
		    	</div>
		  	  <div id="idFind" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
			      <div class="card-body">
			        <form id="findIdForm">
						<div class="form-row">	
							<div class="form-group col-md-4">
								<label for="name"><strong>이름</strong></label>
								<input type="text" class="form-control-plaintext inline-form" name="findName" id="findName" data-toggle="tooltip" data-placement="right" title="조회하실 회원명을 입력해 주세요." placeholder="조회대상 회원명 입력"/>
							</div>
							<div class="form-group col-md-7">
						  	  <label for="email"><strong>이메일 주소</strong></label>	
							  <div class="form-row" id="email">
							    <div class="form-group col-md-3">
							      <input type="text" class="form-control-plaintext inline-form" id="findIdemail1" name="findIdemail1" placeholder="이메일 " data-toggle="tooltip" data-placement="bottom" title="이메일을 전부 입력하세요."/>
							    </div>
								<span>@</span>  
							    <div class="form-group col-md-4">
							      	<input type="text" class="form-control-plaintext inline-form" name="findIdemail2" id="findIdemail2" data-toggle="tooltip" data-placement="bottom" title="이메일을 전부 입력하세요."/>
							    </div>
							    <div class="form-group col-md-4">
							      <select id="findIdInputEmail" class="form-control">
									<option value="">직접입력</option>   
									<option value="gmail.com">gmail.com</option>
									<option value="hanmail.net">hanmail.net</option>
									<option value="naver.com">naver.com</option>
							      </select>
							    </div>
							  </div>
							</div>					  		  	
						</div>	
		
						<div class="row">
							<div class="col" align="right">          	
								<button type="reset" id="resetLogin" class="btn btn-outline-secondary">다시 작성</button>	
								<button type="button" id="findIdBtn" class="btn btn-outline-info">아이디 찾기</button>			
							</div>
						</div>		
						<div id="findIdResult"></div>												
					</form>	
														 
		      	</div>   
		    </div>
		  </div>
			<!-- 아이디 찾기:END-->
			<!-- 비밀번호 찾기:START-->	 
			  <div class="card">
			    <div class="card-header" id="headingTwo">
			      <h2 class="mb-0">
			        <button class="btn btn-link btn-block" type="button" data-toggle="collapse" data-target="#pwdFind" aria-expanded="true" aria-controls="pwdFind">
			          	비밀번호 찾기
			        </button>
		      	  </h2>
		    	</div>
		  	  <div id="pwdFind" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
			      <div class="card-body">
			        <form id="findPwdForm">
						<div class="form-row">	
							<div class="form-group col-md-4">
								<label for="findID"><strong>아이디</strong></label>
								<input type="text" class="form-control-plaintext inline-form" name="findID" id="findID" data-toggle="tooltip" data-placement="right" title="조회하실 아이디를 입력해 주세요." placeholder="조회대상 아이디 입력"/>
							</div>
							<div class="form-group col-md-7">
						  	  <label for="email"><strong>이메일 주소</strong></label>	
							  <div class="form-row" id="email">
							    <div class="form-group col-md-3">
							      <input type="text" class="form-control-plaintext inline-form" id="findPwdemail1" name="findPwdemail1" placeholder="이메일 " data-toggle="tooltip" data-placement="bottom" title="이메일을 전부 입력하세요."/>
							    </div>
								<span>@</span>  
							    <div class="form-group col-md-4">
							      	<input type="text" class="form-control-plaintext inline-form" name="findPwdemail2" id="findPwdemail2" data-toggle="tooltip" data-placement="bottom" title="이메일을 전부 입력하세요."/>
							    </div>
							    <div class="form-group col-md-4">
							      <select id="findPwdInputEmail" class="form-control">
									<option value="">직접입력</option>   
									<option value="gmail.com">gmail.com</option>
									<option value="hanmail.net">hanmail.net</option>
									<option value="naver.com">naver.com</option>
							      </select>
							    </div>
							  </div>
							</div>					  		  	
						</div>	
		
						<div class="form-row">
							<div class="form-group col" align="right">          	
								<button type="reset" id="resetLogin" class="btn btn-outline-secondary">다시 작성</button>	
								<button type="button" id="findPwdBtn" class="btn btn-outline-info">비밀번호 찾기</button>			
							</div>
						</div>		
						<div id="findPwdResult"></div>												
					</form>	
														 
		      	</div>   
		    </div>
		  </div>	
			<!-- 비밀번호 찾기:END-->	 	 	
			</div>
 		</div>
 	</div>	
	<div class="form-row">
		<div class="form-group col"  align="center">
				<a class="button btn-outline-info" href="/minishop/member/writeForm.do" style="padding-right: 20px;"><i class="fas fa-user-plus">회원가입</i></a>	
				<a class="button btn-outline-info" href="/minishop/member/loginForm.do" style="padding-right: 20px;"><i class="fas fa-sign-in-alt">로그인하기</i></a>					     		     
				<a class="button btn-outline-info" href="javascript:history.back()" ><i class="fas fa-undo-alt">돌아가기</i></a>
		</div>				
	</div> 			  
</div> 
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/member.find.js"></script>
	<!--JavaScript Local LINK:END-->