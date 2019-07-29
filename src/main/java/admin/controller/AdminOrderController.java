package admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/admin/order/**")
public class AdminOrderController {

	/* 주문 관리*/
	//재고 관리 화면
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
