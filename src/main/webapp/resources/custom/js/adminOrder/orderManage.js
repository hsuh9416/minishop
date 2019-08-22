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
		url : '/minishop/admin/order/getUserOrderList.do',
		dataType: 'json',
		success: function(data){
			$('#orderListForm').empty();
			
			if(data.userOrderList.length==0||data.userOrderList==null){
				$('#orderListForm').append('<h5 style="color:red;font-weight:bold;text-decoration:underline;>주문 내역이 존재하지 않습니다</h5>');}
			else{
				$.each(data.userOrderList,function(index,items){
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
						if(idx=='0') str.Append('['+i.productID+'] 포함<br>총 '+items.orderList.length+' 건</a>');
						total = total + i.unitcost*i.cart_qty;
					});
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
						class: 'col-2',
						align: 'center',
						text: items.order_id						
					})).append($('<div/>',{
						class: 'col-2',
						align: 'center',
						text: items.order_name						
					})).append($('<div/>',{
						class: 'col-3',
						align: 'center',
						html: productList					
					})).append($('<div/>',{
						class: 'col-2',	
						align: 'center'				
					}).append($('<select/>',{
						id : 'state'+items.order_no,
						name : 'state',
						class: 'form-control-plaintext inline-form'
					}).append($('<option/>',{
						value : '0',
						text: '주문완료'
					})).append($('<option/>',{
						value : '1',
						text: '입금확인'
					})).append($('<option/>',{
						value : '2',
						text: '배송준비'
					})).append($('<option/>',{
						value : '3',
						text: '상품발송'					
					})).append($('<option/>',{
						value : '4',
						text: '환불요청확인'						
					})).append($('<option/>',{
						value : '5',
						text: '배송완료확인'						
					})).append($('<option/>',{
						value : '6',
						text: '환불완료'						
					})).append($('<option/>',{
						value : '7',
						text: '거래완료'						
					})).append($('<option/>',{
						value : '8',
						text: '주문취소'						
					})))).appendTo($('#orderListForm'));
					$('#state'+items.order_no).val(items.order_state);
					if(items.order_state==6||items.order_state==7||items.order_state==8) $('#state'+items.order_no).prop('disabled',true);
					
				});

			}
		}
	});
});


//3. 돌아가기
$('#returnBtn').click(function(){
	window.history.back();
});