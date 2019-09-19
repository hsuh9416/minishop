//0. 비밀번호 유효성 설정(대소문자 구분없이 영문자 포함, 특수문자 포함, 8~16자리 사이로 설정)
var reg = /^(?=.*[a-zA-Z])(?=.*[\~\․\!\@\#\$\%\^\&\*\(\)\_\-\+\=\[\]\|\\\;\:\\'\"\<\>\,\.\?\/])(?=.*[0-9]).{8,16}$/;

//1. 미작성 등의 경고창 on/off
$('#pwd').focusout(function(){
	 if($('#pwd').val()!=''){ 
		 $('#pwd').tooltip('disable');
	 } 
	 else{
		 $('#pwd').tooltip('enable');
	 }
});

$('#newPwd').focusout(function(){
	 if($('#newPwd').val()!=''){ 
		 $('#newPwd').tooltip('disable');
	 } 
	 else{
		 $('#newPwd').tooltip('enable');
	 }
});

$('#rePwd').focusout(function(){
	 if($('#rePwd').val()==$('#pwd').val() && $('#rePwd').val()!=''){ 
		 $('#rePwd').tooltip('disable');
	 } 
	 else{
		 $('#rePwd').tooltip('enable');
	 }
});

//2. 비밀번호 변경 이벤트(DB까지 최종 반영)
$('#resetPwdBtn').click(function(){
	$('#pwd').tooltip('disable');
	$('#newPwd').tooltip('disable');
	$('#rePwd').tooltip('disable');
	if($('#pwd').val()==''){
		 $('#pwd').tooltip('enable');
		 $('#pwd').tooltip('show');
		 $('#pwd').focus();
	}else if($('#newPwd').val()==''){
		 $('#newPwd').tooltip('enable');
		 $('#newPwd').tooltip('show');
		 $('#newPwd').focus();	
	}else if($('#rePwd').val()!=$('#newPwd').val()){
		 $('#rePwd').tooltip('enable');
		 $('#rePwd').tooltip('show');
		 $('#rePwd').focus();	
	}else if($('#pwd').val()==$('#newPwd').val()){
		alert('기존과 동일한 비밀번호를 설정이 불가능합니다.');
	}else if(reg.test($('#newPwd').val())==false){
		alert('비밀번호는 영문자,숫자,특수문자을 각각 하나 이상 포함하여 8자리 이상 16자리 이하로 설정하셔야 합니다');
		$('#newPwd').focus();
	}else{
		$.ajax({
			type : 'post',
			url : '/minishop/member/changePwd.do',
			data: $('#changePwdForm').serialize(),
			dataType: 'text',
			success: function(data){
				if(data=='fail'){
					alert('기존에 사용하시던 비밀번호가 일치하지 않습니다. 재작성 바랍니다.');
					window.location.reload();
				}
				else{
					alert('비밀번호가 성공적으로 변경되었습니다.');
					window.close();
					widow.opener.reload();
				}
				
			}
		});
	}
});
