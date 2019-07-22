package salesInfo.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import product.bean.ProductDTO;


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
}
