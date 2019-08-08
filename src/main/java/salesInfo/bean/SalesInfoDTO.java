package salesInfo.bean;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import product.bean.ProductDTO;
/*
 * STATE_OF_SALES를 관련 정보 관리 클래스
 */
@Component
@Data
public class SalesInfoDTO {
private int seq;
private String salesId;//orderDate+sequence Number
private int salesRevenue;
private List<ProductDTO> cartdetail;
private String salesdetail;//can be stored as html or else
private String specialOrder;//user comment
@JsonFormat(pattern="yyyy.MM.dd")
private Date salesDate;
private String salesPwd;//주문번호 조회를 위한 비밀번호

//STATE_OF_SALES 관련 요소들
private int year;
private int month;
private int week;
private String product_category;
private int number_of_orders;
private int periodical_revenue;

}
