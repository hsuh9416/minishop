<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">    
 <div class="container">       
 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h5>[이메일 재설정]</h5>		
 		</div>
	</div>
	<form id="changeEmailForm">
		<div class="form-row">	
	    <div class="form-group col-3">
	      <input type="text" class="form-control" id="changeEmail1" name="changeEmail1" placeholder="이메일 " data-toggle="tooltip" data-placement="bottom" title="이메일을 입력하세요."/>
	    </div>
		<span>@</span>  
	    <div class="form-group col-3">
	      	<input type="text" class="form-control" name="changeEmail2" id="changeEmail2" data-toggle="tooltip" data-placement="bottom" title="이메일 인증을 하시기 바랍니다.">
	    </div>
	    <div class="form-group col-3">
	      <select id="inputchangeEmail" name="changeEmail2" class="form-control">
			<option value="">직접 입력</option>   
			<option value="gmail.com">gmail.com</option>
			<option value="hanmail.net">hanmail.net</option>
			<option value="naver.com">naver.com</option>
	      </select>
	    </div>
		<div class="form-group col-2">
			<button class="btn btn-outline-info" id="goConfirm" type="button">인증</button>
		</div>		    	
		</div>		
	</form>
		<div id="confirmDiv" class="form-row">
			<div class="form-group col-3"> 
				<input type="text" class="form-control-plaintext" value="인증번호 입력 : "/>
			</div>		
			<div class="form-group col-3"> 
				<input type="text" class="form-control" id="confirmNum" name="confirmNum" placeholder="인증번호"/>
			</div>
			<div class="form-group col-5"> 
				<button class="btn btn-outline-info" id="getConfirm" name="getConfirm" type="button">인증받기</button> 
				<button class="btn btn-outline-success" id="doConfirm" name="doConfirm" type="button">인증확인</button> 				      
			</div>	    
		</div>	
	<div class="row">
		<div class="col" align="right">          	
			<button type="button" id="resetBtn" class="btn btn-outline-secondary">다시 작성</button>	
			<button type="button" id="resetEmailBtn" class="btn btn-outline-info" disabled>이메일 재설정</button>			
		</div>
	</div>			
	
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#confirmDiv').hide();
});
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

$('#inputchangeEmail').change(function() {
	var changeEmail2 = $('#inputchangeEmail option:selected').val();
	$('#changeEmail2').val(changeEmail2);
	if($('#inputchangeEmail option:selected').val()=='') $('#changeEmail2').attr('readonly',false);
	else $('#changeEmail2').attr('readonly',true);
});

//이메일 인증창 열기
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

$('#resetBtn').click(function(){
	window.location.reload();
});

//이메일 인증
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

$('#resetEmailBtn').click(function(){
	$('#email1',opener.document).val($('#changeEmail1').val());
	$('#email2',opener.document).val($('#changeEmail2').val());
	window.close();

});

</script>