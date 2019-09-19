package trading.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class FileMaker {
	
	public File makeOrderList(String reciever,List<OrderDTO> orderList) {
		
		Date date = new Date();
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy.MM.dd");
		String writeDate = sf1.format(date);
		String today = sf2.format(date);
		BufferedWriter writer =null;
		String file_title = reciever+writeDate+".txt";
		FileWriter fileWriter= null;
		File file = new File(file_title);
	
		try {
				fileWriter = new FileWriter(file);
				writer = new BufferedWriter(fileWriter);
				writer.write("["+today+"]일자 "+reciever+" 고객님의 총 거래내역 목록(요약)");
				writer.newLine(); 
				writer.write("=================================================");
				writer.newLine();	
			if(orderList!=null) {
				for(OrderDTO dto: orderList) {
					int orderNum = dto.getOrder_no();
					String orderName = dto.getOrder_name();
					String orderId = dto.getOrder_id();
					String orderAddress = dto.getOrder_address();
					String orderDate = sf2.format(dto.getOrder_date());
					String orderState="";
					for(OrderState state : OrderState.values()) {
						if(dto.getOrder_state()==state.ordinal()) {
							orderState=state.toString();
							break;
						}
					}
					
					String content="[주문번호 : "+orderNum+" ,주문서ID: "+orderId+" ,주문자명 : "+orderName+" ,배송주소 : "+orderAddress+" ,주문일자 : "
					+orderDate+" ,거래 상태 : "+orderState+"]";
						writer.write(content);
						writer.newLine();
				}

			}
			else {
					writer.write("현재 시점("+today+")에 "+reciever+"님과 당사 간의 거래 내역이 존재하지 않습니다.");
					writer.newLine();
			}
			writer.write("=================================================");
			writer.newLine();
			writer.flush();
			
		} 
		catch (IOException e) { e.printStackTrace();}
		finally {
				try {
					if(writer !=null)	
							writer.close();} 
				catch (IOException e) {e.printStackTrace();}}
		
		return file;
		
	}

}
