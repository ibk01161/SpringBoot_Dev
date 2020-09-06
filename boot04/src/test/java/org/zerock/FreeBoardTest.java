package org.zerock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.FreeBoard;
import org.zerock.domain.FreeBoardReply;
import org.zerock.persistence.FreeBoardReplyRepository;
import org.zerock.persistence.FreeBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class FreeBoardTest {
	
	@Autowired
	FreeBoardRepository boardrepos;
	
	@Autowired
	FreeBoardReplyRepository replyrepos;
	
	// 게시물 데이터 추가
	@Test
	public void insertDummy() {
		
		IntStream.range(1, 200).forEach(i -> {
			
			FreeBoard board = new FreeBoard();
			board.setTitle("Free Board ..." + i);
			board.setContent("Free Content ..." + i);
			board.setWriter("user" + i%10);
			
			boardrepos.save(board);
		});
		
	}
	
	// 댓글 데이터 추가 (양방향)
	@Transactional
	@Test
	public void insertReply2Way() {
		
		Optional<FreeBoard> result = boardrepos.findById(199L);
		
		result.ifPresent(board ->{
			List<FreeBoardReply> replies = board.getReplies();
			FreeBoardReply reply = new FreeBoardReply();
			reply.setReply("REPLY............");
			reply.setReplyer("replyer00");
			reply.setBoard(board);
			
			replies.add(reply);
			
			board.setReplies(replies);
		});
		
	}
	
	// 댓글 데이터 추가 (단방향)
	@Test
	public void insertReply1Way() {
		
		FreeBoard board = new FreeBoard();
		board.setBno(198L);
		
		FreeBoardReply reply = new FreeBoardReply();
		reply.setReply("REPLY.................");
		reply.setReplyer("replyer01");
		reply.setBoard(board);
		
		replyrepos.save(reply);
	}
	
	// '게시물 + 댓글의 수'_쿼리메소드 (페이징)
	@Test
	public void testList1() {
		
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		boardrepos.findByBnoGreaterThan(0L, page).forEach(board -> {
			log.info(board.getBno() + " : " + board.getTitle());
		});
	}
	
	// 제목옆에 댓글 수 출력 (지연, 즉시 로딩)
	@Transactional
	@Test
	public void testList2() {
		
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		boardrepos.findByBnoGreaterThan(0L, page).forEach(board -> {
			log.info(board.getBno() + ": " + board.getTitle() + ": " + board.getReplies().size());
		});
	}

	// 제목옆에 댓글 수 출력 (@Query)
	@Test
	public void testList3() {
		
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
		
		boardrepos.getPage(page).forEach(arr -> log.info(Arrays.toString(arr)));
	}
}
