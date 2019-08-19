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
		<input type="hidden" name="id"/>
		<input type="hidden" name="coupon_no"/>			
		<input type="hidden" name="content"/>
		<div class="form-row">	
			<label for="selectTarget">[대상]</label>
			<div class="form-group col-4">
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="all" name="selectTarget" class="custom-control-input" value="all" checked>
					<label class="custom-control-label" for="all">전체</label>
				</div>
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="person" name="selectTarget" class="custom-control-input" value="person">
					<label class="custom-control-label" for="person">개별</label>								
				</div>
			</div>	
			<div class="form-group col-4">
				<input type="text" class="form-control" id="benefit_target" placeholder="회원 아이디 입력" readonly/>
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
			<label for="selectBenefit">[지급]</label>
			<div class="form-group col-5">
				<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="couponSelect" name="selectBenefit" class="custom-control-input" value="couponSelect">
					<label class="custom-control-label" for="couponSelect">쿠폰</label>
				</div>
				<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" id="pointSelect" name="selectBenefit" class="custom-control-input" value="pointSelect">
				<label class="custom-control-label" for="pointSelect">포인트</label>								
				</div>
			</div>
			<div class="form-group col-7"></div>	
		</div>			
		<form id="couponForm">	
			<div class="form-row justify-content-center">
				<div class="col-10" align="center">
					<label for="coupon_no"><strong>지급할 쿠폰</strong></label>				
					<select id="coupon_no" class="form-control">
						<option value="">[지급 쿠폰 선택]</option>   
					</select>				
				</div>																																
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-10" id="couponState" align="center"></div>
			</div>
			<div class="form-row justify-content-center">
				<label for="coupon_duedate"><strong>유효기간</strong></label>	
				<div class="form-group col-5">
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" id="periodic" name="coupon_duedate" class="custom-control-input" value="periodic" checked>
						<label class="custom-control-label" for="periodic">제한</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
					<input type="radio" id="permament" name="coupon_duedate" class="custom-control-input" value="permament">
					<label class="custom-control-label" for="permament">무제한</label>								
					</div>
				</div>								
				<div class="col-4">
					<input class="form-control" type="date" id="coupon_duedate"/>		
				</div>	
			</div>													
		</form>	

		<form id="pointForm">			
			<div class="form-row justify-content-center">
				<label for="pointQty"><strong>지급할 포인트</strong></label>	
				<div class="col-3">
					<input class="form-control" type="number" name="pointQty"/>		
				</div>	
				<div class="col-3">
					<select id="pointQty" class="form-control">
						<option value="">직접입력</option>
						<option value="1000">1,000</option>
						<option value="2000">2,000</option>
						<option value="3000">3,000</option>
						<option value="5000">5,000</option>
						<option value="10000">10,000</option>																								   
					</select>			
				 </div>	
				<div class="col-1">[점]</div>				 																										
			</div>								
		</form>	
		
		<div class="form-row justify-content-center">
			<div class="col-10">
				<label for="subject"><strong>메일 알림 제목</strong></label>
				<input type="text" class="form-control" id="subject"/>	
			</div>			
		</div>			
		<div class="form-row justify-content-center">
			<div class="col-10" >
				<label for="editor_admin"><strong>메일 알림 내용</strong></label>
				<textarea class="form-control" id="editor_admin"></textarea>						
			</div>			
		</div>		
		<div class="form-row">
			<div class="form-group col" align="right">     
				<input type="button" class="btn btn-outline-success" id="couponIssue" value="쿠폰지급" disabled/>
				<input type="button" class="btn btn-outline-primary" id="pointGrant" value="포인트지급" disabled/>	
				<input type="button" class="btn btn-outline-secondary" id="resetBtn" value="새로고침"/>     	
				<button type="button" id="closeBtn" class="btn btn-outline-dark">닫기</button>							
			</div>
		</div>	
	</div>	

</div>
	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/general/ckeditor4Admin.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/benefitGivingForm.js"></script>
	<!--JavaScript Local LINK:END-->