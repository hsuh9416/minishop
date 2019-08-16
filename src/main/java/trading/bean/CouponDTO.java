package trading.bean;

import java.text.NumberFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * DB COUPON_BOOK 관련 요소 관리 클래스
 */
@Component
@Data
public class CouponDTO {
	private int coupon_no;//쿠폰번호
	private String coupon_name;//쿠폰이름
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date coupon_duedate;//유효일자(유효일자가 null이면 무기한으로 간주함)
	private String grant_id;//받은 대상(회원만 조회가능)
	private String personal_code;//식별코드
	private int coupon_target;//0.전부지급  1. 특정지급

	private int coupon_type;//쿠폰 적용 종류 0.정액할인 1.정률할인 2.배송비무료
	private int discount_mount;
	private int coupon_amount;//최종 산출되는 총 할인액
	
	//사용 즉시 쿠폰 번호와 식별코드만 남긴 채 삭제됨.
	private StringBuffer couponBookHTML;
	
	public void callCouponBook() {
		couponBookHTML = new StringBuffer();
		NumberFormat nf = NumberFormat.getCurrencyInstance();

		couponBookHTML.append("<div class='form-group col-1'>"+coupon_no+"</div>");
		couponBookHTML.append("<div class='form-group col-3'>"+coupon_name+"</div>");	
		if(coupon_type==0) {
			couponBookHTML.append("<div class='form-group col-2'>정액할인</div>");	
			couponBookHTML.append("<div class='form-group col-3'>"+nf.format(discount_mount)+"</div>");			
		}
		else if(coupon_type==1) {
			couponBookHTML.append("<div class='form-group col-2'>정률할인</div>");
			couponBookHTML.append("<div class='form-group col-3'>"+discount_mount+"%</div>");	
		}
		else {
			couponBookHTML.append("<div class='form-group col-2'>배송비면제</div>");
			couponBookHTML.append("<div class='form-group col-3'>배송비상당액</div>");	
		}
		if(coupon_target==0) {
			couponBookHTML.append("<div class='form-group col-3'>전체</div>");			
		}
		else {
			couponBookHTML.append("<div class='form-group col-3'>개인</div>");		
		}

	}
}
