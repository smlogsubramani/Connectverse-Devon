package gmc.project.connectversev3.userservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.connectversev3.userservice.entities.SiteSettingsEntity;

public interface SiteSettingsDao extends JpaRepository<SiteSettingsEntity, Long> {

}
