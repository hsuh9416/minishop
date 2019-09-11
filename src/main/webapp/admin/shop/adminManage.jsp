<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<!--CSS Local LINK:START--> 
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/admin.css">
	<!--CSS Local LINK:END-->

<div class="col-lg-8">
	<div class="row" id="titleDiv">
		<div class="col">
			<h3>관리자 및 상점 정보 관리</h3>		
		</div>
	</div>  
	
	<div class="form-row justify-content-center">	
		<div class="form-group col" align="center">
			<strong>KISSIN' BUGS MAIN INFO</strong>
		</div>
	</div>		 	

	<div class="form-row justify-content-center">	
		<div class="form-group col-4">
			<figure class="figure">
  				<img src="/minishop/resources/image/gif/2.gif" class="figure-img img-fluid rounded" alt="">
			</figure>	
		</div>
		<div class="form-group col-8">
			<div class="form-row justify-content-center" style="border-bottom:1px solid darkgrey;">
				<div class="form-group col" align="center">[ADMIN INFO]</div>
			</div>
			<br>
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[접속 관리자 명]</div>
				<div class="form-group col-8">${memberDTO.name}</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[대표 이메일]</div>
				<div class="form-group col-8">${adminDTO.admin_email_addr}</div>
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[대표 번호]</div>
				<div class="form-group col-8">${adminDTO.admin_shop_tel}</div>
			</div>	
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[개설계좌]</div>
				<div class="form-group col-8">${adminDTO.admin_account}</div>
			</div>				
			<div class="form-row justify-content-center">
				<div class="form-group col-4">[개설일자]</div>
				<div class="form-group col-8">
					<fmt:formatDate value="${adminDTO.admin_opendate}" pattern="yyyy년 MM월 dd일"/> 
				</div>
			</div>															
		</div>				
	</div>	
</div>			

	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
	<!--JavaScript Local LINK:END-->