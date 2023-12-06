package gmc.project.connectversev3.jobservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.SchemesEntity;
import gmc.project.connectversev3.jobservice.models.WorkType;

public interface SchemesDao extends JpaRepository<SchemesEntity, Long> {
	SchemesEntity findByWorkType(WorkType workType);
}
