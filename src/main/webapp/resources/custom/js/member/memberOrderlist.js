//0. 메소드

//StringBuilder 선언
function StringBuilder(value){
	this.strings = new Array();
	this.Append(value);
}

//StringBuilder append
StringBuilder.prototype.Append = function(value){
	if(value){
		this.strings.push(value);
	}
}

//StringBuilder clear
StringBuilder.prototype.Clear = function(){
	this.strings.length = 0;
}
//StringBuilder toString
StringBuilder.prototype.ToString = function(){
	return this.strings.join("");
}

function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

//1. 최초 시작시 주문 목록 호출하기
$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/member/getOrderList.do',
		dataType: 'json',
		success: function(data){
			$('#orderListForm').empty();
			
			if(data.orderList.length==0||data.orderList==null){
				$('#orderListForm').append('<h5 style="color:red;font-weight:bold;text-decoration:underline;>주문 내역이 존재하지 않습니다</h5>');}
			else{
				$.each(data.orderList,function(index,items){
					var state='[주문취소]';
					if(items.order_state==0) state='[주문완료]';
					else if(items.order_state==1) state='[입금완료]';
					else if(items.order_state==2) state='[배송대기중]';
					else if(items.order_state==3) state='[배송중]';
					else if(items.order_state==4) state='[환불진행중]';
					else if(items.order_state==5) state='[배송완료]';
					else if(items.order_state==6) state='[환불완료]';
					else if(items.order_state==7) state='[수취완료]';
					else state='[주문취소]';
					var total = 0;
					var str = new StringBuilder();
					
						str.Append('<a class="productViewA" href="/minishop/trading/orderView.do?order_no='+items.order_no+'">');
					$.each(items.orderList, function(idx,i){
						if(idx=='0') str.Append('['+i.productID+'] '+i.productName+'<br> 포함 총 '+items.orderList.length+' 건<br>');
						total = total + i.unitcost*i.cart_qty;
					});
					str.Append('[총] '+formatNumber(total)+' 원<br></a>');
					var productList = str.ToString();
					
					$('<div/>',{
						class: 'form-row justify-content-center'					
					}).append($('<div/>',{
						class: 'col-1',
						align: 'center',
						text: items.order_no
					})).append($('<div/>',{
						class: 'col-2',
						align: 'center',
						text: items.order_date						
					})).append($('<div/>',{
						class: 'col-4',
						align: 'center',
						html: productList						
					})).append($('<div/>',{
						class: 'col-2',
						align: 'center',
						text: state					
					})).append($('<div/>',{
						class: 'col-3',	
						align: 'center'				
					}).append($('<input/>',{
						type: 'button',
						class: 'btn btn-outline-secondary orderBtn',
						value: '주문취소',
						name : 'cancelBtn',
						id : 'cancel'+items.order_no
					})).append($('<input/>',{
						type: 'button',
						class: 'btn btn-outline-danger orderBtn',
						value: '환불요청',
						name : 'refundBtn',
						id : 'refund'+items.order_no
					})).append($('<input/>',{
						type: 'button',
						class: 'btn btn-outline-info orderBtn',
						value: '수취확인',
						name : 'confirmBtn',
						id : 'confirm'+items.order_no
					})).append($('<input/>',{
						type: 'button',
						class: 'btn btn-outline-dark orderBtn',
						value: '내역삭제',
						name : 'deleteBtn',
						id : 'delete'+items.order_no
					}))).appendTo($('#orderListForm'));
					if(items.order_state==0) $('#cancel'+items.order_no).show();
					else $('#cancel'+items.order_no).hide();
					if(items.order_state==1||items.order_state==2||items.order_state==5) $('#refund'+items.order_no).show();
					else $('#refund'+items.order_no).hide();
					if(items.order_state==3||items.order_state==5) $('#confirm'+items.order_no).show();
					else $('#confirm'+items.order_no).hide();
					if(items.order_state==8) $('#delete'+items.order_no).show();
					else $('#delete'+items.order_no).hide();
				});
				
				$('input[name=cancelBtn]').on('click',function(){
					var order_no =$(this).parent().prev().prev().prev().prev().text();
					var realCancel = confirm('정말로 취소하시겠습니까?');
					if(realCancel){
						$.ajax({
							type: 'get',
							url: '/minishop/trading/cancelOrder.do',
							data: 'order_no='+order_no,
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
				
				$('input[name=refundBtn]').on('click',function(){
					var order_no =$(this).parent().prev().prev().prev().prev().text();
					var refundPop = window.open('/minishop/trading/refundForm.do?order_no='+order_no,'환불요청','width=455,height=455,resizable=no');
				});
				
				$('input[name=confirmBtn]').on('click',function(){
					var order_no =$(this).parent().prev().prev().prev().prev().text();
					var realConfirm = confirm('수취확인을 하신 후에는 교환 또는 환불이 제한됩니다. 수취확인하시겠습니까?');
					if(realConfirm){
						$.ajax({
							type: 'get',
							url: '/minishop/trading/confirmDelivery.do',
							data: 'order_no='+order_no,
							success: function(){
								alert('수취확인 작업이 완료되었습니다.');
								window.location.reload();
							}
						});
					}
				});
				
				$('input[name=deleteBtn]').on('click',function(){
					var order_no =$(this).parent().prev().prev().prev().prev().text();
					var realDelete = confirm('주문내역을 삭제하시면 복구되지 않습니다. 삭제하시겠습니까?');
					if(realDelete){
						$.ajax({
							type: 'get',
							url: '/minishop/trading/deleteOrder.do',
							data: 'order_no='+order_no,
							success: function(){
								alert('정상적으로 삭제되었습니다.');
								window.location.reload();
							}
						});
					}
				});				
			}
		}
	});
});

//2. 1:1문의창 띄우기
$('#personalQABtn').click(function(){
	var personalQAPop = window.open('/minishop/member/personalQAForm.do','1:1문의','width=455,height=455,resizable=no');
});

//3. 돌아가기
$('#returnBtn').click(function(){
	window.history.back();
});