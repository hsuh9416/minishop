//1-1. 3자리마다 콤마
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

//1-2. 전체, 검색 리스트 공통으로 사용되는 함수(호출한 data를 뷰에 뿌리기)
function getsalesInfoList(data){	
	$('#salesInfoTable tr:gt(0)').empty();	
	$('#sales_total').empty();	
	$('#cash_total').empty();	
	$('#card_total').empty();	
	$('#point_total').empty();	
	$('#coupon_total').empty();	
	$('#etc_total').empty();	
	var sales_total = 0;
	var cash_total = 0; 		
	var card_total = 0; 		
	var point_total = 0; 		
	var coupon_total = 0; 	
	var etc_total = 0;
	$.each(data.salesInfoList, function(index, items){
		var cash_subtotal = 0; 		
		var card_subtotal = 0; 		
		var point_subtotal = 0; 		
		var coupon_subtotal = 0; 	
		var etc_subtotal = 0;
		
		$.each(items.sales_payment_Info, function(idx, item){
			if(item.payment_method=='1') card_subtotal = parseInt(item.payment_amount,10); 
			else if(item.payment_method=='2') cash_subtotal = parseInt(item.payment_amount,10); 
			else if(item.payment_method=='4') point_subtotal = parseInt(item.payment_amount,10); 
			else if(item.payment_method=='5') coupon_subtotal = parseInt(item.payment_amount,10);
			else etc_subtotal = parseInt(item.payment_amount,10);
		});

		$('<tr/>').append($('<td/>',{
			align: 'center',
			text: items.sales_seq
		})).append($('<td/>',{
			align: 'center',
			text: items.order_no			
		})).append($('<td/>',{
			align: 'center',
			text: items.order_id			
		})).append($('<td/>',{
			align: 'center',
			text: formatNumber(items.sales_revenue)			
		})).append($('<td/>',{
			align: 'center',
			text: formatNumber(cash_subtotal)			
		})).append($('<td/>',{
			align: 'center',
			text: formatNumber(card_subtotal)				
		})).append($('<td/>',{
			align: 'center',
			text: formatNumber(point_subtotal)				
		})).append($('<td/>',{
			align: 'center',
			text: formatNumber(coupon_subtotal)			
		})).append($('<td/>',{
			align: 'center',
			text: formatNumber(etc_subtotal)	
		})).append($('<td/>',{
			align: 'center',
			text: items.sales_date			
		})).appendTo($('#salesInfoTable tbody'));
		
		sales_total += parseInt(items.sales_revenue,10);
		cash_total += cash_subtotal; 		
		card_total += card_subtotal; 		
		point_total += point_subtotal; 		
		coupon_total += coupon_subtotal; 	
		etc_total += etc_subtotal;
	});
		
	$('#sales_total').append(formatNumber(sales_total));
	$('#cash_total').append(formatNumber(cash_total));
	$('#card_total').append(formatNumber(card_total));	
	$('#point_total').append(formatNumber(point_total));
	$('#coupon_total').append(formatNumber(coupon_total));	
	$('#etc_total').append(formatNumber(etc_total));	
	
	$('#salesInfoPagingDiv').html(data.salesInfoPaging.pagingHTML);
	
}

//1. 최초 시작시에 목록 호출
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/minishop/admin/shop/getsalesInfoList.do',
		data : {
				'pg' : $('#pg').val(),
			    'sortSubject':$('#sortSubject').val(),
			    'sortType':$('#sortType').val()
				},
		dataType : 'json',
		success : function(data){
			getsalesInfoList(data);
		}
	});
});

//2. 검색어를 통한 검색 목록을 호출
$('#salesInfoSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else{
		$.ajax({
			type : 'post',
			url : '/minishop/admin/shop/salesInfoSearch.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption').val(),
				   'keyword':$('#keyword').val(),
				   'sortSubject':$('#sortSubject').val(),
				   'sortType':$('#sortType').val()
				   },
			dataType : 'json',
			success : function(data){
				getsalesInfoList(data);
			}
		});
	}
});


//3. 페이징
function salesInfoPaging(pg){
	location.href='/minishop/admin/shop/salesInfoList.do?pg='+pg;}

function salesInfoSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#salesInfoSearchBtn').trigger('click','trigger');}


function openChart(){
	window.open("","[Chart View]");
	var salesInfoSearch = document.salesInfoSearch;
	salesInfoSearch.target = "[Chart View]";
	salesInfoSearch.action = "/minishop/admin/shop/salesChart.do";
	salesInfoSearch.submit();
}