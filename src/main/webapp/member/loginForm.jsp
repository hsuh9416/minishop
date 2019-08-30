<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
	<!--CSS Local LINK:END-->	

<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
 			<h3>로그인</h3>		
		</div>
	</div>  
	<div class="form-row justify-content-center">	
		<div class="form-group col">
			<div class="accordion" id="loginSpace">					
				<div class="card">
					<div class="card-header" id="memberDiv">
						<h2 class="mb-0">
							<button class="btn btn-link btn-block" type="button" data-toggle="collapse" data-target="#memberLogin" aria-expanded="true" aria-controls="memberLogin">
			          			회원 로그인
			        		</button>
		      	  		</h2>
		    		</div>
					<div id="memberLogin" class="collapse" aria-labelledby="memberDiv" data-parent="#loginSpace">
						<div class="card-body">
							<form name="loginForm">
								<div class="form-row">	
									<div class="form-group col-md-5">
										<label for="name"><strong>아이디</strong></label>
										<input type="text" class="form-control-plaintext inline-form" name="loginId" id="loginId" data-toggle="tooltip" data-placement="right" title="회원 아이디 또는 주문번호를 입력하세요." placeholder="아이디/주문번호 입력"/>
									</div>
									<div class="form-group col-md-5">
										 <label for="id"><strong>비밀번호</strong></label>
										 <input type="password" class="form-control-plaintext inline-form" name="loginPwd" id="loginPwd" data-toggle="tooltip" data-placement="right" title="비밀번호를 입력하세요." placeholder="비밀번호 입력"/>
									</div>					  		  	
								</div>	
								
								<div class="form-row">
									<input type="checkbox" name="autologin" id="autologin" value="1">자동 저장합니다.
								</div>	
								
								<div class="form-row">
									<div class="col" align="right">          	
										<button type="reset" id="resetLogin" class="btn btn-outline-secondary">다시 작성</button>	
										<button type="button" id="findBtn" class="btn btn-outline-info">아이디/비밀번호 찾기</button>																	
										<button type="button" id="loginBtn" class="btn btn-outline-success">로그인</button>			
									</div>
								</div>		
								<div id="loginResult"></div>												
							</form>										 
				      	</div>   
				    </div>
		  		</div>
		
		  		<div class="card">
		    		<div class="card-header" id="nonMemberDiv">
		      			<h2 class="mb-0">
					        <button class="btn btn-link btn-block" type="button" data-toggle="collapse" data-target="#nonMemberLogin" aria-expanded="true" aria-controls="nonMemberLogin">
					          	비회원 주문조회
					        </button>
		      			</h2>
		    		</div>
				  	<div id="nonMemberLogin" class="collapse" aria-labelledby="nonMemberDiv" data-parent="#loginSpace">
				      <div class="card-body">
							<div class="form-row">	
								<div class="form-group col-md-5">
									<label for="name"><strong>주문번호</strong></label>
									<input type="text" class="form-control-plaintext inline-form"  id="orderId" data-toggle="tooltip" data-placement="right" title="주문서 아이디를 입력하세요." placeholder="주문아이디 입력"/>
								</div>
								<div class="form-group col-md-5">
									<label for="id"><strong>비밀번호</strong></label>
									<input type="password" class="form-control-plaintext inline-form" id="orderPwd" data-toggle="tooltip" data-placement="right" title="주문서 비밀번호를 입력하세요." placeholder="주문서 비밀번호 입력" >
								</div>					  		  	
							</div> 
										   		
							<div class="form-row">
								<div class="form-group col" align="right">          	
									<button type="reset" id="resetLogin" class="btn btn-outline-secondary">다시 작성</button>
									<button type="button" id="resetPwdBtn" class="btn btn-outline-info">비밀번호 재발급</button>																	
									<button type="button" id="orderCheckBtn" class="btn btn-outline-success">주문조회</button>			
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col" id="orderLoginResult" align="center"></div>
						 	</div>							 
		      			</div>   
					</div>
				</div>  
		 	</div> 
 		</div>
 	</div>
	<div class="form-row">
		<div class="form-group col"  align="center">
				<a class="button btn-outline-info" href="/minishop/member/writeForm.do" style="padding-right: 20px;"><i class="fas fa-user-plus">회원가입</i></a>				     		     
				<a class="button btn-outline-info" href="javascript:history.back()" ><i class="fas fa-undo-alt">돌아가기</i></a>
		</div>				
	</div> 
 					
</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/member.login.js"></script>
	<!--JavaScript Local LINK:END-->