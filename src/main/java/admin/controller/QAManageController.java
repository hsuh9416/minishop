package admin.controller;

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

import board.bean.BoardPaging;
import board.bean.QAboardDTO;
import board.dao.BoardDAO;
/*
 * 관리자: 문의글 관리를 제어하는 클래스
 */
@Controller
@RequestMapping(value="/admin/board/**")
public class QAManageController {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private BoardPaging boardPaging;
	
	//1. 문의글 관리로 이동
	@RequestMapping(value="/qaManage.do",method= RequestMethod.GET)
	public ModelAndView qaList(@RequestParam(required=false,defaultValue="1") String pg) {
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg",pg);
			mav.addObject("location", "adminQAList");
			mav.addObject("display", "/admin/board/qaManage.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}
	
	//2. 글목록 가져오기(한페이지당 3게시물,1블록 표시)
	@RequestMapping(value="/getQaList.do",method= RequestMethod.GET)
	@ResponseBody
	public ModelAndView getQaList(@RequestParam(required=false,defaultValue="1") String pg){
		
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		int totalA;
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("startNum", startNum+"");
			map.put("endNum", endNum+"");		
			totalA = boardDAO.getAdminQATotalA();
			
		List<QAboardDTO> qalist = boardDAO.qaManageList(map);
	
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
	
	//3. 특정 문의글 조회
	@RequestMapping(value="/qaManageView.do",method=RequestMethod.GET)
	public ModelAndView qaManageView(@RequestParam(required=false,defaultValue="1") String pg,String qa_seq, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		QAboardDTO qaBoardDTO = boardDAO.getQaBoard(qa_seq);
		
		ModelAndView mav = new ModelAndView();
			mav.addObject("pg", pg);
			mav.addObject("location", "adminQA");		
			mav.addObject("qaBoardDTO", qaBoardDTO);
			mav.addObject("display", "/admin/board/qaManageView.jsp");
			mav.setViewName("/main/home");
			
		return mav;
	}	
	
	//4. 관리자 답변 입력하기
	@RequestMapping(value="/qaManageWrite.do",method=RequestMethod.POST)
	@ResponseBody
	public void qaManageWrite(@RequestParam Map<String,String> map) {
		
		boardDAO.qaManageWrite(map);
	}
	
	
	
}
