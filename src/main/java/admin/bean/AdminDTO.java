package admin.bean;

import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * 관리자 페이지에 권한을 가진 admin과 ADMIN DB에 대한 정보를 관리하는 클래스
 */
@Component
@Data
public class AdminDTO {
	private String admin_id;
	private String admin_pwd;
	private String admin_email_addr;
	private String admin_email_pwd;
	private String admin_shop_tel;
}
