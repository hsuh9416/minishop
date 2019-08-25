package trading.bean;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import product.bean.ProductDTO;
/*
 * 주문관련 DB 요소 관리 클래스
 */
@Component
@Data
public class OrderDTO {
	//ORDERS DB: 주문서, 비회원 정보
 private int order_no;
 private String order_id;
 private int order_total;
 private int order_state;
 @JsonFormat(pattern="yyyy-MM-dd")
 private Date order_date;
 private String order_address;
 private String order_email;
 private String order_tel;
 private String order_pwd;
 private String order_name;
 private String order_receiver;
 private String orderlist_json;	
 private String order_deliverynum;
 private String order_refundaccount;
 private String order_statement;
 @JsonFormat(pattern="yyyy-MM-dd")
 private Date order_logtime;
 private int order_deliveryfee;
 
 //주문서 상세 내역
 private List<ProductDTO> orderList;
 
//PAYMENT : 특정 주문서 관련 결제 방법/결제일
 private int payment_method;//1.카드 2.무통장결제 3.기타 4.포인트 5.쿠폰
 @JsonFormat(pattern="yyyy-MM-dd")
 private Date payment_date;
 private int payment_amount;
 private String payment_state;
 
}
