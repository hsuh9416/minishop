/**
 * 회원 정보 관리 화면 자바스크립트
 */
var cart;
var coupon;
var order;
$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/member/getUserInfo.do',
		dataType : 'json',
		success : function(data){
			cart = data.map.cartList;
			if(cart=='') $('#goCartMember').text('장바구니에 담긴 상품이 없습니다.');
			else $('#goCart').text(cart.length+'건');
			coupon = data.map.couponList;
			if(coupon=='') $('#goCoupon').text('사용할 수 있는 쿠폰이 존재하지 않습니다.');
			else $('#goCoupon').text(coupon.length+'개');		
			order = data.map.orderList;
			if(order=='') $('#orderlistPg').text('주문하신 내역이 없습니다.');
			else $('#orderlistPg').text(order.length+'건');		
	
		}				
	});//에이작스
});//ready

/* 1대1 문의*/
$('#personalQA').click(function(){
	$('#PQAModal').modal();
});

$('#QAreturnAddr').focusout(function(){
	 if($('#QAreturnAddr').val()!=''){ 
		$('#QAreturnAddr').tooltip('disable');
	 	} 
	else{
		$('#QAreturnAddr').tooltip('enable');
		 }
});

$('#QAdetail').focusout(function(){
	if($('#QAdetail').val()!=''){ 
	   $('#QAdetail').tooltip('disable');
	  } 
	else{
	   $('#QAdetail').tooltip('enable');
	  }
});


$('#sendQABtn').click(function(){
		$('#QAreturnAddr').tooltip('disable');
		$('#QAdetail').tooltip('disable');	
	if($('#QAreturnAddr').val()==''){ 
		$('#QAreturnAddr').tooltip('enable');
		$('#QAreturnAddr').tooltip('show');
		$('#QAreturnAddr').focus();}
	else if($('#QAdetail').val()=='') {
		$('#QAdetail').tooltip('enable');
		$('#QAdetail').tooltip('show');
		$('#QAdetail').focus();}
	else {
		$.ajax({
			type : 'POST',
			url : '/minishop/member/memberQASend.do',
			data : {'subject': $('#QAtype').val() ,'content' : $('#QAdetail').val(), 'sendAddr' : $('#QAreturnAddr').val()},
				 
			success : function(){
				alert('문의가 성공적으로 접수되었습니다. 문의사항은 기재하신 메일주소로 시일 내에 답변해드리겠습니다. 감사합니다.');
				window.location='/minishop/member/memberView.do';
			}
				 
		});
	}
});//1대1문의 접수

/* 회원 탈퇴*/	

$('#memberDelete').click(function(){
	$('#deleteModal').modal();	
});

$('#deleteBtn').click(function(){
	alert('삭제?!');
	$.ajax({
		type : 'get',
		url : '/minishop/member/delete.do',
		success : function(){	
				alert('정상적으로 삭제되었습니다. 탈퇴 후의 개인정보 관리 및 자세한 사항은 메일을 통해 확인 바랍니다.');
				window.location='/minishop/main/innerMain.do';
			}
		});	
});//deleteBtn

/* 회원 정보 수정 가기 */
$('#memberModify').click(function(){
	window.location='/minishop/member/memberModifyForm.do';
});
