package trading.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class OrderDTO {
	//table ORDERS 관련
 private int order_no;//비회원 로그인시의 아이디에 해당
 private String order_id;
 private int order_total;
 private int state;//0.입금대기 1.배송대기 2.발송완료 3.수령대기 4.수령완료 5.환불대기 6.환불완료
 private Date order_date;
 private String order_address;
 private String order_email;
 private String order_tel;
 private String order_pwd;
 private String order_name;
 
 //table ordered_product 관련
 private int order_product_no;
 private String order_productid;
 private String order_product_qty;
 private String order_product_total;
 	//table PAYMENT 관련
 private int payment_method; //0.현금결제  1.현금결제 2.카드결제 3.기타(포인트전부결제 등으로 인해 지불금액이 없는 등의 사유)
 private Date payment_date;
 
 
}
