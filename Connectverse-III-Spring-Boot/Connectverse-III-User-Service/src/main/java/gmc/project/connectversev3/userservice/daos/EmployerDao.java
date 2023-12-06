package gmc.project.connectversev3.userservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.userservice.entities.EmployerEntity;

public interface EmployerDao extends JpaRepository<EmployerEntity, String> {
	EmployerEntity findByEmail(String email);
	EmployerEntity findByMobileNumber(Long mobileNumber);
}
