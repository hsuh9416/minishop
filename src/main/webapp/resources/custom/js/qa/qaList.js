//0. 전체, 검색 리스트 공통으로 사용되는 함수(호출한 data를 뷰에 뿌리기)
function getQaList(data){	
	$('#qaTable tr:gt(0)').empty();					
	$.each(data.qalist, function(index, items){
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
		if ($(this).parent().text()=='[비밀글로 작성된 문의글입니다]' && $(this).parent().next().text()!=$('#userName').val())
		{alert('해당 게시물은 작성자와 관리자만 접근할 수 있습니다. 만약 작성자라면 로그인 후에 시도해주세요.');}
		else{
			var qa_seq = $(this).parent().prev().text();
			window.location='/minishop/board/qa/qaView.do?qa_seq='+qa_seq+'&pg='+$('#pg').val();
		}
	});	
}

//1. 최초 시작시에 목록 호출
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/board/qa/getQaList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			getQaList(data);
		}
	});
});

//2. 검색어를 통한 검색 목록을 호출
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
				getQaList(data);
			}//success
		});//에이작스	
	}
});


//3. 페이징
function boardPaging(pg){
	location.href='/minishop/board/qa/qaList.do?pg='+pg;}

function boardSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#qaSearchBtn').trigger('click','trigger');}

