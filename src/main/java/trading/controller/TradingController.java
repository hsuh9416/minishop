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

@Controller
@RequestMapping(value="/trading/**")
public class TradingController {
	@Autowired 
	ProductDAO productDAO;

	//장바구니 페이지 접근ShoppingCart
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
	//장바구니 추가
	@RequestMapping(value = "/addCart.do", method = RequestMethod.POST)
	@ResponseBody
	public void addCart(@RequestParam String product_name_no,String cart_qty,HttpSession session) {
		int qty =0; 
		try {
			qty =Integer.parseInt(cart_qty);
		}catch(NumberFormatException nf) {
			qty = 1;
		}
		ShoppingCart shoppingCart = (ShoppingCart)session.getAttribute("shoppingCart");
		if (shoppingCart == null) {//장바구니 최초개설
			shoppingCart = new ShoppingCart();
			List<ProductDTO> cartList = new ArrayList<ProductDTO>();
			ProductDTO productDTO = productDAO.getProduct_NameInfo(product_name_no);
			productDTO.setCart_qty(qty);
			cartList.add(productDTO);//최초로 담기
			shoppingCart.setCartList(cartList);
			session.setAttribute("shoppingCart", shoppingCart);
			session.setAttribute("cartList", cartList);
		} else {
			List<ProductDTO> cartList = shoppingCart.getCartList();
			if(cartList==null) cartList = new ArrayList<ProductDTO>();
			int index = -1;
			int number = Integer.parseInt(product_name_no);
			if(shoppingCart.getCartList().size()>0) {//장바구니는 개설되었지만 카트에 아무것도 담겨 있지 않을 때
				index = this.exists(number, cartList);
			}
			if (index == -1) {//장바구니에 없는 상품인 경우
				ProductDTO productDTO = productDAO.getProduct_NameInfo(product_name_no);
				productDTO.setCart_qty(qty);
				cartList.add(productDTO);
			} else {//장바구니에 이미 있는 상품인 경우
				int quantity = cartList.get(index).getCart_qty() + qty;
				cartList.get(index).setCart_qty(quantity);
			}
			shoppingCart.setCartList(cartList);
			session.setAttribute("shoppingCart", shoppingCart);
			session.setAttribute("cartList", cartList);
		}		
	}
	//장바구니 제거
	@RequestMapping(value = "/removeCart.do", method = RequestMethod.POST)
	public ModelAndView removeCart(@RequestParam int[] check, HttpSession session) {
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		for(int product_name_no : check) {
			int index = this.exists(product_name_no, shoppingCart.getCartList());
			shoppingCart.getCartList().remove(index);		
		}
		session.setAttribute("shoppingCart", shoppingCart);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/trading/cartModified");
		return mav;
	}
	//cart상의 인덱스 번호를 반환, 없을 경우는 -1
	private int exists(int product_name_no, List<ProductDTO> cartList) {
		for (int i = 0; i < cartList.size(); i++) {
			if (cartList.get(i).getProduct_name_no()==(product_name_no)) {
				return i;
			}
		}
		return -1;
	}

	//장바구니 가져오기
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
	//장바구니 수량 수정
	@RequestMapping(value = "/modifyCart.do", method = RequestMethod.GET)
	public ModelAndView modifyCart(@RequestParam int product_name_no, int changeNum, HttpSession session) {
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
			int index = this.exists(product_name_no, shoppingCart.getCartList());
			shoppingCart.getCartList().get(index).setCart_qty(changeNum);
		session.setAttribute("shoppingCart", shoppingCart);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/trading/cartModified");
		return mav;
	}
	//상품 주문서 가져오기
	@RequestMapping(value="orderForm.do",method=RequestMethod.POST)
	public ModelAndView orderForm(HttpSession session,@RequestParam int[] product_name_no,@RequestParam(required = false) int cart_qty) {
		ModelAndView mav = new ModelAndView();
		int qty = 0;
		JsonElement orderList_JSON=null;
		List<ProductDTO> orderList = new ArrayList<ProductDTO>();
		if(qty != cart_qty) {//바로 주문한 경우에는 반영한다.		
			ShoppingCart shoppingCart = new ShoppingCart();
			ProductDTO productDTO = productDAO.getProduct_NameInfo(product_name_no[0]+"");
			productDTO.setCart_qty(cart_qty);
			orderList.add(productDTO);
			orderList_JSON = shoppingCart.makeListToJsonElement(orderList);
		}	
		else {
			ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");	
			for(int number : product_name_no) {
				int index = this.exists(number, shoppingCart.getCartList());
				shoppingCart.getCartList().get(index).setCart_qty(cart_qty);
				orderList.add(shoppingCart.getCartList().get(index));
				orderList_JSON = shoppingCart.makeListToJsonElement(orderList);
			}					
		}
		mav.addObject("orderList", orderList);		
		mav.addObject("orderList_JSON", orderList_JSON);
		mav.addObject("display", "/trading/orderForm.jsp");	
		mav.setViewName("/main/home");
		return mav;
	}

}

