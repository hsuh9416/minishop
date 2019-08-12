function getInventory(data){
	$('#inventoryTable tr:gt(0)').empty();
	
	$.each(data.inventoryList, function(index, items){
		var instockdate = '[미정]';
		var stock ='[재고 없음]';
		if(items.product_name_instockdate!=null){
			instockdate = items.product_name_instockdate;}
		if(items.stock>0){
			stock = items.stock+'';}			
		$('<tr/>').append($('<th/>',{
			scope : 'row',
			align : 'center',
			text : items.product_name_no
		})).append($('<td/>',{
			}).append($('<a/>',{
				href : 'javascript:void(0)',
				id : 'subjectA',
				text : items.productID,
				class : items.product_name_no+''
		}))).append($('<td/>',{
			align : 'center',
			text : items.unitcost
		})).append($('<td/>',{
			align : 'center',
			text : stock				
		})).append($('<td/>',{
			align : 'center',
			text : items.productName					
		})).append($('<td/>',{
			align : 'center',
			html : instockdate				
		})).appendTo($('#inventoryTable tbody'));			
	});		
	
	$('#productPagingDiv').html(data.productPaging.pagingHTML);
	
	$('#inventoryTable').on('click','#subjectA',function(){
			var productID = $(this).text();
			var loginPop = window.open('/minishop/admin/product/inventoryModify.do?productID='+productID+'&pg='+$('#pg').val(),'재고수정','width=565,height=435,resizable=no');

	});
}

$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/admin/product/inventoryList.do',
		data : 'pg='+$('#pg').val(),
		dataType : 'json',
		success : function(data){
			getInventory(data);
		}
	});
});

function productPaging(pg){
	location.href='/minishop/admin/product/inventoryManage.do?pg='+pg;
}

function productSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#inventorySearchBtn').trigger('click','trigger');}

$('#inventorySearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else{
		$.ajax({
			type : 'post',
			url : '/minishop/admin/product/inventorySearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption option:selected').val(),
				   'keyword':$('#keyword').val()},
			dataType : 'json',
			success : function(data){
				getInventory(data);
			}
		});
	}
});

$('#reloadIcon').click(function(){
	window.location.reload();
});
