//1. 최초 시작시에 상품 목록 불러오기(이름순)
$(document).ready(function(){
		$.ajax({
			type: 'get',
			url:'/minishop/product/getSpecialProductList.do',
			data:'condition='+$('#condition').val(),
			dataType: 'json',
			success: function(data){
				$('#mainList').empty();
				$.each(data.productList, function(index, items){
					$('#mainList').append(items.productListHTML);
				});
						
			}
		});		
});

//2. 검색어를 통해 검색한 상품 목록 불러오기
$('#searchBtn').click(function(){
	var searchWord = $('#searchWord').val();
	if(searchWord=='') alert('검색어를 한 글자 이상 입력해주세요');
	else {
		$.ajax({
			type: 'get',
			url:'/minishop/product/getSpecialProductList.do',
			data: {'product_category_name' : $('#product_category_name').val(),
					'order' : $('#order').val(),
					'searchOption' : $('input:radio[name=searchOption]:checked').val(),				
					'searchWord' : searchWord},
			dataType: 'json',
			success: function(data){
				$('#mainList').empty();
				$.each(data.productList, function(index, items){
					$('#mainList').append(items.productListHTML);
				});
			}
		});			
	}
});
