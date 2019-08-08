package admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
 * 관리자: 회원 관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/user/**")
public class AdminUserController {

	//1. 관리자 회원 조회 화면 이동
	@RequestMapping(value="/userManage.do",method= RequestMethod.GET)
	public ModelAndView userManage() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location","adminUserInfo");		
			mav.addObject("display", "/admin/user/userManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//2. 회원 정보 가져오기
	
	//3. 1:1문의 목록으로 이동
	@RequestMapping(value="/personalQAManager.do",method= RequestMethod.GET)	
	public ModelAndView personalQAManager() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location","adminUserInfo");
			mav.addObject("display", "/admin/user/personalQAManager.jsp");
			mav.setViewName("/main/home");
			
		return mav;		
	}
	
	//4. 1:1문의 리스트 가져오기
	
	//5. 개별 1:1문의 조회 화면 이동
	
	//6. 1:1문의 답변(메일 전송) & 삭제
	
}
