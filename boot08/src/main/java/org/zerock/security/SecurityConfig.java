package org.zerock.security;

import javax.sql.DataSource;

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
	
	@Autowired
	DataSource dataSource;
	
	// 인증에 대한 처리 (메모리)
	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * 
	 * log.info("build Auth global............");
	 * 
	 * auth.inMemoryAuthentication().withUser("manager").password("1111").roles(
	 * "MANAGER");
	 * 
	 * }
	 */
	
	// 인증에 대한 처리 (JDBC)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		log.info("build Auth global...............");
		
		String query1 = "SELECT uid username, CONCAT('{noop}', upw) password, true enabled FROM tbl_members WHERE uid = ?";
		String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";
		
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query1).rolePrefix("ROLE_").authoritiesByUsernameQuery(query2);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config................");
		
		http.authorizeRequests().antMatchers("/guest/**").permitAll();
		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		
		// 권한이 없으면 로그인 페이지로
		http.formLogin().loginPage("/login");
		
		// 권한 없음을 알려주고 로그인 화면으로 이동 (admin으로 이동할 때)
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		
		// 세션 무효화
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
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
