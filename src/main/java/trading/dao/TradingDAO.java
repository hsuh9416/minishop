package trading.dao;

import java.util.Map;

import trading.bean.ShoppingCart;
/*
 * TradingDAOLImpl의 인터페이스
 */
public interface TradingDAO {

//--------공통: START--------//
	Map<String, Object> getUserInfo(String id);	
//--------공통 : END--------//
//--------장바구니 : START--------//
	ShoppingCart getCartList(String memberid);
	void storeCartList(ShoppingCart shoppingCart);
//--------장바구니 : END--------//
	
	
}
