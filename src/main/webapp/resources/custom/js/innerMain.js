/**
 * 사용자 화면 js
 */
$(document).ready(function(){
	if(window.innerWidth < 992){
		$('#menuDiv').hide();
		$('#titleDiv').show();	
	}
	else if(window.innerWidth>991){
		$('#menuDiv').show();	
		$('#titleDiv').hide();		
	}			

	//목록 불러오기(신상품순서)
	$.ajax({
		type: 'get',
		url:'/minishop/product/getAllproduct.do',
		dataType: 'json',
		success: function(data){
			$('#mainList').empty();
			$.each(data.productList, function(index, items){
				$('#mainList').append(items.productListHTML);
			});//each
					
		}//success
	});
	
});//ready

$(window).on('resize', function(){
	if(window.innerWidth < 992){
			$('#menuDiv').hide();
			$('#titleDiv').show();	
	}
	else if(window.innerWidth>991){
		$('#menuDiv').show();	
		$('#titleDiv').hide();		
	}
});	

$(document).on('scroll', function(){
	if ( $( this ).scrollTop() > 0) {
		$( '.top' ).show();
	} else {
		$( '.top' ).hide();
	}
	
});	

$( '.top' ).click( function() {
	$( 'html, body' ).animate( { scrollTop : 0 }, 400 );
	return false;
} );

$('#goLogin').click(function(){
	var loginPop = window.open('/minishop/member/loginPopup.do','Login','width=450,height=350,resizable=no');
});

$('#goLogout').click(function(){
	 $.ajax({
		 type : 'get',
		 url : '/minishop/member/logout.do',
		 success : function(){	
				alert('로그아웃 하셨습니다');
				window.location='/minishop/main/home.do';
			}
	 });
});

$('#goSignUp').click(function(){
	window.location='/minishop/member/writeForm.do'
});

$('#mgoLogin').click(function(){
	window.location='/minishop/member/loginForm.do'
});

$('#mgoLogout').click(function(){
	window.location.reload();
	 $.ajax({
		 type : 'get',
		 url : '/minishop/member/logout.do',
		 success : function(){	
				alert('로그아웃 하셨습니다');
				window.location='/minishop/main/home.do';
			}
	 });
});

$('#mgoSignUp').click(function(){
	window.location='/minishop/member/writeForm.do'
});

/* 1대1 문의*/
$('#personalQA').click(function(){
	$('#PQAModal').modal();
});

$('#QAreturnAddr').focusout(function(){
	 if($('#QAreturnAddr').val()!=''){ 
		 $('#QAreturnAddr').tooltip('disable');
	 } 
	 else{
		 $('#QAreturnAddr').tooltip('enable');
	 }
});

$('#QAdetail').focusout(function(){
	 if($('#QAdetail').val()!=''){ 
		 $('#QAdetail').tooltip('disable');
	 } 
	 else{
		 $('#QAdetail').tooltip('enable');
	 }
});

$('#sendQABtn').click(function(){
	 $('#QAreturnAddr').tooltip('disable');
	 $('#QAdetail').tooltip('disable');	
	 if($('#QAreturnAddr').val()==''){ 
		 $('#QAreturnAddr').tooltip('enable');
		 $('#QAreturnAddr').tooltip('show');
		 $('#QAreturnAddr').focus();}
	 else if($('#QAdetail').val()=='') {
		 $('#QAdetail').tooltip('enable');
		 $('#QAdetail').tooltip('show');
		 $('#QAdetail').focus();}
	 else {
		 $.ajax({
			 type : 'POST',
			 url : '/minishop/member/memberQASend.do',
			 data : {'subject': $('#QAtype').val() ,'content' : $('#QAdetail').val(), 'sendAddr' : $('#QAreturnAddr').val()},
			 
			 success : function(){
				 alert('문의가 성공적으로 접수되었습니다. 문의사항은 기재하신 메일주소로 시일 내에 답변해드리겠습니다. 감사합니다.');
				 window.location='/minishop/member/memberView.do';
			 }
			 
		 });
	 }
});

$('#memberDelete').click(function(){
	$('#deleteModal').modal();	
});

$('#deleteBtn').click(function(){
	//alert($('#deletePwd').val());
	 $.ajax({
		 type : 'post',
		 url : '/minishop/member/delete.do',
		 data : 'pwd='+$('#deletePwd').val(),
		 dataType : 'text',
		 success : function(data){	
			 if(data=='success'){
				alert('정상적으로 삭제되었습니다. 탈퇴 후의 개인정보 관리 및 자세한 사항은 메일을 통해 확인 바랍니다.');
				window.location='/minishop/main/home.do';
			 }
			 else{
				alert('비밀번호가 일치하지 않습니다. 다시한번 확인 부탁드립니다.');
				$('#deletePwd').val('');
			 }
			}
	 });	
});

$('#goCart').click(function(){
	//alert($('#goCart span').text());
	if($('#goCart span').text()=='0') alert('장바구니의 아이템이 담겨 있지 않습니다!');
	else alert('장바구니는 공사중!');
});