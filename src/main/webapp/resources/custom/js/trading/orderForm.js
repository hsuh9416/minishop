//0.초기화
var cartTotal = 0;
var deliveryFee = 0;//임시
var discoutableTotal = 0;
var couponDiscountTotal = 0;
var orderList= null;
var memberDTO = null;
var memberPoint = 0;
var couponList = null;

function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

function setDeliveryFee(cartTotal){
	if(cartTotal>=30000) deliveryFee = 0;
	else deliveryFee = 5000;
}

$(document).ready(function(){
	$('#wrap').hide();
	$('#bankDiv').hide();	
	$('#etcDiv').hide();	
	$.ajax({
		type: 'get',
		url: '/minishop/trading/getPreOrderInfo.do',
		dataType: 'json',
		success: function(data){
			orderList = data.orderList;
			$.each(orderList, function(index, items){			
				var unitcost_format=formatNumber(items.unitcost);
				var totalCost=items.unitcost*items.cart_qty;
				cartTotal=cartTotal+totalCost;//합계 정산
				if(items.promotioncode=='1'){
					discoutableTotal=discoutableTotal+totalCost;				
				}
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
				}).append($('<a/>',{
					href : '/minishop/product/productView.do?product_name_no='+items.product_name_no
				}).append($('<img/>',{
					src : '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
					style : 'width:80px;height:80px;'
				})))).append($('<div/>',{
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
				setDeliveryFee(cartTotal);
			$('#productTotalDiv').text('총 '+formatNumber(cartTotal)+' 원');
			$('#deliveryFeeDiv').text('총 '+formatNumber(deliveryFee)+' 원');
			$('#subTotalDiv').text('총 '+formatNumber(cartTotal+deliveryFee)+' 원');
			discoutableTotal=discoutableTotal+deliveryFee;
			$('#discoutableTotal').text('총 '+formatNumber(discoutableTotal)+' 원').css('color','darkgreen');			
			if(discoutableTotal==0){
				$('input[name=useBenefit][value=no]').prop('checked',true);
				$('input[name=useBenefit][value=yes]').prop('disabled',true);
				$('#point').val(0);
				$('#point').prop('disabled',true);	
				$('#coupon_no').prop('disabled',true);
				$('#couponDiv').text('* 할인가능액 없음').css('color','red');
				$('#member_point').text('0');	
			}
			$('#discoutTotal').text('총 0 원').css('color','blue');	
			
			$('#finalTotal').text('총 '+formatNumber(cartTotal+deliveryFee)+' 원').css('color','red');			
			
				memberDTO = data.memberDTO;
			if(memberDTO!=null){
				$('#zipcode').val(memberDTO.zipcode);
				$('#addr1').val(memberDTO.addr1);
				$('#addr2').val(memberDTO.addr2);
				 memberPoint = formatNumber(memberDTO.point);
				$('#member_point').text(memberPoint);
				
				couponList = data.couponList;
				if(couponList!=null){
					$.each(couponList,function(i,coupon){
						coupon.coupon_amount =  0;
						var type = '배송';
						var discount = '변동';
						if(coupon.coupon_type=='0'){
							coupon.coupon_amount = coupon.discount_mount;
							type = '정액';
							discount= formatNumber(coupon.discount_mount)+'(원)';
						}
						else if(coupon.coupon_type=='1'){
							coupon.coupon_amount = discoutableTotal*coupon.discount_mount/100;
							type = '정률';
							discount= coupon.discount_mount+'(%)';
						}
						else{
							coupon.coupon_amount = deliveryFee;
						}
						$('<option/>',{
							align : 'center',
							value : coupon.coupon_amount,
							text : '['+coupon.coupon_no+']'+'['+coupon.coupon_name+']'+'['+type+']'+'['+discount+']',
							name : coupon.coupon_no+''
						}).appendTo($('#coupon_no'));							
					});
				}
				else {
					$('<option/>',{
						align : 'center',
						value : 0,
						text : '[소지하신 쿠폰이 존재하지 않습니다]'
					}).appendTo($('#coupon_no'));					
				}

			}
			else{
				$('input[name=delivery][value=new]').prop('checked',true);
				$('input[name=delivery][value=original]').prop('disabled',true);	
				$('input[name=useBenefit][value=no]').prop('checked',true);
				$('input[name=useBenefit][value=yes]').prop('disabled',true);
				$('#point').val(0);
				$('#point').prop('disabled',true);	
				$('#coupon_no').prop('disabled',true);
				$('#couponDiv').text('* 대상이 아닙니다').css('color','red');
				$('#member_point').text('0');
			}
		}
	});
	
});

$('input[name=delivery]').on('change',function(){
	if($('input[name=delivery]:checked').val()=='new'){
		$('#zipcode').val('');
		$('#addr1').val('');
		$('#addr2').val('');		
	}
	else{
		$('#zipcode').val(memberDTO.zipcode);
		$('#addr1').val(memberDTO.addr1);
		$('#addr2').val(memberDTO.addr2);		
	}
});

$('input[name=useBenefit]').on('change',function(){
	if($('input[name=useBenefit]:checked').val()=='yes'){
		$('#point').prop('disabled',false);	
		$('#coupon_no').prop('disabled',false);
		$('#couponDiv').text('');
		$('#member_point').text(memberPoint);
	}
	else{
		$('#point').val(0);
		$('#point').prop('disabled',true);	
		$('#coupon_no').val(0);
		$('#coupon_no').prop('disabled',true);
		$('#couponDiv').text('*사용하지 않음').css('color','red');
		$('#member_point').text('0');	
		setDiscount();
	}
});

$('#point').focusout(function(){
	if($('#point').val()!=''){
		var number = parseInt($('#point').val(),10);
		if(number<0) {
			alert('포인트는 0보다 작게 설정하실 수 없습니다');
			$('#point').val(0);
		}
		else{
			if(discoutableTotal!=0){
				var diff = discoutableTotal - number;
				var discountable_String = formatNumber(discoutableTotal);	
				if(diff<0) {
					alert('포인트는 할인 가능 한도 내에서만 사용하실 수 하실 수 있습니다.(할인 가능 한도: '+discountable_String+'(원)');
					if(memberDTO.point <discoutableTotal) $('#point').val(memberDTO.point);
					else $('#point').val(discoutableTotal);
				}
				else if(memberDTO.point-number<0) {
					alert('보유하신 포인트 한도 내에서만 사용 가능합니다');
					$('#point').val(memberDTO.point);
				}
				else {$('#point').val(number);}
				setDiscount();
			}
			else alert('포인트를 사용할 수 없습니다');
		}
	}
});	

$('#coupon_no').on('change',function(){
	 setDiscount();
});

function setDiscount(){
	var point = 0;
	if($('#point').val()!='') point = parseInt($('#point').val(),10);
	var coupon = parseInt($('#coupon_no').val(),10);
	var subTotal = point+coupon;
	if(subTotal>discoutableTotal){
		if(coupon<=discoutableTotal){
			$('#point').val(discoutableTotal-coupon);
			subTotal = discoutableTotal;
		}
		else{
			$('#point').val(0);
			$('#couponWarningDiv').text('[주의]쿠폰상당액의 초과 결제분은 환급되지 않습니다.').css('color','red');
			subTotal = discoutableTotal;
		}
	}
	else {
		$('#discoutTotal').text('총 '+formatNumber(subTotal)+' 원').css('color','blue');	
	}
	var finalTotal = cartTotal + deliveryFee - subTotal;
	if(finalTotal==0){
		$('input[name=payment][value=etc]').prop('disabled',false);
		$('input[name=payment][value=etc]').prop('checked',true);
		$('input[name=payment][value=card]').prop('disabled',true);
		$('input[name=payment][value=bank]').prop('disabled',true);	
		$('#etcDiv').show();			
		$('#cardDiv').hide();	
		$('#bankDiv').hide();	
	}
	else{
		$('input[name=payment][value=etc]').prop('disabled',true);
		$('input[name=payment][value=card]').prop('checked',true);
		$('input[name=payment][value=card]').prop('disabled',false);
		$('input[name=payment][value=bank]').prop('disabled',false);	
		$('#cardDiv').show();			
		$('#bankDiv').hide();	
		$('#etcDiv').hide();			
	}
	$('#finalTotal').text('총 '+formatNumber(finalTotal)+' 원').css('color','red');
}

$('input[name=payment]').on('change',function(){
	if($('input[name=payment]:checked').val()=='card'){
		$('#cardDiv').show();			
		$('#bankDiv').hide();	
		$('#etcDiv').hide();	
	}
	else if($('input[name=payment]:checked').val()=='bank'){
		$('#bankDiv').show();			
		$('#cardDiv').hide();	
		$('#etcDiv').hide();					
	}
	else{
		$('#etcDiv').show();			
		$('#cardDiv').hide();	
		$('#bankDiv').hide();		
	}
});

$('#checkPost').click(function(){
	$('#wrap').show();
});

$('#choiceOrder').click(function(){
	alert('마음의 준비중!');
});
$('#returnBtn').click(function(){
	var realReturn = confirm('돌아가기를 선택하시면 기존의 주문 내역서가 초기화됩니다. 정말로 돌아가시겠습니까?');
	if(realReturn) window.history.back();
});