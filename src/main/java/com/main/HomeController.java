package com.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
 *쇼핑몰 메인 제어용 클래스(공통 논리, 특정 계층에 속하지 않는 경우 포함)
 */
@Controller
public class HomeController {
		
	//1. 쇼핑몰 내부 메인 이동
	@RequestMapping(value = "/main/home.do", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "home");
		mav.addObject("display", "/template/body.jsp");
		mav.addObject("menu", "/template/left.jsp");		
		mav.setViewName("/main/home");		
		return mav;
	}

	//2. 쇼핑몰 내부'introduce' 화면 이동
	@RequestMapping(value="/main/introduce.do",method = RequestMethod.GET)
	public ModelAndView introduce() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "home");		
		mav.addObject("display", "/main/introduce.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}
	
	//3. 쇼핑몰 내부 'contact' 화면 이동
	@RequestMapping(value="/main/userContact.do",method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "home");		
		mav.addObject("display", "/main/userContact.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}
	//4. 허가받지 않은 접근 경고 화면 이동
	@RequestMapping(value = "/error/unauthorized.do", method = RequestMethod.GET)
	public ModelAndView unauthorized() {
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("/common/unauthorized");		
		return mav;
	}	
	
	//5. 거래약관 팝업창으로 이동
	@RequestMapping(value="/main/viewPolicy.do",method = RequestMethod.GET)
	public ModelAndView viewPolicy() {
		
		ModelAndView mav = new ModelAndView();		
			mav.setViewName("/main/viewPolicy");
		
		return mav;
	}	
}
