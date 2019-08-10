//0. 전체, 검색 리스트 공통으로 사용되는 함수(호출한 data를 뷰에 뿌리기)
function getReviewList(data){	
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

		for(i=0; i<items.review_lev; i++){
			$('.'+items.review_seq).before('&emsp;');
		}
		if(items.review_pseq!=0){
			$('.'+items.review_seq).before($('<i/>',{
				class : 'fab fa-replyd'
			}));
		}				
	});
	
	$('#boardPagingDiv').html(data.boardPaging.pagingHTML);
	
	$('#reviewTable').on('click','#subjectA',function(){
			var review_seq = $(this).parent().prev().text();
			window.location='/minishop/board/review/reviewView.do?review_seq='+review_seq+'&pg='+$('#pg').val();
	});
}

//1. 최초 시작시에 목록 호출
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/board/review/getReviewList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			getReviewList(data);
		}
	});
});

//2. 검색어를 통한 검색 목록을 호출
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
				getReviewList(data);				
				}
		});
	}
});

//3. 페이징
function boardPaging(pg){
	location.href='/minishop/board/review/reviewList.do?pg='+pg;
}

function boardSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#reviewSearchBtn').trigger('click','trigger');
}
