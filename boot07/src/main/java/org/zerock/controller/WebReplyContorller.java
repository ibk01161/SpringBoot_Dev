package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	@PostMapping("/{bno}")
	public ResponseEntity<Void> addReply(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		
		log.info("========================================");
		log.info("addReply.....................");
		log.info("addReply()_BNO : " + bno);
		log.info("addReply()_reply : " + reply);
		log.info("========================================");
		
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}

}
