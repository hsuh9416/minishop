package salesInfo.dao;

import java.util.List;

import salesInfo.bean.SalesInfoDTO;
/*
 * SalesInfoDAOImpl의 인터페이스
 */
public interface SalesInfoDAO {

	List<SalesInfoDTO> getSalesInfoList();
}
