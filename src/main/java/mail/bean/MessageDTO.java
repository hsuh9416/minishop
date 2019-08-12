package mail.bean;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * 메일 전송용 요소를 관리하는 클래스
 */
@Component
@Data
public class MessageDTO {
 private String type;
 private String sender;
 private String sendAddr;
 private String receiver; 
 private String receiveAddr;
 private String subject;
 private String content;
 @JsonFormat(pattern="yyyy.MM.dd")
 private Date sendDate;
 
 private String code;
 
 private File mailData;
}
