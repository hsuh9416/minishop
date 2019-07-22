package admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/admin/**")
public class AdminController {

	
	//관리자 화면
	@RequestMapping(value="/outterMain.do",method = RequestMethod.GET)
	public ModelAndView outterMain() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("adminDisplay", "/admin/shop/adminManage.jsp");
		mav.setViewName("/admin/outterMain");
		return mav;
	}	
	
	// 관리자 로그아웃(수정 예정: 마감 메소드 진행시켜야 함)
	@RequestMapping(value="/adminLogout.do",method=RequestMethod.GET)
	public ModelAndView adminLogout(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		session.removeAttribute("adminDTO");//관리자 계정은 로그아웃시에 무조건 적으로 제거해야 하면 자동 로그인도 불허한다.
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
