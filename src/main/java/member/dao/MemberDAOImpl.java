package member.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import mail.bean.MessageDTO;
import member.bean.GuestDTO;
import member.bean.MemberDTO;
import member.bean.PostDTO;

@Repository
@DependsOn(value= {"sqlSession"})
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;
	
	//로그인
	@Override
	public MemberDTO login(String id, String pwd) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id",id);
		map.put("pwd",pwd);
		return sqlSession.selectOne("memberSQL.login",map);
		
	}

	//아이디 체크
	@Override
	public MemberDTO checkId(String id) {
		return sqlSession.selectOne("memberSQL.checkId", id);
	}

	//회원가입
	@Override
	public int write(MemberDTO memberDTO) {
		return sqlSession.insert("memberSQL.write", memberDTO);
	}

	//우편번호 검색
	@Override
	public List<PostDTO> getPostList(Map<String,String> map) {
		return sqlSession.selectList("memberSQL.getPostList", map);
	}
	
	//회원 정보수정
	@Override
	public int modify(MemberDTO memberDTO) {
		return sqlSession.update("memberSQL.modify", memberDTO);
	}

	@Override
	public void keepLogin(String id, String sessionId, Date sessionLimit){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("sessionid", sessionId);
		map.put("sessionlimit", sessionLimit);		
		sqlSession.update("memberSQL.keepLogin", map);
		
	}

	@Override
	public MemberDTO checkLoginBefore(String value){
		
		return sqlSession.selectOne("memberSQL.checkLoginBefore", value);
	}

	@Override
	public void deleteMember(String id) {
		sqlSession.update("memberSQL.deleteMember", id);
		
	}

	//주문내역으로 조회-DB 필요
	@Override
	public GuestDTO orderCheck(String id, String pwd) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("orderId",id);
		map.put("orderPwd",pwd);
		return sqlSession.selectOne("memberSQL.orderCheck",map);
	}

	//관리자 정보 가져오기
	@Override
	public MemberDTO getAdmin() {
		return sqlSession.selectOne("memberSQL.getAdmin");
	}

	//회원 문의 사항 저장
	@Override
	public void memberQASend(MessageDTO messageDTO) {
		sqlSession.insert("memberSQL.memberQASend",messageDTO);
	}

	//비밀번호 재설정
	@Override
	public void setNewPwd(MemberDTO memberDTO) {
		sqlSession.update("memberSQL.setNewPwd",memberDTO);
		
	}

	//아이디 검색
	@Override
	public MemberDTO findLostId(Map<String, String> map) {
		return sqlSession.selectOne("memberSQL.findLostId",map);
	}




	
}
