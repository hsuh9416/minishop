package product.dao;

import java.util.List;
import java.util.Map;

import product.bean.ProductDTO;

public interface ProductDAO {
	//공통
	int getSeq();
	
	//관리자-정보 가져오기
	List<ProductDTO> inventoryList(int startNum, int endNum);

	int getTotalA(Map<String, String> map);

	List<ProductDTO> inventorySearch(Map<String, String> map);

	int getTotalSearchA(Map<String, String> map);

	ProductDTO getProductInfo(String productID);
	//관리자-상품 관련

	List<ProductDTO> productList(int startNum, int endNum);

	List<ProductDTO> productSearch(Map<String, String> map);

	ProductDTO getProduct_NameInfo(String product_name_no);

	//사용자-상품 관련
	List<ProductDTO> getAllproduct();

	List<ProductDTO> getUserProductList(String product_category_name,String order,String searchWord);

	void product_hitUpdate(int product_name_no);	
	
	//상품 생성,수정,제거
	int productUpload(ProductDTO productDTO);
	
	int inventoryUpload(ProductDTO productDTO);
	
	int productModify(ProductDTO productDTO);
	
	int inventoryModify(ProductDTO productDTO);
	
	void doModify(Map<String, String> map);
	
	void productDelete(String product_name_no);

	int getLikeValue(Map<String, String> map);

	int addLike(Map<String, String> map);
	
	void removeLike(int SEQ);







}
