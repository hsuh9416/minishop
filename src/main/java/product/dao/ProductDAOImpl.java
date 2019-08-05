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
	
	@Override
	public int getSeq() {
		return sqlSession.selectOne("productSQL.product_getSeq");
	}
	
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
	//상품 관리 관련
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

	//상세 상품 정보 DTO
	@Override
	public ProductDTO getProduct_NameInfo(String product_name_no) {
		return sqlSession.selectOne("productSQL.getProduct_NameInfo", product_name_no);
	}

	//리뷰 등의 상품 목록
	@Override
	public List<ProductDTO> getAllproduct() {
		return sqlSession.selectList("productSQL.getAllproduct");
	}
	@Override
	public List<ProductDTO> getUserProductList(String product_category_name,String order,String searchWord) {
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
		map.put("searchWord", searchWord);
		return sqlSession.selectList("productSQL.getUserProductList",map);
	}
	@Override
	public void product_hitUpdate(int product_name_no) {
		sqlSession.update("productSQL.product_hitUpdate", product_name_no);
	}	
	
	//상품 업로드
	@Override
	public int productUpload(ProductDTO productDTO) {
		return sqlSession.insert("productSQL.product_nameUpload", productDTO);

	}
	//입점재고 업로드

	@Override
	public int inventoryUpload(ProductDTO productDTO) {
		return sqlSession.insert("productSQL.productUpload", productDTO);	
	}
	//상품 변경
	@Override
	public int productModify(ProductDTO productDTO) {
		return sqlSession.update("productSQL.product_nameModify", productDTO);
	}
	@Override
	public int inventoryModify(ProductDTO productDTO) {
		return sqlSession.update("productSQL.productModify", productDTO);
	}
	//삭제
	@Override
	public void productDelete(String product_name_no) {
		ProductDTO inventory = sqlSession.selectOne("productSQL.getProduct_NameInfo",product_name_no);
		if(inventory!=null) {sqlSession.delete("productSQL.inventoryDelete", product_name_no);}
		sqlSession.delete("productSQL.productDelete", product_name_no);
	}







	

}
