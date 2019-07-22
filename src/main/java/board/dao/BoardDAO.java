package board.dao;

import java.util.List;
import java.util.Map;

import admin.bean.AdminboardDTO;
import board.bean.QAboardDTO;
import board.bean.ReviewboardDTO;

public interface BoardDAO {
	//공용
	int getTotalA(Map<String, String> map);	
	
	int getTotalSearchA(Map<String, String> map);	
	
	void boardDelete(String seq);	
	
	//qa
	void qaWrite(Map<String, String> map);	
	
	List<QAboardDTO> qaList(int startNum, int endNum);

	List<QAboardDTO> qaSearch(Map<String, String> map);	
	
	QAboardDTO getQaBoard(String qa_seq);
	
	AdminboardDTO getQaAns(String seq);
	
	void qaModify(Map<String, String> modifymap);

	
	

	
	//review
	void reviewWrite(Map<String, String> map);
	
	List<ReviewboardDTO> reviewList(int startNum, int endNum);

	List<ReviewboardDTO> reviewSearch(Map<String, String> map);

	ReviewboardDTO getReviewBoard(String seq);

	void reviewReply(ReviewboardDTO boardDTO, Map<String, String> resource);

	void reviewModify(Map<String, String> modifymap);

	int hitUpdate(int seq);

	List<QAboardDTO> qaManageList(int startNum, Map<String, String> map);

	int getAdminQATotalA();

	void qaManageWrite(Map<String, String> map);



















}
