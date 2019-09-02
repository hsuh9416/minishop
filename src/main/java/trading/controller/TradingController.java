package trading.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonElement;

import admin.bean.AdminDTO;
import admin.dao.AdminDAO;
import mail.bean.Mailing;
import mail.bean.MessageDTO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import product.bean.ProductDTO;
import product.dao.ProductDAO;
import salesInfo.bean.SalesInfoDTO;
import salesInfo.dao.SalesInfoDAO;
import trading.bean.CouponDTO;
import trading.bean.DeliveryDTO;
import trading.bean.EventDTO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
import trading.bean.OrderState;
import trading.bean.PaymentMethod;
import trading.bean.ShoppingCart;
import trading.dao.TradingDAO;
/*
 * 사용자 거래 관련 활동을 제어하는 클래스
 */
@Controller
@RequestMapping(value="/trading/**")
public class TradingController {
	
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired 
	ProductDAO productDAO;

	@Autowired
	SalesInfoDAO salesInfoDAO;
	
	@Autowired
	TradingDAO tradingDAO;
	
	@Autowired
	JsonTransitioner jsonTrans;
	
	@Autowired
	Mailing mailing;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//1. 장바구니 화면 이동
	@RequestMapping(value = "/userCart.do", method = RequestMethod.GET)
	public ModelAndView userCart(HttpSession session){
		
		ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
		if(shoppingCart==null) shoppingCart = new ShoppingCart();
		
		List<ProductDTO> cartList = shoppingCart.getCartList();
		if(cartList==null) cartList = new ArrayList<ProductDTO>();
		
			shoppingCart.setCartList(cartList);
			session.setAttribute("shoppingCart", shoppingCart);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "orderUser");
			mav.addObject("display", "/trading/userCart.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//2. 장바구니 항목 추가하기
	@RequestMapping(value = "/addCart.do", method = RequestMethod.POST)
	@ResponseBody
	public void addCart(@RequestParam String product_name_no,String cart_qty,HttpSession session) {
		
		int index;
		int targetNumber;
		int qty; 
		int quantity;
		
		List<ProductDTO> cartList;
		ProductDTO productDTO;
		ShoppingCart shoppingCart;
		
		try {qty =Integer.parseInt(cart_qty);}
		catch(NumberFormatException nf) {qty = 1;}
		
			shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
		if (shoppingCart == null) {
				shoppingCart = new ShoppingCart();
				cartList = new ArrayList<ProductDTO>();
				productDTO = productDAO.getProductInfo(product_name_no);
			
			if(productDTO==null) return;
			
				productDTO.setCart_qty(qty);
				cartList.add(productDTO);
				shoppingCart.setCartList(cartList);
				session.setAttribute("shoppingCart", shoppingCart);
				session.setAttribute("cartList", cartList);} 
		else {
				cartList = shoppingCart.getCartList();
			if(cartList==null) cartList = new ArrayList<ProductDTO>();
					index = -1;
					targetNumber = Integer.parseInt(product_name_no);
					
			if(shoppingCart.getCartList().size()>0) {
				index = shoppingCart.exists(targetNumber, cartList);}
			
			if (shoppingCart.getCartList().size()==0 || index == -1) {
					productDTO = productDAO.getProductInfo(product_name_no);
				
				if(productDTO==null) return;
					
					productDTO.setCart_qty(qty);
					cartList.add(productDTO);} 
			
			else {
					quantity = cartList.get(index).getCart_qty() + qty;
					cartList.get(index).setCart_qty(quantity);}
			
				shoppingCart.setCartList(cartList);
				session.setAttribute("shoppingCart", shoppingCart);
				session.setAttribute("cartList", cartList);}
	}
	
	//3. 장바구니 항목 단일/복수 삭제하기
	@RequestMapping(value = "/removeCart.do", method = RequestMethod.POST)
	public ModelAndView removeCart(@RequestParam int[] check, HttpSession session) {
		
		int index;
		
		ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
		
		for(int product_name_no : check) {
				index = shoppingCart.exists(product_name_no, shoppingCart.getCartList());
				shoppingCart.getCartList().remove(index);}
		
			session.setAttribute("shoppingCart", shoppingCart);
			
		ModelAndView mav = new ModelAndView();
			mav.setViewName("/trading/cartModified");
			
		return mav;
	}
	
	//4. 장바구니 호출하기
	@RequestMapping(value="/getCartList.do",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView getCartList(HttpSession session) {
		
		ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
		List<ProductDTO> cartList = shoppingCart.getCartList();
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("cartList", cartList);
			mav.setViewName("jsonView");
		
		return mav;
	}
	
	//5. 장바구니 특정 항목 수량 수정하기
	@RequestMapping(value = "/modifyCart.do", method = RequestMethod.GET)
	public ModelAndView modifyCart(@RequestParam int product_name_no, int changeNum, HttpSession session) {
		int index;
		
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
			index = shoppingCart.exists(product_name_no, shoppingCart.getCartList());
			shoppingCart.getCartList().get(index).setCart_qty(changeNum);
			
			session.setAttribute("shoppingCart", shoppingCart);
			
		ModelAndView mav = new ModelAndView();
			mav.setViewName("/trading/cartModified");
			
		return mav;
	}
	
	//6. 상품 주문서 화면 이동
	@RequestMapping(value="orderForm.do",method=RequestMethod.POST)
	public ModelAndView orderForm(HttpSession session,@RequestParam int[] product_name_no,@RequestParam(required = false) int cart_qty,@RequestParam String directOrder) {
		
		int index;
		int qty;	
		JsonElement orderList_JSON;
		ProductDTO productDTO;
		
		List<ProductDTO> orderList = new ArrayList<ProductDTO>();				
			qty=0;
			index=-1;
			orderList_JSON=null;
		
		if(qty != cart_qty) {
				productDTO = productDAO.getProductInfo(product_name_no[0]+"");
				productDTO.setCart_qty(cart_qty);
				orderList.add(productDTO);
				orderList_JSON = jsonTrans.makeListToJsonElement(orderList);}
		else {
			 ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");	
			for(int number : product_name_no) {
					index = shoppingCart.exists(number, shoppingCart.getCartList());
					orderList.add(shoppingCart.getCartList().get(index));
					orderList_JSON = jsonTrans.makeListToJsonElement(orderList);}
			}
		
			session.setAttribute("orderList_JSON", orderList_JSON);
		
		ModelAndView mav = new ModelAndView();	
			mav.addObject("directOrder", directOrder);
			mav.addObject("location", "orderUser");
			mav.addObject("display", "/trading/orderForm.jsp");	
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//7. 상품 주문 내역, 회원 기존 정보 호출하기
	@RequestMapping(value="/getPreOrderInfo.do",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getPreOrderInfo(HttpSession session) {
		
		JsonElement orderList_JSON = (JsonElement)session.getAttribute("orderList_JSON");
		List<ProductDTO> orderList = jsonTrans.makeJsonToList(orderList_JSON.toString());
		List<DeliveryDTO> deliveryPolicy = tradingDAO.getDeliveryPolicy();
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		ModelAndView mav = new ModelAndView();
			mav.addObject("orderList", orderList);
			mav.addObject("deliveryPolicy", deliveryPolicy);
			if(memberDTO!=null) {
				List<CouponDTO> couponList = tradingDAO.getAvailableUserCoupon(memberDTO.getId());
				mav.addObject("couponList", couponList);
			}
			mav.addObject("memberDTO", memberDTO);	
			
			mav.setViewName("jsonView");
		return mav;
	}
	
	//8. 도서,상간 지역 특별 배송료 판정하기
	@RequestMapping(value="/verifyAdditionalFee.do",method = RequestMethod.GET)
	@ResponseBody
	public String verifyAdditionalFee(String zipcode) {	
		int result = tradingDAO.verifyAdditionalFee(zipcode);
		if(result!=0) return "exist";
		else return "not_exist";
	}
	//8. 최종 주문서 제출
	@RequestMapping(value="/putOrderForm.do",method = RequestMethod.POST)
	@ResponseBody
	public String putOrderForm(@ModelAttribute OrderDTO orderDTO, @RequestParam(required = false) String point,@RequestParam(required = false) String coupon_amount, @RequestParam(required = false) String coupon_option,@RequestParam(required = false,defaultValue = "true") String directOrder, HttpSession session) {	

		
		String order_pwd = "회원비밀번호와 동일함";
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		AdminDTO adminDTO = adminDAO.getAdmin();
		JsonElement orderList_JSON = (JsonElement)session.getAttribute("orderList_JSON");	
		List<ProductDTO> orderList = jsonTrans.makeJsonToList(orderList_JSON.toString());
			orderDTO.setOrderlist_json(jsonTrans.makeListToJson(orderList));
			
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if(directOrder.equals("false")&&shoppingCart!=null&&shoppingCart.getCartList().size()>0) {
			for(ProductDTO orderedItem : orderList) {
				int index = shoppingCart.exists(orderedItem.getProduct_name_no(), shoppingCart.getCartList());
					shoppingCart.getCartList().remove(index);
			}
				session.setAttribute("cartList", shoppingCart.getCartList());
				session.setAttribute("shoppingCart", shoppingCart);
		}
		
		int order_no = tradingDAO.getOrderNum();
				orderDTO.setOrder_no(order_no);
				
		Date today = new Date();
				orderDTO.setOrder_date(today);

		if(memberDTO!=null) {
			
				orderDTO.setOrder_id(memberDTO.getId());
				orderDTO.setOrder_pwd(memberDTO.getPwd());
				if(point!=null&&!point.equals("0")&&!point.equals("")) {
					memberDAO.reducePoint(memberDTO.getId(), point);

					OrderDTO pointPay = new OrderDTO();
						pointPay.setOrder_no(order_no);
						pointPay.setPayment_amount(Integer.parseInt(point));
						pointPay.setPayment_method(PaymentMethod.POINT.ordinal());
						pointPay.setPayment_date(today);
						pointPay.setPayment_state("사용 포인트 : ["+point+"] 점");
						tradingDAO.setPayment(pointPay);
				}
				if (!coupon_amount.equals("")) {
					String[] primarySplit= coupon_option.split("\\]");
					String[] secoundarySplit = primarySplit[0].split("\\[");
					
					String coupon_no= secoundarySplit[1];
					
					Map<String,String> map = new HashMap<String,String>();
						map.put("personal_code", "");
						map.put("coupon_no", coupon_no);
						map.put("id", memberDTO.getId());
						tradingDAO.modifyUserBenefit(map);
					
					OrderDTO couponPay = new OrderDTO();
						couponPay.setOrder_no(order_no);
						couponPay.setPayment_amount(Integer.parseInt(coupon_amount));
						couponPay.setPayment_method(PaymentMethod.COUPON.ordinal());
						couponPay.setPayment_date(today);
						couponPay.setPayment_state("사용 쿠폰번호: ["+coupon_no+"]");
						
						tradingDAO.setPayment(couponPay);

				}
				
				if(orderDTO.getPayment_method()==PaymentMethod.CARD.ordinal()) {
					orderDTO.setPayment_state("카드 결제");
					orderDTO.setPayment_date(today);
				}
				else if(orderDTO.getPayment_method()==PaymentMethod.NOPAID.ordinal()) {
					orderDTO.setPayment_state("전액 포인트 등 결제");
					orderDTO.setPayment_date(today);
				}
				else if(orderDTO.getPayment_method()==PaymentMethod.CASH.ordinal()) {
					orderDTO.setPayment_state("입금 은행 계좌 : ["+adminDTO.getAdmin_account()+"] ");
					orderDTO.setPayment_date(today);
				}
					tradingDAO.setPayment(orderDTO);
		}
		else {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			
			String order_id= sf.format(today)+"-"+order_no;
				order_pwd = mailing.getKey(8);
			
				orderDTO.setOrder_id(order_id);
				orderDTO.setOrder_pwd(passwordEncoder.encode(order_pwd));
			if(orderDTO.getPayment_method()==PaymentMethod.CARD.ordinal()) {
				orderDTO.setPayment_state("카드 결제");
				orderDTO.setPayment_date(today);
			}
			else if(orderDTO.getPayment_method()==PaymentMethod.CASH.ordinal()) {
				orderDTO.setPayment_state("입금 은행 계좌 : ["+adminDTO.getAdmin_account()+"] ");
				orderDTO.setPayment_date(today);
			}
			
			tradingDAO.setPayment(orderDTO);
			
			GuestDTO guestDTO = new GuestDTO();
				guestDTO.setGuest_id(orderDTO.getOrder_id());
				guestDTO.setGuest_pwd(orderDTO.getOrder_pwd());
				guestDTO.setGuest_name(orderDTO.getOrder_name());
				guestDTO.setGuest_email(orderDTO.getOrder_email());
				guestDTO.setGuest_tel(orderDTO.getOrder_tel());
				guestDTO.setOrder_no(orderDTO.getOrder_no());
				session.setAttribute("guestDTO", guestDTO);
		}	
		

			session.removeAttribute("orderList_JSON");
			session.removeAttribute("orderList");
		

			int result = tradingDAO.putOrder(orderDTO);
			

			if(result!=0) {
					if (memberDTO!=null) orderDTO.setOrder_id("회원아이디와 동일함");
					orderDTO.setOrder_pwd(order_pwd);
				MessageDTO messageDTO = new MessageDTO();
					messageDTO = mailing.sendOrderMail(messageDTO,orderDTO);
					
					
					
					mailing.sendMail(adminDTO, messageDTO);
					
					return orderDTO.getOrder_no()+"";
			}
			
			
			return "fail";
			
	}	
	
	//9. 주문 내역서 확인 화면 이동
	@RequestMapping(value="/orderView.do",method = RequestMethod.GET)
	public ModelAndView orderView(@RequestParam String order_no) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("location", "orderUser");
			mav.addObject("order_no", order_no);
			mav.addObject("display", "/trading/orderView.jsp");
			mav.setViewName("/main/home");
		
		return mav;
	}

	//10. 주문 명세 호출
	@RequestMapping(value="/getOrderInfo.do",method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getOrderInfo(@RequestParam String order_no) {
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

	//11. 주문 취소 요청 
	@RequestMapping(value="/cancelOrder",method= RequestMethod.GET)
	@ResponseBody
	public String cancelOrder(@RequestParam String order_no,HttpSession session) {
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
		GuestDTO guestDTO = (GuestDTO)session.getAttribute("guestDTO");
		if(memberDTO==null&&guestDTO==null) return "nonVerifiedAttempt";
	
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
		
		if( (memberDTO!=null&&orderDTO.getOrder_id().equals(memberDTO.getId())) || 
			(guestDTO!=null&&orderDTO.getOrder_id().equals(guestDTO.getGuest_id())) ){
			orderDTO.setOrder_state(OrderState.ORDERCANCELED.ordinal());
			orderDTO.setOrder_statement("[주문취소(일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")]");
			tradingDAO.modifyOrderAdmin(orderDTO);
		}
		else return "nonVerifiedAttempt";
		
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
			int subResult = tradingDAO.cancelPayment(Integer.parseInt(order_no));
			if(subResult==0) return "fail";
		}
		return "success";
	}
	//12. 환불요청 팝업창 이동하기
	@RequestMapping(value="/refundForm.do",method = RequestMethod.GET)
	public ModelAndView refundForm(@RequestParam String order_no) {
		ModelAndView mav = new ModelAndView();
			mav.addObject("order_no", order_no);		
			mav.setViewName("/trading/refundForm");
			
		return mav;
	}	
	
	//13. 환불요청하기
	@RequestMapping(value="/requestRefund.do",method = RequestMethod.POST)
	@ResponseBody
	public String requestRefund(@RequestParam String order_no,String order_refundaccount,String order_statement) {
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
		List<OrderDTO> paymentList = tradingDAO.getPaymentInfo(order_no);
			orderDTO.setOrder_logtime(new Date());
			orderDTO.setOrder_refundaccount(order_refundaccount);
			orderDTO.setOrder_statement("[환불진행중 (전환일자:"+new SimpleDateFormat("yyyy.MM.dd").format(new Date())+")] [환불사유 : "+order_statement+"]");
			int total = 0;
			for(OrderDTO payment: paymentList) {
				if(payment.getPayment_method()==1||payment.getPayment_method()==2) total+=payment.getPayment_amount();
			}
			orderDTO.setPayment_amount(total);
			
			if(orderDTO.getOrder_state()==OrderState.INDELIVERY.ordinal()||orderDTO.getOrder_state()==OrderState.DELIVERED.ordinal()) {
				List<ProductDTO> requestInventory = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());

				for(ProductDTO dto : requestInventory) {
					
					ProductDTO existInventory = productDAO.getInventoryInfo(dto.getProductID());
						int returnedStock = existInventory.getStock()+dto.getCart_qty();
						System.out.println(dto.getCart_qty());
						System.out.println(returnedStock);
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
			
		MessageDTO messageDTO = new MessageDTO();
		AdminDTO adminDTO = adminDAO.getAdmin();	
		
		
		Map<String,Object> Modifymap = new HashMap<String,Object>();
			Modifymap.put("modify_type", "extraInfo");
			Modifymap.put("order_no", order_no);
			Modifymap.put("order_deliverynum", "[해당없음]");
			Modifymap.put("order_refundaccount", orderDTO.getOrder_refundaccount());
			Modifymap.put("order_statement", orderDTO.getOrder_statement());
			
		int modify = tradingDAO.changeOrderInfo(Modifymap);
		if(modify==0) return "fail";
		
		if(orderDTO.getOrder_state()>OrderState.INDELIVERY.ordinal()) {
		List<ProductDTO> requestInventory = jsonTrans.makeJsonToList(orderDTO.getOrderlist_json());
			for(ProductDTO dto : requestInventory) {
				ProductDTO existInventory = productDAO.getInventoryInfo(dto.getProductID());
					int returnedStock = existInventory.getStock()+dto.getCart_qty();
					Map<String,String> map = new HashMap<String,String>();
						map.put("stock", returnedStock+""); 
						map.put("unitcost", dto.getUnitcost()+""); 
						map.put("productID", dto.getProductID());
						map.put("product_name_no", dto.getProduct_name_no()+"");
						map.put("ordering","0");
					productDAO.inventoryUpdate(map);
					dto.setStock(returnedStock);
			}		
		
			orderDTO.setOrderlist_json(jsonTrans.makeListToJson(requestInventory));
			int subResult = tradingDAO.implementingInventoryChange(orderDTO);
			if(subResult==0) return "fail";
		}
			
			messageDTO = mailing.sendRefundInfoMail(messageDTO, orderDTO);
			mailing.sendMail(adminDTO, messageDTO);		
			orderDTO.setOrder_state(OrderState.REFUNDPROCESSING.ordinal());
		int result = tradingDAO.modifyOrderAdmin(orderDTO);
		if(result==0) return "fail";
		 
		return "success";
	}	
	
	//14. 수취확인하기
	@RequestMapping(value="/confirmDelivery.do",method = RequestMethod.GET)
	@ResponseBody
	public void confirmDelivery(@RequestParam String order_no) {
		
		OrderDTO orderDTO = tradingDAO.getOrderInfo(order_no);
			orderDTO.setOrder_logtime(new Date());
		MemberDTO memberDTO = memberDAO.getUser(orderDTO.getOrder_id());
		MessageDTO messageDTO = new MessageDTO();
		AdminDTO adminDTO = adminDAO.getAdmin();
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
			
			salesInfoDAO.uploadSalesInfo(salesInfoDTO);
			
			
			orderDTO.setOrder_state(OrderState.ORDERCOMPLETED.ordinal());
			tradingDAO.modifyOrderAdmin(orderDTO);
			
	}
	
	//15. 거래내역 삭제하기
	@RequestMapping(value="/deleteOrder.do",method = RequestMethod.GET)
	@ResponseBody
	public void deleteOrder(@RequestParam String order_no) {
		tradingDAO.deleteOrder(order_no);
	}
	//16. 주문서 내역 수정요청하기
	@RequestMapping(value="/changeOrderInfo.do",method = RequestMethod.POST)
	@ResponseBody
	public String changeOrderInfo(@RequestParam Map<String,Object> map) {
		
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
	//17. 배너 호출하기
	@RequestMapping(value="/getBannerList.do",method = RequestMethod.GET)
	public ModelAndView getBannerList() {
		
		List<EventDTO> bannerList = tradingDAO.getBannerList();
	
		if(bannerList!=null) {
			for(EventDTO data: bannerList) {
				data.settingBanner();}}

		ModelAndView mav = new ModelAndView();
			mav.addObject("bannerList", bannerList);		
			mav.setViewName("jsonView");
			
		return mav;
	}
	//18. 주문 비밀번호 변경 발급하기
	@RequestMapping(value="/resetOrderPwd.do",method = RequestMethod.GET)
	@ResponseBody
	public String resetOrderPwd(@RequestParam String guest_id) {
		OrderDTO orderDTO = tradingDAO.orderCheck(guest_id);
		if(orderDTO==null) return "no_order_exist";
		else {
			
			String newPwd = mailing.getKey(8);
			orderDTO.setOrder_pwd(passwordEncoder.encode(newPwd));

			Map<String,Object> map =new HashMap<String,Object>();
				map.put("order_no",orderDTO.getOrder_no());
				map.put("order_pwd",orderDTO.getOrder_pwd());
				map.put("modify_type","resetPwd");
				
			int result = tradingDAO.changeOrderInfo(map);
			if(result==0) return "fail";
			
			AdminDTO adminDTO = adminDAO.getAdmin();
			
			MessageDTO messageDTO = new MessageDTO();
				messageDTO = mailing.sendPwdResetMail(messageDTO,orderDTO,newPwd);
				mailing.sendMail(adminDTO, messageDTO);	
				
			return "success";
		}
	}
	
}

