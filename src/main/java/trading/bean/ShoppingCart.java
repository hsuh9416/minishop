package trading.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import product.bean.ProductDTO;

@Component
public class ShoppingCart {

	private List<ProductDTO> cartList;
	
	public List<ProductDTO> findAll(){
		return this.cartList;
	}
	
	public ProductDTO find(String productID) {
		for(ProductDTO productDTO : this.cartList) {
			if(productDTO.getProductID().equalsIgnoreCase(productID))
				return productDTO;
			}//for
		return null;
		}//개별 ProductDTO 존재 여부
}
