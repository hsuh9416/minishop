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
			<h3>[상품정보] ${productDTO.productName}</h3>
		</div>
	</div>
	
    <input type="hidden" id="productID" value="${productDTO.productID}"/>
    <input type="hidden" id="product_name_no" value="${productDTO.product_name_no}"/>   
    <input type="hidden" id="product_onstore" value="${productDTO.product_onstore}"/>
	<input type="hidden" id="pg" value="${pg}">	
	<input type="hidden" id="product_name_price" value="${productDTO.product_name_price}">	
	<input type="hidden" id="unitcost" value="${productDTO.unitcost}">			

	<div class="form-row">
		<div class="form-group col" align="center">      	
			<div class="accordion" id="productInfo">
		
				<div class="card">
					<div class="card-header" id="headingOne" align="center">
						<h2 class="mb-0">
					        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#basicInfo" aria-expanded="true" aria-controls="basicInfo">
					         	 상품 기본 정보
					        </button>
			      		</h2>
			    	</div>
			
			    	<div id="basicInfo" class="collapse show" data-parent="#productInfo">
			      		<div class="card-body">
			      		
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>상품코드</font></div>		
								<div class="col-3 subContent">${productDTO.product_name_no}</div>	
								<div class="col-2 subTitle"><font>분류</font></div>	
								<div class="col-5 subContent">${productDTO.product_category_name}</div>										
							</div>	
					
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>등록코드</font></div>		
								<div class="col-3 subContent">${productDTO.productID}</div>	
								<div class="col-2 subTitle"><font>책정가격</font></div>	
								<div class="col-5 subContent"><fmt:formatNumber type="number" value="${productDTO.product_name_price}" pattern="#,###"/>(원)/(개)</div>										
							</div>		
					
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>이름</font></div>	
								<div class="col-10 subContent" align="center">${productDTO.productName}</div>										
							</div>				      
			
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>상태</font></div>	
								<div class="col-10 subContent" align="center">
								 	<c:if test="${productDTO.product_onstore=='YES'}"><strong id="iconActive">[입점]</strong></c:if>
									<c:if test="${productDTO.product_onstore=='NO'}"><strong id="iconInactive">[미입점]</strong></c:if>	
								</div>										
							</div>		      
			    		</div>
					</div>
				</div>
				
				<div class="card">
					<div class="card-header" id="headingTwo"  align="center">
						<h2 class="mb-0">
					        <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
					          	상품 옵션 정보
					        </button>
			      		</h2>
			    	</div>
			    
			    	<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#productInfo">
			      		<div class="card-body">
			      		
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>판매가격</font></div>		
								<div class="col-4 subContent"><fmt:formatNumber type="number" value="${productDTO.unitcost}" pattern="#,###"/>(원)/(개)</div>	
								<div class="col-2 subTitle"><font>누적판매량</font></div>								
								<div class="col-4 subContent">
									<i id="iconMinimum" class="fas fa-shopping-cart">${productDTO.product_salesMount}</i>
								</div>									
							</div>	
							
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>조회수</font></div>		
								<div class="col-4 subContent">
									<i id="iconMinimum" class="fas fa-eye">${productDTO.product_hit}</i>
								</div>	
								<div class="col-2 subTitle"><font>관심도</font></div>		
								<div class="col-4 subContent">
									<i id="iconMinimum" class="fas fa-heart">${productDTO.product_like}</i>
								</div>																
							</div>					
		
							<div class="form-row align-items-center">
								<div class="col-2 subTitle"><font>할인정책</font></div>		
								<div class="col-10 subContent">
									<c:if test="${productDTO.promotioncode=='1'}">
										<i id="iconActive" class="fas fa-percent"><strong>[중복할인가능]</strong></i>
									</c:if>
									<c:if test="${productDTO.promotioncode=='0'}">
										<i id="iconInactive" class="fas fa-percent"><strong>[중복할인불허]</strong></i>
									</c:if>	
								</div>																
							</div>							      		
		
						</div>
					</div>
				</div>
			  	  
				<div class="card">
			    	<div class="card-header" id="headingThree"  align="center">
						<h2 class="mb-0">
							<button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
			        			 상품 상세 정보
			        		</button>
			      		</h2>
			    	</div>
			    	
			    	<div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#productInfo">
			      		<div class="card-body">
			      		
							<div class="form-row align-items-center">
								<div class="col-2 subTitle-white">등록 상품 제목</div>		
								<div class="col-10 subContent">${productDTO.product_name_title}</div>				
							</div>		        	
							<hr class="sub-hr">
							<div class="form-row align-items-center">
								<div class="col-2 subTitle-white">등록 상품 이미지</div>		
								<div class="col-10 subContent">
									<img style="width:300px; height:300px;"src="/minishop/storage/showProduct.do?product_name_image=${productDTO.product_name_image}">
								</div>				
							</div>
							<hr class="sub-hr">
							<div class="form-row align-items-center">
								<div class="col-2 subTitle-white">등록 상품 내용</div>		
								<div class="col-10 subContent">
									${productDTO.product_name_detail}
								</div>				
							</div>							        	
			    		 </div>
			    	</div>    	
				</div>		
			</div>	
	
		</div>
	</div>	
		
	<div class="form-row">
		<div class="form-group col" align="center">      
			<input type="button" id="productModifyBtn"class="btn btn-outline-dark" value="상품 수정">
			<input type="button" id="productDelete" value="상품 삭제" class="btn btn-outline-dark">
			<input type="button" id="productReturn" class="btn btn-outline-dark" value="뒤로가기">										    									
		</div>
	</div>	
	
</div>	
	<!--JavaScript Local LINK:START-->	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/admin.product.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminProduct/productView.js"></script>
	<!--JavaScript Local LINK:END-->