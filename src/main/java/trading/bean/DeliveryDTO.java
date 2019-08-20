package trading.bean;

import org.springframework.stereotype.Component;

import lombok.Data;

/*
 * DB DELIVERY 관련 요소 관리 클래스
 */
@Component
@Data
public class DeliveryDTO {
	private int delivery_code;
	private String delivery_type;
	private int delivery_fee;
}
