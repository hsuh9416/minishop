package product.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import member.bean.GuestDTO;
import member.bean.MemberDTO;
import product.bean.ProductDTO;
import product.bean.ProductPaging;
import product.dao.ProductDAO;
import trading.bean.OrderDTO;

@Controller
@RequestMapping(value="/product/**")
public class ProductController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ProductPaging productPaging;
	
	//상품 목록 가기
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
			
	//페이징 처리 없는 순수 상품리스트 목록 가져오기:리뷰등의 메서드 활용
	@RequestMapping(value="/getAllproduct.do", method = RequestMethod.GET)
	public ModelAndView getAllproduct() {
		List<ProductDTO> productList = productDAO.getAllproduct();
		//업로드전 처리
		for(ProductDTO data: productList) {
			data.makeProductListHTML();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("productList", productList);		
		mav.setViewName("jsonView");
		return mav;
	}

	//조건부 검색
	@RequestMapping(value="/getUserProductList.do",method = RequestMethod.GET)
	public ModelAndView getUserProductList(@RequestParam(required=false,defaultValue="ALL") String product_category_name,@RequestParam(required=false,defaultValue="new") String order,@RequestParam(required=false,defaultValue="") String searchWord) {
		List<ProductDTO> productList = null;
		productList = productDAO.getUserProductList(product_category_name,order,searchWord);
		//업로드전 처리
		for(ProductDTO data: productList) {
			data.makeProductListHTML();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("product_category_name", product_category_name);
		mav.addObject("order",order);
		mav.addObject("searchWord",searchWord);
		mav.addObject("productList", productList);		
		mav.setViewName("jsonView");
		return mav;
	}
	
	//특별전 이동
	@RequestMapping(value="/eventProductList.do",method = RequestMethod.GET)
	public ModelAndView eventProductList(@RequestParam(required=false,defaultValue="new") String condition) {
		ModelAndView mav = new ModelAndView();		
		mav.addObject("location", "event");
		mav.addObject("condition", condition);
		mav.addObject("display", "/product/eventProductList.jsp");
		mav.setViewName("/main/home");
		return mav;
	}

	//특별전 리스트 가져오기
	@RequestMapping(value="/getSpecialProductList.do",method = RequestMethod.GET)
	public ModelAndView getSpecialProductList(@RequestParam(required=false,defaultValue="new") String condition) {
		List<ProductDTO> productList = productDAO.getAllproduct();
		//업로드전 처리
		for(ProductDTO data: productList) {
			data.makeSpecialListHTML(condition);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("condition",condition);
		mav.addObject("productList", productList);		
		mav.setViewName("jsonView");
		return mav;
	}

	//개별 상품 보기
	@RequestMapping(value="/productView.do",method = RequestMethod.GET)
	public ModelAndView productView(@RequestParam String product_name_no, HttpSession Session,HttpServletRequest request,HttpServletResponse response) {
		String id="";
		MemberDTO memberDTO =null; OrderDTO orderDTO=null; GuestDTO guestDTO=null;
		
		memberDTO = (MemberDTO)Session.getAttribute("memberDTO");
		if(memberDTO!=null) id=memberDTO.getId();
		else if(memberDTO==null) {
			orderDTO = (OrderDTO) Session.getAttribute("memberDTO");
			if(orderDTO!=null)	id = orderDTO.getOrder_id();
			else if(orderDTO==null) {
				guestDTO = (GuestDTO) Session.getAttribute("guestDTO");
				if(guestDTO!=null) id = guestDTO.getGuest_id();
			}
		}
		boolean today = false;
		Cookie[] ar = request.getCookies();
		if(ar!=null&&(memberDTO!=null|| orderDTO !=null|| guestDTO!=null)) {
			for(int i=0; i<ar.length; i++) {
				if((ar[i].getName()).equals(id+product_name_no)) {
					today = true;}
			}//for
			
			if(!today) {
				productDAO.product_hitUpdate(Integer.parseInt(product_name_no));
				Cookie cookie = new Cookie(id+product_name_no, product_name_no+"");
				//System.out.println(cookie.getName());
				cookie.setMaxAge(30*60);
				response.addCookie(cookie);
			}//if not today
		}//hit update
		
		
		ModelAndView mav = new ModelAndView();
		ProductDTO productDTO = productDAO.getProduct_NameInfo(product_name_no);
		mav.addObject("productDTO", productDTO);
		mav.addObject("location","productView");
		mav.addObject("display", "/product/productView.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	
}
