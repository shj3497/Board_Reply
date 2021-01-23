package com.spring.common.chaebun.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.vo.BoardVO;
import com.spring.common.chaebun.dao.ChaebunDAO;
import com.spring.reply.vo.ReplyVO;

@Service
@Transactional
public class ChaebunServiceImpl implements ChaebunService {

	private Logger logger = Logger.getLogger(ChaebunServiceImpl.class);
	private ChaebunDAO chaebunDAO;
	
	@Autowired(required=false)
	public ChaebunServiceImpl(ChaebunDAO chaebunDAO) {
		this.chaebunDAO=chaebunDAO;
	} 
	
	@Override
	public BoardVO getChaebun() {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) getChaebun() 진입");
		BoardVO bvo = chaebunDAO.getChaebun();		
		return bvo;
	}

	@Override
	public ReplyVO getChaebun_r() {
		// TODO Auto-generated method stub
		logger.info("(ServiceImpl) getChaebun_r() 진입");
		ReplyVO rvo = chaebunDAO.getChaebun_r();
		
		return rvo;
	}

}
