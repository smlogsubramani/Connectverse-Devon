package gmc.project.connectversev3.jobservice.services;

import java.util.List;

import gmc.project.connectversev3.jobservice.entities.SchemesEntity;
import gmc.project.connectversev3.jobservice.models.SchemeModel;

public interface SchemesService {
	public SchemesEntity findOne(Long id);
	public SchemeModel findOneScheme(Long id);
	public List<SchemeModel> findAllScheme();
	public void createOrUpdate(SchemeModel schemeModel);
	public void applyToScheme(Long schemeId, String employeeId);
	public void rejectFromScheme(Long schemeId, String employeeId);
	public void addToScheme(Long schemeId, String employeeId);
	public void removeFromScheme(Long schemeId, String employeeId);
}
