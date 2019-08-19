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
		<div class="form-row ">	
			<div class="col-2" align="center">
				<label for="id" style="text-decoration:underline;text-underline-position:under"><strong>회원아이디</strong></label>
			</div>	
			<div class="col-2" align="center">
				<font id="id"></font>
			</div>				  
			<div class="col-2" align="center">
				<label for="name" style="text-decoration:underline;text-underline-position:under"><strong>회원명</strong></label>
		  	</div>	
			<div class="col-2">
				<font id="name"></font>
			</div>				  			  	
			<div class="col-2" align="center">
				<label for="rank" style="text-decoration:underline;text-underline-position:under"><strong>회원등급</strong></label>
		  	</div>	
			<div class="col-2" align="center">
				<font id="rank"></font>
			</div>					  				  	
		</div>		
			
		<div class="form-row ">	
			<div class="col-2" align="center">
				<label for="email" style="text-decoration:underline;text-underline-position:under"><strong>회원이메일</strong></label>
			</div>	
			<div class="col-4" align="center">
				<font id="email"></font>
			</div>
			<div class="col-2" align="center">
				<label for="tel" style="text-decoration:underline;text-underline-position:under"><strong>회원연락처</strong></label>
			</div>	
			<div class="col-4" align="center">
				<font id="tel"></font>
			</div>								  	
		</div>	
		<div class="form-row justify-content-center">	
			<div class="col-2" align="center">
				<label for="address1" style="text-decoration:underline;text-underline-position:under"><strong>회원주소</strong></label>
			</div>	
			<div class="col-10" id="address1"></div>
		</div>			
		<div class="form-row justify-content-center">	
			<div class="col-2">
				<label for="address2"><strong></strong></label>
			</div>	
			<div class="col-10" id="address2"></div>
		</div>
		<div class="form-row">	
			<div class="col-2" align="center">
				<label for="point" style="text-decoration:underline;text-underline-position:under"><strong>포인트</strong></label>
			</div>	
			<div class="col-4" align="center">
				<font id="point"></font>
			</div>
			<div class="col-2" align="center">
				<label for="registerDate" style="text-decoration:underline;text-underline-position:under"><strong>가입일자</strong></label>
			</div>	
			<div class="col-4" align="center">
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
										<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">#</div>
										<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">주문번호</div>
										<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">거래일자</div>
										<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">거래금액</div>
										<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">거래상태</div>																																		
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
										<div class="col-2" align="center" style="text-decoration:underline;text-underline-position:under">#</div>
										<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">이름</div>
										<div class="col-1" align="center" style="text-decoration:underline;text-underline-position:under">분류</div>
										<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">할인액(률)</div>
										<div class="col-3" align="center" style="text-decoration:underline;text-underline-position:under">유효기간</div>																																		
									</div>
									<form id="couponForm"></form>
								</div>					
							</div>			    			
					</div>
					
				</div>			
			</div>
		</div>
		<form id="requestedDelete">
			<h5 class="deleteTitle" align="center" style="color:red;text-align:center;text-decoration:underline;text-underline-position:under">
				[탈퇴 및 계정 삭제 요청을 한 회원입니다]
			</h5>
			<div class="form-row justify-content-center">
				<div class="form-group col-3" style="text-decoration:underline;text-underline-position:under">
					<strong>회신메일</strong>
				</div>
				<div class="form-group col-8" id="dReturnAddr"></div>
			</div>					
			<div class="form-row justify-content-center">
				<div class="form-group col-3" style="text-decoration:underline;text-underline-position:under">
					<strong>탈퇴사유</strong>
				</div>
				<div class="form-group col-8" id="dReason"></div>				
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-10" align="center" style="text-decoration:underline;text-underline-position:under">
					<strong>상세사유</strong>
				</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-10" id="dDetail" align="center"></div>
			</div>							
			<div class="form-row justify-content-center">
				<div class="form-group col-3" style="text-decoration:underline;text-underline-position:under">
					<strong>탈퇴요청일</strong>
				</div>
				<div class="form-group col-8" id="requestDate"></div>
			</div>			
		</form>		
		<div class="row">
			<div class="col" align="right">          	
				<button type="button" id="memberRestoreBtn" class="btn btn-outline-success" disabled>계정복구</button>
				<button type="button" id="benefitGivingBtn" class="btn btn-outline-primary">혜택지급</button>
				<button type="button" id="infoSendingBtn" class="btn btn-outline-info">메일발신</button>		
				<button type="button" id="memberDeleteBtn" class="btn btn-outline-danger" disabled>계정삭제</button>
				<button type="button" id="closeBtn" class="btn btn-outline-secondary">닫기</button>							
			</div>
		</div>			
	</div>	

</div>
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminUser/memberDetailView.js"></script>
	<!--JavaScript Local LINK:END-->