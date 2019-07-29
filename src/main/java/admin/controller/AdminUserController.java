package admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/admin/user/**")
public class AdminUserController {

	//관리자 회원 조회 가져오기
	@RequestMapping(value="/userManage.do",method= RequestMethod.GET)
	public ModelAndView userManage() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location","adminUserInfo");		
		mav.addObject("display", "/admin/user/userManage.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	//회원 정보 가져오기
	
	//1:1문의 목록 가져오기
	@RequestMapping(value="/personalQAManager.do",method= RequestMethod.GET)	
	public ModelAndView personalQAManager() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("location","adminUserInfo");
		mav.addObject("display", "/admin/user/personalQAManager.jsp");
		mav.setViewName("/main/home");
		return mav;		
	}
	
	//1:1문의 리스트 가져오기
	
	//1:1문의 조회
	
	//1:1문의 답변(메일 전송) & 삭제
	
}
