<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--CSS Local LINK:START--> 
<link rel="stylesheet" href="/minishop/resources/custom/css/userproduct.css">
	<!--CSS Local LINK:END-->
	
<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
				<h3>이벤트 관리</h3>
			</div>	
		</div>
		
	<form id="bannerManage" method="post" enctype="multipart/form-data" action="/minishop/admin/shop/bannerModify.do">
		<div class="form-row">
			<div class="col subTitleDiv"><h5>배너 관리</h5></div>								
		</div>
		
		<input type="hidden" id="event_no" value="${event_no}"/>
		<input type="hidden" name="event_image" value=""/>
		<div class="form-row">
			<div class="col-2">
				<label for="event_no">배너 번호</label>
				<select id="bannerNum" name="event_no" class="form-control">
					<option value="" selected>번호 선택</option>
					<option value="1">[1]</option>
					<option value="2">[2]</option>
					<option value="3">[3]</option>
				</select>
			</div>
		 	<div class="col-5">
		 		<label for="event_no">배너 명</label>
		  		<input type="text" class="form-control" name="event_name"/>
		  	</div>
			<div class="col-5">	
				<label for="event_no">배너 이미지</label>	 
	    		<input type="file" class="form-control-file" name="event_image_new"/>
    		</div>		  	
		 </div>
    	<div class="form-row">
			<div class="col-6">			
				<label for="event_no">이벤트 주소</label>
    			<input type="text" class="form-control" name="event_url" placeholder="/minishop/이벤트주소.do"/>
    		</div>
    		<div class="col-3">	
  			<label for="event_no">시작일자</label>
    			<input type="date" class="form-control" name="start_date"/>
    		</div>
    		<div class="col-3">	
  			<label for="event_no">종료일자</label>
    			<input type="date" class="form-control" name="end_date"/>
    		</div>    		
		</div>
    	<div class="form-row">
			<div class="col-8"></div>
    		<div class="col-4">	
    			<input type="button" id="resetForm" class="btn btn-outline-secondary" value="다시 작성">
				<input type="button" id="changeBannerBtn" class="btn btn-outline-info" value="수정 반영">
    		</div>
		</div>		
	</form>
	
	<form id="saleManage">
		<div class="form-row">
			<div class="form-group col-1">이벤트 번호</div>
			<div class="form-group col-3">이벤트 이름</div>
			<div class="form-group col-2">이벤트 분류</div>
			<div class="form-group col-3">이벤트 대상</div>
			<div class="form-group col-3">집행기간</div>									
		</div>
		<div class="form-row" id="saleDiv"></div>	
	</form>	
	<form id="couponManage">
		<div class="form-row">
			<div class="form-group col-1">발행 번호</div>
			<div class="form-group col-3">쿠폰 이름</div>
			<div class="form-group col-2">쿠폰 분류</div>
			<div class="form-group col-3">지급 대상</div>
			<div class="form-group col-3">집행기간</div>									
		</div>
		<div class="form-row" id="couponDiv"></div>			
	</form>
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/admin/admin.event.js"></script>
	<!--JavaScript Local LINK:END-->