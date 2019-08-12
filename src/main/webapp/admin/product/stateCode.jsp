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
	else if(stateCode=='상품등록완료'){
		alert('상품 등록이 완료되었습니다. 상품관리 화면으로 돌아갑니다.');
		window.location='/minishop/admin/product/productManage.do';
	}
	else if(stateCode=='입점완료'){
		alert('입점이 완료되었습니다. 재고는 재고관리 화면에서 반영 바랍니다.');
		window.location='/minishop/admin/product/inventoryManage.do';
	}else if(stateCode=='상품변경완료'){
		alert('상품 변경이 완료되었습니다. 상품관리 화면으로 돌아갑니다.');
		window.location='/minishop/admin/product/productManage.do';		
	}else if(stateCode=='deleted'){
		alert('상품 삭제가 완료되었습니다. 상품관리 화면으로 돌아갑니다.');
		window.location='/minishop/admin/product/productManage.do';		
	}else{
		alert('오류가 발생하였습니다. 상품관리 화면으로 돌아갑니다.');	
		window.location='/minishop/admin/product/productManage.do';	
	}	
});	
</script>
