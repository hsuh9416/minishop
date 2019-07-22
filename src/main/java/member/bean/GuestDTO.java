package member.bean;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GuestDTO {
	private String guest_id;//order_no
	private String guest_pwd;//order_pwd
	private String guest_name;//order_name
	private String guest_address;//order_address
	private String guest_tel;//order_tel
}
