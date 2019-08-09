<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var modifiedItem = ${item};
	alert('정상적으로 수정되었습니다.');
	
	if(modifiedItem=='qa') window.location='/minishop/board/qa/qaList.do';
	else if(modifiedItem=='review') window.location='/minishop/board/review/reviewList.do';
	else if(modifiedItem=='member') window.location='/minishop/member/memberView.do';
	else if(modifiedItem=='userAdmin') window.location='/minishop/admin/user/userManage.do';
	else if(modifiedItem=='productAdmin') window.location='/minishop/admin/product/productManage.do';
	else if(modifiedItem=='userCart') window.location='/minishop/trading/userCart.do';
	else window.location='/minishop/board/main/home.do';
});	
</script>