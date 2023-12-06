package gmc.project.connectversev3.jobservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.connectversev3.jobservice.models.ListEmployeeModel;
import gmc.project.connectversev3.jobservice.services.ContractorService;

@RestController
@RequestMapping(path = "/contractor")
public class ContractorController {
	
	@Autowired
	private ContractorService contractorService;
	
	@GetMapping(path = "/{contractorId}")
	private ResponseEntity<List<ListEmployeeModel>> reachToEmployees(@PathVariable String contractorId) {
		List<ListEmployeeModel> returnValue = contractorService.employeesToReach(contractorId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}

}
