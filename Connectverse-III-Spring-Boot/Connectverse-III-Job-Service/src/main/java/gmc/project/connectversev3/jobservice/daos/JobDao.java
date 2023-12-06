package gmc.project.connectversev3.jobservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import gmc.project.connectversev3.jobservice.entities.JobEntity;

public interface JobDao extends JpaRepository<JobEntity, Long> {
	List<JobEntity> findByIsCompleted(Boolean isCompleted);
	List<JobEntity> findByIsTechnicalJob(Boolean isTechnicalJob);
}
