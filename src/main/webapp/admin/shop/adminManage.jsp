<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/admin.css">

	<div class="col-lg-8">
	<!-- 관리자 정보 화면 -->
	 	<div class="row" id="titleDiv">
	 		<div class="col" align="center" style="padding-bottom: 20px;">
	 			<h3>관리자 및 상점 정보 관리</h3>		
	 		</div>
		</div>	  	
		<div class="table-resposive">
			<table id="boardTable" class="table justify-content-center">
			  <thead class="thead">
			  		<tr align="center"><td colspan="3" style="font-size:18pt;">ADMIN INFO</td></tr>
			  </thead>  
			   <tbody>
			   	<tr>
			   		<td rowspan="5" >
			   			<figure class="figure">
  							<img src="/minishop/resources/image/gif/2.gif" class="figure-img img-fluid rounded" alt="">
						</figure>
					</td>
			   			<td colspan="3">ADMIN ID : 
			   			<c:if test="${adminDTO.admin_id!=null}">
			   				<strong>${adminDTO.admin_id}</strong>			   			
			   			</c:if>	
			   		</td>					
			   	</tr>
			   	<tr>
			   		<td>Unread QAs : </td>
			   		<td colspan="2"><a></a></td>
			   	</tr>			   	
			   	<tr>
			   		<td>ADMIN EMAIL :</td>
			   		<td colspan="2">shu2380@naver.com</td>
			   	</tr>
			   	<tr>
			   		<td>ADMIN TEL :</td>
			   		<td colspan="2">010-3231-5839</td>
			   	</tr>
			   	<tr>
			   		<td>SHOP OPENED : </td>
			   		<td colspan="2">
			   			2016년 1월 1일
			   		</td>
			   	</tr>	
			   	<tr>
			   		<td colspan="3" align="right">
			   			<input type="button" id="changeAdminInfo" class="btn btn-outline-dark" value="관리자 정보 변경"/>		   			
			   		</td>
			   	</tr>				   			   				   	
			   </tbody> 	  
			</table>
		</div>	
	</div>

	

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/bootstrap4/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
$('#changeAdminInfo').click(function(){
	var realConfirm = confirm('관리자 정보를 변경합니까?');
	if(realConfirm){alert('대충 변경하는 짤');}
	
});
</script>