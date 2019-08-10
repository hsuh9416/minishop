//0. 초기화값(공개글=0)
var qa_state ='0';

//1.최초 시작시에 정보값을 불러오고 설정하는 이벤트 발생
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
			if($('#productid').val()!=''){
				var getImgOn='<img style="height:100%;width:90px;" src="/minishop/storage/showProduct.do?product_name_image='+$('#productid').val()+'">';				
			}
			$('#imgDiv').html(getImgOn);
		}
	});
	
	qa_state = $('#qa_state').val();
});

//2. 수정버튼을 클릭할 때 발생하는 이벤트
$('#qaModifyBtn').click(function(){
	$('#missingMod').empty();
	if($('#qa_subject').val()==''){ 
		$('#missingMod').append(notitleMsg).alert();
	}else if($('#qa_content').val()==''){
		$('#missingMod').append(noContentMsg).alert();
	}else if($('#qa_pwd').val()==''){
		$('#missingMod').append(emptyPwdMsg).alert();
	}else if($('#qa_pwd').val()!=$('#qa_check').val()){
		$('#missingMod').append(missMatchingPwdMsg).alert();
	}else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/qa/qaModify.do',
			data : {'qa_seq':$('#qa_seq').val(),
					'qa_subject':$('#qa_subject').val(),
					'qa_content':$('#qa_content').val(),
					'productid': $('#productid option:selected').val(),
					'qa_pwd': $('#qa_pwd').val(),					
					'qa_state': qa_state},
			success : function(){
				alert('문의 게시글이 수정되었습니다.');
				window.location='/minishop/board/qa/qaView.do?qa_seq='+$('#qa_seq').val()+'&pg='+$('#pg').val();}
		});
	}
});

//3.자물쇠 버튼의 동적 변화를 구현하는 이벤트
$('#changeSecret button').on('click',function(){
	if(qa_state=='0'){
		$('#changeSecret i').removeClass('fas fa-lock-open').addClass('fas fa-lock');	
		qa_state='1';
	}
	else if(qa_state=='1'){
		$('#changeSecret i').removeClass('fas fa-lock').addClass('fas fa-lock-open');		
		qa_state='0';		
	}
});