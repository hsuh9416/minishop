function searchId(target){
	$.ajax({
		type: 'get',
		url : '/minishop/admin/user/getMemberDetail.do',
		data: 'id='+target,
		dataType: 'json',
		success : function(data){
			if(data.memberInfo.memberDTO==null){
				$('#targetInfo').text('존재하지 않는 아이디입니다. 다시 한번 검색해주세요').attr('color','red').attr('font-weight','bold');
				$('#benefit_target').prop('readonly',false);					
				$('#searchTarget').prop('disabled',false);	
				$('#benefit_target').val('');
				$('input[name=id]').val('');
			}
			else{
				$('#targetInfo').text('['+data.memberInfo.memberDTO.name+'] 회원님께 지급').attr('color','blue').attr('font-weight','bold');
				$('input[name=id]').val(target);}
		}
	});	
}

function resetItem(){
	$('#targetInfo').empty();
	$('#couponState').empty();
	$('#coupon_no option:eq(0)').attr('selected','selected');
	$('#pointQty option:eq(0)').attr('selected','selected');
	$('input[name=coupon_no]').val('');	
}

$(document).ready(function(){
	var target = $('#target').val();
	$('#couponForm').hide();
	$('#pointForm').hide();
	if(target=='all'){
		$('input[name=selectTarget][value=all]').prop('checked',true);	
		$('#targetInfo').text('[전체] 회원 대상으로 지급').attr('color','blue').attr('font-weight','bold');
	}else{
		$('input[name=selectTarget][value=person]').prop('checked',true);
		$('#benefit_target').val(target);
			searchId(target);

	}
	
});

$('input[name=selectTarget]').on('change',function(){
	resetItem();
	var select = $('input[name=selectTarget]:checked').val();	
	if(select=='person'){
		$('#benefit_target').prop('readonly',false);					
		$('#searchTarget').prop('disabled',false);
		if($('input[name=id]').val()==''){
			$('#targetInfo').text('공지 메일을 발송할 회원 아이디를 입력하세요').attr('color','red').attr('font-weight','bold');
			$('#benefit_target').val('');			
		}
		else{
			searchId($('#target').val());
		}

	}
	
	else{
		$('#targetInfo').text('[전체] 회원님께 공지 메일을 보냅니다').attr('color','blue').attr('font-weight','bold');
		$('#benefit_target').prop('readonly',true);					
		$('#searchTarget').prop('disabled',true);	
	}
});

$('#searchTarget').click(function(){
	if($('#benefit_target').val()=='') alert('검색하실 아이디를 입력해주세요');
	else{
		searchId($('#benefit_target').val());
	}
});

$('input[name=selectBenefit]').on('change',function(){
	resetItem();
	var select = $('input[name=selectBenefit]:checked').val();
	if(select=='pointSelect'){
		$('#couponForm').hide();
		$('#pointForm').show();
		$('#couponIssue').prop('disabled',true);
		$('#pointGrant').prop('disabled',false);
	}else{
		$('#pointForm').hide();
		$('#couponForm').show();
		$('#pointGrant').prop('disabled',true);
		$('#couponIssue').prop('disabled',false);
		$.ajax({
			type: 'get',
			url: '/minishop/admin/shop/getEventList.do',
			dataType: 'json',
			success: function(data){
				$('#coupon_no').empty();					
				$('<option/>',{
					align : 'center',
					value : '',
					text : '[지급 쿠폰 선택]'
				}).appendTo($('#coupon_no'));
				$.each(data.couponBook,function(index,items){
					var target = '[전체]';
					var type='[배송비 (변동)]';
					var mount = ' 변동]';
					if(items.coupon_target=='1') {
						target='[개별]'; 
					}
					if(items.coupon_type=='0') {
						type ='[정액 ('+items.discount_mount+'원)]';
					}
					else if(items.coupon_type=='1') {

						type='[정률('+items.discount_mount+'%)]'; 						
					}
					$('<option/>',{
						align : 'center',
						value : items.coupon_no,
						text : '['+items.coupon_no+'] ['+items.coupon_name+'] '+target+' '+type
					}).appendTo($('#coupon_no'));
				});
			}
		});
	}
});

$('#benefit_target').focusout(function(){
	searchId($('#benefit_target').val());
});

//쿠폰 발급 여부를 확인하는 메소드
$('#coupon_no').on('change',function(){
	resetItem();
	if($('#coupon_no').val()=='') $('#couponState').text('쿠폰을 선택하여 주시기 바랍니다').css('color','darkgrey').css('text-weight','bold');
	else if($('#coupon_no').val()!=''&& ($('input[name=selectTarget]:checked').val()=='person' && $('input[name=id]').val()=='')){
		$('#couponState').text('지급 대상을 선택해 주세요').css('color','red').css('text-weight','bold');
	}
	else {
		$.ajax({
			type : 'post',
			url : '/minishop/admin/user/checkCouponState.do',
			data:{
				'coupon_no':$('#coupon_no').val(),
				'selectTarget':$('input[name=selectTarget]:checked').val(),
				'id':$('input[name=id]').val(),
			},
			dataType: 'text',
			success : function(data){
				if(data=='typeMissMatching'){
					$('#couponState').text('쿠폰의 타입이 맞지 않습니다').css('color','darkgrey').css('text-weight','bold');
					$('input[name=coupon_no]').val('');
				}
				else if(data=='preIssued'){
					$('#couponState').text('이미 발급받은 쿠폰입니다').css('color','red').css('text-weight','bold');
					$('input[name=coupon_no]').val('');
				}
				else{
					$('#couponState').text('발급 가능한 쿠폰입니다').css('color','darkgreen').css('text-weight','bold');
					$('input[name=coupon_no]').val($('#coupon_no').val());
				}
			}
		});
	}
});

