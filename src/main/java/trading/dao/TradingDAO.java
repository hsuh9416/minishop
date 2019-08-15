package trading.dao;

import java.util.List;
import java.util.Map;

import trading.bean.CouponDTO;
import trading.bean.EventDTO;
import trading.bean.OrderDTO;
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
//--------쿠폰 : START--------//	
	void setCoupon(CouponDTO couponDTO);
//--------쿠폰 : END--------//	
//--------주문 : START--------//	
	List<OrderDTO> getOrderList(String id);	
	void setNewOrderPwd(OrderDTO orderDTO);
//--------주문 : END--------//	
//--------이벤트 : START--------//	
	List<EventDTO> getBannerList();
	EventDTO getSelectedBanner(String event_no);
	int bannerModify(EventDTO eventDTO);
//--------이벤트 : END--------//	

}
