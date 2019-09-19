package trading.dao;

import java.util.List;
import java.util.Map;

import trading.bean.CouponDTO;
import trading.bean.DeliveryDTO;
import trading.bean.EventDTO;
import trading.bean.OrderDTO;
import trading.bean.ShoppingCart;
/*
 * TradingDAOLImpl의 인터페이스
 */
public interface TradingDAO {

//--------공통: START--------//
	Map<String, Object> getUserInfo(String id);	
	int getOrderNum();
//--------공통 : END--------//
//--------장바구니 : START--------//
	ShoppingCart getCartList(String memberid);
	void storeCartList(ShoppingCart shoppingCart);
//--------장바구니 : END--------//
//--------쿠폰 : START--------//	
	List<CouponDTO> getCouponBook();
	int makeCoupon(CouponDTO couponDTO);
	int modifyCoupon(CouponDTO couponDTO);
	CouponDTO getSelectedCoupon(String coupon_no);	
	List<CouponDTO> getGivenCoupon(String coupon_no);
	int deleteCoupon(String coupon_no);
	void setCoupon(CouponDTO couponDTO);
	void modifyUserBenefit(Map<String, String> map);
	void deleteUserBenefit(String id);
	List<CouponDTO> getAvailableUserCoupon(String id);
	List<CouponDTO> getCouponListAll(String id);
//--------쿠폰 : END--------//	
//--------주문 : START--------//	
	int getTotalA();
	int getTotalSearchA(Map<String, String> map);
	List<OrderDTO> getUserOrderList(int startNum, int endNum);
	List<OrderDTO> userSearchOrderList(Map<String, String> map);	
	List<OrderDTO> getOrderList(String id);	
	void setNewOrderPwd(OrderDTO orderDTO);
	int putOrder(OrderDTO orderDTO);
	void setPayment(OrderDTO orderDTO);
	OrderDTO getOrderInfo(String order_no);
	List<OrderDTO> getPaymentInfo(String order_no);
	int modifyOrderAdmin(OrderDTO orderDTO);
	int changeOrderInfo(Map<String, Object> map);
	int updatePayment(OrderDTO orderDTO);
	int implementingInventoryChange(OrderDTO orderDTO);
	int cancelPayment(int order_no);
	void deleteOrder(String order_no);
	OrderDTO orderCheck(String id);
	void setNewOrderId(OrderDTO dto);
//--------주문 : END--------//	
//--------이벤트 : START--------//	
	List<EventDTO> getBannerList();
	EventDTO getSelectedBanner(String event_no);
	int bannerModify(EventDTO eventDTO);
//--------이벤트 : END--------//	
//--------배송료 : START--------//	
	List<DeliveryDTO> getDeliveryPolicy();
	int modifyDeliveryPolicy(DeliveryDTO deliveryDTO);
	int verifyAdditionalFee(String zipcode);
//--------배송료 : END--------//




















}
