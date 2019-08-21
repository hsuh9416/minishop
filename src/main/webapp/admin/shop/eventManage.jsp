<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--CSS Local LINK:START--> 
<link rel="stylesheet" href="/minishop/resources/custom/css/event.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>이벤트 관리</h3>
		</div>	
	</div>
	
	<div class="form-row">
		<div class="form-group col" align="center">  
		    	
			<div class="accordion" id="eventInfo">
			
				<div class="card">
					<div class="card-header" id="headingOne" align="center">
						<h2 class="mb-0">
					        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#seeAll" aria-expanded="true" aria-controls="seeAll">
					         	진행중인 이벤트 현황
					        </button>
			      		</h2>
			    	</div>	
			    	
			    	<div id="seeAll" class="collapse show" data-parent="#eventInfo">
			      		<div class="card-body">
			      		
			      				<div class="form-row justify-content-center">
			      					<h5 class="subTitle-white">배너 정보</h5>
			      				</div>			      		
								<div class="form-row justify-content-center subTitle">
									<div class="col-1 subContent"><font>#</font></div>
									<div class="col-2 subContent"><font>배너명</font></div>
									<div class="col-2 subContent"><font>배너이미지</font></div>
									<div class="col-3 subContent"><font>이벤트주소</font></div>
									<div class="col-2 subContent"><font>시작일</font></div>	
									<div class="col-2 subContent"><font>종료일</font></div>																		
								</div>
								<div class="form-row"><div class="form-group col"></div></div>
								
								<div class="form-row justify-content-center" id="bannerDiv"></div>	
											      		
								<div class="form-row"><div class="form-group col"></div></div>
			      				
			      				<div class="form-row justify-content-center">
			      					<h5 class="subTitle-white">발행 쿠폰 목록</h5>
			      				</div>
								<div class="form-row justify-content-center subTitle">
									<div class="col-1 subContent"><font>#</font></div>
									<div class="col-3 subContent"><font>쿠폰이름</font></div>
									<div class="col-2 subContent"><font>쿠폰분류</font></div>
									<div class="col-3 subContent"><font>할인액/할인률</font></div>
									<div class="col-3 subContent"><font>지급대상</font></div>										
								</div>
								
								<div class="form-row"><div class="form-group col"></div></div>
								
								<div class="form-row justify-content-center" id="couponDiv"></div>	
								
			      				<div class="form-row justify-content-center">
			      					<h5 class="subTitle-white">현행 배송료 정책</h5>
			      				</div>
								<div class="form-row justify-content-center subTitle">
									<div class="col-2"></div>
									<div class="col-1 subContent"><font>#</font></div>
									<div class="col-3 subContent"><font>배송분류</font></div>
									<div class="col-3 subContent"><font>배송료</font></div>	
									<div class="col-3"></div>								
								</div>
								
								<div class="form-row"><div class="form-group col"></div></div>
								
								<form id="deliveryDiv"></form>									
			      		
			      		</div>
			      	</div>
			      	
				</div>
						
				<div class="card">
					<div class="card-header" id="headingtwo" align="center">
						<h2 class="mb-0">
					        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#bannerInfo" aria-expanded="false" aria-controls="bannerInfo">
					         	배너 관리
					        </button>
			      		</h2>
			    	</div>
			
			    	<div id="bannerInfo" class="collapse" data-parent="#eventInfo">
			      		<div class="card-body">
							<form id="bannerManage" method="post" enctype="multipart/form-data" action="/minishop/admin/shop/bannerModify.do">								
								<input type="hidden" id="event_no" value="${event_no}"/>
								<input type="hidden" name="event_image" value=""/>
								<div class="form-row justify-content-center">
									<div class="form-group col-2" style="padding-top:5px;">
										<label for="event_no">배너 번호</label>
										<select id="bannerNum" name="event_no" class="form-control-plaintext inline-form">
											<option value="" selected>번호 선택</option>
											<option value="1">[1]</option>
											<option value="2">[2]</option>
											<option value="3">[3]</option>
										</select>
									</div>
								 	<div class="form-group col-5">
								 		<label for="event_no">배너명</label>
								  		<input type="text" class="form-control-plaintext inline-form" name="event_name"/>
								  	</div>
									<div class="form-group col-5">	
										<label for="event_no">배너이미지</label>	 
							    		<input type="file" class="form-control-file" name="event_image_new"/>
						    		</div>		  	
								 </div>
						    	<div class="form-row justify-content-center">
									<div class="form-group col-6">			
										<label for="event_no">이벤트주소</label>
						    			<input type="text" class="form-control-plaintext inline-form" name="event_url" placeholder="/minishop/이벤트주소.do"/>
						    		</div>
						    		<div class="form-group col-3">	
						  			<label for="event_no">시작일자</label>
						    			<input type="date" class="form-control-plaintext inline-form" name="start_date"/>
						    		</div>
						    		<div class="form-group col-3">	
						  			<label for="event_no">종료일자</label>
						    			<input type="date" class="form-control-plaintext inline-form" name="end_date"/>
						    		</div>    		
								</div>
						    	<div class="form-row justify-content-center">
									<div class="form-group col-8"></div>
						    		<div class="form-group col-4">	
						    			<button type="reset" class="btn btn-outline-secondary">초기화</button>
										<input type="button" id="changeBannerBtn" class="btn btn-outline-info" value="수정 반영">
						    		</div>
								</div>		
							</form>			      		
		      
			    		</div>
					</div>
				</div>
	
				<div class="card">
					<div class="card-header" id="headingthree" align="center">
						<h2 class="mb-0">
					        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#couponInfo" aria-expanded="false" aria-controls="couponInfo">
					         	쿠폰 관리
					        </button>
			      		</h2>
			    	</div>	
			    	
			    	<div id="couponInfo" class="collapse" data-parent="#eventInfo">
			      		<div class="card-body">
							<form id="couponManage" method="post" action="/minishop/admin/shop/makeCoupon.do">								
								<div class="form-row">
									<div class="form-group col-4" id="findCoupon">
										<div class="custom-control custom-radio custom-control-inline">
											<input type="radio" id="issue" name="selectionBtn" class="custom-control-input" value="new" checked>
											<label class="custom-control-label" for="issue">신규발행</label>
										</div>
										<div class="custom-control custom-radio custom-control-inline">
											<input type="radio" id="modify" name="selectionBtn" class="custom-control-input" value="existing">
										  	<label class="custom-control-label" for="modify">기존수정</label>								
										</div>
									</div>
									<div class="form-group col-2"></div>
									<div class="form-group col-4">
										<input type="text" class="form-control-plaintext inline-form" id="coupon_no" placeholder="쿠폰 발행 번호 입력" readonly/>
									</div>
									<div class="form-group col-2" id="findCoupon">		
										<input type="button" id="searchCoupon" class="btn btn-outline-info" value="검색" disabled/>
									</div>
								</div>		
								<div class="form-row">
									<div class="form-group col-2">
								 		<label for="coupon_name">발행번호</label>
								  		<input type="text" class="form-control-plaintext" name="coupon_no" readonly/>
								  	</div>
								 	<div class="form-group col-5">
								 		<label for="coupon_name">쿠폰이름</label>
								  		<input type="text" class="form-control-plaintext inline-form" name="coupon_name"/>
								  	</div>
									<div class="form-group col-2" style="padding-top:5px;">	
										<label for="coupon_target">지급대상</label>
										<select id="target" name="coupon_target" class="form-control-plaintext inline-form">
											<option value="" selected>선택</option>
											<option value="0">전체</option>
											<option value="1">개인</option>
										</select>
						    		</div>	
									<div class="form-group col-3" style="padding-top:5px;">	
										<label for="coupon_type">쿠폰종류</label>
										<select id="type" name="coupon_type" class="form-control-plaintext inline-form">
											<option value="" selected>선택</option>
											<option value="0">정액할인</option>
											<option value="1">정률할인</option>
											<option value="2">배송비면제</option>											
										</select>
						    		</div>						    		
								 </div>
						    	<div class="form-row">
									<div class="form-group col-8">
								 		<label for="warning"></label>
								  		<input type="text" class="form-control-plaintext" id="warning" readonly value="*정수로 기입해주세요.(ex>할인액 1,000원=>'1000' 할인률 20%=>'20')"/>
								  	</div>	
								 	<div class="form-group col-4">
								 		<label for="discount_mount">쿠폰 할인액(률)</label>
								  		<input type="number" class="form-control-plaintext inline-form" name="discount_mount"/>
								  	</div>									  								  								
								</div>
						    	<div class="form-row justify-content-center">
									<div class="form-group col-6"></div>
						    		<div class="form-group col-6">	
						    			<button type="reset" class="btn btn-outline-secondary">초기화</button>
						    			<input type="button" id="issueCoupon" class="btn btn-outline-secondary" value="쿠폰 발행">
										<input type="button" id="modifyCoupon" class="btn btn-outline-info" value="쿠폰 수정">
										<input type="button" id="deleteCoupon" class="btn btn-outline-info" value="쿠폰 삭제" disabled>
						    		</div>
								</div>									
							</form>			      		
			      		</div>
			      	</div>
			      	
				</div>

				<div class="card">
					<div class="card-header" id="headingFour" align="center">
						<h2 class="mb-0">
					        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#deliveryFeeInfo" aria-expanded="false" aria-controls="deliveryFeeInfo">
					         	배송료 정책
					        </button>
			      		</h2>
			    	</div>	
			    	
			    	<div id="deliveryFeeInfo" class="collapse" data-parent="#eventInfo">
			      		<div class="card-body">
							<form id="deliveryFeeManage" method="post" action="/minishop/admin/shop/modifyDeliveryFee.do">								
							    	<div class="form-row">
							    		<div class="form-group col-2"></div>
										<div class="form-group col-3" style="padding-top:5px;">	
											<label for="delivery_type">분류</label>
											<select id="delivery_type" name="delivery_code" class="form-control-plaintext inline-form">
												<option value="" selected>선택</option>	
												<option value="1">일반</option>	
												<option value="2">특별</option>																																		
											</select>
							    		</div>	
										<div class="form-group col-4">	
											<label for="delivery_fee">금액</label>
											<input type="number" class="form-control-plaintext inline-form" name="delivery_fee" value=""/>
							    		</div>
										<div class="form-group col-1">
											<label></label>
											<input type="text" readonly class="form-control-plaintext" value="(원)"/>
										</div>	
										<div class="form-group col-2" style="padding-top:20px;">
											<input type="button" id="deliveryModify" class="btn btn-outline-info" value="배송료 변경">
										</div>																	    										    									  								  								
									</div>								
							</form>			      		
			      		</div>
			      	</div>
			      	
				</div>
								
			</div>
	
		</div>
	</div>			

	
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminShop/eventManage.js"></script>
	<!--JavaScript Local LINK:END-->