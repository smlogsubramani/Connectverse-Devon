package gmc.project.connectversev3.jobservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.connectversev3.jobservice.models.JobCreateOrUpdateModel;
import gmc.project.connectversev3.jobservice.models.JobModel;
import gmc.project.connectversev3.jobservice.models.ListJobModel;
import gmc.project.connectversev3.jobservice.services.JobService;

@RestController
@RequestMapping(path = "/job")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping
	private ResponseEntity<List<ListJobModel>> getAllJobs() {
		List<ListJobModel> returnValue = jobService.getAllJobs();
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{jobId}")
	private ResponseEntity<JobModel> getAJob(@PathVariable String jobId) {
		JobModel returnValue = jobService.getAJob(Long.valueOf(jobId));
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{jobId}/apply/{employeeId}")
	public ResponseEntity<String> applyForJob(@PathVariable String jobId, @PathVariable String employeeId) {
		jobService.applyForJob(Long.valueOf(jobId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Your Profile has been sent to the Employer...");
	}
	
	@GetMapping(path = "/{jobId}/apply-from-mobile/{employeeId}")
	private ResponseEntity<String> applyForJobThroughMobile(@PathVariable String jobId, @PathVariable String employeeId) {
		jobService.applyJobThroughSMS(Long.valueOf(jobId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("JobApplied...");
	}
	
	@GetMapping(path = "/{jobId}/accept/{employeeId}")
	private ResponseEntity<String> acceptJoiningRequest(@PathVariable String jobId, @PathVariable String employeeId) {
		jobService.acceptJoiningRequest(Long.valueOf(jobId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Profile has been selected for further Steps...");
	}
	
	@GetMapping(path = "/{jobId}/reject/{employeeId}/{reason}")
	private ResponseEntity<String> rejectJoiningRequest(@PathVariable String jobId, @PathVariable String employeeId, @PathVariable String reason) {
		jobService.rejectJoiningRequest(Long.valueOf(jobId), employeeId, reason);
		return ResponseEntity.status(HttpStatus.OK).body("Profile declined...");
	}
	
	@GetMapping(path = "/{jobId}/{employeeId}/{employerId}/jobReport/{points}")
	private ResponseEntity<String> jobReport(@PathVariable String jobId, @PathVariable String employeeId, @PathVariable String employerId, @PathVariable String points) {
		jobService.reportJob(Long.valueOf(jobId), employeeId, employerId, points);
		return ResponseEntity.status(HttpStatus.OK).body("Employer Reported...");
	}
	
	@PostMapping
	private ResponseEntity<String> createOrUpdateJob(@RequestBody JobCreateOrUpdateModel jobCreateOrUpdateModel) {
		jobService.createOrUpdateJob(jobCreateOrUpdateModel);
		return ResponseEntity.status(HttpStatus.OK).body("Job Saved Successfully...");
	}
	
	@GetMapping(path = "/get/10/jobs")
	public ResponseEntity<String> sendTenJobs() {
		jobService.sendTenJobSuggestions();
		return ResponseEntity.status(HttpStatus.OK).body("Job sent Successfully...");
	}
	
	@GetMapping(path = "contractor/get/10/jobs/{employeeId}")
	public ResponseEntity<List<ListJobModel>> sendTenJobs(@PathVariable String employeeId) {
		List<ListJobModel> returnValue = jobService.getTenJobForEmployee(employeeId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}

}
