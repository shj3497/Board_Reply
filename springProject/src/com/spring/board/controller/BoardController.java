package com.spring.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.service.BoardService;
import com.spring.board.vo.BoardVO;
import com.spring.common.chaebun.ChaebunUtil;
import com.spring.common.chaebun.service.ChaebunService;
import com.spring.common.file.FileUploadUtil;
import com.spring.common.page.Paging;
import com.spring.common.util.Util;

@Controller
@RequestMapping(value="/board")
public class BoardController {

	private Logger logger = Logger.getLogger(BoardController.class);
	
	@Autowired(required=false)
	private BoardService boardService;
	@Autowired(required=false)
	private ChaebunService chaebunService;
	
	@Autowired(required=false)
	public BoardController(BoardService boardService,
						   ChaebunService chaebunService) {
		this.boardService=boardService;
		this.chaebunService=chaebunService;
	}
	
	// 글쓰기 폼 출력하기
	@RequestMapping(value="/writeForm", method=RequestMethod.GET)
	public String writeForm() {
		logger.info("(Controller) writeForm() 진입");
		
		return "board/writeForm";
	}
	
	// 글 목록 구현
	@RequestMapping(value="/boardList", method=RequestMethod.GET)
	public String boardList(Model model, @ModelAttribute BoardVO bvo) {
		logger.info("(Controller) boardList() 진입");
		
		// 정렬에 대한 기본값 설정
		if(bvo.getOrder_by()==null) {
			bvo.setOrder_by("b_num"); // >> ??
		}
		if(bvo.getOrder_sc() == null) {
			bvo.setOrder_sc("DESC");
		}
		
		// 정렬에 대한 데이터 확인
		logger.info("order_by >>> : " + bvo.getOrder_by());
		logger.info("order_sc >>> : " + bvo.getOrder_sc());
		
		// 검색에 대한 데이터 확인
		logger.info("search >>> : " + bvo.getSearch());
		logger.info("keyword >>> : " + bvo.getKeyword());
		
		// 페이지 세팅
		Paging.setPage(bvo);
		
		// 전체 레코드 수 구현
		int total = boardService.boardListCnt(bvo);
		logger.info("total >>> : " + total);
		
		// 글 번호 재설정
		int count = total - (Util.nvl(bvo.getPage()) - 1) * Util.nvl(bvo.getPageSize());
		logger.info("count >>> : " + count);
		
		
		List<BoardVO> boardList = boardService.boardList(bvo);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("count", count);
		model.addAttribute("total", total);
		model.addAttribute("data", bvo); // >>>?? 리스트로 조회가 될텐데 bvo를 보내는건 무슨소리일까..
		
		return "board/boardList";
	}
	
	// 글 상세보기 구현
	@RequestMapping(value="/boardDetail", method=RequestMethod.GET)
	public String boardDetail(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("(Controller) boardDetail() 진입");
		logger.info("b_num >>> : " + bvo.getB_num());
		
		BoardVO _bvo = new BoardVO();
		_bvo = boardService.boardDetail(bvo);
		
		if(_bvo != null && (!_bvo.equals(""))) {
			_bvo.setB_content(_bvo.getB_content().toString().replaceAll("\n", "<br>"));
		}
		logger.info("_bvo.b_content >>> " + _bvo.getB_content());
		
		model.addAttribute("detail", _bvo);
		
		return "board/boardDetail";
	}
	
	
	// 글쓰기 구현
	@RequestMapping(value="/boardInsert", method=RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo, HttpServletRequest request)
			throws IllegalStateException, IOException{
		logger.info("(Controller) boardInsert() 진입");
		logger.info("fileName >>> : " + bvo.getFile().getOriginalFilename());
		logger.info("b_title >>> : " + bvo.getB_title());
		
		String chaebun = ChaebunUtil.getBoardChaebun("N", chaebunService.getChaebun().getB_num());
		logger.info("chaebun >>> : " + chaebun);
		
		bvo.setB_num(chaebun);
		
		String b_file = FileUploadUtil.fileUpload(bvo.getFile(), request);
		logger.info("b_file >>> : " + b_file);
		bvo.setB_file(b_file);
		
		
		int result = boardService.boardInsert(bvo);
		String url = "";
		if(result == 1) {
			url = "/board/boardList.shj";
		}
		// "redirect:넘겨줄 Context경로" >> 뷰페이지가 아닌 Context경로(@RequestMapping)를 반환한다.
		return "redirect:"+url;
	}
	
	// 글수정 폼 출력하기
	@RequestMapping(value="/updateForm",method=RequestMethod.POST)
	public String updateForm(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("(Controller) updateForm() 진입");
		logger.info("b_num >>> : " + bvo.getB_num());
		
		BoardVO updateData = new BoardVO();
		updateData = boardService.boardDetail(bvo);
		
		model.addAttribute("updateData", updateData);
		
		return "board/updateForm";
	}
	
	// 글 수정 구현하기
	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public String boardUpdate(@ModelAttribute BoardVO bvo, HttpServletRequest request)
			throws IllegalStateException, IOException{
		logger.info("(Controller) boardUpdate() 진입");
	
		String url = "";
		String b_file = "";
		
		if(!bvo.getB_file().isEmpty()) {
			FileUploadUtil.fileDelete(bvo.getB_file(), request);
			b_file = FileUploadUtil.fileUpload(bvo.getFile(), request);
			bvo.setB_file(b_file);
		}else {
			logger.info("첨부파일 없음");
			bvo.setB_file("");
		}
		logger.info("b_file >>> : " + bvo.getB_file());
		
		int result = boardService.boardUpdate(bvo);
		logger.info("result >>> :" + result);
		
		if(result==1) {
			url = "/board/boardList.shj"; // 수정 후 목록으로 이동
			// 아래 url은 수정 후 상세페이지로 이동
			// url = "/board/boardDetail.do?b_num="+bvo.getB_num();
		}
		
		return "redirect:"+url;
	}
	
	// 글 삭제 구현하기
	@RequestMapping(value="boardDelete", method=RequestMethod.POST)
	public String boardDelete(@ModelAttribute BoardVO bvo, HttpServletRequest request)
			throws IOException{
		logger.info("(Controller) boardDelete() 진입");

		String url = "";
		int result = boardService.boardDelete(bvo);
		logger.info("result >>> : " + result);
		
		if(result == 1) {
			url = "/board/boardList.shj";
		}
		
		return "redirect:"+url;
	}
	
	// 패스워드 확인
	// @param b_num, @param b_pwd, @return int
	// @ResponseBody 는 전달된 뷰를 통해서 츌력하는 것이 아니라 HTTP Response Body에 직접 출력하는 방식
	@RequestMapping(value="/pwdConfirm", method=RequestMethod.POST)
	@ResponseBody
	public String pwdConfirm(@ModelAttribute BoardVO bvo) {
		logger.info("(Controller) pwdConfirm() 진입");
		
		logger.info("bvo.getB_num >>> : " + bvo.getB_num());
		logger.info("bvo.getB_pwd >>> : " + bvo.getB_pwd());
		
		List<BoardVO> list = boardService.pwdConfirm(bvo);
		int nCnt = list.size();
		logger.info("nCnt >>> : " + nCnt);
		String result = "";
		if(nCnt == 1) {
			result = "1";
		}else {
			result = "0";
		}
		
		logger.info("result >>> : " + result);
		return result;
	}
}
