var cartTotal = 0;
var newState = 0;
var originalState = 0;
var orderDTO = null;
var paymentInfo = null;
var orderList = null;
var deliveryNum = '[미정]';
var refundAccount='[미입력]';
//0.5 3자리마다 콤마 찍는 정규식
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

function checkTel(num){
	var regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	return regExp.test(num);
}

function checkEmail(text){
	var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	return regExp.test(text);
}

function formatDate(date){
	if(date!=null&&date!='') return date.split('-').join('.');
	else return '[미정]';
}

function setOrderList(orderList){
	$.each(orderList, function(index, items){			
		var unitcost_format=formatNumber(items.unitcost);
		var totalCost=items.unitcost*items.cart_qty;
		cartTotal=cartTotal+totalCost;
		var totalCost_format=formatNumber(totalCost);
		$('<div/>',{
			class: 'form-row align-items-center'			
		}).append($('<div/>',{
			class : 'col-1',
			align : 'center',
			text : items.product_name_no
		})).append($('<div/>',{
			class : 'col-2',
			align : 'center'
		}).append($('<img/>',{
			src : '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
			style : 'width:80px;height:80px;'
		}))).append($('<div/>',{
			class : 'col-2',
			align : 'center',
			html : items.productName
		})).append($('<input/>',{
			type : 'hidden',
			value : items.unitcost
		})).append($('<div/>',{
			class : 'col-2',
			align : 'center',
			html : unitcost_format+'원'
		})).append($('<div/>',{
			class : 'col-2',
			align : 'center',
			html : items.cart_qty+'개',
		})).append($('<div/>',{
			class : 'col-3',
			align : 'center',
			html : totalCost_format+'원'
		})).appendTo($('#cartListForm'));
	
	});			
}

$(document).ready(function(){
	$('#wrap').hide();
	$('#modifyDeliveryForm').hide();
	$('#changeDiv').hide();	
	
	if($('#new_state').val()!=null&&$('#new_state').val()!=''){
		newState = parseInt($('#new_state').val(),10);}
	
	if($('#old_state').val()!=null&&$('#old_state').val()!=''){
		originalState = parseInt($('#old_state').val(),10);}

	if(newState!=originalState) {
		$('#changeDiv').show();				
		
		if(newState==0) $('#newState').text('주문완료');
		else if(newState==1) $('#newState').text('입금완료');
		else if(newState==2) $('#newState').text('배송대기중');
		else if(newState==3) $('#newState').text('배송중');
		else if(newState==4) $('#newState').text('환불진행중');
		else if(newState==5) $('#newState').text('배송완료');
		else if(newState==6) $('#newState').text('환불완료');
		else if(newState==7) $('#newState').text('수취완료');
		else $('#newState').text('주문취소');		
		
		if(originalState==0) $('#oldState').text('주문완료');
		else if(originalState==1) $('#oldState').text('입금완료');
		else if(originalState==2) $('#oldState').text('배송대기중');
		else if(originalState==3) $('#oldState').text('배송중');
		else if(originalState==4) $('#oldState').text('환불진행중');
		else if(originalState==5) $('#oldState').text('배송완료');
		else if(originalState==6) $('#oldState').text('환불완료');
		else if(originalState==7) $('#oldState').text('수취완료');
		else $('#oldState').text('주문취소');			
	}

	if(originalState>=3) {
		$('input[name=selectModify]').prop('disabled',true);
	}
	
	if(originalState>=5) {
		$('#modifyCheck').prop('disabled',true);
		$('#extraAddCheck').prop('disabled',true);
	}

	$.ajax({
		type: 'get',
		url: '/minishop/admin/order/getPersonalOrderInfo.do',
		data: 'order_no='+$('input[name=order_no]').val(),
		dataType: 'json',
		success: function(data){
			orderDTO = data.orderDTO;
			paymentInfo = data.paymentInfo;
			orderList = data.productList;
			
			$('#order_no').text(orderDTO.order_no);
			$('#order_date').text(formatDate(orderDTO.order_date));
			
			if(originalState==0)  $('#order_state').text('주문완료');
			else if(originalState==1) $('#order_state').text('입금완료');
			else if(originalState==2) $('#order_state').text('배송대기중');
			else if(originalState==3) $('#order_state').text('배송중');
			else if(originalState==4) $('#order_state').text('환불진행중');
			else if(originalState==5) $('#order_state').text('배송완료');
			else if(originalState==6) $('#order_state').text('환불완료');
			else if(originalState==7) $('#order_state').text('수취완료');
			else $('#order_state').text('주문취소');
			
			$('#order_name').text(orderDTO.order_name);
			$('#order_id').text(orderDTO.order_id);
			$('#order_logtime').text(formatDate(orderDTO.order_logtime));	
			$('#order_address').text(orderDTO.order_address);
			$('#order_tel').val(orderDTO.order_tel);	
			$('#order_email').val(orderDTO.order_email);
			$('input[name=order_tel]').val(orderDTO.order_tel);	
			$('input[name=order_email]').val(orderDTO.order_email);	
			
			$('#order_statement').val(orderDTO.order_statement);
			if(orderDTO.order_deliverynum!=null&&orderDTO.order_deliverynum!='[미정]'){
				deliveryNum = orderDTO.order_deliverynum;
				$('#order_deliverynum').val(deliveryNum).css('color','blue');					
			}
			else $('#order_deliverynum').val(deliveryNum).css('color','red');		
			if(orderDTO.order_refundaccount!=null&&orderDTO.order_refundaccount!=''){
				refundAccount = orderDTO.order_refundaccount;
				$('#order_refundaccount').val(refundAccount);				
			}
			else $('#order_refundaccount').val(refundAccount).css('color','red');
			
			setOrderList(orderList);
			
			$('#productTotalDiv').text('총 '+formatNumber(cartTotal)+' 원');
			$('#deliveryFeeDiv').text('총 '+formatNumber(orderDTO.order_deliveryfee)+' 원');
			$('#subTotalDiv').text('총 '+formatNumber(cartTotal+orderDTO.order_deliveryfee)+' 원');	
			
			$.each(paymentInfo,function(index,items){
				$('<div/>',{
					class: 'form-row justify-content-center',
				}).append($('<div/>',{
					class: 'col-2',		
					align: 'center',
					text: items.payment_method
				})).append($('<div/>',{
					class: 'col-3',		
					align: 'center',
					text: formatDate(items.payment_date)		
				})).append($('<div/>',{
					class: 'col-3',		
					align: 'center',
					text: formatNumber(items.payment_amount)+'원'			
				})).append($('<div/>',{
					class: 'col-4',		
					align: 'center',
					text: items.payment_state				
				})).appendTo($('#paymentForm'));
			});
			
			

		
		}
	});

});

