package gmc.project.connectversev3.userservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("Connectverse-Job-Service")
public interface JobServiceFeighClient {

	@GetMapping(path = "/job/get/10/jobs")
	public ResponseEntity<String> sendTenJobs();
	
	@GetMapping(path = "/scheme/{schemeId}/apply/{employeeId}")
	public ResponseEntity<String> applyForScheme(@PathVariable String schemeId, @PathVariable String employeeId);

}
