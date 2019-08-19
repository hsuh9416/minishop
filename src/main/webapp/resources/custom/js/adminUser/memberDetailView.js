function numberFormat(num){
	var regexp = /\B(?=(\d{3})+(?!\d))/g; 
	return num.toString().replace(regexp,',');
}

$(document).ready(function(){
	$('#requestedDelete').hide();
	$('#memberRestoreBtn').hide();
	$.ajax({
		type : 'get',
		url : '/minishop/admin/user/getMemberDetail.do',
		data : 'id='+$('#memberID').val(),
		dataType : 'json',
		success: function(data){
			var memberDTO = data.memberInfo.memberDTO;
			var orderList = data.memberInfo.orderList;
			var couponList = data.memberInfo.couponList;
			
			$('#id').text(memberDTO.id); 
			$('#name').text(memberDTO.name);
			if(memberDTO.state=='0') {
				$('#rank').text('관리자').attr('color','black');
				$('#benefitGivingBtn').prop('disabled',true);
				$('#infoSendingBtn').prop('disabled',true);				
			}
			else if(memberDTO.state=='2') $('#rank').text('특별').attr('color','blue');
			else if(memberDTO.state=='3'){
				$('#benefitGivingBtn').prop('disabled',true);
				$('#infoSendingBtn').prop('disabled',true);					
				$('#memberDeleteBtn').prop('disabled',false);
				$('#memberRestoreBtn').prop('disabled',false);
				$('#requestedDelete').show();
				$('#memberRestoreBtn').show();
				$('#dReturnAddr').text(memberDTO.delete_mail);
				if(memberDTO.delete_reason=='1') $('#dReason').text('[개인정보유출우려]');
				else if(memberDTO.delete_reason=='2') $('#dReason').text('[이용불편함]');
				else if(memberDTO.delete_reason=='3') $('#dReason').text('[이용빈도낮음]');
				else $('#dReason').text('[기타]');
				$('#dDetail').text(memberDTO.reason_etc);				
				$('#requestDate').text(memberDTO.delete_date);

			}
			else $('#rank').text('일반').attr('color','green');
			$('#email').text(memberDTO.email1+'@'+memberDTO.email2);
			if(memberDTO.tel1!=null&&memberDTO.tel2!=null&&memberDTO.tel3!=null){
				$('#tel').html('<p style="color:black;">'+memberDTO.tel1+'-'+memberDTO.tel2+'-'+memberDTO.tel3+'</p>');			
			}
			else{
				$('#tel').html('<p style="text-align:center;color:darkgrey;" align="center">[미입력]</p>');
			}
			$('#email').text(memberDTO.email1+'@'+memberDTO.email2);
			if(memberDTO.zipcode!=null&&memberDTO.addr1!=null&&memberDTO.addr2!=null){
				$('#address1').html('<p style="color:black;">['+memberDTO.zipcode+']&emsp;'+memberDTO.addr1+'</p>');
				$('#address2').html('<p style="color:black;">'+memberDTO.addr2+'</p>');			
			}
			else {
				$('#address2').html('<p style="text-align:center;color:darkgrey;" align="center">[미입력]</p>');
			}
			$('#point').text(memberDTO.point+' 점');
			$('#registerDate').text(memberDTO.registerdate);
			if(orderList==null||orderList.length==0){
				$('<div/>',{
					class: 'form-row justify-content-center'
				}).append($('<div/>',{
					class: 'col admin-col',
					align : 'center',
					html : '<font style="color:red;">[현재 진행중인 거래 또는 주문이 존재하지 않습니다.]</font>'	
				})).appendTo($('#orderForm'));				
			}
			else{
				var num = 0;
				$.each(orderList,function(index,items){
					num++;
					var orderState = '[주문완료]';
					if(items.coupon_duedate!=null){
						dueDate = items.coupon_duedate;
					}
					if (items.order_state=='1'){orderState='입금완료';}
					else if(items.order_state=='2'){orderState='배송대기중';}
					else if(items.order_state=='3'){orderState='배송중';}
					else if(items.order_state=='4'){orderState='환불진행중';}
					else if(items.order_state=='5'){orderState='배송완료';}
					else if(items.order_state=='6'){orderState='환불완료';}					
					$('<div/>',{
						class: 'form-row justify-content-center'
					}).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						html : num				
					})).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						html: items.order_no										
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html: items.order_date										
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html: numberFormat(items.order_total)									
					})).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						html: orderState									
					})).appendTo($('#orderForm'));
				});				
			}
			if(couponList==null||couponList.length==0){
				$('<div/>',{
					class: 'form-row justify-content-center'
				}).append($('<div/>',{
					class: 'col admin-col',
					align : 'center',
					html : '<font style="color:red;">[발급받은 쿠폰이 존재하지 않습니다.]</font>'	
				})).appendTo($('#couponForm'));
			}
			else{

				$.each(couponList,function(index,items){
					var couponType = '정액';
					var discount_mount;
					if(items.coupon_type=='1'){
						couponType = '정률';
						discount_mount = items.discount_mount+'%';
					}
					else if(items.coupon_type=='2'){
						couponType = '배송';
					}
					else{
						var money = numberFormat(items.discount_mount);
						discount_mount = money+'원';
					}
					
					$('<div/>',{
						class: 'form-row justify-content-center'
					}).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						html : items.coupon_no				
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html : items.coupon_name												
					})).append($('<div/>',{
						class: 'col-1',
						align : 'center',
						html : couponType											
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html : discount_mount										
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html : items.coupon_duedate										
					})).appendTo($('#couponForm'));
				});			
			}
		}
	});
});

