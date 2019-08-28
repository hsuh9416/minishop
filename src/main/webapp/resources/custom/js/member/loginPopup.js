//1. 최초 시작시(Member Login 창 셋팅)
$(document).ready(function(){
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
  
//2. 팝업 로그인 이벤트
$('#mloginBtn').click(function(){
 	 $('#loginP').empty();
 	 $('#loginId').tooltip('disable');
 	 $('#loginPwd').tooltip('disable');
 	 if($('#loginId').val()==''){ 
 		 $('#loginId').tooltip('enable');
 		 $('#loginId').tooltip('show');
 		 $('#loginId').focus();
 	 }
 	 else if($('#loginPwd').val()=='') {
 		 $('#loginPwd').tooltip('enable');
 		 $('#loginPwd').tooltip('show');
 		 $('#loginPwd').focus();
 	 }
 	 else {
 		 if($('#autologin').is(':checked')) var autoLogin = '1';
 		 else var autoLogin ='0';
 		 $.ajax({
 			 type : 'POST',
 			 url : '/minishop/member/login.do',
 			 data : {'id': $('#loginId').val(),'pwd' : $('#loginPwd').val(), 'autoLogin' : autoLogin},
 			 dataType: 'text',
 			 success : function(data){
 					if(data=='userLogin') {
 						alert('반갑습니다, 회원님! 회원정보로 이동합니다');	
 						window.opener.location.href='/minishop/member/memberView.do';						
 						window.close();

 					}
 					else if(data=='adminLogin'){
 						var result = confirm('관리자 계정으로 접속하셨습니다. 관리자 화면으로 이동 하시겠습니까?');
 							if(result){
 	 							window.close();
 								window.opener.location.href='/minishop/member/adminLogin.do';}
 							else {				
 								window.close();
 								window.opener.location.href='/minishop/member/memberView.do';
 							}
 					}
 					else if(data=='invalidate'){
 						$('#loginP').append('<strong>탈퇴한 멤버</strong> 이미 탈퇴한 멤버 정보입니다. 계정 복원 등의 문의 사항은 담당자에 연락주시기 바랍니다.').css('color','red').css('font-weight','bold').css('font-size','18px');
 					}
 					else if(data=='fail'){
 						$('#loginP').append('<strong>로그인 실패!</strong> 아이디 또는 비밀번호가 일치하지 않습니다. 다시 한번 확인 후 시도해주세요.').css('color','red').css('font-weight','bold').css('font-size','18px');
 					}
 					else $('#loginP').append('유효하지 않은 접속입니다. 주문번호 조회의 경우 비회원 주문조회란에서 접속하시기 바랍니다.').css('color','red').css('font-weight','bold').css('font-size','18px');
 			 }
 		 });
 	 }
 	 
});

//3. 아이디/비밀번호 검색창 이동
$('#popFindBtn').click(function(){
		window.close();
		window.opener.location.href='/minishop/member/findForm.do';	
});

//4. 비회원 주문번호 로그인
$('#orderCheckBtn').click(function(){
	 $('#checkP').empty();
	 $('#orderId').tooltip('disable');
	 $('#orderPwd').tooltip('disable');
	 if($('#orderId').val()==''){ 
		 $('#orderId').tooltip('enable');
		 $('#orderId').tooltip('show');
		 $('#orderId').focus();
	 }
	 else if($('#orderPwd').val()=='') {
		 $('#orderPwd').tooltip('enable');
		 $('#orderPwd').tooltip('show');
		 $('#orderPwd').focus();
	 }
	 else{
		 $.ajax({
				 type : 'POST',
				 url : '/minishop/member/orderCheck.do',
				 data : {'id': $('#orderId').val(),'pwd' : $('#orderPwd').val()},
				 dataType: 'text',
				 success : function(data){
					 if(data=='memberLogin')  $('#checkP').append('[주의]회원님께서는 회원 아이디로 조회 부탁드립니다.').css('color','red').css('font-weight','bold').css('font-size','18px');
					 else if(data=='fail') $('#checkP').append('[조회결과없음]해당 아이디로 주문한 내역이 존재하지 않습니다.').css('color','red').css('font-weight','bold').css('font-size','18px');
					 else if(data=='pwdMissMatch') $('#checkP').append('[비밀번호불일치]주문번호에 대한 비밀번호를 재확인해주세요.').css('color','red').css('font-weight','bold').css('font-size','18px');
					 else{
						 alert('반갑습니다!\n주문조회 화면으로 이동합니다.');
	 					 window.opener.location.href='/minishop/trading/orderView.do?order_no='+data;					
	 					 window.close();
					 }
				 }
		 });
	 }
});