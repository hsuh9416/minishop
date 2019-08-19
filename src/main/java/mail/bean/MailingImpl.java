package mail.bean;

import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import admin.bean.AdminDTO;
/*
 * 관리자가 사용자에게 전송하는 메일 관련 메소드를 관리하는 클래스
 */
@Component
public class MailingImpl implements Mailing {
	 
	@Override	
	  public String checkNum() {
		  String checkNum = "";
		  for(int i=0; i<5; i++) {
			  checkNum += Integer.toString((int)(Math.random()*10));//랜덤 숫자 생성후 인증번호에 저장	 
		  }
		  
		return checkNum;
	  }
	
	@Override
	public String getKey(int length) {
	        char pwCollection[] = new char[] { 
	                          '1','2','3','4','5','6','7','8','9','0', 
	                          'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z', 
	                          'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', 
	                          '!','@','#','$','%','^','&','*','(',')'};//배열에 선언 

	        String getKey = ""; 

	        for (int i = 0; i < length; i++) { 
	          int selectRandomPw = (int)(Math.random()*(pwCollection.length));
	          getKey += pwCollection[selectRandomPw]; 
	        }   
		return getKey;
	}	  
	
	@Override
	public MessageDTO sendBenefitGrantMail(MessageDTO messageDTO, String benefitInfo) {
		messageDTO.setSender("[Kissin' Bugs]");
		String benefitStatement = "<br><h5>"+messageDTO.getReceiver()+"고객님께 지급되는 혜택은 다음과 같습니다.</h5>"+
								"<hr style='width:80%;'>"+
								"<p><strong style='color:blue;'>"+benefitInfo+"</strong></p>"+
								"<hr style='width:80%;'>"+
								"<p>저희 Kissin' Bugs는 "+messageDTO.getReceiver()+" 님의 편의와 만족을 위하여 최선을 다하고 있습니다.</p>"+
								"<p>이번 혜택을 통해 "+messageDTO.getReceiver()+" 님의 더욱 설레는 하루가 되길 바라며 앞으로도 지속적인 관심 부탁드립니다.</p>"+
								"<p>&emsp; &emsp; &emsp;[Kissin' Bugs] 직원 일동 드림</p>";
		benefitStatement=messageDTO.getContent()+benefitStatement;
		messageDTO.setContent(benefitStatement);
		return messageDTO;
	}
	
	@Override
	public MessageDTO sendConfirmMail(MessageDTO messageDTO,String checkNum) {
		messageDTO.setSender("[Kissin' Bugs]");
		messageDTO.setSubject("[Kissin' Bugs]인증번호를 확인해주세요!");
		String confirmText = "안녕하세요, Kissin' Bugs에 관심을 가져주셔서 감사합니다.\n"+
							" 저희 사이트는 사내 규정에 따라 가입시와 이메일 변경시에 고객님께 이메일 인증을 요청하고 있습니다.\n"+
							"해당 메일 내에 있는 이메일 인증번호를 입력해주시기 바랍니다.\n"+
							"========================================\n"+
							"인증번호는 ["+checkNum+"]입니다.\n"+
							"========================================\n";		
		messageDTO.setContent(confirmText);
		
		return messageDTO;
	}

	@Override
	public MessageDTO sendWelcomeMail(MessageDTO messageDTO) {
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject("[Kissin' Bugs]회원 가입하신 것을 환영합니다.");
		String PersonalCode = UUID.randomUUID().toString(); 
		String welcomeText = "안녕하세요, Kissin' Bugs에 회원이 되신 것을 환영합니다.\n"+
				"현재 저희 샵에서는 신규가입 회원님께 [전품목 20% 할인 쿠폰]을 증정하는 이벤트를 진행하고 있습니다. \n"+
				"일부 상품에 대하여 할인이 적용되지 않는 경우가 있으므로 이 점 양해 부탁드립니다.\n"+
				"========================================\n"+
				"증정되는 쿠폰의 코드 넘버는 [9999]이면 PersonalCode는 ["+PersonalCode+"]입니다.\n"+
				"========================================\n"+			
				"신감각 편집샵 Kissin' Bugs와 함께 설레는 하루되시기 바랍니다.\n\n"+
				"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";		
		messageDTO.setCode(PersonalCode);
		messageDTO.setContent(welcomeText);		
		return messageDTO;
	}

