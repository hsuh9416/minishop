<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    

	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->

<div class="col-lg-8">

	<div class="row" id="titleDiv">
		<div class="col">
			<h3>${productDTO.product_category_name}</h3>		
		</div>
	</div>
	
	<input type="hidden" id="SEQ" value="${SEQ}"/>
	<input type="hidden" id="productID" value="${productDTO.productID}"/>
	<input type="hidden" id="product_name_no" value="${productDTO.product_name_no}"/>
	<input type="hidden" id="unitcost" value="${productDTO.unitcost}"/>
	<input type="hidden" id="stock" value="${productDTO.stock}"/>	
	<input type="hidden" id="userName" value="${memberDTO.name}"/>
	<input type="hidden" id="orderName" value="${guestDTO.guest_name}"/>
	<input type="hidden" id="memberID" value="${memberDTO.id}"/>
	<input type="hidden" id="guestID" value="${guestDTO.guest_id}"/>
					
	<form id="buyNowForm" method="post" action="/minishop/trading/orderForm.do">
		<input type="hidden" name="directOrder" value="true"/>
		<input type="hidden" name="product_name_no" value=""/>
		<input type="hidden" name="cart_qty" value="1"/>
	</form>
	
	<div class="table table-borderless">
		<table id="userProductTable" class="table justify-content-center">
			<thead class="thead">
				<tr align="center">
					<th scope="col" colspan="4" >${productDTO.product_name_title}</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td rowspan="6" >
			   			<img src="/minishop/storage/showProduct.do?product_name_image=${productDTO.product_name_image}">
			   		</td>
			   		<td colspan="2"><h2>${productDTO.productName}</h2></td>
			   	</tr>
			   	<tr>
			   		<td colspan="2">
			   			<c:if test="${productDTO.product_salesMount>=300}">
			   				<span>#인기 상품</span>&emsp;
						</c:if>
			   			<c:if test="${productDTO.product_name_price>productDTO.unitcost}">
			   				<span>#세일 상품</span>&emsp;
						</c:if>	
			   			<c:if test="${productDTO.product_hit>=300}">
			   				<span>#주목 상품</span>&emsp;
						</c:if>	
			   			<c:if test="${productDTO.product_like>=300}">
			   				<span>#관심 상품</span>&emsp;
						</c:if>																						
			   		</td>			   	
			   	</tr>		
			   	<tr>
				   	<td colspan="2">
				   	<c:if test="${productDTO.product_name_price<=productDTO.unitcost}">
						<div class="product_price">NowOn Price&emsp;<fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>원</div>
					</c:if>
					<c:if test="${productDTO.product_name_price>productDTO.unitcost}">
						<div class="original_price">정상가&emsp;<fmt:formatNumber type="number" value="${productDTO.product_name_price}" pattern="#,###"/>원</div>
						<div class="product_price">판매가&emsp;<fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>원</div>					
					</c:if>	
			   		</td>		   	
			   	</tr>	
				<tr>
			   		<td align="right"><h4>수량</h4></td>
			   		<td align="right">
						<div class="btn-group mr-2" role="group" aria-label="Second group">
						 	<button type="button" id="cart_minus" class="btn btn-light"><i class="fa fa-minus" aria-hidden="true"></i></button>
							<input type="number" class="form-control-plaintext" id="cart_qty" value="1">
							<button type="button" id="cart_plus" class="btn btn-light"><i class="fa fa-plus" aria-hidden="true"></i></button>
  						</div>		   		
			   		</td>					
				</tr>
				<tr>
					<td colspan="2"><div align="right" class="total_price">합계&emsp;<font id="totalCost"><fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/></font>원</div></td>
				</tr>			
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" id="putCartBtn" class="btn btn-outline-danger btn-lg">add to cart</button>
							<c:if test="${SEQ=='NO'}">
								<button class="btn btn-danger" id="product_like"><i class="fas fa-heart">좋아요</i></button>
							</c:if>
							<c:if test="${SEQ=='YES'}">
								<button class="btn btn-secondary" id="product_like"><i class="fas fa-heart">좋아요 취소</i></button>
							</c:if>							
							
						</div>
			   		</td>
			   	</tr>
			   	<tr align="center">
				   	<td colspan="4">
				   		<h2>상세정보</h2>
				   	</td>
			   	</tr>
			   	<tr align="center">
				   	<td colspan="4">
				   		${productDTO.product_name_detail}
				   	</td>
			   	</tr>
			   </tbody>  			
			</table>
		</div>
		
		<div class="row justify-content-center">
			<div class="col" align="center">
				<button type="button" id="writeQABtn" class="btn btn-outline-info btn-lg">이 상품의 문의 쓰기</button>
				<button type="button" id="writeReviewBtn" class="btn btn-outline-info btn-lg">이 상품의 리뷰 쓰기</button>
			</div>
		</div>
		
	 	<!-- shop policies -->
	<jsp:include page="/template/shopPolicy.jsp"/>
	
</div>

   	<!--JavaScript Local LINK:START--> 
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/product/productView.js"></script>
	<!--JavaScript Local LINK:END-->