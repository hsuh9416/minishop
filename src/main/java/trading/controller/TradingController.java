package trading.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonElement;

import product.bean.ProductDTO;
import product.dao.ProductDAO;
import trading.bean.ShoppingCart;
/*
 * 사용자 거래 관련 활동을 제어하는 클래스
 */
@Controller
@RequestMapping(value="/trading/**")
public class TradingController {
	@Autowired 
	ProductDAO productDAO;

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
		ShoppingCart shoppingCart;
		ProductDTO productDTO;
		
		List<ProductDTO> orderList = new ArrayList<ProductDTO>();				
			qty=0;
			index=-1;
			orderList_JSON=null;
		
		if(qty != cart_qty) {
				shoppingCart = new ShoppingCart();
				productDTO = productDAO.getProductInfo(product_name_no[0]+"");
				productDTO.setCart_qty(cart_qty);
				orderList.add(productDTO);
				orderList_JSON = shoppingCart.makeListToJsonElement(orderList);}
		else {
				shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");	
			for(int number : product_name_no) {
					index = shoppingCart.exists(number, shoppingCart.getCartList());
					shoppingCart.getCartList().get(index).setCart_qty(cart_qty);
					orderList.add(shoppingCart.getCartList().get(index));
					orderList_JSON = shoppingCart.makeListToJsonElement(orderList);}}
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("orderList", orderList);		
			mav.addObject("orderList_JSON", orderList_JSON);
			mav.addObject("display", "/trading/orderForm.jsp");	
			mav.setViewName("/main/home");
			
		return mav;
	}

}

