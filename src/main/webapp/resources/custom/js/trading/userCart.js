//0.초기화
var cartTotal = 0;
var totalCost = '';
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

//1. 시작시에 session에서 장바구니 불러오기
$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/trading/getCartList.do',
		dataType : 'json',
		success : function(data){
			
			var size = Object.keys(data.cartList).length;

			if(size>0){
			$.each(data.cartList, function(index, items){			
				var unitcost_format=formatNumber(items.unitcost);
				var totalCost=items.unitcost*items.cart_qty;
				cartTotal=cartTotal+totalCost;//합계 정산
				var totalCost_format=formatNumber(totalCost);
				$('<div/>',{
					class: 'form-row align-items-center'			
				}).append($('<div/>',{
					class : 'col-1',
					align : 'center'
				}).append($('<input/>',{
					class : 'form-check-input check',
					type : 'checkbox',
					name : 'check',
					value : items.product_name_no
				})).append($('<label/>',{
					class: 'form-check-label',
					html : items.product_name_no
				}))).append($('<div/>',{
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
					class : 'col-3',
					align : 'right',
					style : 'display:inline-block;padding:0;float:right;'
				}).append($('<input/>',{
					type : 'hidden',
					value : items.stock
				})).append($('<input/>',{
					type : 'hidden',
					value : items.cart_qty
				})).append($('<input/>',{
					type : 'number',
					value : items.cart_qty,
					id : 'changeQty',
					style : 'width:40px;height:30px;padding:0;border:none;'
				})).append('<span>개</span>').append($('<input/>',{
					type : 'button',
					class : 'btn btn-sm btn-info',
					value : '반영',
					id : 'modifyBtn'
				}))).append($('<div/>',{
					class : 'col-2',
					align : 'center',
					html : totalCost_format+'원'
				})).appendTo($('#cartListForm'));
			});
			$('#cartListForm').on('change','#changeQty',function(){
				var originalQty = $(this).prev().val();
				var newQty = $(this).val();
				var stock = $(this).prev().prev().val();
				var leftStock = stock-newQty;

				if(newQty<1){
					alert('최소 1개 이상으로 수량을 선택해주세요');
					$(this).val(1);
					return;
				}else if(newQty>99){
					alert('100개 이상은 주문하실 수 없습니다');
					$(this).val(originalQty);
					return;
				}else if(leftStock<0){
					alert('재고 이상은 주문하실 수 없습니다[현재 재고: '+stock+'(개)]');
					$(this).val(originalQty);
					return;					
				}
				var unicost = $(this).parent().prev().prev().val();
				var diff = (newQty-originalQty)*unicost;
				var newTotalcost = newQty*unicost;
				var newTotalcost_format = formatNumber(newTotalcost);
				
				$(this).parent().next().text(newTotalcost_format+'원');
				cartTotal=cartTotal+diff;
				totalCost = formatNumber(cartTotal);
				$('#totalDiv').text('총 '+totalCost+' 원');
				$(this).prev().val(newQty); 
				
			});
			$('#cartListForm').on('click','#modifyBtn',function(){
				var cart_qty = $(this).prev().prev().val();
				var unicost = $(this).parent().prev().prev().val();
				var product_name_no = $(this).parent().prev().prev().prev().prev().prev().children().prop('value');
				var doModify = confirm('해당 재고의 수량을 확정적으로 반영하시겠습니까?');
				if(doModify){
					window.location='/minishop/trading/modifyCart.do?product_name_no='+product_name_no+'&changeNum='+cart_qty;
				}
			});
		}
			else{
				$('#cartListForm').append('<div class="form-row align-items-center"><div class="col" align="center"><strong style="color:red;font-size:20px;">장바구니에 담긴 상품이 없습니다.</strong></div></div>');		
			}
			totalCost = formatNumber(cartTotal);
			$('#totalDiv').text('총 '+totalCost+' 원');
		}
	});

	
});

//2. 장바구니 전체 선택, 해제
$('#checkAll').click(function(){
	
	if($('#checkAll').prop('checked')){
		$('.check').prop('checked', true);
	}else{
		$('.check').prop('checked', false);
	}
});

//3. 장바구니 선택 삭제
$('#choiceDelete').click(function(){
	if($('.check:checked').length==0) alert("항목을 선택해주세요");
	else{
		$('#cartListForm').submit();
	}//else
});
$('#goCategory').click(function(){
	window.location='/minishop/product/categories.do'
});

//4. 장바구니 선택 주문
$('#choiceOrder').click(function(){
	if($('.check:checked').length==0) alert("항목을 선택해주세요");
	else{
		if($('#memberID').val()==''||$('#guestID').val()!='') {
			var signUp = confirm('회원가입 시에는 더욱 풍부한 혜택을 얻으실 수 있습니다. 회원 가입하시겠습니까');
			if(signUp) {
				alert('회원 가입 화면으로 이동합니다');
				window.location='/minishop/member/writeForm.do';
			}
			else goOrderForm();
		} 
		else goOrderForm();
	}
});

function goOrderForm(){
	
	alert('주문 화면으로 이동합니다');
	
	var product_name_no = new Array();
	
	$(".check:checked").each(function(index, items) { 
		product_name_no[index] = $(this).val();  
	});

	$('input[name=product_name_no]').val(product_name_no);	
	$('#cartOrderForm').submit();
}