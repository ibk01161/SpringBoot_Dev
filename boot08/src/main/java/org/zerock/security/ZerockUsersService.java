package org.zerock.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.persistence.MemberRepository;

import lombok.extern.java.Log;

@Service
@Log
public class ZerockUsersService implements UserDetailsService {
	
	@Autowired
	MemberRepository repos;
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	
//		User sampleUser = new User(username, "{noop}1111", Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//		return sampleUser;
//		
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		//repos.findById(username).ifPresent(member -> log.info("" + member));
		
		return repos.findById(username).filter(m -> m != null).map(m -> new ZerockSecurityUser(m)).get();
		
	}

}
