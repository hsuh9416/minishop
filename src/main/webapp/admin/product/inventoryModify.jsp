<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
 	<link rel="stylesheet" href="/minishop/resources/custom/css/adminProduct.css">


<div id="invMod" class="container">
   <input type="hidden" id="productID" value="${productDTO.productID}">
   <input type="hidden" id="product_name_no" value="${productDTO.product_name_no}">   
   <input type="hidden" id="pg " value="${pg}">
		<div class="form-row">	
			<div class="form-group col-12">
				<h4 align="center">[상품 수량 및 단가 변경]</h4>
			</div>		

		</div>
		<div class="form-row">	
			<div class="form-group col-12" align="right">
				<i id="iconGeneral" class="fas fa-dolly-flatbed">등록코드</i> : ${productDTO.productID}
			</div>
		</div>		
		<div class="form-row justify-content-center">						
			<div class="form-group col-4">
				<label for="beforeVal"><strong>재고 반영 전</strong></label>			
				<input type="number" class="form-control-plaintext" id="beforeVal" value="${productDTO.stock}" readonly>	
			</div>
			<div class="form-group col-4">
				<label for="stock"><strong>재고 반영 후</strong></label>			
				<input type="number" class="form-control-plaintext" id="stock" value="" readonly>				
			</div>				
			<div class="form-group col-3">
				<label for="numberUpDown"><strong>재고증감</strong></label>		
				<input type="number"  class="form-control" id="numberUpDown" value="0">	
			</div>															  		  	
		</div>	
 		<div class="form-row justify-content-center">	
 			<div class="form-group col-4">
				<label for="beforeCost"><strong>단가 반영 전</strong></label>			
				<input type="number" class="form-control" id="beforeCost" value="${productDTO.unitcost}" readonly>	
			</div>
			<div class="form-group col-4">
				<label for="unitcost"><strong>단가 반영 후</strong></label>			
				<input type="number" class="form-control" id="unitcost" value="" readonly>				
			</div>				
			<div class="form-group col-4">
				<label for="costUpDown"><strong>단가증감</strong></label>		
				<input type="number"  class="form-control" id="costUpDown" value="0">					  		  	
			</div>	
		</div>	   
			<div class="form-group">
				 <div class="row">
					<div class="col" align="right">          	
						<button type="button" id="resetMod" class="btn btn-outline-secondary">다시 작성</button>								
						<button type="button" id="doModify" class="btn btn-outline-info">재고 반영</button>			
					</div>
					</div>	
			</div>		
<div id="modifyResult"></div>								
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.inventory.js"></script>
<script type="text/javascript">
$(function(){
	var beforeVal = parseInt('${productDTO.stock}',10);
	var afterVal = parseInt('${productDTO.stock}',10);
	var beforeCost = parseInt('${productDTO.unitcost}',10);
	var afterCost = parseInt('${productDTO.unitcost}',10);	
	$('#stock').val(afterVal);	
	$('#unitcost').val(afterCost);			
});//ready

</script>