$('input[name=selectModify]').on('change',function(){
	if($(this).val()=='new') {
		$('#modifyDeliveryForm').show();
		$('#checkPost').prop('disabled',false);
		$('#wrap').hide();
	}
	else {
		$('#checkPost').prop('disabled',true);
		$('#wrap').hide();
		$('#modifyDeliveryForm').hide();
	}
});

$('#checkPost').click(function(){
	$('#wrap').show();
});

$('#doModifyDelivery').click(function(){
	if($('#zipcode').val()==''||$('#addr1').val()==''||$('#addr2').val()=='') alert('주소를 올바르게 전부 입력해주세요');
	else{
		$.ajax({
			type: 'post',
			url: '/minishop/admin/order/changeOrderInfo.do',
			data:{'order_address':'['+$('#zipcode').val()+'] '+$('#addr1').val()+','+$('#addr2').val(),
				  'modify_type' : 'deliveryInfo',
				  'order_no':$('input[name=order_no]').val()},
			dataType: 'text',
			success: function(data){
				if(data=='success'){
					alert('성공적으로 반영되었습니다.');
					window.location.reload();
				}
				else{
					alert('오류로 인하여 변경이 실패하였습니다. 다시 한번 시도해주세요.');
				}
			}
		});
	}
});

$('#modifyCheck').click(function(){
	if($(this).is(':checked')){
		$('#order_tel').prop('readonly',false);		
		$('#order_email').prop('readonly',false);	
		$('#modifyContact').prop('disabled',false);			
	}
	else {
		$('#order_tel').val(orderDTO.order_tel);
		$('#order_tel').prop('readonly',true);		
		$('#order_email').val(orderDTO.order_email);	
		$('#order_email').prop('readonly',true);
		$('#modifyContact').prop('disabled',true);	
	}
});

