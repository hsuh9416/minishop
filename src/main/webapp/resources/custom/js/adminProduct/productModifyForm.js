//1. 최초 시작시에 셋팅
$(function(){
	var unitcost = $('#unitcost').val();
	$('input[type=radio][name=product_onstore][value='+$('#product_onstore').val()+']').prop('checked',true);
	$('input[name=product_category_no]').val($('#product_category_no').val());
	$('input[type=radio][name=promotioncode][value='+$('#promotioncode').val()+']').prop('checked',true);
	if($('#product_onstore').val()=='YES'){
		$('#instore').show();
	}else{
		$('#instore').hide();
	}
	

 	$('input[type=radio][name=product_onstore]').change(function(){
 		if($('input[type=radio][name=product_onstore]:checked').val()=='YES'){
 			$('#instore').show();
 			$('#unitcost').val(unitcost);
 		}
 		else{
 			$('#instore').hide();
 			$('#unitcost').val('0');}
 	});
 	

});

//2. 수정 버튼 이벤트
$('#productModifyBtn').click(function(event){

		$('input[name=product_name_detail]').val(CKEDITOR.instances.editor_admin.getData());

		if($('input[name=productName]').val()=='') {
			alert('이름이 입력되지 않았습니다.');
			$('input[name=productName]').focus();}
		else if($('input[name=product_name_price]').val()=='')	{
			alert('가격이 입력되지 않았습니다.');
			$('input[name=product_name_price]').focus();}
		else if($('input[name=product_name_titleinput]').val()=='')	{
			alert('상품 소개 제목이 입력되지 않았습니다.');
			$('input[name=product_name_title]').focus();}
		else if(CKEDITOR.instances.editor_admin.getData()=='')	{
			alert('상품 소개 내용이 입력되지 않았습니다.');	
			$('#product_name_detail').focus();}
		else{
			if($('input[name=product_onstore]').val()=='YES'){
				 if($('input[name=unitcost]').val()=='') {
					alert('판매가격이 입력되지 않았습니다.');
					$('input[name=unitcost]').focus();}
				 else if($('input[name=productID]').val()==''){
					alert('등록코드가 입력되지 않았습니다.');
					$('input[name=productID]').focus();}
				else{$('#modifyForm').submit();}}
			else{$('#modifyForm').submit();}}
	});

//3. 초기화
$('#modifyReset').click(function(){
	window.location.reload();
});