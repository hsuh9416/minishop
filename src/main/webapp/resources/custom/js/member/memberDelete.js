//1. 미작성 등 경고창 on/off
$('#dreturnAddr').focusout(function(){
	 if($('#dreturnAddr').val()!=''){ 
		$('#dreturnAddr').tooltip('disable');
	 	} 
	else{
		$('#dreturnAddr').tooltip('enable');
		 }
});

//2. 탈퇴 요청 이벤트 수행
$('#deleteBtn').click(function(){
	$('#dreturnAddr').tooltip('disable');
	if($('#dreturnAddr').val()==''){ 
		$('#dreturnAddr').tooltip('enable');
		$('#dreturnAddr').tooltip('show');
		$('#dreturnAddr').focus();}
	else{
	$.ajax({
		type : 'post',
		url : '/minishop/member/delete.do',
		data: {
				'email':$('#dreturnAddr').val(),
				'dReason':$('#dReason :selected').val(),
				'etcDetail':$('#etcDetail').val(),
				'deletePwd':$('#deletePwd').val()
				},
		dataType: 'text',
		success : function(data){	
				if(data=='pwdCheckFailed'){
					alert('입력하신 비밀번호가 올바르지 않습니다. 다시 한번 입력바랍니다.');
				}
				else if(data=='submitSuccess'){
					alert('요청이 정상적으로 접수되었습니다. 자세한 사항은 메일을 통해 확인 바랍니다.');
					window.close();
					window.opener.location.href='/minishop/member/logout.do';	
				}
				else{
					alert('일시적인 오류로 요청이 실패하였습니다. 다시 한번 시도해주시기 바랍니다.');
					window.close();					
				}
			}
		});	
	}
});