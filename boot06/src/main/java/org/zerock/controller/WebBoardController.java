package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;
import org.zerock.vo.PageMaker;
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
	/*@GetMapping("/list")
	public void list(PageVO vo, Model model) {
		
		Pageable page = vo.makePageable(0, "bno");
		
		Page<WebBoard> result = repos.findAll(repos.makePredicate(null, null), page);
		
		log.info("------------------------------------------------------------------");
		log.info("page : " + page);
		log.info("result : " + result);
		log.info("------------------------------------------------------------------");
		
		model.addAttribute("result", result);
		
	}
	*/
	
	// 리스트 출력_페이징 처리(PageMaker 사용) 및 검색 처리
	@GetMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo, Model model) {
		
		Pageable page = vo.makePageable(0, "bno");
		
		Page<WebBoard> result = repos.findAll(repos.makePredicate(vo.getType(), vo.getKeyword()), page);
		
		log.info("------------------------------------------------------------------");
		log.info("page : " + page);
		log.info("result : " + result);
		log.info("TOTAL PAGE NUMBER : " + result.getTotalPages());
		log.info("------------------------------------------------------------------");
		
		model.addAttribute("result", new PageMaker(result));
		
	}
	
	// 게시물 등록화면
	@GetMapping("/register")
	public void register(@ModelAttribute("vo") WebBoard vo) {
		
		log.info("========================================");
		log.info("register get............");
		log.info("========================================");
		
	}
	
	// 게시물 등록
	@PostMapping("/register")
	public String register(@ModelAttribute("vo") WebBoard vo, RedirectAttributes rttr) {
		
		log.info("========================================");
		log.info("register post............");
		log.info("" + vo);
		log.info("========================================");
		
		repos.save(vo);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/boards/list";
		
	}
}
