package mail.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Component
@Data
public class MessageDTO {
 private String type;//메세지 종류
 private String sender;
 private String sendAddr;
 private String receiver; 
 private String receiveAddr;
 private String subject;
 private String content;
 @JsonFormat(pattern="yyyy.MM.dd")
 private Date sendDate;
}
