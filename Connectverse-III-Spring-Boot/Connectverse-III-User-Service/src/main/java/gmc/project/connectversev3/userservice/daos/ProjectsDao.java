package gmc.project.connectversev3.userservice.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.userservice.entities.EmployeeEntity;
import gmc.project.connectversev3.userservice.entities.ProjectEntity;

public interface ProjectsDao extends JpaRepository<ProjectEntity, Long> {
	Optional<ProjectEntity> findByProjectAdmin(EmployeeEntity projectAdmin);
}
