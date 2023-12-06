package gmc.project.connectversev3.jobservice.daos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.jobservice.entities.SkillEntity;

@Transactional
public interface SkillDao extends JpaRepository<SkillEntity, String> {

}
