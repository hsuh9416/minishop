<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!--CSS Local LINK:START-->        
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">	 
	<!--CSS Local LINK:END-->
	
<div class="container">       

 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h2 class="first">[회원 혜택 관리]</h2>		
 		</div>
	</div>	

	<div class="card card-body">
		<input type="hidden" id="target" value="${target}"/>
		<div class="form-row">	
			<label for="selectBenefit">지급대상</label>
			<div class="form-group col-4" id="selectBenefit">
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="global" name="selectTarget" class="custom-control-input" value="all" checked>
					<label class="custom-control-label" for="global">전체지급</label>
				</div>
				<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" id="personal" name="selectTarget" class="custom-control-input" value="person">
				<label class="custom-control-label" for="personal">개별지급</label>								
				</div>
			</div>	
			<div class="form-group col-2"></div>
			<div class="form-group col-4">
				<input type="text" class="form-control" id="coupon_no" placeholder="회원 아이디 입력" readonly/>
			</div>
			<div class="form-group col-2" id="findCoupon">		
				<input type="button" id="searchTarget" class="btn btn-outline-info" value="검색" disabled/>
			</div>				
		</div>
		<div class="form-row justify-content-center">
				<div class="col-10" align="center">
					<font id="targetInfo"></font>	
				</div>		
		</div>	
		<div class="form-row">	
			<label for="selectBenefit">지급혜택</label>
			<div class="form-group col-4">
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="couponCheck" name="selectBenefit" class="custom-control-input" value="couponSelect" checked>
					<label class="custom-control-label" for="couponCheck">쿠폰</label>
				</div>
				<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" id="poingCheck" name="selectBenefit" class="custom-control-input" value="pointSelect">
				<label class="custom-control-label" for="poingCheck">포인트</label>								
				</div>
			</div>
			<div class="form-group col-8"></div>	
		</div>			
		<form id="couponForm">	
			<div class="form-row justify-content-center">
				<div class="col-8" align="center">
					<label for="productid"><strong>지급할 쿠폰</strong></label>				
					<select id="coupon_no" class="form-control">
						<option value="">[지급 쿠폰 선택]</option>   
					</select>				
				</div>
				<div class="col-4" align="center" id="couponState"></div>																																	
			</div>
			<div class="form-row">
				<div class="col-10">
					<label for="subject"><strong>메일 알림 제목</strong></label>
					<input type="text" class="form-control" name="subject"/>	
				</div>			
			</div>
			<div class="form-row">
				<div class="col-10" >
					<label for="content"><strong>메일 알림 내용</strong></label>
					<textarea class="form-control" name="content"></textarea>					
				</div>			
			</div>	
			<div class="form-row">
				<div class="col-9" id="checkC"></div>
				<div class="col-3">
					<input type="button" class="btn btn-outline-info" id="couponIssue"/>					
				</div>			
			</div>						
		</form>	

		<form id="pointForm">	
			<div class="form-row justify-content-center">
				<div class="col-4" align="center">
					<label for="productid"><strong>지급할 포인트</strong></label>				
					<input type="number" name="pointQty" value="0"/>(점)			
				</div>
				<div class="col-8" align="center" id="pointState"></div>																																	
			</div>
			<div class="form-row">
				<div class="col-10">
					<label for="subject"><strong>메일 알림 제목</strong></label>
					<input type="text" class="form-control" name="subject"/>	
				</div>			
			</div>
			<div class="form-row">
				<div class="col-10" >
					<label for="content"><strong>메일 알림 내용</strong></label>
					<textarea class="form-control" name="content"></textarea>					
				</div>			
			</div>
			<div class="form-row">
				<div class="col-9" id="checkP"></div>
				<div class="col-3">
					<input type="button" class="btn btn-outline-info" id="pointGrant"/>					
				</div>			
			</div>									
		</form>	
		<div class="form-row">
			<div class="col" align="right">          	
				<button type="button" id="closeBtn" class="btn btn-outline-secondary">닫기</button>							
			</div>
		</div>	
	</div>	

</div>
	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/benefitGivingForm.js"></script>
	<!--JavaScript Local LINK:END-->