	@Override
	public MessageDTO sendOrderMail(MessageDTO messageDTO) {
		// TODO Auto-generated method stub
		return messageDTO;
	}

	@Override
	public MessageDTO sendGoodbyeMail(MessageDTO messageDTO) {
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(messageDTO.getReceiver()+"의 회원 탈퇴 요청이 정상적으로 접수되었습니다.");
		String goodByeText = "회원님의 탈퇴 요청이 정상적으로 접수되었습니다.\n"+
				"탈퇴 요청을 한 시점부터 회원님의 계정은 비활성화 상태로 전환되오나, \n"+
				"일정한 경우에 복구가 가능하도록 14일간 회원님의 정보는 유지됩니다.\n"+
				"14일이  경과된 경우 기존의 회원님의 정보는 안전하게 삭제되며, 복구를 요청하실 수 없습니다.\n"+
				"기존에 주문 완료가 된 상품에 대해서는 주문번호로 접속하여 접근하실 수 있으며,\n"+
				"주문번호에 대한 비밀번호는 임시로 발급된 다음의 비밀번호를 공통적으로 적용합니다.\n"+					
				"========================================\n"+
				"회원님의 주문서에 대한 임시 비밀번호는 ["+messageDTO.getCode()+"]입니다.\n"+				
				"========================================\n"+
				"아직 정산되지 않은 주문완료 상품의 번호 목록을 첨부하였으므로 확인 바랍니다.\n"+	
				"주문목록을 보관하고 계시되, 해당 목록을 유실하시거나 주문번호를 잊어 버리신 경우에\n"+					
				"주문번호에 대한 조회는 저의 샵의 고객센터에 유선상으로 또는 메일로 직접 문의 주시기 바랍니다.\n\n"+
				"탈퇴한 이후에도 고객님은 언제든 부담없는 가입이 가능하십니다.\n"+			
				"Kissin' Bugs은 고객님의 재방문을 언제든 환영합니다.\n"+
				"다시 뵙게될 날까지 고객님의 설레는 매일이 계속되길 진심으로 기원합니다.\n\n"+				
				"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";		
		messageDTO.setContent(goodByeText);
		return messageDTO;	
	}
	
