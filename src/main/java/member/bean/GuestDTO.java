package member.bean;

import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * 익명사용자 정보를 관리하는 클래스
 */
@Component
@Data
public class GuestDTO {
	private String guest_id;
	private String guest_pwd;
	private String guest_name;
	private String guest_email;
	private String guest_tel;
	private int order_no;
}
