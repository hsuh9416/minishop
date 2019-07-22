package member.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import admin.bean.AdminDTO;
import admin.dao.AdminDAO;
import mail.bean.Mailing;
import mail.bean.MessageDTO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import trading.dao.TradingDAO;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private TradingDAO tradingDAO;
	@Autowired
	private Mailing mailing;
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//로그인창 띄우기 : 모달창
	@RequestMapping(value="/loginModal.do",method = RequestMethod.GET)
	public ModelAndView loginModal() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/loginModal");
		return mav;
	}
	//로그인 화면
	@RequestMapping(value="/loginForm.do",method=RequestMethod.GET)
	public ModelAndView loginForm(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "loginForm");		
		mav.addObject("display", "/member/loginForm.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}	
	//비번찾기 화면
	@RequestMapping(value="/findForm.do",method=RequestMethod.GET)
	public ModelAndView findForm(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "findForm");		
		mav.addObject("display", "/member/findForm.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}		
	
	/*
	//로그인 체크
	@RequestMapping(value="/login.do",method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam String id, String pwd,String autoLogin,HttpSession session,HttpServletResponse response){
		MemberDTO memberDTO = memberDAO.checkId(id);
		if(memberDTO==null) {
			GuestDTO questDTO = memberDAO.orderCheck(id,pwd);//회원이 아니면 orderCheck 먼저
			if(questDTO==null) return "fail";
			else {
				session.setAttribute("questDTO", questDTO);
				return "guestLogin";}
		}	
		//존재하는 경우에는 비번체크
			String objectPwd = memberDTO.getPwd();
			if(passwordEncoder.matches(pwd, objectPwd)) {//일치의 경우
				if(memberDTO.getState()==3) return "invalidate";//정지된 계정 여부 체크
				else {
					session.setAttribute("memberDTO", memberDTO);
					if(memberDTO.getState()==0) {
						AdminDTO adminDTO = adminDAO.getAdmin();//차후 admin 접근을 위한 별로 로그인 추가할 것
						session.setAttribute("adminDTO", adminDTO);	
						System.out.println("관리자의 멤버 계정 : "+session.getAttribute("memberDTO"));
						System.out.println("관리자의 관리자 계정 : "+session.getAttribute("adminDTO"));						
						return  "adminLogin";
					}
					String sessionId = session.getId();
					Cookie loginCookie = new Cookie("loginCookie",session.getId());
					loginCookie.setPath("/");
					loginCookie.setMaxAge(60*60*24*2);//이틀 간 유효
					response.addCookie(loginCookie);
					if(autoLogin.equals("1")) {//로그인 유지 체크 시
							int amount =60*60*24*2;//2일
							Date sessionLimit = new Date(System.currentTimeMillis()+(1000*amount));
							memberDAO.keepLogin(id, sessionId, sessionLimit);
												}
						return "userLogin";
				}
			}	
			return "fail";
}

	//로그아웃
	@RequestMapping(value="/logout.do",method=RequestMethod.GET)
	@ResponseBody
	public void logout(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		Object object = session.getAttribute("memberDTO");
		if(object != null) {
			MemberDTO memberDTO = (MemberDTO) object;
			session.removeAttribute("memberDTO");
			session.invalidate();
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie != null) {
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				memberDAO.keepLogin(memberDTO.getId(),"NONE",new Date());
			}
		}
		
	}	
*/

	//회원가입 화면
	@RequestMapping(value="/writeForm.do",method=RequestMethod.GET)
	public ModelAndView writeForm(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "writeForm");		
		mav.addObject("display", "/member/writeForm.jsp");
		mav.addObject("menu", "/template/left.jsp");			
		mav.setViewName("/main/home");
		return mav;
	}
		
	//아이디 체크
	@RequestMapping(value="/checkId.do",method=RequestMethod.POST)
	@ResponseBody
	public String checkId(@RequestParam String id) {
		MemberDTO memberDTO = memberDAO.checkId(id);
		if(memberDTO==null)
			return "not_exist";
		else
			return "exist";
	}
	
	//이메일 인증
	@RequestMapping(value="/checkEmail.do",method=RequestMethod.POST)
	@ResponseBody
	public String checkEmail(@RequestParam String email){
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setReceiver("예비 회원님");
		messageDTO.setReceiveAddr(email);
		String checkNum = mailing.checkNum();
		messageDTO = mailing.sendConfirmMail(messageDTO, checkNum);
		AdminDTO adminDTO = adminDAO.getAdmin();
		mailing.sendMail(adminDTO, messageDTO);//메일 전송
		
		return checkNum;//인증번호는 회수함.
	}	
		
	//회원 가입
	@RequestMapping(value="/write.do",method=RequestMethod.POST)
	@ResponseBody
	public String write(@ModelAttribute MemberDTO memberDTO) {
		String pwd = passwordEncoder.encode(memberDTO.getPwd());
		memberDTO.setPwd(pwd);
		int result = memberDAO.write(memberDTO);
		if(result!=0) {		
			//회원 가입 메일 송신(쿠폰설정은 차기 개선할 것)
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setReceiver(memberDTO.getName()+"님");
			messageDTO.setReceiveAddr(memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
			messageDTO = mailing.sendWelcomeMail(messageDTO);
			AdminDTO adminDTO = adminDAO.getAdmin();
			mailing.sendMail(adminDTO, messageDTO);//메일 전송	
			return "success";	
		}	
		else return "fail";
	}
	
	//회원정보수정 화면
	@RequestMapping(value="/memberModifyForm.do",method=RequestMethod.GET)
	public ModelAndView memberModifyForm(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/member/memberModifyForm.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	

	//회원 정보수정
	@RequestMapping(value="/memberModify.do",method=RequestMethod.POST)
	@ResponseBody
	public String modify(@ModelAttribute MemberDTO memberDTO,HttpSession session) {
		String pwd = passwordEncoder.encode(memberDTO.getPwd());
		memberDTO.setPwd(pwd);
		int result = memberDAO.modify(memberDTO);
		session.setAttribute("memberDTO", memberDTO);
		if(result==1) return "success";		
		else return "fail";
	}
	
	//회원 정보 화면
	@RequestMapping(value="/memberView.do",method=RequestMethod.GET)
	public ModelAndView memberView(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/member/memberView.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	
	
	//회원 주문 현황 화면
	@RequestMapping(value="/memberOrderlist.do",method=RequestMethod.GET)
	public ModelAndView memberOrderlist(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/member/memberOrderlist.jsp");
		mav.setViewName("/main/home");
		return mav;
	}		
	//회원 관리 정보 불러오기
	@RequestMapping(value="/getUserInfo.do",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView getUserInfo(HttpSession session) {;
		Map<String,Object> map = new HashMap<String,Object>();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		map = tradingDAO.getUserInfo(memberDTO.getId());		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("jsonView");
		return mav;
	}
	
	
	//개인문의 창 띄우기 : 모달창
	@RequestMapping(value="/personalQAForm.do",method = RequestMethod.GET)
	public ModelAndView personalQAForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/personalQAForm");
		return mav;
	}
	//개인문의 보내기->관리자 수령
	@RequestMapping(value="/memberQASend",method = RequestMethod.POST)
	@ResponseBody
	public void memberQASend(@ModelAttribute MessageDTO messageDTO, HttpSession session) {
		MemberDTO admin = memberDAO.getAdmin();
		
		messageDTO.setReceiver(admin.getName());
		messageDTO.setReceiveAddr(admin.getEmail1()+"@"+admin.getEmail2());
		
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		if(memberDTO == null) {GuestDTO guestDTO = (GuestDTO) session.getAttribute("guestDTO");
			messageDTO.setSender(guestDTO.getGuest_name());
		}
		else {messageDTO.setSender(memberDTO.getName());
		}

		
		memberDAO.memberQASend(messageDTO);
		
	}
	
	
	//탈퇴 요청 띄우기 : 모달창
	@RequestMapping(value="/memberDelete.do",method = RequestMethod.GET)
	public ModelAndView memberDelete() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/memberDelete");
		return mav;
	}	

	//탈퇴 처리
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam String pwd,HttpServletRequest request,HttpServletResponse response, HttpSession session){
		//관리자의 DB에 멤버 제거요청 상태로 업데이트-관리자가 최종 제거해야함.

		MemberDTO memberDTO  = (MemberDTO)session.getAttribute("memberDTO");

		if(memberDTO != null) {		String objectPwd = memberDTO.getPwd();
			if(passwordEncoder.matches(pwd, objectPwd)) {
				String id = memberDTO.getId();
				memberDAO.deleteMember(id);
			}
			else return "fail";
		}
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		if(loginCookie != null) {
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
			memberDAO.keepLogin(memberDTO.getId(),"NONE",new Date());
		}
		session.removeAttribute("memberDTO");
		session.invalidate();
		return "success";		
	}	

}
