package admin.dao;

import java.util.List;

import admin.bean.AdminDTO;
import mail.bean.MessageDTO;
/*
 * AdminDaoImpl의 인터페이스
 */
public interface AdminDAO {
	AdminDTO getAdmin();

	List<MessageDTO> getPersonalQAList();

	MessageDTO getPersonalQA(String seq);

	int deleteQA(String string);
}
