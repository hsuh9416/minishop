package trading.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

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
	
	//DB로 저장하기 위해 카트를 JSONString으로 변환
	public String makeListToJson(List<ProductDTO> cartList) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(cartList);}
	
	//웹으로 보내기 위해 카트를 JSONElement으로 변환
	public JsonElement makeListToJsonElement(List<ProductDTO> cartList) {
		Gson gson = new GsonBuilder().create();
		return gson.toJsonTree(cartList);}	
	
	//DB에 저장되어 있던 String을 List로 반환
	public List<ProductDTO> makeJsonToList(String json){
		Gson gson = new GsonBuilder().create();
		List<ProductDTO> cartList = gson.fromJson(json, new TypeToken<ArrayList<ProductDTO>>() {}.getType());
		return cartList;}
	
}
