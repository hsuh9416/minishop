package admin.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import admin.bean.AdminDTO;
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

}
