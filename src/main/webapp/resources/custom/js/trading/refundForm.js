$('#doRefund').click(function(){
	if($('#refund_Num').val()==''|| $('#bankOption').val()=='') alert('환불계좌와 은행을 전부 입력해주세요.');
	else if($('#order_statement').val()=='') alert('환불사유를 전부 기입해주세요.');
	else{
		var order_refundaccount = order_refundaccount = '('+$('#bankOption option:selected').text()+')'+$('#refund_Num').val();
		$.ajax({
			type: 'post',
			url: '/minishop/trading/requestRefund.do',
			data: {'order_refundaccount' : order_refundaccount,
					'order_statement' : $('#order_statement').val(),
					'order_no' : $('#order_no').val()},
			dataType: 'text',
			success: function(data){
				if(data=='success'){
					alert('해당 주문서에 대하여 환불요청이 완료되었습니다.\n 자세한 사항은 안내 메일을 확인하세요.');
					opener.location.reload();
					window.close();
				}
				else alert('오류로 인하여 실패하였습니다.\n 다시 한번 시도해주시기 바랍니다.');
			}
		});
	}
});



$('#resetBtn').click(function(){
	window.location.reload();
});