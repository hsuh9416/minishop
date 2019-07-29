<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">    
 <div class="container">       
 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h5>[비밀번호 재설정]</h5>		
 		</div>
	</div>
	<form id="changePwdForm">
		<div class="form-row justify-content-center">	
			<div class="form-group col-7">
				<label for="pwd"><strong>기존 비밀번호</strong></label>
				<input type="password" class="form-control" name="pwd" id="pwd" data-toggle="tooltip" data-placement="right" title="기존 비밀번호를 입력하세요." placeholder="기존 비밀번호"/>
			</div>
		</div>
		<div class="form-row justify-content-center">	
			<div class="form-group col-7">
				<label for="newPwd"><strong>새 비밀번호</strong></label>
				<input type="password" class="form-control" name="newPwd" id="newPwd" data-toggle="tooltip" data-placement="right" title="새 비밀번호를 입력하세요." placeholder="새 비밀번호"/>
			</div>
		</div>
		<div class="form-row justify-content-center">	
			<div class="form-group col-7">
				<label for="rePwd"><strong>비밀번호 재입력</strong></label>
				<input type="password" class="form-control" name="rePwd" id="rePwd" data-toggle="tooltip" data-placement="right" title="비밀번호가 일치하지 않습니다." placeholder="비밀번호 재확인"/>
			</div>
		</div>						
	</form>
		<div class="row">
			<div class="col" align="right">          	
				<button type="reset" class="btn btn-outline-secondary">다시 작성</button>	
				<button type="button" id="resetPwdBtn" class="btn btn-outline-info">비밀번호 재설정</button>			
			</div>
		</div>			
	
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
var reg = /^(?=.*[a-zA-Z])(?=.*[\~\․\!\@\#\$\%\^\&\*\(\)\_\-\+\=\[\]\|\\\;\:\\'\"\<\>\,\.\?\/])(?=.*[0-9]).{8,16}$/;
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

//비밀번호 변경
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
					widow.openter.reload();
				}
				
			}//success
		});
	}
});

</script>