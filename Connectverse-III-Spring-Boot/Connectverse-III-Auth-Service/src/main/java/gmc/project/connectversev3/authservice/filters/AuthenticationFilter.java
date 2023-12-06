package gmc.project.connectversev3.authservice.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import gmc.project.connectversev3.authservice.configurations.AuthConfig;
import gmc.project.connectversev3.authservice.entities.EmployeeEntity;
import gmc.project.connectversev3.authservice.entities.EmployerEntity;
import gmc.project.connectversev3.authservice.models.LoginRequestModel;
import gmc.project.connectversev3.authservice.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthService authService;	
	private final AuthConfig authConfig;

	public AuthenticationFilter(AuthService authService, AuthConfig authConfig,
			AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.authService = authService;
		this.authConfig = authConfig;
	}
			
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginRequestModel creds;
		
		try {
			creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			return getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(
									creds.getUserName(),
									creds.getPassword()
								)
					);
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		byte[] secret = authConfig.getJwtSecret().getBytes(StandardCharsets.UTF_8);
		Key hmacKey = new SecretKeySpec(secret, SignatureAlgorithm.HS256.getJcaName());
		String userName = ((User)authResult.getPrincipal()).getUsername();
		try {
			EmployeeEntity foundUser = authService.findEmployeeByUserName(userName);
			String token = Jwts.builder()
					.setIssuer(authConfig.getIssuer())
					.setExpiration(new Date(System.currentTimeMillis() + authConfig.getExpeiry()))
					.setSubject(userName)
					.signWith(hmacKey)
					.compact();
			String refreshToken = Jwts.builder()
					.setIssuer(authConfig.getIssuer())
					.setExpiration(new Date(System.currentTimeMillis() + authConfig.getRefreshToken()))
					.setSubject(userName)
					.signWith(hmacKey)
					.compact();
			response.setHeader("Authorization", "GMC "+token);
			response.setHeader("Refresh_Token", "GMC "+refreshToken);
			response.setHeader("Employee_Id", foundUser.getId());
		} catch(UsernameNotFoundException e) {
			EmployerEntity foundUser = authService.findEmployerByUserName(userName);
			String token = Jwts.builder()
					.setSubject(userName)
					.setIssuer(authConfig.getIssuer())
					.setExpiration(new Date(System.currentTimeMillis() + authConfig.getExpeiry()))
					.signWith(hmacKey)
					.compact();
			String refreshToken = Jwts.builder()
					.setIssuer(authConfig.getIssuer())
					.setExpiration(new Date(System.currentTimeMillis() + authConfig.getRefreshToken()))
					.setSubject(userName)
					.signWith(hmacKey)
					.compact();
			response.setHeader("Authorization", "GMC "+token);
			response.setHeader("Refresh_Token", "GMC "+refreshToken);
			response.setHeader("Employer_Id", foundUser.getId());
		}
	}

}
