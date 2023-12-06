package gmc.project.connectversev3.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConnectverseIiiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectverseIiiGatewayServiceApplication.class, args);
	}

}
