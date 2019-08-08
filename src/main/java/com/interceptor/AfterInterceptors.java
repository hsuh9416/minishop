package com.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import admin.bean.AdminDTO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
import trading.bean.OrderDTO;
/*
 * 메소드 실행후 수행되는 인터셉터
 */
@Component
public class AfterInterceptors extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AfterInterceptors.class);	
@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	//START 0.공통
		HttpSession session = request.getSession();	
		GuestDTO guestDTO = (GuestDTO) session.getAttribute("guestDTO");//임시계정 확인
		//logger.info("계정 확인 :"+guestDTO);
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
		
		OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
		if(orderDTO!=null) {
			if(guestDTO!=null) session.removeAttribute("guestDTO");
			return;
		}
	//END 1.로그인 상태 여부 확인

	//START 2.임시계정 생성:비로그인 상태에서 화면 전환등의 활동으로 임시계정이 자동 활성화
		if(guestDTO==null) {
			GuestDTO newGuest = new GuestDTO();
			newGuest.setGuest_name("GUEST");
			newGuest.setGuest_id(UUID.randomUUID().toString());//임시부여 ID
			logger.info("요청한 주소명: "+request.getRequestURI());
			logger.info("GuestID 생성!: "+newGuest.getGuest_id());
			session.setAttribute("guestDTO", newGuest);
		}
	//END 2.임시계정 생성

	}//posthandler

}//class
