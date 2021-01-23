package com.spring.reply.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.reply.vo.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	@Autowired(required=false)
	private SqlSession sqlSession;
	
	// 글 목록
	@Override
	public List<ReplyVO> replyList(String b_num) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList("replyList", b_num);
	}

	// 글 입력
	@Override
	public int replyInsert(ReplyVO rvo) {
		// TODO Auto-generated method stub\
		
		return (Integer)sqlSession.insert("replyInsert", rvo);
	}

	// 글 수정
	@Override
	public int replyUpdate(ReplyVO rvo) {
		// TODO Auto-generated method stub
		
		return (Integer)sqlSession.update("replyUpdate", rvo);
	}

	// 글 삭제
	@Override
	public int replyDelete(String r_num) {
		// TODO Auto-generated method stub
		
		return (Integer)sqlSession.delete("replyDelete", r_num);
	}

}
