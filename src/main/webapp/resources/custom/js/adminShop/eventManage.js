var deliveryList =null;

$(document).ready(function(){
	if($('#event_no').val()!=''||$('#event_no').val()!=null){
		$('#bannerNum').trigger('change','trigger');	
	}
	$.ajax({
		type: 'get',
		url : '/minishop/admin/shop/getEventList.do',
		dataType: 'json',
		success: function(data){
			$('#bannerDiv').empty();
			$('#couponDiv').empty();
			
			$.each(data.bannerList,function(index,items){
				$('#bannerDiv').append(items.event_banner);
			});
			
			$.each(data.couponBook,function(index,items){
				$('#couponDiv').append(items.couponBookHTML);
			});
			
			deliveryList= data.deliveryList;		
			
			$.each(deliveryList,function(index,items){

				$('<div>',{
					class: 'form-row justify-content-center'
				}).append($('<div>',{
					class: 'form-group col-2'
				})).append($('<div>',{
					class: 'form-group col-1',
					text: items.delivery_code
				})).append($('<div>',{
					class: 'form-group col-3',
					text: items.delivery_type
				})).append($('<div>',{
					class: 'form-group col-3',
					text: items.delivery_fee
				})).append($('<div>',{
					class: 'form-group col-3'
				})).appendTo($('#deliveryDiv'));
				
			});
			
		}
	});
	
});

$('#bannerNum').on('change',function(){
	$.ajax({
		type: 'get',
		url: '/minishop/admin/shop/getSelectedBanner.do',
		data: 'event_no='+$('#bannerNum :selected').val(),
		dataType: 'json',
		success: function(data){
			$('input[name=event_name]').val(data.banner.event_name);
			$('input[name=event_image]').val(data.banner.event_image);
			$('input[name=event_url]').val(data.banner.event_url);
			$('input[name=start_date]').val(data.banner.event_startdate);			
			$('input[name=end_date]').val(data.banner.event_enddate);	
	
			$('#event_no').val($('#bannerNum :selected').val());
		}
	});
	
});

$('#changeBannerBtn').click(function(){
	var today = new Date();
	var startdate = Date.parse($('input[name=start_date]').val());
	var enddate = Date.parse($('input[name=end_date]').val());
	var startdiff = startdate - today;
	var enddiff =enddate - today;
	var perioddiff = enddate - startdate;
	if($('input[name=event_name]').val()==''|| $('input[name=event_url]').val()==''||
	 $('input[name=start_date]').val()==''||$('input[name=end_date]').val()=='') alert('필요한 정보를 전부 입력해주세요');
	else if(startdiff>0 || enddiff<0|| perioddiff<0) alert('유효한 기간을 선택해주세요');
	else{
		$('#bannerManage').submit();
	}
});


$('input[name=selectionBtn]').on('change',function(){
	var state = $('input[name=selectionBtn]:checked').val();
	if(state=='new') {
		$('#coupon_no').prop('readonly',true);
		$('#searchCoupon').prop('disabled',true);
	}
	else {
		$('#coupon_no').prop('readonly',false);
		$('#searchCoupon').prop('disabled',false);		
	}
});

$('#searchCoupon').click(function(){
	if($('#coupon_no').val()=='') alert('쿠폰의 발행 번호를 입력하세요');
	else
		$.ajax({
			type: 'get',
			url: '/minishop/admin/shop/getSelectedCoupon.do',
			data: 'coupon_no='+$('#coupon_no').val(),
			dataType: 'json',
			success: function(data){
			if(data.coupon==null) alert('쿠폰 발행 번호를 다시 확인하세요[상단 목록 참조]');
			else{
				$('input[name=coupon_no]').val(data.coupon.coupon_no);
				$('input[name=coupon_name]').val(data.coupon.coupon_name);
				$('#target').val(data.coupon.coupon_target);
				$('#type').val(data.coupon.coupon_type);
				$('input[name=discount_mount]').val(data.coupon.discount_mount);	
				$('#deleteCoupon').prop('disabled',false);
			}
			}
		});
});

$('#issueCoupon').click(function(){
	var mount = 0;
	if($('input[name=discount_mount]').val()!=''){
		var mount = parseInt($('input[name=discount_mount]').val(),10);
	}
	if($('input[name=coupon_name]').val()==''||$('input[name=coupon_target] :selected').val()==''||
			$('input[name=coupon_type] :selected').val()=='')alert('쿠폰 발행을 위한 모든 사항을 입력하세요.');
	else if(mount<0){
		alert('0이상의 숫자만 입력되야 합니다.');}
	else{
		$('input[name=coupon_no]').val(0);
		if($('input[name=discount_mount]').val()=='') $('input[name=discount_mount]').val(0);
		$('#couponManage').submit();
	}
});

$('#modifyCoupon').click(function(){
	var mount = 0;
	if($('input[name=discount_mount]').val()!=''){
		var mount = parseInt($('input[name=discount_mount]').val(),10);
	}	
	if($('input[name=coupon_name]').val()==''||$('input[name=coupon_target] :selected').val()==''||
			$('input[name=coupon_type] :selected').val()=='')	alert('쿠폰 발행을 위한 모든 사항을 입력하세요.');
	else if(mount<0){
		alert('0이상의 숫자만 입력되야 합니다.');}
	else{
		if($('input[name=discount_mount]').val()=='') $('input[name=discount_mount]').val(mount);
		$.ajax({
			type: 'post',
			url : '/minishop/admin/shop/modifyCoupon.do',
			data : $('#couponManage').serialize(),
			dataType: 'text',
			success: function(data){
				if(data=='success') {
					alert('성공적으로 수정되었습니다.');
					window.location.reload();
				}
				else alert('수정이 실패하였습니다. 다시 한번 시도해주세요.');
			}
		});
	}
});


$('#deleteCoupon').click(function(){
	$.ajax({
		type: 'post',
		url : '/minishop/admin/shop/deleteCoupon.do',
		data : 'coupon_no='+$('input[name=coupon_no]').val(),
		dataType: 'text',
		success: function(data){
			if(data=='cannotDelete') {
				alert('회수 되지 않은 쿠폰이 존재하여 삭제가 불가능합니다. 사용자로부터 회수 후 삭제 바랍니다.');
				
			}else if(data=='success'){
				alert('성공적으로 삭제되었습니다.');
				window.location.reload();			
			}
			else alert('수정이 실패하였습니다. 다시 한번 시도해주세요.');
		}
	});
});

$('#delivery_type').on('change',function(){
	$.each(deliveryList,function(index,items){
		if($('#delivery_type').val()==items.delivery_code) $('input[name=delivery_fee]').val(items.delivery_fee);
	});
});

$('#deliveryModify').click(function(){
	if($('input[name=delivery_code]:selected').val()=='') alert('변경하실 배송분류를 선택해주세요');
	else if($('input[name=delivery_fee]').val()=='') alert('배송료를 입력해주세요');
	else{
		var deliveryFee = parseInt($('input[name=delivery_fee]').val(),10);	
		if($('input[name=delivery_code]:selected').val()=='1'&&(delevieryFee<5000)) alert('일반 배송료는 최소 5,000원 이상으로 설정해주세요.');
		else if($('input[name=delivery_code]:selected').val()=='2'&&(delevieryFee<10000)) alert('특별 배송료는 최소 10,000원 이상으로 설정해주세요.');
		else $('#deliveryFeeManage').submit();
	}
 
});