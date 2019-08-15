package trading.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/*
 * 배너 정보 관리 클래스
 */
@Component
@Data
public class EventDTO {
	private int event_no;
	private String event_name;
	private String event_image;
	private String event_url;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date event_startdate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date event_enddate;
	private StringBuffer event_banner;
	
	public void callBannerList() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		if(!today.after(event_startdate)&&!today.before(event_enddate)) {
			event_url="/minishop/main/home.do";
			event_image="no_banner.jpg";}
		event_banner = new StringBuffer();
		event_banner.append("<div class='form-group col-1'>"+event_no+"</div>");
		event_banner.append("<div class='form-group col-2'>"+event_name+"</div>");	
		event_banner.append("<div class='form-group col-2'><img style='width:50px;height:50px;'"+
				"src='/minishop/storage/showBanner.do?event_image="+event_image+"'></div>");	
		event_banner.append("<div class='form-group col-3'>"+event_url+"</div>");
		event_banner.append("<div class='form-group col-2'>"+sdf.format(event_startdate)+"</div>");	
		event_banner.append("<div class='form-group col-2'>"+sdf.format(event_enddate)+"</div>");	
	}
	
	public void settingBanner() {
		
		Date today = new Date();
		
		
		if(!today.after(event_startdate)&&!today.before(event_enddate)) {
			event_url="/minishop/main/home.do";
			event_image="no_banner.jpg";}
		
			event_banner = new StringBuffer();
		if(event_no==1) {
			event_banner.append("<div class='carousel-item active'>");		
		}
		else {
			event_banner.append("<div class='carousel-item'>");			
		}
		event_banner.append("<a href='"+event_url+"'>");	
		event_banner.append("<img class='d-block img-fluid' style='width:900px;height:350px;' "+ 
				"src='/minishop/storage/showBanner.do?event_image="+event_image+"'>");	
		event_banner.append("</a></div>");			
	}
}
