<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
			<a id="member" href="#"><i class="fas fa-id-card">회원 탈퇴 요청</i></a>
		</div>		
	</div>
	<div class="row justify-content-center">
		<div class="card card-body">
			<div class="form-row">
				<div class="form-group col">
			    	<label for="dreturnAddr">답변을 받으실 주소</label>
			    	<input type="email" class="form-control" id="dreturnAddr" value="${memberDTO.email1}@${memberDTO.email2}" data-toggle="tooltip" data-placement="bottom" title="회신받으실 [메일 주소]를 입력하세요.">			
				</div>
		    </div>
		    
			<div class="form-row">
				<div class="form-group col">
				    <label for="dReason">탈퇴 사유</label>
				    <select class="form-control" id="dReason">
						<option value="1">1.개인 정보 유출이 염려됨</option>
						<option value="2">2.이용이 불편함</option>
						<option value="3">3.더이상 이용하지 않음</option>
						<option value="4">4.기타</option>
				    </select>		
				</div>
		    </div>

			<div class="form-row">
				<div class="form-group col">
				    <label for="etcDetail">기타 하실 말씀</label>
				    <textarea class="form-control" id="etcDetail" rows="2"></textarea>				
				</div>		    		    
		    </div>

			<div class="form-row">
				<div class="form-group col">
				    <label for="deletePwd">계정 비밀번호</label>
				    <input type="password" class="form-control" id="deletePwd" placeholder="계정 비밀번호를 입력하세요."/> 			
				</div>		    		    
		    </div>		    
			<div class="form-row">
				<div class="form-group col">        								
					<button type="button" id="deleteBtn" class="btn btn-block btn-dark">회원 탈퇴 요청</button>				
				</div>
			</div>				    	    					
		</div>
	</div>
</div>				

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript"  src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/member.view.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/member/memberDelete.js"></script>
  	<!--JavaScript Local LINK:END-->