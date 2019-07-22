package trading.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CouponDTO {
	private String grant_id;
	private int coupon_no;
	private Date coupon_duedate;//유효기간
	private String coupon_name;
	private int coupon_type;//0.단위할인 1.비율할인 2.배송료무료
	private int discount_mount;//단위할인시
	private Double discount_pecentage;//비율할인시
}
