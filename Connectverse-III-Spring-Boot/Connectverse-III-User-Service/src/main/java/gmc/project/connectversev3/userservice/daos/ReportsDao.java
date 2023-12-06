package gmc.project.connectversev3.userservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.userservice.entities.ReportEntity;

public interface ReportsDao extends JpaRepository<ReportEntity, String> {

}
