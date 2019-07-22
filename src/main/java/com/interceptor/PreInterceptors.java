package com.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import admin.bean.AdminDTO;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import trading.bean.OrderDTO;
@Component
public class PreInterceptors extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(PreInterceptors.class);
	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//START 0.공통
		logger.info("요청한 주소명: "+request.getRequestURI());
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();	
		String[] addr = request.getRequestURI().split("\\.");
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");//현재 로그인 여부부터 확인
		//END 0.공통
		
		//START 1. 자동 로그인 확인
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		if(memberDTO==null) {//로그인이 되어 있지 않아야 시작
		if(loginCookie != null) {
			logger.info("자동로그인 대상자인지를 확인합니다.");
			 memberDTO = memberDAO.checkLoginBefore(loginCookie.getValue());
			if(memberDTO !=null) {
				logger.info("자동로그인 처리중");
				session.setAttribute("memberDTO",memberDTO);
				response.addCookie(loginCookie);
					if(addr[0].contains("/mallproject/main/home")) return true;//두번 리다이렉트하지 않음.
					else response.sendRedirect(request.getContextPath()+"/main/home.do");//어디서 접근하든 메인으로 일단 복귀		
				}
			}
		}
		//END 1.자동 로그인 확인
		
		//START 2.로그인 전 접근인지 확인
		if(uri.contains("/member/member")||//멤버 특수 메뉴 접근 불가
				uri.contains("personalQAForm")||//1대1 문의 접근 제어
				uri.contains("Modify")||//일체의 수정 제어
				uri.contains("Write")||//일체의 작성 제어
				uri.contains("admin")||//관리자 제어
				uri.contains("Reply")||//답글 제어
				uri.contains("logout")//로그아웃 제어
				) {
			logger.info("로그인 여부를 체크합니다.");			
		if(memberDTO == null) {		
			if(uri.contains("Orderlist")) {//해당 경우에 한해서 추가 체크
				OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
				if(orderDTO!=null) return true;}
			logger.info("올바르지 않은 접근입니다.");	
			if(addr[0].contains("/mallproject/main/home")) return false;
			else {response.sendRedirect(request.getContextPath()+"/main/home.do");
			return false;}
			}		
		}
		//END 2.로그인 전 접근인지 확인	
		
		//START 3.관리자 계정의 이중 접근인지 확인
		//관리자 로그인까지 처리후 쇼핑몰 내부로 이동하려 할 경우
		AdminDTO adminDTO = (AdminDTO) session.getAttribute("AdminDTO");
		
		if(adminDTO != null && !uri.contains("/admin/")) {//관리자계정이 개설된 상태에서 관리자 메뉴 이외로 접근할 경우 제지
			logger.info("관리자계정의 이중 접근은 불가능합니다.");
				response.sendRedirect(request.getContextPath()+"/admin/outterMain.do");
			return false;}
		//END 3. 관리자 계정의 이중 접근인지 확인
		
		//START 4. 로그인 후 로그인/회원가입 폼에 접근
			if((uri.contains("/member/loginForm") ||uri.contains("/member/writeForm")) && memberDTO !=null) {
			logger.info("로그인 후에 접근할 수 없는 경로입니다.");
				response.sendRedirect(request.getContextPath()+"/main/home.do");
			return false;}		
		//END 4. 로그인 후 로그인/회원가입 폼에 접근
		return true;
	}
	
}//class end
