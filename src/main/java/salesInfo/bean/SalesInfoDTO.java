package salesInfo.bean;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import product.bean.ProductDTO;
import trading.bean.OrderDTO;
/*
 * SALES_INFO 관련 정보 관리 클래스
 */
@Component
@Data
public class SalesInfoDTO {
//SALES_INFO 관련 요소들
private int sales_seq;
private String order_no;//주문서
private String order_id;//주문자(비회원일 경우 guest로 통일됨)
private int sales_revenue;//총매출액
private String sales_payment_json;//매출액 지급 정보
private List<OrderDTO> sales_payment_Info;//해당 매출서의 지급 구성 리스트
private List<ProductDTO> sales_product_Info;//해당 매출서의 판매 목록 리스트
@JsonFormat(pattern="yyyy.MM.dd")
private Date sales_date;//매출일자(확인일자)

//chart 계산용 요소들
private int cash_total;
private int card_total;
private int point_total;
private int coupon_total;
private int etc_total;

private int unknown_total;
private int women_total;
private int men_total;
private int accessories_total;

}
