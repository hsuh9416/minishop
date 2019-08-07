package com.mallproject.minishop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = "/main/home.do", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "home");
		mav.addObject("display", "/template/body.jsp");
		mav.addObject("menu", "/template/left.jsp");		
		mav.setViewName("/main/home");		
		return mav;
	}

	//사용자 화면 'introduce'
	@RequestMapping(value="/main/introduce.do",method = RequestMethod.GET)
	public ModelAndView introduce() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "introduce");		
		mav.addObject("display", "/main/introduce.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}
	
	//사용자 화면 'contact'
	@RequestMapping(value="/main/userContact.do",method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "userContact");		
		mav.addObject("display", "/main/userContact.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}
	//허가받지 않은 페이지 경고
	@RequestMapping(value = "/error/unauthorized.do", method = RequestMethod.GET)
	public ModelAndView unauthorized() {
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("/error/unauthorized");		
		return mav;
	}	
}
