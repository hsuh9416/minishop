package salesInfo.dao;

import java.util.List;
import java.util.Map;

import salesInfo.bean.SalesInfoDTO;
/*
 * SalesInfoDAOImpl의 인터페이스
 */
public interface SalesInfoDAO {

	List<SalesInfoDTO> getChartRawData(Map<String, String> map);
	
	int getSalesSeq();
	
	int uploadSalesInfo(SalesInfoDTO salesInfoDTO);

	int getTotalA();

	List<SalesInfoDTO> getsalesInfoList(Map<String, String> map);

	int getTotalSearchA(Map<String, String> map);

	List<SalesInfoDTO> salesInfoSearch(Map<String, String> map);

}
