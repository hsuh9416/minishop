package salesInfo.bean;

import java.util.Comparator;

import lombok.Data;
import member.bean.MemberDTO;
import product.bean.ProductDTO;

@Data
public class SortCollection {

	
	Comparator<ProductDTO> salesSort = new Comparator<ProductDTO>() {
		@Override
		public int compare(ProductDTO x, ProductDTO y) {
			int totalX = x.getCart_qty()*x.getUnitcost(); int totalY = y.getCart_qty()*y.getUnitcost();
			if(totalX!=0||totalY!=0) {
				if(totalX>totalY) return -1;
				else if(totalX<totalY) return 1;
				else {
					if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
					else return 1;
				}		
			}
			else {
				if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
				else return 1;
			}		
		}
	};
	
	Comparator<ProductDTO> qtySort = new Comparator<ProductDTO>() {

		@Override
		public int compare(ProductDTO x, ProductDTO y) {
			int qtyX = x.getCart_qty(); int qtyY = y.getCart_qty();
			if(qtyX!=0||qtyY!=0) {
				if(qtyX>qtyY) return -1;
				else if(qtyX<qtyY) return 1;
				else {
					if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
					else return 1;
				}		
			}
			else {
				if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
				else return 1;
			}		
		}
		
	};

	Comparator<ProductDTO> likeitSort = new Comparator<ProductDTO>() {

		@Override
		public int compare(ProductDTO x, ProductDTO y) {
			int likeitX = x.getCart_qty(); int likeitY = y.getCart_qty();
			if(likeitX!=0||likeitY!=0) {
				if(likeitX>likeitY) return -1;
				else if(likeitX<likeitY) return 1;
				else {
					if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
					else return 1;
				}		
			}
			else {
				if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
				else return 1;
			}		
		}
		
	};

	Comparator<ProductDTO> hitSort = new Comparator<ProductDTO>() {

		@Override
		public int compare(ProductDTO x, ProductDTO y) {
			int hitX = x.getProduct_hit(); int hitY = y.getProduct_hit();
			if(hitX!=0||hitY!=0) {
				if(hitX>hitY) return -1;
				else if(hitX<hitY) return 1;
				else {
					if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
					else return 1;
				}		
			}
			else {
				if(x.getProduct_name_no()>y.getProduct_name_no()) return -1;
				else return 1;
			}		
		}
		
	};
	Comparator<MemberDTO> orderNumSort = new Comparator<MemberDTO>() {

		@Override
		public int compare(MemberDTO x, MemberDTO y) {
			int orderNumX = x.getOrderNum(); int orderNumY = y.getOrderNum();
			if(orderNumX!=0||orderNumY!=0) {
				if(orderNumX>orderNumY) return -1;
				else if(orderNumX<orderNumY) return 1;
				else {
					return x.getId().compareTo(y.getId());
				}		
			}
			else {
				return x.getId().compareTo(y.getId());
			}		
		}
	
	};

	Comparator<MemberDTO> orderTotalSort = new Comparator<MemberDTO>() {

		@Override
		public int compare(MemberDTO x, MemberDTO y) {
			int orderTotalX = x.getOrderTotal(); int orderTotalY = y.getOrderTotal();
			if(orderTotalX!=0||orderTotalY!=0) {
				if(orderTotalX>orderTotalY) return -1;
				else if(orderTotalX<orderTotalY) return 1;
				else {
					return x.getId().compareTo(y.getId());
				}		
			}
			else {
				return x.getId().compareTo(y.getId());
			}		
		}
	
	};
	
}
