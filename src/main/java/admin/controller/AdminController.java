package admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
 *관리자 화면에 대한 접근과 관리자 로그아웃을 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/**")
public class AdminController {

	
	//1. 관리자 메인으로 이동
	@RequestMapping(value="/adminHome.do",method = RequestMethod.GET)
	public ModelAndView adminHome() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "homeAdmin");
			mav.addObject("display", "/admin/shop/adminManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//2. 관리자 계정 로그아웃
	@RequestMapping(value="/adminLogout.do",method=RequestMethod.GET)
	public ModelAndView adminLogout(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		session.removeAttribute("adminDTO");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/common/logoutAdmin");
		return mav;	
	}			
}
