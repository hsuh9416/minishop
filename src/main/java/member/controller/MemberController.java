package member.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import product.bean.ProductDTO;
import trading.bean.OrderDTO;
import trading.bean.ShoppingCart;
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
	
	//로그인창 띄우기 : 팝업창
	@RequestMapping(value="/loginPopup.do",method = RequestMethod.GET)
	public ModelAndView loginPopup() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/loginPopup");
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
		
	//로그인 체크(주문조회도 로그인으로 받음)
	@RequestMapping(value="/login.do",method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam String id, String pwd,String autoLogin,HttpSession session,HttpServletResponse response,Model model){

		MemberDTO memberDTO = memberDAO.checkId(id);
		if(memberDTO==null) {
			OrderDTO orderDTO = memberDAO.orderCheck(id,pwd);//회원이 아니면 orderCheck 먼저
			if(orderDTO==null) return "fail";
			else {
				session.setAttribute("orderDTO", orderDTO);
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
					//장바구니 호출
					ShoppingCart shoppingCart = tradingDAO.getCartList(memberDTO.getId());
					System.out.println(shoppingCart);
					if(shoppingCart!=null) {
						
						String json = shoppingCart.getCartList_json();
						List<ProductDTO> cartList = shoppingCart.makeJsonToList(json);
						if(cartList!=null) {
							session.setAttribute("cartList", cartList);
							shoppingCart.setCartList(cartList);
						}
					session.setAttribute("shoppingCart", shoppingCart);
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
			ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
			if(shoppingCart!=null) {//장바구니가 개설된 때에만 저장한다.
				List<ProductDTO> cartList = shoppingCart.getCartList();
				if(cartList!=null) {//cartList에 담긴 것이 없으면 역시 저장하지 않는다.
					//cartList ->JSON String
					String cartList_json = shoppingCart.makeListToJson(cartList);
					shoppingCart.setCartList_json(cartList_json);
					shoppingCart.setMemberid(memberDTO.getId());
					tradingDAO.storeCartList(shoppingCart);					
				}
		
			}
				
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
	public String checkEmail(@RequestParam String email,HttpSession session){
		MessageDTO messageDTO = new MessageDTO();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		if(memberDTO!=null) {//null이 아니면 가입이 아닌 이메일 변경
			messageDTO.setReceiver(memberDTO.getName()+" 회원님");			
		}
		else {messageDTO.setReceiver("예비 회원님");}
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
	
	//아이디/비밀번호 검색 화면
	@RequestMapping(value="/findForm.do",method=RequestMethod.GET)
	public ModelAndView findForm() {
	  ModelAndView mav = new ModelAndView();
		mav.addObject("location", "memberfind");
		mav.addObject("display", "/member/findForm.jsp");
		mav.setViewName("/main/home");
		return mav;	  
	}
	//아이디 검색
	@RequestMapping(value="/findLostId.do",method=RequestMethod.POST)
	@ResponseBody
	public String findLostId(@RequestParam Map<String,String> map) {
		MemberDTO memberDTO = memberDAO.findLostId(map);
		if(memberDTO==null) return "not_exist"; 
		String id = memberDTO.getId();
		StringBuffer data= new StringBuffer();
		String result;
		if(id.length()>=5) {
			for(int i=0;i<id.length()-4;i++) {
				data.append("*");}
			result = data+"";
			result = id.substring(0, 4)+result;//4글자까지 출력하도록 함	
		 }
		else {
			for(int i=0;i<id.length()-2;i++) {
				data.append("*");}
			result = data+"";
			result = id.substring(0, 2)+result;//2글자까지 출력하도록 함	
		}
		return result;
	}
	
	//비밀번호 재설정 메일 전송
	@RequestMapping(value="/findLostPwd.do",method=RequestMethod.POST)
	@ResponseBody
	public String findLostPwd(@RequestParam Map<String,String> map){
		String id = map.get("findID");
		MemberDTO memberDTO = memberDAO.checkId(id);//아이디로 우선 검색
		if(memberDTO==null|| !memberDTO.getEmail1().equals(map.get("findPwdemail1")) || !memberDTO.getEmail2().equals(map.get("findPwdemail2")) ) {
			return "not_exist";
		}
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setReceiver(memberDTO.getName()+" 님");
		messageDTO.setReceiveAddr(memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
		String resetPwd = mailing.getKey(8);//8자리 난수 발생
		messageDTO = mailing.sendResetPwdMail(messageDTO, resetPwd);
		AdminDTO adminDTO = adminDAO.getAdmin();
		mailing.sendMail(adminDTO, messageDTO);//메일 전송
		memberDTO.setPwd(passwordEncoder.encode(resetPwd));
		memberDAO.setNewPwd(memberDTO);
		return resetPwd;//인증번호는 회수함.
	}	
	
	//회원정보수정 화면
	@RequestMapping(value="/memberModifyForm.do",method=RequestMethod.GET)
	public ModelAndView memberModifyForm(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "memberModify");
		mav.addObject("display", "/member/memberModifyForm.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	
	//비번 변경 폼
	@RequestMapping(value="/changePwdForm.do",method=RequestMethod.GET)
	public ModelAndView changePwdForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/changePwdForm");
		return mav;		
	}
	//비번 체크
	@RequestMapping(value="/checkPwd.do",method=RequestMethod.POST)
	@ResponseBody	
	public String checkPwd(@RequestParam String pwd,HttpSession session) {
	MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
	String objectPwd = memberDTO.getPwd();
	if(passwordEncoder.matches(pwd, objectPwd)) {
		return "success";
	}
	else {
		return "fail";
		}
	}	
	//비번 변경
	@RequestMapping(value="/changePwd.do",method=RequestMethod.POST)
	@ResponseBody
	public String changePwd(@RequestParam Map<String,String> map,HttpSession session) {
	MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
	String objectPwd = memberDTO.getPwd();
	if(!passwordEncoder.matches(map.get("pwd"), objectPwd)) {
		return "fail";
	}
	else {
		String newPwd = passwordEncoder.encode(map.get("newPwd"));
		memberDTO.setPwd(newPwd);	
		memberDAO.setNewPwd(memberDTO);	
		session.setAttribute("memberDTO", memberDTO);		
		return "success";
		}
	}
	//이메일 변경 폼
	@RequestMapping(value="/changeEmailForm.do",method=RequestMethod.GET)
	public ModelAndView changeEmailForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/member/changeEmailForm");
		return mav;		
	}
	
	//회원 정보수정(비번 제외)
	@RequestMapping(value="/memberModify.do",method=RequestMethod.POST)
	@ResponseBody
	public String modify(@ModelAttribute MemberDTO memberDTO,HttpSession session) {
		int result = memberDAO.modify(memberDTO);
		MemberDTO renew = memberDAO.getUser(memberDTO.getId());
		session.setAttribute("memberDTO", renew);
		if(result==1) return "success";		
		else return "fail";
	}
	
	//회원 정보 화면
	@RequestMapping(value="/memberView.do",method=RequestMethod.GET)
	public ModelAndView memberView(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("location", "memberView");
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
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
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

	
}//class end
