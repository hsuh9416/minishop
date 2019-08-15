package trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import trading.bean.CouponDTO;
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
	
	//1.쿠폰 발급
	@Override
	public void setCoupon(CouponDTO couponDTO) {
		sqlSession.insert("tradingSQL.setCoupon",couponDTO);
	}		
	
//----------- 쿠폰 : END ----------//	

//----------- 주문 : START ----------//
	
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
}
