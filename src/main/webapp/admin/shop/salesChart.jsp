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
 			<h2 class="first">[매출 분석]</h2>		
 		</div>
	</div>	
	<div class="card card-body">
	<div class="form-row justify-content-center">
		<h4>[분석조건]</h4>
	</div>	
	<div class="form-row justify-content-center">
		<p style="color:black;">[검색 조건 : <font id="searchOption"></font>] [검색 키워드 :<font id="keyword"></font>]</p>
	</div>
	<hr style="color:lightgrey;width:100%;">
	
	<div class="form-row justify-content-center">
		<h4>[차트 분석]</h4>
	</div>
	<div class="form-row justify-content-center" id="pieChart_div">
		<!-- 카테고리별 매출액  비중-->
		<div class="col-6" id="pieChart_category_div"></div>	
		<!-- 지급방법별 매출액 비중 -->
		<div class="col-6" id="pieChart_payment_div"></div>

	</div>
	<!-- 주문번호, 주문일자로는 보이지 않는 구역 -->
	<div class="form-row justify-content-center" id="lineChart_div">
			<!--월별 매출액(기간별 비중도 표현)-->
		<div class="col" id="lineChartM_div"></div>
	</div>	
	
	<hr style="color:lightgrey;width:100%;">
	
	<div class="form-row justify-content-center">
		<h4>[단위 분석]</h4>
	</div>
	<!-- 공통 출현 -->
	<div class="form-row justify-content-center">
		<div class="col-2">
			<label>최고매출상품</label>
		</div>
		<div class="col-4"></div>
		<div class="col-2">
			<label>최다판매상품</label>
		</div>		
		<div class="col-4"></div>					
	</div>
	<div class="form-row justify-content-center">
		<div class="col-2">
			<label>최저매출상품</label>
		</div>
		<div class="col-4"></div>
		<div class="col-2">
			<label>최소판매상품</label>
		</div>		
		<div class="col-4"></div>					
	</div>	
	
	<!-- 주문번호,주문자 선택시 조회 안됨 -->		
	<div class="form-row justify-content-center" id="order_div">
		<div class="col-2">
			<label>최다주문회원</label>
		</div>
		<div class="col-2"></div>
		<div class="col-2">
			<h6>최고기여회원</h6>
		</div>		
		<div class="col-2"></div>	
		<div class="col-2">
			<label>비회원주문비율</label>
		</div>		
		<div class="col-2"></div>						
	</div>
	<!-- 주문번호,매출일자 선택시 조회 안됨 -->	
	<div class="form-row justify-content-center" id="sales_div">
		<div class="col-2">
			<label>최고매출주문</label>
		</div>
		<div class="col-4"></div>
		<div class="col-2">
			<label>최고매출일자</label>
		</div>		
		<div class="col-4"></div>					
	</div>
			
	<div class="form-row">
		<div class="form-group col" align="right">     	
			<input type="button" class="btn btn-outline-secondary" id="resetBtn" value="새로고침"/>     	
			<button type="button" id="closeBtn" class="btn btn-outline-dark">닫기</button>							
		</div>
	</div>	
		
	</div>	
		


</div>
	
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/adminShop/salesChart.js"></script>
	<!--JavaScript Local LINK:END-->