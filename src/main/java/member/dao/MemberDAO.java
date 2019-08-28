package member.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mail.bean.MessageDTO;
import member.bean.MemberDTO;
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
	MemberDTO findLostId(Map<String, String> map);
	void setNewPwd(MemberDTO memberDTO);
	MemberDTO getUser(String id);
	List<MemberDTO> getMemberList();
	void setPoint(String id,String pointQty);
	void reducePoint(String id,String pointQty);
	int deleteUserInfo(String id);
	int makeUserRestored(String id);
//----------MEMBER:END----------//
//----------QAMESSAGE:START----------//		
	void memberQASend(MessageDTO messageDTO);
//----------QAMESSAGE:END----------//
//----------MEMBER_DELETE:START----------//	
	void deleteMemberAdmin(Map<String, String> map);
	MemberDTO getDeleteRequest(String id);
	void deleteRequest(String id);
//----------MEMBER_DELETE:END----------//
	

	



} 
