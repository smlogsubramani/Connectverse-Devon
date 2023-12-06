package gmc.project.connectversev3.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.connectversev3.authservice.models.DifficultyLevel;
import gmc.project.connectversev3.authservice.models.EmployeeModel;
import gmc.project.connectversev3.authservice.models.EmployerModel;
import gmc.project.connectversev3.authservice.models.Gender;
import gmc.project.connectversev3.authservice.models.State;
import gmc.project.connectversev3.authservice.models.WorkType;
import gmc.project.connectversev3.authservice.services.AuthService;

@RequestMapping(path = "/auth")
@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping(path = "/register/employer")
	private ResponseEntity<String> resgisterEmployer(@RequestBody EmployerModel employerModel) {
		authService.createEmployer(employerModel);
		return ResponseEntity.status(HttpStatus.CREATED).body("The Employer is Registered Successfully...");
	}
	
	@PostMapping(path = "/register/employee")
	private ResponseEntity<String> resgisterEmployee(@RequestBody EmployeeModel employeeModel) {
		authService.createEmployee(employeeModel);
		return ResponseEntity.status(HttpStatus.CREATED).body("The Employee is Registered Successfully...");
	}
	
	@GetMapping(path = "/states")
	private ResponseEntity<State[]> getAllStates() {
		return ResponseEntity.status(HttpStatus.OK).body(State.values());
	}
	
	@GetMapping(path = "/work-type")
	private ResponseEntity<WorkType[]> getAllWorks() {
		return ResponseEntity.status(HttpStatus.OK).body(WorkType.values());
	}
	
	@GetMapping(path = "/gender")
	private ResponseEntity<Gender[]> getAllGender() {
		return ResponseEntity.status(HttpStatus.OK).body(Gender.values());
	}
	
	@GetMapping(path = "/difficulty-level")
	private ResponseEntity<DifficultyLevel[]> getAllDifficultyLevels() {
		return ResponseEntity.status(HttpStatus.OK).body(DifficultyLevel.values());
	}

}
