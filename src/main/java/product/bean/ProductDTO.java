package product.bean;

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
	@JsonFormat(pattern="yyyy.MM.dd")
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
}
