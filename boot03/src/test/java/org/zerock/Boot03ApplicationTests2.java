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
import org.zerock.persistence.BoardRepository_Queryano;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot03ApplicationTests2 {

	@Autowired
	private BoardRepository_Queryano repos;
	
	//1. 제목에 대한 검색 처리
	@Test
	public void testByTitle() {
		repos.findByTitle("17").forEach(board -> System.out.println(board));
	}
	
	//2. 내용에 대한 검색 처리_@Param
	@Test
	public void testByContent() {
		repos.findByContent("19").forEach(board -> System.out.println(board));
	}
	
	//3. 작성자에 대한 검색 처리_#{#entityName}
	@Test
	public void testByWriter() {
		repos.findByWriter("user09").forEach(board -> System.out.println(board));
	}
}
