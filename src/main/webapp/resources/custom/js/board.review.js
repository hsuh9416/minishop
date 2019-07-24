/**
 * 리뷰게시판 관련 자바스트립트
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

var noProductMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">리뷰할 상품을 선택하세요'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';


$('#productid').change(function(){
	$('#imgDiv').empty();
	if($("#productid option:selected").val()=='')
		 var getImgOn = '<div class="alert alert-warning alert-dismissible fade show" style="height: 100%; width:70%;" role="alert">리뷰할 상품을<br> 선택해주세요.'
		 +'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
		 '<span aria-hidden="true">&times;</span></button></div>';
	else
		var getImgOn='<img style="height:100%;width:90px;" src="/minishop/storage/'+$("#productid option:selected").val()+'.jpg">';
	$('#imgDiv').html(getImgOn);
});//선택지가 바뀔 때 마다

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
					'review_pwd':$('#review_pwd').val(),					
					'review_state':$('#review_state').val()},
			success : function(){
				alert('성공적으로 문의가 접수되었습니다! 문의 게시판으로 이동합니다.');
				window.location='/minishop/board/review/reviewList.do';}
			
		});
	}
});

$('#reviewReset').click(function(){
	window.location.reload();
});

$('#reviewReturn').click(function(){
	window.history.back();
});

function boardPaging(pg){
	location.href='/minishop/board/review/reviewList.do?pg='+pg;
}

function boardSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#reviewSearchBtn').trigger('click','trigger');
}

$('#reviewSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else{
		$.ajax({
			type : 'post',
			url : '/minishop/board/review/reviewSearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption').val(),
				   'keyword':$('#keyword').val()},
			dataType : 'json',
			success : function(data){
				
					$('#reviewTable tr:gt(0)').empty();
					$.each(data.reviewSearchList, function(index, items){
						$('<tr/>').append($('<th/>',{
							scope : 'row',
							align : 'center',
							text : items.review_seq
						})).append($('<td/>',{
							}).append($('<a/>',{
								href : 'javascript:void(0)',
								id : 'subjectA',
								text : items.review_subject,
								class : items.review_seq+''
						}))).append($('<td/>',{
							align : 'center',
							text : items.name,
						})).append($('<td/>',{
							align : 'center',
							text : '['+items.productid+']'				
						})).append($('<td/>',{
							align : 'center'			
						}).append($('<img/>',{
							src : '/minishop/storage/'+items.productid+'.jpg',
							width :'100',
							height : '100'
						}))).append($('<td/>',{
							align : 'center',
							text : items.review_hit					
						})).append($('<td/>',{
							align : 'center',
							text : items.review_logtime			
						})).appendTo($('#reviewTable tbody'));
						//답글
						if(items.review_pseq!=0){
							$('.'+items.review_pseq).before($('<img/>',{
								src : '../image/reply.gif'
							}));
						}				
					});//each
					
					$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
					
					$('#reviewTable').on('click','#subjectA',function(){
							var review_seq = $(this).parent().prev().text();
							alert(review_seq);
					});//제목 클릭시!
				}//success
		});//에이작스	
	}
});

$('#reviewModifyBtn').click(function(){
	window.location='/minishop/board/review/reviewModifyForm.do?review_seq='+$('#review_seq').val()+'&pg='+$('#pg').val();
});

$('#reviewDeleteBtn').click(function(){
	
});

$('#reviewReplyBtn').click(function(){
	
});

$('#reviewModifyBtn').click(function(){
	$('#missingMod').empty();
	if($('#review_subject').val()=='') $('#missingMod').append(notitleMsg).alert();
	else if($("#productid option:selected").val()=='') $('#missingMod').append(noProductMsg).alert();
	else if($('#review_content').val()=='') $('#missingMod').append(noContentMsg).alert();
	else if($('#review_pwd').val()=='') $('#missingMod').append(emptyPwdMsg).alert();
	else if($('#review_pwd').val()!=$('#review_check').val()) $('#missingMod').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/review/reviewModify.do',
			data : {'review_seq':$('#review_seq').val(),
					'review_subject':$('#review_subject').val(),
					'review_content':$('#review_content').val(),
					'productid': $('#productid option:selected').val(),
					'review_pwd':$('#review_pwd').val(),					
					'review_state':$('#review_state').val()},
			success : function(){
				alert('문의 게시글이 수정되었습니다.');
				window.location='/minishop/board/review/reviewView.do?review_seq='+$('#review_seq').val()+'&pg='+$('#pg').val();}
		});
	}
});
$('#goReviewWrite').click(function(){
	window.location='/minishop/board/review/reviewWriteForm.do';
});
