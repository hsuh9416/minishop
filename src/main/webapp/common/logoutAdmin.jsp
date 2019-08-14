<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var returnHome = confirm('관리자 계정에서 로그아웃하였습니다. 사용자 계정으로 복귀하시겠습니까?');
	if(returnHome) {
		alert('사용자계정으로 복귀합니다.');
		window.location='/minishop/member/memberView.do';
	}
	else {
		alert('사용자계정에서 로그아웃합니다.');
		window.location='/minishop/member/logout.do';
	}
	
});	
</script>