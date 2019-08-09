/**
 * 리뷰게시판 관련 자바스트립트
 */
 
//0.공통 변수
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

var noProductMsg = '<div class="alert alert-warning alert-dismissible fade show" role="alert">상품선택은 필수사항입니다'+
'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
'<span aria-hidden="true">&times;</span></button></div>';


$('#productid').change(function(){
	$('#imgDiv').empty();
	if($("#productid option:selected").val()=='')
		 var getImgOn = '<div class="alert alert-warning alert-dismissible fade show" style="height: 100%; width:100%;" role="alert">선택<br>필수'
		 +'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
		 '<span aria-hidden="true">&times;</span></button></div>';
	else
		var getImgOn='<img style="height:100%;width:90px;" src="/minishop/storage/showProduct.do?product_name_image='+$("#productid option:selected").val()+'">';
	$('#imgDiv').html(getImgOn);
});//선택지가 바뀔 때 마다

$('#reviewReset').click(function(){
	window.location.reload();
});

$('#reviewReturn').click(function(){
	window.history.back();
});

$('#goReviewWrite').click(function(){
	window.location='/minishop/board/review/reviewWriteForm.do';
});

//1.리뷰 작성
$('#goReviewWrite').click(function(){
	window.location='/minishop/board/review/reviewWriteForm.do';
});

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


//2.리뷰 목록
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
					$.each(data.reviewList, function(index, items){
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
							src : '/minishop/storage/showProduct.do?product_name_image='+items.productid,
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
						for(i=0; i<items.review_lev; i++){
							$('.'+items.review_seq).before('&emsp;');
						}
						if(items.review_pseq!=0){
							$('.'+items.review_seq).before($('<i/>',{
								class : 'fab fa-replyd'
							}));
						}				
					});//each
					
					$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
					
					$('#reviewTable').on('click','#subjectA',function(){
						var review_seq = $(this).parent().prev().text();
						window.location='/minishop/board/review/reviewView.do?review_seq='+review_seq+'&pg='+$('#pg').val();
					});//제목 클릭시!
				}//success
		});//에이작스	
	}
});

//3.리뷰 보기 화면
$('#reviewModifyFormBtn').click(function(){
	$('#alertText').val('수정하실 글의 비밀번호를 입력하세요').css('color','black').css('font-size','15px');	
	$('#pwdConfirm').show();
	$('#purpose').val('modify');

});

$('#reviewDeleteBtn').click(function(){
	$('#alertText').val('삭제하실 글의 비밀번호를 입력하세요').css('color','black').css('font-size','15px');	
	$('#pwdConfirm').show();
	$('#purpose').val('delete');
});

$('#checkReviewPwd').click(function(){
	$('#alertText').val('');
	if($('#rePwd').val()==''){
		$('#alertText').val('비밀번호는 필수입력입니다').css('color','red').css('font-size','15px');		
	}
	else if($('#rePwd').val()==$('#review_pwd').val()){
		if($('#purpose').val()=='modify'){
			window.location='/minishop/board/review/reviewModifyForm.do?review_seq='+$('#review_seq').val()+'&pg='+$('#pg').val();		
		}
		else if($('#purpose').val()=='delete'){
			var realDelete = confirm('정말 삭제하시겠습니까?');
			if(realDelete){
				$.ajax({
					type : 'get',
					url : '/minishop/board/review/reviewDelete.do',
					data : 'review_seq='+$('#review_seq').val(),
					success: function(){
						window.location='/minishop/board/review/reviewDeleted.jsp';
					}
				});
			}
		}
	}
	else{
		$('#alertText').val('비밀번호를 다시 입력하세요').css('color','red').css('font-size','15px');			
	}
});

$('#reviewReplyFormBtn').click(function(){
	window.location='/minishop/board/review/reviewReplyForm.do?review_pseq='+$('#review_pseq').val()+'&productid='+$('#productid').val()+'&pg='+$('#pg').val();	
});

//4.리뷰 수정
$('#reviewModBtn').click(function(){
	$('#missingMod').empty();
	if($('#review_subject').val()=='') $('#missingMod').append(notitleMsg).alert();
	else if($("#productid option:selected").val()=='') $('#missingMod').append(noProductMsg).alert();
	else if($('#review_content').val()=='') $('#missingMod').append(noContentMsg).alert();
	else if($('#review_pwd').val()=='') $('#missingMod').append(emptyPwdMsg).alert();
	else if($('#review_pwd').val()!=$('#review_repwd').val()) $('#missingMod').append(missMatchingPwdMsg).alert();
	else{	
		$.ajax({
			type : 'post',
			url : '/minishop/board/review/reviewModify.do',
			data : {'review_seq':$('#review_seq').val(),
					'review_subject':$('#review_subject').val(),
					'review_content': CKEDITOR.instances.review_content.getData(),
					'productid': $('#productid option:selected').val(),
					'review_pwd':$('#review_pwd').val()},
			success : function(){
				alert('리뷰 게시글이 수정되었습니다.');
				window.location='/minishop/board/review/reviewView.do?review_seq='+$('#review_seq').val()+'&pg='+$('#pg').val();}
		});
	}
});


//5.리뷰 답글
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

