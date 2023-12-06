package gmc.project.connectversev3.jobservice.daos;

import java.util.Optional;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.CompanyEntity;

@Transactional
public interface CompanyDao extends JpaRepository<CompanyEntity, String> {
	Optional<CompanyEntity> findByGstNumber(String gstNumber);
	List<CompanyEntity> findByIsEnabled(Boolean isEnabled);
}
