package org.zerock;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	
}
