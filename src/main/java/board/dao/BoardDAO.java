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
	
	//qa
	void qaWrite(Map<String, String> map);	
	
	List<QAboardDTO> qaList(int startNum, int endNum);

	List<QAboardDTO> qaSearch(Map<String, String> map);	
	
	QAboardDTO getQaBoard(String qa_seq);
	
	AdminboardDTO getQaAns(String seq);
	
	void qaModify(Map<String, String> map);

	List<QAboardDTO> qaManageList(Map<String, String> map);

	int getAdminQATotalA();

	void qaManageWrite(Map<String, String> map);	

	void qaDelete(int qa_seq);

	
	//review
	void reviewWrite(Map<String, String> map);
	
	List<ReviewboardDTO> reviewList(int startNum, int endNum);

	List<ReviewboardDTO> reviewSearch(Map<String, String> map);

	ReviewboardDTO getReviewBoard(String review_seq);

	void reviewReply(ReviewboardDTO reviewboardDTO, Map<String, String> map);

	int reviewModify(Map<String, String> map);

	int hitUpdate(int review_seq);

	void reviewDelete(int review_seq);























}
