package org.zerock.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.extern.java.Log;

@Log
public class PageVO {
	
	// 게시물의 수 (size)
	private static final int DEFAULT_SIZE = 10;
	// 게시물의 수 최대 값 (size)
	private static final int DEFAULT_MAX_SIZE = 50;
	
	private int page;
	private int size;
	
	public PageVO() {
		this.page = 1;
		this.size = DEFAULT_SIZE;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page < 0 ? 1 : page;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		
		this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SIZE ? DEFAULT_SIZE : size;
		
	}
	
	// 전달되는 파라미터를 이용해서 최종적으로 PageRequest로 Pageable 객체 만듬
	public Pageable makePageable(int direction, String... props) {
		
		log.info("=======================================");
		log.info("makePageable................");
		log.info("dirction : " + direction);
		log.info("props : " + props);
		log.info("page : " + this.page);
		log.info("size : " + this.size);
		log.info("=======================================");
		
		Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		return PageRequest.of(this.page -1, this.size, dir, props);
	}

}
