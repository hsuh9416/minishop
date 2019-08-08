package board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import board.bean.BoardPaging;
import board.bean.ReviewboardDTO;
import board.dao.BoardDAO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
import trading.bean.OrderDTO;
/*
 * 사용자: 후기 게시판 관련 제어를 하는 클래스
 */
@Controller
@RequestMapping(value="/board/review/**")
public class ReviewboardController {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private BoardPaging boardPaging;
	
	//1. 후기 글쓰기 화면 이동
	@RequestMapping(value="/reviewWriteForm.do",method = RequestMethod.GET)
	public ModelAndView reviewWriteForm(@RequestParam(required=false) String productID) {
		
		ModelAndView mav = new ModelAndView();
		if(productID!=null) mav.addObject("productID", productID);		
			mav.addObject("display", "/board/review/reviewWriteForm.jsp");		
			mav.setViewName("/main/home");
			
		return mav;
	}

	//2. 후기글 본문용 이미지 업로드(CKEDITOR4)
	@RequestMapping(value="/reviewImgUpload.do")
	@ResponseBody
	public Map<String,Object> reviewImgUpload(HttpServletRequest request,@RequestParam MultipartFile upload)
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
		Map<String,Object> data = new HashMap<String,Object>();
		
	    		//(1) 이미지 양식 검증		
			fileName = upload.getOriginalFilename();	
	      	post = fileName.lastIndexOf(".");
	      	ext = fileName.substring(post + 1).toLowerCase();
	       	images = new String[]{"jpg", "jpeg", "png", "gif", "bmp"};
	       	result = false;
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
				//(2) 서버 업로드
				uploadPath = request.getSession().getServletContext().getRealPath("/")+"\\storage\\review\\"+fileName;
				upload.transferTo(new File(uploadPath));
				fileUrl = request.getContextPath()+"\\storage\\review\\"+fileName;
				data.put("fileName", uploadPath);
				data.put("uploaded", 1);
				data.put("url", fileUrl);		
				//(3) 미리보기 화면용 업로드
				Localpath="D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\review\\";
			try {
				FileCopyUtils.copy(upload.getInputStream(), new FileOutputStream(new File(Localpath,fileName)));} 
			catch (IOException e) {e.printStackTrace();}}
	        catch(IOException e) {e.printStackTrace();}
	        
		return data;
	}

	//3. 후기 글쓰기 반영하기
	@RequestMapping(value="/reviewWrite.do",method = RequestMethod.POST)
	@ResponseBody
	public void boardWrite(@RequestParam Map<String,String> map, HttpSession session) {
		
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			map.put("user_id", memberDTO.getId());	
		
		boardDAO.reviewWrite(map);
	}
	
	//4. 후기 글목록 화면 이동
	@RequestMapping(value="/reviewList.do",method= RequestMethod.GET)
	public ModelAndView boardList(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "reviewList");
			mav.addObject("display", "/board/review/reviewList.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//5. 후기 글목록 가져오기(한 페이지당 3게시물,1블록 표시)
	@RequestMapping(value="/getReviewList.do",method= RequestMethod.POST)
	public ModelAndView getReviewList(@RequestParam(required=false,defaultValue="1") String pg){
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		int totalA;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "BOARD_REVIEW");
			totalA = boardDAO.getTotalA(map);
		
		List<ReviewboardDTO> reviewList = boardDAO.reviewList(startNum, endNum);
	
			boardPaging.setCurrentPage(page);
			boardPaging.setPageBlock(1);
			boardPaging.setPageSize(3);
			boardPaging.setTotalA(totalA);
			boardPaging.makePagingHTML();
			
		ModelAndView mav = new ModelAndView();
			mav.addObject("boardPaging", boardPaging);
			mav.addObject("reviewList", reviewList);
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//6. 특정 검색어로 검색한 후기 글목록 가져오기(한 페이지당 3게시물,1블록 표시)
	@RequestMapping(value="/reviewSearch.do",method= RequestMethod.POST)
	public ModelAndView reviewSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		int totalA;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption", searchOption);
			map.put("keyword", keyword);
			map.put("tableName", "BOARD_REVIEW");
			totalA = boardDAO.getTotalSearchA(map);	
		
			boardPaging.setCurrentPage(page);
			boardPaging.setPageBlock(1);
			boardPaging.setPageSize(3);
			boardPaging.setTotalA(totalA);
			boardPaging.makeSearchPagingHTML();		
			
		List<ReviewboardDTO> reviewSearchList = boardDAO.reviewSearch(map);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("reviewSearchList", reviewSearchList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("boardPaging", boardPaging);
			mav.setViewName("jsonView");
			
		return mav;	
	}
		
	//7. 특정 후기글 조회 화면 가져오기 
	@RequestMapping(value="/reviewView.do",method=RequestMethod.GET)
	public ModelAndView reviewView(@RequestParam(required=false,defaultValue="1") String pg,String review_seq, HttpSession Session,HttpServletRequest request,HttpServletResponse response) {
		
		boolean today;
		String id="";
		String content;
		
		Cookie[] ar;
		GuestDTO guestDTO=null;
		MemberDTO memberDTO =null; 
		OrderDTO orderDTO=null; 
		
			memberDTO = (MemberDTO)Session.getAttribute("memberDTO");
			if(memberDTO!=null) id=memberDTO.getId();
			else if(memberDTO==null) {
				orderDTO = (OrderDTO) Session.getAttribute("memberDTO");
				if(orderDTO!=null)	id = orderDTO.getOrder_id();
				else if(orderDTO==null) {
					guestDTO = (GuestDTO) Session.getAttribute("guestDTO");
					if(guestDTO!=null) id = guestDTO.getGuest_id();}}
			
			today = false;
			ar = request.getCookies();
			
			if(ar!=null&&(memberDTO!=null|| orderDTO !=null|| guestDTO!=null)) {
				for(int i=0; i<ar.length; i++) {
					if((ar[i].getName()).equals(id+review_seq)) {
						today = true;}}
				
				if(!today) {
						boardDAO.hitUpdate(Integer.parseInt(review_seq));
						
					Cookie cookie = new Cookie(id+review_seq, review_seq+"");
						cookie.setMaxAge(30*60);
						response.addCookie(cookie);}}
		
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(review_seq);
			content = StringEscapeUtils.unescapeHtml3(reviewboardDTO.getReview_content());
			reviewboardDTO.setReview_content(content);

		ModelAndView mav = new ModelAndView();		
			mav.addObject("pg", pg);
			mav.addObject("reviewboardDTO", reviewboardDTO);
			mav.addObject("location","reviewView");
			mav.addObject("display", "/board/review/reviewView.jsp");
			mav.setViewName("/main/home");
			
		return mav;	
	}
	
	//8. 후기 글수정  화면 이동
	@RequestMapping(value="/reviewModifyForm.do",method= RequestMethod.GET)
	public ModelAndView reviewModifyForm(@RequestParam String review_seq,String pg) {
		
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(review_seq);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("reviewboardDTO", reviewboardDTO);
			mav.addObject("display", "/board/review/reviewModifyForm.jsp");
			mav.setViewName("/main/home");
			
	return mav;	
	}
	
	//9. 후기 글수정 반영하기
	@RequestMapping(value="/reviewModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void reviewModify(@RequestParam Map<String,String> map, Model model) {	
		
		boardDAO.reviewModify(map);
	}

	//10.후기 글삭제 반영하기 
	@RequestMapping(value="/reviewDelete.do",method= RequestMethod.GET)
	public ModelAndView reviewDelete(@RequestParam int review_seq) {	
		
		boardDAO.reviewDelete(review_seq);
		
		ModelAndView mav = new ModelAndView();
			mav.setViewName("/board/review/reviewDeleted");
			
		return mav;
	}
	
	//11. 후기 답글 작성 화면 이동
	@RequestMapping(value="/reviewReplyForm.do",method= RequestMethod.GET)
	public ModelAndView boardReplyForm(@RequestParam String review_pseq,String productid,String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("review_pseq", review_pseq);
			mav.addObject("productid", productid);		
			mav.addObject("pg", pg);
			mav.addObject("display", "/board/review/reviewReplyForm.jsp");
			mav.setViewName("/main/home");
			
		return mav;	
	}	
	
	//12. 후기 답글 작성 반영하기
	@RequestMapping(value="/reviewReply.do",method= RequestMethod.POST)
	@ResponseBody
	public void boardReply(@RequestParam Map<String,String> map,HttpSession session) {

		MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
		
		String review_seq = map.get("review_pseq");
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(review_seq);
			map.put("user_id",memberDTO.getId());	
			
		boardDAO.reviewReply(reviewboardDTO,map);
	}	
	
}
