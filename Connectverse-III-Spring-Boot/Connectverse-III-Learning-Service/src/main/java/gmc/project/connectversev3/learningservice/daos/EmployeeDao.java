package gmc.project.connectversev3.learningservice.daos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.learningservice.entities.EmployeeEntity;


@Transactional
public interface EmployeeDao extends JpaRepository<EmployeeEntity, String> {

}
