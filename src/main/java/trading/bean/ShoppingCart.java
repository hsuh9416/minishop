package trading.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import product.bean.ProductDTO;
/*
 * 장바구니 관련 요소 및 SHOPPINGCART DB 요소 관리 클래스
 */
@Data
@Component
public class ShoppingCart{

	//SHOPPINGCART DB : 직접 저장되는 요소
	private int cartnum;
	private String memberid;	
	private String cartList_json;	
	
	//session으로 관리되는 항목의 구성요소
	private List<ProductDTO> cartList;
	private int cartTotal;
	
	//장바구니 내부  해당 상품 여부 조회 : cart상의 인덱스 번호를 반환, 없을 경우는 -1
	public int exists(int product_name_no, List<ProductDTO> cartList) {
		for (int i = 0; i < cartList.size(); i++) {
			if (cartList.get(i).getProduct_name_no()==(product_name_no)) {
				return i;}}
		return -1;
	}	
		
}
