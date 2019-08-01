package product.bean;

import java.text.NumberFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Component
@Data
public class ProductDTO {
	//table 'product_name'관련 : 실제로 업로드된 물품 관련
	private int product_name_no;//productBoard identification(순수번호)
	private int product_category_no;//type
	private String product_name_title;//업로드되는 화면 타이틀
	private String product_name_detail;//업로드되는 화면 내용
	private int product_name_price;//(정가격)
	private String product_name_image;//업로드된 이미지
	private int product_hit;//조회수
	private int product_like;//좋아요수
	private int product_salesMount;//(accumulated) salesMount
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date product_name_instockdate;//입고 예정일
	private String product_onstore;//입점여부
	//table 'product'관련 
	private String productID;//product code(type+color+sequence Number)
	private int unitcost;//시즌할인 등이 적용된 후 실제로 업로드 되는 가격(판매가격)
	private int stock;
	private int promotioncode;//0.할인불가 1.할인가능 
	private String productName;//상품명
	
	//table 'product_category' 관련
	private String product_category_name;
	
	//상품 페이지 관련
	private String type;
	
	//메인 화면 리스트 포맷
	private StringBuffer productListHTML;
	
	public void makeProductListHTML(){
		Date date = new Date();
 
        long diff = date.getTime() - product_name_instockdate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
		productListHTML = new StringBuffer();
		productListHTML.append("<div class='col-lg-4 col-md-6 mb-4'><div class='card h-100'>");		
		productListHTML.append("<a href='/minishop/product/productView.do?product_name_no="+product_name_no+"'>");	
        productListHTML.append("<img class='card-img-top' src='/minishop/storage/"+product_name_image+"' alt='' style='height:200px;'></a>");
        productListHTML.append("<div class='card-body'><h4 class='card-title'><a href='/minishop/product/productView.do?product_name_no="+product_name_no+"'>"+productName+"</a></h4>");
        if(unitcost<product_name_price) {//정가보다 판매가가 저렴하면 세일 표시
        	productListHTML.append("<h5>"+nf.format(unitcost)+"&emsp;&emsp;<span style='color:red;font-size:10px;'><i class='fas fa-tag'>특가판매중</i></span></h5>");
        }
        else {
        	productListHTML.append("<h5>"+nf.format(unitcost)+"</h5>");
        }
        productListHTML.append("<p class='card-text'>"+product_name_title+"</p></div>");
        productListHTML.append("<div class='card-footer'><small class='text-muted'>");
        productListHTML.append("<i id='iconMinimum' class='fas fa-eye'><strong>"+product_hit+"</strong></i>");
        productListHTML.append("<i id='iconMinimum' class='fas fa-heart'><strong>"+product_like+"</strong></i>"); 
        if(stock==0) {//재고가 0이하인 상태에서
        	if(diffDays<0)
            	productListHTML.append("<span class='badge badge-pill badge-secondary'>입고예정</span>");	       		
        	else
            	productListHTML.append("<span class='badge badge-pill badge-light'>일시품절</span>");	        		
        }
        else if(diffDays==0 || (diffDays>0 && diffDays<=14)) {//2주내 입고상품은 신규상품으로 분류
        	productListHTML.append("<span class='badge badge-pill badge-info'>신규입고</span>");	     	
        }
        if(product_salesMount>=300) {//판매 300이상->인기상품
        	productListHTML.append("<span class='badge badge-pill badge-primary'>인기상품</span>");	
        }
        productListHTML.append("</small></div></div></div>");          

	}
}
