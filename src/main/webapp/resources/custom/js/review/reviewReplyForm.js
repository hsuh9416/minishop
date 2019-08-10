//1. 최초 실행시 원글의 상품 그대로 인계하여 셋팅됨
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

//2.후기 답글 업로드 이벤트
$('#replyWriteBtn').click(function(){
	$('#missing').empty();
	if($('#review_subject').val()=='') $('#missing').append(notitleMsg).alert();
	else if($("#productid option:selected").val()=='') $('#missing').append(noProductMsg).alert();
	else if(CKEDITOR.instances.review_content.getData()=='') $('#missing').append(noContentMsg).alert();
	else if($('#review_pwd').val()=='') $('#missing').append(noPwdMsg).alert();
	else if($('#review_pwd').val()!=$('#review_repwd').val()) $('#missing').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/review/reviewReply.do',
			data : {'review_subject':$('#review_subject').val(),
					'review_pseq' : $('#review_pseq').val(),
					'review_content': CKEDITOR.instances.review_content.getData(),
					'review_pwd':$('#review_pwd').val()},
			success : function(){
				alert('답글이 성공적으로 작성 되었습니다! 리뷰 게시판으로 이동합니다.');
				window.location='/minishop/board/review/reviewList.do';}
			
		});
	}
});