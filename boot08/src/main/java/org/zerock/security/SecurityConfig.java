package org.zerock.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ZerockUsersService zerockUsersService;
	
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
	
	// JdbcTokenRepositoryImple 설정 (자동로그인)
	private PersistentTokenRepository getJDBCRepository() {
		
		JdbcTokenRepositoryImpl repos = new JdbcTokenRepositoryImpl();
		repos.setDataSource(dataSource);
		return repos;
		
	}
	
	// 인증에 대한 처리 (메모리)
//	@Autowired 
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	  
//		log.info("build Auth global............");
//	  
//		auth.inMemoryAuthentication().withUser("manager").password("1111").roles("ADMIN");
//	  
//	}
	 
	
	// 인증에 대한 처리 (JDBC)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		log.info("build Auth global...............");
		
		// passwordEncoder() 사용시 {noop} 제외
//		String query1 = "SELECT uid username, CONCAT('{noop}', upw) password, true enabled FROM tbl_members WHERE uid = ?";
		String query1 = "SELECT uid username, upw password, true enabled FROM tbl_members WHERE uid = ?";
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
		
		// ZerockUsersService 이용
		//http.userDetailsService(zerockUsersService);
		
		// ZerockUsersService 이용 & remeber-me 설정
		//http.rememberMe().key("zerock").userDetailsService(zerockUsersService);
		
		// remeber-me 설정 (JdbcTokenRepository 이용)
		http.rememberMe().key("zerock").userDetailsService(zerockUsersService).tokenRepository(getJDBCRepository()).tokenValiditySeconds(60*60*24);
	}
	
	

}
