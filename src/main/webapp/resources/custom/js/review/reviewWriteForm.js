//1.시작시에 리뷰 목록을 불러오기
$(document).ready(function(){	
	$.ajax({
		type : 'get',
		url : '/minishop/product/getAllproduct.do',
		dataType : 'json',
		success : function(data){
			$('#productid option:gt(0)').empty();
			$.each(data.productList, function(index, items){
			$('<option/>',{
				align : 'center',
				value : items.productID,
				text : items.productID
			}).appendTo($('#productid'));
			});
		$('#productid').val($('input[name=productid]').val());
		if($("#productid option:selected").val()!=''){
			var getImgOn='<img style="height:100%;width:90px;" src="/minishop/storage/showProduct.do?product_name_image='+$("#productid option:selected").val()+'">';
			$('#imgDiv').html(getImgOn);}
		}
	});

});


//2.리뷰 작성 이벤트
$('#reviewWriteBtn').click(function(){
	$('#missing').empty();
	if($('#review_subject').val()=='') $('#missing').append(notitleMsg).alert();
	else if($("#productid option:selected").val()=='') $('#missing').append(noProductMsg).alert();
	else if(CKEDITOR.instances.review_content.getData()=='') $('#missing').append(noContentMsg).alert();
	else if($('#review_pwd').val()=='') $('#missing').append(noPwdMsg).alert();
	else if($('#review_pwd').val()!=$('#review_repwd').val()) $('#missing').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/review/reviewWrite.do',
			data : {'review_subject':$('#review_subject').val(),
					'user_id' : $('#user_id').val(),
					'review_content': CKEDITOR.instances.review_content.getData(),
					'productid': $('#productid option:selected').val(),
					'review_pwd':$('#review_pwd').val()},
			success : function(){
				alert('성공적으로 리뷰가 접수되었습니다! 리뷰 게시판으로 이동합니다.');
				window.location='/minishop/board/review/reviewList.do';}
			
		});
	}
});