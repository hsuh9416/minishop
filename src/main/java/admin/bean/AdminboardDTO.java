package admin.bean;

import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * 문의 게시글에 대한 답변을 관리하는 BOARD_ADMIN DB의 구성요소를 관리하는 클래스
 */
@Component
@Data
public class AdminboardDTO {
	private int admin_seq;
	private int admin_pseq;
	private String user_id;
	private String admin_content;
}
