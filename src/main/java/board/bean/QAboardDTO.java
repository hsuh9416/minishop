package board.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * DB BOARD_QA에 대한 정보를 관리하는 클래스
 */
@Component
@Data
public class QAboardDTO {
	//1. DB 구성요소(열 순서)
	private int qa_seq;
	private String productid;
	private String user_id;
	private String qa_pwd;
	private String qa_subject;
	private String qa_content;
	private int qa_state;
	private int qa_reply;
	@JsonFormat(pattern="yyyy.MM.dd")
	private Date qa_logtime;
	
	//2.Member과 JOIN해서 별도로 불러 들이는 요소
	private String name;
}











