package gmc.project.connectversev3.prophetservice.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

	private String host;
	
	private Integer port;
	
	private String username;
	
	private String password;
	
}
