package trading.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
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
 private Date order_date;
 private String order_address;
 private String order_email;
 private String order_tel;
 private String order_pwd;
 private String order_name;
 
 //ORDERED_PRODUCT : 특정 주문서 상세 내역
 private int order_product_no;
 private String order_productid;
 private String order_product_qty;
 private String order_product_total;
 
//PAYMENT : 특정 주문서 관련 결제 방법/결제일
 private int payment_method;
 private Date payment_date;
 
}
