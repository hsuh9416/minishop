function numberFormat(num){
	var regexp = /\B(?=(\d{3})+(?!\d))/g; 
	return num.toString().replace(regexp,',');
}

$(document).ready(function(){
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
			if(memberDTO.state=='0') $('#rank').text('관리자').attr('color','black');
			else if(memberDTO.state=='2') $('#rank').text('특별').attr('color','blue');
			else $('#rank').text('일반').attr('color','green');
			$('#email').text(memberDTO.email1+'@'+memberDTO.email2);
			if(memberDTO.tel1!=null&&memberDTO.tel2!=null&&memberDTO.tel3!=null){
				$('#tel').text(memberDTO.tel1+'-'+memberDTO.tel2+'-'+memberDTO.tel3);			
			}
			else{
				$('#tel').parent().addClass('centering');
				$('#tel').text('[미입력]').attr('color','darkgrey').css('align','center');
			}
			$('#email').text(memberDTO.email1+'@'+memberDTO.email2);
			if(memberDTO.zipcode!=null&&memberDTO.addr1!=null&&memberDTO.addr2!=null){
				$('#address1').html('['+memberDTO.zipcode+']&emsp;'+memberDTO.addr1);
				$('#address2').html(memberDTO.addr2);			
			}
			else {
				$('#address2').parent().addClass('centering');
				$('#address2').text('[미입력]').attr('color','darkgrey');
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
					if (items.order_state=='1'){orderState='[입금완료]';}
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
						html: items.order_total											
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
					var dueDate = '[무기한]';
					var couponType = '[정액할인]';
					var discount_mount;
					if(items.coupon_type=='1'){
						couponType = '[정률할인]';
						discount_mount = items.discount_mount+'%';
					}
					else if(items.coupon_type=='2'){
						couponType = '[배송비면제]';
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
						class: 'col-2',
						align : 'center',
						html : items.coupon_name												
					})).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						html : couponType											
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html : discount_mount										
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						html : dueDate										
					})).appendTo($('#couponForm'));
				});			
			}
		}
	});
});