package trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import trading.bean.CouponDTO;
import trading.bean.DeliveryDTO;
import trading.bean.EventDTO;
import trading.bean.OrderDTO;
import trading.bean.ShoppingCart;
/*
 * 거래 관련 DB요소를 제어하는 메소드를 관리하는 클래스
 */
@Repository
@DependsOn(value= {"sqlSession"})
public class TradingDAOImpl implements TradingDAO {
	@Autowired
	private SqlSession sqlSession;

//----------- 공통 : START ----------//
	
	//1. 특정 회원 거래 정보 불러오기(장바구니 제외)
	@Override
	public Map<String, Object> getUserInfo(String id) {

		List<OrderDTO> orderList = sqlSession.selectList("tradingSQL.getOrderList", id);
		List<CouponDTO> couponList = sqlSession.selectList("tradingSQL.getCouponList",id);
		
		Map<String,Object> map = new HashMap<String,Object>();		
			map.put("orderList", orderList);		
			map.put("couponList", couponList);		
			
		return map;
	}
	//2. 주문 번호 얻기
	@Override
	public int getOrderNum() {
		return sqlSession.selectOne("tradingSQL.getOrderNum");
	}
//----------- 공통 : END ----------//
		
//----------- 장바구니 : START ----------//

	//1. 특정 회원 거래 정보 불러오기(장바구니 호출)
	@Override
	public ShoppingCart getCartList(String memberid) {
		return sqlSession.selectOne("tradingSQL.getCartList", memberid);
	}
	
	//2. 특정 회원 장바구니 DB 저장
	@Override
	public void storeCartList(ShoppingCart shoppingCart) {
		
		String memberid = shoppingCart.getMemberid();
		ShoppingCart existOne =null;
			existOne = sqlSession.selectOne("tradingSQL.getCartList", memberid);
		
		if(existOne!=null) sqlSession.update("tradingSQL.updateCartList", shoppingCart);	
		else sqlSession.insert("tradingSQL.insertCartList", shoppingCart);		
	}

//----------- 장바구니 : END ----------//	

//----------- 쿠폰 : START ----------//
	
	
	
	
	//1.발행된 쿠폰북 가져오기
	@Override
	public List<CouponDTO> getCouponBook() {
		return sqlSession.selectList("tradingSQL.getCouponBook");
	}

	//2.쿠폰북에 새 쿠폰 발행
	@Override
	public int makeCoupon(CouponDTO couponDTO) {
		return sqlSession.insert("tradingSQL.makeCoupon",couponDTO);		
	}
	
	//3.쿠폰북의 쿠폰 수정
	@Override
	public int modifyCoupon(CouponDTO couponDTO) {
		return sqlSession.update("tradingSQL.modifyCoupon",couponDTO);	
	}
	
	//4.쿠폰 번호로 발행된 쿠폰 조회
	@Override
	public CouponDTO getSelectedCoupon(String coupon_no) {
		return sqlSession.selectOne("tradingSQL.getSelectedCoupon",coupon_no);
	}
	
	//5.쿠폰 번호로 지급된 쿠폰 리스트 조회
	@Override
	public List<CouponDTO> getGivenCoupon(String coupon_no) {
		return sqlSession.selectList("tradingSQL.getGivenCoupon",coupon_no);
	}
	
	//6.쿠폰북에서 발행 쿠폰 삭제
	@Override
	public int deleteCoupon(String coupon_no) {
		return sqlSession.delete("tradingSQL.deleteCoupon",coupon_no);	
	}
	
	//5.대상에 쿠폰 지급
	@Override
	public void setCoupon(CouponDTO couponDTO) {
		sqlSession.insert("tradingSQL.setCoupon",couponDTO);
	}	
	//6. 사용된 쿠폰 삭제
	@Override
	public void modifyUserBenefit(Map<String,String> map) {
		sqlSession.update("tradingSQL.modifyUserBenefit",map);
	}
	//7.계정삭제 회원의 쿠폰 삭제
	@Override
	public void deleteUserBenefit(String id) {
		sqlSession.delete("tradingSQL.deleteUserBenefit",id);
	}
	
	//8.결제 회원 쿠폰 호출
	@Override
	public List<CouponDTO> getAvailableUserCoupon(String id) {
		return sqlSession.selectList("tradingSQL.getAvailableUserCoupon",id);
	}
	//9. 존재하는 모든 쿠폰 가져오기
	@Override
	public List<CouponDTO> getCouponListAll(String id) {
		return sqlSession.selectList("tradingSQL.getCouponList",id);
	}
//----------- 쿠폰 : END ----------//	

//----------- 주문 : START ----------//
	
