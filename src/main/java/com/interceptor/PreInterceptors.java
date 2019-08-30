package com.interceptor;

import java.util.List;

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
import member.bean.GuestDTO;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import product.bean.ProductDTO;
import trading.bean.JsonTransitioner;
import trading.bean.ShoppingCart;
import trading.dao.TradingDAO;
/*
 * 메소드 실행전 실행되는 인터셉터
 */
@Component
public class PreInterceptors extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(PreInterceptors.class);
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private TradingDAO tradingDAO;
	@Autowired
	JsonTransitioner jsonTrans;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//START 0.공통
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();	
		String[] addr = request.getRequestURI().split("\\.");
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");//현재 로그인 여부부터 확인
		GuestDTO guestDTO = (GuestDTO)session.getAttribute("guestDTO");
		if(uri.contains("/storage/")||uri.contains("/main/")) return true;//storage,main 누구나 접근 가능함
		else if(uri.contains("/getBannerList.do")||uri.contains("/getUserProductList.do")) return true;//배너와 상품 목록을 불러오는 과정에도 유효성 검사 배제
		//END 0.공통/
		
		//START 1. 자동 로그인 확인
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		if(memberDTO==null) {//로그인이 되어 있지 않아야 시작
			logger.info("요청한 주소명: "+request.getRequestURI());
		if(loginCookie != null) {
			logger.info("자동로그인 대상자인지를 확인합니다.");
			 memberDTO = memberDAO.checkLoginBefore(loginCookie.getValue());
			if(memberDTO !=null) {
				logger.info("자동로그인 처리중");
				session.setAttribute("memberDTO",memberDTO);
				//장바구니 호출
				ShoppingCart shoppingCart = tradingDAO.getCartList(memberDTO.getId());
				if(shoppingCart!=null) {
					
					String json = shoppingCart.getCartList_json();
					List<ProductDTO> cartList = jsonTrans.makeJsonToList(json);
					if(cartList!=null) {
						session.setAttribute("cartList", cartList);
						shoppingCart.setCartList(cartList);
					}
				session.setAttribute("shoppingCart", shoppingCart);
				}
				response.addCookie(loginCookie);
					if(addr[0].contains("/minishop/main/home")) return true;//두번 리다이렉트하지 않음.
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
				uri.contains("Reply")||//답글 제어
				uri.contains("logout")||//로그아웃 제어
				uri.contains("/trading/orderView")//주문내역 확인
				) {
			logger.info("요청한 주소명: "+request.getRequestURI());
			logger.info("로그인 여부를 체크합니다.");			
		if(memberDTO == null) {		
			if((uri.contains("/trading/orderView")||
				uri.contains("/qa/")||
				uri.contains("/review/")||
				uri.contains("logout")||
				uri.contains("memberQASend")||
				uri.contains("personalQAForm")) && guestDTO!=null) return true;
			logger.info("올바르지 않은 접근입니다.");	
			response.sendRedirect(request.getContextPath()+"/common/noLogin.jsp");//경고 페이지 이동
			return false;
			}		
		}
		//END 2.로그인 전 접근인지 확인	
		
		//START 3.관리자 계정의 이중 접근인지 확인
		//관리자 로그인까지 처리후 쇼핑몰 내부로 이동하려 할 경우(일부 메소드 제외)
		AdminDTO adminDTO = (AdminDTO) session.getAttribute("adminDTO");
		if(adminDTO == null && uri.contains("/admin/")) {//관리자가 아닌데 또는 관리자 로그인 없이 관리자페이지에 접근하는 경우
			logger.info("요청한 주소명: "+request.getRequestURI());
			logger.info("관리자계정만 접근 가능합니다.");
			response.sendRedirect(request.getContextPath()+"/common/inaccessible.jsp");//경고 페이지 이동
			return false;			
		}
		else if(adminDTO != null && !uri.contains("/admin/")&&!uri.contains("/storage/")&&!uri.contains("/trading/")) {//관리자계정이 개설된 상태에서 관리자 메뉴 이외로 접근할 경우 제지
			logger.info("요청한 주소명: "+request.getRequestURI());
			logger.info("관리자계정의 이중 접근은 불가능합니다.");
				response.sendRedirect(request.getContextPath()+"/admin/adminHome.do");
			return false;}
		//END 3. 관리자 계정의 이중 접근인지 확인
		
		//START 4. 로그인 후 로그인/회원가입 폼에 접근
			if((uri.contains("/member/loginForm") ||
				uri.contains("/member/loginModal") ||
				uri.contains("/member/findForm") ||				
				uri.contains("/member/writeForm")) && (memberDTO !=null||guestDTO!=null)) {
				logger.info("요청한 주소명: "+request.getRequestURI());
				logger.info("로그인 후에 접근할 수 없는 경로입니다.");
				response.sendRedirect(request.getContextPath()+"/main/home.do");
			return false;}		
		//END 4. 로그인 후 로그인/회원가입 폼에 접근
		return true;
	}
	
}//class end
