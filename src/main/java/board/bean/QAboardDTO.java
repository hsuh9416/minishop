package board.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Component
@Data
public class QAboardDTO {
	private int qa_seq;
	private String productid;//=productId(nullable)
	private String user_id;//작성자의 id
	private String qa_pwd;//작성자가 설정한 비밀번호
	private String qa_subject;
	private String qa_content;
	private int qa_state;//0. 공개글  1. 비밀글  2.FNQ
	private int qa_reply;//0. 답변없음 1.답변있음
	@JsonFormat(pattern="yyyy.MM.dd")
	private Date qa_logtime;
	
	private String name;//회원 이름
}











