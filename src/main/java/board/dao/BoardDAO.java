package board.dao;

import java.util.List;
import java.util.Map;

import admin.bean.AdminboardDTO;
import board.bean.QAboardDTO;
import board.bean.ReviewboardDTO;
/*
 * BoardDaoImpl의 인터페이스
 */
public interface BoardDAO {
	
//--------COMMON:START--------//
	int getTotalA(Map<String, String> map);	
	int getTotalSearchA(Map<String, String> map);	
//--------COMMON:END--------//	

//--------BOARD_QA:START--------//	
	int getAdminQATotalA();		
	void qaManageWrite(Map<String, String> map);	
	AdminboardDTO getQaAns(String seq);	
	List<QAboardDTO> qaManageList(Map<String, String> map);	
//--------BOARD_QA:END--------//
	
//--------BOARD_QA:START--------//
	void qaWrite(Map<String, String> map);		
	List<QAboardDTO> qaList(int startNum, int endNum);
	List<QAboardDTO> qaSearch(Map<String, String> map);		
	QAboardDTO getQaBoard(String qa_seq);	
	void qaModify(Map<String, String> map);
	void qaDelete(int qa_seq);		
//--------BOARD_QA:END--------//
	
//--------BOARD_REVIEW:START--------//
	void reviewWrite(Map<String, String> map);	
	List<ReviewboardDTO> reviewList(int startNum, int endNum);
	List<ReviewboardDTO> reviewSearch(Map<String, String> map);
	ReviewboardDTO getReviewBoard(String review_seq);
	int hitUpdate(int review_seq);
	int reviewModify(Map<String, String> map);
	void reviewDelete1(int review_seq);
	void reviewDelete2(int review_seq);
	void reviewDelete3(int review_seq);	
	void reviewReply(ReviewboardDTO reviewboardDTO, Map<String, String> map);		
//--------BOARD_REVIEW:END--------//
























}
