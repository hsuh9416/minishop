$(document).ready(function(){
	var pg = $('#pg',opener.document).val();
	var searchOption = $('#searchOption',opener.document).val();
	var keyword = $('#keyword',opener.document).val();
	$.ajax({
		type : 'post',
		url : '/minishop/admin/shop/getChartRawData.do',
		data: {'pg':pg, 'searchOption':searchOption,'keyword':keyword},
		dataType: 'json',
		success: function(data){
			alert('done!');
		}
	});
});