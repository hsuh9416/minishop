/**
 * 관리자 상품 관리 관련 자바스크립트
 */
var notitleMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">제목을 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var noContentMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">내용을 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var noPwdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">비밀번호를 설정해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var emptyPwdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">비밀번호를 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var missMatchingPwdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">비밀번호를 다시 확인해 주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

function toJson(form) {
	var obj = {};
	var arr = form;
	$.each(arr, function() {
		if (obj[this.name]) {
			if (! obj[this.name].push) {
				obj[this.name] = [obj[this.name]];
			}
			obj[this.name].push(this.value || '');
		} else {
			obj[this.name] = this.value || '';
		}
	});
	obj= JSON.stringify(obj);
	return obj;
}

$('#qaWriteBtn').click(function(){
	$('#missing').empty();
	if($('#qa_subject').val()=='') $('#missing').append(notitleMsg).alert();
	else if($('#qa_content').val()=='') $('#missing').append(noContentMsg).alert();
	else if($('#qa_pwd').val()=='') $('#missing').append(noPwdMsg).alert();
	else if($('#qa_pwd').val()!=$('#qa_repwd').val()) $('#missing').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/mallproject/board/qa/qaWrite.do',
			data : {'qa_seq':$('#qa_seq').val(),
					'qa_subject':$('#qa_subject').val(),
					'user_id' : $('#user_id').val(),
					'qa_content':$('#qa_content').val(),
					'productid': $('#productid option:selected').val(),
					'qa_pwd':$('#qa_pwd').val(),					
					'qa_state':$('#qa_state').val()},
			success : function(){
				alert('성공적으로 문의가 접수되었습니다! 문의 게시판으로 이동합니다.');
				window.location='/mallproject/board/qa/qaList.do';}
			
		});
	}
});

$('#qaReset').click(function(){
	window.location.reload();
});

$('#qaReturn').click(function(){
	window.history.back();
});

$('#qaModifyBtn').click(function(){
	$('#missingMod').empty();
	if($('#qa_subject').val()=='') $('#missingMod').append(notitleMsg).alert();
	else if($('#qa_content').val()=='') $('#missingMod').append(noContentMsg).alert();
	else if($('#qa_pwd').val()=='') $('#missingMod').append(emptyPwdMsg).alert();
	else if($('#qa_pwd').val()!=$('#qa_check').val()) $('#missingMod').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/mallproject/board/qa/qaModify.do',
			data : {'qa_subject':$('#qa_subject').val(),
					'qa_content':$('#qa_content').val(),
					'productid': $('#productid option:selected').val(),
					'qa_pwd':$('#qa_pwd').val(),					
					'qa_state':$('#qa_state').val()},
			success : function(){
				alert('문의 게시글이 수정되었습니다.');
				window.location='/mallproject/board/qa/qaView.do?qa_seq='+$('#qa_seq').val()+'&pg='+$('#pg').val();}
		});
	}
});

//datepicker:START

//datepicker:END




