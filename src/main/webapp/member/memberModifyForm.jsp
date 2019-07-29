<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  


<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
    
	<!-- 회원 정보 수정 -->
	<div class="col-lg-8">
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>회원 정보 수정</h3>		
	 		</div>
		</div>	
		
<form id="modifyForm">	
   <div class="form-row">	
 	  <div class="form-group col-md-3">
	    <label for="formGroupExampleInput"><strong>아이디</strong></label>
	    <input type="text" readonly class="form-control-plaintext" name="id" id="id" value="${memberDTO.id}">
	  </div>	  
	  <div class="form-group col-md-3">
	    <label for="formGroupExampleInput"><strong>이름</strong></label>
	    <input type="text" class="form-control" name="name" id="name" value="${memberDTO.name}" data-toggle="tooltip" data-placement="right" title="회원님의 이름을 입력하세요.">
	  </div>	
   </div>	

   <div class="form-row">
	    <div class="form-group col-md-3">
	      <label for="inputEmail4"><strong>비밀번호</strong></label>
	      <input type="password" class="form-control" name="pwd" id="pwd" value="${memberDTO.pwd}" data-toggle="tooltip" data-placement="right" title="비밀번호를 입력하세요." readonly>
	    </div>
	    <div class="form-group col-md-3">
	      <label for="inputPassword4"><strong>재입력</strong></label>
	      <input type="password" class="form-control" name="repwd" id="repwd" placeholder="비밀번호 재입력 " data-toggle="tooltip" data-placement="right" title="비밀번호가 일치하지 않습니다.">
	    </div>
	   <div class="form-group col-md-3" style="padding-top:30px;">
	    <input type="button" class="btn btn-outline-info" value="비밀번호 변경" id="pwdChangeBtn"/>
	   </div>
	   <div id="pwdDiv"></div>
	   <input type="hidden" id="checkPwd" value=""/>
   </div>



  <label for="email"><strong>이메일 주소</strong></label>	
  <div class="form-row" id="email">
    <div class="form-group col-md-2">
      <input type="text" class="form-control" id="email1" name="email1" value="" readonly>
    </div>
	<span>@</span>  
    <div class="form-group col-md-3">
      	<input type="text" class="form-control" name="email2" id="email2" readonly>
    </div>
    <div class="form-group col-md-3">
      <select id="emailInput" name="email2" class="form-control" disabled>
		<option value="">직접 입력</option>   
		<option value="gmail.com">gmail.com</option>
		<option value="hanmail.net">hanmail.net</option>
		<option value="naver.com">naver.com</option>
      </select>
    </div>	
    <div class="form-group col-md-3">
      	<input type="button" class="btn btn-outline-info" value="이메일 변경" id="changeEmailBtn"/>
    </div>      
  </div>	

   <label for="tel"><strong>연락처</strong></label>	
	  <div class="form-row" id="tel">
	    <div class="form-group col-md-2">
	      <select name="tel1" id="tel1" class="form-control">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="019">019</option>
	      </select>
	    </div>	    
	    <span>-</span>  
	    <div class="form-group col-md-2">
	      <input type="text" class="form-control" id="tel2" name="tel2" value="${memberDTO.tel2}">
	    </div>
	    <span>-</span>     
	    <div class="form-group col-md-2">
	      	<input type="text" class="form-control" name="tel3" id="tel3" value="${memberDTO.tel3}">
	    </div>
	  </div>
  
   <label for="address1"><strong>우편 번호</strong></label>	
 	 <div class="form-row" id="address1">
	    <div class="form-group col-md-3">
			<input type="text" readonly class="form-control" name="zipcode" id="zipcode" value="${memberDTO.zipcode }"> 
	    </div>
	    <div class="form-group col-md-3">
			<input type="button" class="btn btn-outline-dark" value="우편번호검색" id="checkPost" onclick="sample3_execDaumPostcode()">
	    </div>
  	</div>	

   <label for="address2"><strong>상세 주소</strong></label>	
 	 <div class="form-row" id="address2">
	    <div class="form-group col-6">
	    	<input type="text" readonly class="form-control" name="addr1" id="addr1" value="${memberDTO.addr1 }">
	    </div>
	   	<div class="form-group col-4">
	    	<input type="text" readonly class="form-control" name="extra" id="extra" value="">
	    </div>

  	</div>	
   	<div id="wrap">
  		<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" onclick="foldDaumPostcode()" alt="접기 버튼">
  	</div> 	 
  	 <div class="form-row" id="address2">
	    <div class="form-group col-10">
			<input type="text" class="form-control" name="addr2" id="addr2" value="${memberDTO.addr2}">
	    </div>
  	 </div>	 	

  	
	<div class="form-group">
		<div class="row">
			<div class="col" align="center">      
				<input type="button" value="회원정보수정" class="btn btn-outline-dark" id="modifyBtn">
				<input type="button" value="다시작성" class="btn btn-outline-dark" id="resetBtn">  				    									
			</div>
		</div>	
	</div>	  		
 </form>			
</div>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member.post.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member.modify.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#email1').val('${memberDTO.email1}');	
	$('#email2').val('${memberDTO.email2}');
	$('#tel1').val('${memberDTO.tel1}');
	$('#wrap').hide();
});//ready
</script>










