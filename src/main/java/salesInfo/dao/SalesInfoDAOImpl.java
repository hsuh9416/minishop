package salesInfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import salesInfo.bean.SalesInfoDTO;
/*
 * STATE_OF_SALES 제어 관련 관리 클래스
 */
@Repository
@DependsOn(value= {"sqlSession"})
public class SalesInfoDAOImpl implements SalesInfoDAO {
	@Autowired
	private SqlSession sqlSession;

	//1. 기간별 매출 정보 목록 호출하기
	@Override
	public List<SalesInfoDTO> getSalesInfoList() {
		return sqlSession.selectList("salesInfoSQL.getSalesInfoList");
	}
	
	//2. 시퀀스 넘버 가져오기
	@Override
	public int getSalesSeq() {
		return sqlSession.selectOne("salesInfoSQL.getSalesSeq");
	}
	
	//3. 매상정보 업로드
	@Override
	public int uploadSalesInfo(SalesInfoDTO salesInfoDTO) {
		return sqlSession.insert("salesInfoSQL.uploadSalesInfo", salesInfoDTO);
	}
	//4. 매상정보 삭제(단일)
	
	//5. 매상정보 삭제(복수)
	
}
