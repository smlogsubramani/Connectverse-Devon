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

import gmc.project.connectversev3.jobservice.models.SchemeModel;
import gmc.project.connectversev3.jobservice.services.SchemesService;

@RestController
@RequestMapping(path = "/scheme")
public class SchemesController {
	
	@Autowired
	private SchemesService schemesService;
	
	@GetMapping
	private ResponseEntity<List<SchemeModel>> getAllSchemes() {
		List<SchemeModel> returnValue = schemesService.findAllScheme();
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{schemeId}")
	private ResponseEntity<SchemeModel> getASchemes(@PathVariable String schemeId) {
		SchemeModel returnValue = schemesService.findOneScheme(Long.valueOf(schemeId));
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@PostMapping
	private ResponseEntity<String> saveScheme(@RequestBody SchemeModel schemeModel) {
		schemesService.createOrUpdate(schemeModel);
		return ResponseEntity.status(HttpStatus.OK).body("Scheme details have been updated...");
	}
	
	@GetMapping(path = "/{schemeId}/apply/{employeeId}")
	public ResponseEntity<String> applyForScheme(@PathVariable String schemeId, @PathVariable String employeeId) {
		schemesService.applyToScheme(Long.valueOf(schemeId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("You have Successfully applued for the scheme...");
	}
	
	@GetMapping(path = "/{schemeId}/reject/{employeeId}")
	private ResponseEntity<String> rejectApproval(@PathVariable String schemeId, @PathVariable String employeeId) {
		schemesService.rejectFromScheme(Long.valueOf(schemeId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("You have Successfully removed for the scheme...");
	}
	
	@GetMapping(path = "/{schemeId}/add/{employeeId}")
	private ResponseEntity<String> addToScheme(@PathVariable String schemeId, @PathVariable String employeeId) {
		schemesService.addToScheme(Long.valueOf(schemeId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("You have Successfully applued for the scheme...");
	}
	
	@GetMapping(path = "/{schemeId}/remove/{employeeId}")
	private ResponseEntity<String> removeFromScheme(@PathVariable String schemeId, @PathVariable String employeeId) {
		schemesService.removeFromScheme(Long.valueOf(schemeId), employeeId);
		return ResponseEntity.status(HttpStatus.OK).body("You have Successfully removed for the scheme...");
	}

}
