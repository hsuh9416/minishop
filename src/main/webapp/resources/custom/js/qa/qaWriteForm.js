//0.초기값 설정(공개글=0)
var qa_state='0';

//1.시작시에 문의 목록을 불러오기
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
			
		}
	});
	$('#productid').val($('input[name=productid]').val());
	if($('#productid option:selected').val()!=''){
		var getImgOn='<img style="height:100%;width:90px;" src="/minishop/storage/showProduct.do?product_name_image='+$("#productid option:selected").val()+'">';
		$('#imgDiv').html(getImgOn);}
	
});

//2. 문의 작성 버튼을 클릭한 후의 이벤트
$('#qaWriteBtn').click(function(){
	$('#missing').empty();
	if($('#qa_subject').val()=='') $('#missing').append(notitleMsg).alert();
	else if($('#qa_content').val()=='') $('#missing').append(noContentMsg).alert();
	else if($('#qa_pwd').val()=='') $('#missing').append(noPwdMsg).alert();
	else if($('#qa_pwd').val()!=$('#qa_repwd').val()) $('#missing').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/qa/qaWrite.do',
			data : {'qa_subject':$('#qa_subject').val(),
					'user_id' : $('#user_id').val(),
					'name' :$('#name').val(),
					'qa_content':$('#qa_content').val(),
					'productid': $('#productid option:selected').val(),
					'qa_pwd':$('#qa_pwd').val(),					
					'qa_state': qa_state},
			success : function(){
				alert('성공적으로 문의가 접수되었습니다! 문의 게시판으로 이동합니다.');
				window.location='/minishop/board/qa/qaList.do';}
			
		});
	}
});

//3. 공개,비공개 전환 이벤트
$('#makeSecret button').on('click',function(){
	if(qa_state=='0'){
		$('#makeSecret i').removeClass('fas fa-lock-open').addClass('fas fa-lock');	
		qa_state='1';
	}
	else if(qa_state=='1'){
		$('#makeSecret i').removeClass('fas fa-lock').addClass('fas fa-lock-open');		
		qa_state='0';	
	}
});