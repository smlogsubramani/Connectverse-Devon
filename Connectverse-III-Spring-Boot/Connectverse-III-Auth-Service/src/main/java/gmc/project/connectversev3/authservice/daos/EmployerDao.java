package gmc.project.connectversev3.authservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.authservice.entities.EmployerEntity;

public interface EmployerDao extends JpaRepository<EmployerEntity, String> {
	EmployerEntity findByEmail(String email);
	EmployerEntity findByMobileNumber(Long mobileNumber);
}
