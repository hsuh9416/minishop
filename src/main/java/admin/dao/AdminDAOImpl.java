package admin.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import admin.bean.AdminDTO;

@Repository
@DependsOn(value= {"sqlSession"})
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public AdminDTO getAdmin() {
		return sqlSession.selectOne("adminSQL.getAdmin");
	}

}
