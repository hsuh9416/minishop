
//1. 최초 시작시에 입점시 입력 정보란을 숨김
$(function(){
	$('#instore').hide();
	
 	$('input[type=radio][name=product_onstore]').change(function(){
 		if($('input[type=radio][name=product_onstore]:checked').val()=='YES'){
 			$('#instore').show();
 			$('#unitcost').val('0');
 		}
 		else{
 			$('#instore').hide();
 			$('#unitcost').val('');}
 	});
});

//2. 업로드 이벤트
$('#productUploadBtn').click(function(event){
	
		$('input[name=product_name_detail]').val(CKEDITOR.instances.editor_admin.getData());
		
		if($('#productName').val()=='') {
			alert('이름이 입력되지 않았습니다.');
			$('#productName').focus();}
		else if($('#product_name_price').val()=='')	{
			alert('가격이 입력되지 않았습니다.');
			$('#product_name_price').focus();}
		else if($('#product_name_image').val()=='')	{
			alert('사진이 업로드되지 않았습니다.');
			$('#product_name_image').focus();}
		else if($('#product_name_title').val()=='')	{
			alert('상품 소개 제목이 입력되지 않았습니다.');
			$('#product_name_title').focus();}
		else if(CKEDITOR.instances.editor_admin.getData()=='')	{
			alert('상품 소개 내용이 입력되지 않았습니다.');	
			$('#product_name_detail').focus();}
		else{
			if($('input[type=radio][name=product_onstore] :checked').val()=='YES'){
				if($('#productID').val()=='') {
					alert('등록코드가 입력되지 않았습니다.');
					$('#productID').focus();}
				else if($('#unitcost').val()=='') {
					alert('판매가격이 입력되지 않았습니다.');
					$('#unitcost').focus();	}
				else{$('#uploadForm').submit();}}
			else{$('#uploadForm').submit();}}
	});
