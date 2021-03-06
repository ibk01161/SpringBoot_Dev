package org.zerock.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/replies/*")
@Log
public class WebReplyContorller {
	
	@Autowired
	private WebReplyRepository replyrepos;
	
	// 댓글 등록
	@Secured(value= {"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@Transactional
	@PostMapping("/{bno}")
	public ResponseEntity<List<WebReply>> addReply(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		
		log.info("========================================");
		log.info("addReply.....................");
		log.info("addReply()_BNO : " + bno);
		log.info("addReply()_reply : " + reply);
		log.info("========================================");
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		reply.setBoard(board);
		
		replyrepos.save(reply);
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
		
	}
	
	// 댓글 삭제
	@Secured(value= {"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> remove(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
		
		log.info("========================================");
		log.info("delete reply : " + rno);
		log.info("========================================");
		
		replyrepos.deleteById(rno);
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
		
	}
	
	// 댓글 수정
	@Secured(value= {"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@Transactional
	@PutMapping("/{bno}")
	public ResponseEntity<List<WebReply>> modify(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		
		log.info("========================================");
		log.info("modifyReply : " + reply);
		log.info("========================================");
		
		replyrepos.findById(reply.getRno()).ifPresent(origin -> {
			
			origin.setReplyText(reply.getReplyText());
			
			replyrepos.save(origin);
			
		});
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
		
	}
	
	// 댓글 목록
	@GetMapping("/{bno}")
	public ResponseEntity<List<WebReply>> getReplies(@PathVariable("bno") Long bno) {
		
		log.info("get All Replies...............");
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
		
	}

	// 댓글 리스트 조회 메소드
	private List<WebReply> getListByBoard(WebBoard board) throws RuntimeException {
		
		log.info("getListByBoard....." + board);
		
		return replyrepos.getRepliesOfBoard(board);
	}
	
	
	

}
