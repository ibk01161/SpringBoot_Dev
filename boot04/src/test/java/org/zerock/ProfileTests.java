package org.zerock;

import java.util.Arrays;
import java.util.List;
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
	
	// uid가 'user1'인 회원의 정보와 회원의 프로필 사진 숫자 (@Query)
	@Test
	public void testFetchJoin1() {
		List<Object[]> result = memberRepos.getMemberWithProfileCount("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
		
	}
	
	// 회원 정보와 현재 사용 중인 프로필에 대한 정보
	@Test
	public void testFetchJoin2() {
		List<Object[]> result = memberRepos.getMemberWithProfile("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	@Test
	public void testJoin() {
		List<Object[]> result = memberRepos.getJoin("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}

}
