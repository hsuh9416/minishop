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

import trading.bean.CartDTO;
import trading.bean.ShoppingCart;

@Controller
@RequestMapping(value="/trading/")
public class TradingController {
	@Autowired ShoppingCart shoppingCart;
	//장바구니 페이지 접근
	@RequestMapping(value = "/userCart.do", method = RequestMethod.GET)
	public ModelAndView userCart() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/trading/userCart.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	//장바구니 추가
	@RequestMapping(value = "/addCart.do", method = RequestMethod.GET)
	@ResponseBody
	public void addCart(@RequestParam String productID, int cart_qty,HttpSession session) {
		@SuppressWarnings("unchecked")
		List<CartDTO> cartList = (List<CartDTO>) session.getAttribute("cartList");
		if (cartList == null) {//장바구니 최초개설
			cartList = new ArrayList<CartDTO>();
			cartList.add(new CartDTO(shoppingCart.find(productID), cart_qty));
			session.setAttribute("cartList", cartList);
		} else {
			int index = this.exists(productID, cartList);
			if (index == -1) {
				cartList.add(new CartDTO(shoppingCart.find(productID), cart_qty));
			} else {
				int quantity = cartList.get(index).getCart_qty() + 1;
				cartList.get(index).setCart_qty(quantity);
			}
			session.setAttribute("cartList", cartList);
		}
	}
	//장바구니 제거
	@RequestMapping(value = "/removeCart.do", method = RequestMethod.GET)
	@ResponseBody
	public void removeCart(@RequestParam String[] check, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<CartDTO> cartList = (List<CartDTO>)session.getAttribute("cartList");
		for(String productID : check) {
			int index = this.exists(productID, cartList);
			cartList.remove(index);		
		}
		session.setAttribute("cartList", cartList);
	}
	//cart상의 인덱스 번호를 반환, 없을 경우는 -1
	private int exists(String productID, List<CartDTO> cartList) {
		for (int i = 0; i < cartList.size(); i++) {
			if (cartList.get(i).getProductDTO().getProductID().equalsIgnoreCase(productID)) {
				return i;
			}
		}
		return -1;
	}
	//장바구니 항목 전부 가져오기
	@RequestMapping(value = "/getCart.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getCart(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<CartDTO> cartList = (List<CartDTO>)session.getAttribute("cartList");
		ModelAndView mav = new ModelAndView();
		mav.addObject("cartList", cartList);		
		mav.setViewName("jsonView");
		return mav;
	}
}

