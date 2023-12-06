package gmc.project.connectversev3.jobservice.controllers;

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

import gmc.project.connectversev3.jobservice.models.CompanyModel;
import gmc.project.connectversev3.jobservice.models.CreateOrUpdateCompanyModel;
import gmc.project.connectversev3.jobservice.services.CompanyService;

@RestController
@RequestMapping(path = "/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	private ResponseEntity<List<CompanyModel>> getAllCompanies() {
		List<CompanyModel> returnValue = companyService.getAllCompanies();
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{companyId}")
	private ResponseEntity<CompanyModel> getACompany(@PathVariable String companyId) {
		CompanyModel returnValue = companyService.getACompany(companyId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@PostMapping
	private ResponseEntity<String> registerCompany(@RequestBody CreateOrUpdateCompanyModel companyModel) {
		companyService.addCompany(companyModel);
		return ResponseEntity.status(HttpStatus.OK).body("Company Registered...");
	}
	
	@PostMapping(path = "/many")
	private ResponseEntity<String> registerMayCompany(@RequestBody List<CreateOrUpdateCompanyModel> companyModel) {
		companyService.addManyCompany(companyModel);
		return ResponseEntity.status(HttpStatus.OK).body("Company Registered...");
	}
	
	@DeleteMapping
	private ResponseEntity<String> deleteCompanies() {
		companyService.deleteAllCompanies();
		return ResponseEntity.status(HttpStatus.OK).body("Company Deleted...");
	}

}
