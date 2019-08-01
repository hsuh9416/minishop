package product.dao;

import java.util.List;
import java.util.Map;

import product.bean.ProductDTO;

public interface ProductDAO {
	//공통
	int getSeq();
	//관리자-재고 관련
	List<ProductDTO> inventoryList(int startNum, int endNum);

	int getTotalA(Map<String, String> map);

	List<ProductDTO> inventorySearch(Map<String, String> map);

	int getTotalSearchA(Map<String, String> map);

	ProductDTO getProductInfo(String productID);
	//관리자-상품 관련

	List<ProductDTO> productList(int startNum, int endNum);

	List<ProductDTO> productSearch(Map<String, String> map);

	void productDelete(String product_name_no);

	void doModify(Map<String, String> map);

	ProductDTO getProduct_NameInfo(String product_name_no);

	//사용자-상품 관련
	int getTotalItemA(Map<String, String> map);

	int getSelectedItemA(Map<String, String> map);

	List<ProductDTO> getProductList(Map<String, String> map);

	List<ProductDTO> getAllproduct();

	int productUpload(ProductDTO productDTO);
	
	int inventoryUpload(ProductDTO productDTO);
}
