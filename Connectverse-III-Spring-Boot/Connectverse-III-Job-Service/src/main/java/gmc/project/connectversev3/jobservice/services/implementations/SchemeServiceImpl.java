package gmc.project.connectversev3.jobservice.services.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.jobservice.daos.EmployeeDao;
import gmc.project.connectversev3.jobservice.daos.SchemesDao;
import gmc.project.connectversev3.jobservice.entities.EmployeeEntity;
import gmc.project.connectversev3.jobservice.entities.SchemesEntity;
import gmc.project.connectversev3.jobservice.exceptions.EmployeeNotFoundException;
import gmc.project.connectversev3.jobservice.exceptions.SchemeNotFoundException;
import gmc.project.connectversev3.jobservice.models.SchemeModel;
import gmc.project.connectversev3.jobservice.services.SchemesService;

@Service
public class SchemeServiceImpl implements SchemesService {
	
	@Autowired
	private SchemesDao schemesDao;
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public SchemesEntity findOne(Long id) {
		SchemesEntity foundScheme = schemesDao.findById(id).orElse(null);
		if(foundScheme == null)
			throw new SchemeNotFoundException("Scheme Id: " + id);
		return foundScheme;
	}

	@Override
	public SchemeModel findOneScheme(Long id) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SchemesEntity found = findOne(id);
		SchemeModel returnValue = modelMapper.map(found, SchemeModel.class);
		return returnValue;
	}

	@Override
	public List<SchemeModel> findAllScheme() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<SchemesEntity> allScheme = schemesDao.findAll();
		List<SchemeModel> returnValue = new ArrayList<>();
		allScheme.forEach(sche -> {
			SchemeModel scheme = modelMapper.map(sche, SchemeModel.class);
			returnValue.add(scheme);
		});
 		return returnValue;
	}

	@Override
	public void createOrUpdate(SchemeModel schemeModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		if(schemeModel.getId() == null) {
			SchemesEntity detached = modelMapper.map(schemeModel, SchemesEntity.class);
			detached.setIntroducedIn(LocalDate.now());
			schemesDao.save(detached);
		} else {
			SchemesEntity detached = findOne(schemeModel.getId());
			detached.setTittle(schemeModel.getTittle());
			detached.setDescription(schemeModel.getDescription());
			detached.setWorkType(schemeModel.getWorkType());
			schemesDao.save(detached);
		}
	}

	@Override
	public void applyToScheme(Long schemeId, String employeeId) {
		SchemesEntity scheme = findOne(schemeId);
		EmployeeEntity employee = employeeDao.findById(employeeId).orElse(null);
		if(employee == null)
			throw new EmployeeNotFoundException(employeeId);
		scheme.getAppliedEmployees().add(employee);
		employee.getAppliedSchemes().add(scheme);
		employeeDao.save(employee);
	}

	@Override
	public void rejectFromScheme(Long schemeId, String employeeId) {
		SchemesEntity scheme = findOne(schemeId);
		EmployeeEntity employee = employeeDao.findById(employeeId).orElse(null);
		if(employee == null)
			throw new EmployeeNotFoundException(employeeId);
		scheme.getAppliedEmployees().remove(employee);
		employee.getAppliedSchemes().remove(scheme);
		employeeDao.save(employee);
	}

	@Override
	public void addToScheme(Long schemeId, String employeeId) {
		SchemesEntity scheme = findOne(schemeId);
		EmployeeEntity employee = employeeDao.findById(employeeId).orElse(null);
		if(employee == null)
			throw new EmployeeNotFoundException(employeeId);
		scheme.getAppliedEmployees().remove(employee);
		employee.getAppliedSchemes().remove(scheme);
		scheme.getBenifittedEmployees().add(employee);
		employee.getBenifittedSchemes().add(scheme);
		employeeDao.save(employee);
	}

	@Override
	public void removeFromScheme(Long schemeId, String employeeId) {
		SchemesEntity scheme = findOne(schemeId);
		EmployeeEntity employee = employeeDao.findById(employeeId).orElse(null);
		if(employee == null)
			throw new EmployeeNotFoundException(employeeId);
		scheme.getBenifittedEmployees().remove(employee);
		employee.getBenifittedSchemes().remove(scheme);
		employeeDao.save(employee);
	}

}
