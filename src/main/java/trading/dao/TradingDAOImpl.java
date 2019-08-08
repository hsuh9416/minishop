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
 * 
 */
@Repository
@DependsOn(value= {"sqlSession"})
public class TradingDAOImpl implements TradingDAO {
	@Autowired
	private SqlSession sqlSession;
	
	//회원 관리의 정보 불러오기(shoppingcart제외)
	@Override
	public Map<String, Object> getUserInfo(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<OrderDTO> orderList = sqlSession.selectList("tradingSQL.getOrderList", id);
		map.put("orderList", orderList);
		List<CouponDTO> couponList = sqlSession.selectList("tradingSQL.getCouponList",id);
		map.put("couponList", couponList);		
		return map;
	}
	//sessionid값으로 저장된 장바구니 가져오기
	@Override
	public ShoppingCart getCartList(String memberid) {
		return sqlSession.selectOne("tradingSQL.getCartList", memberid);
	}
	//회원 Cart 저장
	@Override
	public void storeCartList(ShoppingCart shoppingCart) {
		String memberid = shoppingCart.getMemberid();
		ShoppingCart existOne =null;
		existOne = sqlSession.selectOne("tradingSQL.getCartList", memberid);
		if(existOne!=null) sqlSession.update("tradingSQL.updateCartList", shoppingCart);	
		else sqlSession.insert("tradingSQL.insertCartList", shoppingCart);		
	}
	
}
