package board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import admin.bean.AdminboardDTO;
import board.bean.BoardPaging;
import board.bean.QAboardDTO;
import board.dao.BoardDAO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;

/*
 * 사용자: 문의 게시판 관련 제어를 하는 클래스
 */
@Controller
@RequestMapping(value="/board/qa/**")
public class QAboardController {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private BoardPaging boardPaging;
	
	//1. 문의 글쓰기 화면 이동
	@RequestMapping(value="/qaWriteForm.do",method = RequestMethod.GET)
	public ModelAndView qaWriteForm(@RequestParam(required=false,defaultValue="") String productID) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("productID", productID);	
			mav.addObject("location","board");
			mav.addObject("display", "/board/qa/qaWriteForm.jsp");		
			mav.setViewName("/main/home");
			
		return mav;
	}
		
	//2. 문의 글쓰기 업로드하기
	@RequestMapping(value="/qaWrite.do",method = RequestMethod.POST)
	@ResponseBody
	public void qaWrite(@RequestParam Map<String,String> map, HttpSession session) {
		
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		if(memberDTO==null) {
			GuestDTO guestDTO = (GuestDTO) session.getAttribute("guestDTO");
				map.put("user_id", guestDTO.getGuest_id());}
		else {map.put("user_id", memberDTO.getId());}
		
			boardDAO.qaWrite(map);
	}
	
	//3. 문의글 목록 화면 이동
	@RequestMapping(value="/qaList.do",method= RequestMethod.GET)
	public ModelAndView qaList(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location","board");
			mav.addObject("display", "/board/qa/qaList.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//4. 문의 글목록 가져오기(한페이지당 3게시물,1블록 표시)
	@RequestMapping(value="/getQaList.do",method= RequestMethod.POST)
	public ModelAndView getQaList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		int totalA;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "BOARD_QA");
			totalA = boardDAO.getTotalA(map);
		
		List<QAboardDTO> qalist = boardDAO.qaList(startNum, endNum);
	
			boardPaging.setCurrentPage(page);
			boardPaging.setPageBlock(1);
			boardPaging.setPageSize(3);
			boardPaging.setTotalA(totalA);
			boardPaging.makePagingHTML();;	
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("boardPaging", boardPaging);
			mav.addObject("qalist", qalist);
			mav.setViewName("jsonView");
			
		return mav;
	}
	
	//5. 특정 검색어로 검색된 문의 글목록 가져오기(한페이지당 3게시물,1블록 표시)
	@RequestMapping(value="/qaSearch.do",method= RequestMethod.POST)
	public ModelAndView qaSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		int totalA;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");
			map.put("searchOption", searchOption);
			map.put("keyword", keyword);
			map.put("tableName", "BOARD_QA");
			totalA = boardDAO.getTotalSearchA(map);	
			
			boardPaging.setCurrentPage(page);
			boardPaging.setPageBlock(1);
			boardPaging.setPageSize(3);
			boardPaging.setTotalA(totalA);
			boardPaging.makeSearchPagingHTML();	
		
		List<QAboardDTO> qaSearchList = boardDAO.qaSearch(map);		
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("qalist", qaSearchList);
			mav.addObject("searchOption", searchOption);
			mav.addObject("keyword", keyword);
			mav.addObject("boardPaging", boardPaging);
			mav.setViewName("jsonView");
			
		return mav;	
	}
		
	//6. 특정 문의글 조회 화면 이동
	@RequestMapping(value="/qaView.do",method=RequestMethod.GET)
	public ModelAndView boardView(@RequestParam(required=false,defaultValue="1") String pg,String qa_seq, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		QAboardDTO qaBoardDTO = boardDAO.getQaBoard(qa_seq);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("location","board");
			mav.addObject("qaBoardDTO", qaBoardDTO);
			mav.addObject("display", "/board/qa/qaView.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//7. 특정 문의글에 대한 관리자 답변 가져오기
	@RequestMapping(value="/getQaAns.do",method= RequestMethod.POST)
	public ModelAndView qaSearch(@RequestParam String seq) {
		
		AdminboardDTO adminboardDTO = boardDAO.getQaAns(seq);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("adminboardDTO", adminboardDTO);
			mav.setViewName("jsonView");
			
		return mav;	
	}	
	
	//8. 특정 문의글 수정 화면 이동
	@RequestMapping(value="/qaModifyForm.do",method= RequestMethod.GET)
	public ModelAndView qaModifyForm(@RequestParam String qa_seq,String pg) {
		
		QAboardDTO qaBoardDTO = boardDAO.getQaBoard(qa_seq);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("location","board");
			mav.addObject("qaBoardDTO", qaBoardDTO);
			mav.addObject("display", "/board/qa/qaModifyForm.jsp");
			mav.setViewName("/main/home");
		
	return mav;	
	}
	
	//9. 문의글 수정 반영하기
	@RequestMapping(value="/qaModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void qaModify(@RequestParam Map<String,String> map) {
		
		boardDAO.qaModify(map);
	}
	
	//10. 문의글 삭제하기 
	@RequestMapping(value="/qaDelete.do",method= RequestMethod.GET)
	public ModelAndView qaDelete(@RequestParam int qa_seq) {	
		boardDAO.qaDelete(qa_seq);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("item", "qa");
			mav.setViewName("/common/deleted");
			
		return mav;
	}
	
}

