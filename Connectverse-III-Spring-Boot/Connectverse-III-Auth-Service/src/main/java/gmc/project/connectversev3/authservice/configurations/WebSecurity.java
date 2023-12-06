package gmc.project.connectversev3.authservice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import gmc.project.connectversev3.authservice.filters.AuthenticationFilter;
import gmc.project.connectversev3.authservice.services.AuthService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthConfig authConfig;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.headers().frameOptions().disable()
		.and()
			.csrf().disable();
		
		http.addFilter(getAuthFilter());
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	private AuthenticationFilter getAuthFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authService, authConfig, authenticationManager());
		authenticationFilter.setFilterProcessesUrl(authConfig.getAuthUrl());
		return authenticationFilter;
	}

}
