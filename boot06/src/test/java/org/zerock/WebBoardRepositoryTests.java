package org.zerock;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebBoardRepositoryTests {

	@Autowired
	WebBoardRepository repos;
	
	// 1부터 300까지 bno 값을 가지는 테스트 데이터 생성 
	@Test
	public void insertDummies() {
		
		IntStream.range(0, 300).forEach(i -> {
			
			WebBoard board = new WebBoard();
			
			board.setTitle("Sample Board Title " + i);
			board.setContent("content Sample..." + i + " of Board ");
			board.setWriter("user0" + (i%10));
			
			repos.save(board);
			
		});
		
	}
	
	// 페이징 처리
	@Test
	public void testList1() {
		
		Pageable pageable = PageRequest.of(0, 20, Direction.DESC, "bno");
		
		Page<WebBoard> result = repos.findAll(repos.makePredicate(null, null), pageable);
		
		log.info("------------------------------------------------------------------");
		log.info("PAGE : " + result.getPageable());
		log.info("------------------------------------------------------------------");
		
		result.getContent().forEach(board -> log.info("" + board));
	}
	
	// 페이징, 검색 조건에 대한 처리
	@Test
	public void testList2() {
		
		Pageable pageable = PageRequest.of(0, 20, Direction.DESC, "bno");
		
		Page<WebBoard> result = repos.findAll(repos.makePredicate("t", "10"), pageable);
		
		log.info("------------------------------------------------------------------");
		log.info("PAGE : " + result.getPageable());
		log.info("------------------------------------------------------------------");
		
		result.getContent().forEach(board -> log.info("" + board));
		
	}
}
