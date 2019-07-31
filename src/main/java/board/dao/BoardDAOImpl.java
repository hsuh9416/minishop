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
		return sqlSession.selectOne("boardSQL.getTotalA",map);}
	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("boardSQL.getTotalSearchA", map);}

//BOARD_QA 관련
	@Override
	public void qaWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.qaWrite", map);}
	@Override
	public List<QAboardDTO> qaList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("boardSQL.qaList", map);}

	@Override
	public QAboardDTO getQaBoard(String qa_seq) {
		return sqlSession.selectOne("boardSQL.getQaBoard", qa_seq);}
	@Override
	public List<QAboardDTO> qaSearch(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.qaSearch", map);}
	@Override
	public void qaModify(Map<String, String> map) {
		sqlSession.update("boardSQL.qaModify", map);}
	@Override
	public void qaManageWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.qaManageWrite", map);}

	@Override
	public void qaDelete(int qa_seq) {
		sqlSession.delete("boardSQL.qaDelete", qa_seq);}
	@Override
	public AdminboardDTO getQaAns(String seq) {
		return sqlSession.selectOne("boardSQL.getQaAns", seq);}
	
//BOARD_REVIEW 관련
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
	public ReviewboardDTO getReviewBoard(String review_seq) {
		return sqlSession.selectOne("boardSQL.getReviewBoard", review_seq);}

	@Override
	public void reviewReply(ReviewboardDTO reviewboardDTO, Map<String, String> map) {
		sqlSession.update("boardSQL.reviewReply1",reviewboardDTO);
		//System.out.println("done1");
		map.put("productid", reviewboardDTO.getProductid());
		map.put("review_ref", reviewboardDTO.getReview_ref()+"");
		map.put("review_lev", reviewboardDTO.getReview_lev()+1+"");
		map.put("review_step", reviewboardDTO.getReview_step()+1+"");
		sqlSession.insert("boardSQL.reviewReply2", map);
		//System.out.println("done2");
		sqlSession.update("boardSQL.reviewReply3", reviewboardDTO.getReview_seq());
		//System.out.println("done3");
		}
	@Override
	public int reviewModify(Map<String, String> map) {
		return sqlSession.insert("boardSQL.reviewModify",map);}
	@Override
	public List<QAboardDTO> qaManageList(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.qaManageList", map);}
	@Override
	public int getAdminQATotalA() {
		return sqlSession.selectOne("boardSQL.getAdminQATotalA");}
	@Override
	public int hitUpdate(int review_seq) {
		return sqlSession.update("boardSQL.hitUpdate",review_seq);}
	@Override
	public void reviewDelete(int review_seq) {
		sqlSession.delete("boardSQL.reviewDelete", review_seq);}



}
