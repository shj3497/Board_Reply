package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO{

	// SqlSessionTemplate 와 SqlSession의 차이점
	// SqlSessionTemplate는 SqlSession을 구현하고 코드에서 SqlSession을 대체하는 역할을 한다.
	// SqlSessionTemplate는 쓰레드에 안전하고 여러개의 DAO나 매퍼에서 공유할 수 있다.
	// 책에서 SqlSession으로 사용했으니 SqlSession으로 사용해 보겠다.
	@Autowired(required=false)
	private SqlSession sqlSession;
	// private SqlSessionTemplate sqlSession;
	
	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) boardList() 진입");
		
		return sqlSession.selectList("boardList", bvo);
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) boardDetail() 진입");
		
		return (BoardVO)sqlSession.selectOne("boardDetail", bvo);
	}

	@Override
	public int boardInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) boardInsert() 진입");
		
		return (Integer)sqlSession.insert("boardInsert", bvo);
	}

	@Override
	public int boardUpdate(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) boardUpdate() 진입");
		
		return (Integer)sqlSession.update("boardUpdate", bvo);
	}

	@Override
	public int boardDelete(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) boardDelete() 진입");
		
		return (Integer)sqlSession.update("boardDelete", bvo);
	}

	@Override
	public List<BoardVO> pwdConfirm(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) pwdConfirm() 진입");
		
		return sqlSession.selectList("pwdConfirm", bvo);
	}

	@Override
	public int boardListCnt(BoardVO bvo) {
		// TODO Auto-generated method stub
		System.out.println("(DAOImpl) boardListCnt() 진입");
		
		return (Integer)sqlSession.selectOne("boardListCnt", bvo);
	}

}
