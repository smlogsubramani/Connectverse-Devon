package gmc.project.connectversev3.gatewayservice.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter> {
	
	private final Environment env;
	
	public AuthorizationFilter(Environment env) {
		super(AuthorizationFilter.class);
		this.env = env;
	}

	@Override
	public GatewayFilter apply(AuthorizationFilter config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, "No Authorization Header...", HttpStatus.UNAUTHORIZED);
			
			String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String token = authHeader.replace("GMC", "");
			log.info("The Extracyed Token: {}.", token);
			
			if(!isValid(token)) {
				log.error("Token is In-valid.");
				return onError(exchange, "In Valid JWT...", HttpStatus.UNAUTHORIZED);
			}
			
			return chain.filter(exchange);
		};
	}
	
	Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	private boolean isValid(String jwt) {
		boolean isValid = true;		
		byte[] secret = env.getProperty("jwt.token.secret").getBytes(StandardCharsets.UTF_8);
		Key hamacKey = new SecretKeySpec(secret, SignatureAlgorithm.HS512.getJcaName());
		
		Jws<Claims> claims = null;
		
		try {
			
			claims = Jwts.parserBuilder().setSigningKey(hamacKey)
	                .build().parseClaimsJws(jwt);
			
		} catch (Exception e) {
			isValid = false;
		}
		
		if(claims.getBody().getSubject() == null)
			isValid = false;

		log.info("Token is valid: {}.", isValid);

		return isValid;
	}

}
