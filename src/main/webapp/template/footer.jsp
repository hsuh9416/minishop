<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    <!-- 일부  사용된 template에 대한 저작권 표시 -->
	<footer class="footer">
		<div class="container">
		
			<div class="row">
			 <c:if test="${adminDTO == null}">			
				<div class="col-lg-4">
					<div class="footer_nav_container d-flex flex-sm-row flex-column align-items-center justify-content-lg-start justify-content-center text-center">
						<ul class="footer_nav">
							<li><a href="/minishop/board/review/reviewList.do">Reviews</a></li>
							<li><a href="/minishop/board/qa/qaList.do">QAs</a></li>
							<li><a href="/minishop/main/userContact.do">Contact us</a></li>
						</ul>
					</div>
				</div>   
			</c:if>   				
	        </div>

	        
			<div class="row">
				<div class="col-lg-12">
					<div class="footer_nav_container">
						<div class="cr">©2018 All Rights Reserverd. Made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="#">Colorlib</a> &amp; distributed by <a href="https://themewagon.com">ThemeWagon</a></div>
					</div>
				</div>
			</div>
		</div>
	</footer>