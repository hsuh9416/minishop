//1. 최초 시작시 인증란 숨기기
$(document).ready(function(){
	$('#confirmDiv').hide();
});

//2.미작성 등 경고 tooltip on/off
$('#changeEmail1').focusout(function(){
	 if($('#changeEmail1').val()!=''){ 
		 $('#changeEmail1').tooltip('disable');
	 } 
	 else{
		 $('#changeEmail1').tooltip('enable');
	 }
});

$('#changeEmail2').change(function() {
	 if($('#changeEmail2').val()!=''){ 
		 $('#changeEmail2').tooltip('hide');	
		 $('#changeEmail2').tooltip('disable');
	 } 
	 else if($('#checkEmail').val()==$('#changeEmail2').val()){
		 $('#changeEmail2').tooltip('hide');			 
		 $('#changeEmail2').tooltip('disable');		 
	 }
	 else{
		 $('#changeEmail2').tooltip('enable');
		 $('#changeEmail2').tooltip('show');		 
	 }
});

//3. 직접 작성하는 경우가 아니면 readonly 설정
$('#inputchangeEmail').change(function() {
	var changeEmail2 = $('#inputchangeEmail option:selected').val();
	$('#changeEmail2').val(changeEmail2);
	if($('#inputchangeEmail option:selected').val()=='') $('#changeEmail2').attr('readonly',false);
	else $('#changeEmail2').attr('readonly',true);
});

//4. 이메일 인증창 열기
$('#goConfirm').click(function(){
	$('#confirmDiv').hide();
	if($('#changeEmail1').val()==''|| $('#changeEmail2').val()=='') {
		alert('인증하실 이메일이 입력되지 않았습니다.');
	}
	else{
		$('#confirmDiv').show();
		$('#resetEmailBtn').attr('disabled',true);
	}
});	

//5. 재작성
$('#resetBtn').click(function(){
	window.location.reload();
});

//6. 이메일 인증 이벤트
$('#getConfirm').click(function(){		
		var email = $('#changeEmail1').val()+'@'+$('#changeEmail2').val();

		$.ajax({
			type : 'post',
			url : '/minishop/member/checkEmail.do',
			data : 'email='+email,
			dataType : 'text',
			success : function(data){
				checkNum = data;
			}
		});
		
		alert('이메일을 통해 확인하신 인증번호를 입력해주세요.');
});

//7. 인증 후 이벤트(인증 완료 후에 창닫기)
$('#doConfirm').click(function(){
	$('#checkEmail').val();
	$('#changeEmail1').attr('readonly',false);
	$('#changeEmail2').attr('readonly',false);
	if(checkNum == $('#confirmNum').val()){
		alert('인증 성공하셨습니다. 다른 메일을 사용하시려면 다시 작성 버튼을 클릭하여 초기화 바랍니다.');
		$('#checkEmail').val($('#changeEmail1').val()+'@'+$('#changeEmail2').val());	
		$('#changeEmail1').attr('readonly',true);
		$('#changeEmail2').attr('readonly',true);	
		$('#resetEmailBtn').attr('disabled',false);
	}
	
	else {alert('인증 실패하였습니다. 새로운 인증 번호를 받으시려면 아이콘을 재클릭 부탁드립니다.');
	}
	
	$('#confirmDiv').hide();	
});

//8. 최종 승인 후 opener에 반영시키고 닫기(DB에 반영되지 않고 수정 확정시 반영됨)
$('#resetEmailBtn').click(function(){
	$('input[name=email1]',opener.document).val($('#changeEmail1').val());
	$('input[name=email2]',opener.document).val($('#changeEmail2').val());
	window.close();
});