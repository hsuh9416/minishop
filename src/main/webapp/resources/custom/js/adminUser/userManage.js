$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/admin/user/getMemberList.do',
		dataType: 'json',
		success: function(data){
			$('#memberListForm').empty();
			
			$.each(data.memberList,function(index,items){
				var rank ='<font style="color:green;">일반</font>';
				var state ='<font>정상</font>';
				var button = '<input type="button" name="memberDetailBtn" class="btn btn-outline-info" value="조회"/>'
				if(items.state=='0'){rank='<font>관리자</font>';}
				else if(items.state=='2'){rank='<font style="color:blue;">특별</font>';}
				else if(items.state=='3'){state='<font style="color:red;">삭제요청</font>';}
				
				$('<div/>',{
					class: 'form-row align-items-center'
				}).append($('<div/>',{
					class: 'form-group col-1',
					align : 'center',
					text : items.seq
				})).append($('<div/>',{
					class: 'form-group col-2',
					align : 'center',
					text : items.name
				})).append($('<div/>',{
					class: 'form-group col-2',
					align : 'center',
					text : items.id
				})).append($('<div/>',{
					class: 'form-group col-1',
					align : 'center',
					html : rank
				})).append($('<div/>',{
					class: 'form-group col-2',
					align : 'center',
					text: items.registerdate
				})).append($('<div/>',{
					class: 'form-group col-2',
					align : 'center',
					html: state
				})).append($('<div/>',{
					class: 'form-group col-2',
					align : 'center',
					html: button
				})).appendTo($('#memberListForm'));			
			});
			$('input[name=memberDetailBtn]').on('click',function(){
				var targetId = $(this).parent().prev().prev().prev().prev().text();
				var viewPop = window.open('/minishop/admin/user/memberDetailView.do?id='+targetId,'회원조회','width=820,height=530,resizable=no');
			});
		}
	});
});

$('#giveBenefitAll').click(function(){
	var benefitGivePop = window.open('/minishop/admin/user/benefitGivingForm.do?target=all','회원 혜택 관리','width=565,height=530,resizable=no');
});

$('#giveNoticeAll').click(function(){
	var infoWritePop = window.open('/minishop/admin/user/infoWriteForm.do?target=all','회원 공지 발신','width=565,height=530,resizable=no');
});