	@Override
	public int getTotalA() {
		return sqlSession.selectOne("tradingSQL.getTotalA");
	}
	
	@Override
	public List<OrderDTO> getUserOrderList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);		
		return sqlSession.selectList("tradingSQL.getUserOrderList",map);
	}

	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("tradingSQL.getTotalSearchA",map);
	}
	
	@Override
	public List<OrderDTO> userSearchOrderList(Map<String, String> map) {
		return sqlSession.selectList("tradingSQL.userSearchOrderList",map);
	}
	
	//1.주문서 목록 호출
	@Override
	public List<OrderDTO> getOrderList(String id) {
		return sqlSession.selectList("tradingSQL.getOrderList", id);
	}		
	//2.주문서의 새 비밀번호 발급
	@Override
	public void setNewOrderPwd(OrderDTO orderDTO) {
		sqlSession.update("tradingSQL.setNewOrderPwd", orderDTO);
	}

	//3.주문서 업로드
	@Override
	public int putOrder(OrderDTO orderDTO) {
		return sqlSession.insert("tradingSQL.putOrder", orderDTO);
	}

	//4. 결제 업로드
	@Override
	public void setPayment(OrderDTO orderDTO) {
		sqlSession.insert("tradingSQL.setPayment", orderDTO);
	}
	//5.특정 주문서 호출
	@Override
	public OrderDTO getOrderInfo(String order_no) {
		return sqlSession.selectOne("tradingSQL.getOrderInfo",order_no);
	}
	//6. 특정 주문서의 지급결제 정보 호출
	@Override
	public List<OrderDTO> getPaymentInfo(String order_no) {
		return sqlSession.selectList("tradingSQL.getPaymentInfo",order_no);
	}
	
	// 주문서 상태 업데이트(주문취소, 환불 공통)
	@Override
	public int modifyOrderAdmin(OrderDTO orderDTO) {
		return sqlSession.update("tradingSQL.modifyOrderAdmin",orderDTO);
	}
	
	//주문서 정보 수정
	@Override
	public int changeOrderInfo(Map<String, Object> map) {
		return sqlSession.update("tradingSQL.changeOrderInfo",map);
	}
	//입금 완료 반영
	@Override
	public int updatePayment(OrderDTO orderDTO) {
		return sqlSession.update("tradingSQL.changeOrderInfo",orderDTO);
	}
	//거래로 인한 재고 변동 반영
	@Override
	public int implementingInventoryChange(OrderDTO orderDTO) {
		return sqlSession.update("tradingSQL.implementingInventoryChange",orderDTO);
	}
	//결제내역 삭제 및 결제 취소
	@Override
	public int cancelPayment(int order_no) {
		return sqlSession.delete("tradingSQL.cancelPayment", order_no);
	}
	//주문서 삭제
	@Override
	public void deleteOrder(String order_no) {
		sqlSession.delete("tradingSQL.deleteOrder", order_no);
	}
	//비회원 로그인
	@Override
	public OrderDTO orderCheck(String id) {
		return sqlSession.selectOne("tradingSQL.orderCheck",id);
	}
	@Override
	public void setNewOrderId(OrderDTO dto) {
		sqlSession.update("tradingSQL.setNewOrderId", dto);
	}
	
//----------- 주문 : END ----------//	

//----------- 이벤트 : START ----------//	

	//1. 배너리스트 불러오기
	@Override
	public List<EventDTO> getBannerList() {
		return sqlSession.selectList("tradingSQL.getBannerList");
	}
	
	//2. 특정 배너 정보 불러오기
	@Override
	public EventDTO getSelectedBanner(String event_no) {
		return sqlSession.selectOne("tradingSQL.getSelectedBanner",event_no);
	}

	//3. 배너 수정하기
	@Override
	public int bannerModify(EventDTO eventDTO) {
		return sqlSession.update("tradingSQL.bannerModify", eventDTO);
	}	
	
//----------- 이벤트 : END ----------//	
	
//----------- 배송료 : END ----------//
	@Override
	public List<DeliveryDTO> getDeliveryPolicy() {
		return sqlSession.selectList("tradingSQL.getDeliveryPolicy");
	}
	
	@Override
	public int modifyDeliveryPolicy(DeliveryDTO deliveryDTO) {
		return sqlSession.update("tradingSQL.modifyDeliveryPolicy", deliveryDTO);
	}
	
	@Override
	public int verifyAdditionalFee(String zipcode) {
		return sqlSession.selectOne("tradingSQL.verifyAdditionalFee", zipcode);
	}
//----------- 배송료 : END ----------//
	
}
