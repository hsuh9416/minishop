$(document).ready(function(){
	$.ajax({
		type: 'get',
		url : '/minishop/admin/user/getPersonalQAList.do',
		dataType: 'json',
		success: function(data){
			if(data.personalQAList==null||data.personalQAList.length==0){
				$('<div/>',{
					class: 'form-row justify-content-center'
				}).append($('<div/>',{
					class: 'col admin-col',
					align : 'center',
					html : '<font style="color:red;">[현재 답변하지 않은 1:1문의가 없습니다]</font>'	
				})).appendTo($('#personalQAListForm'));		
			}
			else{
				$.each(data.personalQAList,function(index,items){
					$('<div/>',{
						class: 'form-row justify-content-center'
					}).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						text : items.seq	
					})).append($('<div/>',{
						class: 'col-2',
						align : 'center'	
					}).append($('<a/>',{
						href : 'javascript:void(0)',
						text : items.subject,
						id: 'subjectA'
					}))).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						text : items.sender	
					})).append($('<div/>',{
						class: 'col-3',
						align : 'center',
						text : items.sendAddr	
					})).append($('<div/>',{
						class: 'col-2',
						align : 'center',
						text : items.senddate	
					})).appendTo($('#personalQAListForm'));						
				});
				
				$('#personalQAListForm').on('click','#subjectA',function(){
					var seq = $(this).parent().prev().text();
					var personalQA = window.open('/minishop/admin/user/personalQAFormAdmin.do?seq='+seq,'개별1:1문의보기','width=565,height=530,resizable=no');
				});	
			}
		}
	});
});