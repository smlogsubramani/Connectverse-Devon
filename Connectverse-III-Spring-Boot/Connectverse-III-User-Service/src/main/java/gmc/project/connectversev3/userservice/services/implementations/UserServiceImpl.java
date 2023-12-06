package gmc.project.connectversev3.userservice.services.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gmc.project.connectversev3.userservice.daos.EmployeeDao;
import gmc.project.connectversev3.userservice.daos.EmployerDao;
import gmc.project.connectversev3.userservice.daos.ProjectsDao;
import gmc.project.connectversev3.userservice.daos.SiteSettingsDao;
import gmc.project.connectversev3.userservice.entities.EmployeeEntity;
import gmc.project.connectversev3.userservice.entities.EmployerEntity;
import gmc.project.connectversev3.userservice.entities.ProjectEntity;
import gmc.project.connectversev3.userservice.entities.SiteSettingsEntity;
import gmc.project.connectversev3.userservice.exceptions.UserNotFoundException;
import gmc.project.connectversev3.userservice.models.EmployeeModel;
import gmc.project.connectversev3.userservice.models.EmployerModel;
import gmc.project.connectversev3.userservice.models.Gender;
import gmc.project.connectversev3.userservice.models.UserModel;
import gmc.project.connectversev3.userservice.models.WorkType;
import gmc.project.connectversev3.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private EmployerDao employerDao;
	@Autowired
	private SiteSettingsDao siteSettingsDao;
	@Autowired
	private ProjectsDao projectsDao;
	@Autowired
	private Environment environment;

	@Override
	public EmployerEntity findEmployerByUserName(String userName) {
		EmployerEntity foundEmployer = null;
		if (userName.contains("@")) {
			foundEmployer = employerDao.findByEmail(userName);
		} else {
			try {
				Long mobileNumber = Long.valueOf(userName);
				foundEmployer = employerDao.findByMobileNumber(mobileNumber);
			} catch (Exception e) {
				foundEmployer = employerDao.findById(userName).get();
			}
		}
		if (foundEmployer == null)
			throw new UserNotFoundException("Employer Not found...");
		return foundEmployer;
	}

	@Override
	public EmployeeEntity findEmployeeByUserName(String userName) {
		EmployeeEntity foundEmployee = null;
		if (userName.contains("@")) {
			foundEmployee = employeeDao.findByEmail(userName);
		} else {
			try {
				Long mobileNumber = Long.valueOf(userName);
				foundEmployee = employeeDao.findByMobileNumber(mobileNumber);
			} catch (Exception e) {
				foundEmployee = employeeDao.findById(userName).orElse(null);
			}
		}
		if (foundEmployee == null)
			throw new UserNotFoundException("Employee Not found...");
		return foundEmployee;
	}

	@Override
	public List<EmployeeEntity> createManyEmployees(List<EmployeeModel> employees) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SiteSettingsEntity batchNo = siteSettingsDao.findById(1L).orElse(null);

		List<EmployeeEntity> employeesDetached = new ArrayList<>();
		employees.forEach(employee -> {
			EmployeeEntity foundEmployee = employeeDao.findByAadharId(employee.getAadharId()).orElse(null);
			if (foundEmployee == null) {
				EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
				employeeEntity.setAadharId(bCryptPasswordEncoder.encode(employeeEntity.getAadharId()));
				employeeEntity.setBatchNo(Integer.valueOf(batchNo.getField1()));
				employeesDetached.add(employeeEntity);
			} else {
				foundEmployee.setFirstName(employee.getFirstName());
				foundEmployee.setLastName(employee.getLastName());
				foundEmployee.setAge(employee.getAge());
				foundEmployee.setGender(employee.getGender());
				foundEmployee.setPrefferedWorkType(employee.getPrefferedWorkType());
				foundEmployee.setCvUrl(employee.getCvUrl());
				foundEmployee.setEmail(employee.getEmail());
				foundEmployee.setMobileNumber(employee.getMobileNumber());
				foundEmployee.setAddress(employee.getAddress());
				foundEmployee.setLocation(employee.getLocation());
				foundEmployee.setState(employee.getState());
				foundEmployee.setReadyToRelocate(employee.getReadyToRelocate());
				foundEmployee.setHasDrivingLicence(employee.getHasDrivingLicence());
				foundEmployee.setHasVehicle(employee.getHasVehicle());
				foundEmployee.setExpectedWagePerHour(employee.getExpectedWagePerHour());
				foundEmployee.setExpectedWorkingHoursPerWeek(employee.getExpectedWorkingHoursPerWeek());
				foundEmployee.setIsTechnicalWorker(employee.getIsTechnicalWorker());
				foundEmployee.setIsOccupied(employee.getIsOccupied());
				foundEmployee.setPhysicalHealthPoints(employee.getPhysicalHealthPoints());
				foundEmployee.setMentalHealthPoints(employee.getMentalHealthPoints());
				foundEmployee.setKnowsToOperateMobile(employee.getKnowsToOperateMobile());
				foundEmployee.setKnowsToReadAndWrite(employee.getKnowsToReadAndWrite());
				employeesDetached.add(foundEmployee);
			}
		});
		List<EmployeeEntity> saved = employeeDao.saveAllAndFlush(employeesDetached);
		Integer latestBatch = Integer.valueOf(batchNo.getField1()) + 1;
		Long totalEmployeeCount = employeeDao.count();
		Integer addedCount = employees.size();
		batchNo.setField1(latestBatch.toString());
		batchNo.setField2(totalEmployeeCount.toString());
		batchNo.setField3(addedCount.toString());
		siteSettingsDao.save(batchNo);
		return saved;

	}

	@Override
	public void updateEmployer(EmployerModel employerModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		EmployerEntity existing = employerDao.findById(employerModel.getId()).orElse(null);
		if (existing == null)
			throw new UserNotFoundException(employerModel.getId());
		modelMapper.map(employerModel, existing);
		employerDao.save(existing);
	}

	@Override
	public void updateEmployee(EmployeeModel employeeModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		EmployeeEntity existing = employeeDao.findById(employeeModel.getId()).orElse(null);
		if (existing == null)
			throw new UserNotFoundException(employeeModel.getId());
		modelMapper.map(employeeModel, existing);
		employeeDao.save(existing);
	}

	@Override
	public void deleteAllEmployees() {
		Set<EmployeeEntity> foundEmployees = new HashSet<>();

		employeeDao.findAll().forEach(employee -> {
			ProjectEntity foundEntity = projectsDao.findByProjectAdmin(employee).orElse(null);
			if (foundEntity == null) {
				foundEmployees.add(employee);
			}
		});

		foundEmployees.forEach(employee -> {
			employeeDao.delete(employee);
		});

		SiteSettingsEntity batchNo = siteSettingsDao.findById(1L).orElse(null);
		batchNo.setField1("1");
		batchNo.setField2("0");
		batchNo.setField3("0");
		siteSettingsDao.save(batchNo);
	}

	@Override
	public List<EmployeeEntity> fetchFromEshram() {
		String eShramUrl = environment.getProperty("eShramUrl");
		String mlServerWageCalculation = environment.getProperty("mlServerWageCalculation");
		String mlServerWorkingCalculation = environment.getProperty("mlServerWorkingCalculation");

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EmployeeModel[]> response = restTemplate.getForEntity(eShramUrl, EmployeeModel[].class);
		log.error(response.toString());
		List<EmployeeModel> employees = new ArrayList<>();
		for(EmployeeModel employee :response.getBody()) {
			log.debug(employee.getFirstName());
			employee.setId(null);
			
			Integer WorkingHrsMoreThan40PerWeek_input = 0;
			if(employee.getExpectedWorkingHoursPerWeek() > 40) {
				WorkingHrsMoreThan40PerWeek_input = 1;
			}
			
			String finalUrlWage = mlServerWageCalculation + "/" + employee.getAge() + "/" + employee.getGender()
					+ "/" + WorkingHrsMoreThan40PerWeek_input + "/" + employee.getPrefferedWorkType();	
			log.error(finalUrlWage);
			ResponseEntity<Integer> expectedWage = restTemplate.getForEntity(finalUrlWage, Integer.class);
			
			
			String finalUrlWorking = mlServerWorkingCalculation + "/" + employee.getAge() + "/" + employee.getGender() + "/" + expectedWage.getBody()
					+ "/" +  employee.getPrefferedWorkType() + "/" + employee.getPhysicalHealthPoints();
			log.error(finalUrlWorking);
			ResponseEntity<Integer> workTime = restTemplate.getForEntity(finalUrlWorking, Integer.class);

			employee.setExpectedWagePerHour(expectedWage.getBody());
			if(workTime.getBody() == 0) {
				employee.setExpectedWorkingHoursPerWeek(20);
			} else {
				employee.setExpectedWorkingHoursPerWeek(40);
			}
			employee.setIsBlocked(false);
			employee.setIsOccupied(false);
			employee.setIsTechnicalWorker(false);
			employee.setWaitingForJobTime(-10);
			employee.setInactiveJobSeekTime(0);
			employee.setJobReports(0);
			employee.setCreditPoints(0);
			employees.add(employee);
		}
		List<EmployeeEntity> savedEmployees = createManyEmployees(employees);
//		restTemplate.delete(eShramUrl);
		List<EmployeeEntity> returnVal = new ArrayList<>();
//		log.error("Size = {}", savedEmployees.size());
		return savedEmployees;
	}

	@Override
	public UserModel getProfile(String userId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserModel returnValue = new UserModel();

		EmployeeEntity employee = employeeDao.findById(userId).orElse(null);
		if (employee != null) {
			modelMapper.map(employee, returnValue);
			returnValue.setIsEmployer(false);
		} else {
			EmployerEntity employer = employerDao.findById(userId).orElse(null);
			if (employer == null)
				throw new UserNotFoundException(userId);
			modelMapper.map(employer, returnValue);
			returnValue.setIsEmployer(true);
		}

		return returnValue;
	}

}
