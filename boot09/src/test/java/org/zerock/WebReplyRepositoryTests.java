package org.zerock;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebReplyRepositoryTests {
	
	@Autowired
	WebReplyRepository repos;
	
	// 댓글 더미 데이터 추가
	@Test
	public void testInsertReplies() {
		
		Long[] arr = {299L, 302L, 303L};
		
		Arrays.stream(arr).forEach(num -> {
			
			WebBoard board = new WebBoard();
			board.setBno(num);
			
			IntStream.range(0, 10).forEach(i -> {
				
				WebReply reply = new WebReply();
				reply.setReplyText("REPLY..." + i);
				reply.setReplyer("replyer" + i);
				reply.setBoard(board);
				
				repos.save(reply);
				
			});
			
		});
		
	}

}
