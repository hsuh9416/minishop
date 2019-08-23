<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<!--CSS Local LINK:START-->    
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" href="/minishop/resources/custom/css/adminProduct.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">	 
	<!--CSS Local LINK:END-->
	
<div class="container">  	

	<div class="form-row">	
		<div class="col" align="center">
 			<h2 class="first">[개별주문서조회]</h2>		
 		</div>
	</div>

	<input type="hidden" id="order_no" value="${order_no}">
	<input type="hidden" id="new_order_state" value="${new_order_state}">
	
	<div class="card card-body">		
	
		<div class="form-row justify-content-center">	
			<div class="form-group col-8"></div>
			<div class="form-group col-4">
					<i id="iconGeneral" class="fas fa-dolly-flatbed">등록코드</i> : 
			</div>
		</div>	
	
		<div class="form-row justify-content-center">
			<div class="form-group col-6"></div>
			<div class="form-group col-6">      	
				<button type="button" id="resetMod" class="btn btn-outline-secondary">다시 작성</button>								
				<button type="button" id="doModify" class="btn btn-outline-info">재고 반영</button>			
			</div>
		</div>		
	

		<div class="form-row justify-content-center">
			<div class="form-group col-10" id="modifyResult"></div>		
		</div>	
		
	</div>
</div>		
							

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminOrder/personalOrderView.js"></script>
	<!--JavaScript Local LINK:END-->