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




@Controller
@RequestMapping(value="/board/qa/**")
public class QAboardController {
	@Autowired
	private BoardDAO boardDAO;

	//글쓰기 서식 이동
	@RequestMapping(value="/qaWriteForm.do",method = RequestMethod.GET)
	public ModelAndView qaWriteForm() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("display", "/board/qa/qaWriteForm.jsp");		
		mav.setViewName("/main/home");
		return mav;
	}
		
	//글쓰기 반영
	@RequestMapping(value="/qaWrite.do",method = RequestMethod.POST)
	@ResponseBody
	public void qaWrite(@RequestParam Map<String,String> map, HttpSession session) {
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
		if(memberDTO==null) {
			GuestDTO guestDTO = (GuestDTO) session.getAttribute("guestDTO");
			map.put("user_id", guestDTO.getGuest_id());	
		}
		else {
			map.put("user_id", memberDTO.getId());	
		}
		
		boardDAO.qaWrite(map);
	}
	
	//글목록 이동
	@RequestMapping(value="/qaList.do",method= RequestMethod.GET)
	public ModelAndView qaList(@RequestParam(required=false,defaultValue="1") String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("display", "/board/qa/qaList.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	//글목록 가져오기(3게시물,1블록 표시)
	@RequestMapping(value="/getQaList.do",method= RequestMethod.POST)
	public ModelAndView getQaList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "BOARD_QA");
		int totalA = boardDAO.getTotalA(map);
		
		List<QAboardDTO> qalist = boardDAO.qaList(startNum, endNum);
	
		//페이징 처리
		BoardPaging boardPaging = new BoardPaging();
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
	//검색어 제한 글목록
	@RequestMapping(value="/qaSearch.do",method= RequestMethod.POST)
	public ModelAndView qaSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("tableName", "BOARD_QA");
		List<QAboardDTO> qaSearchList = boardDAO.qaSearch(map);
		
		ModelAndView mav = new ModelAndView();
		int totalA = boardDAO.getTotalSearchA(map);		
		//페이징 처리
		BoardPaging boardPaging = new BoardPaging();		
		boardPaging.setCurrentPage(page);
		boardPaging.setPageBlock(1);
		boardPaging.setPageSize(3);
		boardPaging.setTotalA(totalA);
		boardPaging.makeSearchPagingHTML();		
		mav.addObject("pg", pg);
		mav.addObject("qaSearchList", qaSearchList);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);
		mav.addObject("boardPaging", boardPaging);
		mav.setViewName("jsonView");
		return mav;	
	}
		
	//글보기
	@RequestMapping(value="/qaView.do",method=RequestMethod.GET)
	public ModelAndView boardView(@RequestParam(required=false,defaultValue="1") String pg,String qa_seq, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView();
		QAboardDTO qaBoardDTO = boardDAO.getQaBoard(qa_seq);
		mav.addObject("pg", pg);
		mav.addObject("qaBoardDTO", qaBoardDTO);
		mav.addObject("display", "/board/qa/qaView.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	//관리자 답변 가져오기
	@RequestMapping(value="/getQaAns.do",method= RequestMethod.POST)
	public ModelAndView qaSearch(@RequestParam String seq) {
		AdminboardDTO adminboardDTO = boardDAO.getQaAns(seq);
		ModelAndView mav = new ModelAndView();
		mav.addObject("adminboardDTO", adminboardDTO);
		//System.out.println(adminboardDTO);
		mav.setViewName("jsonView");
		return mav;	
	}	
	
	//글수정 이동
	@RequestMapping(value="/qaModifyForm.do",method= RequestMethod.GET)
	public ModelAndView qaModifyForm(@RequestParam String qa_seq,String pg) {
		ModelAndView mav = new ModelAndView();
		QAboardDTO qaBoardDTO = boardDAO.getQaBoard(qa_seq);
		mav.addObject("pg", pg);
		mav.addObject("qaBoardDTO", qaBoardDTO);
		mav.addObject("display", "/board/qa/qaModifyForm.jsp");
		mav.setViewName("/main/home");
	return mav;	
	}
	
	//글수정 DB
	@RequestMapping(value="/qaModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void qaModify(@RequestParam Map<String,String> map) {
		boardDAO.qaModify(map);
	}
	
	//글삭제 
	@RequestMapping(value="/qaDelete.do",method= RequestMethod.POST)
	public ModelAndView boardDelete(@RequestParam String seq) {		
		ModelAndView mav = new ModelAndView();
		boardDAO.boardDelete(seq);
		mav.addObject("display", "/board/boardList.jsp");
		mav.addObject("pg", 1);
		mav.setViewName("/main/home");
		return mav;
	}
	
}

