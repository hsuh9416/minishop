<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

   
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap4/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/custom/css/member.css">


<!-- Modal 출연 구역 -->
  <div class="modal fade" id="loginModal" role="dialog">
    <div class="modal-dialog">
 <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <div class="title_logo">
			<a><span>Sign In</span></a>
		  </div>
        </div>
        <div class="modal-body">
				<form name="loginForm">
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fas fa-user"></i></span>
							</div>
							<input type="text" class="form-control" id="loginId" data-toggle="tooltip" data-placement="bottom" title="회원 아이디 또는 주문번호를 입력하세요." placeholder="아이디/주문번호 입력"/>
						</div>
						
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fas fa-key"></i></span>
							</div>
							<input type="password" class="form-control" id="loginPwd" data-toggle="tooltip" data-placement="bottom" title="비밀번호를 입력하세요." placeholder="비밀번호 입력"/>
						</div>
						
						<div class="row align-items-center remember">
							<input type="checkbox" name="autologin" id="autologin" value="1">자동 저장합니다.
						</div>

						<div class="form-group">
				        	<div class="row">
								<div class="col" align="right">          	
									<button type="reset" id="resetLogin" class="btn btn-outline-secondary">다시 작성</button>								
									<button type="button" id="loginBtn" class="btn btn-outline-info">로그인</button>			
								</div>
							</div>	
						</div>	
						
						<div id="loginResult"></div>								
				</form>
        </div>
        <div class="modal-footer">
        	<div class="row">
				<div class="col" align="right">          				
					<button type="button" id="joinBtn" class="btn btn-outline-success">회원 가입</button>
					<button type="button" id="findBtn"  class="btn btn-outline-primary">ID/PWD 찾기</button>						
         			<button type="button" class="btn btn-outline-warning" data-dismiss="modal">닫기</button>
				</div>
         	</div>	
        </div>
       </div>      
    </div>
  </div>
	<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="/mallproject/resources/custom/js/member.login.js"></script>











