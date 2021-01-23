package com.spring.board.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.dao.BoardDAO;
import com.spring.board.vo.BoardVO;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	private Logger logger = Logger.getLogger(BoardServiceImpl.class);
	private BoardDAO boardDAO;
	
	@Autowired(required=false)
	public BoardServiceImpl(BoardDAO boardDAO) {
		this.boardDAO=boardDAO;
	}
	
	
	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) boardList() 진입");
		
		List<BoardVO> list = boardDAO.boardList(bvo);
		
		return list;
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) boardDetail() 진입");
		
		BoardVO _bvo = null;
		_bvo = boardDAO.boardDetail(bvo);
		
		return _bvo;
	}

	@Override
	public int boardInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) boardInsert() 진입");
		
		int nCnt = boardDAO.boardInsert(bvo);
		
		return nCnt;
	}

	@Override
	public int boardUpdate(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) boardUpdate() 진입");
		
		int nCnt = boardDAO.boardUpdate(bvo);
		
		return nCnt;
	}

	@Override
	public int boardDelete(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) boardDelete() 진입");
		
		int nCnt = boardDAO.boardDelete(bvo);
		
		return nCnt;
	}

	@Override
	public List<BoardVO> pwdConfirm(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) pwdConfirm() 진입");
		
		List<BoardVO> list = boardDAO.pwdConfirm(bvo);
		
		return list;
	}


	@Override
	public int boardListCnt(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) boardListCnt() 진입");
		
		int nCnt = boardDAO.boardListCnt(bvo);
		
		return nCnt;
	}

}
