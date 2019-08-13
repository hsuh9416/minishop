<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   	<!--CSS Local LINK:START-->
<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/fontawesome5/css/all.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/popup.css">
 	<!--CSS Local LINK:END-->

 <div class="container">       
 	<div class="row" id="titleDiv">
 		<div class="col" align="center">
 			<h2 class="first">[Kissin'BUGS] 거래 약관</h2>
 		</div>
	</div>
		<div class="card card-body">	

			<div class="form-row justify-content-center">	
				<div class="form-group col">
					<textarea style="width:100%;height:400px;">
						<jsp:include page="/resources/text/shop_policy.txt"></jsp:include>
					</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col" align="right">          	
					<button type="button" id="closeBtn" class="btn btn-outline-info">닫기</button>			
				</div>
			</div>			
		</div>					
</div>

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/main/innerMain.js"></script>
	<!--JavaScript Local LINK:END-->