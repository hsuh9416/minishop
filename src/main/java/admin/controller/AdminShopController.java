package admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
 * 관리자: 관리자 및 상점 정보를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/shop/**")
public class AdminShopController {

	//1. 관리자 정보 화면 이동
	@RequestMapping(value="/adminManage.do",method = RequestMethod.GET)
	public ModelAndView adminManage() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "adminHome");
			mav.addObject("display", "/admin/shop/adminManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	

	//2. 관리자 정보 수정 화면 이동
	@RequestMapping(value="/adminManageForm.do",method = RequestMethod.GET)
	public ModelAndView adminManageForm() {
		
		ModelAndView mav = new ModelAndView();
			mav.setViewName("/admin/shop/adminManageForm");
			
		return mav;
	}	
	
	//3. 매출 정보 화면 이동
	@RequestMapping(value="/salesInfo.do",method = RequestMethod.GET)
	public ModelAndView salesInfo() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "adminHome");
			mav.addObject("display", "/admin/shop/salesInfo.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}		
}
