package board.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * DB BOARD_REVIEW에 대한 정보를 관리하는 클래스
 */
@Component
@Data
public class ReviewboardDTO {
	//1. DB 구성요소(열 순서)
	private int review_seq;
	private String productid;
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
	
	//2.Member과 JOIN해서 별도로 불러 들이는 요소
	private String name;
}











