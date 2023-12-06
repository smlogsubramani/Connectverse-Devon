package gmc.project.connectversev3.jobservice.services;

import java.util.List;

import gmc.project.connectversev3.jobservice.entities.CompanyEntity;
import gmc.project.connectversev3.jobservice.models.CompanyModel;
import gmc.project.connectversev3.jobservice.models.CreateOrUpdateCompanyModel;

public interface CompanyService {
	public CompanyEntity findOne(String companyId);
	public List<CompanyModel> getAllCompanies();
	public CompanyModel getACompany(String companyId);
	public void addCompany(CreateOrUpdateCompanyModel companyModel);
	public void addManyCompany(List<CreateOrUpdateCompanyModel> companyModel);
	public void deleteAllCompanies();
}
