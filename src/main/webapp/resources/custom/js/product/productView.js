
var stock= parseInt($('#stock').val(),10);
var unitcost= parseInt($('#unitcost').val(),10);

var getCookie = function(name) {
	  var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	  return value? value[2] : null;
};

//1.NumberFormat 함수
function formatNumber(x){
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//2. 직접 입력시 유효성 검사
$('#cart_qty').change(function (e){
	e.preventDefault();	
	var num = parseInt($('#cart_qty').val(),10);
	if(num<1) {
		alert('더이상 줄일수 없습니다.');
		num=1;
	}
	else if(num>stock){
		alert('재고 이상으로 담을 수 없습니다. [현재재고 :'+stock+'(개)]');
		num=stock;	
	}
	$('#cart_qty').val(num);	
	var totalcost = formatNumber(unitcost*num);
	$('#totalCost').text(totalcost);
});

//3.감소 버튼 클릭시 유효성 검사
$('#cart_minus').click(function(e){
	e.preventDefault();	
	$('#totalCost').empty();
	var num = parseInt($('#cart_qty').val(),10);
	num=num-1;	
	if(num<1) {
		alert('더이상 줄일수 없습니다.');
		num=1;
	}
	$('#cart_qty').val(num);	
	var totalcost = formatNumber(unitcost*num);
	$('#totalCost').text(totalcost);
});

//4. 증가버튼 클릭시 유효성 검사
$('#cart_plus').click(function(e){
	e.preventDefault();	
	var num = parseInt($('#cart_qty').val(),10);
	num=num+1;
	if(num>stock){ 
		alert('재고 이상으로 담을 수 없습니다. [현재 남은 재고 수량 :'+stock+'(개)]');
		num=stock;
	} 
	$('#cart_qty').val(num);
	var totalcost = formatNumber(unitcost*num);
	$('#totalCost').text(totalcost);
});

//5. 장바구니에 담는 버튼 클릭시 이벤트
$('#putCartBtn').click(function(){
	var num = parseInt($('#cart_qty').val(),10);	
	if(num>stock){
		alert('재고 이상으로 담을 수 없습니다. [현재재고 :'+stock+'(개)]');
	}
	else if(num<1){
		alert('최소 1개 이상 주문하셔야 합니다.');
	}
	else{
		if($('#memberID').val()==''||$('#guestID').val()!='') {
			 var goSignUp = confirm('회원가입 시에는 더욱 풍부한 혜택을 얻으실 수 있습니다. 회원 가입하시겠습니까?');
				if(goSignUp) {
					alert('회원 가입 화면으로 이동합니다');
					window.location='/minishop/member/writeForm.do';}
				else preOrderEvent();
		}
		else preOrderEvent();
	}
});

function preOrderEvent(){
	
	var buyNow = confirm('바로 구입하시겠습니까?');
	
	if(buyNow){
		var product_name_no = new Array();
		product_name_no[0] = $('#product_name_no').val();
		$('input[name=product_name_no]').val(product_name_no);
		$('input[name=cart_qty]').val($('#cart_qty').val());	
		$('#buyNowForm').submit();
	}
	
	else{
		$.ajax({
			type: 'post',
			url : '/minishop/trading/addCart.do',
			data: {'product_name_no':$('#product_name_no').val(),
					'cart_qty':$('#cart_qty').val()},
			success: function(){
				var seeCart = confirm('현재 품목을 장바구니에 담았습니다. 장바구니를 확인하러 가시겠습니까?');
				if(seeCart){
					window.location='/minishop/trading/userCart.do';
				}//if
				else{
					alert('쇼핑목록으로 돌아갑니다.');
					window.location='/minishop/product/categories.do?product_category_name=ALL';}//else
				}

			});
		}
}

$('#writeQABtn').click(function(){
	window.location='/minishop/board/qa/qaWriteForm.do?productID='+$('#productID').val();
});
$('#writeReviewBtn').click(function(){
	window.location='/minishop/board/review/reviewWriteForm.do?productID='+$('#productID').val();	
});
$('#product_like').click(function(){
	if($('#userName').val()==''&& $('#orderName').val()=='') {
		alert('좋아요를 하시려면 로그인이 필요합니다.');
		var login = confirm('로그인 화면으로 이동합니까?');
		if(login){
			window.location='/minishop/member/loginForm.do';
		}
	}
	else{
	$.ajax({
		type: 'get',
		url : '/minishop/product/likeOnAndOff.do',
		data: {'product_name_no':$('#product_name_no').val()},
		dataType: 'text',
		success: function(data){
			$('#SEQ').val(data);
			
			window.location.reload();
		}

	});
	}
	
});