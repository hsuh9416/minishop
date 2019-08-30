package mail.bean;

import org.springframework.mail.javamail.JavaMailSender;

import admin.bean.AdminDTO;
import trading.bean.OrderDTO;
/*
 * MailingImpl의 인터페이스
 */
public interface Mailing {
	public String checkNum();
	public String getKey(int length);
	public MessageDTO sendBenefitGrantMail(MessageDTO messageDTO,String benefitInfo);
	public MessageDTO sendConfirmMail(MessageDTO messageDTO,String checkNum);
	public MessageDTO sendWelcomeMail(MessageDTO messageDTO);
	public MessageDTO sendOrderMail(MessageDTO messageDTO, OrderDTO orderDTO);
	public MessageDTO sendOrderModifiedMail(MessageDTO messageDTO, OrderDTO orderDTO);
	public MessageDTO sendPaymentConfirmMail(MessageDTO messageDTO, OrderDTO orderDTO);
	public MessageDTO sendDeliveryInfoMail(MessageDTO messageDTO,OrderDTO orderDTO);
	public MessageDTO sendDeliveryConfirmMail(MessageDTO messageDTO,OrderDTO orderDTO);
	public MessageDTO sendRefundInfoMail(MessageDTO messageDTO,OrderDTO orderDTO);
	public MessageDTO sendRefundCompleteMail(MessageDTO messageDTO,OrderDTO orderDTO);
	public MessageDTO sendOrderCompletedMail(MessageDTO messageDTO, OrderDTO orderDTO, String state, int point);
	public MessageDTO sendOrderCancelMail(MessageDTO messageDTO, OrderDTO orderDTO);
	public MessageDTO sendGoodbyeMail(MessageDTO messageDTO);
	public MessageDTO sendResetPwdMail(MessageDTO messageDTO, String resetPwd);
	public MessageDTO sendRestoreMail(MessageDTO messageDTO,String resetPwd);
	public MessageDTO sendPwdResetMail(MessageDTO messageDTO, OrderDTO orderDTO,String rePwd);
	public JavaMailSender getJavaMailSender(AdminDTO adminDTO);
	public void sendMail(AdminDTO adminDTO,MessageDTO messageDTO);
	public void sendMailwithFile(AdminDTO adminDTO,MessageDTO messageDTO);




}
