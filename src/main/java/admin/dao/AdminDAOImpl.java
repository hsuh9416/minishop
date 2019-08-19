package admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import admin.bean.AdminDTO;
import mail.bean.MessageDTO;
/*
 * DB ADMIN를 조작하는 클래스 
 */
@Repository
@DependsOn(value= {"sqlSession"})
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	private SqlSession sqlSession;
	
	//1.관리자 계정 정보 불러오기
	@Override
	public AdminDTO getAdmin() {
		
		return sqlSession.selectOne("adminSQL.getAdmin");
		
	}
	//2. 1:1문의 가져오기
	@Override
	public List<MessageDTO> getPersonalQAList() {
		return sqlSession.selectList("adminSQL.getPersonalQAList");
	}
	
	//3. 개별 1:1뷰 가져오기
	@Override
	public MessageDTO getPersonalQA(String seq) {
		return sqlSession.selectOne("adminSQL.getPersonalQA",seq);
	}
	//3. 1:1문의 삭제하기
	@Override
	public int deleteQA(String seq) {
		return sqlSession.delete("adminSQL.deleteQA",seq);
	}

}
