package gmc.project.connectversev3.prophetservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.prophetservice.entities.MailEntity;

public interface MailDao extends JpaRepository<MailEntity, String> {

}
