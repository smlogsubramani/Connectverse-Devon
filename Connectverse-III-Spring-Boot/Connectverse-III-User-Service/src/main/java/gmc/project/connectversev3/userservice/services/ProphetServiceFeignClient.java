package gmc.project.connectversev3.userservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("Connectverse-Prophet-Service")
public interface ProphetServiceFeignClient {

	@GetMapping(value = "/sms/sendSMS/{mobileNumber}/{message}")
	public ResponseEntity<String> sendSMS(@PathVariable String mobileNumber, @PathVariable String message);
	
}
