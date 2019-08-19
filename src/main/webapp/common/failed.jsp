<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var faliedReason = ${reason};
	if(failedReason=='checkPeriodForDelete') alert('상점 규정상 삭제요청으로부터 2주내에는 회원정보를 삭제하실 수 없습니다. 2주가 경과 후 삭제 바랍니다.');
	else alert('오류로 인하여 실패하였습니다. 다시 한번 시도해주십시오.');

	window.history.back();
});	
</script>