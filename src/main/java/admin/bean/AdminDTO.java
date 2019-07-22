package admin.bean;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AdminDTO {
 private String admin_id;
 private String admin_pwd;
 private String admin_email_addr;
 private String admin_email_pwd;
}
