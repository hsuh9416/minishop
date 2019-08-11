package member.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import mail.bean.MessageDTO;
import member.bean.MemberDTO;
import trading.bean.OrderDTO;
/*
 * MEMBER,QAMESSAGE DB 제어 메소드를 관리하는 클래스
 */
@Repository
@DependsOn(value= {"sqlSession"})
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;

//----------MEMBER:START----------//	
	//1. 회원 정보 호출
	@Override
	public MemberDTO login(String id, String pwd) {
		
		Map<String,String> map = new HashMap<String,String>();
			map.put("id",id);
			map.put("pwd",pwd);
		return sqlSession.selectOne("memberSQL.login",map);
	}

	//2. 아이디 검색 결과 반환
	@Override
	public MemberDTO checkId(String id) {
		return sqlSession.selectOne("memberSQL.checkId", id);
	}

	//3. 회원가입 업로드
	@Override
	public int write(MemberDTO memberDTO) {
		return sqlSession.insert("memberSQL.write", memberDTO);
	}
	
	//4. 회원 정보수정 반영
	@Override
	public int modify(MemberDTO memberDTO) {
		return sqlSession.update("memberSQL.modify", memberDTO);
	}

	//5. 자동로그인 정보 업로드/업데이트
	@Override
	public void keepLogin(String id, String sessionId, Date sessionLimit){
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("sessionid", sessionId);
			map.put("sessionlimit", sessionLimit);		
			sqlSession.update("memberSQL.keepLogin", map);
	}

	//6. 자동로그인을 통한 회원 정보 호출
	@Override
	public MemberDTO checkLoginBefore(String value){
		return sqlSession.selectOne("memberSQL.checkLoginBefore", value);
	}

	//7. 회원삭제 요청을 통한 회원정보 만료화 반영
	@Override
	public void deleteMember(String id) {
		sqlSession.update("memberSQL.deleteMember", id);	
	}

	//8. 주문 정보 호출(비회원 로그인)
	@Override
	public OrderDTO orderCheck(String id, String pwd) {
		Map<String,String> map = new HashMap<String,String>();
			map.put("orderId",id);
			map.put("orderPwd",pwd);
		return sqlSession.selectOne("memberSQL.orderCheck",map);
	}

	//9. 아이디 찾기 결과 반환
	@Override
	public MemberDTO findLostId(Map<String, String> map) {
		return sqlSession.selectOne("memberSQL.findLostId",map);
	}

	//10. 비밀번호 임시번호로 재설정 반영
	@Override
	public void setNewPwd(MemberDTO memberDTO) {
		sqlSession.update("memberSQL.setNewPwd",memberDTO);
	}
	
	//11. 회원 정보 호출
	@Override
	public MemberDTO getUser(String id) {
		return sqlSession.selectOne("memberSQL.getMember",id);//재반환
	}

//----------MEMBER:END----------//
	
//----------QAMESSAGE:START----------//	

	//1. 1:1 문의사항 DB 저장
	@Override
	public void memberQASend(MessageDTO messageDTO) {
		sqlSession.insert("memberSQL.memberQASend",messageDTO);
	}

//----------QAMESSAGE:END----------//		

//----------QAMESSAGE:START----------//	
	@Override
	public void deleteMemberAdmin(Map<String, String> map) {
		sqlSession.insert("memberSQL.deleteMemberAdmin",map);		
	}	
//----------QAMESSAGE:END----------//

}
