package admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import product.bean.ProductDTO;
import product.bean.ProductPaging;
import product.dao.ProductDAO;
/*
 * 관리자: 상품 및 재고 관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/product/**")
public class AdminProductController {
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductPaging productPaging;
	
//-----------재고 관리:START-----------//
	
	//1. 재고 관리 화면 이동
	@RequestMapping(value="/inventoryManage.do",method = RequestMethod.GET)
	public ModelAndView inventoryManage(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "productAdmin");
			mav.addObject("display", "/admin/product/inventoryManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	

	//2. 재고 목록 가져오기(한페이지당 3게시물,3블록 표시)
	@RequestMapping(value="/inventoryList.do",method= RequestMethod.POST)
	public ModelAndView inventoryList(@RequestParam(required=false,defaultValue="1") String pg){
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		int totalA=0;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "product");		
			totalA = productDAO.getTotalA(map);
		
		List<ProductDTO> inventoryList = productDAO.inventoryList(startNum, endNum);
	
			productPaging.setCurrentPage(page);
			productPaging.setPageBlock(3);
			productPaging.setPageSize(3);
			productPaging.setTotalA(totalA);
			productPaging.makePagingHTML();;	
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("productPaging", productPaging);
			mav.addObject("inventoryList", inventoryList);
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//3. 특정 검색어에 해당하는 재고 목록 가져오기(한페이지당 3게시물,3블록 표시)
	@RequestMapping(value="/inventorySearch.do",method= RequestMethod.POST)
	public ModelAndView inventorySearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		int totalA=0;
		
			if(searchOption.equals("productid")) keyword = keyword.toUpperCase();
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption",searchOption);
			map.put("keyword", keyword);
			map.put("tableName", "PRODUCT");
			totalA = productDAO.getTotalSearchA(map);	
			
			productPaging.setCurrentPage(page);
			productPaging.setPageBlock(3);
			productPaging.setPageSize(3);
			productPaging.setTotalA(totalA);
			productPaging.makeSearchPagingHTML();				
		
		List<ProductDTO> inventorySearchList = productDAO.inventorySearch(map);
		ModelAndView mav = new ModelAndView();		
			mav.addObject("pg", pg);
			mav.addObject("inventoryList", inventorySearchList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("productPaging", productPaging);
			mav.setViewName("jsonView");
		
		return mav;	
	}	
	
	//4. 재고 수정 팝업창 불러오기
	@RequestMapping(value="/inventoryModify.do",method = RequestMethod.GET)
	public ModelAndView inventoryModify(@RequestParam(required=false,defaultValue="1") String pg, String productID) {
		
		ProductDTO productDTO = productDAO.getInventoryInfo(productID);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("productDTO", productDTO);	
			mav.addObject("location", "productAdmin");
			mav.setViewName("/admin/product/inventoryModify");
			
		return mav;
	}	
	
	//5. 재고 수정 반영하기
	@RequestMapping(value="/doModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void doModify(@RequestParam Map<String,String> map){
		System.out.println(map.get("ordernum"));
		productDAO.inventoryUpdate(map);
	}	

//-----------재고 관리:END-----------//

//-----------상품 관리:START-----------//
	
	//1.상품 관리 화면 이동
	@RequestMapping(value="/productManage.do",method = RequestMethod.GET)
	public ModelAndView productManage(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "productAdmin");		
			mav.addObject("display", "/admin/product/productManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//2. 상품 목록 가져오기(한페이지당 3게시물,3블록 표시)
	@RequestMapping(value="/productList.do",method= RequestMethod.POST)
	public ModelAndView productList(@RequestParam(required=false,defaultValue="1") String pg){
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;	
		int totalA = 0;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "product_name");
			totalA = productDAO.getTotalA(map);
		
		List<ProductDTO> productList = productDAO.productList(startNum, endNum);
	
			productPaging.setCurrentPage(page);
			productPaging.setPageBlock(3);
			productPaging.setPageSize(3);
			productPaging.setTotalA(totalA);
			productPaging.makePagingHTML();;	
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("productPaging", productPaging);
			mav.addObject("productList", productList);
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//3. 특정 검색어에 해당하는 상품 목록 가져오기(한페이지당 3게시물,3블록 표시)
	@RequestMapping(value="/productSearch.do",method= RequestMethod.POST)
	public ModelAndView productSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		int totalA = 0;
		
		if(searchOption.equals("productid")) keyword = keyword.toUpperCase();
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption",searchOption);
			map.put("keyword", keyword);
			map.put("tableName", "PRODUCT_NAME");
			map.put("joinName", "PRODUCT");				
			totalA = productDAO.getTotalSearchA(map);	
		
			productPaging.setCurrentPage(page);
			productPaging.setPageBlock(3);
			productPaging.setPageSize(3);
			productPaging.setTotalA(totalA);
			productPaging.makeSearchPagingHTML();	
			
		List<ProductDTO> productSearchList = productDAO.productSearch(map);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("productList", productSearchList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("productPaging", productPaging);
			mav.setViewName("jsonView");
			
		return mav;	
	}
	
	//4. 특정 상품조회 화면 이동 
	@RequestMapping(value="/productView.do",method=RequestMethod.GET)
	public ModelAndView productView(@RequestParam(required=false,defaultValue="1") String pg, String product_name_no) {
		
		ProductDTO productDTO = productDAO.getProductInfo(product_name_no);		
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("productDTO",productDTO);
			mav.addObject("pg", pg);
			mav.addObject("location", "productAdmin");
			mav.addObject("display", "/admin/product/productView.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//5. 상품 등록 화면 이동
	@RequestMapping(value="/productUpload.do",method=RequestMethod.GET)
	public ModelAndView productUpload() {
		
		ModelAndView mav = new ModelAndView();	
			mav.addObject("location", "productAdmin");	
			mav.addObject("display", "/admin/product/productUpload.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//6. 상품 상세정보 등록 이미지(CKEDITOR4 이미지) 업로드 하기
	@RequestMapping(value="/productImgUpload.do")
	@ResponseBody
	public Map<String,Object> productImgUpload(HttpServletRequest request,@RequestParam MultipartFile upload)
	throws Exception
	{
		boolean result;
		int post;
		String ext;
		String fileName;
		String fileUrl;
		String Localpath;
		String uploadPath;
		String[] images;
		
			fileName = upload.getOriginalFilename();
			post = fileName.lastIndexOf(".");
			ext = fileName.substring(post + 1).toLowerCase();
			images = new String[]{"jpg", "jpeg", "png", "gif", "bmp"};
			result = false;
			
		Map<String,Object> data= new HashMap<String,Object>();	
	    		//(1) 이미지 양식 검증
	        for (String str : images) {
	            if (str.equals(ext)) {
	                result = true;
	                break;}}
	        
	        if(!result) {
					data.put("uploaded", 0);
					data.put("fileName", fileName);		
					data.put("error", "올바른 형식의 이미지가 아닙니다.");
					
				return data;}

	    
	        try {
				//(2) 서버에 업로드
				uploadPath = request.getSession().getServletContext().getRealPath("/")+"storage\\product\\"+fileName;
				upload.transferTo(new File(uploadPath));
				fileUrl = request.getContextPath()+"/storage/product/"+fileName;
				
				data.put("fileName", uploadPath);
				data.put("uploaded", 1);
				data.put("url", fileUrl);		
				
				//(3) WEB 미리보기용 업로드
				Localpath="D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\product\\";
			try {
				FileCopyUtils.copy(upload.getInputStream(), new FileOutputStream(new File(Localpath,fileName)));}
			catch (IOException e) {e.printStackTrace();}}
	        catch(IOException e) {e.printStackTrace();}
		
		return data;
	}
	
	//7. 상품 등록하기
	@RequestMapping(value="/doUpload.do",method=RequestMethod.POST)
	public ModelAndView doUpload(@ModelAttribute ProductDTO productDTO, @RequestParam MultipartFile product_image, String date, HttpServletRequest request){
		
		
		boolean result;
		Date product_name_instockdate;
		File newFile;
		int done1;
		int done2;
		int post;
		int product_name_no;
		String ext;
		String filePath; 
		String originalfileName;
		String state;
		String uploadPath;
		String uploadfileName;
		String[] images;
		
			//(1) 받아온 글자를 date 형식으로 치환
		SimpleDateFormat targetDate = new SimpleDateFormat("yyyy-mm-dd");
			if(date==null||date.equals("")) {
				Date today = new Date();
				productDTO.setProduct_name_instockdate(today);
			}
			else {
				try {
					product_name_instockdate = targetDate.parse(date);			
				} catch (ParseException e) {e.printStackTrace(); product_name_instockdate=null;}
				
				productDTO.setProduct_name_instockdate(product_name_instockdate);}
			
			//(2) 이미지 양식 검증(검증 이후 파일 네임을 반환)
			originalfileName = product_image.getOriginalFilename();

			
			post = originalfileName.lastIndexOf(".");
			ext = originalfileName.substring(post + 1).toLowerCase();
			images = new String[]{"jpg", "jpeg", "png", "gif", "bmp"};
			result = false;
			
	        for (String str : images) {
	            if (str.equals(ext)) {
	                result = true;
	                break;}}
	        
	        if(!result) {state="WrongFile";}
	        else {
	        
	        	originalfileName = originalfileName.substring(0, post)+"."+ext;

	    	
			
		        //(3) seq 배정받고 PRODUCTID 재설정
		        product_name_no = productDAO.getSeq();
				productDTO.setProduct_name_no(product_name_no);
				productDTO.setProductID(productDTO.getProductID()+product_name_no);
				
	
				//(4) 서버에 업로드 
				uploadfileName = productDTO.getProductID()+"."+ext;	
				productDTO.setProduct_name_image(uploadfileName);
				uploadPath = request.getSession().getServletContext().getRealPath("/")+"storage\\onstore\\"+uploadfileName;
		     
				try {
					product_image.transferTo(new File(uploadPath));}
			     catch (IllegalStateException | IOException e) {e.printStackTrace();}
		     
				//(5) workspace상에 업로드
				filePath = "D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\onstore\\";
				newFile = new File(filePath,uploadfileName);
	     
				try {FileCopyUtils.copy(product_image.getInputStream(), new FileOutputStream(newFile));} 
				catch (IOException e) {e.printStackTrace();}	     
				System.out.println(productDTO);
				//(6) DB upload
				try {
					done1 = productDAO.productUpload(productDTO);
					if(done1==0) {
						state="입점실패";}
					else {
						if(productDTO.getProduct_onstore().equals("YES")){
							done2 = productDAO.inventoryUpload(productDTO);
							if(done2==1) state = "입점완료";
							else state="입점실패";}
						else state="상품등록완료";}}
				catch(Exception e) {e.printStackTrace();	state="입점실패";}}
	        
		//(7) 최종 반환
		ModelAndView mav = new ModelAndView();
			mav.addObject("stateCode",state);
			mav.setViewName("/admin/product/stateCode");
			
		return mav;
	}
	
	//8. 상품 수정 화면 이동
	@RequestMapping(value="/productModifyForm.do",method=RequestMethod.GET)
	public ModelAndView productModifyForm(@RequestParam String product_name_no) {
		
		ProductDTO productDTO = productDAO.getProductInfo(product_name_no);
		
		ModelAndView mav = new ModelAndView();	
			mav.addObject("location", "productAdmin");	
			mav.addObject("productDTO", productDTO);	
			mav.addObject("display", "/admin/product/productModifyForm.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//9. 상품 수정하기
	@RequestMapping(value="/productModify.do",method=RequestMethod.POST)
	public ModelAndView productModify(@ModelAttribute ProductDTO productDTO, @RequestParam MultipartFile product_image_new, String date, HttpServletRequest request){
		
		boolean result;
		Date product_name_instockdate = null;
		int done1;
		int done2;
		int post;
		String ext;
		String filePath;
		String originalfileName;
		String state;
		String uploadfileName;
		String uploadPath;
	    String[] images;
	    
			//(1) 재고 확인
		ProductDTO inventory = productDAO.getInventoryInfo(productDTO.getProductID());

		
			//(2) 받아온 글자를 date 형식으로 치환
		SimpleDateFormat targetDate = new SimpleDateFormat("yyyy-mm-dd");
			if(date==null||date.equals("")) {
				Date today = new Date();
					productDTO.setProduct_name_instockdate(today);}
			else {
				try {
					product_name_instockdate = targetDate.parse(date);}
				catch (ParseException e) {e.printStackTrace();}
	
			productDTO.setProduct_name_instockdate(product_name_instockdate);}
			
			//(3) 미입점시 입점처리
			productDTO.setProductID(productDTO.getProductID()+productDTO.getProduct_name_no());
		
			//(4) 이미지 양식 검증(검증 이후 파일 이름을 반환)
			if(!product_image_new.isEmpty()) {
				originalfileName = product_image_new.getOriginalFilename();


				
				post = originalfileName.lastIndexOf(".");
				ext = originalfileName.substring(post + 1).toLowerCase();//ext
				images = new String[]{"jpg", "jpeg", "png", "gif", "bmp"};
				result = false;
				
	        for (String str : images) {
	            if (str.equals(ext)) {
	                result = true;
	                break;}}
	        
	        if(!result) {state="WrongFile";}
	        else {originalfileName = originalfileName.substring(0, post)+"."+ext;}
	        
	        	uploadfileName = productDTO.getProductID()+"."+ext;
	        	productDTO.setProduct_name_image(uploadfileName);

	        	//(5) 서버에 업로드
	        	uploadPath = request.getSession().getServletContext().getRealPath("/")+"storage\\onstore\\"+uploadfileName;
	        	
		        try {
		        	product_image_new.transferTo(new File(uploadPath));}
		        catch (IllegalStateException | IOException e) {e.printStackTrace();}
	        
		        //(6)workspace상에 업로드
		        filePath = "D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\onstore\\";
		        
	     File newFile = new File(filePath,uploadfileName);
				try {FileCopyUtils.copy(product_image_new.getInputStream(), new FileOutputStream(newFile));} 
				catch (IOException e) {e.printStackTrace();}	  
		}
	
				//(7) DB upload
			state="변경실패";
			try {
				done1 = productDAO.productModify(productDTO);
				if(done1==0) {state="변경실패";}
				else {
					if(productDTO.getProduct_onstore().equals("YES")){
						if(inventory==null) {
							done2 = productDAO.inventoryUpload(productDTO);
							if(done2==1) state = "입점완료";
							else state="입점실패";	}
						else if(inventory!=null) {
							done2 = productDAO.inventoryModify(productDTO);
						if(done2==1) state = "상품변경완료";
						else state="변경실패";	}}
					else state="상품변경완료";}}
			catch(Exception e) {e.printStackTrace(); state="변경실패";}
		
		//(8) 결과 최종 송출
		ModelAndView mav = new ModelAndView();
			mav.addObject("stateCode",state);
			mav.setViewName("/admin/product/stateCode");
			
		return mav;
	}
	
	//10. 상품 삭제하기(재고 삭제도 병행)
	@RequestMapping(value="/productDelete.do", method=RequestMethod.GET)
	public String productDelete(@RequestParam String product_name_no,Model model) {
		
			productDAO.productDelete(product_name_no);
			productDAO.inventoryDelete(product_name_no);
			
			model.addAttribute("stateCode", "deleted");	
		
		return "/admin/product/stateCode";
	}
	
//-----------상품 관리:END-----------//	
}
