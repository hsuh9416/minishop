<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<!--CSS Local STYLE:START-->
<link rel="stylesheet" href="/minishop/resources/custom/css/orderForm.css">
	<!--CSS Local STYLE:END-->   
	
	    
<div class="col-lg-8">

	<div class="row" id="titleDiv">
		<div class="col">
	 		<h3>주문 내역서</h3>		
	 	</div>
	</div>
		<input type="hidden" name="order_no" value="${order_no}"/>
		<h5 class="sub-title-undeline">[기본 정보]</h5>
		<div class="form-row ">	
			<div class="col-1" align="center">
				<label for="order_no" class="under-line-title">#</label>
			</div>	
			<div class="col-2" align="center">
				<font id="order_no"></font>
			</div>	
			<div class="col-2" align="center">
				<label for="order_id" class="under-line-title">주문ID</label>
		  	</div>	
			<div class="col-2" align="center">
				<font id="order_id"></font>
			</div>							  
			<div class="col-2" align="center">
				<label for="order_date" class="under-line-title">최초주문일</label>
		  	</div>	
			<div class="col-3" align="center">
				<font id="order_date"></font>
			</div>				  			  		  				  	
		</div>	

		<div class="form-row ">	
			<div class="col-1" align="center">
				<label for="order_state" class="under-line-title">상태</label>
		  	</div>	
			<div class="col-2" align="center">
				<font id="order_state"></font>
			</div>			
			<div class="col-2" align="center">
				<label for="order_name" class="under-line-title">주문자</label>
			</div>	
			<div class="col-2" align="center">
				<font id="order_name"></font>
			</div>				  	  			  	
			<div class="col-2" align="center">
				<label for="order_logtime" class="under-line-title">최종수정일</label>
		  	</div>	
			<div class="col-3" align="center">
				<font id="order_logtime"></font>
			</div>					  				  	
		</div>		

		<div class="form-row">	
			<div class="col-2" align="center">
				<label for="order_statement" class="under-line-title">비고</label>
		  	</div>	
			<div class="col-10" align="center">
				<input type="text" readonly class="form-control-plaintext inline-form" id="order_statement"/>			
			</div>	
		</div>
		
	<hr class="sub-hr" noshade/>		
		<h5 class="sub-title-undeline">[배송 정보]</h5>
		<div class="form-row" >	
			<div class="col-2" align="center">
				<label for="selectModify">배송지</label>					
			</div>	
			<div class="col-4"></div>				
			<div class="col-6">
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="original" name="selectModify" class="custom-control-input" value="original" checked>
					<label class="custom-control-label" for="original">기존주소</label>
				</div>
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="new" name="selectModify" class="custom-control-input" value="new">
					<label class="custom-control-label" for="new">변경주소</label>								
				</div>
			</div>						  				  	
		</div>
 	 	<div class="form-row" id="zip">
			<div class="col-2" align="center">
				<label for="zipcode" class="under-line-title">우편번호</label>
			</div>
		    <div class="col-10" align="center">
				<font id="order_address"></font> 
		    </div>
		</div>
		<form id="modifyDeliveryForm">
			<hr  class="sub-secondary-hr" noshade/>		
	 	 	<div class="form-row" id="zip">
				<div class="col-2" align="center">
					<label for="zipcode" class="under-line-title">우편번호</label>
			  	</div>	 	 	
			    <div class="col-3">
					<input type="text" readonly class="form-control-plaintext inline-form" name="zipcode" id="zipcode" value=""> 
			    </div>
			    <div class="col-3">
					<input type="button" class="btn btn-outline-dark" value="우편번호검색" id="checkPost" onclick="sample3_execDaumPostcode()" disabled>
			    </div>
			    <div class="col-4"></div>
			</div>	
	 	 	<div class="form-row" id="address1">
				<div class="col-2" align="center">
					<label for="addr1" class="under-line-title">상세주소</label>
			  	</div>
			    <div class="col-6" align="center">
			    	<input type="text" readonly class="form-control-plaintext inline-form" name="addr1" id="addr1" value="${memberDTO.addr1 }">
			    </div>
				<div class="col-4">
			    	<input type="text" readonly class="form-control-plaintext inline-form" name="extra" id="extra" value="">
				</div>	    		  						
			</div>  	
		   	<div id="wrap">
		  		<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" onclick="foldDaumPostcode()" alt="접기 버튼">
		  	</div> 	
	  	 
			<div class="form-row" id="address2">
				<div class="col-2" align="center">
					<label for="addr2" class="under-line-title">상세주소</label>
			  	</div>		
				<div class="col-10" align="center">
					<input type="text" class="form-control-plaintext inline-form" name="addr2" id="addr2" value="${memberDTO.addr2}">
			    </div>
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col" align="right">      								
					<button type="button" id="doModifyDelivery" class="btn btn-outline-info">반영하기</button>			
				</div>
			</div>				
			<hr  class="sub-secondary-hr" noshade/>					 
		</form>

		<hr class="sub-hr" noshade/>
		<h5 class="sub-title-undeline">[연락 정보]</h5>		
		<div class="form-row">
			<div class="col-8">
				<input type="checkbox" id="modifyCheck" value="1">연락 정보를 변경합니다
			</div>
			<div class="col-4"></div>
		</div>					
 	 	
 	 	<div class="form-row" id="zip">
			<div class="col-2" align="center">
				<label for="order_tel" class="under-line-title">회신연락처</label>
			</div>
		    <div class="col-10" align="center">
		    	<input type="tel" id="order_tel" readonly class="form-control-plaintext inline-form"/>
		    </div>
		</div>		
 	 	<div class="form-row" id="zip">
			<div class="col-2" align="center">
				<label for="order_email" class="under-line-title">회신이메일</label>
			</div>
		    <div class="col-10" align="center">
		    	<input type="email" id="order_email" readonly class="form-control-plaintext inline-form"/>		
		    </div>
		</div>		
		<div class="form-row justify-content-center">
			<div class="form-group col" align="right">      								
				<button type="button" id="modifyContact" class="btn btn-outline-info" disabled>반영하기</button>			
			</div>
		</div>
		<hr class="sub-hr" noshade/>		
		<h5 class="sub-title-undeline">[상품 구입 내역]</h5>	
		<div class="form-row align-items-center">
			<div class="col-1" align="center">#</div>
			<div class="col-2" align="center">상품이미지</div>
			<div class="col-2" align="center">상품명</div>					
			<div class="col-2" align="center">판매단가</div>
			<div class="col-2" align="center">수량</div>
			<div class="col-3" align="center">합계</div>													
		</div>		
		
		<form id="cartListForm"></form>
					
		<hr  class="sub-secondary-hr" noshade/>

		<div class="form-row align-items-center">
			<div class="col-2" align="center">
				<label class="under-line-title">상품합계</label>			
			</div>
			<div class="col-7" align="center"></div>
			<div class="col-3" align="center" id="productTotalDiv"></div>	
		</div>
		<div class="form-row align-items-center">
			<div class="col-2" align="center">
				<label class="under-line-title">배송료</label>			
			</div>
			<div class="col-7" align="center"></div>
			<div class="col-3" align="center" id="deliveryFeeDiv"></div>	
		</div>		
		<div class="form-row align-items-center">
			<div class="col-2" align="center">
				<label class="under-line-title">총합계</label>			
			</div>
			<div class="col-7" align="center"></div>
			<div class="col-3" align="center" id="subTotalDiv"></div>
		</div>
									
		<hr class="sub-hr" noshade/>	
			
		<h5 class="sub-title-undeline">[결제 정보]</h5>		
		<div class="form-row justify-content-center">
			<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">결제방법</div>
			<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">결제일자</div>
			<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">결제금액</div>
			<div class="col-4" align="center" style="text-decoration:underline;text-underline-position:under">비고</div>																																		
		</div>
		
		<form id="paymentForm"></form>
			
		<hr class="sub-hr" noshade/>	
			
		<h5 class="sub-title-undeline">[추가 정보]</h5>	
	
		<div class="form-row">					
			<div class="col-2" align="center">
				<label for="order_deliverynum" class="under-line-title">배송장번호</label>
		  	</div>	
			<div class="col-10" align="center">
				<input type="text" readonly class="form-control-plaintext inline-form" id="order_deliverynum"/>
			</div>							  				  			  		  				  	
		</div>			
		<div class="form-row">					
			<div class="col-2" align="center">
				<label for="order_refundaccount" class="under-line-title">환불계좌</label>
		  	</div>	
			<div class="col-10" align="center">
				<input type="text" readonly class="form-control-plaintext inline-form"  id="order_refundaccount"/>
			</div>							  				  			  		  				  	
		</div>	

		
		<hr class="sub-hr" noshade/>
			
		<div class="form-row justify-content-center">
			<div class="form-group col" align="right">      								
				<button type="button" id="orderCancelBtn" class="btn btn-outline-secondary">주문취소</button>	
				<button type="button" id="requestRefundBtn" class="btn btn-outline-danger">환불요청</button>	
				<button type="button" id="deliveryConfirmBtn" class="btn btn-outline-primary">수취확인</button>
				<button type="button" id="personalQABtn" class="btn btn-outline-info">1:1문의</button>				
			</div>
		</div>	
</div>

 	<!--JavaScript Local LINK:START-->	
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/post.daum.js"></script>		
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/trading/orderView.js"></script>
	<!--JavaScript Local LINK:END-->