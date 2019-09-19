package mail.bean;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import trading.bean.OrderDTO;
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
				"증정되는 쿠폰의 코드 넘버는 [9999]이며 PersonalCode는 ["+PersonalCode+"]입니다.\n"+
				"========================================\n"+			
				"신감각 편집샵 Kissin' Bugs와 함께 설레는 하루되시기 바랍니다.\n\n"+
				"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";		
		messageDTO.setCode(PersonalCode);
		messageDTO.setContent(welcomeText);		
		return messageDTO;
	}
	@Override
	public MessageDTO sendOrderMail(MessageDTO messageDTO, OrderDTO orderDTO) {
			messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
			messageDTO.setReceiveAddr(orderDTO.getOrder_email());
			messageDTO.setSender("[Kissin' Bugs]");		  
			messageDTO.setSubject(orderDTO.getOrder_name()+" 님의 주문서가 정상적으로 접수되었습니다.");	
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
						orderDTO.getOrder_name()+"님의 주문이 정상적으로 접수되었으며, 최종 입금 확인과 동시에 배송준비에 착수합니다.\n"+
						 "이 메일은 "+orderDTO.getOrder_name()+" 님의 주문 접수를 확인하도록 하는 목적으로 발송된 것입니다.\n"+
						 "주문서의 상세 내역은 아래 기재된 주문서의 아이디와 비밀번호로 저희 몰에 로그인하시어 조회하실 수 있습니다.\n"+
						 "주문서를 분실하시거나 비밀번호를 잊어 버리신 경우에는 저희 고객센터에 직접 문의바랍니다.\n"+
						 "========================================\n"+
						 " 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
						 " 금번 주문하신 주문서의 주문서 아이디:  ["+orderDTO.getOrder_id()+"]\n"+
						 " 금번 주문하신 주문서의 주문서 비밀번호 :  ["+orderDTO.getOrder_pwd()+"]\n"+					 
						 "========================================\n"+					
						 "저희 샵은 중도 주소변경 등으로 인한  착오 발생을 방지하기 위하여 고객 정보 변경의 경우에는 유선상 또는 메일 서신을 통한 요청만 허용하고 있습니다.\n"+
						 "배송지 변경의 경우에는 최소 발송 전에 저희 샵으로 연락바라며, 발송 이후에는 주문취소와 주소지 변경 등의 제약이 발생합니다.\n"+
						 "연락처 변경의 경우에는 주문서에 따른 연락처를 기준으로 하고 있으며 이는 고객 정보 수정과는 별도로 저희 직원에 요청 부탁드립니다.\n"+
						 "주문서상 연락처 변경에 따른 변경 연락처는 요청 수락에 따른 변경으로부터 전진적으로 적용됩니다.(유선 및 무선 전화,전자메일  모두 동일)\n"+
						 "주문서상 변경에 따라 발생하는 차액 등은 별도로 환불해드리지 않으며 불가피한 경우에는 차기 구입에 사용할 수 있는 포인트 등으로 적립됩니다.\n"+
						 "갑작스런 재고 부족 등의 상황에 관하여는 저희 샵에서 별도의 메일 및 연락을 드리오니 해당 경우에는 양해 부탁드립니다.\n"+
						 "저희 Kissin'Bugs와 거래해주신 것에 감사드리며, 앞으로도 많은 관심 부탁드립니다.\n"+
						 "저희 Kissin'Bugs와 함께 고객님의 설레는 매일이 계속되길 바랍니다.\n\n"+				
						 "\t\t\t\t\t[Kissin' Bugs] 드림";	
			messageDTO.setContent(context);
			
		return messageDTO;
	}
	@Override
	public MessageDTO sendOrderModifiedMail(MessageDTO messageDTO, OrderDTO orderDTO) {
			messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
			messageDTO.setReceiveAddr(orderDTO.getOrder_email());
			messageDTO.setSender("[Kissin' Bugs]");		  
			messageDTO.setSubject(orderDTO.getOrder_name()+" 님의 주문서가 정상적으로 수정되었습니다.");	
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
				orderDTO.getOrder_name()+"님의 주문이 정상적으로 수정되었습니다.\n"+
				 "이 메일은 "+orderDTO.getOrder_name()+" 님의 주문정보가 수정되었음을 확인하는 목적으로 발송된 것입니다.\n"+
				 "주문서의 상세 내역은 주문서의 아이디와 비밀번호로 접속하신 뒤에 조회하시기 바랍니다.\n"+
				 "주문서를 분실하시거나 비밀번호를 잊어 버리신 경우에는 저희 고객센터에 직접 문의바랍니다.\n"+
				 "저희 샵은 중도 주소변경 등으로 인한  착오 발생을 방지하기 위하여 고객 정보 변경의 경우에는 유선상 또는 메일 서신을 통한 요청만 허용하고 있습니다.\n"+
				 "배송지 변경의 경우에는 최소 발송 전에 저희 샵으로 연락바라며, 발송 이후에는 주문취소와 주소지 변경 등의 제약이 발생합니다.\n"+
				 "연락처 변경의 경우에는 주문서에 따른 연락처를 기준으로 하고 있으며 이는 고객 정보 수정과는 별도로 저희 직원에 요청 부탁드립니다.\n"+
				 "주문서상 연락처 변경에 따른 변경 연락처는 요청 수락에 따른 변경으로부터 전진적으로 적용됩니다.(유선 및 무선 전화,전자메일  모두 동일)\n"+
				 "주문서상 변경에 따라 발생하는 차액 등은 별도로 환불해드리지 않으며 불가피한 경우에는 차기 구입에 사용할 수 있는 포인트 등으로 적립됩니다.\n"+
				 "갑작스런 재고 부족 등의 상황에 관하여는 저희 샵에서 별도의 메일 및 연락을 드리오니 해당 경우에는 양해 부탁드립니다.\n"+
				 "저희 Kissin'Bugs와 거래해주신 것에 감사드리며, 앞으로도 많은 관심 부탁드립니다.\n"+
				 "저희 Kissin'Bugs와 함께 고객님의 설레는 매일이 계속되길 바랍니다.\n\n"+				
				 "\t\t\t\t\t[Kissin' Bugs] 드림";	
				messageDTO.setContent(context);
		return messageDTO;
	}
	@Override
	public MessageDTO sendPaymentConfirmMail(MessageDTO messageDTO, OrderDTO orderDTO) {
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님의 주문서가 입금확인 처리 되었습니다.");	
	String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
			orderDTO.getOrder_name()+"님의 주문서가 입금확인 처리되었습니다.\n"+
			 "이 메일은 "+orderDTO.getOrder_name()+" 님께서 지급하신 금액을 저희 샵에서 확인하였음을 알리는 목적으로 보내는 메일입니다.\n"+
			 "저희 Kissin'Bugs는 입금확인 직후 상품을 발송하는 것을 원칙으로 하고 있습니다만, 일부 상품의 입고지연 등의 경우에는 별도의 안내 메일을 전송하여 알립니다.\n"+
			 "재고 발송 후에는 배송지 등의 변경이 어렵사오니 입력하신 배송지가 정확한지를 배송 전까지 주문서를 통해 확인하시기 바랍니다.\n"+
			 "주문서의 상세 내역은 주문서의 아이디와 비밀번호로 접속하신 뒤에 조회하시기 바랍니다.\n"+
			 "주문서를 분실하시거나 비밀번호를 잊어 버리신 경우에는 저희 고객센터에 직접 문의바랍니다.\n"+
			 "갑작스런 재고 부족 등의 상황에 관하여는 저희 샵에서 별도의 메일 및 연락을 드리오니 해당 경우에는 양해 부탁드립니다.\n"+
			 "저희 Kissin'Bugs와 거래해주신 것에 감사드리며, 앞으로도 많은 관심 부탁드립니다.\n"+
			 "저희 Kissin'Bugs와 함께 고객님의 설레는 매일이 계속되길 바랍니다.\n\n"+				
			 "\t\t\t\t\t[Kissin' Bugs] 드림";	
			messageDTO.setContent(context);
	return messageDTO;
	}
	
	@Override
	public MessageDTO sendDeliveryInfoMail(MessageDTO messageDTO, OrderDTO orderDTO) {
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님께서 주문하신 물품이 발송되었습니다.");
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
				orderDTO.getOrder_name()+"님께서 주문하신 상품이 발송되었으므로 공지합니다. 자세한 정보는 아래와 같습니다.\n"+
				 		"========================================\n"+
				 		" 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 송장번호:  ["+orderDTO.getOrder_deliverynum()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 추가 정보:  ["+orderDTO.getOrder_statement()+"]\n"+	
				 		"========================================\n"+	
						"배송진행 상환 등은 추가 정보에 포함된 배송사가 제공하는 배송조회 시스템을 이용하기 바랍니다.\n"+	
						"또한 배송중에는 배송지의 변경이 어렵사오니 불가피한 경우에는 고객센터에 직접 문의하시기 바랍니다.\n"+	
						"배송 전까지 추가적인 사항에 대한 공지는 신속하게 전달해 드리며 추가적인 고객 문의는 언제든지 환영합니다.\n"+					 		
						"Kissin' Bugs은 고객만족을 위하여 항상 최선을 다하려고 노력하고 있습니다.\n\n"+
						"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";	
			messageDTO.setContent(context);	 		
		return messageDTO;
	}	
	@Override
	public MessageDTO sendDeliveryConfirmMail(MessageDTO messageDTO, OrderDTO orderDTO) {
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님께서 주문하신 상품에 대한 수취확인 메일입니다.");	
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
						orderDTO.getOrder_name()+"님께서 주문하신 상품에 대한 발송이 완료되었습니다.\n"+
						"\n"+
				 		"========================================\n"+
				 		" 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 송장번호:  ["+orderDTO.getOrder_deliverynum()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 배송 완료 일자:  ["+new SimpleDateFormat("yyyy.MM.dd").format(orderDTO.getOrder_logtime())+"]\n"+	
				 		" 금번 주문하신 주문서의 주문서에 대한 추가 정보:  ["+orderDTO.getOrder_statement()+"]\n"+	
				 		"========================================\n"+	
						"Kissin'Bugs의 내부규정상 수취확인을 한 이후에 포인트 등의 혜택이 지급됩니다.\n"+					 		
						"Kissin'Bugs의 상품과 함께 설레는 하루 되시길 바라며 앞으로도 많은 관심 부탁드립니다.\n"+					 		
						"Kissin' Bugs은 고객만족을 위하여 항상 최선을 다하려고 노력하고 있습니다.\n\n"+
						"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";	
			messageDTO.setContent(context);	 		
		return messageDTO;
	}

	@Override
	public MessageDTO sendRefundInfoMail(MessageDTO messageDTO, OrderDTO orderDTO) {
		NumberFormat df = NumberFormat.getInstance();
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님의 환불요청이 접수되었습니다.");	
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
				orderDTO.getOrder_name()+"님의 환불 요청의 건이 정상적으로 접수되었으며, 설정해주신 계좌와 환불 주문서의 정보를 공지합니다.\n"+
				 		"========================================\n"+
				 		" 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 환불 계좌:  ["+orderDTO.getOrder_refundaccount()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 환불 예상액:  ["+df.format(orderDTO.getPayment_amount())+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 환불 접수 일자:  ["+new SimpleDateFormat("yyyy.MM.dd").format(orderDTO.getOrder_logtime())+"]\n"+	
				 		" 금번 주문하신 주문서의 주문서에 대한 추가 정보:  ["+orderDTO.getOrder_statement()+"]\n"+	
				 		"========================================\n"+	
						"Kissin'Bugs의 내부규정 및 절차상 환불요청으로부터 일정 시간이 소요됨을 알립니다.\n"+	
						"환불이 요청된 건에 대해서는 부득이한 경우를 제외하고는 복구되지 않사오니, 환불의사가 변경된 경우 등에는 새로이 주문서를 작성해주시기 바랍니다.\n"+	
						"사용하신 포인트와 쿠폰은 최종 환불 완료시에 일괄 복원됩니다.\n"+
						"환불의 건이 완료되는 고객님께 확인 메일을 발송하오니 양해부탁드립니다.\n"+					 		
						"Kissin' Bugs은 고객만족을 위하여 항상 최선을 다하려고 노력하고 있습니다.\n\n"+
						"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";	
			messageDTO.setContent(context);	 		
		return messageDTO;
	}
	@Override
	public MessageDTO sendRefundCompleteMail(MessageDTO messageDTO, OrderDTO orderDTO) {
		NumberFormat df = NumberFormat.getInstance();
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님의 주문에 대한 환불이 완료되었습니다.");
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
				orderDTO.getOrder_name()+"님의 환불 요청의 건이 정상적으로 접수되었으며, 설정해주신 계좌와 환불 주문서의 정보를 공지합니다.\n"+
				 		"========================================\n"+
				 		" 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 환불 계좌:  ["+orderDTO.getOrder_refundaccount()+"]\n"+
				 		" 금번 주문하신 주문서의 주문서에 대한 최종 환불액:  ["+df.format(orderDTO.getPayment_amount())+"]\n"+	
				 		" 금번 주문하신 주문서의 주문서에 대한 환불 일자:  ["+new SimpleDateFormat("yyyy.MM.dd").format(orderDTO.getOrder_logtime())+"]\n"+	
				 		"========================================\n"+	
						"확인된 금액은 Kissin'Bugs의 내부규정에 따라 지급하는 최종 금액이므로 양해부탁드립니다.\n"+					 		
						"Kissin' Bugs은 고객만족을 위하여 항상 최선을 다하려고 노력하고 있습니다.\n\n"+
						"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";	
			messageDTO.setContent(context);	 	
		return messageDTO;
	}
	@Override
	public MessageDTO sendOrderCompletedMail(MessageDTO messageDTO, OrderDTO orderDTO, String state, int point){

		NumberFormat df = NumberFormat.getInstance();
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님께 포인트지급이 완료되었습니다.");
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
				orderDTO.getOrder_name()+"님께서 수취확인을 하신 결과로 아래와 같은 회원 혜택을 지급합니다.\n"+
				 		"========================================\n"+
				 		" 포인트 적립 대상 ID(등급) :  ["+orderDTO.getOrder_id()+"("+state+")]\n"+
				 		" 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
				 		" 금번 주문하신 주문서의 총 거래금액:  ["+df.format(orderDTO.getOrder_total())+"원]\n"+
				 		" 금번 주문하신 주문서로 인한 지급 포인트 :  ["+df.format(point)+"점]\n"+	
				 		"========================================\n"+	
						"이는 Kissin'Bugs의 내부규정에 따른 지급 혜택이오니 참고바랍니다.\n"+	
						"저희 Kissin'Bugs는 항상 새롭고 참신한 상품을 준비하고 있습니다. 앞으로도 많은 관심 부탁드립니다.\n\n"+	
						"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";	
			messageDTO.setContent(context);	 	
		return messageDTO;
	}
	@Override
	public MessageDTO sendOrderCancelMail(MessageDTO messageDTO, OrderDTO orderDTO) {
		messageDTO.setReceiver(orderDTO.getOrder_name()+" 고객님");
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSender("[Kissin' Bugs]");		  
		messageDTO.setSubject(orderDTO.getOrder_name()+" 님의 주문이 취소되었습니다.");
		String context = "안녕하세요, 감각적인 쇼핑몰 Kissin' Bugs 입니다.\n"+
						orderDTO.getOrder_name()+"님의 다음의 주문이 취소처리되었습니다.\n"+
				 		"========================================\n"+
				 		" 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+	
				 		"========================================\n"+	
						"이는 Kissin'Bugs의 내부규정에 따라 일정한 경우(상품 및 배송 문제 혹은 고객의 요청 등) 관리자가 주문을 취소하는 규정을 두고 있습니다.\n"+
						"주문 취소 처리로 인한 고객님의 불편을 끼친 점 대단히 죄송합니다. 빠른 시일 내에 관련 문제를 시정하도록 노력하겠습니다.\n"+				 		
						"Kissin' Bugs은 고객만족을 위하여 항상 최선을 다하려고 노력하고 있습니다.\n\n"+
						"\t\t\t\t\t[Kissin' Bugs] 직원 일동 드림";	
			messageDTO.setContent(context);	
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
				"기존에 주문 완료가 된 상품에 대해서는 별도로 안내된 주문서 아이디로 접속하여 접근하실 수 있으며,\n"+
				"주문번호에 대한 비밀번호는 임시로 발급된 다음의 비밀번호를 공통적으로 적용합니다.\n"+					
				"========================================\n"+
				"회원님의 주문서에 대한 조회 아이디는 [주문일자(yyyymmdd)-주문번호(nn)]형식입니다.\n"+	
				"회원님의 주문서에 대한 임시 비밀번호는 ["+messageDTO.getCode()+"]입니다.\n"+	
				"========================================\n"+
				"*상세 내역은 주문목록 참고\n"+	
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
	
	//주문 비밀번호 재설정 메일 보내기
	@Override
	public MessageDTO sendPwdResetMail(MessageDTO messageDTO, OrderDTO orderDTO,String rePwd) {
		messageDTO.setSender("[Kissin' Bugs]");
		messageDTO.setReceiver(orderDTO.getOrder_name());
		messageDTO.setReceiveAddr(orderDTO.getOrder_email());
		messageDTO.setSubject(orderDTO.getOrder_name()+"님의 주문서에 대한 비밀번호 재설정 안내 메일입니다.");
		String confirmText = "안녕하세요, Kissin' Bugs입니다.\n"+
						orderDTO.getOrder_name()+"님의 주문서 비밀번호 변경 요청에 따라 주문서의 비밀번호가 재설정되었습니다.\n"+
						"변경 후의 주문서에 대한 간단 명세는 아래와 같습니다.\n"+
						"========================================\n"+
						 " 금번 주문하신 주문서의 주문번호 :  ["+orderDTO.getOrder_no()+"]\n"+
						 " 금번 주문하신 주문서의 주문서 아이디:  ["+orderDTO.getOrder_id()+"]\n"+
						 " 금번 주문하신 주문서의 주문서 비밀번호 :  ["+rePwd+"]\n"+		
						"========================================\n"+
						"*주> 주문서의 비밀번호는 규정상 사용자가 직접 수정하지 않도록 되어 있으며, 관리자가 변경시마다 새로이 임의의 비밀번호를 발급합니다.\n"+	
						"기타 문의사항은 고객센터에 직접 문의 바랍니다.\n";	
		messageDTO.setContent(confirmText);
		return messageDTO;
	}
	
	//일반 메일 보내기
	@Override
	public void sendMail(AdminDTO adminDTO, MessageDTO messageDTO) {
		
		JavaMailSender emailSender = getJavaMailSender(adminDTO);
		
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
		
		JavaMailSender emailSender = getJavaMailSender(adminDTO);
		
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
	public JavaMailSender getJavaMailSender(AdminDTO adminDTO) {
		
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
