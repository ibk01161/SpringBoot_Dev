package org.zerock.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;

public interface BoardRepository_Queryano extends CrudRepository<Board, Long>{

	//1. 제목에 대한 검색 처리
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);
	
	//2. 내용에 대한 검색 처리_@Param
	@Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByContent(@Param("content") String content);
	
	//3. 작성자에 대한 검색 처리_#{#entityName}
	@Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	List<Board> findByWriter(String writer);
	
	//4. 필요 컬럼만 추출하는 경우 (content 컬럼 제외)
	@Query("SELECT b.bno, b.title, b.writer, b.regdate FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);
	
	//5. nativeQuery의 사용
	@Query(value="select bno, title, writer from tbl_boards where title like "
			+ "CONCAT('%', ?1, '%') and bno > 0 order by bno desc", nativeQuery=true)
	List<Object[]> fintByTitle3(String title);
	
	//6. @Query와 Paging 처리/정렬
	@Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findBypage(Pageable page);
}
