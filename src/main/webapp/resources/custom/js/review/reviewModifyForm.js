//1. 최초 시작시 셋팅
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
				var getImgOn='<img style="height:80px;weight:60px;" src="/minishop/storage/showProduct.do?product_name_image='+$("#productid option:selected").val()+'">';
				$('#imgDiv').html(getImgOn);
			}
		}
	});
});

//2.리뷰수정 이벤트
$('#reviewModBtn').click(function(){
	$('#missingMod').empty();
	if($('#review_subject').val()=='') $('#missingMod').append(notitleMsg).alert();
	else if($("#productid option:selected").val()=='') $('#missingMod').append(noProductMsg).alert();
	else if($('#review_content').val()=='') $('#missingMod').append(noContentMsg).alert();
	else if($('#review_pwd').val()=='') $('#missingMod').append(emptyPwdMsg).alert();
	else if($('#review_pwd').val()!=$('#qa_check').val()) $('#missingMod').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/review/reviewModify.do',
			data : {'review_seq':$('#review_seq').val(),
					'review_subject':$('#review_subject').val(),
					'review_content': CKEDITOR.instances.review_content.getData(),
					'productid': $('#productid option:selected').val()},
			success : function(){
				alert('리뷰 게시글이 수정되었습니다.');
				window.location='/minishop/board/review/reviewView.do?review_seq='+$('#review_seq').val()+'&pg='+$('#pg').val();}
		});
	}
});
