package com.spring.common.chaebun.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.vo.BoardVO;
import com.spring.reply.vo.ReplyVO;

@Repository
public class ChaebunDAOImpl implements ChaebunDAO{

	@Autowired(required=false)
	private SqlSession sqlSession;
	
	@Override
	public BoardVO getChaebun() {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) getChaebun() 진입");
		
		return sqlSession.selectOne("getChaebun");
	}

	@Override
	public ReplyVO getChaebun_r() {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) getChaebun_r() 진입");
		
		return sqlSession.selectOne("getChaebun_r");
	}

}
