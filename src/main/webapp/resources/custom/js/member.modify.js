/**
 * 회원 수정 관련 메소드
 */
$(document).ready(function(){
	$('#email2').val('${memberDTO.email2}');
	$('#tel1').val('${memberDTO.tel1}');
	$('#wrap').hide();
});//ready


/*메일에서 셀렉트 박스 선택지 변경시*/
$('#emailInput').change(function() {
	var email2 = $('#emailInput option:selected').val();
	$('#email2').val(email2);
	if($('#emailInput option:selected').val()=='') $('#email2').attr('readonly',false);
	else $('#email2').attr('readonly',true);
});

//이름과 비밀번호의 조건을 충족시키면 툴팁이 더이상 발생하지 않는다.
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
			 data: 'pwd='+$('#rePwd').val(),
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

//비밀번호 변경창 popup
$('#pwdChangeBtn').click(function(){
	 var pwdPop = window.open('/minishop/member/changePwdForm.do','비밀번호 변경','width=400,height=400,resizable=no');		
});


$('#modifyBtn').click(function(){
	//alert('수정하기!');
	 $('#name').tooltip('disable');
	 $('#pwd').tooltip('disable');
	 $('#repwd').tooltip('disable');	 
	if($('#name').val()==''){
		$('#name').tooltip('enable');
		$('#name').tooltip('show');
		$('#name').focus();	
	}else if($('#repwd').val()==''|| $('#checkPwd').val()!=$('#repwd').val()){
		$('#repwd').tooltip('enable');
		$('#repwd').tooltip('show');	
		$('#repwd').focus();
	}else{
		$.ajax({
			type: 'post',
			url : '/minishop/member/memberModify.do',
			data : $('#modifyForm').serialize(),
			dataType : 'text',
			success : function(data){
				if(data=='success') {alert('성공적으로 수정되었습니다.');
					window.location='/minishop/member/memberView.do';}
				else if(data=='fail') {
					alert('오류가 발생하여 수정에 실패하였습니다. 다시 한번 시도해주세요.');
					window.location.reload();
				}//else if
			}//success			
		});//ajax
	}//else
});//수정버튼	

$('#resetBtn').click(function(){
	window.location.reload();
});//리셋

//이메일 재설정
$('#changeEmailBtn').click(function(){
	 var pwdPop = window.open('/minishop/member/changeEmailForm.do','이메일 변경','width=800,height=200,resizable=no');	
});