/**
 * 관리자 재고 관련 자바스크립트 모음
 */


/* inventoryModify */

$('#numberUpDown').change(function(e){
		e.preventDefault();	
		var stat = $('#numberUpDown').val();
		var num = parseInt(stat,10);
		if(num<=beforeVal*(-1)){
			alert('더이상 줄일수 없습니다.');
			num = beforeVal*(-1);
			}
		afterVal = beforeVal + num;
		$('#stock').val(afterVal);	
});//재고 변동

$('#costUpDown').change(function(e){
		e.preventDefault();	
		var stat = $('#costUpDown').val();
		var num = parseInt(stat,10);
		if(num<=beforeCost*(-1)){
			alert('더이상 줄일수 없습니다.');
			num = beforeCost*(-1);
			}
		afterCost = beforeCost + num;
		$('#unitcost').val(afterCost);	
});//가격 변동	

$('#resetMod').click(function(){
	window.location.reload();
});

$('#doModify').click(function(){
	var result = confirm('최종 반영하시겠습니까?');
	if(result){
		$.ajax({
			type : 'post',
			url : '/minishop/admin/product/doModify.do',
			data : {'productID':$('#productID').val(),
					'product_name_no' :$('#product_name_no').val(),
					'stock':$('#stock').val(), 
				   'unitcost':$('#unitcost').val()},
			success : function(){
				opener.location.reload();
				window.close();
			}			
		});
	}
	
});	