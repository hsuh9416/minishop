<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="col-lg-8">	
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>상품 주문서</h3>		
	 		</div>
		</div>

</div>
<script type="text/javascript"  src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var result = ${orderList_JSON};
	alert(JSON.stringify(result));
});
</script>