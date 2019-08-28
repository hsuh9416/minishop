<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header -->
<c:if test="${adminDTO == null}">
	<header class="header trans_300">

		<!-- Top Navigation : 최상단 고정 메뉴바 -->
		<div class="top_nav">
			<div class="container">
				<div class="row">
					<div class="col-md-6">
						<div class="top_nav_left">KISSIN'S BUGS WILL FIND U OUT!</div>
					</div>
					<div class="col-md-6 text-right">
						<div class="top_nav_right">
							<ul class="top_nav_menu">
								<!--  My Account -->
								<li class="account">
									<a href="#">
										<c:if test="${memberDTO == null&&guestDTO== null}">My Account</c:if>
										<c:if test="${memberDTO != null}">${memberDTO.name} 님</c:if>
										<c:if test="${guestDTO != null}">${guestDTO.guest_name} 님</c:if>
										<i class="fa fa-angle-down"></i>
									</a>
									<ul class="account_selection">
										<c:if test="${memberDTO == null&&guestDTO== null}">
											<li><a id="goLogin">Sign In</a></li>
											<li><a id="goSignUp">Register</a></li>										
										</c:if>
										<c:if test="${memberDTO != null||guestDTO != null}">
											<li><a id="goLogout" href="#">SignOut</a></li>
											<li><a id="goUserView" href="/minishop/member/memberView.do">My Info</a></li>																					
										</c:if>										
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Main Navigation : 유동 메뉴바-->	
		<div class="main_nav_container">
			<div class="container">
				<div class="row">
					<div class="col-lg-12 text-right">
						<div class="logo_container">
							<a href="/minishop/main/home.do">Kissin'<span>BUGs</span></a>
						</div>
						<nav class="navbar">
							<ul class="navbar_menu">
								<li><a href="/minishop/main/introduce.do">Kissin' Bugs</a></li>
								<li><a href="/minishop/main/home.do">메인</a></li>
								<li><a href="/minishop/product/categories.do">카테고리</a></li>
								<li><a href="/minishop/board/review/reviewList.do">고객게시판</a></li>
								<li><a href="/minishop/main/userContact.do">찾아오시는 길</a></li>
							</ul>
							<ul class="navbar_user">
								<li><a href="#"><i class="fa fa-search" aria-hidden="true"></i></a></li>
								<li><a href="/minishop/member/memberView.do"><i class="fa fa-user" aria-hidden="true"></i></a></li>
								<li class="checkout">
									<a href="#" id="goCart">
										<i class="fa fa-shopping-cart" aria-hidden="true"></i>
										<span id="checkout_items" class="checkout_items">
											<c:if test="${cartList==null}">0</c:if>
											<c:if test="${cartList!=null}">${cartList.size()}</c:if>											
										</span>
									</a>
								</li>
							</ul>
							<div class="hamburger_container">
								<i class="fa fa-bars" aria-hidden="true"></i>
							</div>
						</nav>
					</div>
				</div>
			</div>
		</div>

	</header>

	<div class="fs_menu_overlay"></div>
	<div class="hamburger_menu">
		<div class="hamburger_close"><i class="fa fa-times" aria-hidden="true"></i></div>
		<div class="hamburger_menu_content text-right">
			<ul class="menu_top_nav">
				<li class="menu_item has-children">
					<a href="#">
						<c:if test="${memberDTO == null&&guestDTO == null}">My Account</c:if>
						<c:if test="${memberDTO != null}">${memberDTO.name} 님</c:if>
						<c:if test="${guestDTO != null}">${guestDTO.guest_name} 님</c:if>
						<i class="fa fa-angle-down"></i>
					</a>
					<ul class="menu_selection">
						<c:if test="${memberDTO == null&&guestDTO == null}">
							<li><a id="mgoLogin">Sign In</a></li>
							<li><a id="mgoSignUp">Register</a></li>
						</c:if>
						<c:if test="${memberDTO != null||guestDTO != null}">
							<li><a id="mgoLogout" href="#">Sign Out</a></li>
							<li><a id="mgoUserView" href="/minishop/member/memberView.do">My Info</a></li>														
						</c:if>
					</ul>
				</li>
				<li class="menu_item"><a href="/minishop/main/introduce.do">Kissin' Bugs</a></li>
				<li class="menu_item"><a href="/minishop/main/home.do">메인</a></li>
				<li class="menu_item has-children">
					<a href="#">
						OUR PRODUCTS
						<i class="fa fa-angle-down"></i>						
					</a>
					<ul class="menu_selection">
						<li><a href="/minishop/product/categories.do">ALL</a></li>
						<li><a href="/minishop/product/eventProductList.do">SPECIAL</a></li>														
					</ul>						
				</li>
				<li class="menu_item has-children">
					<a href="#">
						고객게시판
						<i class="fa fa-angle-down"></i>
					</a>
					<ul class="menu_selection">
						<li><a href="/minishop/board/qa/qaList.do">문의게시판</a></li>
						<li><a href="/minishop/board/review/reviewList.do">후기게시판</a></li>														
					</ul>				
				</li>
				<li class="menu_item"><a href="/minishop/main/userContact.do">찾아오시는 길</a></li>
			</ul>
		</div>
	</div>
</c:if>
<c:if test="${adminDTO != null}">
	<jsp:include page="/template/adminHeader.jsp"/>	
</c:if>	