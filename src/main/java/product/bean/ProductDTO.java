package product.bean;

import java.text.NumberFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * 상품 정보 관리 클래스
 */
@Component
@Data
public class ProductDTO{
	
	//PRODUCT_NAME DB 관련 : 실제로 업로드된 상품
	private int product_name_no;
	private int product_category_no;
	private String product_name_title;
	private String product_name_detail;
	private int product_name_price;
	private String product_name_image;
	private int product_hit;
	private int product_like;
	private int product_salesMount;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date product_name_instockdate;
	private String product_onstore;
	
	//PRODUCT DB 관련 : 입점한 이력이 있는 상품
	private String productID;
	private int unitcost;
	private int stock;
	private int promotioncode;
	private String productName;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date product_registerdate;
	private int ordernum;
	
	//PRODUCT_CATEGORY 관련 : CATEGORY의 이름
	private String product_category_name;
	
	//장바구니 관련(DB 항목X)
	private int cart_qty;
	
	//메인 화면 상품 리스트 컨테이너
	private StringBuffer productListHTML;
	
	//상품 리스트 작성 메소드
	public void makeProductListHTML(){
		Date date = new Date();
        long diff = date.getTime() - product_name_instockdate.getTime();
        long diffNew = date.getTime() - product_registerdate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        long diffNewDays = diffNew/(24 * 60 * 60 * 1000);
        
        NumberFormat nf = NumberFormat.getCurrencyInstance();
			productListHTML = new StringBuffer();
			productListHTML.append("<div class='col-lg-4 col-md-6 mb-4'><div class='card h-100'>");		
			productListHTML.append("<a href='/minishop/product/productView.do?product_name_no="+product_name_no+"'>");	
	        productListHTML.append("<img class='card-img-top' src='/minishop/storage/showProduct.do?product_name_image="+product_name_image+"' alt='' style='height:200px;'></a>");
	        productListHTML.append("<div class='card-body'><h4 class='card-title'><a href='/minishop/product/productView.do?product_name_no="+product_name_no+"'>"+productName+"</a></h4>");
        if(unitcost<product_name_price) {
        		productListHTML.append("<h5>"+nf.format(unitcost)+"&emsp;&emsp;<span style='color:red;font-size:10px;'><i class='fas fa-tag'>특가판매중</i></span></h5>");}
        else {
        		productListHTML.append("<h5>"+nf.format(unitcost)+"</h5>");}
	        productListHTML.append("<p class='card-text'>"+product_name_title+"</p></div>");
	        productListHTML.append("<div class='card-footer'><small class='text-muted'>");
	        productListHTML.append("<i id='iconMinimum' class='fas fa-eye'><strong>"+product_hit+"</strong></i>");
	        productListHTML.append("<i id='iconMinimum' class='fas fa-heart'><strong>"+product_like+"</strong></i>"); 
        if(stock==0) {
        	if(diffDays<0)
            		productListHTML.append("<span class='badge badge-pill badge-secondary'>입고예정</span>");	       		
        	else
            		productListHTML.append("<span class='badge badge-pill badge-light'>일시품절</span>");}
        else if(diffDays==0 || (diffDays>0 && diffDays<=14)) {
        	if(ordernum<2) productListHTML.append("<span class='badge badge-pill badge-info'>신규입고</span>");	
        	else productListHTML.append("<span class='badge badge-pill badge-info'>재입고</span>");}
        if(product_salesMount>=300) {
        		productListHTML.append("<span class='badge badge-pill badge-primary'>인기상품</span>");}
        if(diffNewDays>=0 && diffNewDays<=14) {
        		productListHTML.append("<span class='badge badge-pill badge-success'>신규입점</span>");}
        	productListHTML.append("</small></div></div></div>"); }

	//특별전 목록 작성 메소드(원 목록의 일부를 Filter)
	public void makeSpecialListHTML(String condition){
		Date date = new Date();
        long timeDiff = date.getTime() - product_registerdate.getTime();
        long timeDiffDays = timeDiff/(24 * 60 * 60 * 1000);
        
        if(condition.equals("onSale")) {
        	if(unitcost<product_name_price) {
        			makeProductListHTML();}
        	else return;}
        else if(condition.equals("newArrival")) {
        	if(timeDiffDays>=0 && timeDiffDays<=14) {
        			makeProductListHTML();}
        	else return;}
        else if(condition.equals("mustHave")) {
        	if(product_salesMount>=300) {
        			makeProductListHTML();}
        	else return;}
        else return;}

}
