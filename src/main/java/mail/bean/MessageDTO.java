package mail.bean;

import java.io.File;
import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * 메일 전송용 요소를 관리하는 클래스
 */
@Component
@Data
public class MessageDTO {
 private int seq;
 private String sender;
 private String sendAddr;
 private String receiver;
 private String receiveAddr;
 private String subject;
 private String content;
 private Boolean containHTML=false;
 
 private String code;
 
 private File mailData;
}
