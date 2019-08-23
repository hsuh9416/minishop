
$(document).ready(function(){
	$.ajax({
		type: 'get',
		url: '/minishop/admin/order/getPersonalOrderInfo.do',
		data: 'order_no'+$('#order_no').val(),
		dataType: 'json',
		success: function(data){
			var orderDTO = data.orderDTO;
			var paymentInfo = data.paymentInfo;
			
		}
	});
});