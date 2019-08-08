package trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import trading.bean.CouponDTO;
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
	
}
