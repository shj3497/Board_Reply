package com.spring.board.dao;

import java.util.List;

import com.spring.board.vo.BoardVO;

public interface BoardDAO {

	public List<BoardVO> boardList(BoardVO bvo);
	public BoardVO boardDetail(BoardVO bvo);
	public int boardInsert(BoardVO bvo);
	public int boardUpdate(BoardVO bvo);
	public int boardDelete(BoardVO bvo);
	
	public List<BoardVO> pwdConfirm(BoardVO bvo);
	
	// 페이징 처리를 위한
	public int boardListCnt(BoardVO bvo);
	
}
