package trading.dao;

import java.util.Map;

import trading.bean.ShoppingCart;

public interface TradingDAO {

	Map<String, Object> getUserInfo(String id);

	ShoppingCart getCartList(String memberid);

	void storeCartList(ShoppingCart shoppingCart);

}
