//0.초기화
//0-0 고정항목
var cartTotal = 0;
var discountableSubTotal = 0;

//0-1 변동 항목
var deliveryFee = 0;
var additionalCost = false;
var isSubmitted = false;
var subTotal =0;
var finalTotal = 0;

//0-2 호출전까지는 null인 항목
var orderList= null;
var deliveryPolicy = null;

//0-3 조건부 호출 항목
var memberDTO = null;
var memberPoint = 0;
var couponList = null;

//1. 공통 메소드
//1-1. 3자리마다 콤마
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

function confirmTel(num){
	var regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	return regExp.test(num);
}

//1-2. 배송료 적용
function setDeliveryFee(){
	if(deliveryPolicy!=null){
		if(cartTotal>=100000) deliveryFee = 0;
		else if(additionalCost==true) deliveryFee = parseInt(deliveryPolicy[1].delivery_fee,10);
		else deliveryFee =  parseInt(deliveryPolicy[0].delivery_fee,10);
		$('#deliveryFeeDiv').text('총 '+formatNumber(deliveryFee)+' 원');
		$('#subTotalDiv').text('총 '+formatNumber(cartTotal+deliveryFee)+' 원');		
	}
}

//1-3. 할인 적용 후 최종 지급액 표시
function setDiscount(){
	var point = 0; var coupon = 0;
	if($('#point').val()!='') point = parseInt($('#point').val(),10);
	if($('#coupon_no').val()!='') coupon = parseInt($('#coupon_no').val(),10);
	subTotal = point+coupon;
	if(subTotal>discountableSubTotal){
		if(coupon<=discountableSubTotal){
			$('#point').val(discountableSubTotal-coupon);
			subTotal = discountableSubTotal;
		}
		else{
			$('#point').val(0);
			$('#couponWarningDiv').text('[주의]쿠폰상당액의 초과 결제분은 환급되지 않습니다.').css('color','red');
			subTotal = discountableSubTotal;
		}
	}
	else {
		$('#discountTotal').text('총 '+formatNumber(subTotal)+' 원').css('color','blue');	
	}
	  finalTotal = cartTotal + deliveryFee - subTotal;
	if(finalTotal==0){
		$('input[name=payment][value=3]').prop('disabled',false);
		$('input[name=payment][value=3]').prop('checked',true);
		$('input[name=payment][value=1]').prop('disabled',true);
		$('input[name=payment][value=2]').prop('disabled',true);	
		$('#etcDiv').show();			
		$('#cardDiv').hide();	
		$('#bankDiv').hide();	
	}
	else{
		$('input[name=payment][value=3]').prop('disabled',true);
		$('input[name=payment][value=1]').prop('checked',true);
		$('input[name=payment][value=1]').prop('disabled',false);
		$('input[name=payment][value=2]').prop('disabled',false);	
		$('#cardDiv').show();			
		$('#bankDiv').hide();	
		$('#etcDiv').hide();			
	}
	$('#finalTotal').text('총 '+formatNumber(finalTotal)+' 원').css('color','red');
}

//1-4. 도서 산간지방 여부 확인하기
function checkAddtionalFee(){
	$.ajax({
		type: 'get',
		url : '/minishop/trading/verifyAdditionalFee.do',
		data: 'zipcode='+ $('#zipcode').val(),
		dataType : 'text',
		success : function(data){
			if(data=='exist') additionalCost = true;
			else additionalCost = false;	
			setDeliveryFee();
			
			setDiscount();	
		}	
	});
}

