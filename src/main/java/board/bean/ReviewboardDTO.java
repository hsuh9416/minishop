package board.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Component
@Data
public class ReviewboardDTO {
	private int review_seq;
	private String productid;//=productID
	private String user_id;
	private String review_pwd;
	private String review_subject;
	private String review_content;
	private int review_ref;
	private int review_lev;
	private int review_step;
	private int review_pseq;
	private int review_reply;
	private int review_hit;
	@JsonFormat(pattern="yyyy.MM.dd")
	private Date review_logtime;
	
	private String name;//회원 이름
}











