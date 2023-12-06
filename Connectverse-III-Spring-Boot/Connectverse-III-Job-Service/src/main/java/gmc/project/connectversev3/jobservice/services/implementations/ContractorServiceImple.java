package gmc.project.connectversev3.jobservice.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.jobservice.daos.EmployeeDao;
import gmc.project.connectversev3.jobservice.daos.EmployerDao;
import gmc.project.connectversev3.jobservice.entities.EmployeeEntity;
import gmc.project.connectversev3.jobservice.entities.EmployerEntity;
import gmc.project.connectversev3.jobservice.exceptions.EmployerNotFoundException;
import gmc.project.connectversev3.jobservice.models.ListEmployeeModel;
import gmc.project.connectversev3.jobservice.services.ContractorService;

@Service
public class ContractorServiceImple implements ContractorService {
	
	@Autowired
	private EmployerDao employerDao;
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public List<ListEmployeeModel> employeesToReach(String contractorid) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		EmployerEntity foundEmployer = employerDao.findById(contractorid).orElse(null);
		if (foundEmployer == null)
			throw new EmployerNotFoundException("Contrator Id: " + contractorid);
		List<EmployeeEntity> employees = employeeDao.findByAddress(foundEmployer.getAddress());
		Stream<EmployeeEntity> isNontech = employees.stream().filter(employee -> employee.getIsTechnicalWorker().equals(false));
		Stream<EmployeeEntity> isFree = isNontech.filter(employee -> employee.getIsOccupied().equals(false));
		List<ListEmployeeModel> returnValue = new ArrayList<>();
		isFree.forEach(employee -> {
			returnValue.add(modelMapper.map(employee, ListEmployeeModel.class));
		});
		return returnValue;
	}

}
