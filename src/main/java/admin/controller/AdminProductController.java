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

@Controller
@RequestMapping(value="/admin/product/**")
public class AdminProductController {
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductPaging productPaging;
	
	/* 재고 관리*/
	//재고 관리 화면
	@RequestMapping(value="/inventoryManage.do",method = RequestMethod.GET)
	public ModelAndView inventoryManage(@RequestParam(required=false,defaultValue="1") String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("location", "inventory");
		mav.addObject("display", "/admin/product/inventoryManage.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	

	//글목록 가져오기(3게시물,3블록 표시)
	@RequestMapping(value="/inventoryList.do",method= RequestMethod.POST)
	public ModelAndView inventoryList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "product");		
		int totalA = productDAO.getTotalA(map);
		
		List<ProductDTO> inventoryList = productDAO.inventoryList(startNum, endNum);
	
		//페이징 처리
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
	//검색어 제한 글목록
	@RequestMapping(value="/inventorySearch.do",method= RequestMethod.POST)
	public ModelAndView inventorySearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		map.put("searchOption",searchOption);
		map.put("keyword", keyword);
		map.put("tableName", "product");
		map.put("joinName", "product_name");		
		List<ProductDTO> inventorySearchList = productDAO.inventorySearch(map);
		System.out.println(inventorySearchList);
		ModelAndView mav = new ModelAndView();
		int totalA = productDAO.getTotalSearchA(map);		
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(3);
		productPaging.setPageSize(3);
		productPaging.setTotalA(totalA);
		productPaging.makeSearchPagingHTML();		
		mav.addObject("pg", pg);
		mav.addObject("inventorySearchList", inventorySearchList);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);
		mav.addObject("productPaging", productPaging);
		mav.setViewName("jsonView");
		return mav;	
	}	
	//재고 변동 팝업
	@RequestMapping(value="/inventoryModify.do",method = RequestMethod.GET)
	public ModelAndView inventoryModify(@RequestParam(required=false,defaultValue="1") String pg, String productID) {
		ProductDTO productDTO = productDAO.getProductInfo(productID);
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg", pg);
		mav.addObject("productDTO", productDTO);		
		mav.setViewName("/admin/product/inventoryModify");
		return mav;
	}	
	//재고 변동 반영
	@RequestMapping(value="/doModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void doModify(@RequestParam Map<String,String> map){
		productDAO.doModify(map);
	}	
	
	/* 상품 관리*/
	//등록 관리 화면
	@RequestMapping(value="/productManage.do",method = RequestMethod.GET)
	public ModelAndView productManage(@RequestParam(required=false,defaultValue="1") String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("location", "adminProduct");		
		mav.addObject("display", "/admin/product/productManage.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	
	
	//글목록 가져오기(5게시물,3블록 표시)
	@RequestMapping(value="/productList.do",method= RequestMethod.POST)
	public ModelAndView productList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "product_name");
		int totalA = productDAO.getTotalA(map);
		
		List<ProductDTO> productList = productDAO.productList(startNum, endNum);
	
		//페이징 처리
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
	//검색어 제한 글목록
	@RequestMapping(value="/productSearch.do",method= RequestMethod.POST)
	public ModelAndView productSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		map.put("searchOption",searchOption);
		map.put("keyword", keyword);
		map.put("tableName", "product_name");
		map.put("joinName", "product");			
		List<ProductDTO> productSearchList = productDAO.productSearch(map);
		ModelAndView mav = new ModelAndView();
		int totalA = productDAO.getTotalSearchA(map);		
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(3);
		productPaging.setPageSize(3);
		productPaging.setTotalA(totalA);
		productPaging.makeSearchPagingHTML();		
		mav.addObject("pg", pg);
		mav.addObject("productSearchList", productSearchList);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);
		mav.addObject("productPaging", productPaging);
		mav.setViewName("jsonView");
		return mav;	
	}
	
	//특정 상품 뷰로 이동
	@RequestMapping(value="/productView.do",method=RequestMethod.GET)
	public ModelAndView productView(@RequestParam(required=false,defaultValue="1") String pg, String product_name_no) {
		ModelAndView mav = new ModelAndView();
		ProductDTO productDTO = productDAO.getProduct_NameInfo(product_name_no);
		mav.addObject("productDTO",productDTO);
		mav.addObject("pg", pg);
		mav.addObject("location", "adminProduct");
		mav.addObject("display", "/admin/product/productView.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	//상품 등록 페이지 이동
	@RequestMapping(value="/productUpload.do",method=RequestMethod.GET)
	public ModelAndView productUpload() {
		ModelAndView mav = new ModelAndView();	
		mav.addObject("location", "adminProduct");	
		mav.addObject("display", "/admin/product/productUpload.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	//글쓰기 이미지 업로드
	@RequestMapping(value="/productImgUpload.do")
	@ResponseBody
	public Map<String,Object> productImgUpload(HttpServletRequest request,@RequestParam MultipartFile upload)
	throws Exception
	{
		Map<String,Object> data = new HashMap<String,Object>();
		String fileUrl = "";
		String uploadPath = "";
		String fileName = upload.getOriginalFilename();

	    //이미지 양식 검증
	     boolean result = false;
	      int post = fileName.lastIndexOf(".");
	       String ext = fileName.substring(post + 1).toLowerCase();
	       String[] images = {"jpg", "jpeg", "png", "gif", "bmp"};
	        for (String str : images) {
	            if (str.equals(ext)) {
	                result = true;
	                break;
	            }
	        }
	        if(!result) {
				data.put("uploaded", 0);
				data.put("fileName", fileName);		
				data.put("error", "올바른 형식의 이미지가 아닙니다.");
				return data;
	        }

	    
		try {
			//실제 업로드 과정
			uploadPath = request.getSession().getServletContext().getRealPath("/")+"\\storage\\product\\"+fileName;//저장경로
			//System.out.println(uploadPath);
			upload.transferTo(new File(uploadPath));
			fileUrl = request.getContextPath()+"/storage/product/"+fileName;//url경로
			//System.out.println(fileUrl);
			data.put("fileName", uploadPath);
			data.put("uploaded", 1);
			data.put("url", fileUrl);		
			//DB저장용 product upload
			String Localpath="D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\product\\";
			try {
				FileCopyUtils.copy(upload.getInputStream(), new FileOutputStream(new File(Localpath,fileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	//상품 등록
	@RequestMapping(value="/doUpload.do",method=RequestMethod.POST)
	public ModelAndView doUpload(@ModelAttribute ProductDTO productDTO, @RequestParam MultipartFile product_image, String date, Model map,HttpServletRequest request){
		Date product_name_instockdate = null;
		String state;

		
		//1.받아온 글자를 date 형식으로 치환
		SimpleDateFormat targetDate = new SimpleDateFormat("yyyy-mm-dd");
		if(date!=null) {
		try {
			product_name_instockdate = targetDate.parse(date);			
		} catch (ParseException e) {e.printStackTrace();}
		
		productDTO.setProduct_name_instockdate(product_name_instockdate);	
		}
		//2.파일 형식 확인
		//업로드 된 파일명
		String originalfileName = product_image.getOriginalFilename();

	    //이미지 양식 검증(검증 이후 파일 네임을 반환
	     boolean result = false;
	      int post = originalfileName.lastIndexOf(".");
	       String ext = originalfileName.substring(post + 1).toLowerCase();//ext
	       String[] images = {"jpg", "jpeg", "png", "gif", "bmp"};
	        for (String str : images) {
	            if (str.equals(ext)) {
	                result = true;
	                break;
	            }
	        }
	        if(!result) {state="WrongFile";}//오류로 복귀
	        else {
	        originalfileName = originalfileName.substring(0, post)+"."+ext; //이미지명.

	    	
			
		//3.seq 배정받기
		int product_name_no = productDAO.getSeq();
		productDTO.setProduct_name_no(product_name_no);//seq 설정
		productDTO.setProductID(productDTO.getProductID()+product_name_no);//등록코드 변경ex>CSXS2200
			//업로드될 파일명
		String uploadfileName = productDTO.getProductID()+"."+ext;//기본적으로는 productID와 동일ex>CSXS2200.jpg		
		 productDTO.setProduct_name_image(uploadfileName);//이미지 파일명	
		//4.서버에 업로드
	     String uploadPath = request.getSession().getServletContext().getRealPath("/")+"\\storage\\onstore\\"+uploadfileName;
	     try {
			product_image.transferTo(new File(uploadPath));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	     //5.workspace상에 업로드
	     String filePath = "D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\onstore\\";
	     
	     File newFile = new File(filePath,uploadfileName);
	     
	     /*이름 업데이트되어 변경:실행결과 변경된 점이 없으므로 유보시킴
	      * File originalFile = new File(filePath,originalfileName);
	     originalFile.renameTo(new File(filePath,uploadfileName));*/
			
			try {FileCopyUtils.copy(product_image.getInputStream(), new FileOutputStream(newFile));} 
			catch (IOException e) {e.printStackTrace();}	     
		
		System.out.println(productDTO);
		 
		//6.DB upload
		try {
		int done1 = productDAO.productUpload(productDTO);
		if(done1==0) {
			state="입점실패";
		}
		else {//즉시 입점시에는 추가로 DB엡데이트
			if(productDTO.getProduct_onstore().equals("YES")){
				int done2 = productDAO.inventoryUpload(productDTO);
				if(done2==1) state = "입점완료";
				else state="입점실패";					
			}
			else state="상품등록완료";
		}
		}catch(Exception e) {
			state="입점실패";
		}
		 }
		//7.결과값 송출
		ModelAndView mav = new ModelAndView();
		mav.addObject("stateCode",state);
		mav.setViewName("/admin/product/stateCode");
		return mav;
	}
	//상품 삭제
	@RequestMapping(value="/productDelete.do", method=RequestMethod.POST)
	public String productDelete(@RequestParam String product_name_no,Model model) {
		productDAO.productDelete(product_name_no);
		model.addAttribute("pg","1");
		model.addAttribute("display", "/admin/product/productManage.jsp");
		return "/main/home";
	}
}
