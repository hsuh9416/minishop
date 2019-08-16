package member.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mail.bean.MessageDTO;
import member.bean.MemberDTO;
import trading.bean.OrderDTO;
/*
 * MemberDAOImple의 인터페이스
 */
public interface MemberDAO {

//----------MEMBER:START----------//		
	MemberDTO login(String id, String pwd);
	MemberDTO checkId(String id);
	int write(MemberDTO memberDTO);
	int modify(MemberDTO memberDTO);
	void keepLogin(String id, String sessionId, Date sessionLimit);
	MemberDTO checkLoginBefore(String value);
	void deleteMember(String id);
	OrderDTO orderCheck(String id, String pwd);
	MemberDTO findLostId(Map<String, String> map);
	void setNewPwd(MemberDTO memberDTO);
	MemberDTO getUser(String id);
	List<MemberDTO> getMemberList();
//----------MEMBER:END----------//
//----------QAMESSAGE:START----------//		
void memberQASend(MessageDTO messageDTO);
//----------QAMESSAGE:END----------//
//----------MEMBER_DELETE:START----------//	
void deleteMemberAdmin(Map<String, String> map);
//----------MEMBER_DELETE:END----------//


} 
