package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 인증에 대한 처리
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		log.info("build Auth global............");
		
		auth.inMemoryAuthentication().withUser("manager").password("1111").roles("MANAGER");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config................");
		
		http.authorizeRequests().antMatchers("/guest/**").permitAll();
		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		
		// 권한이 없으면 로그인 페이지로
		http.formLogin();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new PasswordEncoder() {
			
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}
			
		};
		
	}

}
