<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-lg-8">	
	<div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="carousel-item active" >
				<a href="/minishop/main/introduce.do">
					<img class="d-block img-fluid" style="width:900px;height:350px;"
	              		src="/minishop/resources/image/background/title_slide1.jpg" alt="First slide">
				</a>
			</div>
			<div class="carousel-item">
					<a href="/minishop/product/eventProductList.do?condition=new">
						<img class="d-block img-fluid" style="width:900px;height:350px;"
		              		src="/minishop/resources/image/background/title_slide2.jpg" alt="Second slide">
					</a>
			</div>
			<div class="carousel-item">
					<a href="/minishop/member/writeForm.do">
						<img class="d-block img-fluid" style="width:900px;height:350px;"
		              		src="/minishop/resources/image/background/title_slide3.jpg" alt="Third slide">
					</a>
			</div>
		</div>
		<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
			<span class="sr-only">Previous</span>
		</a>
		<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
		</a>
	</div>

	<div id="mainList" class="row"></div>

</div>  
	<!--JavaScript Local LINK:START-->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/minishop/resources/custom/js/main/main.body.js"></script>
	<!--JavaScript Local LINK:END-->
