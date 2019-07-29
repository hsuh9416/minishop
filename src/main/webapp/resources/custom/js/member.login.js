/**
 * 로그인용 js 모음
 */
var alertMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
'<strong>로그인 실패!</strong> 아이디 또는 비밀번호가 일치하지 않습니다.<br> 다시 한번 확인 후 시도해주세요.'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>'

var invalidateMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
'<strong>탈퇴한 멤버</strong> 이미 탈퇴한 멤버 정보입니다.<br> 계정 복원 등의 문의 사항은 담당자에 연락주시기 바랍니다.'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>'
/*일반 로그인*/
$('#loginId').focusout(function(){
	 if($('#loginId').val()!=''){ 
		 $('#loginId').tooltip('disable');
	 } 
	 else{
		 $('#loginId').tooltip('enable');
	 }
});
$('#loginPwd').focusout(function(){
	 if($('#loginPwd').val()!=''){ 
		 $('#loginPwd').tooltip('disable');
	 } 
	 else{
		 $('#loginPwd').tooltip('enable');
	 }
});

//일반 로그인시
 $('#loginBtn').click(function(){
	 //alert($('#autologin').is(':checked'));	 
	 $('#loginResult').empty();
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
						window.close();
						window.location='/minishop/member/memberView.do';
					}
					else if(data=='adminLogin'){
						var result = confirm('관리자 로그인을 하시겠습니까?');
						if(result){alert('로그인하셨습니다. 관리자 화면으로 이동합니다.');
							window.location='/minishop/admin/outterMain.do';}
						else {
							window.location='/minishop/admin/adminLogout.do';
						}
					}
					else if(data=='invalidate'){
						$('#loginResult').append(invalidateMsg).alert();
					}
					else if(data=='guestLogin'){
						alert('환영합니다! 주문 내역을 조회합니다.');
						window.location='/minishop/member/memberOrderlist.do';						
					}
					else if(data=='fail'){
						$('#loginResult').append(alertMsg).alert();
					}
			 }//function
		 });//ajax

	 }//else
	 
 });//로그인 버튼

//팝업 로그인시
 $('#mloginBtn').click(function(){
	 //alert($('#autologin').is(':checked'));	 
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
			 }//function
		 });//ajax

	 }//else
	 
 });//팝업 로그인 버튼
 
 //아이디/비밀번호 검색창 이동
 $('#popFindBtn').click(function(){
		window.close();
		window.opener.location.href='/minishop/member/findForm.do';	
	});
 $('#findBtn').click(function(){
		window.location.href='/minishop/member/findForm.do';	
	}); 