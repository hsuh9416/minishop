<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap4/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/custom/css/member.css">

<!-- Modal 출연 구역 -->
  <div class="modal fade" id="PQAModal" role="dialog">
    <div class="modal-dialog">
<!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <div class="title_logo">
			<a><span>1:1 문의 사항</span></a>
		  </div>
        </div>
        <div class="modal-body">
		   <div class="form-group">
		    <label for="QAreturnAddr">답변을 받으실 주소</label>
		    <input type="email" class="form-control" id="QAreturnAddr" value="${memberDTO.email1}@${memberDTO.email2}" data-toggle="tooltip" data-placement="bottom" title="회신받으실 [메일 주소]를 입력하세요.">
		  </div>
		  <div class="form-group">
		    <label for="QAtype">문의하실 사항</label>
		    <select class="form-control" id="QAtype">
		      <option>1.입금/결제 관련</option>
		      <option>2.상품 관련</option>
		      <option>3.주문/배송 관련</option>
		      <option>4.기타</option>
		    </select>
		  </div>       
		  <div class="form-group">
		    <label for="QAdetail">문의하실 내용</label>
		    <textarea class="form-control" id="QAdetail" rows="3" data-toggle="tooltip" data-placement="bottom" title="[문의하실 내용]은 필수 입력 사항입니다."></textarea>
		  </div>
		<div class="form-group">
			<div class="row">
				<div class="col" align="center">          								
					<button type="button" id="sendQABtn" class="btn btn-dark">보내기</button>			
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
	<script type="text/javascript"  src="/mallproject/resources/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript"  src="/mallproject/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/mallproject/resources/custom/js/innerMain.js"></script>


    