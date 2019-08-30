package admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import salesInfo.bean.SalesInfoDTO;
import salesInfo.bean.SalesInfoPaging;
import salesInfo.dao.SalesInfoDAO;
import trading.bean.CouponDTO;
import trading.bean.DeliveryDTO;
import trading.bean.EventDTO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
import trading.dao.TradingDAO;
/*
 * 관리자: 관리자 및 상점 정보를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/shop/**")
public class AdminShopController {

	@Autowired
	TradingDAO tradingDAO;
	
	@Autowired
	SalesInfoDAO salesInfoDAO;
	
	@Autowired
	SalesInfoPaging salesInfoPaging;
	
	@Autowired
	JsonTransitioner jsonTrans;
	
	//1. 관리자 정보 화면 이동
	@RequestMapping(value="/adminManage.do",method = RequestMethod.GET)
	public ModelAndView adminManage() {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "shopAdmin");
			mav.addObject("display", "/admin/shop/adminManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//2. 매출 정보 화면 이동
	@RequestMapping(value="/salesInfo.do",method = RequestMethod.GET)
	public ModelAndView salesInfo(@RequestParam(required = false,defaultValue = "1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "shopAdmin");
			mav.addObject("pg", pg);
			mav.addObject("display", "/admin/shop/salesInfo.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//3.이벤트 관리 화면 이동
	@RequestMapping(value="/eventManage.do",method = RequestMethod.GET)
	public ModelAndView eventManage(@RequestParam(required = false) String event_no) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "shopAdmin");
			mav.addObject("event_no", event_no);			
			mav.addObject("display", "/admin/shop/eventManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}		
	
	//4. 배너 호출
	@RequestMapping(value="/getSelectedBanner.do",method=RequestMethod.GET)
	public ModelAndView getSelectedBanner(String event_no) {
		
		EventDTO banner = tradingDAO.getSelectedBanner(event_no);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("banner", banner);
			mav.setViewName("jsonView");
		
	return mav;
	}
	
	//5. 배너  수정하기
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
		SimpleDateFormat targetDate = new SimpleDateFormat("yyyy-MM-dd");
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
	
	//6.이벤트 현황 목록 호출하기
	@RequestMapping(value="/getEventList.do",method=RequestMethod.GET)
	public ModelAndView getEventList() {
		
		List<EventDTO> bannerList = tradingDAO.getBannerList();
		List<CouponDTO> couponBook = tradingDAO.getCouponBook();
		List<DeliveryDTO> deliveryList = tradingDAO.getDeliveryPolicy();
		for(EventDTO data : bannerList) {
			data.callBannerList();
		}
		for(CouponDTO data : couponBook) {
			data.callCouponBook();
		}
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("bannerList",bannerList);		
			mav.addObject("couponBook",couponBook);
			mav.addObject("deliveryList",deliveryList);
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
	
	//8.쿠폰 발행하기
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
	
	//9.쿠폰 수정하기
	@RequestMapping(value="/modifyCoupon.do",method=RequestMethod.POST)
	@ResponseBody
	public String modifyCoupon(@ModelAttribute CouponDTO couponDTO) {
		
		int result = tradingDAO.modifyCoupon(couponDTO);
		
		if(result==1) return "success";
		else return "fail";
	}		
	
	//10.쿠폰 삭제하기
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
	
	//11. 배송료 변경하기
	@RequestMapping(value="/modifyDeliveryFee.do",method=RequestMethod.POST)
	public ModelAndView modifyDeliveryFee(@ModelAttribute DeliveryDTO deliveryDTO) {
		
		int result = tradingDAO.modifyDeliveryPolicy(deliveryDTO);
		String state="fail";
		
		if(result==1) state="success";
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("stateCode", state);
			mav.setViewName("/admin/shop/stateCode");
			
		return mav;
	}
	

	//12. 매출 목록 가져오기(한페이지당 10게시물,3블록 표시)
	@RequestMapping(value="/getsalesInfoList.do",method= RequestMethod.POST)
	public ModelAndView getsalesInfoList(@RequestParam(required=false,defaultValue="1") String pg,@RequestParam(required=false,defaultValue="sales_date") String sortSubject,@RequestParam(required=false,defaultValue="desc") String sortType){
		
		int page = Integer.parseInt(pg);
		int endNum = page*10;
		int startNum = endNum-9;		
		int totalA=0;
				
			totalA = salesInfoDAO.getTotalA();
			Map<String,String> map = new HashMap<String,String>();
				map.put("startNum", startNum+"");
				map.put("endNum", endNum+"");
				map.put("sortSubject",sortSubject);
				map.put("sortType", sortType);	
		List<SalesInfoDTO> salesInfoList = salesInfoDAO.getsalesInfoList(map);
	
			salesInfoPaging.setCurrentPage(page);
			salesInfoPaging.setPageBlock(3);
			salesInfoPaging.setPageSize(10);
			salesInfoPaging.setTotalA(totalA);
			salesInfoPaging.makePagingHTML();;	
		
			for(SalesInfoDTO dto : salesInfoList) {
				List<OrderDTO> sales_payment_Info = jsonTrans.makeJsonToPaymentList(dto.getSales_payment_json());
				dto.setSales_payment_Info(sales_payment_Info);
			}
			
		ModelAndView mav = new ModelAndView();
			mav.addObject("salesInfoPaging", salesInfoPaging);
			mav.addObject("salesInfoList", salesInfoList);
			mav.addObject("sortSubject", sortSubject);
			mav.addObject("sortType", sortType);	
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//13. 특정 검색어에 해당하는 매출 목록 가져오기(한페이지당 10게시물,3블록 표시)
	@RequestMapping(value="/salesInfoSearch.do",method= RequestMethod.POST)
	public ModelAndView salesInfoSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption,@RequestParam(required=false,defaultValue="sales_date") String sortSubject,@RequestParam(required=false,defaultValue="desc") String sortType) {
		
		int page = Integer.parseInt(pg);
		int endNum = page*10;
		int startNum = endNum-9;
		int totalA=0;
		
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption",searchOption);
			map.put("keyword", keyword);
			map.put("sortSubject",sortSubject);
			map.put("sortType", sortType);	
			totalA = salesInfoDAO.getTotalSearchA(map);	
			
			salesInfoPaging.setCurrentPage(page);
			salesInfoPaging.setPageBlock(3);
			salesInfoPaging.setPageSize(10);
			salesInfoPaging.setTotalA(totalA);
			salesInfoPaging.makeSearchPagingHTML();				

			
			List<SalesInfoDTO> salesInfoSearchList = salesInfoDAO.salesInfoSearch(map);
		
		for(SalesInfoDTO dto : salesInfoSearchList) {
			List<OrderDTO> sales_payment_Info = jsonTrans.makeJsonToPaymentList(dto.getSales_payment_json());
			dto.setSales_payment_Info(sales_payment_Info);
		}
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("pg", pg);
			mav.addObject("salesInfoList", salesInfoSearchList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("salesInfoPaging", salesInfoPaging);
			mav.addObject("sortSubject", sortSubject);
			mav.addObject("sortType", sortType);	
			mav.setViewName("jsonView");
		
		return mav;	
	}	
	
	//14. 특정 검색어에 해당하는 차트 불러오기
	@RequestMapping(value="/salesChart.do",method= RequestMethod.GET)
	public ModelAndView salesChart() {

		ModelAndView mav = new ModelAndView();		
		mav.setViewName("/admin/shop/salesChart");
	
	return mav;	
	}	
	
	//15. 페이징 없는 리스트 불러오기
	@RequestMapping(value="/getChartRawData.do",method= RequestMethod.POST)
	public ModelAndView getChartRawData(@RequestParam(required=false) String keyword,String searchOption) {
		
		Map<String,String> map = new HashMap<String,String>();
		
		List<SalesInfoDTO> salesInfoList = salesInfoDAO.getChartRawData(map);
		
	for(SalesInfoDTO dto : salesInfoList) {
		List<OrderDTO> sales_payment_Info = jsonTrans.makeJsonToPaymentList(dto.getSales_payment_json());
		dto.setSales_payment_Info(sales_payment_Info);
	}
		
		ModelAndView mav = new ModelAndView();	
		mav.addObject("salesRawInfoList", salesInfoList);
		mav.setViewName("jsonView");
	
	return mav;	
	}
}
