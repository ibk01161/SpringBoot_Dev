package org.zerock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.PDSBoard;
import org.zerock.domain.PDSFile;
import org.zerock.persistence.PDSBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class PDSBoardTests {

	@Autowired
	PDSBoardRepository repos;
	
	// 등록 메소드
	@Test
	public void testInsertPDS() {
		PDSBoard pds = new PDSBoard();
		pds.setPname("Document");
		
		PDSFile file1 = new PDSFile();
		file1.setPdsfile("file1.doc");
		
		PDSFile file2 = new PDSFile();
		file2.setPdsfile("file2.doc");
		
		pds.setFiles(Arrays.asList(file1,file2));
		
		log.info("try to save pds");
		
		repos.save(pds);
	}
	
	// 첨부파일 수정
	@Transactional
	@Test
	public void testUpdateFileName1() {
		Long fno = 3L;
		String newName = "updateFile1.doc";
		
		int count = repos.updatePDSFile(fno, newName);
		
		log.info("update count : " + count);
	}
	
	// 전통적인 방식으로 첨부파일 수정하기
	@Transactional
	@Test
	public void testUpdateFileName2() {
		String nweName = "updateFile222.doc";
		// 반드시 번호가 있는지 확인
		Optional<PDSBoard> result = repos.findById(2L);
		
		result.ifPresent(pds -> {
			log.info("데이터가 존재하므로 update 시도");
			
			PDSFile target = new PDSFile();
			target.setFno(4L);
			target.setPdsfile(nweName);
			
			int idx = pds.getFiles().indexOf(target);
			
			if (idx > -1) {
				log.info("idx : " + idx);
				List<PDSFile> list = pds.getFiles();
				list.remove(idx);
				list.add(target);
				pds.setFiles(list);
			}
			repos.save(pds);
		});
	}
	
	// 첨부파일 삭제
	@Transactional
	@Test
	public void deletePDSFile() {
		// 첨부파일 번호
		Long fno = 3L;
		
		int count = repos.deletePDSFile(fno);
		log.info("DELETE PDSFILE : " + count);
	}
	
	// tbl_pds, tbl_pdsfiles 테스트 데이터 추가
	@Test
	public void insertDummies() {
		List<PDSBoard> list = new ArrayList<>();
		
		IntStream.range(1, 100).forEach(i -> {
			
			PDSBoard pds = new PDSBoard();
			pds.setPname("자료 " + i);
			
			PDSFile file1 = new PDSFile();
			file1.setPdsfile("file1.doc");
			
			PDSFile file2 = new PDSFile();
			file2.setPdsfile("file2.doc");
			
			pds.setFiles(Arrays.asList(file1, file2));
			
			log.info("try to save pds");
			
			list.add(pds);
		});
		
		repos.saveAll(list);
	}
	
	// 자료와 첨부 파일의 수를 자료 번호의 역순으로 출력
	@Test
	public void viewSummary() {
		repos.getSummary().forEach(arr -> log.info(Arrays.toString(arr)));
	}
}
