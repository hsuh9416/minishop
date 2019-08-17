<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<!--CSS Local LINK:START--> 
<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>입점상품현황</h3>
		</div>
	</div>  
	
	<input type="hidden" id="pg" value="${pg}">	
		
	<div class="form-row align-items-center">
		<table id="inventoryTable" class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col">상품코드</th>
					<th scope="col">등록코드</th>
					<th scope="col">상품단가</th>
					<th scope="col">재고수량</th>					
					<th scope="col">상품명</th>					
					<th scope="col">입고(예정)일</th>					
				</tr>
			</thead>  
			<tbody>
				<tr>
			   		<th scope="row"></th>
			   			<td colspan="6"></td>			   		
				</tr>
			</tbody> 	  
		</table>
	</div>	
	
	<div class="form-row align-items-center">
		<div class="col-3">
			<a id="reloadIcon"><i class="fas fa-retweet">새로고침</i></a>
		</div>
		<div class="col-9"></div>
	</div>	
	
	<div class="form-row justify-content-center subContent">
		<div class="col">
			<nav aria-label="Page navigation">
			  	<ul class="pagination justify-content-center" id="productPagingDiv"></ul>
			</nav>
		</div>								
	</div>
			
	<form id="inventorySearch">	
		<div class="form-row justify-content-center">	
			<input type="hidden" name="pg" value="1">					
			<div class="col-sm-2">
				<select id="searchOption" class="form-control">
					<option value="productid">등록코드</option>
					<option value="productName">상품명</option>
					<option value="product_name_no">상품코드</option>
				</select>			
			</div>
			<div class="col-sm-4">
		    	<input type="text"  class="form-control" id="keyword" value="${keyword}">			
			</div>
			<div class="col-2">
			    <input type="button" id="inventorySearchBtn" class="form-control btn btn-outline-dark" value="검색">		
			</div>			
		</div>				
	</form>		
			
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/admin.product.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/inventoryManage.js"></script>
	<!--JavaScript Local LINK:END-->
