//1.시작시 셋팅
$(document).ready(function(){
	$('input[name=email1]').val($('#email1').val());	
	$('input[name=email2]').val($('#email2').val());
	$('input[name=tel1]').val($('#tel1').val());
	$('#wrap').hide();
});

//2. 이메일 창 이벤트
$('#emailInput').change(function() {
	var email2 = $('#emailInput option:selected').val();
	$('#email2').val(email2);
	if($('#emailInput option:selected').val()=='') $('#email2').attr('readonly',false);
	else $('#email2').attr('readonly',true);
});

//3. 미작성 등 경고창 on/off
$('#name').focusout(function(){

	 if($('#name').val()!=''){ 
		 $('#name').tooltip('disable');
	 } 
	 else{
		 $('#name').tooltip('enable');
	 }
});

$('#repwd').focusout(function(){
	$('#pwdDiv').empty();
	 if($('#repwd').val()!=''){ 
		 $('#repwd').tooltip('disable');
		 $.ajax({
			 type : 'post',
			 url : '/minishop/member/checkPwd.do',
			 data: 'pwd='+$('#repwd').val(),
			 dataType: 'text',
			 success: function(data){
				 if(data=='success'){
					 $('#checkPwd').val($('#repwd').val());
				 }
				 else if(data=='fail'){
					$('#pwdDiv').text('비밀번호가 일치하지 않습니다!').attr('color','red'); 
				 }
			 }
		 });
	 } 
	 else{
		 $('#repwd').tooltip('enable');
	 }
});

//4. 비밀번호 변경창 popup
$('#pwdChangeBtn').click(function(){
	 var pwdPop = window.open('/minishop/member/changePwdForm.do','비밀번호 변경','width=400,height=400,resizable=no');		
});


//5. 수정 반영 이벤트
$('#modifyBtn').click(function(){
	 $('#name').tooltip('disable');
	 $('#pwd').tooltip('disable');
	 $('#repwd').tooltip('disable');	 
	if($('#name').val()==''){
		$('#name').tooltip('enable');
		$('#name').tooltip('show');
		$('#name').focus();	
	}else{
		$.ajax({
			type: 'post',
			url : '/minishop/member/memberModify.do',
			data : $('#modifyForm').serialize(),
			dataType : 'text',
			success : function(data){
				if(data=='success') {alert('성공적으로 수정되었습니다.');
					window.location='/minishop/member/memberView.do';}
				else if(data=='unMatchedPwd') alert('비밀번호가 일치하지 않습니다. 다시 한번 시도해주세요');
				else if(data=='fail') {
					alert('오류가 발생하여 수정에 실패하였습니다. 다시 한번 시도해주세요.');
					window.location.reload();
				}
			}		
		});
	}
});

//6.리셋
$('#resetBtn').click(function(){
	window.location.reload();
});

//7.이메일 재설정창 열기
$('#changeEmailBtn').click(function(){
	 var pwdPop = window.open('/minishop/member/changeEmailForm.do','이메일 변경','width=780,height=200,resizable=no');	
});

//8.돌아가기 버튼
$('#returnBtn').click(function(){
	window.history.back();
});