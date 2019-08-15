//1. 최초 시작시 목록 불러오기
$(document).ready(function(){

	$.ajax({
		type: 'get',
		url : '/minishop/trading/getBannerList.do',
		dataType: 'json',
		success: function(data){
			$('#bannerDiv').empty();
			$.each(data.bannerList, function(index, items){
				$('#bannerDiv').append(items.event_banner);
			});
		}
	});
	
	$.ajax({
		type: 'get',
		url:'/minishop/product/getUserProductList.do',
		dataType: 'json',
		success: function(data){
			$('#mainList').empty();
			$.each(data.productList, function(index, items){
				$('#mainList').append(items.productListHTML);
			});
					
		}
	});
	

});