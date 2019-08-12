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
 						alert('환영합니다!');	
 						window.opener.location.href='/minishop/member/memberView.do';						
 						window.close();

 					}
 					else if(data=='adminLogin'){
 						var result = confirm('관리자 로그인을 하시겠습니까?');
 						if(result){alert('로그인하셨습니다. 관리자 화면으로 이동합니다.');
 							window.close();
 							window.opener.location.href='/minishop/admin/adminHome.do';}
 						else {
 							window.opener.location.href='/minishop/admin/adminLogout.do';
 						}
 					}
 					else if(data=='invalidate'){
 						$('#loginP').append('<strong>탈퇴한 멤버</strong> 이미 탈퇴한 멤버 정보입니다. 계정 복원 등의 문의 사항은 담당자에 연락주시기 바랍니다.');
 					}
 					else if(data=='fail'){
 						$('#loginP').append('<strong>로그인 실패!</strong> 아이디 또는 비밀번호가 일치하지 않습니다. 다시 한번 확인 후 시도해주세요.');
 					}
 			 }
 		 });
 	 }
 	 
});

//3. 아이디/비밀번호 검색창 이동
$('#popFindBtn').click(function(){
		window.close();
		window.opener.location.href='/minishop/member/findForm.do';	
});