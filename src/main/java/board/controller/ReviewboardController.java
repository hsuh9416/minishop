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

@Controller
@RequestMapping(value="/board/review/**")
public class ReviewboardController {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private BoardPaging boardPaging;
	
	//글쓰기 서식 이동
	@RequestMapping(value="/reviewWriteForm.do",method = RequestMethod.GET)
	public ModelAndView reviewWriteForm() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/board/review/reviewWriteForm.jsp");		
		mav.setViewName("/main/home");
		return mav;
	}

	//글쓰기 이미지 업로드
	@RequestMapping(value="/reviewImgUpload.do")
	@ResponseBody
	public Map<String,Object> reviewImgUpload(HttpServletRequest request,@RequestParam MultipartFile upload)
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
			uploadPath = request.getSession().getServletContext().getRealPath("/")+"\\storage\\review\\"+fileName;//저장경로
			//System.out.println(uploadPath);
			upload.transferTo(new File(uploadPath));
			fileUrl = request.getContextPath()+"\\storage\\review\\"+fileName;//url경로
			//System.out.println(fileUrl);
			data.put("fileName", uploadPath);
			data.put("uploaded", 1);
			data.put("url", fileUrl);		
			//DB저장용 storage upload
			String Localpath="D:\\lib\\workspace\\minishop\\src\\main\\webapp\\storage\\review\\";
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


	
	//글쓰기 반영
	@RequestMapping(value="/reviewWrite.do",method = RequestMethod.POST)
	@ResponseBody
	public void boardWrite(@RequestParam Map<String,String> map, HttpSession session) {
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		map.put("user_id", memberDTO.getId());	
		
		boardDAO.reviewWrite(map);
	}
	
	//글목록 이동
	@RequestMapping(value="/reviewList.do",method= RequestMethod.GET)
	public ModelAndView boardList(@RequestParam(required=false,defaultValue="1") String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("location", "reviewList");
		mav.addObject("display", "/board/review/reviewList.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	//글목록 가져오기(3게시물,1블록 표시)
	@RequestMapping(value="/getReviewList.do",method= RequestMethod.POST)
	public ModelAndView getReviewList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "BOARD_REVIEW");
		int totalA = boardDAO.getTotalA(map);
		
		List<ReviewboardDTO> reviewList = boardDAO.reviewList(startNum, endNum);
	
		//페이징 처리
		boardPaging.setCurrentPage(page);
		boardPaging.setPageBlock(1);
		boardPaging.setPageSize(3);
		boardPaging.setTotalA(totalA);
		boardPaging.makePagingHTML();;	
		//System.out.println(reviewList);
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardPaging", boardPaging);
		mav.addObject("reviewList", reviewList);
		mav.setViewName("jsonView");
		return mav;
	}
	//검색어 제한 글목록
	@RequestMapping(value="/reviewSearch.do",method= RequestMethod.POST)
	public ModelAndView reviewSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("tableName", "BOARD_REVIEW");		
		List<ReviewboardDTO> reviewSearchList = boardDAO.reviewSearch(map);
		
		ModelAndView mav = new ModelAndView();
		int totalA = boardDAO.getTotalSearchA(map);
		//페이징 처리
		boardPaging.setCurrentPage(page);
		boardPaging.setPageBlock(1);
		boardPaging.setPageSize(3);
		boardPaging.setTotalA(totalA);
		boardPaging.makeSearchPagingHTML();		
		
		mav.addObject("pg", pg);
		mav.addObject("reviewSearchList", reviewSearchList);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);
		mav.addObject("boardPaging", boardPaging);
		mav.setViewName("jsonView");
		return mav;	
	}
		
	//글보기
	@RequestMapping(value="/reviewView.do",method=RequestMethod.GET)
	public ModelAndView reviewView(@RequestParam(required=false,defaultValue="1") String pg,String review_seq, HttpSession Session,HttpServletRequest request,HttpServletResponse response) {
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
				if((ar[i].getName()).equals(id+review_seq)) {
					today = true;}
			}//for
			
			if(!today) {
				boardDAO.hitUpdate(Integer.parseInt(review_seq));
				Cookie cookie = new Cookie(id+review_seq, review_seq+"");
				//System.out.println(cookie.getName());
				cookie.setMaxAge(30*60);
				response.addCookie(cookie);
			}//if not today
		}//hit update
		
		
		ModelAndView mav = new ModelAndView();
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(review_seq);
		//원래 글자로 복귀 시킴
		String content = StringEscapeUtils.unescapeHtml3(reviewboardDTO.getReview_content());
		reviewboardDTO.setReview_content(content);
		//System.out.println(reviewboardDTO.getReview_content());
		mav.addObject("pg", pg);
		mav.addObject("reviewboardDTO", reviewboardDTO);
		mav.addObject("location","reviewView");
		mav.addObject("display", "/board/review/reviewView.jsp");
		mav.setViewName("/main/home");
		return mav;	
	}
	
	//글수정 이동
	@RequestMapping(value="/reviewModifyForm.do",method= RequestMethod.GET)
	public ModelAndView reviewModifyForm(@RequestParam String review_seq,String pg) {
		ModelAndView mav = new ModelAndView();
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(review_seq);
		mav.addObject("pg", pg);
		mav.addObject("reviewboardDTO", reviewboardDTO);
		mav.addObject("display", "/board/review/reviewModifyForm.jsp");
		mav.setViewName("/main/home");
	return mav;	
	}
	
	//글수정 DB
	@RequestMapping(value="/reviewModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void reviewModify(@RequestParam Map<String,String> map, Model model) {		
		boardDAO.reviewModify(map);

	}

	//글삭제 
	@RequestMapping(value="/reviewDelete.do",method= RequestMethod.GET)
	public ModelAndView reviewDelete(@RequestParam int review_seq) {		
		ModelAndView mav = new ModelAndView();
		boardDAO.reviewDelete(review_seq);
		mav.setViewName("/board/review/reviewDeleted");
		return mav;
	}
	
	//답글 화면
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
	
	//답글 달기
	@RequestMapping(value="/reviewReply.do",method= RequestMethod.POST)
	@ResponseBody
	public void boardReply(@RequestParam Map<String,String> map,HttpSession session) {
		//기존 Map 정보:review_pseq,review_pwd,review_subject,review_content
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		String review_seq = map.get("review_pseq");
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(review_seq);
		map.put("user_id",memberDTO.getId());	
		boardDAO.reviewReply(reviewboardDTO,map);
	}	
}
