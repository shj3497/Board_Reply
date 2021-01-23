package com.spring.reply.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.common.chaebun.ChaebunUtil;
import com.spring.common.chaebun.service.ChaebunService;
import com.spring.reply.service.ReplyService;
import com.spring.reply.vo.ReplyVO;

/*
 * 참고 : @RestController(@Controller + @ReponseBody) 오호.. @ResponseBody는 ajax통신할 때 사용
 * 기존의 특정한 JSP와 같은 뷰를 만들어 내는 것이 목적이 아닌 REST 방식의 데이터 처리를
 * 위해서 사용하는(데이터 자체를 반환) 어노테이션이다.
 * */

@Controller
@RequestMapping(value="/replies")
public class ReplyController {

	private Logger logger = Logger.getLogger(ReplyController.class);
	
	@Autowired(required=false)
	private ReplyService replyService;
	
	@Autowired(required=false)
	private ChaebunService chaebunService;
	
	@Autowired(required=false)
	public ReplyController(ReplyService replyService,
						   ChaebunService chaebunService) {
		this.replyService=replyService;
		this.chaebunService=chaebunService;
	}
	
	/*
	 * 댓글 글 목록 구현하기
	 * @return List<ReplyVO>
	 * 참고 : @PathVariable은 URI 경로에서 원하는 데이터를 추출하는 용도로 사용.
	 * 		 ResponseEntity 타입은 개발자가 직접 결과 데이터와 HTTP의 상태코드를 직접 제어할 수 있는 클래스이다.
	 * 		 404나 500같은 상태코드를  전송하고 싶은 데이터와 함께 전송 할 수 있기때문에 좀더 세밀한 제어가 가능하다. 
	 * */
	@RequestMapping(value="/all/{b_num}.shj", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity <List<ReplyVO>> list(@PathVariable("b_num") String b_num){
		
		logger.info("(Controller) list 진입");
		
		ResponseEntity<List<ReplyVO>> entity = null;
		try {
			entity = new ResponseEntity<>(replyService.replyList(b_num), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*
	 * 댓글 글쓰기 구현하기
	 * @return String
	 * 참고 : @RequestBody >> : jsp에서 ajax통신을 JSON데이터타입으로 한다면 @RequesyBody로 요청을 받고
	 * 						   @ResponseBody로 넘겨줘야함
	 */
	@RequestMapping(value="/replyInsert")
	@ResponseBody
	public ResponseEntity <String> replyInsert(@RequestBody ReplyVO rvo){
		
		logger.info("(Controller) replyInsert() 진입");
		
		ResponseEntity<String> entity = null;
		int result;
		String chaebun = ChaebunUtil.getBoardChaebun_r("N", chaebunService.getChaebun_r().getR_num());
		
		rvo.setR_num(chaebun);
		try {
			result = replyService.replyInsert(rvo);
			logger.info("result >>> : " + result);
			if(result == 1) {
				logger.info("정상적으로 댓글 등록");
				entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// ResponseEntity가 Model 같은 역할을 하는것같은데..
		return entity;
	}
	
	/*
	 * 댓글 수정 구현하기
	 * @return
	 * 참고 : REST 방식에서 Update 작업은 PUT, PATCH 방식을 이용해서 처리한다.!!!!
	 * 		전체 데이터를 수정하는 경우에는 PUT을 이용하고
	 * 		일부의 데이터를 수정하는 경우에는 PATCH를 이용한다.
	 */
	@RequestMapping(value="/{r_num}.shj", method= {RequestMethod.POST, RequestMethod.PATCH})
	@ResponseBody
	public ResponseEntity<String> replyUpdate(@PathVariable("r_num") String r_num, 
											  @RequestBody ReplyVO rvo){
		
		logger.info("(Controller) replyUpdate() 진입");
		ResponseEntity<String> entity = null;
		
		try {
			rvo.setR_num(r_num);
			replyService.replyUpdate(rvo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/*
	 * 댓글 삭제 구현하기
	 * @return
	 * 참고 : REST 방식에서 DELETE 작업은 DELETE 방식을 이용해서 처리
	 * */
	@RequestMapping(value="/{r_num}.shj", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> replyDelete(@PathVariable("r_num") String r_num){
		
		logger.info("(Controller) replyDelete() 진입");
		ResponseEntity<String> entity = null;
		
		try {
			replyService.replyDelete(r_num);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
		
		return entity;
	}
	
	
}
