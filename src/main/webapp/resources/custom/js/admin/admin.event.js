$(document).ready(function(){
	if($('#event_no').val()!=''||$('#event_no').val()!=null){
		$('#bannerNum').trigger('change','trigger');	
	}
});

$('#bannerNum').on('change',function(){
	$.ajax({
		type: 'get',
		url: '/minishop/admin/shop/getSelectedBanner.do',
		data: 'event_no='+$('#bannerNum :selected').val(),
		dataType: 'json',
		success: function(data){
			$('input[name=event_name]').val(data.banner.event_name);
			$('input[name=event_image]').val(data.banner.event_image);
			$('input[name=event_url]').val(data.banner.event_url);
			$('input[name=start_date]').val(data.banner.event_startdate);			
			$('input[name=end_date]').val(data.banner.event_enddate);	
	
			$('#event_no').val($('#bannerNum :selected').val());
		}
	});
});

$('#resetForm').click(function(){
	window.location='/minishop/admin/shop/eventManage.do?event_no='+$('#event_no').val();
});

$('#changeBannerBtn').click(function(){
	var today = new Date();
	var startdate = Date.parse($('input[name=start_date]').val());
	var enddate = Date.parse($('input[name=end_date]').val());
	var startdiff = startdate - today;
	var enddiff =enddate - today;
	var perioddiff = enddate - startdate;
	if($('input[name=event_name]').val()==''|| $('input[name=event_url]').val()==''||
	 $('input[name=start_date]').val()==''||$('input[name=end_date]').val()=='') alert('필요한 정보를 전부 입력해주세요');
	else if(startdiff<0 || enddiff<0|| perioddiff<0) alert('유효한 기간을 선택해주세요');
	else{
		$('#bannerManage').submit();
	}
});
