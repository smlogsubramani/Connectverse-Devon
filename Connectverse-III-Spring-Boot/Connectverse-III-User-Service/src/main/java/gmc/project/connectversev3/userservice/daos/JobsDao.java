package gmc.project.connectversev3.userservice.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.userservice.entities.JobEntity;

public interface JobsDao extends JpaRepository<JobEntity, Long> {
	List<JobEntity> findByIsTechnicalJob(Boolean isTechnicalJob);
	List<JobEntity> findByLocation(String location);
}
