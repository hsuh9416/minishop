//0. 공통 변수
var notexistMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'
+'<strong>[조회결과 없음]</strong>해당하는 정보가 존재하지 않습니다.'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

//1. 경고창 on/off
$('#findName').focusout(function(){
	 if($('#findName').val()!=''){ 
		 $('#findName').tooltip('disable');
	 } 
	 else{
		 $('#findName').tooltip('enable');
	 }
});

$('#findIdemail1').focusout(function(){
	 if($('#findIdemail1').val()!=''){ 
		 $('#findIdemail1').tooltip('disable');
	 } 
	 else{
		 $('#findIdemail1').tooltip('enable');
	 }
});
$('#findIdemail2').focusout(function(){
	 if($('#findIdemail2').val()!=''){ 
		 $('#findIdemail2').tooltip('disable');
	 } 
	 else{
		 $('#findIdemail2').tooltip('enable');
	 }
});

$('#findPwdemail1').focusout(function(){
	 if($('#findPwdemail1').val()!=''){ 
		 $('#findPwdemail1').tooltip('disable');
	 } 
	 else{
		 $('#findPwdemail1').tooltip('enable');
	 }
});

$('#findPwdemail2').focusout(function(){
	 if($('#findPwdemail2').val()!=''){ 
		 $('#findPwdemail2').tooltip('disable');
	 } 
	 else{
		 $('#findPwdemail2').tooltip('enable');
	 }
});

$('#findID').focusout(function(){
	 if($('#findID').val()!=''){ 
		 $('#findID').tooltip('disable');
	 } 
	 else{
		 $('#findID').tooltip('enable');
	 }
});

//2. 직접 입력 외 readonly 처리 
$('#findIdInputEmail').change(function() {
	var email2 = $('#findIdInputEmail option:selected').val();
	$('#findIdemail2').val(email2);
	if($('#findIdInputEmail option:selected').val()=='') $('#findIdemail2').attr('readonly',false);
	else $('#findIdemail2').attr('readonly',true);
});

$('#findPwdInputEmail').change(function() {
	var email2 = $('#findPwdInputEmail option:selected').val();
	$('#findPwdemail2').val(email2);
	if($('#findPwdInputEmail option:selected').val()=='') $('#findPwdemail2').attr('readonly',false);
	else $('#findPwdemail2').attr('readonly',true);
});

//3. 아이디 검색 이벤트 수행
$('#findIdBtn').click(function(){
	$('#findIdResult').empty();
	$('#findName').tooltip('disable');
	$('#findIdemail1').tooltip('disable');
	$('#findIdemail2').tooltip('disable');
	if($('#findName').val()==''){
		 $('#findName').tooltip('enable');
		 $('#findName').tooltip('show');
		 $('#findName').focus();
	}else if($('#findIdemail1').val()==''){
		 $('#findIdemail1').tooltip('enable');
		 $('#findIdemail1').tooltip('show');
		 $('#findIdemail1').focus();	
	}else if($('#findIdemail2').val()==''){
		 $('#findIdemail2').tooltip('enable');
		 $('#findIdemail2').tooltip('show');
		 $('#findIdemail2').focus();	
	}else{
	$.ajax({
		type: 'post',
		url : '/minishop/member/findLostId.do',
		data : $('#findIdForm').serialize(),
		dataType : 'text',	
		success : function(data){
			if(data=='not_exist')
				$('#findIdResult').append(notexistMsg).alert();
			else{
				alert('[조회결과]'+$('#findName').val()+'님의 아이디는 '+data+'입니다.');
			}
		}
	});
	}
});

//4. 비밀번호 재설정 이벤트 수행(비밀번호 재설정은 다시 로그인해서 회원 정보로 수정하도록 유도함)
$('#findPwdBtn').click(function(){
	$('#findPwdResult').empty();
	$('#findID').tooltip('disable');
	$('#findPwdemail1').tooltip('disable');
	$('#findPwdemail2').tooltip('disable');
	if($('#findID').val()==''){
		 $('#findID').tooltip('enable');
		 $('#findID').tooltip('show');
		 $('#findID').focus();
	}else if($('#findPwdemail1').val()==''){
		 $('#findPwdemail1').tooltip('enable');
		 $('#findPwdemail1').tooltip('show');
		 $('#findPwdemail1').focus();	
	}else if($('#findPwdemail2').val()==''){
		 $('#findPwdemail2').tooltip('enable');
		 $('#findPwdemail2').tooltip('show');
		 $('#findPwdemail2').focus();	
	}else{
	$.ajax({
		type: 'post',
		url : '/minishop/member/findLostPwd.do',
		data : $('#findPwdForm').serialize(),
		dataType : 'text',	
		success : function(data){
			if(data=='not_exist')
				$('#findPwdResult').append(notexistMsg).alert();
			else{
				alert('가입시에 인증하시 메일 주소로 임시번호가 발급되었습니다. 임시번호로 로그인하신 후 회원정보수정란에서 비밀번호를 변경해주세요.');
				window.location='/minishop/member/loginForm.do';
			}
		}
	});
	}
});