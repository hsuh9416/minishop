<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta name="description" content="Colo Shop Template">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--Bootsrap 4-->
<link rel="stylesheet" type="text/css" href="/mallproject/resources/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	
    <!--Fontawesome CDN-->
<link rel="stylesheet" href="/mallproject/resources/fontawesome-free-5.9.0-web/css/all.css">

	<!-- template custom -->
<link rel="stylesheet" type="text/css" href="/mallproject/resources/styles/categories_styles.css">
<link rel="stylesheet" type="text/css" href="/mallproject/resources/styles/categories_responsive.css">  

	<!--Custom styles-->
<link rel="stylesheet" type="text/css" href="/mallproject/css/userproduct.css">  
	
	<div class="container product_section_container">
		<div class="row">
			<div class="col product_section clearfix">
			<input type="hidden" id="pg" value="${pg}">
			<input type="hidden" id="type" value="${type}">			
				<!-- Breadcrumbs -->

				<div class="breadcrumbs d-flex flex-row align-items-center">
					<ul>
						<li><a href="/mallproject/main/innerMain.do">Home</a></li>
						<li class="active" id="type"><i class="fa fa-angle-right" aria-hidden="true"></i>${type}</li>
					</ul>
				</div>
				
				<!-- Sidebar -->

				<div class="sidebar">
					<div class="sidebar_section">
						<div class="sidebar_title">
							<h5>Product Category</h5>
						</div>
						<ul class="sidebar_categories">
							<li><a href="/mallproject/product/categories.do?type=All">All</a></li>
							<li><a href="/mallproject/product/categories.do?type=Women">Women</a></li>
							<li><a href="/mallproject/product/categories.do?type=Men">Men</a></li>
							<li><a href="/mallproject/product/categories.do?type=Accessories">Accessories</a></li>
							<li><a href="#" id="getNewProducts">New Arrivals</a></li>
						</ul>
					</div>
				</div>

				<!-- Main Content -->

				<div class="main_content">

					<!-- Products -->

					<div class="products_iso">
						<div class="row">
							<div class="col">

						<!-- Product Sorting -->
							<div class="product_sorting_container product_sorting_container_top">
								<div class="form-row">
								 	<div class="form-group col-md-2">
								      <select id="order" class="form-control">
										<option value="product_name_instockdate">신상품순</option> 
										<option value="unitcost asc">가격오름차순</option> 									
										<option value="unitcost desc">가격내림차순</option> 									
										<option value="productName">상품명</option> 									  
								      </select>	
							      	</div> 						      
								</div>
							</div>
						<!-- Product Sorting:End-->
						
						<!-- Product Grid -->

	
					<div class="container-fluid">
						<div class="table-responsive">
							<table id="itemTable" class="table justify-content-center">
								<thead class="thead-dark">
								    <tr align="center">
										<th scope="col">#</th>			    
										<th scope="col">상품이미지</th>	
										<th scope="col">등록코드</th>
										<th scope="col">상품분류</th>					
										<th scope="col">상품명</th>	
										<th scope="col">조회수</th>	
										<th scope="col">좋아요</th>																					
								  </tr>
								   </thead>
								   <tbody>
									   	<tr>
									   		<th scope="row"></th>
									   		<td colspan="5"></td>			   		
									   	</tr> 	
									</tbody>   	  
							</table>
						</div>
					</div>
							<!-- Product Grid:Emd -->								
							<!-- Product Paging -->

							<div class="container-fluid">
									<nav aria-label="Page navigation">
									  <ul class="pagination justify-content-center" id="productPagingDiv"></ul>
									</nav>
							</div>
							<!-- Product Paging:End-->
							</div>
						<!-- Product Grid:END -->	
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	 	<!-- shop policies -->
	<jsp:include page="../template/shopPolicy.jsp"/>
	
	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/mallproject/resources/bootstrap-4.3.1-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/mallproject/js/user.product.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url : '/mallproject/product/getProductList.do',
		data : {'pg' :	$('#pg').val(),
				'type': $('#type').val(),
				'order' : $('#order').val()},
		dataType : 'json',
		success : function(data){
			$('#itemTable tr:gt(0)').empty();
			$.each(data.productList, function(index, items){
			$('<tr/>').append($('<td/>',{
				align: 'center',
				text : items.product_name_no	
			})).append($('<td/>',{
				align: 'center'
			}).append($('<img/>',{
				src: '/mallproject/storage/'+items.product_name_image,
				width: '100',
				height: '100',
				id : 'imageA'
			}))).append($('<td/>',{
				align : 'center',
				html : items.productID				
			})).append($('<td/>',{
				align : 'center',
				html : items.product_category_name				
			})).append($('<td/>',{
				align : 'center',
				html : items.productName				
			})).append($('<td/>',{
				text : items.product_hit
			}).prepend($('<i/>',{
				class : 'fas fa-eye'
			}))).append($('<td/>',{
			}).append($('<a/>',{
				id :'likeA'				
			}).prepend($('<i/>',{
				text : items.product_like,
				class : 'far fa-thumbs-up'
			})))).appendTo($('#itemTable tbody'));
		});//each
		
		$('#productPagingDiv').html(data.productPaging.pagingHTML);
		
		$('#itemTable').on('click','#imageA',function(){
				var product_name_no = $(this).parent().prev().text();
				window.location='/mallproject/product/productView.do?product_name_no='+product_name_no+'&pg='+$('#pg').val();
		});//제목 클릭시!
		
			
		}//success
	});//ajax
	function productPaging(pg){
		location.href='/mallproject/admin/product/categories.do?pg='+pg+'&type='+$('#type').val();
	}

});//ready

</script>





	
				


