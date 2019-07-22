package com.mallproject.minishop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="/main/**")
public class HomeController {
	
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "home");
		mav.addObject("display", "/template/body.jsp");
		mav.addObject("menu", "/template/left.jsp");		
		mav.setViewName("/main/home");		
		return mav;
	}
	//사용자 화면 'introduce'
	@RequestMapping(value="/introduce.do",method = RequestMethod.GET)
	public ModelAndView introduce() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "");		
		mav.addObject("display", "/main/introduce.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}
	
	//사용자 화면 'contact'
	@RequestMapping(value="/userContact.do",method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "");		
		mav.addObject("display", "/main/userContact.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}	
}
