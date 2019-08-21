package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import admin.bean.AdminDTO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
/*
 * 메소드 실행후 수행되는 인터셉터
 */
@Component
public class AfterInterceptors extends HandlerInterceptorAdapter {
@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	//START 0.공통
		String uri = request.getRequestURI();
		if(uri.contains("/storage/")||uri.contains("/main/")) return;//storage,main상에서는 반응하지 않음	
		else if(uri.contains("/getBannerList.do")||uri.contains("/getUserProductList.do")) return;//배너와 상품 목록을 불러오는 과정에서도 반응하지 않음
		HttpSession session = request.getSession();	
		GuestDTO guestDTO = (GuestDTO) session.getAttribute("guestDTO");//임시계정 확인

	//END 0.공통			
		
	//START 1.로그인 상태 여부 확인:하나라도 접속해 있으면 해당 안됨+접속했으면 임시계정은 삭제되어야 함.
		AdminDTO adminDTO = (AdminDTO) session.getAttribute("adminDTO");
		if(adminDTO!=null) {
			if(guestDTO!=null) session.removeAttribute("guestDTO");
			return;
		}
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		if(memberDTO!=null) {
			if(guestDTO!=null) session.removeAttribute("guestDTO");
			return;
		}
	//END 1.로그인 상태 여부 확인

	}//posthandler

}//class
