package org.zerock;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot03ApplicationTests {

	@Autowired
	private BoardRepository repos;
	
	@Test
	public void testInsert200() {
		for (int i = 1; i <= 200; i++) {
			Board board = new Board();
			board.setTitle("제목.." + i);
			board.setContent("내용.." + i);
			board.setWriter("user0" + (i % 10));
			repos.save(board);
		}
	}
	
	//1. Board테이블에 있는 title로 select 하기
	@Test
	public void testByTitle() {
		repos.findBoardByTitle("제목..177").forEach(board -> System.out.println(board));
		
	}
	
	//2. findBy를 이용한 특정 컬럼 찾기_작성자 이름으로 select하기
	@Test
	public void testByWriter() {
		Collection<Board> results = repos.findByWriter("user00");
		
		results.forEach(board -> System.out.println(board));
	}
	
	//3. like구문 처리_작성자에 대한 like % 키워드 %
	@Test
	public void testByWriterContaining() {
		Collection<Board> results = repos.findByWriterContaining("05");
		
		results.forEach(board -> System.out.println(board));
	}

	//5. 부등호 처리_title LIKE % ? % AND BNO > ? => 제목에 5가 포함되어있고, 게시물의 번호가 50보다 큰 데이터 조회
	@Test
	public void testByTitleAndBno() {
		Collection<Board> results = repos.findByTitleContainingAndBnoGreaterThan("5", 100L);
		
		results.forEach(board -> System.out.println(board));
	}
	
	//6. Order by 처리 (bno > ? ORDER BY bno DESC)
	@Test
	public void testBnoOrderby() {
		Collection<Board> results = repos.findByBnoGreaterThanOrderByBnoDesc(100L);
		results.forEach(board -> System.out.println(board));
	}
	
	//7_1. 기본적인 페이징 처리 (bno > 0 ORDER BY DESC limit ?,?)_0부터 10까지 10건 데이터 가져오기
	@Test
	public void testBnoOrderbyPaging() {
		Pageable paging = PageRequest.of(0, 10);
		
		Collection<Board> results = repos.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
		results.forEach(board -> System.out.println(board));
	}
	
	//7_2. 정렬 처리 (Pageable 인터페이스 적용)_bno의 속성값을 순서대로 정렬
	/*@Test
	public void testBnoPagingSort() {
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
		
		Collection<Board> results = repos.findByBnoGreaterThan(0L, paging);
		results.forEach(board -> System.out.println(board));
	}*/
	
	//8. 정렬 처리 (Pageable 인터페이스 적용, Page<T>타입 적용)
	@Test
	public void testBnoPagingSort() {
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
		
		Page<Board> result = repos.findByBnoGreaterThan(0L, paging);
		System.out.println("PAGE SIZE : " + result.getSize());
		System.out.println("TOTAL SIZE : " + result.getTotalPages());
		System.out.println("TOTAL COUNT : " + result.getTotalElements());
		System.out.println("NEXT : " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(board -> System.out.println(board));
	}
}
