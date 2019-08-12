function getProductList(data){
	$('#productTable tr:gt(0)').empty();
	
	$.each(data.productList, function(index, items){
		var discountable = '[할인불가상품]';
		var isOnMall = '[미등재]';
		if(items.promotioncode==1){
			discountable = '[할인가능상품]';}
		if(items.product_onstore=='YES'){
			isOnMall = '[등재]';}	
		$('<tr/>').append($('<a/>',{
			align: 'center',
			id : 'checkA',
			}).prepend($('<i/>',{
				text : items.product_name_no,
				id : 'deleteA',
				class: 'fas fa-trash-alt'
		}))).append($('<td/>',{
			align: 'center'
		}).append($('<img/>',{
			src: '/minishop/storage/showProduct.do?product_name_image='+items.product_name_image,
			width: '100',
			height: '100',
			id : 'imageA'
		}))).append($('<td/>',{
			align : 'center',
			html : items.productID				
		})).append($('<td/>',{
			align : 'center',
			html : items.product_category_name				
		})).append($('<td/>',{
			align : 'center',
			html : items.productName				
		})).append($('<td/>',{
			align : 'center',
			html : discountable				
		})).append($('<td/>',{
			align : 'center',
			html : isOnMall,
			id : 'onMall'					
		})).appendTo($('#productTable tbody'));
	});
	
	$('#productPagingDiv').html(data.productPaging.pagingHTML);
	
	$('#productTable').on('click','#imageA',function(){
			var product_name_no = $(this).parent().prev().text();
			window.location='/minishop/admin/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
	});
	

	$('#productTable').on('click','#checkA',function(){
		var onShop = $(this).next().next().next().next().next().next().text();
		if(onShop=='[등재]') alert('현재 매장에 등록되어 있는 상품은 삭제하실 수 없습니다. 등록 해제를 하신 후 재시도해주세요.');
		else {
			var result =confirm('해당 상품을 정말로 삭제하시겠습니까? 삭제시에는 재고관리 목록에서도 제거됩니다.');
			if(result){
				window.location='/minishop/admin/product/productDelete.do?product_name_no='+$(this).text();					
			}

		}

	});
}

$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/admin/product/productList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			getProductList(data);			
		}
	});
});

function productPaging(pg){
	location.href='/minishop/admin/product/productManage.do?pg='+pg;
}

function productSearchPaging(pg){

	$('input[name=pg]').val(pg);
	$('#productSearchBtn').trigger('click','trigger');
}

$('#productSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else
		$.ajax({
			type : 'post',
			url : '/minishop/admin/product/productSearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption option:selected').val(),
				   'keyword':$('#keyword').val()},
			dataType : 'json',
			success : function(data){
				getProductList(data);
			}
		});
});