$('#modifyContact').click(function(){
	if(($('#order_tel').val()==orderDTO.order_tel) && ($('#order_email').val()==orderDTO.order_email)) alert('변동 사항이 없으므로 반영하지 않습니다.');
	else if(!checkTel($('#order_tel').val()||!checkEmail($('#order_email').val()))) alert('형식이 올바르지 않습니다.');
	else{
		$.ajax({
			type: 'post',
			url: '/minishop/admin/order/changeOrderInfo.do',
			data:{'order_tel':$('#order_tel').val(),
				  'order_email' : $('#order_email').val(),
				  'modify_type' : 'contactInfo',
				  'order_no':$('input[name=order_no]').val()},
			dataType: 'text',
			success: function(data){
				if(data=='success'){
					alert('성공적으로 반영되었습니다.');
					window.location.reload();
				}
				else{
					alert('오류로 인하여 변경이 실패하였습니다. 다시 한번 시도해주세요.');
				}
			}
		});
	}
});

$('#extraAddCheck').click(function(){
	if($(this).is(':checked')){	
		if(originalState>=3)$('#order_deliverynum').prop('readonly',true);	
		else $('#order_deliverynum').prop('readonly',false);	
		$('#order_refundaccount').prop('readonly',false);			
		$('#modifyExtra').prop('disabled',false);			
	}
	else {	
		$('#order_deliverynum').prop('readonly',true);	
		$('#order_refundaccount').prop('readonly',true);	
		$('#modifyExtra').prop('disabled',true);	
		window.location.reload();
	}
});

$('#modifyExtra').click(function(){
	if($('#order_deliverynum').val()==deliveryNum && $('#order_refundaccount').val()==refundAccount) alert('변동 사항이 없으므로 반영하지 않습니다.');
	else if(newState>2&&newState<8&&($('#order_deliverynum').val()==''||$('#order_deliverynum').val()=='[미정]')) alert('송장번호의 입력을 확인해주세요.');
	else if((newState==4 || newState==6) && ($('#order_refundaccount').val()==''||$('#order_refundaccount').val()=='[미입력]')) alert('환불계좌의 입력을 확인해주세요.');
	else{
		if($('#order_deliverynum').val()=='') $('#order_deliverynum').val('[미정]');
		if($('#order_refundaccount').val()=='') $('#order_refundaccount').val('[미입력]');
		$.ajax({
			type: 'post',
			url: '/minishop/admin/order/changeOrderInfo.do',
			data:{'order_deliverynum' : $('#order_deliverynum').val(),				
				  'order_refundaccount' : $('#order_refundaccount').val(),
				  'modify_type' : 'extraInfo',
				  'order_no':$('input[name=order_no]').val()},
			dataType: 'text',
			success: function(data){
				if(data=='success'){
					alert('성공적으로 반영되었습니다.');
					window.location.reload();
				}
				else{
					alert('오류로 인하여 변경이 실패하였습니다. 다시 한번 시도해주세요.');
				}
			}
		});
	}
});

$('#stateChangeBtn').click(function(){
	if(newState>2&&newState<8&&($('#order_deliverynum').val()==''||$('#order_deliverynum').val()=='[미정]')) alert('송장번호의 입력을 확인하고 먼저 주문서에 반영해주세요.');
	else if((newState==4 || newState==6) && ($('#order_refundaccount').val()==''||$('#order_refundaccount').val()=='[미입력]')) alert('환불계좌의 입력을 확인하고 먼저 주문서에 반영해주세요.');
	else{
		var realChange = confirm('[주의]거래상태는 현재 입력된 정보가 아닌 반영이 완료된 정보에 기반하여 변경됩니다.\n'+
								'배송정보,연락정보,추가정보가 정확하게 수정 및 반영되었는지 다시 한번 확인하신 후가 아니라면 취소 버튼을'+
								'확인이 완료되어 진행하시려면 확인 버튼을 클릭해주세요.');
		if(realChange){
			$.ajax({
				type: 'post',
				url: '/minishop/admin/order/changeOrderState.do',
				data:{'newState' : newState,				
					  'order_no':$('input[name=order_no]').val()},
				dataType: 'text',
				success: function(data){
					if(data=='success'){
						alert('성공적으로 반영되었습니다.');
						window.close();
					}
					else{
						alert('오류로 인하여 변경이 실패하였습니다. 다시 한번 시도해주세요.');
					}
				}
			});			
		}
		else alert('취소하셨습니다. 주문 정보의 변경 상태를 꼼꼼히 확인해주시기 바랍니다.');
	}
});