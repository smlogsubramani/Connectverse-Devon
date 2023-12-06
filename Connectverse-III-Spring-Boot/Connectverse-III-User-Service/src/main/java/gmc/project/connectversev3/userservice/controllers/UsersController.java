package gmc.project.connectversev3.userservice.controllers;

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

import gmc.project.connectversev3.userservice.entities.EmployeeEntity;
import gmc.project.connectversev3.userservice.entities.EmployerEntity;
import gmc.project.connectversev3.userservice.models.EmployeeModel;
import gmc.project.connectversev3.userservice.models.EmployerModel;
import gmc.project.connectversev3.userservice.models.UserModel;
import gmc.project.connectversev3.userservice.services.UserService;


@RestController
@RequestMapping(path = "/user")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/register/bulk")
	private ResponseEntity<String> registerManyEmployees(@RequestBody List<EmployeeModel> emlpoyeesModel) {
		userService.createManyEmployees(emlpoyeesModel);
		return ResponseEntity.status(HttpStatus.CREATED).body("The Users have been added to this website...");
	}
	
	@GetMapping(path = "/e-shram/transfer")
	private ResponseEntity<String> fetchDataFromEshram() {
		userService.fetchFromEshram();
		return ResponseEntity.status(HttpStatus.OK).body("Data Migrated Successfully...");
	}
	
	@GetMapping(path = "/{userId}")
	private ResponseEntity<UserModel> getProfile(@PathVariable String userId) {
		UserModel returnValue = userService.getProfile(userId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/employee/{employeeId}")
	private ResponseEntity<EmployeeEntity> getAEmployee(@PathVariable String employeeId) {
		EmployeeEntity returnValue = userService.findEmployeeByUserName(employeeId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/employer/{employerId}")
	private ResponseEntity<EmployerEntity> getAEmployer(@PathVariable String employerId) {
		EmployerEntity returnValue = userService.findEmployerByUserName(employerId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@PostMapping(path = "/employee")
	private ResponseEntity<String> updateAEmployee(@RequestBody EmployeeModel employeeEntity) {
		userService.updateEmployee(employeeEntity);
		return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully...");
	}
	
	@PostMapping(path = "/employer")
	private ResponseEntity<String> updateAEmployer(@RequestBody EmployerModel employerEntity) {
		userService.updateEmployer(employerEntity);
		return ResponseEntity.status(HttpStatus.OK).body("Updated Successfully...");
	}
	
	@DeleteMapping
	private ResponseEntity<String> deleteManyUsers() {
		userService.deleteAllEmployees();
		return ResponseEntity.status(HttpStatus.OK).body("Employees Deleted");
	}

}
