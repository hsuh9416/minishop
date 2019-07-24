<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <!--Made with love by Mutiullah Samim -->
   
	<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/member.css">
	
<!-- Modal 출연 구역 -->
  <div class="modal fade" id="deleteModal" role="dialog">
    <div class="modal-dialog">
<!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <div class="title_logo">
			<a><span>회원 탈퇴</span></a>
		  </div>
        </div>
        <div class="modal-body">
		   <div class="form-group">
		    <label for="dReturnAddr">회신받으실 주소</label>
		    <input type="email" class="form-control" id="dReturnAddr" value="${memberDTO.email1}@${memberDTO.email2}">
		  </div>
		  <div class="form-group">
		    <label for="dReason">탈퇴 사유</label>
		    <select class="form-control" id="dReason">
		      <option value="1">1.개인 정보 유출이 염려됨</option>
		      <option value="2">2.이용이 불편함</option>
		      <option value="3">3.더이상 이용하지 않음</option>
		      <option value="4">4.기타</option>
		    </select>
		  </div>       
		  <div class="form-group">
		    <label for="etcDetail">기타 문의 사항</label>
		    <textarea class="form-control" id="etcDetail" rows="2"></textarea>
		  </div>
		  <div class="form-group">
		    <label for="deletePwd">계정 비밀번호</label>
		    <input type="password" class="form-control" id="deletePwd" placeholder="계정 비밀번호를 입력하세요."/> 
		  </div>
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">          								
					<button type="button" id="deleteBtn" class="btn btn-dark">회원 탈퇴</button>			
				</div>
			</div>	
		</div>	
      </div>
        <div class="modal-footer">
        	<div class="row">
				<div class="col" align="right">          									
         			<button type="button" class="btn btn-dark" data-dismiss="modal">닫기</button>
				</div>
         	</div>	
        </div>       
      </div>
     </div>
</div>
	<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script type="text/javascript"  src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/minishop/resources/custom/js/innerMain.js"></script>
