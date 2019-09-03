package admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
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

import member.bean.MemberDTO;
import member.dao.MemberDAO;
import product.bean.ProductCategory;
import product.bean.ProductDTO;
import product.dao.ProductDAO;
import salesInfo.bean.SalesInfoDTO;
import salesInfo.bean.SalesInfoPaging;
import salesInfo.bean.SortCollection;
import salesInfo.dao.SalesInfoDAO;
import trading.bean.CouponDTO;
import trading.bean.DeliveryDTO;
import trading.bean.EventDTO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
import trading.bean.PaymentMethod;
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
	MemberDAO memberDAO;
	
	@Autowired
	ProductDAO productDAO;
	
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
				map.put("sortSubject", sortSubject);
				map.put("sortType", sortType);	
		List<SalesInfoDTO> salesInfoList = salesInfoDAO.getsalesInfoList(map);

			salesInfoPaging.setCurrentPage(page);
			salesInfoPaging.setPageBlock(3);
			salesInfoPaging.setPageSize(10);
			salesInfoPaging.setTotalA(totalA);
			salesInfoPaging.makePagingHTML();
		
			for(SalesInfoDTO dto : salesInfoList) {
				
				OrderDTO orderDTO = tradingDAO.getOrderInfo(dto.getOrder_no());
				List<ProductDTO> productList = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
				List<OrderDTO> paymentInfo = jsonTrans.makeJsonToPaymentList(dto.getSales_payment_json());
				
					dto.setSales_product_Info(productList);
					dto.setSales_payment_Info(paymentInfo);
					dto.setWomen_total(0); dto.setMen_total(0); dto.setAccessories_total(0); dto.setUnknown_total(0);
					dto.setCard_total(0); dto.setCash_total(0); dto.setCoupon_total(0); dto.setPoint_total(0); dto.setEtc_total(0);
				
				for(ProductDTO product : productList) {
					if(product.getProduct_category_no()==ProductCategory.WOMEN.ordinal()) dto.setWomen_total(product.getUnitcost()*product.getCart_qty());
					else if(product.getProduct_category_no()==ProductCategory.MEN.ordinal())  dto.setMen_total(product.getUnitcost()*product.getCart_qty());
					else if(product.getProduct_category_no()==ProductCategory.ACCESSORIES.ordinal())  dto.setAccessories_total(product.getUnitcost()*product.getCart_qty());
					else dto.setUnknown_total(product.getUnitcost()*product.getCart_qty());
				}
				
				for(OrderDTO payment: paymentInfo) {
					if(payment.getPayment_method()==PaymentMethod.CARD.ordinal()) dto.setCard_total(payment.getPayment_amount());
					else if(payment.getPayment_method()==PaymentMethod.CASH.ordinal()) dto.setCash_total(payment.getPayment_amount());
					else if(payment.getPayment_method()==PaymentMethod.POINT.ordinal()) dto.setPoint_total(payment.getPayment_amount());
					else if(payment.getPayment_method()==PaymentMethod.COUPON.ordinal()) dto.setCoupon_total(payment.getPayment_amount());
					else dto.setEtc_total(payment.getPayment_amount());
				}
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
		
			//선택된 검색어에 따른 무제한 데이터 호출 
			for(SalesInfoDTO dto : salesInfoSearchList) {
				
				OrderDTO orderDTO = tradingDAO.getOrderInfo(dto.getOrder_no());
				List<ProductDTO> productList = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
				List<OrderDTO> paymentInfo = jsonTrans.makeJsonToPaymentList(dto.getSales_payment_json());
				
					dto.setSales_product_Info(productList);
					dto.setSales_payment_Info(paymentInfo);
					dto.setWomen_total(0); dto.setMen_total(0); dto.setAccessories_total(0); dto.setUnknown_total(0);
					dto.setCard_total(0); dto.setCash_total(0); dto.setCoupon_total(0); dto.setPoint_total(0); dto.setEtc_total(0);
				
				for(ProductDTO product : productList) {
					if(product.getProduct_category_no()==ProductCategory.WOMEN.ordinal()) dto.setWomen_total(product.getUnitcost()*product.getCart_qty());
					else if(product.getProduct_category_no()==ProductCategory.MEN.ordinal())  dto.setMen_total(product.getUnitcost()*product.getCart_qty());
					else if(product.getProduct_category_no()==ProductCategory.ACCESSORIES.ordinal())  dto.setAccessories_total(product.getUnitcost()*product.getCart_qty());
					else dto.setUnknown_total(product.getUnitcost()*product.getCart_qty());
				}



				for(OrderDTO payment: paymentInfo) {
					if(payment.getPayment_method()==PaymentMethod.CARD.ordinal()) dto.setCard_total(payment.getPayment_amount());
					else if(payment.getPayment_method()==PaymentMethod.CASH.ordinal()) dto.setCash_total(payment.getPayment_amount());
					else if(payment.getPayment_method()==PaymentMethod.POINT.ordinal()) dto.setPoint_total(payment.getPayment_amount());
					else if(payment.getPayment_method()==PaymentMethod.COUPON.ordinal()) dto.setCoupon_total(payment.getPayment_amount());
					else dto.setEtc_total(payment.getPayment_amount());
				}
				

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
	
	//15. 차트 분석을 위한 데이터 가져오기
	@RequestMapping(value="/getChartRawData.do",method= RequestMethod.POST)
	public ModelAndView getChartRawData(@RequestParam(required=false) String keyword, String searchOption) {
		int grobe_total=0;
		int total_previousM =0; int total_lastM =0; int total_thisM =0;
		int women_previousM =0; int women_lastM =0; int women_thisM =0;
		int men_previousM =0; int men_lastM =0; int men_thisM =0;
		int accessories_previousM =0; int accessories_lastM =0; int accessories_thisM =0;		
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("keyword", keyword);
			map.put("searchOption", searchOption);
		List<SalesInfoDTO> totalSalesData = salesInfoDAO.getChartRawData(map);

		SimpleDateFormat sf = new SimpleDateFormat("MM");
		List<ProductDTO> dataList = productDAO.getInventoryCatalog();
		List<MemberDTO> memberList = memberDAO.getMemberList();
		MemberDTO guestDTO = new MemberDTO();
			guestDTO.setId("GUEST");
			guestDTO.setOrderNum(0);
			guestDTO.setOrderTotal(0);	
	for(SalesInfoDTO dto : totalSalesData) {	
		grobe_total+= dto.getSales_revenue();
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(dto.getOrder_no());
		List<ProductDTO> productList = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
		List<OrderDTO> paymentInfo = jsonTrans.makeJsonToPaymentList(dto.getSales_payment_json());
		
			dto.setSales_product_Info(productList);
			dto.setSales_payment_Info(paymentInfo);
			dto.setWomen_total(0); dto.setMen_total(0); dto.setAccessories_total(0); dto.setUnknown_total(0);
			dto.setCard_total(0); dto.setCash_total(0); dto.setCoupon_total(0); dto.setPoint_total(0); dto.setEtc_total(0);
		

			
			for(ProductDTO product : productList) {
				for(ProductDTO data: dataList) {
					if(data.getProduct_name_no()==product.getProduct_name_no()) {
						int cart_qty = data.getCart_qty()+product.getCart_qty();
						data.setCart_qty(cart_qty);
						break;
					}
				}
				if(product.getProduct_category_no()==ProductCategory.WOMEN.ordinal()) dto.setWomen_total(product.getUnitcost()*product.getCart_qty());
				else if(product.getProduct_category_no()==ProductCategory.MEN.ordinal())  dto.setMen_total(product.getUnitcost()*product.getCart_qty());
				else if(product.getProduct_category_no()==ProductCategory.ACCESSORIES.ordinal())  dto.setAccessories_total(product.getUnitcost()*product.getCart_qty());
				else dto.setUnknown_total(product.getUnitcost()*product.getCart_qty());
			}

		for(OrderDTO payment: paymentInfo) {
			if(payment.getPayment_method()==PaymentMethod.CARD.ordinal()) dto.setCard_total(payment.getPayment_amount());
			else if(payment.getPayment_method()==PaymentMethod.CASH.ordinal()) dto.setCash_total(payment.getPayment_amount());
			else if(payment.getPayment_method()==PaymentMethod.POINT.ordinal()) dto.setPoint_total(payment.getPayment_amount());
			else if(payment.getPayment_method()==PaymentMethod.COUPON.ordinal()) dto.setCoupon_total(payment.getPayment_amount());
			else dto.setEtc_total(payment.getPayment_amount());
		}
		int thisMonth = Integer.parseInt(sf.format(new Date()));
		int targetMonth = Integer.parseInt(sf.format(dto.getSales_date())); 
		if(targetMonth==thisMonth) {
			total_thisM += dto.getSales_revenue();
			women_thisM += dto.getWomen_total();
			men_thisM += dto.getMen_total();
			accessories_thisM += dto.getAccessories_total();
		}
		else if(targetMonth==thisMonth-1) {
			total_lastM += dto.getSales_revenue();
			women_lastM += dto.getWomen_total();
			men_lastM += dto.getMen_total();
			accessories_lastM += dto.getAccessories_total();
			
		}
		else {
			total_previousM += dto.getSales_revenue();
			women_previousM += dto.getWomen_total();
			men_previousM += dto.getMen_total();
			accessories_previousM += dto.getAccessories_total();
		}
		boolean isMember = false;
		for(MemberDTO member: memberList) {
			if(dto.getOrder_id().equals(member.getId())) {
				member.setOrderTotal(member.getOrderTotal()+dto.getSales_revenue());
				member.setOrderNum(member.getOrderNum()+1);
				isMember = true;
				break;
			}
		}
		if(!isMember) {
			guestDTO.setOrderTotal(guestDTO.getOrderTotal()+dto.getSales_revenue());
			guestDTO.setOrderNum(guestDTO.getOrderNum()+1);
		}

	}
	
	//단위 분석 자료 추출:START
	SortCollection sortCollection = new SortCollection();
	Collections.sort(dataList,sortCollection.getSalesSort());	
	String maxRevenueItem = dataList.get(0).getProduct_name_no()+"";
	String minRevenueItem = dataList.get(dataList.size()-1).getProduct_name_no()+"";
	
	Collections.sort(dataList,sortCollection.getQtySort());
	String maxSalesItem = dataList.get(0).getProduct_name_no()+"";
	String minSalesItem = dataList.get(dataList.size()-1).getProduct_name_no()+"";
	
	Collections.sort(dataList,sortCollection.getHitSort());
	String mostViewedItem = dataList.get(0).getProduct_name_no()+"";
	
	Collections.sort(dataList,sortCollection.getLikeitSort());		
	String bestLikeitItem = dataList.get(0).getProduct_name_no()+"";
	
	Collections.sort(memberList,sortCollection.getOrderNumSort());
	String mostOrderedMember =memberList.get(0).getId();
	
	Collections.sort(memberList,sortCollection.getOrderTotalSort());
	String mostBenefitMemeber=memberList.get(0).getId();
	
	String guestOrderRatio = String.format("%.2f",(float)guestDTO.getOrderTotal()/grobe_total*100);
	String salesDiffRatio = String.format("%.2f",(float)total_lastM/total_thisM*100);
	//단위 분석 자료 추출:END
	Map<String,Integer> periodicList = new HashMap<String,Integer>();
		periodicList.put("total_previousM", total_previousM);
		periodicList.put("total_lastM", total_lastM); 
		periodicList.put("total_thisM", total_thisM);
		periodicList.put("women_previousM", women_previousM);	
		periodicList.put("women_lastM", women_lastM);		
		periodicList.put("women_thisM", women_thisM);		
		periodicList.put("men_previousM", men_previousM);
		periodicList.put("men_lastM", men_lastM);
		periodicList.put("men_thisM", men_thisM);
		periodicList.put("accessories_previousM", accessories_previousM);
		periodicList.put("accessories_lastM", accessories_lastM);
		periodicList.put("accessories_thisM", accessories_thisM);
		

	String[][] stringData = new String[10][3];
		stringData[0] = new String[] {"maxRevenueItem",maxRevenueItem,"/minishop/admin/product/productViewPop.do?product_name_no="+maxRevenueItem};
		stringData[1] = new String[] {"maxSalesItem",maxSalesItem,"/minishop/admin/product/productViewPop.do?product_name_no="+maxSalesItem};
		stringData[2] = new String[] {"minRevenueItem",minRevenueItem,"/minishop/admin/product/productViewPop.do?product_name_no="+minRevenueItem};
		stringData[3] = new String[] {"minSalesItem",minSalesItem,"/minishop/admin/product/productViewPop.do?product_name_no="+minSalesItem};
		stringData[4] = new String[] {"mostOrderedMember",mostOrderedMember,"/minishop/admin/user/memberDetailView.do?id="+mostOrderedMember};
		stringData[5] = new String[] {"mostBenefitMemeber",mostBenefitMemeber,"/minishop/admin/user/memberDetailView.do?id="+mostBenefitMemeber};
		stringData[6] = new String[] {"guestOrderRatio",guestOrderRatio+"%","N/A"};
		stringData[7] = new String[] {"salesDiffRatio",salesDiffRatio+"%","N/A"};
		stringData[8] = new String[] {"mostViewedItem",mostViewedItem,"/minishop/admin/product/productViewPop.do?product_name_no="+mostViewedItem};
		stringData[9] = new String[] {"bestLikeitItem",bestLikeitItem,"/minishop/admin/product/productViewPop.do?product_name_no="+bestLikeitItem};
		
		Map<String,String> analysisData = new HashMap<String,String>();
		for(int i=0; i<10;i++) {
			analysisData.put(stringData[i][0], stringData[i][1]);
		}
		ModelAndView mav = new ModelAndView();	
		mav.addObject("totalSalesData", totalSalesData);
		mav.addObject("periodicList", periodicList);
		mav.addObject("analysisData", analysisData);
		mav.setViewName("jsonView");
	
	return mav;	
	}
		
}
