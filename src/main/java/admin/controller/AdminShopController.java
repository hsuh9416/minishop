package admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import trading.bean.CouponDTO;
import trading.bean.EventDTO;
import trading.dao.TradingDAO;
/*
 * 관리자: 관리자 및 상점 정보를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/shop/**")
public class AdminShopController {

	@Autowired
	TradingDAO tradingDAO;
	
	//1. 관리자 정보 화면 이동
	@RequestMapping(value="/adminManage.do",method = RequestMethod.GET)
	public ModelAndView adminManage() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "adminHome");
			mav.addObject("display", "/admin/shop/adminManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	

	//2. 관리자 정보 수정 화면 이동
	@RequestMapping(value="/adminManageForm.do",method = RequestMethod.GET)
	public ModelAndView adminManageForm() {
		
		ModelAndView mav = new ModelAndView();
			mav.setViewName("/admin/shop/adminManageForm");
			
		return mav;
	}	
	
	//3. 매출 정보 화면 이동
	@RequestMapping(value="/salesInfo.do",method = RequestMethod.GET)
	public ModelAndView salesInfo() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "adminHome");
			mav.addObject("display", "/admin/shop/salesInfo.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//4.이벤트 관리 화면 이동
	@RequestMapping(value="/eventManage.do",method = RequestMethod.GET)
	public ModelAndView eventManage(@RequestParam(required = false) String event_no) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "adminHome");
			mav.addObject("event_no", event_no);			
			mav.addObject("display", "/admin/shop/eventManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}		
	
	//5. 배너 호출
	@RequestMapping(value="/getSelectedBanner.do",method=RequestMethod.GET)
	public ModelAndView getSelectedBanner(String event_no) {
		
		EventDTO banner = tradingDAO.getSelectedBanner(event_no);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("banner", banner);
			mav.setViewName("jsonView");
		
	return mav;
	}
	
	//6. 배너  수정하기
	@RequestMapping(value="/bannerModify.do",method=RequestMethod.POST)
	public ModelAndView bannerModify(@ModelAttribute EventDTO eventDTO, @RequestParam MultipartFile event_image_new, String start_date, String end_date, HttpServletRequest request){
		
		boolean result;
		Date event_startdate = null;
		Date event_enddate = null;
		int post;
		String ext;
		String filePath;
		String originalfileName;
		String state="fail";
		String uploadfileName;
		String uploadPath;
	    String[] images;
	    		
			//(1) 받아온 글자를 date 형식으로 치환
		SimpleDateFormat targetDate = new SimpleDateFormat("yyyy-mm-dd");
		try {
			if(start_date==null||start_date.equals("")) {
				Date today = new Date();
				event_startdate= today;}
			else {
					event_startdate = targetDate.parse(start_date);}
				
				event_enddate = targetDate.parse(end_date);		
			}
			catch (ParseException e) {e.printStackTrace();}

				eventDTO.setEvent_startdate(event_startdate);
				eventDTO.setEvent_enddate(event_enddate);
			
		
			//(2) 이미지 양식 검증(검증 이후 파일 이름을 반환)
			if(!event_image_new.isEmpty()) {
				originalfileName = event_image_new.getOriginalFilename();


				
				post = originalfileName.lastIndexOf(".");
				ext = originalfileName.substring(post + 1).toLowerCase();
				images = new String[]{"jpg", "jpeg", "png", "gif", "bmp"};
				result = false;
				
	        for (String str : images) {
	            if (str.equals(ext)) {
	                result = true;
	                break;}}
	        
	        if(!result) {state = "WrongFile";}
	        else {originalfileName = originalfileName.substring(0, post)+"."+ext;}
	        
	        	uploadfileName = eventDTO.getEvent_no()+"."+ext;
	        	eventDTO.setEvent_image(uploadfileName);

	        	//(3) 서버에 업로드
	        	uploadPath = request.getSession().getServletContext().getRealPath("/")+"storage\\banner\\"+uploadfileName;
	        	
		        try {
		        	event_image_new.transferTo(new File(uploadPath));}
		        catch (IllegalStateException | IOException e) {e.printStackTrace();}
	        
		        //(4)workspace상에 업로드
		        filePath = "D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\banner\\";
		        
	     File newFile = new File(filePath,uploadfileName);
				try {FileCopyUtils.copy(event_image_new.getInputStream(), new FileOutputStream(newFile));} 
				catch (IOException e) {e.printStackTrace();}	  
		}
	
				//(5) DB upload
			try {
				int finalResult = tradingDAO.bannerModify(eventDTO);
				if(finalResult==0) state = "fail";
				else state ="success";}
			catch(Exception e) {e.printStackTrace(); state = "fail";}

		ModelAndView mav = new ModelAndView();
		mav.addObject("stateCode", state);
		mav.setViewName("/admin/shop/stateCode");
		return mav;
	}	
	//7.이벤트 현황 목록 호출하기
	@RequestMapping(value="/getEventList.do",method=RequestMethod.GET)
	public ModelAndView getEventList() {
		
		List<EventDTO> bannerList = tradingDAO.getBannerList();
		List<CouponDTO> couponBook = tradingDAO.getCouponBook();
		
		for(EventDTO data : bannerList) {
			data.callBannerList();
		}
		for(CouponDTO data : couponBook) {
			data.callCouponBook();
		}
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("bannerList",bannerList);		
			mav.addObject("couponBook",couponBook);
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//7.이벤트 현황 목록 호출하기
	@RequestMapping(value="/getSelectedCoupon.do",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView getSelectedCoupon(@RequestParam String coupon_no) {
		
		CouponDTO couponDTO = tradingDAO.getSelectedCoupon(coupon_no);
		

		ModelAndView mav = new ModelAndView();	
			mav.addObject("coupon",couponDTO);
			mav.setViewName("jsonView");
			
		return mav;
	}	
	
	//7.쿠폰 발행하기
	@RequestMapping(value="/makeCoupon.do",method=RequestMethod.POST)
	public ModelAndView makeCoupon(@ModelAttribute CouponDTO couponDTO) {
		
		int result = tradingDAO.makeCoupon(couponDTO);
		String state="fail";
		
		if(result==1) state="issued";
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("stateCode", state);
			mav.setViewName("/admin/shop/stateCode");
			
		return mav;
	}	
	
	//8.쿠폰 수정하기
	@RequestMapping(value="/modifyCoupon.do",method=RequestMethod.POST)
	@ResponseBody
	public String modifyCoupon(@ModelAttribute CouponDTO couponDTO) {
		
		int result = tradingDAO.modifyCoupon(couponDTO);
		
		if(result==1) return "success";
		else return "fail";
	}		
	
	//9.쿠폰 삭제하기
	@RequestMapping(value="/deleteCoupon.do",method=RequestMethod.POST)
	@ResponseBody
	public String modifyCoupon(@RequestParam String coupon_no) {
	
		List<CouponDTO> existingCoupon = tradingDAO.getGivenCoupon(coupon_no);
		if(existingCoupon!=null&&existingCoupon.size()>0) return "cannotDelete";
		else {
			int result = tradingDAO.deleteCoupon(coupon_no);	
			if(result==1) return "success";
			else return "fail";
		}

		

	}			
}
