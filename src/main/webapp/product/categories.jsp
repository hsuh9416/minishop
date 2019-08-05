<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
     <div class="col-lg-8">	
     	<input type="hidden" id="product_category_name" value="${product_category_name}">
     	<input type="hidden" id="order" value="${order}">     	
     	<div class="form-row justify-content-center" id="categories">	
			<div class="form-group">
				<h4 align="center">${product_category_name}</h4>
				<div class="borderless">
				<ul class="list-group list-group-horizontal">
				  <li class="list-group-item">
				  	<a class="list-group-item dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			          	${product_category_name}
			     	</a>
				     <div class="dropdown-menu" aria-labelledby="navbarDropdown">
					       <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=ALL&order=new">ALL</a>
					       <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=MEN&order=new">MEN</a>
					       <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=WOMEN&order=new">WOMEN</a>
					       <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=ACCESORIES&order=new">ACCESORIES</a>					       					       
				     </div>
				  </li> 				  
				  <li class="list-group-item">
				  	<a class="list-group-item dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			          	<c:if test="${order=='new'}">신상품순</c:if>
			          	<c:if test="${order=='name'}">상품명순</c:if>
			          	<c:if test="${order=='highPrice'}">최고가순</c:if>
			          	<c:if test="${order=='lowPrice'}">최저가순</c:if>
			     	</a>
				     <div class="dropdown-menu" aria-labelledby="navbarDropdown">
					       <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=new">신상품순</a>
					       <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=name">상품명순</a>
						   <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=highPrice">최고가순</a>
						   <a class="dropdown-item" href="/minishop/product/categories.do?product_category_name=${product_category_name}&order=lowPrice">최저가순</a>		       
				     </div>
				  </li> 
				  <li class="list-group-item">상품명 검색</li>				  				  
				  <li class="list-group-item">
					<form class="form-inline">
					  <input class="form-control form-control-sm mr-3 w-75" id="searchWord" type="text" placeholder="Search"
					    aria-label="Search" value="${searchWord}">
					  <a id="searchBtn" href="#"><i class="fas fa-search" aria-hidden="true"></i></a>
					</form>
				   </li>		  
				</ul>
				</div>
			</div>	
		</div>


        <div id="mainList" class="row"></div>
        <!-- /.row -->
        		
      </div>
		<!-- /.col-lg-8 -->    



	
	
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/user.product.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	var product_category_name = $('#product_category_name').val();
		//목록 불러오기(신상품순서)
		$.ajax({
			type: 'get',
			url:'/minishop/product/getUserProductList.do',
			data:'product_category_name='+$('#product_category_name').val()+'&order='+$('#order').val(),
			dataType: 'json',
			success: function(data){
				$('#mainList').empty();
				$.each(data.productList, function(index, items){
					$('#mainList').append(items.productListHTML);
				});//each
						
			}//success
		});		
});

$('#searchBtn').click(function(){
	var searchWord = $('#searchWord').val();
	if(searchWord=='') alert('검색어를 한 글자 이상 입력해주세요');
	else getSearchedList(searchWord);
});

function getSearchedList(searchWord){
	//목록 불러오기(신상품순서)
	$.ajax({
		type: 'get',
		url:'/minishop/product/getUserProductList.do',
		data: {'product_category_name' : $('#product_category_name').val(),
				'order' : $('#order').val(),
				'searchWord' : searchWord},
		dataType: 'json',
		success: function(data){
			$('#mainList').empty();
			$.each(data.productList, function(index, items){
				$('#mainList').append(items.productListHTML);
			});//each
					
		}//success
	});		
}
</script>




	
				