	//비번 재설정 메일 전송
	@Override
	public MessageDTO sendResetPwdMail(MessageDTO messageDTO, String resetPwd) {
		messageDTO.setSender("[Kissin' Bugs]");
		messageDTO.setSubject("[Kissin' Bugs]비밀번호 재설정!");
		String confirmText = "안녕하세요, Kissin' Bugs입니다.\n"+
							" 저희 사이트는 사내 규정에 따라  비밀번호 조회시 임시번호를 발급합니다.\n"+
							"해당 메일 내에 있는 임시번호로 로그인 바랍니다.\n"+
							"========================================\n"+
							"임시 비밀번호는 ["+resetPwd+"]입니다.\n"+
							"========================================\n"+
							"*주> 비밀번호 변경을 원하시는 경우에는 임시번호로 우선 로그인하신 후, 회원정보 수정란에서 새로운 비밀번호로 변경하시기 바랍니다.";		
		messageDTO.setContent(confirmText);
		
		return messageDTO;
	}
	@Override
	public MessageDTO sendRestoreMail(MessageDTO messageDTO, String resetPwd) {
		messageDTO.setSender("[Kissin' Bugs]");
		messageDTO.setSubject("[Kissin' Bugs]회원 계정 복구 완료 안내 메일입니다.");
		String confirmText = "안녕하세요, Kissin' Bugs입니다.\n"+
							"요청에 따라 "+messageDTO.getReceiver()+" 님의 회원 계정이 정상적으로 복구되었습니다. \n"+
							"복구 시의 모든 계정은 일반 회원 등급으로 일시 분류됩니다. 일정 기간 경과 후에도 회원님의 계정이 특별 회원 등급으로\n"+
							"복구 되지 않은 경우에는 담당자에게 문의 바랍니다.\n"+
							"저희 사이트는 사내 규정에 따라  복구시에 비밀번호의 임시번호를 발급합니다.\n"+
							"해당 메일 내에 있는 임시번호로 로그인 바랍니다.\n"+
							"========================================\n"+
							"임시 비밀번호는 ["+resetPwd+"]입니다.\n"+
							"========================================\n"+	
							"*주> 비밀번호 변경을 원하시는 경우에는 임시번호로 우선 로그인하신 후, 회원정보 수정란에서 새로운 비밀번호로 변경하시기 바랍니다.\n"+
							"Kissin' Bugs은 고객님의 재방문을 환영합니다.\n"+
							"저희샵과 함께하는 고객님의 설레는 매일이 계속되길 진심으로 기원합니다.\n\n"+				
							"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";
		messageDTO.setContent(confirmText);		
		return messageDTO;
	}
	//일반 메일 보내기
	@Override
	public void sendMail(AdminDTO adminDTO, MessageDTO messageDTO) {
		
		JavaMailSender emailSender = getJavaMailSenger(adminDTO);
		
		if(emailSender!=null) {
		SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(messageDTO.getReceiveAddr());
			message.setSubject(messageDTO.getSubject());
			message.setText(messageDTO.getContent());
			emailSender.send(message);}
		else {
			System.out.println("[오류 발생]관리자 이메일 계정을 업데이트해주세요!");
		}
				
	}

	//파일 첨부/html 형식이 추가된 메일 보내기
	@Override
	public void sendMailwithFile(AdminDTO adminDTO, MessageDTO messageDTO) {
		
		JavaMailSender emailSender = getJavaMailSenger(adminDTO);
		
		if(emailSender!=null) {

		MimeMessage message = emailSender.createMimeMessage();
		
		try {
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
				helper.setSubject(messageDTO.getSubject());
				helper.setText(messageDTO.getContent(),messageDTO.getContainHTML());
			if(messageDTO.getMailData()!=null) {
				FileSystemResource file = new FileSystemResource(messageDTO.getMailData());	
				helper.addAttachment(messageDTO.getMailData().getName(), file);	}
				
				helper.setTo(messageDTO.getReceiveAddr());				
			
				emailSender.send(message);} 
		catch (MessagingException e) {e.printStackTrace();}}
		else {
			System.out.println("[오류 발생]관리자 이메일 계정을 업데이트해주세요!");
		}
	}

	@Override
	public JavaMailSender getJavaMailSenger(AdminDTO adminDTO) {
		
		String user= adminDTO.getAdmin_email_addr();
		String password = adminDTO.getAdmin_email_pwd();
		String[] email = adminDTO.getAdmin_email_addr().split("@");
		JavaMailSenderImpl mailSender;

		if(email[1].equals("gmail.com")) {
			mailSender = new JavaMailSenderImpl();
			mailSender.setHost("smtp.gmail.com");
			mailSender.setPort(587);
			mailSender.setUsername(user);
			mailSender.setPassword(password);
			mailSender.setDefaultEncoding("UTF-8");
			
			Properties props = mailSender.getJavaMailProperties();
		    	props.put("mail.transport.protocol", "smtp");
		    	props.put("mail.smtp.auth", "true");
		    	props.put("mail.smtp.starttls.enable", "true");
		    	props.put("mail.debug", "true");}
		else mailSender = null;
					
		return mailSender;
	}



}
