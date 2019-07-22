/**
 *  회원 가입 관련 자바스크립트
 */

var checkNum = '';

var noIdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">아이디를 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var noCheckMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">아이디 중복체크를 해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var noConfirmMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">이메일 인증을 해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var failMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
+'<strong>가입 실패</strong>다시 한번 시도해주세요!'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var existMsg = '<div class="alert alert-danger alert-dismissible fade show" role="alert">'
+'<strong>[사용불가]</strong>이미 존재하는 아이디입니다'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';
	
var notexistMsg = '<div class="alert alert-success alert-dismissible fade show" role="alert">'+
'<strong>[사용가능]</strong>사용가능한 아이디입니다'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

$(document).ready(function(){
	$('#confirmDiv').hide();
});

/*메일에서 셀렉트 박스 선택지 변경시*/
$('#inputEmail').change(function() {
	var email2 = $('#inputEmail option:selected').val();
	$('#email2').val(email2);
	if($('#inputEmail option:selected').val()=='') $('#email2').attr('readonly',false);
	else $('#email2').attr('readonly',true);
});


/*각 항목의 조건을 충족시키면 툴팁이 더이상 발생하지 않는다.*/
$('#name').focusout(function(){
	 if($('#name').val()!=''){ 
		 $('#name').tooltip('disable');
	 } 
	 else{
		 $('#name').tooltip('enable');
	 }
});

$('#id').focusout(function(){
	 if($('#id').val()!=''){ 
		 $('#id').tooltip('disable');
	 } 
	 else{
		 $('#id').tooltip('enable');
	 }
});

$('#pwd').focusout(function(){
	 if($('#pwd').val()!=''){ 
		 $('#pwd').tooltip('disable');
	 } 
	 else{
		 $('#pwd').tooltip('enable');
	 }
});
$('#repwd').focusout(function(){
	 if($('#pwd').val()!=$('#repwd').val()){ 
		 $('#pwd').tooltip('disable');
	 } 
	 else{
		 $('#pwd').tooltip('enable');
	 }
});
$('#email1').focusout(function(){
	 if($('#email1').val()!=''){ 
		 $('#email1').tooltip('disable');
	 } 
	 else{
		 $('#email1').tooltip('enable');
	 }
});

$('#email2').change(function() {
	 if($('#email2').val()!=''){ 
		 $('#email2').tooltip('hide');	
		 $('#email2').tooltip('disable');
	 } 
	 else if($('#checkEmail').val()==$('#email2').val()){
		 $('#email2').tooltip('hide');			 
		 $('#email2').tooltip('disable');		 
	 }
	 else{
		 $('#email2').tooltip('enable');
		 $('#email2').tooltip('show');		 
	 }
});

$('#writeBtn').click(function(){
	$('#confirmDiv').hide();
	$('#writeResult').empty();
	$('#name').tooltip('disable');
	$('#id').tooltip('disable');
	$('#pwd').tooltip('disable');
	$('#repwd').tooltip('disable');
	$('#email1').tooltip('disable');	
	$('#email2').tooltip('disable');
		if($('#name').val()==''){
			 $('#name').tooltip('enable');
			 $('#name').tooltip('show');
			 $('#name').focus();
		}else if($('#id').val()==''){
			 $('#id').tooltip('enable');
			 $('#id').tooltip('show');
			 $('#id').focus();	
		}else if($('#pwd').val()==''){
			 $('#pwd').tooltip('enable');
			 $('#pwd').tooltip('show');
			 $('#pwd').focus();	
		}else if($('#pwd').val()!=$('#repwd').val()){
			 $('#repwd').tooltip('enable');
			 $('#repwd').tooltip('show');
			 $('#repwd').focus();	
		}else if($('#id').val()!=$('#checkId').val()){
			 $('#writeResult').append(noCheckMsg).alert();	
			 $('#id').focus();	
		}else if($('#email1').val()==''|| $('#email2').val()==''){
			 $('#email1').tooltip('enable');
			 $('#email1').tooltip('show');
			 $('#email1').focus();	
		}else if($('#checkEmail').val()!= $('#email1').val()+'@'+$('#email2').val()){
			 $('#writeResult').append(noConfirmMsg).alert();	
			 $('#confirmDiv').show();	
		}else{
			$.ajax({
				type: 'post',
				url : '/mallproject/member/write.do',
				data : $('#writeForm').serialize(),
				dataType : 'text',
				success : function(data){
					$('#writeResult').empty();
					if(data=='success') {
					 alert('축하합니다. 회원가입되셨습니다!!(메인 화면으로 이동합니다.)');	
					 $(this).modal('dispose');
					 window.location='/mallproject/main/innerMain.do';}
					else if(data=='fail') {
						$('#writeForm').empty();
						$('#writeResult').append(failMsg).alert();				
					}
				}				
			});//에이작스
		} 
	});//제출
	
	$('#id').focusout(function(){
		$('#writeResult').empty();
		if($('#id').val()=='') $('#writeResult').append(noIdMsg).alert();
		else
			$.ajax({
				type : 'post',
				url : '/mallproject/member/checkId.do',
				data : 'id='+$('#id').val(),
				dataType : 'text',
				success : function(data){
					if(data=='exist')
						$('#writeResult').append(existMsg).alert();
					else if(data=='not_exist'){
						$('#writeResult').append(notexistMsg).alert();
						$('#checkId').val($('#id').val());
					}
				}
			});//에이작스
});//아이디 중복체크
	
	//이메일 인증창 열기
$('#goConfirm').click(function(){
	$('#confirmDiv').hide();
	if($('#email1').val()==''|| $('#email2').val()=='') {
		alert('인증하실 이메일이 입력되지 않았습니다.');
	}
	else{
		$('#confirmDiv').show();
	}
});	

$('#getConfirm').click(function(){		
		var email = $('#email1').val()+'@'+$('#email2').val();

		$.ajax({
			type : 'post',
			url : '/mallproject/member/checkEmail.do',
			data : 'email='+email,
			dataType : 'text',
			success : function(data){
				checkNum = data;
			}
		});
		
		alert('이메일을 통해 확인하신 인증번호를 입력해주세요.');
});

$('#doConfirm').click(function(){
		$('#checkEmail').val();
		$('#email1').attr('readonly',false);
		$('#email2').attr('readonly',false);
	if(checkNum == $('#confirmNum').val()){
		alert('인증 성공하셨습니다. 다른 메일을 사용하시려면 다시 작성 버튼을 클릭하여 초기화 바랍니다.');
		$('#checkEmail').val($('#email1').val()+'@'+$('#email2').val());	
		$('#email1').attr('readonly',true);
		$('#email2').attr('readonly',true);			
	}
	else {alert('인증 실패하였습니다. 새로운 인증 번호를 받으시려면 아이콘을 재클릭 부탁드립니다.');
	}
		$('#confirmDiv').hide();	
});

