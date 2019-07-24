/**
 * 질의 게시판 관련 자바 스크립트
 */
 var notitleMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">제목을 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var noContentMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">내용을 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var noPwdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">비밀번호를 설정해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var emptyPwdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">비밀번호를 입력해주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';

var missMatchingPwdMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">비밀번호를 다시 확인해 주세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';


var state = '비밀글';
var subject = '[비밀글로 작성된 문의글입니다]';
var isreplied ='미답변';

$('#isSecret').click(function(){
	//alert($('#qa_state').val());
	if($('#qa_state').val()=='0'){
		$('#isSecret i').removeClass('fas fa-lock');
		$('#isSecret i').addClass('fas fa-lock-open');	
		$('#qa_state').val('1');
	}
	else if($('#qa_state').val()=='1'){
		$('#isSecret i').removeClass('fas fa-lock-open');	
		$('#isSecret i').addClass('fas fa-lock');	
		$('#qa_state').val('0');		
	}
});

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
					'qa_state':$('#qa_state').val()},
			success : function(){
				alert('성공적으로 문의가 접수되었습니다! 문의 게시판으로 이동합니다.');
				window.location='/minishop/board/qa/qaList.do';}
			
		});
	}
});

$('#qaReset').click(function(){
	window.location.reload();
});

$('#qaReturn').click(function(){
	window.history.back();
});


function boardPaging(pg){
	location.href='/minishop/board/qa/qaList.do?pg='+pg;
}

function boardSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#qaSearchBtn').trigger('click','trigger');
}

$('#qaSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else{
		$.ajax({
			type : 'post',
			url : '/minishop/board/qa/qaSearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption').val(),
				   'keyword':$('#keyword').val()},
			dataType : 'json',
			success : function(data){
				
					$('#qaTable tr:gt(0)').empty();
					$.each(data.qaSearchList, function(index, items){
						var state = '<i class="fas fa-lock"></i>';
						var subject = '[비밀글로 작성된 문의글입니다]';
						var isreplied ='<i class="far fa-square"></i>';
						var productid ='[비공개]';
						if (items.qa_state=='0'){//공개글
							subject = items.qa_subject;
							state = '<a><i class="fas fa-lock-open"></i></a>';
							if(items.productid!=null){
								productid ='['+items.productid+']';}	
							else if(items.productid==null){productid = '[문의 상품 없음]';}
						}
						if(items.qa_reply=='1'){
							isreplied ='<a><i class="far fa-check-square"></i></a>';
						}
						
						$('<tr/>').append($('<th/>',{
							scope : 'row',
							align : 'center',
							text : items.qa_seq
						})).append($('<td/>',{
							}).append($('<a/>',{
								href : 'javascript:void(0)',
								id : 'subjectA',
								text : subject,
								class : items.qa_seq+''
						}))).append($('<td/>',{
							align : 'center',
							text : items.name,
						})).append($('<td/>',{
							align : 'center',
							text : productid				
						})).append($('<td/>',{
							align : 'center',
							text : items.qa_logtime					
						})).append($('<td/>',{
							align : 'center',
							html : state			
						})).append($('<td/>',{
							align : 'center',
							html : isreplied					
						})).appendTo($('#qaTable tbody'));
					});//each
					
				$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
				
				$('#qaTable').on('click','#subjectA',function(){
					if ($(this).parent().text()=='[비밀글로 작성된 문의글입니다]' && $(this).parent().next().text()!='${memberDTO.name}')
					{alert('해당 게시물은 작성자와 관리자만 접근할 수 있습니다. 만약 작성자라면 로그인 후에 시도해주세요.');}
					else{
						var qa_seq = $(this).parent().prev().text();
						window.location='/minishop/board/qa/qaView.do?qa_seq='+qa_seq+'&pg='+$('input[name=pg]').val();
					}//else
				});//제목 클릭시!
			}//success
		});//에이작스	
	}
});

$('#qaModifyBtn').click(function(){
	window.location='/minishop/board/qa/qaModifyForm.do?qa_seq='+$('#qa_seq').val()+'&pg='+$('#pg').val();
});

$('#qaDeleteBtn').click(function(){
	
});

$('#qaModifyBtn').click(function(){
	$('#missingMod').empty();
	if($('#qa_subject').val()=='') $('#missingMod').append(notitleMsg).alert();
	else if($('#qa_content').val()=='') $('#missingMod').append(noContentMsg).alert();
	else if($('#qa_pwd').val()=='') $('#missingMod').append(emptyPwdMsg).alert();
	else if($('#qa_pwd').val()!=$('#qa_check').val()) $('#missingMod').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/qa/qaModify.do',
			data : {'qa_seq':$('#qa_seq').val(),
					'qa_subject':$('#qa_subject').val(),
					'qa_content':$('#qa_content').val(),
					'productid': $('#productid option:selected').val(),
					'qa_pwd':$('#qa_pwd').val(),					
					'qa_state':$('#qa_state').val()},
			success : function(){
				alert('문의 게시글이 수정되었습니다.');
				window.location='/minishop/board/qa/qaView.do?qa_seq='+$('#qa_seq').val()+'&pg='+$('#pg').val();}
		});
	}
});