$('#benefitGivingBtn').click(function(){
	var benefitGivePop = window.open('/minishop/admin/user/benefitGivingForm.do?target='+$('#memberID').val(),'회원 혜택 관리','width=565,height=530,resizable=no');
});

$('#infoSendingBtn').click(function(){
	var infoWritePop = window.open('/minishop/admin/user/infoWriteForm.do?target='+$('#memberID').val(),'회원 공지 발신','width=565,height=530,resizable=no');
});

$('#memberRestoreBtn').click(function(){
	var realRestore = confirm('회원 정보를 복구하시겠습니까? 복구시에는 자동으로 회원에 공지 메일이 발송됩니다');
	if(realRestore){
		$.ajax({
			type : 'get',
			url : '/minishop/admin/user/userRestore.do',
			data: 'id='+$('#memberID').val(),
			dataType: 'text',
			success: function(data){
				if(data=='success') {
					alert('회원 계정이 정상적으로 복구되었으며 관련 메일이 전송되었습니다');
					
					$('#benefitGivingBtn').prop('disabled',false);
					$('#infoSendingBtn').prop('disabled',false);	
					$('#memberDeleteBtn').prop('disabled',true);
					$('#memberDeleteBtn').hide();
					$('#memberRestoreBtn').hide();
				}
				else alert('오류가 발생하였습니다. 다시 한번 시도해주세요.');
			}			
		});
	}
});
$('#memberDeleteBtn').click(function(){
	var realDelete = confirm('회원 삭제를 하시면 해당 회원의 모든 정보가 DB에서 삭제됩니다. 정말로 삭제 하시겠습니까?');
	if(realDelete){
		$.ajax({
			type: 'get',
			url: '/minishop/admin/user/userDelete.do',
			data: 'id='+$('#memberID').val(),
			dataType: 'text',
			success: function(data){
				if(data=='checkDuedateForDelete') alert('상점 규정상 삭제요청으로부터 2주내에는 회원정보를 삭제하실 수 없습니다. 2주가 경과 후 삭제 바랍니다.');
				else if(data=='noRequestForDelete') {
					alert('삭제요청이 없는 회원입니다. 해당 회원에 상의 후 시도 바랍니다.');
					$('#memberDeleteBtn').prop('disabled',true);
				}
				else if(data=='success'){
					alert('회원정보가 정상적으로 삭제되었습니다');
					window.close();
				}
			}
		});
	}
});
$('#closeBtn').click(function(){
	window.close();
});