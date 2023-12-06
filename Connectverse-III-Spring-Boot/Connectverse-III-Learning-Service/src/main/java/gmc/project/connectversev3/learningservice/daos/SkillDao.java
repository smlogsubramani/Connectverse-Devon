package gmc.project.connectversev3.learningservice.daos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.learningservice.entities.SkillEntity;

@Transactional
public interface SkillDao extends JpaRepository<SkillEntity, String> {

}