function setOrderList(orderList){
	$.each(orderList, function(index, items){			
		var unitcost_format=formatNumber(items.unitcost);
		var totalCost=items.unitcost*items.cart_qty;
		cartTotal=cartTotal+totalCost;//합계 정산
		if(items.promotioncode=='1'){
			discountableSubTotal=discountableSubTotal+totalCost;				
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
		setDeliveryFee();
		$('#productTotalDiv').text('총 '+formatNumber(cartTotal)+' 원');
		$('#deliveryFeeDiv').text('총 '+formatNumber(deliveryFee)+' 원');
		$('#subTotalDiv').text('총 '+formatNumber(cartTotal+deliveryFee)+' 원');		
		
}

//2. 최초 시작시에 셋팅
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
			deliveryPolicy = data.deliveryPolicy;
			
			setOrderList(orderList);
			
			$('#discoutableTotal').text('총 '+formatNumber(discountableSubTotal)+' 원').css('color','darkgreen');			
			if(discountableSubTotal==0){
				$('input[name=useBenefit][value=no]').prop('checked',true);
				$('input[name=useBenefit][value=yes]').prop('disabled',true);
				$('#point').val(0);
				$('#point').prop('disabled',true);	
				$('#coupon_no').prop('disabled',true);
				$('#couponDiv').text('* 할인가능액 없음').css('color','red');
				$('#member_point').text('0');	
			}
			$('#discountTotal').text('총 0 원').css('color','blue');	
			
			$('#finalTotal').text('총 '+formatNumber(cartTotal+deliveryFee)+' 원').css('color','red');			
			
				memberDTO = data.memberDTO;
			if(memberDTO!=null){
				
				$('#order_name').val(memberDTO.name);
				$('#order_receiver').val(memberDTO.name);
				$('#order_tel').val(memberDTO.tel1+'-'+memberDTO.tel2+'-'+memberDTO.tel3);
				$('#order_email').val(memberDTO.email1+'@'+memberDTO.email2);
				
				
				$('#zipcode').val(memberDTO.zipcode);

				$('#addr1').val(memberDTO.addr1);
				$('#addr2').val(memberDTO.addr2);
				 memberPoint = formatNumber(memberDTO.point);
				$('#member_point').text(memberPoint);
				
				if($('#zipcode').val()!=''){checkAddtionalFee();}
				
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
							coupon.coupon_amount = discountableSubTotal*coupon.discount_mount/100;
							type = '정률';
							discount= coupon.discount_mount+'(%)';
						}
						else{
							coupon.coupon_amount = 0;
						}
						$('<option/>',{
							align : 'center',
							value : coupon.coupon_amount,
							text : '['+coupon.coupon_no+']'+'['+coupon.coupon_name+']'+'['+type+']'+'['+discount+']'
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
				$('input[name=pesonalInfo][value=newP]').prop('checked',true);
				$('input[name=pesonalInfo][value=originalP]').prop('disabled',true);				
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

//3. 배송지역 변경 반영(구체적인 주소까지 작성 완료한 후에만 반영)
$('#addr2').on('change',function(){
	if($('#zipcode').val()!=''){
		checkAddtionalFee();
	}
});

//4. 회원이 기본 정보 신규로 전환하거나 그 반대의 경우
$('input[name=pesonalInfo]').on('change',function(){
	if($('input[name=pesonalInfo]:checked').val()=='newP'){
		$('#order_name').val('');
		$('#order_receiver').val('');
		$('#order_tel').val('');
		$('#order_email').val('');		
	}
	else{
		$('#order_name').val(memberDTO.name);
		$('#order_receiver').val(memberDTO.name);
		$('#order_tel').val(memberDTO.tel1+'-'+memberDTO.tel2+'-'+memberDTO.tel3);
		$('#order_email').val(memberDTO.email1+'@'+memberDTO.email2);	
	}
});

//5. 회원이 신규 또는 기존 배송지를 선택하는 경우
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
	checkAddtionalFee();
	setDeliveryFee();
	setDiscount();
});



//6. 할인 혜택을 사용할지 사용하지 안할지를 결정하는 경우
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
		
	}
	setDeliveryFee();
	setDiscount();
	
});

