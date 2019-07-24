<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

   
 <link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
<style>
body{
 background-color: #1e1e27;
}
h2.first{
 color:white;
}
h2.second{
 color:red;
}

a{
font-size: 18pt;
}
a:link{color:#B5AEC4;}
a:hover{color:white;}
a:visited{color:#B5AEC4;}
a:active {color:white;}
p{color:red;}
</style> 
 	<div class="container">
 	  <div id="title" class="row justify-content-center">
 	  	<h2 class="first">Kissin'</h2><h2 class="second">Bugs</h2>
 	  </div>
 	  <div id="button" class="row justify-content-center">
 	  	<div class="col-6" align="center">
			<a id="member" data-toggle="collapse" href="#memberForm" aria-expanded="false" aria-controls="memberForm"><i class="fas fa-id-card">회원로그인</i></a>
 	  	</div>
 	  	<div class="col-6" align="center">
			<a id="nonmember" href="#" data-toggle="collapse" data-target="#nonMemberForm" aria-expanded="false" aria-controls="nonMemberForm"><i class="fas fa-clipboard-list">주문조회</i></a>
 	  	</div>
 	  </div>
  	  <div class="row justify-content-center">
	    <div class="collapse multi-collapse" id="memberForm">
	      <div class="card card-body">
	        <form name="loginForm">
				<div class="form-row">	
					<div class="form-group col-md-5">
						<label for="name"><strong>아이디</strong></label>
						<input type="text" class="form-control" name="loginId" id="loginId" data-toggle="tooltip" data-placement="top" title="아이디를 입력하세요." placeholder="아이디 입력"/>
					</div>
					<div class="form-group col-md-5">
						 <label for="id"><strong>비밀번호</strong></label>
						 <input type="password" class="form-control" name="loginPwd" id="loginPwd" data-toggle="tooltip" data-placement="top" title="비밀번호를 입력하세요." placeholder="비밀번호 입력"/>
					</div>					  		  	
				</div>	
				<div class="form-row">
					<input type="checkbox" name="autologin" id="autologin" value="1">자동 저장합니다.
					<p id="loginP"></p>
				</div>	
				<div class="row">
					<div class="col" align="right">          	
						<button type="reset" class="btn btn-outline-secondary">다시 작성</button>	
						<button type="button" id="findBtn" class="btn btn-outline-info">아이디/비밀번호 찾기</button>																	
						<button type="button" id="mloginBtn" class="btn btn-outline-success">로그인</button>			
					</div>
				</div>													
			</form>	
	      </div>
	    </div> 	  	
	    <div class="collapse multi-collapse" id="nonMemberForm">
	      <div class="card card-body">
	       <form name="orderCheckForm">
			<div class="form-row">	
				<div class="form-group col-md-5">
					<label for="name"><strong>주문번호</strong></label>
					<input type="text" class="form-control" name="orderId" id="orderId" data-toggle="tooltip" data-placement="top" title="주문번호를 입력해주세요" placeholder="주문번호 입력"/>
				</div>
				<div class="form-group col-md-5">
					<label for="id"><strong>비밀번호</strong></label>
					<input type="password" class="form-control" name="orderPwd" id="orderPwd" data-toggle="tooltip" data-placement="top" title="비밀번호를 입력하세요" placeholder="비밀번호 입력" >
				</div>					  		  	
			</div> 		
				<div class="form-row">
					<p id="checkP">[주의]회원님께서는 회원 아이디로 조회 부탁드립니다</p>
				</div>								   
				<div class="form-group">
				   <div class="row">
					 <div class="col" align="right">          	
						<button type="reset" class="btn btn-outline-secondary">다시 작성</button>																	
						<button type="button" id="orderCheckBtn" class="btn btn-outline-success">주문조회</button>			
					</div>
				 </div>					 
				</div>
			</form>			
	      </div>
	    </div>
  	  </div>	  

 	</div>
  <script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
  <script type="text/javascript" src="/minishop/resources/custom/js/member.login.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	  //최초 시작에 멤버를 열어 둔다
	  $('#memberForm').collapse('show');
  });
  $('#member').click(function(){
	  $('#nonMemberForm').hide();
	  $('#memberForm').show();  
  });  
  $('#nonmember').click(function(){
	  $('#memberForm').hide();
	  $('#nonMemberForm').show();	  
  });
  
  </script>











