package admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * 관리자: 주문관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/order/**")
public class AdminOrderController {

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
}
