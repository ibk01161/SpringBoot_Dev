package org.zerock;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Member;
import org.zerock.domain.Profile;
import org.zerock.persistence.MemberRepository;
import org.zerock.persistence.ProfileRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit		// 테스트 결과 commit
public class ProfileTests {
	
	@Autowired
	MemberRepository memberRepos;
	
	@Autowired
	ProfileRepository profileRepos;
	
	@Test
	public void testInsertMembers() {
		IntStream.range(1, 101).forEach(i -> {
			Member member = new Member();
			member.setUid("user" + i);
			member.setUpw("pw" + i);
			member.setUname("사용자" + i);
			
			memberRepos.save(member);
		});
	}
	
	@Test
	public void testInsertProfile() {
		Member member = new Member();
		member.setUid("user1");
		
		for (int i = 1; i < 5; i++) {
			Profile profile = new Profile();
			profile.setFname("face" + i + "jpg");
			
			if (i == 1) {
				profile.setCurrnet(true);
			}
			profile.setMember(member);
			
			profileRepos.save(profile);
		}
	}

}
