package org.zerock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.persistencs.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {
	
	@Autowired
	private BoardRepository boardRepo;
	
	// 등록 테스트
	@Test
	public void testInsert() {
		Board board = new Board();
		board.setTitle("제목1");
		board.setContent("내용1");
		board.setWriter("user00");
		
		System.out.println("Create...........");
		boardRepo.save(board);
	}

	// 조회 테스트
	@Test
	public void testRead() {
		System.out.println("Select......");
		boardRepo.findById(2L).ifPresent((board)->{
			System.out.println("result======> " + board);
		});
	}
	
	// 수정 테스트
	@Test
	public void testUpdate() {
		System.out.println("Read First..................");
		Board board = boardRepo.findById(2L).get();
		
		System.out.println("Update Title..................");
		board.setTitle("수정제목1");
		
		System.out.println("Call Save()..................");
		boardRepo.save(board);
	}
	
	// 삭제 테스트
	@Test
	public void testDelete() {
		System.out.println("DELETE Entity..........");
		boardRepo.deleteById(2L);
	}

}
