<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<!--Custom styles-->
	<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	
<div class="col-lg-8">
	<input type="hidden" id="pg" value="${pg}"/>
		 <div class="row" id="titleDiv">
		 	<div class="col" align="center" style="padding-bottom: 20px;">
				<h3>고객주문관리</h3>
			</div>	
		</div>
		<input type="hidden" id="pg" value="${pg}">
		<div class="table-responsive">
			<table id="productTable" class="table justify-content-center">
			  <thead class="thead-dark">
			    <tr>
					<th scope="col">#</th>			    
					<th scope="col">상품이미지</th>	
					<th scope="col">등록코드</th>
					<th scope="col">상품분류</th>					
					<th scope="col">상품명</th>	
					<th scope="col">할인가능여부</th>										
					<th scope="col">상품등록여부</th>					
			  </tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<th scope="row"></th>
			   			<td colspan="7"></td>			   		
			   	</tr>
			   </tbody> 	  
			</table>
		</div>
	<div class="container-fluid">
			<nav aria-label="Page navigation">
			  <ul class="pagination justify-content-center" id="productPagingDiv"></ul>
			</nav>
	</div>

	<div class="container-fluid">
		<form id="productSearch" name="productSearch">
			<div class="form-row justify-content-center">
			   <span>
				<input type="hidden" name="pg" id="pg" value="1">
				</span>
				<span style="margin-left:20px;">
				<select name="searchOption" id="searchOption" class="form-control">
			        <option value="productid">등록코드</option>			
			        <option value="product_name_no">상품코드</option>
					<option value="productname">상품명</option>
			    </select>
			    </span>
			    <span style="margin-left:20px;">
			    <input type="text"  class="form-control" name="keyword" id="keyword" value="${keyword}" size="20">
			    </span>
			   <span style="margin-left:20px;">
			    <input type="button" id="productSearchBtn" class="btn btn-outline-dark" value="검색">
			   </span>
		   
			</div>
	  	</form>
	</div>  
		
</div>



<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin.order.js"></script>
<script type="text/javascript">
$(document).ready(function(){

});//onready


</script>