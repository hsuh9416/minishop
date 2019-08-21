package trading.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import trading.bean.CouponDTO;
import trading.bean.DeliveryDTO;
import trading.bean.EventDTO;
import trading.bean.JsonTransitioner;
import trading.bean.OrderDTO;
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
	public ModelAndView orderForm(HttpSession session,@RequestParam int[] product_name_no,@RequestParam(required = false) int cart_qty) {
		
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
	public String putOrderForm(@ModelAttribute OrderDTO orderDTO, @RequestParam(required = false) String point,@RequestParam(required = false) String coupon_amount, @RequestParam(required = false) String coupon_option, HttpSession session) {	

		
		String order_pwd = "회원비밀번호와 동일함";
		
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		
		JsonElement orderList_JSON = (JsonElement)session.getAttribute("orderList_JSON");	
		List<ProductDTO> orderList = jsonTrans.makeJsonToList(orderList_JSON.toString());
			orderDTO.setOrderlist_json(jsonTrans.makeListToJson(orderList));
			
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		
		if(shoppingCart!=null) {
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
				if(point!=null&&!point.equals("0")) {
					memberDAO.reducePoint(memberDTO.getId(), point);

					OrderDTO pointPay = new OrderDTO();
						pointPay.setOrder_no(order_no);
						pointPay.setPayment_amount(Integer.parseInt(point));
						pointPay.setPayment_method(PaymentMethod.POINT.ordinal());
						pointPay.setPayment_date(today);
						
						tradingDAO.setPayment(pointPay);
				}
				if (!coupon_amount.equals("0")) {
					String[] primarySplit= coupon_option.split("[");
					String[] secoundarySplit = primarySplit[1].split("]");
					System.out.println(secoundarySplit[1]);
					String coupon_no = secoundarySplit[1];
					
					Map<String,String> map = new HashMap<String,String>();
						map.put("coupon_no", coupon_no);
						map.put("id", memberDTO.getId());
						tradingDAO.usedUserBenefit(map);
					
					OrderDTO couponPay = new OrderDTO();
						couponPay.setOrder_no(order_no);
						couponPay.setPayment_amount(Integer.parseInt(coupon_amount));
						couponPay.setPayment_method(PaymentMethod.COUPON.ordinal());
						couponPay.setPayment_date(today);	
						
						tradingDAO.setPayment(couponPay);

				}
				
				if(orderDTO.getPayment_method()==PaymentMethod.CARD.ordinal()||orderDTO.getPayment_method()==PaymentMethod.NOPAID.ordinal()) {
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
			if(orderDTO.getPayment_method()==1) orderDTO.setPayment_date(today);
			
			tradingDAO.setPayment(orderDTO);
			
			GuestDTO guestDTO = new GuestDTO();
				guestDTO.setGuest_id(orderDTO.getOrder_id());
				guestDTO.setGuest_pwd(orderDTO.getOrder_pwd());
				guestDTO.setGuest_name(orderDTO.getOrder_name());
				guestDTO.setGuest_address(orderDTO.getOrder_address());
				guestDTO.setGuest_tel(orderDTO.getOrder_tel());
				guestDTO.setOrder_no(orderDTO.getOrder_id());
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
					
					AdminDTO adminDTO = adminDAO.getAdmin();
					
					mailing.sendMail(adminDTO, messageDTO);
					
					return orderDTO.getOrder_no()+"";
			}
			
			
			return "fail";
			
	}	
	
	//9. 주문 내역서 확인 화면 이동
	@RequestMapping(value="/orderView.do",method = RequestMethod.GET)
	public ModelAndView orderView(@RequestParam String order_no) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("order_no", order_no);
			mav.addObject("display", "/trading/orderView.jsp");
			mav.setViewName("/main/home");
		
		return mav;
	}
	
	//1333. 배너 호출하기
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
	
	
}

