package trading.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * DB COUPON_BOOK 관련 요소 관리 클래스
 */
@Component
@Data
public class CouponDTO {
	private String grant_id;
	private int coupon_no;
	private Date coupon_duedate;
	private String coupon_name;
	private int coupon_type;
	private int discount_mount;
	private Double discount_pecentage;
}
