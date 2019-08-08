package trading.dao;

import java.util.Map;

import trading.bean.ShoppingCart;
/*
 * TradingDAOLImpl의 인터페이스
 */
public interface TradingDAO {

	Map<String, Object> getUserInfo(String id);
	ShoppingCart getCartList(String memberid);
	void storeCartList(ShoppingCart shoppingCart);

}
