package gmc.project.connectversev3.authservice.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

	private String authUrl;
	
	private String jwtSecret;
	
	private String issuer;
	
	private Long expeiry;

	private Long refreshToken;
	
}
