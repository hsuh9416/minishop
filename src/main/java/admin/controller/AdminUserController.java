package admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import admin.bean.AdminDTO;
import admin.dao.AdminDAO;
import mail.bean.Mailing;
import mail.bean.MessageDTO;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import trading.dao.TradingDAO;
/*
 * 관리자: 회원 관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/user/**")
public class AdminUserController {

	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	TradingDAO tradingDAO;
	@Autowired
	Mailing mailing;
	
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
	@RequestMapping(value="/getMemberList.do",method=RequestMethod.GET)
	public ModelAndView getMemberList() {
		
		List<MemberDTO> memberList = memberDAO.getMemberList();
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("memberList",memberList);
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//3. 개별 회원 정보 조회 화면 이동
	@RequestMapping(value="/memberDetailView.do",method=RequestMethod.GET)
	public ModelAndView memberDetailView(@RequestParam String id) {
				
		ModelAndView mav = new ModelAndView();
			mav.addObject("id",id);
			mav.setViewName("/admin/user/memberDetailView");
		
		return mav;
	}
	
	//4. 개별 회원 정보 호출
	@RequestMapping(value="/getMemberDetail.do",method=RequestMethod.GET)
	public ModelAndView getMemberDetail(@RequestParam String id) {
		
		Map<String,Object> map = tradingDAO.getUserInfo(id);
			map.put("memberDTO", memberDAO.getUser(id));
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("memberInfo",map);
			mav.setViewName("jsonView");
		
		return mav;
	}	
	
	//5. 전체 혜택 지급 팝업창 이동
	@RequestMapping(value="/benefitGivingForm.do",method=RequestMethod.GET)
	public ModelAndView benefitGivingForm(@RequestParam(required = false,defaultValue = "all") String target) {
				
		ModelAndView mav = new ModelAndView();
			mav.addObject("target", target);
			mav.setViewName("/admin/user/benefitGivingForm");
		
		return mav;
	}

	//6.전체 공지 메일 팝업창 이동
	@RequestMapping(value="/infoWriteForm.do",method=RequestMethod.GET)
	public ModelAndView infoWriteForm(@RequestParam(required = false,defaultValue = "all") String target) {
				
		ModelAndView mav = new ModelAndView();
			mav.addObject("target", target);
			mav.setViewName("/admin/user/infoWriteForm");
		
		return mav;
	}

	//6.회원 메일 발송
	@RequestMapping(value="/infoWrite.do",method=RequestMethod.POST)
	@ResponseBody
	public String infoWrite(@RequestParam Map<String,String> map) {	

		String subject = map.get("subject");
		MessageDTO messageDTO = new MessageDTO();
			messageDTO.setSender("[Kissin' Bugs]");	
			messageDTO.setContent(map.get("content"));	
		AdminDTO adminDTO = adminDAO.getAdmin();	
		System.out.println(messageDTO.getContent());
		if(map.get("selectTarget").equals("all")) {
			subject = "[전체 알림 메일입니다] "+subject;
			messageDTO.setSubject(subject);	
			messageDTO.setContainHTML(true);
			
			List<MemberDTO> memberList = memberDAO.getMemberList();
			for(MemberDTO dto : memberList) {
				messageDTO.setReceiver(dto.getName());
				messageDTO.setReceiveAddr(dto.getEmail1()+"@"+dto.getEmail2());
				
				mailing.sendMail(adminDTO, messageDTO);
			}
			
			return "success";
		}
		else {
			MemberDTO memberDTO = memberDAO.getUser(map.get("id"));
			subject = "["+memberDTO.getName()+" 회원님] "+subject;	
				messageDTO.setSubject(subject);	
				messageDTO.setReceiveAddr(memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
				
				mailing.sendMail(adminDTO, messageDTO);
				
				return "success";
		}
		
	}
	
	//22. 1:1문의 목록으로 이동
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
