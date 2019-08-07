package trading.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.Data;
import product.bean.ProductDTO;

@Data
@Component
public class ShoppingCart{

	private int cartnum;
	
	private List<ProductDTO> cartList;//직렬화 대상
	
	private String cartList_json;
	
	private String memberid;
	
	public ProductDTO find(int product_name_no) {
		for(ProductDTO data : this.cartList) {
			if(data.getProduct_name_no()==product_name_no)
				return data;
			}//for
		return null;
		}//해당 카트의 존재를 확인
	
	//DB로 저장하기 위해 카트를 JSONString으로 변환
	public String makeListToJson(List<ProductDTO> cartList) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(cartList);
	}
	//DB에 저장되어 있던 String을 List로 반환
	public List<ProductDTO> makeJsonToList(String json){
		Gson gson = new GsonBuilder().create();
		List<ProductDTO> cartList = gson.fromJson(json, new TypeToken<ArrayList<ProductDTO>>() {}.getType());
		return cartList;
	}
}