$('#pointQty').on('change',function(){
	resetItem();
	if($('#pointQty').val()!=''){
		$('input[name=pointQty]').val($('#pointQty').val());
		$('input[name=pointQty]').prop('readonly',true);
	}
	else {
		$('input[name=pointQty]').val('');
		$('input[name=pointQty]').prop('readonly',false);		
	}
});

$('#resetBtn').click(function(){
	window.location.reload();
});

$('#closeBtn').click(function(){
	window.close();
});

$('input[name=coupon_duedate]').on('change',function(){
	var select = $('input[name=coupon_duedate]:checked').val();	
	$('#coupon_duedate').val('');
	if(select=='periodic'){
		$('#coupon_duedate').prop('readonly',false);
	}
	else{
		$('#coupon_duedate').prop('readonly',true);
	}
});
$('#couponIssue').click(function(){
	var duedate = 'permanent';
	if($('input[name=coupon_duedate]:checked').val()!='permanent'){
		duedate = $('#coupon_duedate').val();	
	}
	if($('input[name=selectTarget]:checked').val()=='person' && $('input[name=id]').val()=='') alert('쿠폰을 지급할 대상을 지정하세요');
	else if($('#coupon_no').val()!=$('input[name=coupon_no]').val()) alert('지급대상 쿠폰의 유효성을 체크하세요');	
	else if($('#subject').val()==''||CKEDITOR.instances.editor_admin.getData()=='') alert('발신 메일의 제목과 내용을 입력하셔야 합니다.');
	else if($('input[name=coupon_duedate]:checked').val()=='periodic'&& $('input[name=coupon_duedate]').val()=='') alert('쿠폰의 유효기간을 설정하세요');
	else{
		
		$('input[name=content]').val(CKEDITOR.instances.editor_admin.getData());
		$.ajax({
			type: 'post',
			url : '/minishop/admin/user/issueCoupon.do',
			data :{'subject':$('#subject').val(),
					'content':$('input[name=content]').val(),
					'selectTarget':$('input[name=selectTarget]:checked').val(),
					'id':$('input[name=id]').val(),
					'coupon_duedate' : duedate,
					'coupon_no':$('input[name=coupon_no]').val()},
			dataType: 'text',
			success: function(data){
				if(data=='dueDateError'){
					alert('유효하지 않은 날짜를 입력하셨습니다. 다시 한번 시도해주세요.');
				}
				else if(data=='success'){
					alert('쿠폰 발급과 메일 전송이 성공적으로 이루어졌습니다.');
					window.close();					
				}
				else if(data=='adminExcept'){
					alert('관리자는 지급 대상이 아닙니다. (구)관리자 계정인 경우에는 담당자 계정을 먼저 수정해 주세요.');
					window.close();
				}	
				else if(data=='invalidUserExcept'){
					alert('삭제요청중인 회원은 지급대상이 아닙니다. 계정 복구의 경우에는 회원 정보에서 먼저 수정해 주세요');
					window.close();
				}					
				else{
					alert('쿠폰 발급 또는 메일 전송에 실패하였습니다. 다시 시도해주세요.');
					window.location.reload();
				}				
			}
		});
	}
});

$('#pointGrant').click(function(){
	if($('input[name=selectBenefit]:checked').val()=='pointSelect' && $('input[name=pointQty]').val()=='') alert('포인트를 기입하세요');
	else {
		var pointNum = parseInt($('input[name=pointQty]').val(),10);
		
		if($('input[name=selectTarget]:checked').val()=='person' && $('input[name=id]').val()=='') alert('포인트를 지급할 대상을 지정하세요');
		else if($('#subject').val()==''||CKEDITOR.instances.editor_admin.getData()=='') alert('발신 메일의 제목과 내용을 입력하셔야 합니다.');
		else if(pointNum<0) alert('포인트는 0점 이상 정수로만 입력 가능합니다.');
		else{
			$('input[name=content]').val(CKEDITOR.instances.editor_admin.getData());
			$.ajax({
				type: 'post',
				url : '/minishop/admin/user/grantPoint.do',
				data :{'subject':$('#subject').val(),
						'content':$('input[name=content]').val(),
						'selectTarget':$('input[name=selectTarget]:checked').val(),
						'id':$('input[name=id]').val(),
						'pointQty':pointNum},
				dataType: 'text',
				success: function(data){
					if(data=='success'){
						alert('포인트 지급과 메일 전송이 성공적으로 완료되었습니다.');
						window.close();
					}
					else if(data=='adminExcept'){
						alert('관리자는 지급 대상이 아닙니다. (구)관리자 계정인 경우에는 담당자 계정을 먼저 수정해 주세요.');
						window.close();
					}	
					else if(data=='invalidUserExcept'){
						alert('삭제요청중인 회원은 지급대상이 아닙니다. 계정 복구의 경우에는 회원 정보에서 먼저 수정해 주세요');
						window.close();
					}							
					else{
						alert('포인트 지급 또는 메일 전송에 실패하였습니다. 다시 시도해주세요.');
						window.location.reload();
					}				
				}
			});		
		}
	}
});