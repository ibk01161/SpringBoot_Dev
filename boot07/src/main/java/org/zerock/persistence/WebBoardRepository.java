package org.zerock.persistence;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.QWebBoard;
import org.zerock.domain.WebBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>,
													QuerydslPredicateExecutor<WebBoard> {
	
	// default 메소드_페이징,검색 메소드 구현
	public default Predicate makePredicate(String type, String keyword) {
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QWebBoard board = QWebBoard.webBoard;
		
		// bno > 0 조건
		builder.and(board.bno.gt(0));
		
		if(type == null) {
			return builder;
		}
		
		switch(type) {
		case "t" : 
			builder.and(board.title.like("%" + keyword + "%"));
			break;
		case "c" : 
			builder.and(board.content.like("%" + keyword + "%"));
			break;
		case "w" : 
			builder.and(board.writer.like("%" + keyword + "%"));
			break;
		}
		
		return builder;
		
	}
	

}
