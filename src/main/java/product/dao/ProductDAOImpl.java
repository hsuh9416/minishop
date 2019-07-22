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
	//재고 목록 가져오기
	@Override
	public List<ProductDTO> inventoryList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("productSQL.inventoryList",map);
	}

	@Override
	public int getTotalA(Map<String, String> map) {
		return sqlSession.selectOne("productSQL.getTotalA",map);
	}

	@Override
	public List<ProductDTO> inventorySearch(Map<String, String> map) {
		return sqlSession.selectList("productSQL.inventorySearch",map);
	}

	@Override
	public int getTotalSearchA(Map<String, String> map) {
		return sqlSession.selectOne("productSQL.getTotalSearchA", map);
	}
	//재고 및 판매단가 변경용 DTO
	@Override
	public ProductDTO getProductInfo(String productID) {
		return sqlSession.selectOne("productSQL.getProductInfo",productID);
	}	

	@Override
	public void doModify(Map<String, String> map) {
		sqlSession.update("productSQL.doModify",map);
	}	
	//상품 관련
	@Override
	public List<ProductDTO> productList(int startNum, int endNum) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("productSQL.productList",map);
	}

	@Override
	public List<ProductDTO> productSearch(Map<String, String> map) {
		return sqlSession.selectList("productSQL.productSearch",map);
		}


	@Override
	public void productDelete(String product_name_no) {
		sqlSession.delete("productSQL.productDelete", product_name_no);
		
	}
	//상세 상품 정보 DTO
	@Override
	public ProductDTO getProduct_NameInfo(String product_name_no) {
		return sqlSession.selectOne("productSQL.getProduct_NameInfo", product_name_no);
	}
	//사용자 관련
	//상품정보 가져오기 : product_name

	@Override
	public int getTotalItemA(Map<String, String> map) {
		return sqlSession.selectOne("productSQL.getTotalItemA",map);
	}

	@Override
	public int getSelectedItemA(Map<String, String> map) {
		return sqlSession.selectOne("productSQL.getSelectedItemA", map);
	}
	//
	
	//카테고리용 상품
	@Override
	public List<ProductDTO> getProductList(Map<String, String> map) {
		if(map.get("product_category_no")!="1"||map.get("product_category_no")!="2"||map.get("product_category_no")!="3") 
			return sqlSession.selectList("productSQL.getAllList", map);
		
		else return sqlSession.selectList("productSQL.getSelectedList", map);
	}

	@Override
	public List<ProductDTO> getAllproduct() {
		return sqlSession.selectList("productSQL.getProductList");
	}

	//상품 업로드
	@Override
	public void productUpload(ProductDTO productDTO) {
		sqlSession.insert("productSQL.product_nameUpload", productDTO);
	if(productDTO.getProduct_onstore().equals("YES")) {//바로 입점시 재고도 업데이트
		sqlSession.insert("productSQL.productUpload", productDTO);}		
	}




	

}
