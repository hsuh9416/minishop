<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
	<!--CSS Local LINK:START-->        
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">   
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">	 
	<!--CSS Local LINK:END-->

<div class="container">       

 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h2 class="first">[회원 상세 정보]</h2>		
 		</div>
	</div>

	<input type="hidden" id="memberID" value="${id}"/>
	<div class="card card-body">	
		<div class="form-row justify-content-center">	
			<div class="form-group col-2" align="center">
				<label for="id"><strong>회원아이디</strong></label>
			</div>	
			<div class="form-group col-2" align="center">
				<font id="id"></font>
			</div>				  
			<div class="form-group col-2" align="center">
				<label for="name"><strong>회원명</strong></label>
		  	</div>	
			<div class="form-group col-2">
				<font id="name"></font>
			</div>				  			  	
			<div class="form-group col-2" align="center">
				<label for="rank"><strong>회원등급</strong></label>
		  	</div>	
			<div class="form-group col-2" align="center">
				<font id="rank"></font>
			</div>					  				  	
		</div>		
			
		<div class="form-row justify-content-center">	
			<div class="form-group col-2" align="center">
				<label for="email"><strong>회원이메일</strong></label>
			</div>	
			<div class="form-group col-4" align="center">
				<font id="email"></font>
			</div>
			<div class="form-group col-2" align="center">
				<label for="tel"><strong>회원연락처</strong></label>
			</div>	
			<div class="form-group col-4" align="center">
				<font id="tel"></font>
			</div>								  	
		</div>	
		<div class="form-row justify-content-center">	
			<div class="form-group col-2" align="center">
				<label for="address1"><strong>회원주소</strong></label>
			</div>	
			<div class="form-group col-10">
				<font id="address1"></font>
			</div>
		</div>			
		<div class="form-row justify-content-center">	
			<div class="form-group col-2">
				<label for="address2"><strong></strong></label>
			</div>	
			<div class="form-group col-10">
				<font id="address2"></font>
			</div>
		</div>
		<div class="form-row justify-content-center">	
			<div class="form-group col-2" align="center">
				<label for="point"><strong>포인트</strong></label>
			</div>	
			<div class="form-group col-4" align="center">
				<font id="point"></font>
			</div>
			<div class="form-group col-2" align="center">
				<label for="registerDate"><strong>가입일자</strong></label>
			</div>	
			<div class="form-group col-4" align="center">
				<font id="registerDate"></font>
			</div>				
		</div>
		<div class="form-row justify-content-center">
			<div class="form-group col">
				<div class="accordion" id="userTrading">
						<div class="card">
							<div class="card-header" id="orderDiv">
								<h2 class="mb-0">
									<button class="btn btn-link btn-block" type="button" data-toggle="collapse" data-target="#userOrder" aria-expanded="true" aria-controls="userOrder">
					          			회원 주문/거래 현황
					        		</button>
				      	  		</h2>
			    			</div>
							<div id="userOrder" class="collapse" aria-labelledby="orderDiv" data-parent="#userTrading">
								<div class="card-body">
									<div class="form-row justify-content-center">
										<div class="col-2" align="center">#</div>
										<div class="col-2" align="center">주문번호</div>
										<div class="col-3" align="center">거래일자</div>
										<div class="col-3" align="center">거래금액</div>
										<div class="col-2" align="center">거래상태</div>																																		
									</div>
									<form id="orderForm"></form>
								</div>
							</div>	
						</div>
					<div class="card">		    			
							<div class="card-header" id="couponDiv">
								<h2 class="mb-0">
									<button class="btn btn-link btn-block" type="button" data-toggle="collapse" data-target="#userCoupon" aria-expanded="true" aria-controls="userCoupon">
					          			회원 쿠폰 보유 현황
					        		</button>
				      	  		</h2>
			    			</div>		    			
							<div id="userCoupon" class="collapse" aria-labelledby="couponDiv" data-parent="#userTrading">
								<div class="card-body">
									<div class="form-row justify-content-center">
										<div class="col-2" align="center">발행번호</div>
										<div class="col-2" align="center">쿠폰이름</div>
										<div class="col-2" align="center">쿠폰분류</div>
										<div class="col-3" align="center">할인액(률)</div>
										<div class="col-3" align="center">유효기간</div>																																		
									</div>
									<form id="couponForm"></form>
								</div>					
							</div>			    			
					</div>
					
				</div>			
			</div>
		</div>

				
		<div class="row">
			<div class="col" align="right">          	
				<button type="button" id="resetBtn" class="btn btn-outline-primary">포인트지급</button>
				<button type="button" id="resetBtn" class="btn btn-outline-info">쿠폰지급</button>		
				<button type="button" id="memberDeleteBtn" class="btn btn-outline-danger" disabled>계정삭제</button>			
			</div>
		</div>			
	</div>	

</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin/memberDetailView.js"></script>
	<!--JavaScript Local LINK:END-->