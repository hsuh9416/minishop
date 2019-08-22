package admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import product.bean.ProductDTO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
import trading.dao.TradingDAO;

/*
 * 관리자: 주문관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/order/**")
public class AdminOrderController {

	@Autowired
	TradingDAO tradingDAO;
	
	@Autowired
	JsonTransitioner jsonTrans;
	
	//1. 회원주문관리 화면으로 이동
	@RequestMapping(value="/orderManage.do",method = RequestMethod.GET)
	public ModelAndView orderManage(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "adminOrderManage");
			mav.addObject("display", "/admin/order/orderManage.jsp");
			mav.setViewName("/main/home");
		
		return mav;
	}	
	
	//2. 회원주문목록 호출하기
	@RequestMapping(value="/getUserOrderList.do",method = RequestMethod.GET)
	public ModelAndView getUserOrderList() {
		
		List<OrderDTO> userOrderList = tradingDAO.getUserOrderList();
		for(OrderDTO data: userOrderList) {
			List<ProductDTO> productList = jsonTrans.makeJsonToList(data.getOrderlist_json());
			data.setOrderList(productList);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userOrderList",userOrderList);
		mav.setViewName("jsonView");
	
	return mav;
	}
}
