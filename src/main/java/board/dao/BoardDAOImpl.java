package board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import admin.bean.AdminboardDTO;
import board.bean.QAboardDTO;
import board.bean.ReviewboardDTO;

@Repository
@DependsOn(value= {"sqlSession"})
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;

	//공용
	@Override
	public int getTotalA(Map<String,String> map) {
		return sqlSession.selectOne("boardSQL.getTotalA",map);
	}

	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("boardSQL.getTotalSearchA", map);
	}

	@Override
	public int hitUpdate(int seq) {
		return sqlSession.update("boardSQL.hitUpdate",seq);
	}

	@Override
	public void boardDelete(String seq) {
		sqlSession.delete("boardSQL.boardDelete",Integer.parseInt(seq));
		
	}

//BOARD_QA 관련
	@Override
	public void qaWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.qaWrite", map);
		
	}

	@Override
	public List<QAboardDTO> qaList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("boardSQL.qaList", map);
	}

	@Override
	public QAboardDTO getQaBoard(String qa_seq) {
		return sqlSession.selectOne("boardSQL.getQaBoard", qa_seq);
	}

	@Override
	public List<QAboardDTO> qaSearch(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.qaSearch", map);}

	@Override
	public void qaModify(Map<String, String> map) {
		sqlSession.update("boardSQL.qaModify", map);
	}

	@Override
	public AdminboardDTO getQaAns(String seq) {
		return sqlSession.selectOne("boardSQL.getQaAns", seq);
	}
	
	//review
	@Override
	public void reviewWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.reviewWrite",map);
	}

	@Override
	public List<ReviewboardDTO> reviewList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);		
		return sqlSession.selectList("boardSQL.reviewList", map);
	}

	@Override
	public List<ReviewboardDTO> reviewSearch(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.reviewSearch", map);}

	@Override
	public ReviewboardDTO getReviewBoard(String seq) {
		return sqlSession.selectOne("boardSQL.getReviewBoard", seq);}

	@Override
	public void reviewReply(ReviewboardDTO reviewBoardDTO, Map<String, String> resource) {
		sqlSession.update("boardSQL.boardReply1",reviewBoardDTO);
		
		resource.put("ref", reviewBoardDTO.getReview_ref()+"");
		resource.put("lev", reviewBoardDTO.getReview_lev()+1+"");
		resource.put("step", reviewBoardDTO.getReview_step()+1+"");
		sqlSession.insert("boardSQL.boardReply2", resource);
		
		sqlSession.update("boardSQL.boardReply3", reviewBoardDTO.getReview_seq());	
	}

	@Override
	public void reviewModify(Map<String, String> modifymap) {
		sqlSession.insert("boardSQL.reviewModify",modifymap);
	}

	@Override
	public List<QAboardDTO> qaManageList(int startNum, Map<String, String> map) {
		return sqlSession.selectList("boardSQL.qaManageList", map);
	}

	@Override
	public int getAdminQATotalA() {
		return sqlSession.selectOne("boardSQL.getAdminQATotalA");
	}

	@Override
	public void qaManageWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.qaManageWrite", map);
	}




}
