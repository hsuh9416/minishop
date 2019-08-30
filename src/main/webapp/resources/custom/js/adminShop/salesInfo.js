var today = new Date().getFullYear()+'.'+(new Date().getMonth()+1)+'.'+new Date().getDate();
//1-1. 3자리마다 콤마
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

function formatDate(date){
	if(date!=null&&date!='') return date.split('-').join('.');
	else return '[미정]';
}
//1-2. 전체, 검색 리스트 공통으로 사용되는 함수(호출한 data를 뷰에 뿌리기)
function getsalesInfoList(data){	
	$('#salesInfoTable tr:gt(0)').empty();	
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
	$('<tr/>').append($('<td/>',{
		align: 'center',
		colspan: '3',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">[총 합계]</font>'			
	})).append($('<td/>',{
		align: 'center',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(sales_total)+'</font>'		
	})).append($('<td/>',{
		align: 'center',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(cash_total)+'</font>'				
	})).append($('<td/>',{
		align: 'center',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(card_total)+'</font>'					
	})).append($('<td/>',{
		align: 'center',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(point_total)+'</font>'					
	})).append($('<td/>',{
		align: 'center',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(coupon_total)+'</font>'				
	})).append($('<td/>',{
		align: 'center',
		html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(etc_total)+'</font>'		
	})).append($('<td/>',{
		align: 'center',
		html: '('+today+' 현재)'			
	})).appendTo($('#salesInfoTable tbody'));
		
	$('#salesInfoPagingDiv').html(data.salesInfoPaging.pagingHTML);
	
}
function viewUnlimitedData(){
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
	
}
function viewlimitedData(){
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
//1. 최초 시작시에 목록 호출
$(document).ready(function(){
	viewUnlimitedData();
	$.ajax({
		type: 'post',
		url : '/minishop/admin/shop/getChartRawData.do',
		data:{'searchOption':$('#searchOption').val(),
			   'keyword':$('#keyword').val()},
		dataType: 'json',
		success: function(data){
			$('#totalSalesTable tr:gt(0)').empty();
			var totalSalesList = data.salesRawInfoList;
			var order_total = 0;
			var sales_total = 0;
			var cash_total = 0; 		
			var card_total = 0; 		
			var point_total = 0; 		
			var coupon_total = 0; 	
			var etc_total = 0;
			$.each(totalSalesList,function(idx,data){
					var cash_subtotal = 0; 		
					var card_subtotal = 0; 		
					var point_subtotal = 0; 		
					var coupon_subtotal = 0; 	
					var etc_subtotal = 0;
				$.each(items.sales_payment_Info, function(subIdx, subData){
					if(subData.payment_method=='1') card_subtotal = parseInt(subData.payment_amount,10); 
					else if(subData.payment_method=='2') cash_subtotal = parseInt(subData.payment_amount,10); 
					else if(subData.payment_method=='4') point_subtotal = parseInt(subData.payment_amount,10); 
					else if(subData.payment_method=='5') coupon_subtotal = parseInt(subData.payment_amount,10);
					else etc_subtotal = parseInt(subData.payment_amount,10);
				});
				
				order_total++;
				sales_total += parseInt(data.sales_revenue,10);
				cash_total += cash_subtotal; 		
				card_total += card_subtotal; 		
				point_total += point_subtotal; 		
				coupon_total += coupon_subtotal; 	
				etc_total += etc_subtotal;
			});
			
			$('<tr>').append($('<td>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(order_total)+'</font>'				
			})).append($('<td/>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(sales_total)+'</font>'		
			})).append($('<td/>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(cash_total)+'</font>'				
			})).append($('<td/>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(card_total)+'</font>'					
			})).append($('<td/>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(point_total)+'</font>'					
			})).append($('<td/>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(coupon_total)+'</font>'				
			})).append($('<td/>',{
				align: 'center',
				html: '<font style="text-decoration:undeline;text-underline-position:under;text-weight:bold;">'+formatNumber(etc_total)+'</font>'		
			})).appendTo($('#totalSalesTable tbody'));
		}
	});
});

//2. 검색어를 통한 검색 목록을 호출
$('#salesInfoSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	if($('#keyword').val()=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else{
		viewlimitedData();
	}
});

$('#sortSubject').on('change',function(){
	if($('#keyword').val()!='') viewlimitedData();
	else viewUnlimitedData();
});

$('#sortType').on('change',function(){
	if($('#keyword').val()!='') viewlimitedData();
	else viewUnlimitedData();
});

//3. 페이징
function salesInfoPaging(pg){
	location.href='/minishop/admin/shop/salesInfoList.do?pg='+pg;}

function salesInfoSearchPaging(pg){
	$('input[name=pg]').val(pg);
	$('#salesInfoSearchBtn').trigger('click','trigger');}


$('#openChart').click(function(){
	var chartPop = window.open('/minishop/admin/shop/salesChart.do','[Chart View]','width=565,height=530,resizable=no');
});

$('#reloadIcon').click(function(){
	window.location.reload();
});