package admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import product.bean.ProductDTO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
import trading.bean.OrderPaging;
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
	
	@Autowired
	OrderPaging orderPaging;
	
	//1. 회원주문관리 화면으로 이동
	@RequestMapping(value="/orderManage.do",method = RequestMethod.GET)
	public ModelAndView orderManage(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "orderAdmin");
			mav.addObject("display", "/admin/order/orderManage.jsp");
			mav.setViewName("/main/home");
		
		return mav;
	}	
	
	//2. 회원주문목록 호출하기(한페이지당 5게시물,3블록 표시)
	@RequestMapping(value="/getUserOrderList.do",method = RequestMethod.POST)
	public ModelAndView getUserOrderList(@RequestParam(required=false,defaultValue="1") String pg) {
	
		int page = Integer.parseInt(pg);
		int endNum = page*5;
		int startNum = endNum-4;	
		int totalA = 0;
		
		totalA = tradingDAO.getTotalA();
	
		List<OrderDTO> userOrderList = tradingDAO.getUserOrderList(startNum, endNum);

			orderPaging.setCurrentPage(page);
			orderPaging.setPageBlock(3);
			orderPaging.setPageSize(5);
			orderPaging.setTotalA(totalA);
			orderPaging.makePagingHTML();;
		
		
		for(OrderDTO data: userOrderList) {
			List<ProductDTO> productList = jsonTrans.makeJsonToList(data.getOrderlist_json());
			data.setOrderList(productList);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("orderPaging", orderPaging);
		mav.addObject("userOrderList",userOrderList);
		mav.setViewName("jsonView");
	
	return mav;
	}
	
	//3. 특정 검색어에 해당하는 주문 목록 가져오기(한페이지당 5게시물,3블록 표시)
	@RequestMapping(value="/userSearchOrderList.do",method= RequestMethod.POST)
	public ModelAndView userSearchOrderList(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		
			int page = Integer.parseInt(pg);
			int endNum = page*5;
			int startNum = endNum-4;
			int totalA=0;
		
			if(searchOption.equals("productid")) keyword = keyword.toUpperCase();
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption",searchOption);
			map.put("keyword", keyword);
			totalA = tradingDAO.getTotalSearchA(map);	
			
			orderPaging.setCurrentPage(page);
			orderPaging.setPageBlock(3);
			orderPaging.setPageSize(5);
			orderPaging.setTotalA(totalA);
			orderPaging.makeSearchPagingHTML();				
		
		
		List<OrderDTO> userSearchOrderList = tradingDAO.userSearchOrderList(map);
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("pg", pg);
			mav.addObject("userOrderList",userSearchOrderList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("orderPaging", orderPaging);
			mav.setViewName("jsonView");
		
		return mav;	
	}	
	//4. 개별조회서 조회 및 수정사항 반영 팝업창 이동
	@RequestMapping(value="/personalOrderView.do",method= RequestMethod.POST)
	public ModelAndView personalOrderView(@RequestParam String order_no, String order_state) {
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("order_no", order_no);		
			mav.addObject("new_order_state",order_state);
			mav.setViewName("/admin/order/personalOrderView");
		
	return mav;	
	}

	//5. 개별조회서 내용 호출
	@RequestMapping(value="/getPersonalOrderInfo.do",method= RequestMethod.GET)
	@ResponseBody
	public ModelAndView getPersonalOrderInfo(@RequestParam String order_no) {
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
		List<OrderDTO> paymentInfo = tradingDAO.getPaymentInfo(order_no);
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("orderDTO", orderDTO);
			mav.addObject("paymentInfo", paymentInfo);		
			mav.setViewName("jsonView");
		
	return mav;	
	}
	
}
