package product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import product.bean.ProductDTO;

@Repository
@DependsOn(value= {"sqlSession"})
public class ProductDAOImpl implements ProductDAO {
	@Autowired
	private SqlSession sqlSession;
	
//-------------COMMON:START-------------//	
	
	//1. 부여할 시퀀스 번호 반환
	@Override
	public int getSeq() {
		return sqlSession.selectOne("productSQL.product_getSeq");
	}
	
	//2. 전체 목록 총글수 반환
	@Override
	public int getTotalA(Map<String, String> map) {
		return sqlSession.selectOne("productSQL.getTotalA",map);
	}
	
	//3. 검색어로 검색한 목록 총글수 반환
	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("productSQL.getTotalSearchA", map);
	}
	
	
//-------------COMMON:END-------------//	

//-------------재고:START-------------//	
	
	//1. 재고 목록 호출하기
	@Override
	public List<ProductDTO> inventoryList(int startNum, int endNum) {
		
		Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
		return sqlSession.selectList("productSQL.inventoryList",map);
	}

	//2. 검색어로 검색한 재고 목록 호출하기
	@Override
	public List<ProductDTO> inventorySearch(Map<String, String> map) {
		return sqlSession.selectList("productSQL.inventorySearch",map);
	}

	//3. 특정 재고 호출하기
	@Override
	public ProductDTO getInventoryInfo(String productID) {
		return sqlSession.selectOne("productSQL.getInventoryInfo",productID);
	}	

	//4. 재고 변경 반영하기
	@Override
	public void inventoryUpdate(Map<String, String> map) {
		sqlSession.update("productSQL.inventoryUpdate",map);
	}	

	//5. 재고 등록하기
	@Override
	public int inventoryUpload(ProductDTO productDTO) {
		return sqlSession.insert("productSQL.inventoryUpload", productDTO);	
	}	

	//6. 재고 수정하기(상품 정보와 연동한 수정)
	@Override
	public int inventoryModify(ProductDTO productDTO) {
		return sqlSession.update("productSQL.inventoryModify", productDTO);
	}

	//7. 재고 삭제하기(상품 정보와 연동한 삭제)
	@Override
	public int inventoryDelete(String product_name_no) {
		return sqlSession.delete("productSQL.inventoryDelete",product_name_no);
	}
	@Override
	public List<ProductDTO> getInventoryCatalog() {
		return sqlSession.selectList("productSQL.getInventoryCatalog");
	}
//-------------재고:END-------------//
	
//-------------상품(공통):START-------------//		

	//1. 상품 목록 호출하기(페이징O)
	@Override
	public List<ProductDTO> productList(int startNum, int endNum) {
		
		Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			
		return sqlSession.selectList("productSQL.productList",map);
	}
	
	//2. 검색어로 검색한 상품 목록 호출하기(페이징O)
	@Override
	public List<ProductDTO> productSearch(Map<String, String> map) {
		return sqlSession.selectList("productSQL.productSearch",map);
		}	
	
	
	//3. 특정 상품 정보 호출하기
	@Override
	public ProductDTO getProductInfo(String product_name_no) {
		return sqlSession.selectOne("productSQL.getProductInfo", product_name_no);
	}
	//4. 매상 추가하기
	@Override
	public void updateSalesProductInfo(ProductDTO dto) {
		sqlSession.update("productSQL.updateSalesProductInfo",dto);
	}
	
//-------------상품(공통):END-------------//		
	
//-------------상품(관리자):START-------------//	

	//1. 상품 업로드
	@Override
	public int productUpload(ProductDTO productDTO) {
		return sqlSession.insert("productSQL.productUpload", productDTO);
	}	
	
	//2. 상품 변경
	@Override
	public int productModify(ProductDTO productDTO) {
		return sqlSession.update("productSQL.productModify", productDTO);
	}
	
	//3. 상품(및 재고) 삭제
	@Override
	public void productDelete(String product_name_no) {
		sqlSession.delete("productSQL.productDelete",product_name_no);
	}	
	
//-------------상품(관리자):END-------------//	

//-------------상품(사용자):START-------------//	

	//1. 상품 카탈로그별 검색어별 목록 호출(페이징X)
	@Override
	public List<ProductDTO> getUserProductList(String product_category_name,String order,String searchOption, String searchWord) {
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("product_category_name", product_category_name);
			
		if(order.equals("name")) {
			map.put("order1", "PRODUCT.PRODUCTNAME");
			map.put("order2", "asc");
		}else if(order.equals("highPrice")) {
			map.put("order1", "PRODUCT.UNITCOST");
			map.put("order2", "desc");
		}else if(order.equals("lowPrice")) {
			map.put("order1", "PRODUCT.UNITCOST");
			map.put("order2", "asc");
		}
		else {//new and default
			map.put("order1", "PRODUCT.PRODUCT_REGISTERDATE");
			map.put("order2", "desc");			
		}		
			map.put("searchOption", searchOption);		
			map.put("searchWord", searchWord);
			
		return sqlSession.selectList("productSQL.getUserProductList",map);
	}
	
	//2. 특정 상품 조회수 업데이트
	@Override
	public void product_hitUpdate(int product_name_no) {
		sqlSession.update("productSQL.product_hitUpdate", product_name_no);
	}		
	
	//3. 특정 상품 좋아요 여부 조회
	@Override
	public int getLikeValue(Map<String, String> map) {
		try {
			return sqlSession.selectOne("productSQL.getLikeValue", map);		
		}catch(NullPointerException e) {
			return 0;
		}
	}
	
	//4. 좋아요 하기
	@Override
	public int addLike(Map<String, String> map) {
		 	sqlSession.insert("productSQL.addLike", map);
			try {
				return sqlSession.selectOne("productSQL.getLikeValue", map);		
			}catch(NullPointerException e) {
				return 0;}
	}	
	
	//5. 좋아요 취소 하기
	@Override
	public void removeLike(int SEQ) {
		sqlSession.delete("productSQL.removeLike", SEQ);
	}	
		
//-------------상품(사용자):END-------------//	

}
