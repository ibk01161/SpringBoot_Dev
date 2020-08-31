package org.zerock.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

	// uid가 'user1'인 회원의 정보와 회원의 프로필 사진 숫자 (@Query)
	@Query("SELECT m.uid, count(p) FROM Member m LEFT OUTER JOIN Profile p ON m.uid = p.member WHERE m.uid = ?1 GROUP BY m")
	public List<Object[]> getMemberWithProfileCount(String uid);
	
	// 회원 정보와 현재 사용 중인 프로필에 대한 정보
	@Query("SELECT m, p FROM Member m LEFT OUTER JOIN Profile p ON m.uid = p.member WHERE m.uid = ?1 AND p.currnet = true")
	public List<Object[]> getMemberWithProfile(String uid);
	
	@Query("SELECT m, p FROM Member m JOIN Profile p ON m.uid = p.member WHERE m.uid = ?1")
	public List<Object[]> getJoin(String uid);
}
