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
/*
 * BOARD_ADMIN,DB BOARD_QA와 BOARD_REVIEW를 조작하는 클래스
 */
@Repository
@DependsOn(value= {"sqlSession"})
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;

//-------------------COMMON:START-------------------//
	//1. 전체 글개수 가져오기
	@Override
	public int getTotalA(Map<String,String> map) {
		return sqlSession.selectOne("boardSQL.getTotalA",map);}
	
	//2. 특정 검색어로 찾은 글개수 가져오기
	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("boardSQL.getTotalSearchA", map);}
	
//-------------------COMMON:END-------------------//

//-------------------BOARD_ADMIN:START-------------------//
	
	//1. (관리자) 문의글 목록 호출하기
	@Override
	public int getAdminQATotalA() {
		return sqlSession.selectOne("boardSQL.getAdminQATotalA");}	
	
	//2. (관리자) 문의글 답변 작성하기
	@Override
	public void qaManageWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.qaManageWrite", map);}	
	
	//3. (사용자) 문의글 답변 호출하기
	@Override
	public AdminboardDTO getQaAns(String seq) {
		return sqlSession.selectOne("boardSQL.getQaAns", seq);}	
	
	//4. (관리자) 회원 문의글 목록 호출하기
	@Override
	public List<QAboardDTO> qaManageList(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.qaManageList", map);}	
	
//-------------------BOARD_ADMIN:END-------------------//		
	
//-------------------BOARD_QA:START-------------------//
	
	//1. 문의글 업로드
	@Override
	public void qaWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.qaWrite", map);}
	
	//2. 문의글 목록 호출
	@Override
	public List<QAboardDTO> qaList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
		return sqlSession.selectList("boardSQL.qaList", map);}

	//3.특정 검색어로 찾은 문의글 목록 호출
	@Override
	public List<QAboardDTO> qaSearch(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.qaSearch", map);}
	
	//4. 특정 문의글 호출
	@Override
	public QAboardDTO getQaBoard(String qa_seq) {
		return sqlSession.selectOne("boardSQL.getQaBoard", qa_seq);}
	
	//5. 문의글 수정하기
	@Override
	public void qaModify(Map<String, String> map) {
		sqlSession.update("boardSQL.qaModify", map);}
	
	//6. 문의글 삭제하기
	@Override
	public void qaDelete(int qa_seq) {
		sqlSession.delete("boardSQL.qaDelete", qa_seq);}	
	
//-------------------BOARD_QA:END-------------------//	

//-------------------BOARD_REVIEW:START-------------------//
	
	//1. 후기글 업로드
	@Override
	public void reviewWrite(Map<String, String> map) {
		sqlSession.insert("boardSQL.reviewWrite",map);}
	
	//2. 후기글 목록 호출하기
	@Override
	public List<ReviewboardDTO> reviewList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("startNum", startNum);
			map.put("endNum", endNum);		
			
		return sqlSession.selectList("boardSQL.reviewList", map);}
	
	//3. 특정 검색어로 찾은 후기글 목록 호출하기
	@Override
	public List<ReviewboardDTO> reviewSearch(Map<String, String> map) {
		return sqlSession.selectList("boardSQL.reviewSearch", map);}

	//4. 특정 후기글 호출하기
	@Override
	public ReviewboardDTO getReviewBoard(String review_seq) {
		return sqlSession.selectOne("boardSQL.getReviewBoard", review_seq);}

	//5. 후기글 조회수 업데이트
	@Override
	public int hitUpdate(int review_seq) {
		return sqlSession.update("boardSQL.hitUpdate",review_seq);}	
	
	//6. 후기글 수정하기
	@Override
	public int reviewModify(Map<String, String> map) {
		return sqlSession.insert("boardSQL.reviewModify",map);}	
	
	//7. 원글이 삭제되지 않은 경우 답글수 수정하기
	@Override
	public void reviewDelete1(int review_seq) {
		sqlSession.update("boardSQL.reviewDelete1", review_seq);}

	//8. 해당글에 대한 답변이 존재할 때 원글 삭제여부 업데이트
	@Override
	public void reviewDelete2(int review_seq) {
		sqlSession.update("boardSQL.reviewDelete2", review_seq);}	
	
	//9. 후기글 삭제하기	
	@Override
	public void reviewDelete3(int review_seq) {
		sqlSession.delete("boardSQL.reviewDelete3", review_seq);}
	
	//10. 특정 후기글 답변 업로드
	@Override
	public void reviewReply(ReviewboardDTO reviewboardDTO, Map<String, String> map) {
			sqlSession.update("boardSQL.reviewReply1",reviewboardDTO);
		
			map.put("productid", reviewboardDTO.getProductid());
			map.put("review_ref", reviewboardDTO.getReview_ref()+"");
			map.put("review_lev", reviewboardDTO.getReview_lev()+1+"");
			map.put("review_step", reviewboardDTO.getReview_step()+1+"");
			sqlSession.insert("boardSQL.reviewReply2", map);
			
			sqlSession.update("boardSQL.reviewReply3", reviewboardDTO.getReview_seq());}
	
//-------------------BOARD_REVIEW:END-------------------//

}
