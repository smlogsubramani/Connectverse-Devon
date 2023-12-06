package gmc.project.connectversev3.jobservice.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.jobservice.daos.CompanyDao;
import gmc.project.connectversev3.jobservice.daos.EmployerDao;
import gmc.project.connectversev3.jobservice.entities.CompanyEntity;
import gmc.project.connectversev3.jobservice.entities.EmployerEntity;
import gmc.project.connectversev3.jobservice.exceptions.CompanyNotFoundException;
import gmc.project.connectversev3.jobservice.exceptions.EmployerNotFoundException;
import gmc.project.connectversev3.jobservice.models.CompanyModel;
import gmc.project.connectversev3.jobservice.models.CreateOrUpdateCompanyModel;
import gmc.project.connectversev3.jobservice.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private EmployerDao employerDao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public CompanyEntity findOne(String companyId) {
		CompanyEntity foundCompany = companyDao.findById(companyId).orElse(null);
		if(foundCompany == null)
			throw new CompanyNotFoundException("Company Id: " + companyId);
		return foundCompany;
	}

	@Override
	public void addCompany(CreateOrUpdateCompanyModel companyModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		if(companyModel.getId() == null) {
			CompanyEntity detached = modelMapper.map(companyModel, CompanyEntity.class);
			detached.setPasscode(detached.getName() + UUID.randomUUID());
			companyDao.save(detached);
		} else {
			CompanyEntity detached = companyDao.findByGstNumber(companyModel.getGstNumber()).orElse(null);
			if(detached == null)
				throw new CompanyNotFoundException("GST Num: " + companyModel.getGstNumber());
			if(detached.getGstNumber().equals(companyModel.getGstNumber())) {
				detached.setIsEnabled(true);
				CompanyEntity savedCompany = companyDao.save(detached);
				if(companyModel.getEmployerId() != null) {
					EmployerEntity employer = employerDao.findById(companyModel.getEmployerId()).orElse(null);
					if(employer == null)
						throw new EmployerNotFoundException("Employer Id: " + companyModel.getEmployerId());
					detached.setEmployer(null);
					employerDao.delete(employer);
					companyDao.save(savedCompany);
				}			
				if(companyModel.getEmployerUpdated() != null) {
					EmployerEntity employer = modelMapper.map(companyModel.getEmployerUpdated(), EmployerEntity.class);
					employer.setCompany(savedCompany);
					employer.setAadharId(bCryptPasswordEncoder.encode(companyModel.getEmployerUpdated().getAadharId()));
					employer.setIsBlocked(false);
					savedCompany.setEmployer(employer);
					companyDao.save(savedCompany);
				}
			} else {
				throw new CompanyNotFoundException("Company Id: " + companyModel.getId());
			}
		}
	}

	@Override
	public List<CompanyModel> getAllCompanies() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<CompanyModel> returnValue = new ArrayList<>();
		companyDao.findByIsEnabled(true).forEach(company -> {
			returnValue.add(modelMapper.map(company, CompanyModel.class));
		});
		return returnValue;
	}

	@Override
	public void addManyCompany(List<CreateOrUpdateCompanyModel> companyModel) {
		companyModel.forEach(company -> {
			addCompany(company);
		});
		
	}

	@Override
	public void deleteAllCompanies() {
		companyDao.deleteAll();
	}

	@Override
	public CompanyModel getACompany(String companyId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CompanyEntity foundCompany = findOne(companyId);
		CompanyModel returnValue = modelMapper.map(foundCompany, CompanyModel.class);
		return returnValue;
	}

}
