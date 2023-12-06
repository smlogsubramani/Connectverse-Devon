package gmc.project.connectversev3.jobservice.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.EmployeeEntity;


@Transactional
public interface EmployeeDao extends JpaRepository<EmployeeEntity, String> {
	List<EmployeeEntity> findByIsTechnicalWorker(Boolean isTechnicalWorker);
	List<EmployeeEntity> findByAddress(String address);
}
