package gmc.project.connectversev3.authservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.authservice.entities.EmployeeEntity;

public interface EmployeeDao extends JpaRepository<EmployeeEntity, String> {
	EmployeeEntity findByEmail(String email);
	EmployeeEntity findByMobileNumber(Long mobileNumber);
}
