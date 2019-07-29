package board.controller;

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

import board.bean.BoardPaging;
import board.bean.ReviewboardDTO;
import board.dao.BoardDAO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;

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
		
	//글쓰기 반영
	@RequestMapping(value="/reviewWrite.do",method = RequestMethod.POST)
	@ResponseBody
	public void boardWrite(@RequestParam Map<String,String> map, HttpSession session) {
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		if(memberDTO==null) {
			GuestDTO guestDTO = (GuestDTO) session.getAttribute("guestDTO");
			map.put("user_id", guestDTO.getGuest_id());	
		}
		else {
			map.put("user_id", memberDTO.getId());	
		}
		
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
		boardPaging.setPageBlock(3);
		boardPaging.setPageSize(5);
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
	public ModelAndView reviewView(@RequestParam(required=false,defaultValue="1") String pg,String seq, HttpSession Session,HttpServletRequest request,HttpServletResponse response) {
		MemberDTO memberDTO = (MemberDTO)Session.getAttribute("memberDTO");
		boolean today = false;
		Cookie[] ar = request.getCookies();
		if(ar!=null) {
			for(int i=0; i<ar.length; i++) {
				if((ar[i].getName()).equals(memberDTO.getId()+seq)) {
					today = true;}
			}//for
			
			if(!today) {
				boardDAO.hitUpdate(Integer.parseInt(seq));
				Cookie cookie = new Cookie(memberDTO.getId()+seq, seq+"");
				//System.out.println(cookie.getName());
				cookie.setMaxAge(30*60);
				response.addCookie(cookie);
			}//if not today
		}//hit update
		
		
		ModelAndView mav = new ModelAndView();
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(seq);
		mav.addObject("pg", pg);
		mav.addObject("reviewboardDTO", reviewboardDTO);
		mav.addObject("display", "/board/boardView.jsp");
		mav.setViewName("/main/home");
		return mav;	
	}
	
	//글수정 이동
	@RequestMapping(value="/reviewModifyForm.do",method= RequestMethod.POST)
	public ModelAndView reviewModifyForm(@RequestParam String seq,String pg) {
		ModelAndView mav = new ModelAndView();
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(seq);
		mav.addObject("pg", pg);
		mav.addObject("reviewboardDTO", reviewboardDTO);
		mav.addObject("display", "/board/boardModifyForm.jsp");
		mav.setViewName("/main/home");
	return mav;	
	}
	
	//글수정 DB
	@RequestMapping(value="/reviewModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void reviewModify(@RequestParam Map<String,String> map, Model model) {
		Map<String,String> modifymap = new HashMap<String,String>();
		modifymap.put("pg",map.get("pg"));
		modifymap.put("seq",map.get("seq"));
		modifymap.put("subject",map.get("subject"));
		modifymap.put("content",map.get("content"));		
		boardDAO.reviewModify(modifymap);
	}
	
	//글삭제 
	@RequestMapping(value="/reviewDelete.do",method= RequestMethod.POST)
	public ModelAndView reviewDelete(@RequestParam String seq) {		
		ModelAndView mav = new ModelAndView();
		boardDAO.boardDelete(seq);
		mav.addObject("display", "/board/review/reviewList.jsp");
		mav.addObject("pg", 1);
		mav.setViewName("/main/home");
		return mav;
	}
	
	//답글 화면
	@RequestMapping(value="/reviewReplyForm.do",method= RequestMethod.POST)
	public ModelAndView boardReplyForm(@RequestParam String pseq,String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pseq", pseq);
		mav.addObject("pg", pg);
		mav.addObject("display", "/board/reveiew/reviewReplyForm.jsp");
		mav.setViewName("/main/home");
	return mav;	
	}	
	
	//답글 달기
	@RequestMapping(value="/reviewReply.do",method= RequestMethod.POST)
	@ResponseBody
	public void boardReply(@RequestParam Map<String,String> map,HttpSession session) {

		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		String name = memberDTO.getName();
		String id = memberDTO.getId();
		String email = memberDTO.getEmail1()+"@"+memberDTO.getEmail2();
		ReviewboardDTO reviewboardDTO = boardDAO.getReviewBoard(map.get("pseq"));
		
		Map<String,String> resource = new HashMap<String,String>();
		resource.put("id",id);
		resource.put("name",name);
		resource.put("email",email);
		resource.put("subject",map.get("subject"));
		resource.put("content",map.get("content"));
		resource.put("pseq",map.get("pseq"));
		boardDAO.reviewReply(reviewboardDTO,resource);
	}	
}
