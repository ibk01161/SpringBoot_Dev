package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;
import org.zerock.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {
	
	@Autowired
	private WebBoardRepository repos;

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
	/*@GetMapping("/list")
	public void list(PageVO vo) {
		
		Pageable page = vo.makePageable(0, "bno");
		
		log.info("------------------------------------------------------------------");
		log.info("list() called....." + page);
		log.info("------------------------------------------------------------------");
		
	}*/
	
	// 리스트 출력_페이징 처리(Repository와 연동 처리)
	@GetMapping("/list")
	public void list(PageVO vo, Model model) {
		
		Pageable page = vo.makePageable(0, "bno");
		
		Page<WebBoard> result = repos.findAll(repos.makePredicate(null, null), page);
		
		log.info("------------------------------------------------------------------");
		log.info("page : " + page);
		log.info("result : " + result);
		log.info("------------------------------------------------------------------");
		
		model.addAttribute("result", result);
		
	}
	
}
