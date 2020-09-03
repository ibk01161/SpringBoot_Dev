package org.zerock.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.PDSBoard;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {
	
	// 첨부파일 수정
	@Modifying
	@Query("UPDATE FROM PDSFile f set f.pdsfile = ?2 WHERE f.fno = ?1 ")
	public int updatePDSFile(Long fno, String newFileName);
	
	// 첨부파일 삭제
	@Modifying
	@Query("DELETE FROM PDSFile f where f.fno = ?1")
	public int deletePDSFile(Long fno);

}
