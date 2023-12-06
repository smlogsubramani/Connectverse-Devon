package gmc.project.connectversev3.learningservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.connectversev3.learningservice.models.ProjectCreateOrUpdateModel;
import gmc.project.connectversev3.learningservice.models.ProjectModel;
import gmc.project.connectversev3.learningservice.models.ListProjectModel;
import gmc.project.connectversev3.learningservice.models.NoticeModel;
import gmc.project.connectversev3.learningservice.services.ProjectsService;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

	@Autowired
	private ProjectsService projectsService;
	
	@GetMapping
	private ResponseEntity<List<ListProjectModel>> getAllProjects() {
		List<ListProjectModel> returnValue = projectsService.getAllValidprojects(false);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{projectId}")
	private ResponseEntity<ProjectModel> getAProject(@PathVariable String projectId) {
		ProjectModel returnValue = projectsService.findOneProject(Long.valueOf(projectId));
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{projectId}/complete/{employeeId}")
	private ResponseEntity<String> completeProject(@PathVariable String projectId, @PathVariable String employeeId) {
		projectsService.finishProject(Long.valueOf(projectId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Project Completed by Project Admin.");
	}
	
	@GetMapping(path = "/{projectId}/join/{employeeId}")
	private ResponseEntity<String> joinProject(@PathVariable String projectId, @PathVariable String employeeId) {
		projectsService.requestJoinProject(Long.valueOf(projectId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Request Successfully sent to Project Admin.");
	}
	
	@GetMapping(path = "/{projectId}/accept/{employeeId}")
	private ResponseEntity<String> joinProjectApprove(@PathVariable String projectId, @PathVariable String employeeId) {
		projectsService.joinProjectAccept(Long.valueOf(projectId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Request accepted by Project Admin.");
	}
	
	@GetMapping(path = "/{projectId}/reject/{employeeId}")
	private ResponseEntity<String> joinProjectDecline(@PathVariable String projectId, @PathVariable String employeeId) {
		projectsService.joinProjectReject(Long.valueOf(projectId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("Request denied by Project Admin.");
	}
	
	@PostMapping
	private ResponseEntity<String> createOrUpdateProject(@RequestBody ProjectCreateOrUpdateModel projectCreateOrUpdateModel) {
		projectsService.saveProject(projectCreateOrUpdateModel);
		return ResponseEntity.status(HttpStatus.OK).body("Project Saved Successfully...");
	}
	
	@PostMapping(path = "/notice/add")
	private ResponseEntity<String> addProjectNotice(@RequestBody NoticeModel projectNotice) {
		projectsService.addNotice(projectNotice);
		return ResponseEntity.status(HttpStatus.OK).body("Notice Successfully Added...");
	}
	
	@PostMapping(path = "/notice/remove")
	private ResponseEntity<String> removeProjectNotice(@RequestBody NoticeModel projectNotice) {
		projectsService.removeNotice(projectNotice);
		return ResponseEntity.status(HttpStatus.OK).body("Notice Successfully Added...");
	}
	
	@DeleteMapping
	private ResponseEntity<String> deleteAllProjects() {
		projectsService.deleteAllProjects();
		return ResponseEntity.status(HttpStatus.OK).body("Project Deleted Successfully...");
	}

}
