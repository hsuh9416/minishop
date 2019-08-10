//1. 최소 실행시 비밀번호 공간 숨기기
$(document).ready(function(){
	$('#pwdConfirm').hide();
});

//2. 수정버튼 클릭시 비밀번호 입력란을 보여주는 이벤트
$('#reviewModifyFormBtn').click(function(){
	$('#alertText').val('해당글의 비밀번호를 입력하세요').css('color','black').css('font-size','15px');	
	$('#pwdConfirm').show();
	$('#purpose').val('modify');

});

//3. 삭제버튼 클릭시 비밀번호 입력란을 보여주는 이벤트
$('#reviewDeleteBtn').click(function(){
	$('#alertText').val('해당글의 비밀번호를 입력하세요').css('color','black').css('font-size','15px');	
	$('#pwdConfirm').show();
	$('#purpose').val('delete');
});

//4. 호출된 비밀번호 입력의 유효성을 검사하고 실행 여부를 판단하는 이벤트
$('#checkReviewPwd').click(function(){
	$('#alertText').val('');
	if($('#rePwd').val()==''){
		$('#alertText').val('비밀번호는 필수입력사항입니다').css('color','red').css('font-size','15px');		
	}
	else if($('#rePwd').val()==$('#review_pwd').val()){
		if($('#purpose').val()=='modify'){
			window.location='/minishop/board/review/reviewModifyForm.do?review_seq='+$('#review_seq').val()+'&pg='+$('#pg').val();		
		}
		else if($('#purpose').val()=='delete'){
			var realDelete = confirm('정말 삭제하시겠습니까?');
			if(realDelete){
				window.location='/minishop/board/review/reviewDelete.do?review_seq='+$('#review_seq').val();}
		}
	}
	else{
		$('#alertText').val('비밀번호를 다시 입력하세요').css('color','red').css('font-size','15px');			
	}
});