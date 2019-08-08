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
			mav.addObject("location", "adminHome");
			mav.addObject("display", "/admin/shop/adminManage.jsp");//초기화면은 임시로 관리자 정보 화면
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//2. 관리자 계정 로그아웃(수정 예정: 마감 메소드 진행시켜야 함)
	@RequestMapping(value="/adminLogout.do",method=RequestMethod.GET)
	public ModelAndView adminLogout(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		session.removeAttribute("adminDTO");//관리자 계정은 로그아웃시에 무조건적으로 제거되어야 하며 자동 로그인도 불허한다.
		/*MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");//회원으로서의 관리자 세션 마감 처리
		if(memberDTO != null) {
			session.removeAttribute("memberDTO");
			session.invalidate();
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie != null) {
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				MemberDAO memberDAO = new MemberDAOImpl();
				memberDAO.keepLogin(memberDTO.getId(),"NONE",new Date());
			}
		} 관리자의 회원 계정은 회원 계정에서 로그아웃 시킨다.*/
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/template/body.jsp");
		mav.setViewName("/main/home");
		return mav;	
	}			
}
