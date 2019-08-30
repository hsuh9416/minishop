<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Header -->

	<header class="header trans_300">

		<!-- Top Navigation : 최상단 고정 메뉴바 -->

		<div class="top_nav">
			<div class="container">
				<div class="row">
					<div class="col-md-6">
						<div class="top_nav_left">Kissin' Bugs Administration</div>
					</div>
					<div class="col-md-6 text-right">
						<div class="top_nav_right">
							<ul class="top_nav_menu">
								<!--  My Account -->
								<li class="account">
									<a href="#">
										Admin Account
										<i class="fa fa-angle-down"></i>
									</a>
									<ul class="account_selection">
											<li><a id="goAdminLogout" href="/minishop/admin/adminLogout.do"><i class="fa fa-sign-out-alt" aria-hidden="true"></i>Sign Out</a></li>
											<li><a id="goAdminManage" href="/minishop/admin/shop/adminManage.do"><i class="fa fa-address-card" aria-hidden="true"></i>Admin Info</a></li>																					
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
							<a href="/minishop/admin/adminHome.do">Kissin'<span>BUGs</span></a>
						</div>
						<nav class="navbar">
							<ul class="navbar_menu">
								<li class="nav-item">
									<a href="/minishop/admin/adminHome.do">메인</a>
								</li>
								<li class="nav-item">
									<a href="/minishop/admin/shop/salesInfo.do">상점관리</a>
								</li>								
								<li class="nav-item">
							        <a href="/minishop/admin/product/inventoryManage.do">상품관리</a>														
								</li>
								<li class="nav-item">
									<a href="/minishop/admin/user/userManage.do">고객관리</a>
								</li>
								<li class="nav-item">
									<a href="/minishop/admin/order/orderManage.do">주문관리</a>
								</li>								
								<li class="nav-item">
									<a href="/minishop/admin/board/qaManage.do">문의글관리</a>
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
						Admin Account
						<i class="fa fa-angle-down"></i>
					</a>
					<ul class="menu_selection">
							<li><a id="goAdminLogout" href="#"><i class="fa fa-sign-out-alt" aria-hidden="true"></i>Sign Out</a></li>
							<li><a id="goAdminManage" href="/minishop/admin/shop/adminManage.do"><i class="fa fa-address-card" aria-hidden="true"></i>내 정보</a></li>														
					</ul>
				</li>
				<li class="menu_item"><a href="/minishop/admin/adminHome.do">메인</a></li>
				<li class="menu_item has-children">
					<a href="#">
						상점관리
						<i class="fa fa-angle-down"></i>					
					</a>
					<ul class="menu_selection">
						<li><a href="/minishop/admin/shop/eventManage.do">이벤트관리</a></li>		
						<li><a href="/minishop/admin/shop/salesInfo.do">매출현황</a></li>						
					</ul>							
				</li>								
				<li class="menu_item has-children">
					<a href="#">
						상품관리
						<i class="fa fa-angle-down"></i>					
					</a>
					<ul class="menu_selection">
						<li><a href="/minishop/admin/product/inventoryManage.do">입점재고관리</a></li>
						<li><a href="/minishop/admin/product/productManage.do">등록상품관리</a></li>														
					</ul>				
				</li>
				<li class="menu_item has-children">
					<a href="#">
						고객관리
						<i class="fa fa-angle-down"></i>					
					</a>
					<ul class="menu_selection">
							<li><a href="/minishop/admin/user/userManage.do">고객정보관리</a></li>
							<li><a href="/minishop/admin/user/personalQAManager.do">1:1문의관리</a></li>														
					</ul>						
				</li>
				<li class="menu_item">
					<a href="/minishop/admin/order/orderManage.do">주문관리</a>					
				</li>								
				<li class="menu_item">
					<a href="/minishop/admin/qa/qaManage.do">문의글관리</a>
				</li>
			</ul>
		</div>
	</div>
