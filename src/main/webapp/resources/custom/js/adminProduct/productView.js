//0. 공통 변수
var unitcost= parseInt($('#unitcost').val(),10);	
var product_name_price = parseInt($('#product_name_price').val(),10);	

//1. 최초 시작시의 셋팅
$(document).ready(function(){
	if(product_name_price > unitcost) {
		$('#pridceDiff').attr('color','red');
	}
	else if(product_name_price < unitcost) {
		$('#pridceDiff').attr('color','blue');
	}else $('#pridceDiff').attr('color','black');

});

//2. 수정 버튼 클릭시 이동
$('#productModifyBtn').click(function(){
	window.location='/minishop/admin/product/productModifyForm.do?product_name_no='+$('#product_name_no').val();
});

//3. 삭제 버튼 클릭시의 삭제처리
$('#productDelete').click(function(){
	if($('#product_onstore').val()=='YES') {
		alert('현재 매장에 등록되어 있는 상품은 삭제하실 수 없습니다. 등록 해제를 하신 후 재시도해주세요.');}
	else {
		var result =confirm('해당 상품을 정말로 삭제하시겠습니까? 삭제시에는 재고관리 목록에서도 제거됩니다.');
		if(result){
			window.location='/minishop/admin/product/productDelete.do?product_name_no='+$('#product_name_no').val();}
	}
});