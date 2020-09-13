package org.zerock.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {

	// 리스트 출력
	/*@GetMapping("/list")
	public void list() {
		log.info("list() called ...........");
	}*/

	// 리스트 출력_페이징 처리(@PageableDefault 어노테이션 사용)
	/*@GetMapping("/list")
	public void list(@PageableDefault(direction=Sort.Direction.DESC, sort = "bno", size = 10, page = 0) Pageable page) {
		
		log.info("------------------------------------------------------------------");
		log.info("list() called....." + page);
		log.info("------------------------------------------------------------------");
		
	}*/
	
	// 리스트 출력_페이징 처리(PageVO 사용)
	@GetMapping("/list")
	public void list(PageVO vo) {
		
		Pageable page = vo.makePageable(0, "bno");
		
		log.info("------------------------------------------------------------------");
		log.info("list() called....." + page);
		log.info("------------------------------------------------------------------");
		
	}
	
	
}
