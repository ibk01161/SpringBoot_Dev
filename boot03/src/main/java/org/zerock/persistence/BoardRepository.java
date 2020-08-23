package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{

	//1. Board테이블에 있는 title로 select 하기
	public List<Board> findBoardByTitle(String title);
	
	//2. findBy를 이용한 특정 컬럼 찾기_작성자 이름으로 select하기
	public Collection<Board> findByWriter(String writer);
	
	//3. like구문 처리_작성자에 대한 like % 키워드 %
	public Collection<Board> findByWriterContaining(String writer);
	
	//4. and 혹은 or 조건처리_title과 content속성에 특정한 문자열이 들어있는 게시물 검색
	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	//5. 부등호 처리_title LIKE % ? % AND BNO > ?
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, Long num);
	
	//6. Order by 처리 (bno > ? ORDER BY bno DESC)
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	//7_1. 기본적인 페이징 처리 (bno > 0 ORDER BY DESC limit ?,?)
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);
	
	//7_2. 정렬 처리 (Pageable 인터페이스 적용)
	//public List<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	//8. 정렬 처리 (Pageable 인터페이스 적용, Page<T>타입 적용)
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
}
