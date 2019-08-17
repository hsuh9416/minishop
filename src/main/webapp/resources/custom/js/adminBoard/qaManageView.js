$('#adminWriteBtn').click(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/admin/board/qaManageWrite.do',
		data : {'admin_pseq':$('#admin_pseq').val(),
				'user_id':$('#user_id').val(),	
				'admin_content' :$('#admin_content').val()},
		success :function(){
			alert('답변 작성을 완료하였습니다. 목록으로 돌아갑니다.');
			window.location='/minishop/admin/board/qaManage.do?pg='+$('#pg').val();
		}
	});
});
$('#resetAns').click(function(){
	window.location.reload();
});