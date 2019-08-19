$('#putQAAnswerMailBtn').click(function(){
	if(CKEDITOR.instances.editor_admin.getData()=='') alert('문의에 대한 답변을 작성하여야 합니다');
	else{
		var finalContext = '<hr style="border-top:black 3px double;width:100%;">'+
						   '<h5 align="center">['+$('input[name=receiver]').val()+'님께서 문의하신 내용입니다]</h5>'+							
						   '<hr style="border-top:black 3px double;width:100%;">'+	
						   $('#primaryText').html()+'<br><br>'+
							'<hr style="border-top:black 3px double;width:100%;">'+
							'<h5 align="center">[문의에 대한 저희 샵의 답변입니다]</h5>'+							
							'<hr style="border-top:black 3px double;width:100%;">'+		
							CKEDITOR.instances.editor_admin.getData();
		$('input[name=content]').val(finalContext);
		
		$.ajax({
			type: 'post',
			url: '/minishop/admin/user/replyPersonalQA.do',
			data: $('#informSendForm').serialize(),
			dataType: 'text',
			success: function(data){
				if(data=='success') {
					alert('성공적으로 전송되었습니다.');
					window.close();
					window.opener.reload();
				}
				else alert('오류로 인하여 실패하였습니다. 다시 한번 시도해주세요.');
			}
		});
	}
});

$('#resetBtn').click(function(){
	window.location.reload();
});


$('#closeBtn').click(function(){
	window.close();
});