package mail.bean;

import org.springframework.mail.javamail.JavaMailSender;

import admin.bean.AdminDTO;
/*
 * MailingImpl의 인터페이스
 */
public interface Mailing {
	public String checkNum();
	public String getKey(int length);
	public MessageDTO sendBenefitGrantMail(MessageDTO messageDTO,String benefitInfo);
	public MessageDTO sendConfirmMail(MessageDTO messageDTO,String checkNum);
	public MessageDTO sendWelcomeMail(MessageDTO messageDTO);
	public MessageDTO sendOrderMail(MessageDTO messageDTO);
	public MessageDTO sendGoodbyeMail(MessageDTO messageDTO);
	public MessageDTO sendResetPwdMail(MessageDTO messageDTO, String resetPwd);
	public MessageDTO sendRestoreMail(MessageDTO messageDTO,String resetPwd);
	public JavaMailSender getJavaMailSenger(AdminDTO adminDTO);
	public void sendMail(AdminDTO adminDTO,MessageDTO messageDTO);
	public void sendMailwithFile(AdminDTO adminDTO,MessageDTO messageDTO);

}
