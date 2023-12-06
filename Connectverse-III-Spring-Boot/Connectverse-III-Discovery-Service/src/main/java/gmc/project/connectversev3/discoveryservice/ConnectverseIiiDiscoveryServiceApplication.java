package gmc.project.connectversev3.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ConnectverseIiiDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectverseIiiDiscoveryServiceApplication.class, args);
	}

}
