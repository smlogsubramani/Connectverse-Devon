package gmc.project.connectversev3.authservice.services.implementations;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.authservice.daos.EmployeeDao;
import gmc.project.connectversev3.authservice.daos.EmployerDao;
import gmc.project.connectversev3.authservice.entities.EmployeeEntity;
import gmc.project.connectversev3.authservice.entities.EmployerEntity;
import gmc.project.connectversev3.authservice.models.EmployeeModel;
import gmc.project.connectversev3.authservice.models.EmployerModel;
import gmc.project.connectversev3.authservice.models.WorkType;
import gmc.project.connectversev3.authservice.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private EmployeeDao employeeDao;	
	@Autowired
	private EmployerDao employerDao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			EmployerEntity foundUser = findEmployerByUserName(username);
			return new User(username, foundUser.getAadharId(), true, true, true, true, new ArrayList<>());
		} catch (UsernameNotFoundException e) {
			EmployeeEntity foundUser = findEmployeeByUserName(username);
			return new User(username, foundUser.getAadharId(), true, true, true, true, new ArrayList<>());
		}
	}

	@Override
	public EmployerEntity findEmployerByUserName(String userName) {		
		EmployerEntity foundEmployer = null;		
		if(userName.contains("@")) {
			foundEmployer = employerDao.findByEmail(userName);
		} else {
			try {
				Long mobileNumber = Long.valueOf(userName);
				foundEmployer = employerDao.findByMobileNumber(mobileNumber);
			} catch (Exception e) {
				foundEmployer = employerDao.findById(userName).get();
			}
		}		
		if(foundEmployer == null) throw new UsernameNotFoundException("Employer Not found...");		
		return foundEmployer;
	}

	@Override
	public EmployeeEntity findEmployeeByUserName(String userName) {
		EmployeeEntity foundEmployee = null;		
		if(userName.contains("@")) {
			foundEmployee = employeeDao.findByEmail(userName);
		} else {
			try {
				Long mobileNumber = Long.valueOf(userName);
				foundEmployee = employeeDao.findByMobileNumber(mobileNumber);
			} catch (Exception e) {
				foundEmployee = employeeDao.findById(userName).get();
			}
		}		
		if(foundEmployee == null) throw new UsernameNotFoundException("Employee Not found...");		
		return foundEmployee;
	}

	@Override
	public void createEmployee(EmployeeModel employeeModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		
		EmployeeEntity detachedEmployee = modelMapper.map(employeeModel, EmployeeEntity.class);
		detachedEmployee.setIsTechnicalWorker(true);
		detachedEmployee.setPrefferedWorkType(WorkType.TECHNICAL);
		detachedEmployee.setAadharId(bCryptPasswordEncoder.encode(detachedEmployee.getAadharId()));
		employeeDao.save(detachedEmployee);
	}

	@Override
	public void createEmployer(EmployerModel employerModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		
		EmployerEntity detachedEmployer = modelMapper.map(employerModel, EmployerEntity.class);
		detachedEmployer.setAadharId(bCryptPasswordEncoder.encode(detachedEmployer.getAadharId()));
		employerDao.save(detachedEmployer);
	}

}
