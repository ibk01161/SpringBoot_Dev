package org.zerock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class MemberTests {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberRepository repos;
	
	// 테스트 데이터 추가
	@Test
	public void testInsert() {
		
		for(int i = 0; i <= 100; i++) {
			
			Member member = new Member();
			member.setUid("user" + i);
			member.setUpw("pw" + i);
			member.setUname("사용자" + i);
			
			MemberRole role = new MemberRole();
			if (i <= 80) {
				role.setRoleName("BASIC");
			} else if (i <= 90) {
				role.setRoleName("MANAGER");
			} else {
				role.setRoleName("ADMIN");
			}
			member.setRoles(Arrays.asList(role));
			
			repos.save(member);
			
		}
		
	}
	
	// 조회 작업
	@Test
	public void testRead() {
		
		Optional<Member> result = repos.findById("user85");
		
		result.ifPresent(member -> log.info("member" + member));
		
	}
	
	// 암호화 update
	@Test
	public void testUpdateOldMember() {
		
		List<String> ids = new ArrayList<>();
		
		for (int i = 0; i <= 100; i++) {
			ids.add("user" + i);
		}
		
		repos.findAllById(ids).forEach(member -> {
			member.setUpw(passwordEncoder.encode(member.getUpw()));
			repos.save(member);
		});
		
	}

}
