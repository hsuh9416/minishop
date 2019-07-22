package member.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * Bean Info Class to store member and administer DTO
 * */
@Component
@Data
public class MemberDTO{
	private int seq;//while register/order will be given to user randomly
	private int state;// 0.Administer 1.Regular(default) 2.Special 3.invalidate
	private String name;
	private String id;
	private String pwd;// Guest default : tel3
	private String email1;//address part
	private String email2;//domain part
	private String tel1;//010 
	private String tel2;//front
	private String tel3;//back
	private String zipcode;
	private String addr1;
	private String addr2;
	private int point;//member point given
	@JsonFormat(pattern="yyyy.MM.dd")
	private Date registerdate;//date 
	private String sessionId;
	private Date sessionLimit;//date 

}
