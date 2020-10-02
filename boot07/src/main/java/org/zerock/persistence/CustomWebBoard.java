package org.zerock.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomWebBoard {
	
	// 페이징 처리와 검색을 통한 댓글 개수 가져오기
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page);

}
