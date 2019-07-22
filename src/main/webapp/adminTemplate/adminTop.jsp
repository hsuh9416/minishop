<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Start Welcome area -->
     <div class="container-fluid">
        <div class="row">
           <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="sidebar-header"></div>
         </div>
     	</div>
     <div class="header-advance-area">
         <div class="header-top-area">
             <div class="container-fluid">
                 <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                       <div class="header-top-wraper">
                          <div class="row">
                             <div class="col-lg-1 col-md-0 col-sm-1 col-xs-12">
                                <div class="menu-switcher-pro">
                                    <button type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
									 <i class="fa fa-bars"></i>
									</button>
                                 </div>
                              </div>
                              <div class="col-lg-6 col-md-7 col-sm-6 col-xs-12">
                                 <div class="header-top-menu tabl-d-n">
                                     <ul class="nav navbar-nav mai-top-nav">
                                        <li class="nav-item"><a href="/mallproject/admin/outterMain.do" class="nav-link">Home</a></li>
                                     </ul>
                                 </div>
                               </div>
                               <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                                   <div class="header-right-info">
                                      <ul class="nav navbar-nav mai-top-nav header-right-menu">
                                         <li class="nav-item">
                                            <a href="#" data-toggle="dropdown" role="button" aria-expanded="false" class="nav-link dropdown-toggle">
												<i class="fa fa-user adminpro-user-rounded header-riht-inf" aria-hidden="true"></i>
												<span class="admin-name">관리자 화면</span>
												<i class="fa fa-angle-down adminpro-icon adminpro-down-arrow"></i>
											</a>
		                                      <ul role="menu" class="dropdown-header-top author-log dropdown-menu animated zoomIn">
		                                          <li><a href="/mallproject/admin/adminLogout.do"><span class="fa fa-lock author-log-ic"></span>Log Out</a></li>
		                                      </ul>
                                          </li>
                                          </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
<!-- Mobile Menu start -->
            <div class="mobile-menu-area">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="mobile-menu">
                                <nav id="dropdown">
                                    <ul class="mobile-menu-nav">
                                        <li><a data-toggle="collapse" data-target="#Charts" href="#">상점관리<span class="admin-project-icon adminpro-icon adminpro-down-arrow"></span></a>
                                            <ul class="collapse dropdown-header-top">
                                                <li><a href="/mallproject/admin/shop/adminManage.do">관리자 정보</a></li>
                                                <li><a href="/mallproject/admin/shop/salesInfo.do">매출 현황</a></li>
                                            </ul>
                                        </li>
                                        <li><a data-toggle="collapse" data-target="#demo" href="#">상품 관리<span class="admin-project-icon adminpro-icon adminpro-down-arrow"></span></a>
                                            <ul id="demo" class="collapse dropdown-header-top">
                                                <li><a href="/mallproject/admin/product/inventoryManage.do">상품 재고 관리</a>
                                                </li>
                                                <li><a href="/mallproject/admin/product/productManage.do">상품 등록 관리</a>
                                                </li>
                                            </ul>
                                        </li>

                                        <li><a data-toggle="collapse" data-target="#Miscellaneousmob" href="#">고객 관리<span class="admin-project-icon adminpro-icon adminpro-down-arrow"></span></a>
                                            <ul id="Miscellaneousmob" class="collapse dropdown-header-top">
                                                <li><a href="/mallproject/admin/user/userManage.do">고객 정보 관리</a></li>
                                                <li><a href="/mallproject/admin/user/personalQAManage.do">1:1문의 관리</a></li>
                                            </ul>
                                        </li>
                                        <li><a data-toggle="collapse" data-target="#others" href="#">주문 관리 <span class="admin-project-icon adminpro-icon adminpro-down-arrow"></span></a>
                                            <ul id="others" class="collapse dropdown-header-top">
                                                <li><a href="/mallproject/admin/order/orderList.do">주문 현황</a></li>
                                                <li><a href="/mallproject/admin/orderManage.do">개별 주문 조회/수정</a></li>
                                            </ul>
                                        </li>
                                        <li><a data-toggle="collapse" data-target="#Chartsmob" href="#">게시판관리<span class="admin-project-icon adminpro-icon adminpro-down-arrow"></span></a>
                                            <ul id="Chartsmob" class="collapse dropdown-header-top">
                                                <li><a href="/mallproject/admin/board/qaManage.do">문의게시글 관리</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Mobile Menu end -->
       </div>    
      </div>         