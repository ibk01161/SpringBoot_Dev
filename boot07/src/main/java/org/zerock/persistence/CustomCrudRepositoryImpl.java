package org.zerock.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.domain.WebBoard;

import lombok.extern.java.Log;

@Log
public class CustomCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomWebBoard {

	public CustomCrudRepositoryImpl() {
		super(WebBoard.class);
	}

	@Override
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page) {
		
		log.info("=======================");
		log.info("TYPE : " + type);
		log.info("KEYWORD : " + keyword);
		log.info("PAGE : " + page);
		log.info("=======================");
		
		return null;
	}

}
