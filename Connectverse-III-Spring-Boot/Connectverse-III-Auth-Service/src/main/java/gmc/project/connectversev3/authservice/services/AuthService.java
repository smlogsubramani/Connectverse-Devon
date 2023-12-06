package gmc.project.connectversev3.authservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import gmc.project.connectversev3.authservice.entities.EmployeeEntity;
import gmc.project.connectversev3.authservice.entities.EmployerEntity;
import gmc.project.connectversev3.authservice.models.EmployeeModel;
import gmc.project.connectversev3.authservice.models.EmployerModel;

public interface AuthService extends UserDetailsService {
	public EmployerEntity findEmployerByUserName(String userName);
	public EmployeeEntity findEmployeeByUserName(String userName);
	
	public void createEmployee(EmployeeModel employeeModel);
	public void createEmployer(EmployerModel employerModel);
}
