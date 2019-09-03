package salesInfo.dao;

import java.util.List;
import java.util.Map;

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
	public List<SalesInfoDTO> getChartRawData(Map<String, String> map) {
		return sqlSession.selectList("salesInfoSQL.getChartRawData",map);
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

	@Override
	public int getTotalA() {
		return sqlSession.selectOne("salesInfoSQL.getTotalA");
	}

	@Override
	public List<SalesInfoDTO> getsalesInfoList(Map<String, String> map) {
		return sqlSession.selectList("salesInfoSQL.getsalesInfoList", map);
	}

	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("salesInfoSQL.getTotalSearchA",map);
	}

	@Override
	public List<SalesInfoDTO> salesInfoSearch(Map<String, String> map) {
		return sqlSession.selectList("salesInfoSQL.salesInfoSearch", map);
	}

}
