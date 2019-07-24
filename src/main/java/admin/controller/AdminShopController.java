package admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/admin/shop/**")
public class AdminShopController {

	//관리자 정보 화면
	@RequestMapping(value="/adminManage.do",method = RequestMethod.GET)
	public ModelAndView adminManage() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "adminHome");
		mav.addObject("display", "/admin/shop/adminManage.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	
	
	//매출 정보 화면
	@RequestMapping(value="/salesInfo.do",method = RequestMethod.GET)
	public ModelAndView salesInfo() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "adminHome");
		mav.addObject("display", "/admin/shop/salesInfo.jsp");
		mav.setViewName("/main/home");
		return mav;
	}		
}
