package product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import product.bean.ProductDTO;
import product.bean.ProductPaging;
import product.dao.ProductDAO;

@Controller
@RequestMapping(value="/product/**")
public class ProductController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ProductPaging productPaging;
	
	//view shop categories
	@RequestMapping(value="/categories.do",method = RequestMethod.GET)
	public ModelAndView categories(@RequestParam(required=false,defaultValue="1") String pg,String type) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg", pg);		
		mav.addObject("type", type);
		mav.addObject("display", "/product/categories.jsp");
		mav.setViewName("/main/home");
		return mav;
	}

	//view shop productView
	@RequestMapping(value="/productView.do",method = RequestMethod.GET)
	public ModelAndView productView(@RequestParam String productId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/product/productView.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	//페이징 처리 없는 순수 상품리스트 목록 가져오기
	@RequestMapping(value="/getAllproduct.do",method = RequestMethod.GET)
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
	//등재된 상품리스트 져오기
	@RequestMapping(value="/getProductList.do",method = RequestMethod.POST)
	public ModelAndView getProductList(@RequestParam(required=false,defaultValue="1") String pg,String type,String order) {
		ModelAndView mav = new ModelAndView();
	
		int page = Integer.parseInt(pg);
		int endNum = page*4;
		int startNum = endNum-3;	
		int totalA = 0;
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");	
		map.put("order", order);
		if(type.equals("All")) {
			totalA = productDAO.getTotalItemA(map);		
		}
		else {
			if(type.equals("Women")) map.put("product_category_no","1");
			else if(type.equals("Men")) map.put("product_category_no","2");
			else map.put("product_category_no","3");
			totalA = productDAO.getSelectedItemA(map);
		}

		List<ProductDTO> productList = productDAO.getProductList(map);
		
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(1);
		productPaging.setPageSize(4);
		productPaging.setTotalA(totalA);
		productPaging.makePagingHTML();;	
		
		//System.out.println(productList);
		
		mav.addObject("pg",pg);
		mav.addObject("type",type);
		mav.addObject("order",order);
		mav.addObject("productPaging", productPaging);		
		mav.addObject("productList", productList);		
		mav.setViewName("jsonView");
		return mav;
	}
	
}
