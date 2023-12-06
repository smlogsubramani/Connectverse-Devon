package gmc.project.connectversev3.jobservice.daos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.CompanyEntity;
import gmc.project.connectversev3.jobservice.entities.EmployerEntity;

@Transactional
public interface EmployerDao extends JpaRepository<EmployerEntity, String> {
	EmployerEntity findByCompany(CompanyEntity company);
	EmployerEntity findByIsContractor(Boolean isContractor);
}
