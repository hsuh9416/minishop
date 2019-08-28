<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	if(${guestDTO!=null}){
		var order_no = ${guestDTO.order_no};
		alert('회원 로그인 후에 이용해주시기 바랍니다.');
		window.location='/minishop/trading/orderView.do?order_no='+order_no;
	}
	else{
		alert('로그인 후에 이용해주시기 바랍니다.');
		window.location='/minishop/member/loginForm.do';	
	}

});	
</script>