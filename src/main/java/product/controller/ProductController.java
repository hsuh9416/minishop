package product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import member.bean.GuestDTO;
import member.bean.MemberDTO;
import product.bean.ProductDTO;
import product.bean.ProductPaging;
import product.dao.ProductDAO;
import trading.bean.OrderDTO;
/*
 * 상품 관련 활동을 제어하는 클래스
 */
@Controller
@RequestMapping(value="/product/**")
public class ProductController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ProductPaging productPaging;
	
	//1. 상품 목록으로 이동
	@RequestMapping(value="/categories.do",method = RequestMethod.GET)
	public ModelAndView categories(@RequestParam(required=false,defaultValue="ALL") String product_category_name, @RequestParam(required=false,defaultValue="new") String order) {
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("location", "categories");
			mav.addObject("product_category_name", product_category_name);
			mav.addObject("order",order);
			mav.addObject("display", "/product/categories.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
			
	//2. 전체 목록 호출하기
	@RequestMapping(value="/getAllproduct.do", method = RequestMethod.GET)
	public ModelAndView getAllproduct() {
		
		List<ProductDTO> productList = productDAO.getUserProductList("ALL","name","");
		
		if(productList!=null) {
			for(ProductDTO data: productList) {data.makeProductListHTML();}			
		}

		ModelAndView mav = new ModelAndView();
			mav.addObject("productList", productList);		
			mav.setViewName("jsonView");
			
		return mav;
	}

	//3. 검색어 또는 카테고리로 검색한 목록 호출하기
	@RequestMapping(value="/getUserProductList.do",method = RequestMethod.GET)
	public ModelAndView getUserProductList(@RequestParam(required=false,defaultValue="ALL") String product_category_name,@RequestParam(required=false,defaultValue="new") String order,@RequestParam(required=false,defaultValue="") String searchWord) {
		
		List<ProductDTO> productList = productDAO.getUserProductList(product_category_name,order,searchWord);
		
		if(productList!=null) {
			for(ProductDTO data: productList) {
				data.makeProductListHTML();}}

		ModelAndView mav = new ModelAndView();
			mav.addObject("product_category_name", product_category_name);
			mav.addObject("order",order);
			mav.addObject("searchWord",searchWord);
			mav.addObject("productList", productList);		
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//4. 특별전으로 이동
	@RequestMapping(value="/eventProductList.do",method = RequestMethod.GET)
	public ModelAndView eventProductList(@RequestParam(required=false,defaultValue="new") String condition) {
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("location", "event");
			mav.addObject("condition", condition);
			mav.addObject("display", "/product/eventProductList.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}

	//5. 특별전 목록 호출
	@RequestMapping(value="/getSpecialProductList.do",method = RequestMethod.GET)
	public ModelAndView getSpecialProductList(@RequestParam(required=false,defaultValue="new") String condition) {
		
		List<ProductDTO> productList = productDAO.getUserProductList("ALL","name","");
		
		if(productList!=null) {
			for(ProductDTO data: productList) {data.makeSpecialListHTML(condition);}			
		}

		ModelAndView mav = new ModelAndView();
			mav.addObject("condition",condition);
			mav.addObject("productList", productList);		
			mav.setViewName("jsonView");
			
		return mav;
	}

	//6. 특정 상품 조회 화면 이동
	@RequestMapping(value="/productView.do",method = RequestMethod.GET)
	public ModelAndView productView(@RequestParam String product_name_no, HttpSession Session,HttpServletRequest request,HttpServletResponse response) {
		
		String id=""; int SEQ =0; boolean today = false;
		MemberDTO memberDTO =null; OrderDTO orderDTO=null; GuestDTO guestDTO=null;
		
		//(1) 조회자 정보 호출하기
		memberDTO = (MemberDTO)Session.getAttribute("memberDTO");
		if(memberDTO!=null) id=memberDTO.getId();
		else if(memberDTO==null) {
				orderDTO = (OrderDTO) Session.getAttribute("memberDTO");
			if(orderDTO!=null)	id = orderDTO.getOrder_id();
			else if(orderDTO==null) {
					guestDTO = (GuestDTO) Session.getAttribute("guestDTO");
				if(guestDTO!=null) id = guestDTO.getGuest_id();}}
		
		//(2) 조회수 업데이트
		Cookie[] ar = request.getCookies();
		if(ar!=null&&(memberDTO!=null|| orderDTO !=null|| guestDTO!=null)) {
			for(int i=0; i<ar.length; i++) {
				if((ar[i].getName()).equals(id+product_name_no)) {
					today = true;}
			}
			
			if(!today) {
					productDAO.product_hitUpdate(Integer.parseInt(product_name_no));
				Cookie cookie = new Cookie(id+product_name_no, product_name_no+"");
					cookie.setMaxAge(30*60);
					response.addCookie(cookie);}
		}
		
		//(3) 좋아요 여부 호출
		if(guestDTO==null&&!id.equals("")) {
			Map<String,String> map = new HashMap<String,String>();
				map.put("USERID", id);
				map.put("PRODUCT_NO", product_name_no);		
				SEQ = productDAO.getLikeValue(map);		
		}
	
		//(4) 상품 정보 호출
		ProductDTO productDTO = productDAO.getProductInfo(product_name_no);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("productDTO", productDTO);
			mav.addObject("SEQ",SEQ);		
			mav.addObject("location","productView");
			mav.addObject("display", "/product/productView.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//7. 좋아요 처리하기
	@RequestMapping(value="/likeOnAndOff.do",method = RequestMethod.GET)
	@ResponseBody
	public void likeOnAndOff(@RequestParam int product_name_no, HttpSession session,Model model)
	{		
		
		String id=""; int SEQ = 0;
		MemberDTO memberDTO =null; OrderDTO orderDTO=null; GuestDTO guestDTO=null;

		//(1) 조회자 정보 호출
		memberDTO = (MemberDTO)session.getAttribute("memberDTO");
		if(memberDTO!=null) id=memberDTO.getId();
		else if(memberDTO==null) {
			orderDTO = (OrderDTO) session.getAttribute("memberDTO");
			if(orderDTO!=null)	id = orderDTO.getOrder_id();
			else if(orderDTO==null) {
					guestDTO = (GuestDTO) session.getAttribute("guestDTO");
				if(guestDTO!=null) return;}}
		
		//(2) 좋아요 처리하기
		Map<String,String> map = new HashMap<String,String>();
			map.put("USERID", id);
			map.put("PRODUCT_NO", product_name_no+"");		
			SEQ = productDAO.getLikeValue(map);
			
		if(SEQ==0) {
			SEQ = productDAO.addLike(map);
			model.addAttribute("SEQ", SEQ);}
		else {
			productDAO.removeLike(SEQ);
			model.addAttribute("SEQ", 0);}
	}
	
}
