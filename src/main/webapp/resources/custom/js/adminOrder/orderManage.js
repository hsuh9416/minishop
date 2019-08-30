//0. 메소드

//0.1 StringBuilder 선언
function StringBuilder(value){
	this.strings = new Array();
	this.Append(value);
}

//0.2 StringBuilder append
StringBuilder.prototype.Append = function(value){
	if(value){
		this.strings.push(value);
	}
}

//0.3 StringBuilder clear
StringBuilder.prototype.Clear = function(){
	this.strings.length = 0;
}
//0.4 StringBuilder toString
StringBuilder.prototype.ToString = function(){
	return this.strings.join("");
}

//0.5 3자리마다 콤마 찍는 정규식
function formatNumber(num){
	return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g,'$1,');}

//0.6 공백 제거 정규식
function removeSpace(text){
	return text.replace(/(\s*)/g,'');
}

//0.7 (정규식)date형식은 yyyy-(m)m-(d)d로 설정해야 함
function checkDateFormat(text){
	var pattern = /^\d{4}-\d{2}-\d{2}$/;
	var check = pattern.test(text);
	return check;
}

function checkNumber(num){
	var pattern = /^[0-9]+$/;
	var check = pattern.test(num);
	return check;	
}
function getOrderList(data){
	
	$('#orderListForm').empty();
	
	if(data.userOrderList.length==0||data.userOrderList==null){
		$('#orderListForm').append('<h5 style="color:red;font-weight:bold;text-decoration:underline;>주문 내역이 존재하지 않습니다</h5>');}
	else{
		$.each(data.userOrderList,function(index,items){
			var total = 0;
			var str = new StringBuilder();
			
				str.Append('<a class="productViewA" href="#" onclick="viewPopUp('+items.order_no+','+items.order_state+')">');
			$.each(items.orderList, function(idx,i){
				if(idx=='0') str.Append('['+i.productID+'] 포함<br>총 '+items.orderList.length+' 건</a>');
				total = total + i.unitcost*i.cart_qty;
			});
			var productList = str.ToString();
			
			$('<div/>',{
				class: 'form-row justify-content-center'					
			}).append($('<div/>',{
				class: 'col-1',
				align: 'center',
				text: items.order_no
			})).append($('<div/>',{
				class: 'col-2',
				align: 'center',
				text: items.order_date						
			})).append($('<div/>',{
				class: 'col-2',
				align: 'center',
				text: items.order_id						
			})).append($('<div/>',{
				class: 'col-2',
				align: 'center',
				text: items.order_name						
			})).append($('<div/>',{
				class: 'col-3',
				align: 'center',
				html: productList					
			})).append($('<div/>',{
				class: 'col-2',	
				align: 'center'				
			}).append($('<select/>',{
				id : 'state'+items.order_no,
				name : 'state',
				class: 'form-control-plaintext inline-form'
			}).append($('<option/>',{
				value : '0',
				text: '0:주문완료'
			})).append($('<option/>',{
				value : '1',
				text: '1:입금확인'
			})).append($('<option/>',{
				value : '2',
				text: '2:배송준비'
			})).append($('<option/>',{
				value : '3',
				text: '3:상품발송'					
			})).append($('<option/>',{
				value : '4',
				text: '4:환불요청확인'						
			})).append($('<option/>',{
				value : '5',
				text: '5:배송완료확인'						
			})).append($('<option/>',{
				value : '6',
				text: '6:환불완료'						
			})).append($('<option/>',{
				value : '7',
				text: '7:거래완료'						
			})).append($('<option/>',{
				value : '8',
				text: '8:주문취소'						
			})))).appendTo($('#orderListForm'));
			$('#state'+items.order_no).val(items.order_state);
			if(items.order_state==0) {//주문완료후에는 입금완료,배송준비중,배송중을 반드시 거쳐가야 함
				$('#state'+items.order_no+' option[value=4]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=5]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=6]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=7]').prop('disabled',true);
			}
			else{//한번 진행된 거래는 주문완료로 복귀하지 않음
				$('#state'+items.order_no+' option[value=0]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=8]').prop('disabled',true);		
			}
			if(items.order_state==6||items.order_state==7||items.order_state==8) {//돌이킬 수 없는 거래 상태
				$('#state'+items.order_no+' option').prop('disabled',true);
				$('#state'+items.order_no).prop('disabled',true);
			}
			else if(items.order_state==1||items.order_state==2){//입금 완료 후나 배송 준비중에 각종 완료행위로 이동할 수 없다.
				$('#state'+items.order_no+' option[value=5]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=6]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=7]').prop('disabled',true);				
			}
			else if(items.order_state==3||items.order_state==5){//배송중,배송완료에는 환불요청/거래완료만 가능
				if(items.order_state==5) $('#state'+items.order_no+' option[value=3]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=1]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=2]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=6]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=7]').prop('disabled',true);
			}
			else if(items.order_state==4){//환불중에는 환불만 가능
				$('#state'+items.order_no+' option[value=1]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=2]').prop('disabled',true);	
				$('#state'+items.order_no+' option[value=3]').prop('disabled',true);
				$('#state'+items.order_no+' option[value=5]').prop('disabled',true);	
				$('#state'+items.order_no+' option[value=7]').prop('disabled',true);	
			}
			
			$('#state'+items.order_no).on('change',function(){
				var order_state = $(this).val();
				var order_no = $(this).parent().prev().prev().prev().prev().prev().text();
				if(order_state!=items.order_state){
						viewPopUp(order_no,order_state);
				}
				else viewPopUp(order_no,items.order_state);
			});	
		});
		
		$('#orderPaingDiv').html(data.orderPaging.pagingHTML);
		

	}
}

