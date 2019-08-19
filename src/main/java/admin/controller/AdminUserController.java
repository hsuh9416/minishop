package admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import trading.bean.CouponDTO;
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
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//1. 관리자 회원 조회 화면 이동
	@RequestMapping(value="/userManage.do",method= RequestMethod.GET)
	public ModelAndView userManage() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location","userAdmin");		
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
		MemberDTO memberDTO = memberDAO.getUser(id);
		if(memberDTO.getState()==3) {
			MemberDTO deleteDTO = memberDAO.getDeleteRequest(memberDTO.getId());
				memberDTO.setDelete_mail(deleteDTO.getDelete_mail());
				memberDTO.setReason_etc(deleteDTO.getReason_etc());
				memberDTO.setDelete_date(deleteDTO.getDelete_date());
		}
			map.put("memberDTO", memberDTO);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("memberInfo",map);
			mav.setViewName("jsonView");
		
		return mav;
	}	
	
	//5. 개별 회원 정보 삭제
	@RequestMapping(value="/userDelete.do",method=RequestMethod.GET)
	@ResponseBody
	public String userDelete(@RequestParam String id) {
		MemberDTO memberDTO = memberDAO.getDeleteRequest(id);
		if(memberDTO==null) return "noRequestForDelete";
		else {
			
			long diff = new Date().getTime() - memberDTO.getDelete_date().getTime();
			long diffDays = diff/(24*60*60*1000);
			if(diffDays<=14) return "checkDuedateForDelete";
			else {
				int result = memberDAO.deleteUserInfo(memberDTO.getId());
				tradingDAO.deleteUserBenefit(memberDTO.getId());
				if(result!=0) return "success";
			}
		}
		return "fail";
	}
	//6. 개별 회원 복구 
	@RequestMapping(value="/userRestore.do",method=RequestMethod.GET)
	@ResponseBody
	public String userRestore(@RequestParam String id) {
		
		MemberDTO memberDTO = memberDAO.getUser(id);
		if(memberDTO!=null) {
			int result = memberDAO.makeUserRestored(memberDTO.getId());

			if(result!=0) {
				String email = memberDTO.getEmail1()+"@"+memberDTO.getEmail2();
				MemberDTO deleteDTO = memberDAO.getDeleteRequest(id);
				if(deleteDTO!=null) email = deleteDTO.getDelete_mail();
				
				MessageDTO messageDTO = new MessageDTO();
					messageDTO.setReceiver(memberDTO.getName());			
					messageDTO.setReceiveAddr(email);
				String resetPwd = mailing.getKey(8);
					mailing.sendRestoreMail(messageDTO,resetPwd);
					
				AdminDTO adminDTO = adminDAO.getAdmin();
					mailing.sendMail(adminDTO, messageDTO);
					
					memberDTO.setPwd(passwordEncoder.encode(resetPwd));
					memberDAO.setNewPwd(memberDTO);		
					
					memberDAO.deleteRequest(memberDTO.getId());
				return "success";
			}
			else return "fail";
		}
		else return "fail";
		
	}	
	
	//6. 전체 혜택 지급 팝업창 이동
	@RequestMapping(value="/benefitGivingForm.do",method=RequestMethod.GET)
	public ModelAndView benefitGivingForm(@RequestParam(required = false,defaultValue = "all") String target) {
				
		ModelAndView mav = new ModelAndView();
			mav.addObject("target", target);
			mav.setViewName("/admin/user/benefitGivingForm");
		
		return mav;
	}

	//7. 쿠폰 유효성 체크
	@RequestMapping(value="/checkCouponState.do",method=RequestMethod.POST)
	@ResponseBody
	public String checkCouponState(@RequestParam Map<String,String> map) {
		
		CouponDTO couponDTO = tradingDAO.getSelectedCoupon(map.get("coupon_no"));
		
		if((map.get("selectTarget").equals("person")&&couponDTO.getCoupon_target()==0)||
		   (map.get("selectTarget").equals("all")&&couponDTO.getCoupon_target()==1)) 
		{
			return "typeMissMatching";
		}
		else {
			List<CouponDTO> couponList = tradingDAO.getGivenCoupon(map.get("coupon_no"));
			if(map.get("selectTarget").equals("all")) {
				if(couponList==null||couponList.size()==0) return "available";
				else return "preIssued";
			}
			else {
				for(CouponDTO coupon : couponList) {
					if(coupon.getGrant_id().equals(map.get("id"))) return "preIssued";
				}
			}
		}
		return "available";
	}
	
	//8.회원 쿠폰 발급
	@RequestMapping(value="/issueCoupon.do",method=RequestMethod.POST)
	@ResponseBody
	public String issueCoupon(@RequestParam Map<String,String> map) {	
		
		String personalCode = "";
		String benefitInfo = "[쿠폰번호] "+map.get("coupon_no")+" [지급번호] "+personalCode+"(자세한 사항은 개인 계정 정보를 참조하시기 바랍니다)";
		String subject = "[특별쿠폰발급] "+map.get("subject");
		
		MessageDTO messageDTO = new MessageDTO();
		String content = StringEscapeUtils.unescapeHtml4(map.get("content"));	
			messageDTO.setSubject(subject);
			messageDTO.setContainHTML(true);
		AdminDTO adminDTO = adminDAO.getAdmin();	
		CouponDTO couponDTO = tradingDAO.getSelectedCoupon(map.get("coupon_no"));
		
		SimpleDateFormat targetDate = new SimpleDateFormat("yyyy-MM-dd");
		String dueDateString ="9999-12-31";
			if(map.get("coupon_duedate")!=null&&!map.get("coupon_duedate").equals("")&&!map.get("coupon_duedate").equals("permanent")) {
				dueDateString = map.get("coupon_duedate");}			
			try {
				Date dueDate = targetDate.parse(dueDateString);
				if(dueDate.before(new Date())) return "dueDateError";
				else couponDTO.setCoupon_duedate(dueDate);}
			catch (ParseException e) {return "fail";}
				
		if(map.get("selectTarget").equals("all")) {			
			List<MemberDTO> memberList = memberDAO.getMemberList();
			for(MemberDTO dto : memberList) {
				if(dto.getState()==0) continue;
				else if(dto.getState()==3) continue;
				couponDTO.setGrant_id(dto.getId());
				personalCode = UUID.randomUUID().toString();
				couponDTO.setPersonal_code(personalCode);
				benefitInfo = "[쿠폰번호] "+map.get("coupon_no")+" [지급번호] "+personalCode+"(자세한 사항은 개인 계정 정보를 참조하시기 바랍니다)";
				
				tradingDAO.setCoupon(couponDTO);
					
				messageDTO.setContent(content);	
				messageDTO.setReceiver(dto.getName());
				messageDTO.setReceiveAddr(dto.getEmail1()+"@"+dto.getEmail2());
				messageDTO =mailing.sendBenefitGrantMail(messageDTO, benefitInfo);
				
				mailing.sendMailwithFile(adminDTO, messageDTO);
			}
			
			return "success";
		}
		else {
			MemberDTO memberDTO = memberDAO.getUser(map.get("id"));
			if(memberDTO.getState()==0) return "adminExcept";
			else if(memberDTO.getState()==3) return "invalidUserExcept";
				couponDTO.setGrant_id(memberDTO.getId());
				personalCode = UUID.randomUUID().toString();
				couponDTO.setPersonal_code(personalCode);
				benefitInfo = "[쿠폰번호] "+map.get("coupon_no")+" [지급번호] "+personalCode+"(자세한 사항은 개인 계정 정보를 참조하시기 바랍니다)";		
				
				tradingDAO.setCoupon(couponDTO);
				
				messageDTO.setContent(content);	
				messageDTO.setReceiver(memberDTO.getName());
				messageDTO.setReceiveAddr(memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
				messageDTO =mailing.sendBenefitGrantMail(messageDTO, benefitInfo);
				
				mailing.sendMailwithFile(adminDTO, messageDTO);
				
				return "success";
		}
		
	}
	
	//9.회원 포인트 지급
	@RequestMapping(value="/grantPoint.do",method=RequestMethod.POST)
	@ResponseBody
	public String grantPoint(@RequestParam Map<String,String> map) {	
		String benefitInfo = "[지급포인트] 총"+map.get("pointQty")+"(점)";
		String subject = "[특별포인트지급] "+map.get("subject");
		
		MessageDTO messageDTO = new MessageDTO();
		String content = StringEscapeUtils.unescapeHtml4(map.get("content"));	
			messageDTO.setSubject(subject);
			messageDTO.setContainHTML(true);			
		AdminDTO adminDTO = adminDAO.getAdmin();	
		
		if(map.get("selectTarget").equals("all")) {
			
			List<MemberDTO> memberList = memberDAO.getMemberList();
			for(MemberDTO dto : memberList) {
				if(dto.getState()==0) continue;
				else if(dto.getState()==3) continue;
				memberDAO.setPoint(dto.getId(),map.get("pointQty"));
				messageDTO.setContent(content);	
				messageDTO.setReceiver(dto.getName());
				messageDTO.setReceiveAddr(dto.getEmail1()+"@"+dto.getEmail2());
				messageDTO =mailing.sendBenefitGrantMail(messageDTO, benefitInfo);
				
				mailing.sendMailwithFile(adminDTO, messageDTO);
			}
			
			return "success";
		}
		else {
			MemberDTO memberDTO = memberDAO.getUser(map.get("id"));
			if(memberDTO.getState()==0) return "adminExcept";
			else if(memberDTO.getState()==3) return "invalidUserExcept";
				memberDAO.setPoint(memberDTO.getId(),map.get("pointQty"));
				messageDTO.setContent(content);	
				messageDTO.setReceiver(memberDTO.getName());
				messageDTO.setReceiveAddr(memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
				messageDTO =mailing.sendBenefitGrantMail(messageDTO, benefitInfo);
				
				mailing.sendMailwithFile(adminDTO, messageDTO);
				
				return "success";
		}
		
	}
	
	//10.전체 공지 메일 팝업창 이동
	@RequestMapping(value="/infoWriteForm.do",method=RequestMethod.GET)
	public ModelAndView infoWriteForm(@RequestParam(required = false,defaultValue = "all") String target) {
				
		ModelAndView mav = new ModelAndView();
			mav.addObject("target", target);
			mav.setViewName("/admin/user/infoWriteForm");
		
		return mav;
	}

	//11.회원 메일 발송
	@RequestMapping(value="/infoWrite.do",method=RequestMethod.POST)
	@ResponseBody
	public String infoWrite(@RequestParam Map<String,String> map) {	

		String subject = map.get("subject");
		MessageDTO messageDTO = new MessageDTO();
			messageDTO.setSender("[Kissin' Bugs]");	
		String content = StringEscapeUtils.unescapeHtml4(map.get("content"));	
			messageDTO.setContent(content);	
			messageDTO.setContainHTML(true);			
		AdminDTO adminDTO = adminDAO.getAdmin();	
		
		if(map.get("selectTarget").equals("all")) {
			subject = "[전체 알림 메일입니다] "+subject;
			messageDTO.setSubject(subject);	
			
			List<MemberDTO> memberList = memberDAO.getMemberList();
			for(MemberDTO dto : memberList) {
				if(dto.getState()==0) continue;
				messageDTO.setReceiver(dto.getName());
				messageDTO.setReceiveAddr(dto.getEmail1()+"@"+dto.getEmail2());
				
				mailing.sendMailwithFile(adminDTO, messageDTO);
			}
			
			return "success";
		}
		else {
			MemberDTO memberDTO = memberDAO.getUser(map.get("id"));
			if(memberDTO.getState()==0) return "adminExcept";
			subject = "["+memberDTO.getName()+" 회원님] "+subject;	
				messageDTO.setSubject(subject);	
				messageDTO.setReceiver(memberDTO.getName());
				messageDTO.setReceiveAddr(memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
				
				mailing.sendMailwithFile(adminDTO, messageDTO);
				
				return "success";
		}
		
	}
	
	//12. 1:1문의 목록으로 이동
	@RequestMapping(value="/personalQAManager.do",method= RequestMethod.GET)	
	public ModelAndView personalQAManager() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location","userAdmin");
			mav.addObject("display", "/admin/user/personalQAManager.jsp");
			mav.setViewName("/main/home");
			
		return mav;		
	}
	
	//13. 1:1문의 리스트 가져오기
	@RequestMapping(value="/getPersonalQAList.do",method=RequestMethod.GET)
	@ResponseBody	
	public ModelAndView getPersonalQAList() {
		ModelAndView mav = new ModelAndView();
		 List<MessageDTO> personalQAList = adminDAO.getPersonalQAList();
			mav.addObject("personalQAList",personalQAList);
			mav.setViewName("jsonView");
		
		return mav;				
	}
	
	//5. 개별 1:1문의 팝업창 이동
	@RequestMapping(value="/personalQAFormAdmin.do",method=RequestMethod.GET)
	public ModelAndView personalQAFormAdmin(@RequestParam String seq) {
		ModelAndView mav = new ModelAndView();
		 MessageDTO messageDTO = adminDAO.getPersonalQA(seq);
			mav.addObject("messageDTO",messageDTO);
			mav.setViewName("/admin/user/personalQAFormAdmin");
		return mav;				
	}	
	
	//6. 1:1문의 답변(메일 전송) & 삭제
	@RequestMapping(value="/replyPersonalQA.do",method=RequestMethod.POST)
	@ResponseBody
	public String replyPersonalQA(@ModelAttribute MessageDTO messageDTO) {
		
		String subject = "[고객님의 1:1문의에 대한 답변입니다] RE: "+messageDTO.getSubject();
		String content = StringEscapeUtils.unescapeHtml4(messageDTO.getContent());
			messageDTO.setSubject(subject);
			messageDTO.setSender("[Kissin' Bugs]");
			messageDTO.setContent(content);
			messageDTO.setContainHTML(true);
		AdminDTO adminDTO = adminDAO.getAdmin();
			
			mailing.sendMailwithFile(adminDTO, messageDTO);
			adminDAO.deleteQA(messageDTO.getSeq()+"");
		
			return "success";
	}		
}
