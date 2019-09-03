package product.dao;

import java.util.List;
import java.util.Map;

import product.bean.ProductDTO;
/*
 * ProductDAOImpl의 인터페이스
 */
public interface ProductDAO {
//----------COMMON:START----------//
	int getSeq();
	int getTotalA(Map<String, String> map);	
	int getTotalSearchA(Map<String, String> map);	
//----------COMMON:END----------//	
//----------재고:START----------//
	List<ProductDTO> inventoryList(int startNum, int endNum);
	List<ProductDTO> inventorySearch(Map<String, String> map);
	ProductDTO getInventoryInfo(String productID);	
	void inventoryUpdate(Map<String, String> map);
	int inventoryUpload(ProductDTO productDTO);	
	int inventoryModify(ProductDTO productDTO);	
	int inventoryDelete(String product_name_no);
	List<ProductDTO> getInventoryCatalog();
//----------재고:END----------//	
//----------상품(공통):START----------//
	List<ProductDTO> productList(int startNum, int endNum);
	List<ProductDTO> productSearch(Map<String, String> map);	
	ProductDTO getProductInfo(String product_name_no);	
	void updateSalesProductInfo(ProductDTO dto);
//----------상품(공통):END----------//		
//----------상품(관리자):START----------//	
	int productUpload(ProductDTO productDTO);
	int productModify(ProductDTO productDTO);
	void productDelete(String product_name_no);
//----------상품(관리자):END----------//	
//----------상품(사용자):START----------//	
	List<ProductDTO> getUserProductList(String product_category_name,String order,String searchOption,String searchWord);	
	void product_hitUpdate(int product_name_no);		
	int getLikeValue(Map<String, String> map);	
	int addLike(Map<String, String> map);	
	void removeLike(int SEQ);	
//----------상품(사용자):END----------//	



}