function orderPaging(pg){
	location.href='/minishop/admin/order/orderManage.do?pg='+pg;
}

function orderSearchPaging(pg){

	$('input[name=pg]').val(pg);
	$('#orderSearchBtn').trigger('click','trigger');
}

function viewPopUp(order_no,order_state){
	var popup = window.open('/minishop/admin/order/personalOrderView.do?order_no='+order_no+'&order_state='+order_state,'개별주문서조회','width=785,height=1570,resizable=no');
}

//1. 최초 시작시 주문 목록 호출하기
$(document).ready(function(){
	$.ajax({
		type: 'post',
		url : '/minishop/admin/order/getUserOrderList.do',
		data: 'pg='+$('#pg').val(),
		dataType: 'json',
		success: function(data){
			getOrderList(data);
		}
			
	});
});

//2.검색옵션 변경시 추가 설명 팝업 발생
$('#searchOption').on('change',function(){
	var select = $('#searchOption option:selected').val();
	if(select=='order_no') alert('(주)주문번호는 숫자로만 입력해주세요.\n ex) 30번 주문서 ->"30" 입력');
	else if(select=='order_date') alert('(주)검색 날짜는 [4자리 연도-2자리 월-2자리 일]형식으로 입력해주십시오\n ex)2019년 7월 1일 -> "2019-07-01" 입력');
	else if(select=='order_state') alert('(주)거래상태는 번호로 조회해주세요.\n'+
										'[0]주문완료 [1]입금확인 [2]배송준비 [3]상품발송\n'+
										'[4]환불요청확인 [5]배송완료확인 [6]환불완료 [7]거래완료 [8]주문취소\n'+
										'ex) "입금확인" -> "1" 입력');
});

//3. 검색어를 통한 주문 목록 호출하기
$('#orderSearchBtn').click(function(event,str){
	if(str!='trigger') $('[input=pg]').val(1);
	var keyword = removeSpace($('#keyword').val());
	if(keyword=='') 
		alert('검색을 원하는 단어를 입력하세요!');
	else if($('#searchOption option:selected').val()=='order_no'&&keyword!=''&&checkNumber(keyword)==false){
		alert('(주)주문번호는 숫자로만 입력해주세요.\n ex) 30번 주문서 ->"30" 입력');
	}
	else if($('#searchOption option:selected').val()=='order_date'&&keyword!=''&&checkDateFormat(keyword)==false){
		alert('(주)검색 날짜는 [4자리 연도-2자리 월-2자리 일]형식으로 입력해주십시오\n ex)2019년 7월 1일 -> "2019-07-01" 입력');
	}
	else if($('#searchOption option:selected').val()=='order_state'&&keyword!=''&&checkNumber(keyword)==false){
		alert('(주)거래상태는 번호로 조회해주세요.\n'+
				'[0]주문완료 [1]입금확인 [2]배송준비 [3]상품발송\n'+
				'[4]환불요청확인 [5]배송완료확인 [6]환불완료 [7]거래완료 [8]주문취소\n'+
				'ex) 입금확인 -> "1" 입력');
	}
	else
		$.ajax({
			type : 'post',
			url : '/minishop/admin/order/userSearchOrderList.do',
			data : {'pg':$('input[name=pg]').val(), 
				   'searchOption':$('#searchOption option:selected').val(),
				   'keyword':keyword},
			dataType : 'json',
			success : function(data){
				getOrderList(data);
			}
		});
});

//3. 돌아가기
$('#returnBtn').click(function(){
	window.history.back();
});