package trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import trading.bean.CartDTO;
import trading.bean.CouponDTO;
import trading.bean.OrderDTO;

@Repository
@DependsOn(value= {"sqlSession"})
public class TradingDAOImpl implements TradingDAO {
	@Autowired
	private SqlSession sqlSession;
	
	//회원 관리의 정보 불러오기
	@Override
	public Map<String, Object> getUserInfo(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<CartDTO> cartList = sqlSession.selectList("tradingSQL.getCartList", id);	
		map.put("cartList", cartList);
		List<OrderDTO> orderList = sqlSession.selectList("tradingSQL.getOrderList", id);
		map.put("orderList", orderList);
		List<CouponDTO> couponList = sqlSession.selectList("tradingSQL.getCouponList",id);
		map.put("couponList", couponList);		
		return map;
	}
}
