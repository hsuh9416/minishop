var cartTotal = 0;
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
	
	$.ajax({
		type: 'get',
		url: '/minishop/trading/getOrderInfo.do',
		data: 'order_no='+$('input[name=order_no]').val(),
		dataType: 'json',
		success: function(data){
			orderDTO = data.orderDTO;
			paymentInfo = data.paymentInfo;
			orderList = data.productList;
			
			$('#order_no').text(orderDTO.order_no);
			$('#order_date').text(formatDate(orderDTO.order_date));
			
			originalState = parseInt(orderDTO.order_state,10);
			
			if(originalState==0) $('#order_state').text('주문완료');
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
			
			if(paymentInfo!=null&&paymentInfo.length!=0){
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
			else {$('<div/>',{
					class: 'form-row justify-content-center',
				}).append($('<div/>',{
					class: 'col',		
					align: 'center',
					html: '<h5 align="center" style="padding-top:15px;color:red;">결제 내역이 삭제되었거나 초기화되어 존재하지 않습니다.</h5>'
				})).appendTo($('#paymentForm'));
			}
			if(originalState==0){
				$('#orderCancelBtn').prop('disabled',false);
				$('#deliveryConfirmBtn').prop('disabled',true);
				$('#requestRefundBtn').prop('disabled',true);
			}
			else{
				$('#orderCancelBtn').prop('disabled',true);
				if(originalState==3||originalState==5) $('#deliveryConfirmBtn').prop('disabled',false);
				else $('#deliveryConfirmBtn').prop('disabled',true);
				$('#requestRefundBtn').prop('disabled',false);		
			}
			if(originalState>=3) {
				$('input[name=selectModify]').prop('disabled',true);
				if(originalState==4) {
					$('#requestRefundBtn').prop('disabled',true);
				}
			}
			if(originalState>=5) {
				$('#modifyCheck').prop('disabled',true);
				if(originalState!=5){
					$('#requestRefundBtn').prop('disabled',true);
				}
			}
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
			url: '/minishop/trading/changeOrderInfo.do',
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
			url: '/minishop/trading/changeOrderInfo.do',
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

$('#orderCancelBtn').click(function(){
	var realCancel = confirm('정말로 취소하시겠습니까?');
	if(realCancel){
		$.ajax({
			type: 'get',
			url: '/minishop/trading/cancelOrder.do',
			data: 'order_no='+$('input[name=order_no]').val(),
			dataType: 'text',
			success: function(data){
				if(data=='success'){
					alert('주문취소가 완료되었습니다.');
					window.location.reload();						
				}
				else if(data=='nonVerifiedAttempt'){
					alert('유효하지 않은 접근입니다. 해당페이지의 접근을 종료합니다');
					window.location='/minishop/main/home.do';
				}
				else{
					alert('오류로 인하여 주문취소가 반영되지 않았습니다. 다시 한번 시도해주시고 오류가 재발생시에는 고객센터에 문의주세요.');
					window.location.reload();
				}
			}
		});
	}
});

$('#requestRefundBtn').click(function(){
	var refundPop = window.open('/minishop/trading/refundForm.do?order_no='+$('input[name=order_no]').val(),'환불요청','width=455,height=455,resizable=no');
});

$('#deliveryConfirmBtn').click(function(){
	var realConfirm = confirm('수취확인을 하신 후에는 교환 또는 환불이 제한됩니다. 수취확인하시겠습니까?');
	if(realConfirm){
		$.ajax({
			type: 'get',
			url: '/minishop/trading/confirmDelivery.do',
			data: 'order_no='+$('input[name=order_no]').val(),
			success: function(){
				alert('수취확인 작업이 완료되었습니다.');
				window.location.reload();
			}
		});
	}
});
$('#personalQABtn').click(function(){
	var personalQAPop = window.open('/minishop/member/personalQAForm.do','1:1문의','width=455,height=455,resizable=no');
});

