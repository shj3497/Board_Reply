package com.spring.reply.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.reply.dao.ReplyDAO;
import com.spring.reply.vo.ReplyVO;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

	private Logger logger = Logger.getLogger(ReplyServiceImpl.class);
	private ReplyDAO replyDAO;
	
	@Autowired(required=false)
	public ReplyServiceImpl (ReplyDAO replyDAO) {
		this.replyDAO=replyDAO;
	}
	
	
	@Override
	public List<ReplyVO> replyList(String b_num) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) replyList() 진입");
		
		List<ReplyVO> list = replyDAO.replyList(b_num);
	
		return list;
	}

	@Override
	public int replyInsert(ReplyVO rvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) replyInsert() 진입");
		
		int nCnt = replyDAO.replyInsert(rvo);
		
		return nCnt;
	}

	@Override
	public int replyUpdate(ReplyVO rvo) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) replyUpdate() 진입");
		
		int nCnt = replyDAO.replyUpdate(rvo);
		
		return nCnt;
	}

	@Override
	public int replyDelete(String r_num) {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) replyDelete() 진입");
		
		int nCnt = replyDAO.replyDelete(r_num);
		
		return nCnt;
	}

}
