$('#QAreturnAddr').focusout(function(){
	 if($('#QAreturnAddr').val()!=''){ 
		$('#QAreturnAddr').tooltip('disable');
	 	} 
	else{
		$('#QAreturnAddr').tooltip('enable');
		 }
});

$('#QAdetail').focusout(function(){
	if($('#QAdetail').val()!=''){ 
	   $('#QAdetail').tooltip('disable');
	  } 
	else{
	   $('#QAdetail').tooltip('enable');
	  }
});


$('#sendQABtn').click(function(){
		$('#QAreturnAddr').tooltip('disable');
		$('#QAdetail').tooltip('disable');	
	if($('#QAreturnAddr').val()==''){ 
		$('#QAreturnAddr').tooltip('enable');
		$('#QAreturnAddr').tooltip('show');
		$('#QAreturnAddr').focus();}
	else if($('#QAdetail').val()=='') {
		$('#QAdetail').tooltip('enable');
		$('#QAdetail').tooltip('show');
		$('#QAdetail').focus();}
	else {
		$.ajax({
			type : 'POST',
			url : '/minishop/member/memberQASend.do',
			data : {'subject': $('#QAtype').val() ,'content' : $('#QAdetail').val(), 'sendAddr' : $('#QAreturnAddr').val()},
				 
			success : function(){
				alert('문의가 성공적으로 접수되었습니다. 문의사항은 기재하신 메일주소로 시일 내에 답변해드리겠습니다. 감사합니다.');
				window.close();
			}
				 
		});
	}
});