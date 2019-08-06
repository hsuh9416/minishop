package trading.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import product.bean.ProductDTO;

@Data
@Component
public class ShoppingCart {
	
	private List<ProductDTO> cartList;
	
	public ProductDTO find(int product_name_no) {
		for(ProductDTO data : this.cartList) {
			if(data.getProduct_name_no()==product_name_no)
				return data;
			}//for
		return null;
		}//해당 카트의 존재를 확인
}
