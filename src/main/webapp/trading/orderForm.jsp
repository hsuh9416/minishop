<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<!--CSS Local STYLE:START-->
<style>
hr.sub-hr{
	border-top: double 3px;
	color: darkgray;
	width: 100%;
}

hr .sub-secondary-hr{
	border: 1px solid;
	color: lightgray;
	width: 100%;
}

.padding-right{
	padding-right: 15px;
}
</style>	
		<!--CSS Local STYLE:END-->   
		 
<div class="col-lg-8">

	<div class="row" id="titleDiv">
		<div class="col">
	 		<h3>상품 주문서</h3>		
	 	</div>
	</div>
	<hr class="sub-hr" noshade/>
	<div class="form-row align-items-center">
		<div class="col" align="center">
	 		<h5 style="text-decoration:underline;text-undeline-position: under;">주문 목록</h5>		
	 	</div>	
	</div>
	<hr  class="sub-secondary-hr" noshade/>
	
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
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>상품 합계</strong></label>			
		</div>
		<div class="form-group col-6"></div>	
		<div class="form-group col-3" align="center" id="productTotalDiv"></div>	
	</div>
	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>배송료</strong></label>			
		</div>
		<div class="form-group col-6"></div>
		<div class="form-group col-3" align="center" id="deliveryFeeDiv"></div>	
	</div>		
	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>총 합계</strong></label>			
		</div>
		<div class="form-group col-6"></div>
		<div class="form-group col-3" align="center" id="subTotalDiv"></div>
	</div>
	
	<hr class="sub-hr" noshade/>
	
	<div class="form-row align-items-center">
		<div class="col" align="center">
	 		<h5 style="text-decoration:underline;text-undeline-position: under;">배송지</h5>		
	 	</div>	
	</div>
	
	<hr  class="sub-secondary-hr" noshade/>
	

	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label for="delivery" style="padding-right:20px; padding-top:-5px;"><strong>배송지 선택</strong></label>			
		</div>
		<div class="form-group col-2" align="center">
			<input class="form-check-input" type="radio" name="delivery" id="original" value="original" checked>
			<label class="form-check-label" for="original">기존</label>
		</div>
		<div class="form-group col-2"align="center">
			<input class="form-check-input" type="radio" name="delivery" id="new" value="new">
			<label class="form-check-label" for="new">신규</label>
		</div>
	</div>
	<div class="form-row align-items-center">
		<div class="form-group col">
				
	 	 	<div class="form-row" id="address1">
				<div class="form-group col-3" align="center">
			    	<label for="address1" style="padding-right:20px; padding-top:-5px;"><strong>우편 번호</strong></label> 
			    </div>
			    <div class="form-group col-3">
					<input type="text" readonly class="form-control-plaintext" style="border-bottom:1px solid;" name="zipcode" id="zipcode" value=""> 
			    </div>
				<div class="form-group col-1"></div>			    
			    <div class="form-group col-3" align="center">
					<input type="button" class="btn btn-outline-dark" value="우편번호 검색" id="checkPost" onclick="sample3_execDaumPostcode()">
			    </div>
			    <div class="form-group col-2"></div>
			</div>	

	 	 	<div class="form-row" id="address2a">
				<div class="form-group col-3" align="center">
			    	<label for="address2" style="padding-right:20px; padding-top:-5px;"><strong>상세 주소</strong></label>	 
			    </div>
			    <div class="form-group col-4">
					<input type="text" readonly class="form-control-plaintext" style="border-bottom:1px solid;" name="addr1" id="addr1" value=""> 
			    </div>
				<div class="form-group col-3">
					<input type="text" readonly class="form-control-plaintext" style="border-bottom:1px solid;" name="extra" id="extra" value="">		
				</div>
				<div class="form-group col-2"></div>			    
			</div>
  		
  			<div class="form-row" id="address2b">
				<div class="form-group col-3"></div>			
				<div class="form-group col-7">
					<input type="text" class="form-control-plaintext" style="border-bottom:1px solid;" name="addr2" id="addr2" value="">
			    </div>
				<div class="form-group col-2"></div>			    
			</div>			
  				
		   	<div class="form-row" id="wrap">
		   		<div class="form-group col-3"></div>
		   		<div class="form-group col-7">
		  			<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" onclick="foldDaumPostcode()" alt="접기 버튼">
		  		</div>
		  		<div class="form-group col-2"></div>
		  	</div> 	
  	 

		</div>			  	
	</div>
	<hr  class="sub-secondary-hr" noshade/>	
	
	<hr class="sub-hr" noshade/>

	<div class="form-row align-items-center">
		<div class="col" align="center">
	 		<h5 style="text-decoration:underline;text-undeline-position: under;">쿠폰 및 포인트 사용</h5>		
	 	</div>	
	</div>

	<hr  class="sub-secondary-hr" noshade/>	
	
	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>총 할인가능액</strong></label>			
		</div>
		<div class="form-group col-6"></div>				
		<div class="form-group col-3" align="center" id="discoutableTotal"></div>			
	</div>
	
	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label for="useBenefit" style="padding-right:20px; padding-top:-5px;"><strong>사용 여부</strong></label>			
		</div>
		<div class="form-group col-2" align="center">
			<input class="form-check-input" type="radio" name="useBenefit" id="yes" value="yes" checked>
			<label class="form-check-label" for="original">사용함</label>
		</div>
		<div class="form-group col-2"align="center">
			<input class="form-check-input" type="radio" name="useBenefit" id="no" value="no">
			<label class="form-check-label" for="new">사용안함</label>
		</div>
	</div>

	
	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>포인트</strong></label>			
		</div>
		<div class="form-group col-3" align="center">			
			<input type="number" class="form-control-plaintext" style="border-bottom:1px solid;" id="point" value=""/> 		
		</div>
		<div class="form-group col-1" align="center">(점)</div>
		<div class="form-group col-1"></div>		
		<div class="form-group col-4">* 보유 포인트 : <font id="member_point"></font> (점)</div>
								
	</div>

	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>쿠폰</strong></label>			
		</div>	
		<div class="form-group col-5" align="center">			
			<select id="coupon_no" class="form-control-plaintext"  style="border-bottom:1px solid;" >
				<option value="0">[지급 쿠폰 선택]</option>   
			</select>				
		</div>			
		<div class="form-group col-4" id="couponDiv"></div>	
	</div>	

	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>총 할인액</strong></label>			
		</div>
		<div class="form-group col-6" id="couponWarningDiv"></div>				
		<div class="form-group col-3" align="center" id="discoutTotal"></div>			
	</div>
	
	<hr class="sub-hr" noshade/>
	
	<div class="form-row align-items-center">
		<div class="col" align="center">
	 		<h5 style="text-decoration:underline;text-undeline-position: under;">결제 방법</h5>		
	 	</div>	
	</div>
	
	<hr  class="sub-secondary-hr" noshade/>
	
	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label style="padding-right:20px; padding-top:-5px;"><strong>결제하실 금액</strong></label>			
		</div>
		<div class="form-group col-6"></div>				
		<div class="form-group col-3" align="center" id="finalTotal"></div>				
	</div>

	<div class="form-row align-items-center">
		<div class="form-group col-3" align="center">
			<label for="payment" style="padding-right:20px; padding-top:-5px;"><strong>결제수단 선택</strong></label>		
		</div>
		<div class="form-group col-3" align="center">
			<input class="form-check-input" type="radio" name="payment" id="card" value="card" checked>
			<label class="form-check-label" for="card">카드결제</label>
		</div>
		<div class="form-group col-3" align="center">
			<input class="form-check-input" type="radio" name="payment" id="bank" value="bank">
			<label class="form-check-l3abel" for="bank">무통장결제</label>
		</div>
		<div class="form-group col-3" align="center">
			<input class="form-check-input" type="radio" name="payment" id="etc" value="etc" disabled>
			<label class="form-check-label" for="etc">기타</label>
		</div>		
	</div>

	<hr  class="sub-secondary-hr" noshade/>	
	
	<div class="form-row align-items-center" id="cardDiv">
		<div class="col" align="center">카드가 빠밤</div>
	</div>		

	<div class="form-row align-items-center" id="bankDiv">
		<div class="col" align="center">계좌가 빠밤</div>	
	</div>	

	<div class="form-row align-items-center" id="etcDiv">
		<div class="form-group col" style="color:red;font-weight:bold;text-align:center;">[추가로 결제할 금액이 없습니다]</div>	
	</div>	
		
	<hr class="sub-hr" noshade/>
	
	
	<div class="form-row align-items-center">
		<div class="form-group col" align="right">
			<input type="button" class="btn btn-outline-success" value="주문하기" id="choiceOrder">	
			<input type="button" class="btn btn-outline-secondary" value="돌아가기" id="returnBtn">	
		</div>			
	</div>					
</div>
 	<!--JavaScript Local LINK:START-->	
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/post.daum.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/trading/orderForm.js"></script>
	<!--JavaScript Local LINK:END-->