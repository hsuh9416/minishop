var cartTotal = 0;
var originalState = 0;
var newState = 0;
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
	
	if($('#new_order_state').val()!=null&&$('#new_order_state').val()!=''){
		newState = parseInt($('#new_order_state').val(),10);
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
			$('#order_state').text(orderDTO.order_state);
			originalState = orderDTO.order_state;
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
	if(newState!=originalState){
		$('#changeDiv').show();
		$('#originalState').text(originalState);
		$('#newState').text(newState);
	}
	else $('#changeDiv').hide();
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
			url: '/minishop/admin/order/changeDeliberyInfo.do',
			data:{},
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
		alert('마음의 준비중!');
	}
});

$('#extraAddCheck').click(function(){
	if($(this).is(':checked')){
		$('#order_statement').prop('readonly',false);		
		$('#order_deliverynum').prop('readonly',false);	
		$('#order_refundaccount').prop('readonly',false);			
		$('#modifyExtra').prop('disabled',false);			
	}
	else {

		$('#order_statement').prop('readonly',false);		
		$('#order_deliverynum').prop('readonly',false);	
		$('#order_refundaccount').prop('readonly',false);	
		$('#modifyExtra').prop('disabled',true);	
		window.location.reload();
	}
});

$('#modifyExtra').click(function(){
	if(($('#order_statement').val()==orderDTO.order_statement) && 
			($('#order_deliverynum').val()==deliveryNum) && 
			($('#order_refundaccount').val()==refundAccount)) alert('변동 사항이 없으므로 반영하지 않습니다.');
	else if($('#order_statement').val()=='') alert('해당 거래에 대한 추가 설명은 필수 사항입니다.');
	else if(newState>2&&newState<8&&($('#order_deliverynum').val()==''||$('#order_deliverynum').val()=='[미정]')) alert('송장번호의 입력을 확인해주세요.');
	else if((newState==4 || newState==6) && ($('#order_refundaccount').val()==''||$('#order_refundaccount').val()=='[미입력]')) alert('환불계좌의 입력을 확인해주세요.');
	else{
		alert('마음의 준비중!');
	}
});