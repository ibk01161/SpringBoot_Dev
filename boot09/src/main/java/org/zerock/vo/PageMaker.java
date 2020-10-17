package org.zerock.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

@Getter
@ToString(exclude="pageList")
@Log
public class PageMaker<T> {
	
	private Page<T> result;
	
	// 페이지 목록의 맨 앞인 '이전' 으로 이동하는데 필요한 정보를 가진 Pageable
	private Pageable prevPage;
	//페이지 목록의 맨 뒤인 '다음' 으로 이동하는데 필요한 정보를 가진 Pageable
	private Pageable nextPage;
	
	// 화면에 보이는 1부터 시작하는 페이지 번호
	private int currentPageNum;
	private int totalPageNum;
	
	// 현재 페이지의 정보를 가진 Pageable
	private Pageable currentPage;
	
	// 페이지 번호의 시작부터 끝까지의 Pageable 들을 저장한 리스트
	private List<Pageable> pageList;

	
	
	public PageMaker(Page<T> result) {
		
		this.result = result;
		this.currentPage = result.getPageable();
		this.currentPageNum = currentPage.getPageNumber() + 1;
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<>();
		
		log.info("======== PageMaker ========");
		log.info("result : " + result);
		log.info("currentPage : " + currentPage);
		log.info("currentPageNum : " + currentPageNum);
		log.info("totalPageNum : " + totalPageNum);
		log.info("pageList : " + pageList);
		
		
		calcPages();
		
	}

	private void calcPages() {
		
		int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0) * 10);
		
		int startNum = tempEndNum - 9;
		
		Pageable startPage = this.currentPage;
		
		// move to start Pageable
		for(int i = startNum; i < this.currentPageNum; i++) {
			startPage = startPage.previousOrFirst();
		}
		this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();
		
		log.info("==================================");
		log.info("tempEndNum : " + tempEndNum);
		log.info("total : " + totalPageNum);
		log.info("==================================");
		
		if (this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;
		}
		
		for (int i = startNum; i <= tempEndNum; i++) {
			pageList.add(startPage);
			startPage = startPage.next();
		}
		this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
		
	}
	
}
