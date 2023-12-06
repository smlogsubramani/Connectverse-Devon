package gmc.project.connectversev3.jobservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.ReportEntity;

public interface ReportsDao extends JpaRepository<ReportEntity, String> {

}
