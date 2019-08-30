//0. 공통 변수
var alertMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
'<strong>로그인 실패!</strong> 아이디 또는 비밀번호가 일치하지 않습니다.<br> 다시 한번 확인 후 시도해주세요.'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>'

var invalidateMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
'<strong>탈퇴한 멤버</strong> 이미 탈퇴한 멤버 정보입니다.<br> 계정 복원 등의 문의 사항은 담당자에 연락주시기 바랍니다.'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>'

var loginErrorMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
'<strong>유효하지 않은 로그인</strong> 로그인이 유효하지 않습니다.<br> 비회원 주문조회는 비회원 주문조회 로그인단에서 접속하시기 바랍니다.'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>'

//주문번호는 yyyyMMdd-N(N)로 설정해야 함
function checkOrderNumFormat(text){
	var pattern = /^\d{8}-\d{1,2}$/;
	var check = pattern.test(text);
	return check;
}

//1. 미작성 등 경고창 on/off
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

//2. 로그인 이벤트
 $('#loginBtn').click(function(){ 
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
						window.location='/minishop/member/memberView.do';
					}
 					else if(data=='adminLogin'){
 						var result = confirm('관리자 계정으로 접속하셨습니다. 관리자 화면으로 이동 하시겠습니까?');
 							if(result){
 								window.location.href='/minishop/member/adminLogin.do';}
 							else {				
 								window.location.href='/minishop/member/memberView.do';
 							}
 					}
					else if(data=='invalidate'){
						$('#loginResult').append(invalidateMsg).alert();
					}
					else if(data=='fail'){
						$('#loginResult').append(alertMsg).alert();
					}
					else $('#loginResult').append(loginErrorMsg).alert();
			 }
		 });

	 }
	 
 });


//3. 아이디/비밀번호 검색창 이동
 $('#findBtn').click(function(){
		window.location.href='/minishop/member/findForm.do';	
	}); 
 
//4. 비회원 주문번호 로그인
 $('#orderCheckBtn').click(function(){
	  $('#orderLoginResult').empty();
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
	 				 if(data=='memberLogin')  $('#orderLoginResult').append('[주의]회원님께서는 회원 아이디로 조회 부탁드립니다.').css('color','red').css('font-weight','bold').css('font-size','18px');
	 				 else if(data=='fail') $('#orderLoginResult').append('[조회결과없음]해당 아이디로 주문한 내역이 존재하지 않습니다.').css('color','red').css('font-weight','bold').css('font-size','18px');
	 				 else if(data=='pwdMissMatch') $('#orderLoginResult').append('[비밀번호불일치]주문번호에 대한 비밀번호를 재확인해주세요.').css('color','red').css('font-weight','bold').css('font-size','18px');
	 				 else{
	 					 alert('반갑습니다!\n주문조회 화면으로 이동합니다.');
	 					 window.location='/minishop/trading/orderView.do?order_no='+data;
	 				 }
	 			 }
	 	 });
	  }
 });
 
 $('#resetPwdBtn').click(function(){
		var guest_id = prompt("[중요]주문서의 주문아이디를 입력하세요. ex>20190801-02");
		if(guest_id==null || guest_id=='') alert('주문아이디를 입력하셔야 조회가 가능합니다.');
		else if(checkOrderNumFormat(guest_id)==false) alert('유효한 주문아이디 형식이 아닙니다. ex>20190801-02');
		else{
			var realChange = confirm('확인을 누르면 주문시에 입력하신 메일주소로 새 비밀번호가 발급됩니다.\n 발급 이후에는 원래의 비밀번호로 복구되지 않사오니 주의 부탁드립니다.\n 주문서의 분실 등의 사유로 인한 경우에는 고객센터에 직접 문의 바랍니다.');
			if(realChange){
				$.ajax({
					type: 'get',
					url : '/minishop/trading/resetOrderPwd.do',
					data : 'guest_id='+guest_id,
					dataType: 'text',
					success: function(data){
						if(data=='success'){
							alert('변경된 비밀번호에 대한 메일이 주문서상의 메일 주소로 발송되었습니다. \n 메일이 도착하지 않았거나 주문서를 분실하였을 경우에는 고객센터에 연락 바랍니다.');
							window.location.reload();
						}
						else if(data=='no_order_exist') alert('아이디에 해당하는 주문서가 존재하지 않습니다.');
						else alert('오류로 실패하였습니다. 다시 한번 시도해주세요.');
					}
				});
			}		
		}
	});