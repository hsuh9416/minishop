package admin.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String admin_account;
	@JsonFormat(pattern="yyyy년MM월dd일")
	private Date admin_opendate;
	
}
