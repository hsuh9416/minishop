<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">	 
	<!--CSS Local LINK:END-->
	
<div class="container">  	

	<div class="form-row">	
		<div class="col" align="center">
 			<h2 class="first">[주문 환불 요청]</h2>		
 		</div>
	</div>

	<input type="hidden" id="order_no" value="${order_no}">	
	<div class="card card-body">		
	
		<h5 class="sub-title-undeline">[환불 주문번호]</h5>
		<div class="form-row justify-content-center">	
			<div class="form-group col-4"></div>
			<div class="form-group col-4">${order_no}</div>
			<div class="form-group col-4"></div>
		</div>	
		
		<h5 class="sub-title-undeline">[환불 계좌번호]</h5>
		<div class="form-row ">	
			<div class="col-4" align="center">
				<select id="bankOption" class="form-control-plaintext form-inline">		
					<option value="">[은행 선택]</option>					
					<option value="woori">우리은행</option>
					<option value="hana">하나은행</option>
					<option value="kookmin">국민은행</option>
					<option value="shinhan">신한은행</option>
				</select>	
			</div>	
			<div class="col-8" align="center">
				<input type="text" class ="form-control-plaintext form-inline" id="refund_Num" placeholder="환불계좌번호 입력"/>
			</div>		  		  				  	
		</div>	   
		<h5 class="sub-title-undeline">[환불 사유]</h5>
		<div class="form-row">	
			<div class="col" align="center">
				<input type="text" class ="form-control-plaintext form-inline" id="order_statement" placeholder="환불사유를 입력해주세요."/>
			</div>				  		  				  	
		</div>	
	
		<div class="form-row justify-content-center">
			<div class="form-group col-6"></div>
			<div class="form-group col-6">      	
				<button type="button" id="resetBtn" class="btn btn-outline-secondary">다시작성</button>								
				<button type="button" id="doRefund" class="btn btn-outline-info">환불요청</button>			
			</div>
		</div>		
			
	</div>
</div>		
							

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/trading/refundForm.js"></script>
	<!--JavaScript Local LINK:END-->