/**
 * 사용자 화면 js
 */

$(document).ready(function(){
	if(window.innerWidth < 992){
		$('#menuDiv').hide();
		//$('#titleDiv').show();	
	}
	else if(window.innerWidth>991){
		$('#menuDiv').show();	
		//$('#titleDiv').hide();		
	}			

});//ready

$(window).on('resize', function(){
	if(window.innerWidth < 992){
			$('#menuDiv').hide();
			//$('#titleDiv').show();	
	}
	else if(window.innerWidth>991){
		$('#menuDiv').show();	
		//$('#titleDiv').hide();
	}
});	

$(document).on('scroll', function(){
	if ( $( this ).scrollTop() > 0) {
		$( '.top' ).show();
	} else {
		$( '.top' ).hide();
	}
	
});	

$("textarea.autosize").on('keydown keyup', function () {
	  $(this).height(1).height( $(this).prop('scrollHeight')+12 );	
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

$('#goCart').click(function(){
	window.location='/minishop/trading/userCart.do';
});


