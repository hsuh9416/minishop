<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css" href="/minishop/resources/bootstrap4/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/minishop/resources/custom/css/userproduct.css">
     <div class="col-lg-8">	
     	<input type="hidden" id="condition" value="${condition}">     	
     	<div class="form-row justify-content-center" id="categories">	
			<div class="form-group">
				<h4 align="center">
						<c:if test="${condition=='newArrival'}">신규 입점 상품</c:if>
			          	<c:if test="${condition=='popular'}">특별 가격 상품</c:if>
			          	<c:if test="${condition=='sale'}">점내 인기 상품</c:if>
			    </h4>
				<div class="borderless">
				<ul class="list-group list-group-horizontal">				  
				  <li class="list-group-item">
				  	<a class="list-group-item dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			          	<c:if test="${condition=='newArrival'}">신규 입점 상품</c:if>
			          	<c:if test="${condition=='popular'}">특별 가격 상품</c:if>
			          	<c:if test="${condition=='sale'}">점내 인기 상품</c:if>
			     	</a>
				     <div class="dropdown-menu" aria-labelledby="navbarDropdown">
					       <a class="dropdown-item" href="/minishop/product/eventProductList.do?condition=new">신규 입점 상품</a>
					       <a class="dropdown-item" href="/minishop/product/eventProductList.do?condition=sale">특별 가격 상품</a>
						   <a class="dropdown-item" href="/minishop/product/eventProductList.do?condition=popular">점내 인기 상품</a>     
				     </div>
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
			url:'/minishop/product/getSpecialProductList.do',
			data:'condition='+$('#condition').val(),
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
		url:'/minishop/product/getSpecialProductList.do',
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




	
				


