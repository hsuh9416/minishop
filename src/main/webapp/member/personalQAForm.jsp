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
	<div id="title" class="row justify-content-center">
		<h2 class="first">Kissin'</h2><h2 class="second">Bugs</h2>
	</div>
	<div class="row justify-content-center">
		<div class="col" align="center">
			<a id="member" href="#"><i class="fas fa-id-card">1:1 문의 사항</i></a>
		</div>		
	</div>
	<div class="row justify-content-center">
		<div class="card card-body">
			<div class="form-row">
				<div class="form-group col">
			    	<label for="QAreturnAddr">답변을 받으실 주소</label>
			    	<c:if test="${memberDTO!=null}">
			    	<input type="email" class="form-control" id="QAreturnAddr" value="${memberDTO.email1}@${memberDTO.email2}" data-toggle="tooltip" data-placement="bottom" title="회신받으실 [메일 주소]를 입력하세요.">				    	
			    	</c:if>
			    	<c:if test="${guestDTO!=null}">
			    	<input type="email" class="form-control" id="QAreturnAddr" value="${guestDTO.guest_email}" data-toggle="tooltip" data-placement="bottom" title="회신받으실 [메일 주소]를 입력하세요.">				    	
			    	</c:if>
			    	<c:if test="${memberDTO==null&&guestDTO==null}">
			    	<input type="email" class="form-control" id="QAreturnAddr" value="" data-toggle="tooltip" data-placement="bottom" title="회신받으실 [메일 주소]를 입력하세요.">				    	
			    	</c:if>			    			
				</div>
		    </div>
			<div class="form-row">
				<div class="form-group col">
					<label for="QAtype">문의하실 사항</label>
				    <select class="form-control" id="QAtype">
				      <option>1.입금/결제 관련</option>
				      <option>2.상품 관련</option>
				      <option>3.주문/배송 관련</option>
				      <option>4.기타</option>
				    </select>
				</div>
		    </div>
		          
			<div class="form-row">
				<div class="form-group col">
				    <label for="QAdetail">문의하실 내용</label>
				    <textarea class="form-control" id="QAdetail" rows="3" data-toggle="tooltip" data-placement="bottom" title="[문의하실 내용]은 필수 입력 사항입니다."></textarea>
				</div>
		    </div>
			<div class="form-row">
				<div class="form-group col">        								
					<button type="button" id="sendQABtn" class="btn btn-block btn-dark">보내기</button>			
				</div>
			</div>				
		</div>
	</div>	
</div> 	

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/personalQAForm.js"></script>
  	<!--JavaScript Local LINK:END-->


    