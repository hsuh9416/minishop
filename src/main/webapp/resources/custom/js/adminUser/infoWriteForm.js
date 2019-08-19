function searchId(target){
	$.ajax({
		type: 'get',
		url : '/minishop/admin/user/getMemberDetail.do',
		data: 'id='+target,
		dataType: 'json',
		success : function(data){
			if(data.memberInfo.memberDTO==null){
				$('#targetInfo').text('존재하지 않는 아이디입니다. 다시 한번 검색해주세요').attr('color','red').attr('font-weight','bold');
				$('#inform_target').prop('readonly',false);					
				$('#searchTarget').prop('disabled',false);	
				$('#inform_target').empty();
				$('input[name=id]').val('');
				$('#target').val('');
				$('#inform_target').focus();
			}
			else
				$('#targetInfo').text('['+data.memberInfo.memberDTO.name+']님께 공지 메일을 보냅니다').attr('color','blue').attr('font-weight','bold');
				$('input[name=id]').val(target);
		}
	});	
}

$(document).ready(function(){
	var target = $('#target').val();
	if(target=='all'){
		$('input[name=selectTarget][value=all]').prop('checked',true);	
		$('#targetInfo').text('[전체] 회원님께 공지 메일을 보냅니다').attr('color','blue').attr('font-weight','bold');
	}else{
		$('input[name=selectTarget][value=person]').prop('checked',true);
		$('#inform_target').val(target);
			searchId(target);

	}
	
});

$('input[name=selectTarget]').on('change',function(){
	var select = $('input[name=selectTarget]:checked').val();
	$('#targetInfo').empty();
	if(select=='person'){
		$('#inform_target').prop('readonly',false);					
		$('#searchTarget').prop('disabled',false);
		if($('#target').val()=='all'|| $('#target').val()==''){
			$('#targetInfo').text('공지 메일을 발송할 회원 아이디를 입력하세요').attr('color','red').attr('font-weight','bold');
			$('#inform_target').focus();			
		}
		else{
			searchId($('#target').val());
		}
	}
	
	else{
		$('#targetInfo').text('[전체] 회원님께 공지 메일을 보냅니다').attr('color','blue').attr('font-weight','bold');
		$('#inform_target').prop('readonly',true);					
		$('#searchTarget').prop('disabled',true);	
		$('#target').val('all');
	}
});

$('#searchTarget').click(function(){
	if($('#inform_target').val()=='') alert('검색하실 아이디를 입력해주세요');
	else{
		searchId($('#inform_target').val());
	}
});
$('input[name=id]').focusout(function(){
	searchId($('input[name=id]').val());
});

$('#resetBtn').click(function(){
	window.location.reload();
});

$('#closeBtn').click(function(){
	window.close();
});

$('#putInformMail').click(function(){
	if($('input[name=selectTarget]:checked').val()=='person' && $('input[name=id]').val()=='') alert('송신할 대상을 지정하세요');
	else if($('input[name=subject]').val()==''||CKEDITOR.instances.editor_admin.getData()=='') alert('메일의 제목 또는 내용을 전부 입력하세요');
	else {
		$('input[name=content]').val(CKEDITOR.instances.editor_admin.getData());

		$.ajax({
			type: 'post',
			url: '/minishop/admin/user/infoWrite.do',
			data: $('#informSendForm').serialize(),
			dataType: 'text',
			success : function(data){
				if(data=='success'){
					alert('메일 발신이 성공적으로 이루어졌습니다.');
					window.close();
				}
				else if(data=='adminExcept'){
					alert('관리자는 전송 대상이 아닙니다. (구)관리자 계정인 경우에는 담당자에 직접 문의 바랍니다');
					window.close();
				}
				else{
					alert('메일 발신이 실패했습니다. 다시 시도해주세요.');
					window.location.reload();
				}
			}
		});
	}
});