//7. 포인트 사용 유효성 검사
$('#point').focusout(function(){
	if($('#point').val()!=''){
		var number = parseInt($('#point').val(),10);
		if(number<0) {
			alert('포인트는 0보다 작게 설정하실 수 없습니다');
			$('#point').val(0);
		}
		else{
			if(discountableSubTotal!=0){
				var diff = discountableSubTotal - number;
				var discountable_String = formatNumber(discountableSubTotal);	
				if(diff<0) {
					alert('포인트는 할인 가능 한도 내에서만 사용하실 수 하실 수 있습니다.(할인 가능 한도: '+discountable_String+'(원)');
					if(memberDTO.point <discountableSubTotal) $('#point').val(memberDTO.point);
					else $('#point').val(discountableSubTotal);
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

//8. 쿠폰 사용 유효성 검사
$('#coupon_no').on('change',function(){
	if($('#coupon_no option:selected').text().includes('[배송][변동]')){
		deliveryFee = 0;
		$('#deliveryFeeDiv').text('총 '+formatNumber(deliveryFee)+' 원');
		$('#subTotalDiv').text('총 '+formatNumber(cartTotal+deliveryFee)+' 원');		
	}
	else{
		setDeliveryFee();	
	}
	 setDiscount();
});


//9. 결제 방법마다 창변경
$('input[name=payment]').on('change',function(){
	if($('input[name=payment]:checked').val()=='1'){
		$('#cardDiv').show();			
		$('#bankDiv').hide();	
		$('#etcDiv').hide();	
	}
	else if($('input[name=payment]:checked').val()=='2'){
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

//10. 우편번호 검색창 띄우기
$('#checkPost').click(function(){
	$('#wrap').show();
});

//11. 최종 주문서 제출
$('#choiceOrder').click(function(){
	if($('#order_name').val()==''||$('#order_receiver').val()==''||$('#order_tel').val()==''||$('#order_email').val()=='') alert('기본정보를 입력해주세요');
	else if(!confirmTel($('#order_tel').val())) alert('전화번호는 형식에 맞게 작성해주세요. ex>010-1234-5678/02-123-4567');	
	else if($('#zipcode').val()==''||$('#addr1').val()==''||$('#addr2').val()=='') alert('배송지를 입력해주세요');
	else if($('input[name=payment]:checked').val()=='1'&&!$('#cardPaid').is(':checked')) alert('카드결제란을 완성해주세요');
	else if($('input[name=payment]:checked').val()=='2'&&!$('#bankPaid').is(':checked')) alert('무통장결제란을 완성해주세요');	
	else{
		isSubmitted = true;	
		$.ajax({
			type: 'post',
			url : '/minishop/trading/putOrderForm.do',
			data : {'order_name':$('#order_name').val(),
					'order_receiver':$('#order_receiver').val(),
					'order_tel':$('#order_tel').val(),
					'order_email':$('#order_email').val(),
					'order_address':'['+$('#zipcode').val()+'] '+$('#addr1').val()+','+$('#addr2').val(),
					'point':$('#point').val(),
					'coupon_amount':$('#coupon_no option:selected').val(),					
					'coupon_option':$('#coupon_no option:selected').text(),
					'order_total': cartTotal+deliveryFee,
					'payment_amount': finalTotal,	
					'directOrder':$('#directOrder').val(),
					'payment_method':$('input[name=payment]:checked').val(),
					'order_deliveryfee':deliveryFee
			},
			dataType : 'text',
			success: function(data){
				if(data=='fail') {
					alert('오류로 인하여 전송이 실패하였습니다. 잠시 후 다시 한번 시도 바랍니다');
				}
				else {
					isSubmitted = true;	
					alert('주문이 성공적으로 접수되었습니다.주문서 확인 화면으로 이동합니다');
					window.location='/minishop/trading/orderView.do?order_no='+data;
				}
			}
		});
	}
});

//12. 전화면으로 돌아가기
$('#returnBtn').click(function(){
	var realReturn = confirm('돌아가기를 선택하시면 기존의 주문 내역서가 초기화됩니다. 정말로 돌아가시겠습니까?');
	if(realReturn) window.history.back();
});


//13. 화면 전환 이벤트
$(window).on('beforeunload',function(){
	if(!isSumitted){
		return '페이지 이동시 기존의 주문 내역서 양식이 제거됩니다. 정말 이동하시겠습니까?';		
	}
	else $(window).off('beforeunload');
});