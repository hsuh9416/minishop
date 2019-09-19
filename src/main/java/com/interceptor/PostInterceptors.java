package com.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import admin.bean.AdminDTO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
@Component
public class PostInterceptors extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HttpSession session = request.getSession();	
		
		GuestDTO visitorDTO = (GuestDTO)session.getAttribute("visitorDTO");//게스트 계정 존재여부 확인
		AdminDTO adminDTO = (AdminDTO) session.getAttribute("adminDTO");

		if(adminDTO!=null) {
			if(visitorDTO!=null) session.removeAttribute("visitorDTO");
			return;
		}
		
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");//현재 로그인 여부부터 확인
		if(memberDTO!=null) {
			if(visitorDTO!=null) session.removeAttribute("visitorDTO");
			return;
		}
		
		GuestDTO guestDTO = (GuestDTO)session.getAttribute("guestDTO");
		if(guestDTO!=null) {
			if(visitorDTO!=null) session.removeAttribute("visitorDTO");
			return;
		}
		
		//임의계정에 아이디 부여
		if(visitorDTO==null) {
			visitorDTO = new GuestDTO();
			visitorDTO.setGuest_id(UUID.randomUUID().toString());
			session.setAttribute("visitorDTO", visitorDTO);
		}

	}
}
