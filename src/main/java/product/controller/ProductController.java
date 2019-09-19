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
			mav.addObject("location", "category");
			mav.addObject("product_category_name", product_category_name);
			mav.addObject("order",order);
			mav.addObject("display", "/product/categories.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
			
	//2. 전체 목록 호출하기
	@RequestMapping(value="/getAllproduct.do", method = RequestMethod.GET)
	public ModelAndView getAllproduct() {
		
		List<ProductDTO> productList = productDAO.getUserProductList("ALL","name","","");
		
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
	public ModelAndView getUserProductList(@RequestParam(required=false,defaultValue="ALL") String product_category_name,@RequestParam(required=false,defaultValue="new") String order,
			String searchOption, @RequestParam(required=false,defaultValue="") String searchWord) {
		
		List<ProductDTO> productList = productDAO.getUserProductList(product_category_name,order,searchOption,searchWord);
		
		if(productList!=null) {
			for(ProductDTO data: productList) {
				data.makeProductListHTML();}}

		ModelAndView mav = new ModelAndView();
			mav.addObject("product_category_name", product_category_name);
			mav.addObject("order",order);
			mav.addObject("searchOption",searchOption);			
			mav.addObject("searchWord",searchWord);
			mav.addObject("productList", productList);		
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//4. 특별전으로 이동
	@RequestMapping(value="/eventProductList.do",method = RequestMethod.GET)
	public ModelAndView eventProductList(@RequestParam(required=false,defaultValue="newArrival") String condition) {
		
		ModelAndView mav = new ModelAndView();		
			mav.addObject("location", "category");
			mav.addObject("condition", condition);
			mav.addObject("display", "/product/eventProductList.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}

	//5. 특별전 목록 호출
	@RequestMapping(value="/getSpecialProductList.do",method = RequestMethod.GET)
	public ModelAndView getSpecialProductList(@RequestParam(required=false,defaultValue="newArrival") String condition, String searchOption,
			@RequestParam(required=false,defaultValue="") String searchWord) {
		
		List<ProductDTO> productList = productDAO.getUserProductList("ALL","name",searchOption,searchWord);
		
		if(productList!=null) {
			for(ProductDTO data: productList) {data.makeSpecialListHTML(condition);}			
		}

		ModelAndView mav = new ModelAndView();
			mav.addObject("condition",condition);
			mav.addObject("searchOption",searchOption);			
			mav.addObject("searchWord",searchWord);
			mav.addObject("productList", productList);		
			mav.setViewName("jsonView");
			
		return mav;
	}

	//6. 특정 상품 조회 화면 이동
	@RequestMapping(value="/productView.do",method = RequestMethod.GET)
	public ModelAndView productView(@RequestParam String product_name_no, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		String id=""; String SEQ="NO"; boolean today = false;
		MemberDTO memberDTO =null; GuestDTO guestDTO=null;  GuestDTO visitorDTO=null;
		
		//(1) 조회자 정보 호출하기
		memberDTO = (MemberDTO)session.getAttribute("memberDTO");
		if(memberDTO!=null) id=memberDTO.getId();
		else{
			guestDTO = (GuestDTO) session.getAttribute("guestDTO");
			if(guestDTO!=null) id = guestDTO.getGuest_id();
			else {
				visitorDTO = (GuestDTO) session.getAttribute("visitorDTO");	
				id = visitorDTO.getGuest_id();}
		}
		
		//(2) 조회수 업데이트
		Cookie[] ar = request.getCookies();
		if(ar!=null&&(memberDTO!=null || guestDTO!=null || visitorDTO!=null)) {
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
		if(!id.equals("")) {
			Map<String,String> map = new HashMap<String,String>();
				map.put("USERID", id);
				map.put("PRODUCT_NO", product_name_no);		
				int result = productDAO.getLikeValue(map);	
				if(result !=0) SEQ="YES";
		}
	
		//(4) 상품 정보 호출
		ProductDTO productDTO = productDAO.getProductInfo(product_name_no);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("productDTO", productDTO);
			mav.addObject("SEQ",SEQ);		
			mav.addObject("location","category");
			mav.addObject("display", "/product/productView.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//7. 좋아요 처리하기
	@RequestMapping(value="/likeOnAndOff.do",method = RequestMethod.GET)
	@ResponseBody
	public String likeOnAndOff(@RequestParam int product_name_no, HttpSession session)
	{		
		
		String id=""; 
		MemberDTO memberDTO =null; GuestDTO guestDTO=null;
		
		//(1) 조회자 정보 호출하기
		memberDTO = (MemberDTO)session.getAttribute("memberDTO");
		if(memberDTO!=null) id=memberDTO.getId();
		else{
			guestDTO = (GuestDTO) session.getAttribute("guestDTO");
			if(guestDTO!=null) id = guestDTO.getGuest_id();
		}
		if(!id.equals("")) {
			//(2) 좋아요 처리하기
			Map<String,String> map = new HashMap<String,String>();
				map.put("USERID", id);
				map.put("PRODUCT_NO", product_name_no+"");		
				int result = productDAO.getLikeValue(map);	
				if(result==0) {
					productDAO.addLike(map);
					return "YES";}
				else if(result!=0){
					productDAO.removeLike(result);
					return "NO";}}
		
		return "NO";
		}
		

}
