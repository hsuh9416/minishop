<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<input type="hidden" id ="state" value="${stateCode}">

<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var stateCode =$('#state').val();
	if(stateCode=='WrongFile'){
		alert('올바르지 못한 파일 형식이 업로드 되었습니다.상품 이미지는 이미지 양식으로만 업로드 될 수 있습니다.');
		window.history.back();
	}
	else if(stateCode=='success'){
		alert('수정이 완료되었습니다. 반영된 사항은 사용자 메인 화면에서 확인바랍니다.');
		window.location='/minishop/admin/shop/eventManage.do';
	}
	else if(stateCode=='issued'){
		alert('발행이 완료되었습니다. 쿠폰의 지급은 회원 관리란에서 수행하여 주시기 바랍니다.');
		window.location='/minishop/admin/shop/eventManage.do';
	}
	else if(stateCode=='fail'){
		alert('수정이 실패하였습니다. 다시 한번 시도바랍니다.');
		window.history.back();
	}else{
		alert('오류가 발생하였습니다. 관리자 메인 화면으로 돌아갑니다.');	
		window.location='/minishop/admin/shop/adminManage.do';	
	}	
});	
</script>
