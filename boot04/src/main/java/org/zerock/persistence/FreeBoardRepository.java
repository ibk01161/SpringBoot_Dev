package org.zerock.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.FreeBoard;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long> {
	
	// '게시물 + 댓글의 수'_쿼리메소드(페이징)
	public List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);

	// 제목옆에 댓글 수 출력 (@Query)
	@Query("SELECT b.bno, b.title, count(r) FROM FreeBoard b LEFT OUTER JOIN b.replies r WHERE b.bno > 0 GROUP BY b")
	public List<Object[]> getPage(Pageable page);
}
