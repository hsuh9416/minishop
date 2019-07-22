package trading.bean;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import product.bean.ProductDTO;

@Component
@Setter
@Getter
public class CartDTO {
	private ProductDTO productDTO;//productID로 구분
	private int cart_qty;
	
	public CartDTO(){}
	public CartDTO(ProductDTO productDTO,int cart_qty) {
		this.productDTO = productDTO;
		this.cart_qty = cart_qty;
	}

}
