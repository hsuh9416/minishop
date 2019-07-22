package admin.bean;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AdminboardDTO {
	private int admin_seq;
	private int admin_pseq;//답변 대상 번호
	private String user_id;//답변 대상의 유저 아이디
	private String admin_content;//내용
}
