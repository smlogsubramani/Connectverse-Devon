package gmc.project.connectversev3.jobservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.JobDeclineEntity;

public interface JobDeclineDao extends JpaRepository<JobDeclineEntity, String> {

}
