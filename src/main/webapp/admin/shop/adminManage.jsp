<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<!--Bootsrap 4-->
	<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	
    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="/mallproject/resources/fontawesome-free-5.9.0-web/css/all.css">	
	
	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="/mallproject/css/admin.css">

 <div class="memberView-container">
 	<div class="container">
 	<!-- 실행 메뉴 -->
	 <nav aria-label="breadcrumb">
	  <ol class="breadcrumb">
	    <li class="breadcrumb-item active" aria-current="page">관리자 정보</li>     	    
	  </ol>
	</nav>

	<!-- 관리자 정보 화면 -->
		<div class="table-resposive">
			<table id="boardTable" class="table justify-content-center">
			  <thead class="thead-light">
			    <tr>
					<th scope="col" colspan="4">관리자 및 상점 정보 관리</th>
			  	</tr>
			   </thead>  
			   <tbody>
			   	<tr>
			   		<td rowspan="7" >
			   			<figure class="figure">
  							<img src="/mallproject/image/cat4.gif" class="figure-img img-fluid rounded" alt="...">
						</figure>
					</td>
			   			<td colspan="3">ADMINISTER ID : 
			   			<c:if test="${adminDTO.admin_id!=null}">
			   				<strong>${adminDTO.admin_id}</strong>			   			
			   			</c:if>	
			   		</td>					
			   	</tr>
			   	<tr>
			   		<td>답변 대기중인 문의게시글 : </td>
			   		<td colspan="2"><a></a></td>
			   	</tr>			   	
			   	<tr>
			   		<td>관리자 이메일 계정 :</td>
			   		<td colspan="2">shu2380@naver.com</td>
			   	</tr>
			   	<tr>
			   		<td>관리자 연락처 계정 :</td>
			   		<td colspan="2">010-3231-5839</td>
			   	</tr>
			   	<tr>
			   		<td>상점 개설일 : </td>
			   		<td colspan="2">
			   			2016년 1월 1일
			   		</td>
			   	</tr>	
			   	<tr>
			   		<td colspan="3" align="right">
			   			<input type="button" id="changeAdminInfo" class="btn btn-outline-dark" value="관리자 비밀번호 변경"/>		   			
			   		</td>
			   	</tr>				   			   				   	
			   </tbody> 	  
			</table>
		</div>
	</div>
</div>

	<!-- model frame -->

	

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">

</script>