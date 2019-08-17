//0. 공통 변수 설정
var beforeVal = parseInt($('input[name=stock]').val(),10);
var afterVal = parseInt($('input[name=stock]').val(),10);
var beforeCost = parseInt($('input[name=unitcost]').val(),10);
var afterCost = parseInt($('input[name=unitcost]').val(),10);	

//1. 최초 셋팅
$(function(){
	$('#stock').val(afterVal);	
	$('#unitcost').val(afterCost);			
});

//2. 한도 이상으로 바꾸는 경우 경고 및 가격 조정
$('#numberUpDown').change(function(e){
		e.preventDefault();	
		var stat = $('#numberUpDown').val();
		var num = parseInt(stat,10);
		if(num<=beforeVal*(-1)){
			alert('더이상 줄일수 없습니다.');
			num = beforeVal*(-1);
			$('#numberUpDown').val(num);
			}
		afterVal = beforeVal + num;
		$('#stock').val(afterVal);	
});

$('#costUpDown').change(function(e){
		e.preventDefault();	
		var stat = $('#costUpDown').val();
		var num = parseInt(stat,10);
		if(num<=beforeCost*(-1)){
			alert('더이상 줄일수 없습니다.');
			num = beforeCost*(-1);
			$('#numberUpDown').val(num);
			}
		afterCost = beforeCost + num;
		$('#unitcost').val(afterCost);	
});

//3. 초기화
$('#resetMod').click(function(){
	window.location.reload();
});

//4. 재고 반영
$('#doModify').click(function(){
	var result = confirm('최종 반영하시겠습니까?');
	if(result){
		$.ajax({
			type : 'post',
			url : '/minishop/admin/product/doModify.do',
			data : {'productID':$('#productID').val(),
					'product_name_no' :$('#product_name_no').val(),
					'stock':$('#stock').val(), 
				   'unitcost':$('#unitcost').val(),
				   'ordernum':$('input[name=ordernum] :checked').val()},
			success : function(){
				opener.location.reload();
				window.close();
			}			
		});
	}
	
});	