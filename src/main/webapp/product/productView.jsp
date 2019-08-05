<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/template/css/single_styles.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/template/css/single_responsive.css">
<div class="col-lg-8">	

		<div class="row">
			<div class="col-7">
					<div class="row">
						<div class="col image_col order-lg-2 order-1">
							<div class="single_product_image">
								<div class="single_product_image_background" style="background-image:url('/minishop/storage/showProduct.do?product_name_image=${productDTO.product_name_image}')">
								</div>
							</div>
						</div>
					</div>		
			</div>
			<div class="col-lg-5">
				<div class="product_details">
					<div class="product_details_title">
						<h2>${productDTO.productName}</h2>
					</div>
					<div class="free_delivery d-flex flex-row align-items-center justify-content-center">
						<span class="ti-truck"></span><span>free delivery</span>
					</div>
					<div class="original_price">${productDTO.product_name_price}</div>
					<div class="product_price">${productDTO.unitcost}</div>
					<ul class="star_rating">
						<li><i class="fa fa-star" aria-hidden="true"></i></li>
						<li><i class="fa fa-star" aria-hidden="true"></i></li>
						<li><i class="fa fa-star" aria-hidden="true"></i></li>
						<li><i class="fa fa-star" aria-hidden="true"></i></li>
						<li><i class="fa fa-star-o" aria-hidden="true"></i></li>
					</ul>
					<div class="quantity d-flex flex-column flex-sm-row align-items-sm-center">
						<span>Quantity:</span>
						<div class="quantity_selector">
							<span class="minus"><i class="fa fa-minus" aria-hidden="true"></i></span>
							<span id="quantity_value">1</span>
							<span class="plus"><i class="fa fa-plus" aria-hidden="true"></i></span>
						</div>
						<div class="red_button add_to_cart_button"><a href="#">add to cart</a></div>
						<div class="product_favorite d-flex flex-column align-items-center justify-content-center"></div>
					</div>
				</div>
			</div>
		</div>

	<!-- Tabs -->

	<div class="tabs_section_container">

		<div class="container">
			<div class="row">
				<div class="col">
					<div class="tabs_container">
						<ul class="tabs d-flex flex-sm-row flex-column align-items-left align-items-md-center justify-content-center">
							<li class="tab active" data-active-tab="tab_1"><span>상세정보</span></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">

					<!-- Tab Description -->

					<div id="tab_1" class="tab_container active">
						<div class="row justify-content-center">
							<div class="col desc_col">
								<div class="tab_title">
									<h4>${productDTO.product_name_title}</h4>
								</div>
								<div class="tab_text_block">
									${productDTO.product_name_detail}
								</div>
							</div>
						</div>

			</div>
		</div>
	</div>
	</div>
</div>

	 	<!-- shop policies -->
	<jsp:include page="/template/shopPolicy.jsp"/>
</div>

   
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="/minishop/resources/template/js/single_custom.js"></script>
<script type="text/javascript"></script>