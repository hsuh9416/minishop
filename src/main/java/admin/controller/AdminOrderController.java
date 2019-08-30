package admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import product.bean.ProductDTO;
import product.dao.ProductDAO;
import salesInfo.bean.SalesInfoDTO;
import salesInfo.dao.SalesInfoDAO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
import trading.bean.OrderPaging;
import trading.bean.OrderState;
import trading.dao.TradingDAO;

/*
 * 관리자: 주문관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/order/**")
public class AdminOrderController {

	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	SalesInfoDAO salesInfoDAO;
	
	@Autowired
	TradingDAO tradingDAO;

	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	JsonTransitioner jsonTrans;
	
	@Autowired
	OrderPaging orderPaging;
	
	@Autowired
	Mailing mailing;
	
	//1. 회원주문관리 화면으로 이동
	@RequestMapping(value="/orderManage.do",method = RequestMethod.GET)
	public ModelAndView orderManage(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "orderAdmin");
			mav.addObject("display", "/admin/order/orderManage.jsp");
			mav.setViewName("/main/home");
		
		return mav;
	}	
	
	//2. 회원주문목록 호출하기(한페이지당 5게시물,3블록 표시)
	@RequestMapping(value="/getUserOrderList.do",method = RequestMethod.POST)
	public ModelAndView getUserOrderList(@RequestParam(required=false,defaultValue="1") String pg) {
	
		int page = Integer.parseInt(pg);
		int endNum = page*5;
		int startNum = endNum-4;	
		int totalA = 0;
		
		totalA = tradingDAO.getTotalA();
	
		List<OrderDTO> userOrderList = tradingDAO.getUserOrderList(startNum, endNum);

			orderPaging.setCurrentPage(page);
			orderPaging.setPageBlock(3);
			orderPaging.setPageSize(5);
			orderPaging.setTotalA(totalA);
			orderPaging.makePagingHTML();;
		
		
		for(OrderDTO data: userOrderList) {
			List<ProductDTO> productList = jsonTrans.makeJsonToList(data.getOrderlist_json());
			data.setOrderList(productList);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("orderPaging", orderPaging);
		mav.addObject("userOrderList",userOrderList);
		mav.setViewName("jsonView");
	
	return mav;
	}
	
	//3. 특정 검색어에 해당하는 주문 목록 가져오기(한페이지당 5게시물,3블록 표시)
	@RequestMapping(value="/userSearchOrderList.do",method= RequestMethod.POST)
	public ModelAndView userSearchOrderList(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		
			int page = Integer.parseInt(pg);
			int endNum = page*5;
			int startNum = endNum-4;
			int totalA=0;
		
			if(searchOption.equals("productid")) keyword = keyword.toUpperCase();
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption",searchOption);
			map.put("keyword", keyword);
			totalA = tradingDAO.getTotalSearchA(map);	
			
			orderPaging.setCurrentPage(page);
			orderPaging.setPageBlock(3);
			orderPaging.setPageSize(5);
			orderPaging.setTotalA(totalA);
			orderPaging.makeSearchPagingHTML();				
		
		
		List<OrderDTO> userSearchOrderList = tradingDAO.userSearchOrderList(map);
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("pg", pg);
			mav.addObject("userOrderList",userSearchOrderList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("orderPaging", orderPaging);
			mav.setViewName("jsonView");
		
		return mav;	
	}	
	//4. 개별조회서 조회 및 수정사항 반영 팝업창 이동
	@RequestMapping(value="/personalOrderView.do",method= RequestMethod.GET)
	public ModelAndView personalOrderView(@RequestParam String order_no, String order_state) {
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
		String original_order_state = orderDTO.getOrder_state()+"";
		ModelAndView mav = new ModelAndView();		
			mav.addObject("order_no", order_no);	
			mav.addObject("old_state",original_order_state);
			mav.addObject("new_state",order_state);
			mav.setViewName("/admin/order/personalOrderView");
		
	return mav;	
	}

	//5. 개별조회서 내용 호출
	@RequestMapping(value="/getPersonalOrderInfo.do",method= RequestMethod.GET)
	@ResponseBody
	public ModelAndView getPersonalOrderInfo(@RequestParam String order_no) {
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
		List<OrderDTO> paymentInfo = tradingDAO.getPaymentInfo(order_no);
		List<ProductDTO> productList = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
		ModelAndView mav = new ModelAndView();		
			mav.addObject("orderDTO", orderDTO);
			mav.addObject("productList", productList);	
			mav.addObject("paymentInfo", paymentInfo);		
			mav.setViewName("jsonView");
		
	return mav;	
	}
	
	//6. 회원 주문 정보 변경 이벤트
	@RequestMapping(value="/changeOrderInfo.do",method=RequestMethod.POST)
	@ResponseBody
	public String changeOrderInfo(@RequestParam Map<String,Object> map) {
		
		if(map.get("modify_type").equals("extraInfo")) {
			map.put("order_statement", "관리자 변경으로 비고 없음");
		}
		int result = tradingDAO.changeOrderInfo(map);
		
		if(result!=0) {
			AdminDTO adminDTO = adminDAO.getAdmin();
			OrderDTO orderDTO = tradingDAO.getOrderInfo((String)map.get("order_no"));
			if(!map.get("modify_type").equals("extraInfo")) {
			MessageDTO messageDTO = new MessageDTO();
				messageDTO = mailing.sendOrderModifiedMail(messageDTO,orderDTO);

				mailing.sendMail(adminDTO, messageDTO);
			}
			return "success";
			
		}
		else return "fail";
	}

	//6. 거래상태 변경 이벤트(변경 상태에 따른 추가 이벤트 포함)
	@RequestMapping(value="/changeOrderState.do",method=RequestMethod.POST)
	@ResponseBody
	public String changeOrderState(@RequestParam String newState, String order_no) {
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
			orderDTO.setOrder_logtime(new Date());
			
		int order_original_state = orderDTO.getOrder_no();
		int order_new_state = 0;

		try {
			order_new_state = Integer.parseInt(newState);
			orderDTO.setOrder_state(order_new_state);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			return "fail";
		}
		
			
		MessageDTO messageDTO = new MessageDTO();
		AdminDTO adminDTO = adminDAO.getAdmin();
		//주문완료:0,입금완료:1,배송대기중:2,배송중:3,환불진행중:4,배송완료:5,환불완료:6,수취완료:7,주문취소:8
		//1. 주문완료에서 입금완료로 변경(payment 업데이트, 메일 없음)
		if(order_original_state==OrderState.ORDERPLACED.ordinal()&&order_new_state==OrderState.PAYMENTDONE.ordinal()) {
			int paymentUpdated = tradingDAO.updatePayment(orderDTO);
			if(paymentUpdated==0) return "fail";
			orderDTO.setPayment_date(new Date());
			orderDTO.setOrder_statement("[입금확인(최종확인일자:"+orderDTO.getPayment_date()+")]");
		}
		//2. 주문완료/입금완료에서 배송대기중으로 변경(재고 확인, 재고 부족시 관리자에 재고 보충 경고 보냄<-다만 변경은 완료됨, 입금 확인 메일 전송)
		else if(order_new_state==OrderState.PROCESSED.ordinal()) {
			if(order_original_state==OrderState.ORDERPLACED.ordinal()) {
				tradingDAO.updatePayment(orderDTO);
				orderDTO.setPayment_date(new Date());
			}
			List<ProductDTO> requestInventory = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
			for(ProductDTO dto : requestInventory) {
				if(dto.getCart_qty()>dto.getStock()) return "fail";
			}
			orderDTO.setOrder_statement("[배송대기중]");
			
			messageDTO = mailing.sendPaymentConfirmMail(messageDTO,orderDTO);
			mailing.sendMail(adminDTO, messageDTO);
			
		}
		
		//3. 주문완료/입금완료/배송대기중에서 배송중으로 변경(재고 차감,송장 업데이트 유효성 검사, 배송 메일 전송)
		else if(order_new_state==OrderState.INDELIVERY.ordinal()) {
			if(orderDTO.getOrder_deliverynum().equals("")||orderDTO.getOrder_deliverynum()==null) return "fail";
			if(order_original_state==OrderState.ORDERPLACED.ordinal()) {
				tradingDAO.updatePayment(orderDTO);
				orderDTO.setPayment_date(new Date());}

			List<ProductDTO> requestInventory = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
			
				for(ProductDTO dto : requestInventory) {
					ProductDTO existInventory = productDAO.getInventoryInfo(dto.getProductID());
					if(dto.getCart_qty()>existInventory.getStock()) return "fail";
					else {
						int leftStock = existInventory.getStock()-dto.getCart_qty();
						Map<String,String> map = new HashMap<String,String>();
							map.put("stock", leftStock+""); 
							map.put("unitcost", dto.getUnitcost()+""); 
							map.put("productID", dto.getProductID());
							map.put("ordering","no");
							map.put("product_name_no", dto.getProduct_name_no()+"");
						productDAO.inventoryUpdate(map);
						dto.setStock(leftStock);
					}
				}
				orderDTO.setOrderlist_json(jsonTrans.makeListToJson(requestInventory));
				int subResult = tradingDAO.implementingInventoryChange(orderDTO);
				if(subResult==0) return "fail";
				orderDTO.setOrder_statement("[발송완료 (송장번호:"+orderDTO.getOrder_deliverynum()+")]");
				messageDTO = mailing.sendDeliveryInfoMail(messageDTO, orderDTO);
				
				mailing.sendMail(adminDTO, messageDTO);
		}
		//4. 배송완료로 변경(메일 전송)
		else if(order_new_state==OrderState.DELIVERED.ordinal()) {
			
			orderDTO.setOrder_statement("[배송완료 (완료일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")]");
			messageDTO = mailing.sendDeliveryConfirmMail(messageDTO, orderDTO);
			mailing.sendMail(adminDTO, messageDTO);
		}			
		//5. 환불진행중으로 전환됨(배송중인 경우 재고 증감, 메일 전송)
		else if(order_new_state==OrderState.REFUNDPROCESSING.ordinal()) {
			if(order_original_state==OrderState.INDELIVERY.ordinal()||order_original_state==OrderState.DELIVERED.ordinal()) {
				List<ProductDTO> requestInventory = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());

				for(ProductDTO dto : requestInventory) {
					
					ProductDTO existInventory = productDAO.getInventoryInfo(dto.getProductID());
						int returnedStock = existInventory.getStock()+dto.getCart_qty();
						Map<String,String> map = new HashMap<String,String>();
							map.put("stock", returnedStock+""); 
							map.put("unitcost", dto.getUnitcost()+""); 
							map.put("productID", dto.getProductID());
							map.put("product_name_no", dto.getProduct_name_no()+"");
							map.put("ordering","no");
						productDAO.inventoryUpdate(map);
						dto.setStock(returnedStock);
				}		
				orderDTO.setOrderlist_json(jsonTrans.makeListToJson(requestInventory));
				int subResult = tradingDAO.implementingInventoryChange(orderDTO);
				if(subResult==0) return "fail";
			}
			List<OrderDTO> paymentList = tradingDAO.getPaymentInfo(order_no);
				orderDTO.setOrder_logtime(new Date());
				orderDTO.setOrder_statement("[환불진행중 (전환일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")] [환불사유:고객요청]");
				int total = 0;
				for(OrderDTO payment: paymentList) {
					if(payment.getPayment_method()==1||payment.getPayment_method()==2) total+=payment.getPayment_amount();
				}
				orderDTO.setPayment_amount(total);
			
			Map<String,Object> Modifymap = new HashMap<String,Object>();
				Modifymap.put("modify_type", "extraInfo");
				Modifymap.put("order_no", order_no);
				Modifymap.put("order_deliverynum", "[해당없음]");
				Modifymap.put("order_refundaccount", orderDTO.getOrder_refundaccount());
				Modifymap.put("order_statement", orderDTO.getOrder_statement());
				
				int modify = tradingDAO.changeOrderInfo(Modifymap);
				if(modify==0) return "fail";
		

				messageDTO = mailing.sendRefundInfoMail(messageDTO, orderDTO);
				mailing.sendMail(adminDTO, messageDTO);
		}	
		
		//6. 환불진행중에서 환불완료로 변경(포인트, 입금 취소 및 복원, 메일 전송)
		else if(order_new_state==OrderState.REFUNDED.ordinal()) {
			
			MemberDTO memberDTO = memberDAO.getUser(orderDTO.getOrder_id());
			List<OrderDTO> paymentList = tradingDAO.getPaymentInfo(order_no);
			if(memberDTO!=null) {
				for(OrderDTO payment: paymentList) {
					if(payment.getPayment_method()==4&&payment.getPayment_amount()>0) memberDAO.setPoint(memberDTO.getId(), payment.getPayment_amount()+"");
					else if(payment.getPayment_method()==5) {
						String[] primarySplit = payment.getPayment_state().split("\\[");
						String[] secondarySplit = primarySplit[1].split("\\]");
						String coupon_no = secondarySplit[0];
						Map<String,String> map = new HashMap<String,String>();
							map.put("personal_code", UUID.randomUUID().toString());
							map.put("coupon_no", coupon_no);
							map.put("id", memberDTO.getId());
							tradingDAO.modifyUserBenefit(map);}}}
			int total = 0;
			for(OrderDTO payment: paymentList) {
				if(payment.getPayment_method()==1||payment.getPayment_method()==2) total+=payment.getPayment_amount();
			}
			orderDTO.setPayment_amount(total);
			
			int subResult = tradingDAO.cancelPayment(orderDTO.getOrder_no());
			if(subResult==0) return "fail";
			
			orderDTO.setOrder_statement("[환불완료 (환불일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")]");
			messageDTO = mailing.sendRefundCompleteMail(messageDTO, orderDTO);
			mailing.sendMail(adminDTO, messageDTO);
		}

		//7. 배송완료에서 수취완료로 변경(포인트 지급, 회원 한정으로 메일 전송)
		if(order_new_state==OrderState.ORDERCOMPLETED.ordinal()) {
			MemberDTO memberDTO = memberDAO.getUser(orderDTO.getOrder_id());
			if(memberDTO!=null) {
				String state="일반"; double pointRate = 0.05;
				
				if(memberDTO.getState()==2) {
					state="특별";
					pointRate = 0.10;
				}
				else if(memberDTO.getState()==0) {
					state="특수";
					pointRate = 0.15;
				}
				
				int point = (int)Math.round(orderDTO.getOrder_total()*pointRate);
				
				memberDAO.setPoint(memberDTO.getId(), point+"");
				
				messageDTO = mailing.sendOrderCompletedMail(messageDTO, orderDTO,state,point);
				mailing.sendMail(adminDTO, messageDTO);		
			}
			orderDTO.setOrder_statement("[거래완료 (완료일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")]");
			//매출계상하기
			List<ProductDTO> salesProductList = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
			for(ProductDTO dto : salesProductList) {
				int product_salesMount = dto.getProduct_salesMount()+dto.getCart_qty();
				dto.setProduct_salesMount(product_salesMount);
				productDAO.updateSalesProductInfo(dto);
			}

			SalesInfoDTO salesInfoDTO = new SalesInfoDTO();
				salesInfoDTO.setSales_seq(salesInfoDAO.getSalesSeq());
				salesInfoDTO.setOrder_no(order_no);
				if(memberDTO!=null) salesInfoDTO.setOrder_id(memberDTO.getId());
				else salesInfoDTO.setOrder_id("GUEST");
				salesInfoDTO.setSales_revenue(orderDTO.getOrder_total());
			List<OrderDTO> paymentList = tradingDAO.getPaymentInfo(order_no);	
				String sales_payment_Info = jsonTrans.makePaymentListToJson(paymentList);
				salesInfoDTO.setSales_payment_json(sales_payment_Info);
				
				int uploadResult = salesInfoDAO.uploadSalesInfo(salesInfoDTO);
				if(uploadResult==0) return "fail";
		}	
		
		//8. 주문완료에서 주문취소로 변경(관리자 권한으로 변경됨, 메일 전송)
		if(order_original_state==OrderState.ORDERPLACED.ordinal()&&order_new_state==OrderState.ORDERCANCELED.ordinal()) {
			
			MemberDTO memberDTO = memberDAO.getUser(orderDTO.getOrder_id());
			if(memberDTO!=null) {
				List<OrderDTO> paymentList = tradingDAO.getPaymentInfo(order_no);
				for(OrderDTO payment: paymentList) {
					if(payment.getPayment_method()==4&&payment.getPayment_amount()!=0) memberDAO.setPoint(memberDTO.getId(), payment.getPayment_amount()+"");
					else if(payment.getPayment_method()==5) {
						String[] primarySplit = payment.getPayment_state().split("\\[");
						String[] secondarySplit = primarySplit[1].split("\\]");
						String coupon_no = secondarySplit[0];
						Map<String,String> map = new HashMap<String,String>();
							map.put("personal_code", UUID.randomUUID().toString());
							map.put("coupon_no", coupon_no);
							map.put("id", memberDTO.getId());
							tradingDAO.modifyUserBenefit(map);
						
					}
				}
				int subResult = tradingDAO.cancelPayment(orderDTO.getOrder_no());
				if(subResult==0) return "fail";			
			}

			orderDTO.setOrder_statement("[주문취소 (취소일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")]");
			messageDTO = mailing.sendOrderCancelMail(messageDTO, orderDTO);
			mailing.sendMail(adminDTO, messageDTO);
		}
		
		int result = tradingDAO.modifyOrderAdmin(orderDTO);
		
		if(result==0) return "fail";		
		else return "success";
	}
}
